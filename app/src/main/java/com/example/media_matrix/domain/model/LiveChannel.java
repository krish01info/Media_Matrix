package com.example.media_matrix.domain.model;

public class LiveChannel {
    private String id;
    private String name;
    private String country;
    private String language;
    private String category;
    private String youtubeVideoId; // Can be Video ID or Channel ID
    private String logoUrl;
    private String description;
    private boolean isLive;
    private boolean isChannelId;

    public LiveChannel() {}

    public LiveChannel(String id, String name, String country, String language,
                       String category, String youtubeVideoId, String logoUrl,
                       String description, boolean isChannelId) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.language = language;
        this.category = category;
        this.youtubeVideoId = youtubeVideoId;
        this.logoUrl = logoUrl;
        this.description = description;
        this.isLive = true;
        this.isChannelId = isChannelId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getLanguage() { return language; }
    public String getCategory() { return category; }
    public String getYoutubeVideoId() { return youtubeVideoId; }
    public String getLogoUrl() { return logoUrl; }
    public String getDescription() { return description; }
    public boolean isLive() { return isLive; }
    public boolean isChannelId() { return isChannelId; }

    public String getEmbedUrl() {
        if (isChannelId) {
            return "https://www.youtube.com/embed/live_stream?channel=" + youtubeVideoId
                    + "&autoplay=1&playsinline=1&rel=0&modestbranding=1";
        } else {
            return "https://www.youtube.com/embed/" + youtubeVideoId
                    + "?autoplay=1&playsinline=1&rel=0&modestbranding=1";
        }
    }
    
    // Setters omitted for brevity...
    public void setChannelId(boolean channelId) { isChannelId = channelId; }
}
