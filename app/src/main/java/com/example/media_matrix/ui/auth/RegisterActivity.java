package com.example.media_matrix.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.media_matrix.MainActivity;
import com.example.media_matrix.data.local.PreferenceManager;
import com.example.media_matrix.data.repository.AuthRepository;
import com.example.media_matrix.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private ActivityRegisterBinding binding;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authRepository = new AuthRepository();
        authRepository.setPreferenceManager(new PreferenceManager(this));

        binding.btnRegister.setOnClickListener(v -> performRegister());
        binding.linkLogin.setOnClickListener(v -> finish());
    }

    private void performRegister() {
        hideError();

        String username = binding.inputUsername.getText().toString().trim();
        String email    = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();

        // --- Client-side validation ---
        if (TextUtils.isEmpty(username)) {
            showError("Please enter a username.");
            return;
        }
        if (username.length() < 3) {
            showError("Username must be at least 3 characters.");
            return;
        }
        if (!username.matches("[a-zA-Z0-9]+")) {
            showError("Username can only contain letters and numbers.");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            showError("Please enter your email address.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Please enter a valid email address.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showError("Please enter a password.");
            return;
        }
        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            return;
        }

        setLoading(true);
        Log.d(TAG, "Registering user: " + username + " / " + email);

        authRepository.register(username, email, password).observe(this, response -> {
            setLoading(false);
            if (response != null && response.isSuccess()) {
                Log.d(TAG, "Registration successful");
                // Go straight to main — user is already logged in (tokens saved)
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finishAffinity();
            } else {
                String msg = (response != null && response.getErrorMessage() != null)
                        ? response.getErrorMessage()
                        : "Registration failed. Please try again.";
                Log.e(TAG, "Registration failed: " + msg);
                showError(msg);
            }
        });
    }

    private void setLoading(boolean loading) {
        binding.loading.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.btnRegister.setEnabled(!loading);
        binding.linkLogin.setEnabled(!loading);
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
