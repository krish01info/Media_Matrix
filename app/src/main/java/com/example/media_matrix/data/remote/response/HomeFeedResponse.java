package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Newspaper;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HomeFeedResponse {
    @SerializedName("featured")
    private List<Article> featured;

    @SerializedName("trending")
    private List<Article> trending;

    @SerializedName("topCharts")
    private List<Article> topCharts;

    @SerializedName("newspapers")
    private List<Newspaper> newspapers;

    public List<Article> getFeatured() { return featured; }
    public List<Article> getTrending() { return trending; }
    public List<Article> getTopCharts() { return topCharts; }
    public List<Newspaper> getNewspapers() { return newspapers; }
}
