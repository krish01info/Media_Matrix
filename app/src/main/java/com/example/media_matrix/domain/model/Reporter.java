package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class Reporter {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    @SerializedName("title")
    private String title;

    @SerializedName("bio")
    private String bio;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("truth_score")
    private double truthScore;

    @SerializedName("is_verified")
    private boolean isVerified;

    @SerializedName("is_independent")
    private boolean isIndependent;

    @SerializedName("total_articles")
    private int totalArticles;

    @SerializedName("source_name")
    private String sourceName;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getTitle() { return title; }
    public String getBio() { return bio; }
    public String getAvatarUrl() { return avatarUrl; }
    public double getTruthScore() { return truthScore; }
    public boolean isVerified() { return isVerified; }
    public boolean isIndependent() { return isIndependent; }
    public int getTotalArticles() { return totalArticles; }
    public String getSourceName() { return sourceName; }

    public String getFormattedTruthScore() {
        return String.format("%.0f%% TRUTH SCORE", truthScore);
    }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setTitle(String title) { this.title = title; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setTruthScore(double truthScore) { this.truthScore = truthScore; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public void setIndependent(boolean independent) { isIndependent = independent; }
}
