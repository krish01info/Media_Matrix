package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TrendingTopic {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("slug")
    private String slug;

    @SerializedName("engagement_score")
    private long engagementScore;

    @SerializedName("engagement_label")
    private String engagementLabel;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("articles")
    private List<Article> articles;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getSlug() { return slug; }
    public long getEngagementScore() { return engagementScore; }
    public String getEngagementLabel() { return engagementLabel; }
    public boolean isActive() { return isActive; }
    public List<Article> getArticles() { return articles; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setEngagementLabel(String engagementLabel) { this.engagementLabel = engagementLabel; }
    public void setArticles(List<Article> articles) { this.articles = articles; }
}
