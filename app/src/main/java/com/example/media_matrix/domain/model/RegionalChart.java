package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class RegionalChart {
    @SerializedName("id")
    private int id;

    @SerializedName("rank")
    private int rank;

    @SerializedName("title")
    private String title;

    @SerializedName("metric_label")
    private String metricLabel;

    @SerializedName("region_id")
    private int regionId;

    @SerializedName("region_name")
    private String regionName;

    @SerializedName("article_id")
    private int articleId;

    @SerializedName("chart_date")
    private String chartDate;

    public int getId() { return id; }
    public int getRank() { return rank; }
    public String getTitle() { return title; }
    public String getMetricLabel() { return metricLabel; }
    public int getRegionId() { return regionId; }
    public String getRegionName() { return regionName; }
    public int getArticleId() { return articleId; }
    public String getChartDate() { return chartDate; }

    public String getFormattedRank() {
        return String.format("%02d", rank);
    }

    public void setId(int id) { this.id = id; }
    public void setRank(int rank) { this.rank = rank; }
    public void setTitle(String title) { this.title = title; }
    public void setMetricLabel(String metricLabel) { this.metricLabel = metricLabel; }
}
