package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("is_verified")
    private boolean isVerified;

    public int getId() { return id; }
    public String getUuid() { return uuid; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isVerified() { return isVerified; }

    public void setId(int id) { this.id = id; }
    public void setUuid(String uuid) { this.uuid = uuid; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setVerified(boolean verified) { isVerified = verified; }
}
