package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.User;
import com.google.gson.annotations.SerializedName;

/**
 * Matches backend response shape:
 * { success: true, message: "...", data: { user: {...}, access_token: "...", refresh_token: "..." } }
 */
public class AuthResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private AuthData data;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public AuthData getData() { return data; }

    // Convenience helpers used by repositories
    public String getAccessToken() {
        return data != null ? data.accessToken : null;
    }
    public String getRefreshToken() {
        return data != null ? data.refreshToken : null;
    }
    public User getUser() {
        return data != null ? data.user : null;
    }

    public static class AuthData {
        @SerializedName("access_token")
        private String accessToken;

        @SerializedName("refresh_token")
        private String refreshToken;

        @SerializedName("user")
        private User user;

        public String getAccessToken() { return accessToken; }
        public String getRefreshToken() { return refreshToken; }
        public User getUser() { return user; }
    }
}
