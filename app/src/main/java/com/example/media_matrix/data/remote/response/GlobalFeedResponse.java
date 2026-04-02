package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.*;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GlobalFeedResponse {
    @SerializedName("highlights")
    private List<Article> highlights;

    @SerializedName("trendingTopics")
    private List<TrendingTopic> trendingTopics;

    @SerializedName("compareCoverage")
    private List<CompareCoverageResponse> compareCoverage;

    @SerializedName("independentVoices")
    private List<Reporter> independentVoices;

    @SerializedName("regionalPulse")
    private List<Article> regionalPulse;

    public List<Article> getHighlights() { return highlights; }
    public List<TrendingTopic> getTrendingTopics() { return trendingTopics; }
    public List<CompareCoverageResponse> getCompareCoverage() { return compareCoverage; }
    public List<Reporter> getIndependentVoices() { return independentVoices; }
    public List<Article> getRegionalPulse() { return regionalPulse; }
}
