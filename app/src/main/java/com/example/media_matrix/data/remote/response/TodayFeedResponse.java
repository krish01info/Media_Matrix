package com.example.media_matrix.data.remote.response;

import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.RegionalChart;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TodayFeedResponse {
    @SerializedName("morningBrief")
    private List<Article> morningBrief;

    @SerializedName("developing")
    private List<Article> developing;

    @SerializedName("trending")
    private List<Article> trending;

    @SerializedName("regionalCharts")
    private List<RegionalChart> regionalCharts;

    public List<Article> getMorningBrief() { return morningBrief; }
    public List<Article> getDeveloping() { return developing; }
    public List<Article> getTrending() { return trending; }
    public List<RegionalChart> getRegionalCharts() { return regionalCharts; }
}
