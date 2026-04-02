package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Podcast;
import com.example.media_matrix.domain.model.RadioStream;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RadioFeedResponse {
    @SerializedName("streams")
    private List<RadioStream> streams;

    @SerializedName("podcasts")
    private List<Podcast> podcasts;

    @SerializedName("latestCoverage")
    private List<Article> latestCoverage;

    public List<RadioStream> getStreams() { return streams; }
    public List<Podcast> getPodcasts() { return podcasts; }
    public List<Article> getLatestCoverage() { return latestCoverage; }
}
