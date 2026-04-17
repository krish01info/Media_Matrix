package com.example.media_matrix.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Generic wrapper for single-object endpoints.
 * Backend shape: { success: true, message: "...", data: { ... } }
 *
 * Used for: getArticle, getProfile, updateProfile, getRadioStream
 */
public class ApiResponse<T> {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData()         { return data; }
}
