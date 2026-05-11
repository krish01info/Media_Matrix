package com.example.media_matrix.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Single article object returned by NewsAPI.org
 * Shape: { source: { id, name }, author, title, description,
 *          url, urlToImage, publishedAt, content }
 */
public class NewsApiArticle {

    @SerializedName("source")
    private NewsSource source;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("publishedAt")
    private String publishedAt;

    @SerializedName("content")
    private String content;

    // Getters
    public NewsSource getSource() { return source; }
    public String getAuthor() { return author; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUrl() { return url; }
    public String getUrlToImage() { return urlToImage; }
    public String getPublishedAt() { return publishedAt; }
    public String getContent() { return content; }

    public String getSourceName() {
        return source != null ? source.getName() : "Unknown";
    }

    public static class NewsSource {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        public String getId() { return id; }
        public String getName() { return name != null ? name : "Unknown"; }
    }
}
