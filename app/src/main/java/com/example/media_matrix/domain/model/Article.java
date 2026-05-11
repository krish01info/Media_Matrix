package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("id")
    private int id;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("title")
    private String title;

    @SerializedName("subtitle")
    private String subtitle;

    @SerializedName("summary")
    private String summary;

    @SerializedName("content")
    private String content;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("thumbnail_url")
    private String thumbnailUrl;

    @SerializedName("truth_score")
    private double truthScore;

    @SerializedName("is_verified")
    private boolean isVerified;

    @SerializedName("is_featured")
    private boolean isFeatured;

    @SerializedName("is_breaking")
    private boolean isBreaking;

    @SerializedName("is_developing")
    private boolean isDeveloping;

    @SerializedName("is_live")
    private boolean isLive;

    @SerializedName("is_morning_brief")
    private boolean isMorningBrief;

    @SerializedName("interaction_count")
    private long interactionCount;

    @SerializedName("view_count")
    private long viewCount;

    @SerializedName("share_count")
    private long shareCount;

    @SerializedName("published_at")
    private String publishedAt;

    @SerializedName("created_at")
    private String createdAt;

    // Nested objects
    @SerializedName("category")
    private Category category;

    @SerializedName("source")
    private Source source;

    @SerializedName("reporter")
    private Reporter reporter;

    // Category fields (for flat responses)
    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("source_id")
    private int sourceId;

    @SerializedName("source_name")
    private String sourceName;

    @SerializedName("source_logo_url")
    private String sourceLogoUrl;

    @SerializedName("reporter_name")
    private String reporterName;

    @SerializedName("reporter_avatar_url")
    private String reporterAvatarUrl;

    // Getters
    public int getId() { return id; }
    public String getUuid() { return uuid; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public double getTruthScore() { return truthScore; }
    public boolean isVerified() { return isVerified; }
    public boolean isFeatured() { return isFeatured; }
    public boolean isBreaking() { return isBreaking; }
    public boolean isDeveloping() { return isDeveloping; }
    public boolean isLive() { return isLive; }
    public boolean isMorningBrief() { return isMorningBrief; }
    public long getInteractionCount() { return interactionCount; }
    public long getViewCount() { return viewCount; }
    public long getShareCount() { return shareCount; }
    public String getPublishedAt() { return publishedAt; }
    public String getCreatedAt() { return createdAt; }
    public Category getCategory() { return category; }
    public Source getSource() { return source; }
    public Reporter getReporter() { return reporter; }
    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName != null ? categoryName : (category != null ? category.getName() : ""); }
    public int getSourceId() { return sourceId; }
    public String getSourceName() { return sourceName != null ? sourceName : (source != null ? source.getName() : ""); }
    public String getSourceLogoUrl() { return sourceLogoUrl != null ? sourceLogoUrl : (source != null ? source.getLogoUrl() : null); }
    public String getReporterName() { return reporterName != null ? reporterName : (reporter != null ? reporter.getName() : ""); }
    public String getReporterAvatarUrl() { return reporterAvatarUrl != null ? reporterAvatarUrl : (reporter != null ? reporter.getAvatarUrl() : null); }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUuid(String uuid) { this.uuid = uuid; }
    public void setTitle(String title) { this.title = title; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setContent(String content) { this.content = content; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public void setTruthScore(double truthScore) { this.truthScore = truthScore; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public void setFeatured(boolean featured) { isFeatured = featured; }
    public void setBreaking(boolean breaking) { isBreaking = breaking; }
    public void setDeveloping(boolean developing) { isDeveloping = developing; }
    public void setLive(boolean live) { isLive = live; }
    public void setMorningBrief(boolean morningBrief) { isMorningBrief = morningBrief; }
    public void setInteractionCount(long interactionCount) { this.interactionCount = interactionCount; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }
    public void setCategory(Category category) { this.category = category; }
    public void setSource(Source source) { this.source = source; }
    public void setReporter(Reporter reporter) { this.reporter = reporter; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public String getFormattedInteractions() {
        if (interactionCount >= 1_000_000) {
            return String.format("%.1fM", interactionCount / 1_000_000.0);
        } else if (interactionCount >= 1_000) {
            return String.format("%.1fK", interactionCount / 1_000.0);
        }
        return String.valueOf(interactionCount);
    }

    public String getTimeAgo() {
        if (publishedAt == null) return "";
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.US);
            java.util.Date date = sdf.parse(publishedAt.replace(".000Z", "").replace("Z", ""));
            long diff = System.currentTimeMillis() - date.getTime();
            long minutes = diff / (60 * 1000);
            if (minutes < 60) return minutes + "m ago";
            long hours = minutes / 60;
            if (hours < 24) return hours + "h ago";
            long days = hours / 24;
            return days + "d ago";
        } catch (Exception e) {
            return "";
        }
    }
}
