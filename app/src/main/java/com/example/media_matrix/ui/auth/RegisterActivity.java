package com.example.media_matrix.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.media_matrix.MainActivity;
import com.example.media_matrix.data.local.PreferenceManager;
import com.example.media_matrix.data.repository.AuthRepository;
import com.example.media_matrix.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
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
        String username = binding.inputUsername.getText().toString().trim();
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            binding.errorText.setVisibility(View.VISIBLE);
            binding.errorText.setText("Please fill all fields");
            return;
        }

        binding.loading.setVisibility(View.VISIBLE);
        binding.btnRegister.setEnabled(false);
        binding.errorText.setVisibility(View.GONE);

        authRepository.register(username, email, password).observe(this, response -> {
            binding.loading.setVisibility(View.GONE);
            binding.btnRegister.setEnabled(true);

            if (response != null) {
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
            } else {
                binding.errorText.setVisibility(View.VISIBLE);
                binding.errorText.setText("Registration failed. Please try again.");
            }
        });
    }
}
