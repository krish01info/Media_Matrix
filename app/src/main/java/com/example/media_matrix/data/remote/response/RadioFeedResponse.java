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

    public static class RadioFeedData {
        @SerializedName("streams")
        private List<RadioStream> streams;

        @SerializedName("podcasts")
        private List<Podcast> podcasts;

        @SerializedName("latest_coverage")   // snake_case from backend
        private List<Article> latestCoverage;
    }
}
