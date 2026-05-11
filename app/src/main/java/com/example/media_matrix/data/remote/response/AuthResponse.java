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

    // Set by the repository when the call fails — not from JSON
    private String errorMessage;

    public boolean isSuccess() { return success && errorMessage == null; }
    public String getMessage() { return message; }
    public AuthData getData() { return data; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    /**
     * Factory method to build a successful AuthResponse without a server.
     * Used by the local-only AuthRepository.
     */
    public static AuthResponse localSuccess(User user) {
        AuthResponse r = new AuthResponse();
        r.success = true;
        r.message = "OK";
        r.data = new AuthData();
        r.data.user = user;
        r.data.accessToken = "local_token";
        r.data.refreshToken = "local_refresh";
        return r;
    }

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
