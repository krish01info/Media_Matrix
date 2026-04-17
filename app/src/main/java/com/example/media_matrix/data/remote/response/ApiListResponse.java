package com.example.media_matrix.data.remote.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Generic wrapper for all list endpoints.
 * Backend shape: { success: true, data: [...], total?, page?, limit? }
 */
public class ApiListResponse<T> {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<T> data;

    @SerializedName("total")
    private int total;

    @SerializedName("page")
    private int page;

    @SerializedName("limit")
    private int limit;

    public boolean isSuccess() { return success; }
    public List<T> getData()   { return data; }
    public int getTotal()      { return total; }
    public int getPage()       { return page; }
    public int getLimit()      { return limit; }
}
