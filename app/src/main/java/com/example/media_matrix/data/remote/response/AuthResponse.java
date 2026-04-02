package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.User;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("user")
    private User user;

    @SerializedName("message")
    private String message;

    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public User getUser() { return user; }
    public String getMessage() { return message; }
}
