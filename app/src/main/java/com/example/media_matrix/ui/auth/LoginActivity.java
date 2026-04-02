package com.example.media_matrix.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.media_matrix.MainActivity;
import com.example.media_matrix.data.local.PreferenceManager;
import com.example.media_matrix.data.repository.AuthRepository;
import com.example.media_matrix.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authRepository = new AuthRepository();
        authRepository.setPreferenceManager(new PreferenceManager(this));

        binding.btnLogin.setOnClickListener(v -> performLogin());
        binding.linkRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void performLogin() {
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            binding.errorText.setVisibility(View.VISIBLE);
            binding.errorText.setText("Please fill all fields");
            return;
        }

        binding.loading.setVisibility(View.VISIBLE);
        binding.btnLogin.setEnabled(false);
        binding.errorText.setVisibility(View.GONE);

        authRepository.login(email, password).observe(this, response -> {
            binding.loading.setVisibility(View.GONE);
            binding.btnLogin.setEnabled(true);

            if (response != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                binding.errorText.setVisibility(View.VISIBLE);
                binding.errorText.setText("Login failed. Please check your credentials.");
            }
        });
    }
}
