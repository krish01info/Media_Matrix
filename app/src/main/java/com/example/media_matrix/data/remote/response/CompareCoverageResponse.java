package com.example.media_matrix.data.remote.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CompareCoverageResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("topic_title")
    private String topicTitle;

    @SerializedName("items")
    private List<CoverageItem> items;

    public int getId() { return id; }
    public String getTopicTitle() { return topicTitle; }
    public List<CoverageItem> getItems() { return items; }

    public static class CoverageItem {
        @SerializedName("id")
        private int id;

        @SerializedName("headline")
        private String headline;

        @SerializedName("stance_label")
        private String stanceLabel;

        @SerializedName("image_url")
        private String imageUrl;

        @SerializedName("source_name")
        private String sourceName;

        @SerializedName("source_logo_url")
        private String sourceLogoUrl;

        public int getId() { return id; }
        public String getHeadline() { return headline; }
        public String getStanceLabel() { return stanceLabel; }
        public String getImageUrl() { return imageUrl; }
        public String getSourceName() { return sourceName; }
        public String getSourceLogoUrl() { return sourceLogoUrl; }
    }
}
