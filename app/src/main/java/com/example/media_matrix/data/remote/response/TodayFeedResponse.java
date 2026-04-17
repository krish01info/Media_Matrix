package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.RegionalChart;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Backend: GET /api/today/feed
 * Shape: { success, data: { morning_brief, developing, trending, regional_charts } }
 */
public class TodayFeedResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private TodayFeedData data;

    public boolean isSuccess() { return success; }
    public TodayFeedData getData() { return data; }

    public List<Article> getMorningBrief()     { return data != null ? data.morningBrief  : null; }
    public List<Article> getDeveloping()       { return data != null ? data.developing    : null; }
    public List<Article> getTrending()         { return data != null ? data.trending      : null; }
    public List<RegionalChart> getRegionalCharts() { return data != null ? data.regionalCharts : null; }

    public static class TodayFeedData {
        @SerializedName("morning_brief")       // snake_case from backend
        private List<Article> morningBrief;

        @SerializedName("developing")
        private List<Article> developing;

        @SerializedName("trending")
        private List<Article> trending;

        @SerializedName("regional_charts")    // snake_case from backend
        private List<RegionalChart> regionalCharts;
    }
}
