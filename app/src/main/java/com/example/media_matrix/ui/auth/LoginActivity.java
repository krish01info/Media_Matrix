package com.example.media_matrix.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.media_matrix.MainActivity;
import com.example.media_matrix.R;
import com.example.media_matrix.data.local.PreferenceManager;
import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.repository.AuthRepository;
import com.example.media_matrix.databinding.ActivityLoginBinding;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // TODO: Replace with your actual Google Web Client ID from Google Cloud Console
    private static final String GOOGLE_WEB_CLIENT_ID = "34903441828-97 b4s14inffqa8iobq0ae6pojddla3tm.apps.googleusercontent.com";

    private ActivityLoginBinding binding;
    private AuthRepository authRepository;
    private PreferenceManager preferenceManager;
    private CredentialManager credentialManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is already logged in — skip to main
        preferenceManager = new PreferenceManager(this);
        ApiClient.getInstance().setPreferenceManager(preferenceManager);

        if (preferenceManager.isLoggedIn()) {
            navigateToMain();
            return;
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authRepository = new AuthRepository();
        authRepository.setPreferenceManager(preferenceManager);

        // Initialize Credential Manager for Google Sign-In
        credentialManager = CredentialManager.create(this);

        // --- Click Listeners ---
        binding.btnLogin.setOnClickListener(v -> performLogin());
        binding.linkRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
        binding.btnGoogleSignIn.setOnClickListener(v -> performGoogleSignIn());
    }

    // ===== Email / Password Login =====
    private void performLogin() {
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please fill all fields");
            return;
        }

        setLoading(true);

        authRepository.login(email, password).observe(this, response -> {
            setLoading(false);
            if (response != null) {
                navigateToMain();
            } else {
                showError("Login failed. Please check your credentials.");
            }
        });
    }

    // ===== Google Sign-In =====
    private void performGoogleSignIn() {
        setLoading(true);

        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false) // show all accounts
                .setServerClientId(GOOGLE_WEB_CLIENT_ID)
                .setAutoSelectEnabled(true)
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        credentialManager.getCredentialAsync(
                this,
                request,
                null, // CancellationSignal
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        runOnUiThread(() -> handleGoogleSignInResult(result));
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        Log.e(TAG, "Google Sign-In failed", e);
                        runOnUiThread(() -> {
                            setLoading(false);
                            showError("Google Sign-In cancelled or failed.");
                        });
                    }
                });
    }

    private void handleGoogleSignInResult(GetCredentialResponse response) {
        Credential credential = response.getCredential();

        if (credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            GoogleIdTokenCredential googleCredential = GoogleIdTokenCredential.createFrom(credential.getData());
            String idToken = googleCredential.getIdToken();

            if (idToken != null) {
                Log.d(TAG, "Got Google ID token, sending to backend...");
                sendGoogleTokenToBackend(idToken);
            } else {
                setLoading(false);
                showError("Failed to get Google ID token.");
            }
        } else {
            setLoading(false);
            showError("Unexpected credential type.");
        }
    }

    private void sendGoogleTokenToBackend(String idToken) {
        authRepository.googleLogin(idToken).observe(this, response -> {
            setLoading(false);
            if (response != null) {
                navigateToMain();
            } else {
                showError("Google authentication failed on server.");
            }
        });
    }

    // ===== Helpers =====
    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setLoading(boolean loading) {
        binding.loading.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.btnLogin.setEnabled(!loading);
        binding.btnGoogleSignIn.setEnabled(!loading);
        binding.btnGoogleSignIn.setAlpha(loading ? 0.5f : 1f);
    }

    private void showError(String message) {
        binding.errorText.setVisibility(View.VISIBLE);
        binding.errorText.setText(message);
    }
}
