package com.example.media_matrix.data.remote;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final ApiClient apiClient;

    public AuthInterceptor(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();

        // Add auth token if available
        if (apiClient.getPreferenceManager() != null) {
            String token = apiClient.getPreferenceManager().getAccessToken();
            if (token != null && !token.isEmpty()) {
                builder.header("Authorization", "Bearer " + token);
            }
        }

        builder.header("Content-Type", "application/json");
        builder.header("Accept", "application/json");

        return chain.proceed(builder.build());
    }
}
