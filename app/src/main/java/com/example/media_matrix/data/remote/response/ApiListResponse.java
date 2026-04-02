package com.example.media_matrix.data.remote.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiListResponse<T> {
    @SerializedName("data")
    private List<T> data;

    @SerializedName("total")
    private int total;

    @SerializedName("page")
    private int page;

    @SerializedName("limit")
    private int limit;

    public List<T> getData() { return data; }
    public int getTotal() { return total; }
    public int getPage() { return page; }
    public int getLimit() { return limit; }
}
