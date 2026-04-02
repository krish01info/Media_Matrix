package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class RadioStream {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("stream_url")
    private String streamUrl;

    @SerializedName("thumbnail_url")
    private String thumbnailUrl;

    @SerializedName("is_live")
    private boolean isLive;

    @SerializedName("is_high_quality")
    private boolean isHighQuality;

    @SerializedName("listener_count")
    private int listenerCount;

    @SerializedName("display_order")
    private int displayOrder;

    @SerializedName("source_id")
    private int sourceId;

    @SerializedName("source_name")
    private String sourceName;

    @SerializedName("source")
    private Source source;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStreamUrl() { return streamUrl; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public boolean isLive() { return isLive; }
    public boolean isHighQuality() { return isHighQuality; }
    public int getListenerCount() { return listenerCount; }
    public int getDisplayOrder() { return displayOrder; }
    public int getSourceId() { return sourceId; }
    public String getSourceName() { return sourceName != null ? sourceName : (source != null ? source.getName() : ""); }

    public String getFormattedListeners() {
        if (listenerCount >= 1000) {
            return String.format("%.1fK listeners", listenerCount / 1000.0);
        }
        return listenerCount + " listeners";
    }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setStreamUrl(String streamUrl) { this.streamUrl = streamUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public void setLive(boolean live) { isLive = live; }
    public void setListenerCount(int listenerCount) { this.listenerCount = listenerCount; }
}
