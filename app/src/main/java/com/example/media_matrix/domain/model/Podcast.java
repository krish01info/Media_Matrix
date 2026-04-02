package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class Podcast {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("audio_url")
    private String audioUrl;

    @SerializedName("thumbnail_url")
    private String thumbnailUrl;

    @SerializedName("duration_seconds")
    private int durationSeconds;

    @SerializedName("episode_number")
    private int episodeNumber;

    @SerializedName("published_at")
    private String publishedAt;

    @SerializedName("source_name")
    private String sourceName;

    @SerializedName("source")
    private Source source;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAudioUrl() { return audioUrl; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public int getDurationSeconds() { return durationSeconds; }
    public int getEpisodeNumber() { return episodeNumber; }
    public String getPublishedAt() { return publishedAt; }
    public String getSourceName() { return sourceName != null ? sourceName : (source != null ? source.getName() : ""); }

    public String getFormattedDuration() {
        int mins = durationSeconds / 60;
        int secs = durationSeconds % 60;
        return String.format("%d:%02d", mins, secs);
    }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }
}
