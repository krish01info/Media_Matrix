package com.example.media_matrix.data.remote.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Top-level response from NewsAPI.org
 * Shape: { "status": "ok", "totalResults": 123, "articles": [...] }
 */
public class NewsApiResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<NewsApiArticle> articles;

    public boolean isOk() {
        return "ok".equalsIgnoreCase(status);
    }

    public String getStatus() { return status; }
    public int getTotalResults() { return totalResults; }
    public List<NewsApiArticle> getArticles() { return articles; }
}
