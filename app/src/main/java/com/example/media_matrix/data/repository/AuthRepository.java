package com.example.media_matrix.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.data.local.PreferenceManager;
import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.ApiService;
import com.example.media_matrix.data.remote.response.AuthResponse;
import com.example.media_matrix.domain.model.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final ApiService apiService;
    private PreferenceManager preferenceManager;

    public AuthRepository() {
        apiService = ApiClient.getInstance().getApiService();
    }

    public void setPreferenceManager(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }

    public LiveData<AuthResponse> login(String email, String password) {
        MutableLiveData<AuthResponse> data = new MutableLiveData<>();
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        apiService.login(body).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse auth = response.body();
                    if (preferenceManager != null) {
                        preferenceManager.saveTokens(auth.getAccessToken(), auth.getRefreshToken());
                        preferenceManager.saveUser(auth.getUser());
                    }
                    data.setValue(auth);
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<AuthResponse> register(String username, String email, String password) {
        MutableLiveData<AuthResponse> data = new MutableLiveData<>();
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("email", email);
        body.put("password", password);

        apiService.register(body).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse auth = response.body();
                    if (preferenceManager != null) {
                        preferenceManager.saveTokens(auth.getAccessToken(), auth.getRefreshToken());
                        preferenceManager.saveUser(auth.getUser());
                    }
                    data.setValue(auth);
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public void logout() {
        if (preferenceManager != null) {
            String refreshToken = preferenceManager.getRefreshToken();
            if (refreshToken != null) {
                Map<String, String> body = new HashMap<>();
                body.put("refresh_token", refreshToken); // backend expects snake_case
                apiService.logout(body).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {}
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {}
                });
            }
            preferenceManager.clearSession();
        }
    }
}
