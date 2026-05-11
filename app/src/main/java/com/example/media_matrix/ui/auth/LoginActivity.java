package com.example.media_matrix.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import java.security.MessageDigest;
import java.util.UUID;

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

    private static final String GOOGLE_WEB_CLIENT_ID = "34903441828-97b4s14inffqa8iobq0ae6pojddla3tm.apps.googleusercontent.com";

    private ActivityLoginBinding binding;
    private AuthRepository authRepository;
    private PreferenceManager preferenceManager;
    private CredentialManager credentialManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        credentialManager = CredentialManager.create(this);

        binding.btnLogin.setOnClickListener(v -> performLogin());
        binding.linkRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        binding.btnGoogleSignIn.setOnClickListener(v -> performGoogleSignIn());
    }

    // ===== Email / Password Login (Local-Only) =====
    private void performLogin() {
        hideError();

        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            showError("Please enter your email address.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Please enter a valid email address.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showError("Please enter your password.");
            return;
        }
        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            return;
        }

        setLoading(true);
        Log.d(TAG, "Starting local login for: " + email);

        authRepository.login(email, password).observe(this, response -> {
            setLoading(false);
            if (response != null && response.isSuccess()) {
                Log.d(TAG, "Login successful");
                navigateToMain();
            } else {
                String msg = (response != null && response.getErrorMessage() != null)
                        ? response.getErrorMessage()
                        : "Login failed. Please try again.";
                Log.e(TAG, "Login failed: " + msg);
                showError(msg);
            }
        });
    }

    // ===== Google Sign-In (Local — no backend token exchange) =====
    private void performGoogleSignIn() {
        hideError();
        setLoading(true);
        Log.d(TAG, "Starting Google Sign-In...");

        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(GOOGLE_WEB_CLIENT_ID)
                .setAutoSelectEnabled(false)
                .setNonce(generateNonce())
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        credentialManager.getCredentialAsync(
                this,
                request,
                null,
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        runOnUiThread(() -> handleGoogleSignInResult(result));
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        Log.e(TAG, "Google Sign-In API Error: " + e.getType(), e);
                        runOnUiThread(() -> {
                            setLoading(false);
                            showError("Google Sign-In failed: " + e.getMessage());
                        });
                    }
                });
    }

    private void handleGoogleSignInResult(GetCredentialResponse response) {
        Credential credential = response.getCredential();

        if (credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            GoogleIdTokenCredential googleCredential = GoogleIdTokenCredential.createFrom(credential.getData());

            // Extract user info locally — no backend call needed
            String displayName = googleCredential.getDisplayName();
            String email = googleCredential.getId(); // this is the email address

            Log.d(TAG, "Google credential received. Name=" + displayName + ", Email=" + email);

            // Store locally and navigate
            authRepository.googleLogin(displayName, email).observe(this, authResponse -> {
                setLoading(false);
                if (authResponse != null && authResponse.isSuccess()) {
                    Log.d(TAG, "Local Google auth successful");
                    navigateToMain();
                } else {
                    showError("Google Sign-In failed. Please try again.");
                }
            });

        } else {
            setLoading(false);
            showError("Unexpected credential type: " + credential.getType());
        }
    }

    // ===== Nonce Generator =====
    private String generateNonce() {
        String rawNonce = UUID.randomUUID().toString();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(rawNonce.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            Log.w(TAG, "Nonce generation failed, using raw UUID", e);
            return rawNonce;
        }
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
        binding.linkRegister.setEnabled(!loading);
    }

    private void showError(String message) {
        binding.errorText.setText(message);
        binding.errorText.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        binding.errorText.setVisibility(View.GONE);
        binding.errorText.setText("");
    }
}
