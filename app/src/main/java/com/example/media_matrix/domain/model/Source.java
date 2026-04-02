package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class Source {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("short_name")
    private String shortName;

    @SerializedName("slug")
    private String slug;

    @SerializedName("logo_url")
    private String logoUrl;

    @SerializedName("website_url")
    private String websiteUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("is_verified")
    private boolean isVerified;

    @SerializedName("trust_score")
    private double trustScore;

    @SerializedName("has_radio")
    private boolean hasRadio;

    @SerializedName("has_newspaper")
    private boolean hasNewspaper;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getShortName() { return shortName != null ? shortName : name; }
    public String getSlug() { return slug; }
    public String getLogoUrl() { return logoUrl; }
    public String getWebsiteUrl() { return websiteUrl; }
    public String getDescription() { return description; }
    public boolean isVerified() { return isVerified; }
    public double getTrustScore() { return trustScore; }
    public boolean hasRadio() { return hasRadio; }
    public boolean hasNewspaper() { return hasNewspaper; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setShortName(String shortName) { this.shortName = shortName; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public void setTrustScore(double trustScore) { this.trustScore = trustScore; }
}
