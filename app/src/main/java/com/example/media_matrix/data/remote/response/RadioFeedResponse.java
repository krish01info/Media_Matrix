package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Podcast;
import com.example.media_matrix.domain.model.RadioStream;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Backend: GET /api/radio/feed
 * Shape: { success, data: { streams, podcasts, latest_coverage } }
 */
public class RadioFeedResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private RadioFeedData data;

    public boolean isSuccess() { return success; }
    public RadioFeedData getData() { return data; }

    public List<RadioStream> getStreams()       { return data != null ? data.streams       : null; }
    public List<Podcast>     getPodcasts()      { return data != null ? data.podcasts      : null; }
    public List<Article>     getLatestCoverage() { return data != null ? data.latestCoverage : null; }

    /**
     * Factory method — build a RadioFeedResponse from an in-memory list of streams.
     * Used by RadioRepository when assembling results from Radio Browser API.
     */
    public static RadioFeedResponse fromStreams(List<RadioStream> streams) {
        RadioFeedResponse r = new RadioFeedResponse();
        r.success = true;
        r.data = new RadioFeedData();
        r.data.streams = streams;
        r.data.podcasts = new java.util.ArrayList<>();
        r.data.latestCoverage = new java.util.ArrayList<>();
        return r;
    }

    public static class RadioFeedData {
        @SerializedName("streams")
        private List<RadioStream> streams;

        @SerializedName("podcasts")
        private List<Podcast> podcasts;

        @SerializedName("latest_coverage")   // snake_case from backend
        private List<Article> latestCoverage;
    }
}
