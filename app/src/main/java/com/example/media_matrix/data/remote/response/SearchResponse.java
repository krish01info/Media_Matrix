package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.Article;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchResponse {
    @SerializedName("data")
    private List<Article> data;

    @SerializedName("total")
    private int total;

    @SerializedName("page")
    private int page;

    @SerializedName("query")
    private String query;

    public List<Article> getData() { return data; }
    public int getTotal() { return total; }
    public int getPage() { return page; }
    public String getQuery() { return query; }
}
