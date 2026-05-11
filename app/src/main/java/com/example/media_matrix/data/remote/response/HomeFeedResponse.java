package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Newspaper;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Backend: GET /api/home/feed
 * Shape: { success, data: { featured, trending, top_charts, newspapers } }
 */
public class HomeFeedResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private HomeFeedData data;

    public boolean isSuccess() { return success; }
    public HomeFeedData getData() { return data; }

    // Convenience getters (delegates into nested data)
    public List<Article> getFeatured()  { return data != null ? data.featured  : null; }
    public List<Article> getTrending()  { return data != null ? data.trending  : null; }
    public List<Article> getTopCharts() { return data != null ? data.topCharts : null; }
    public List<Newspaper> getNewspapers() { return data != null ? data.newspapers : null; }

    /**
     * Factory method — build a HomeFeedResponse from in-memory lists.
     * Used by HomeRepository when assembling results from public APIs.
     */
    public static HomeFeedResponse fromLists(List<Article> featured, List<Article> trending,
                                              List<Article> topCharts, List<Newspaper> newspapers) {
        HomeFeedResponse r = new HomeFeedResponse();
        r.success = true;
        r.data = new HomeFeedData();
        r.data.featured  = featured;
        r.data.trending  = trending;
        r.data.topCharts = topCharts;
        r.data.newspapers = newspapers;
        return r;
    }

    public static class HomeFeedData {
        @SerializedName("featured")
        private List<Article> featured;

        @SerializedName("trending")
        private List<Article> trending;

        @SerializedName("top_charts")          // backend key is snake_case
        private List<Article> topCharts;

        @SerializedName("newspapers")
        private List<Newspaper> newspapers;
    }
}
