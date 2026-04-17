package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.*;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Backend: GET /api/global/feed
 * Shape: { success, data: { highlights, map_insights, trending_topics, compare_coverage, independent_voices } }
 */
public class GlobalFeedResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private GlobalFeedData data;

    public boolean isSuccess() { return success; }
    public GlobalFeedData getData() { return data; }

    public List<Article> getHighlights()       { return data != null ? data.highlights       : null; }
    public List<TrendingTopic> getTrendingTopics() { return data != null ? data.trendingTopics : null; }
    public List<CompareCoverageResponse> getCompareCoverage() { return data != null ? data.compareCoverage : null; }
    public List<Reporter> getIndependentVoices() { return data != null ? data.independentVoices : null; }

    public static class GlobalFeedData {
        @SerializedName("highlights")
        private List<Article> highlights;

        @SerializedName("map_insights")         // backend key
        private List<Object> mapInsights;       // WorldMapInsight (placeholder)

        @SerializedName("trending_topics")      // snake_case from backend
        private List<TrendingTopic> trendingTopics;

        @SerializedName("compare_coverage")     // snake_case from backend
        private List<CompareCoverageResponse> compareCoverage;

        @SerializedName("independent_voices")   // snake_case from backend
        private List<Reporter> independentVoices;
    }
}
