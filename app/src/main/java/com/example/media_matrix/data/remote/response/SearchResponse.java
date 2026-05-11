package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.Article;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Backend: GET /api/search?q=...
 * Shape: { success, data: { articles: [...], page, limit } }
 */
public class SearchResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private SearchData data;

    public boolean isSuccess() { return success; }
    public SearchData getData() { return data; }

    // Convenience helpers
    public List<Article> getArticles() { return data != null ? data.articles : null; }
    public int getPage()  { return data != null ? data.page  : 0; }
    public int getLimit() { return data != null ? data.limit : 0; }

    /**
     * Factory method — build a SearchResponse from an in-memory list of articles.
     * Used by SearchRepository when assembling results from NewsAPI.
     */
    public static SearchResponse fromArticles(List<Article> articles) {
        SearchResponse r = new SearchResponse();
        r.success = true;
        r.data = new SearchData();
        r.data.articles = articles;
        r.data.page = 1;
        r.data.limit = articles != null ? articles.size() : 0;
        return r;
    }

    public static class SearchData {
        @SerializedName("articles")
        private List<Article> articles;

        @SerializedName("page")
        private int page;

        @SerializedName("limit")
        private int limit;

        public List<Article> getArticles() { return articles; }
        public int getPage()  { return page; }
        public int getLimit() { return limit; }
    }
}
