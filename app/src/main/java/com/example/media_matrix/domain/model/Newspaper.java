package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class Newspaper {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("cover_image_url")
    private String coverImageUrl;

    @SerializedName("pdf_url")
    private String pdfUrl;

    @SerializedName("edition_date")
    private String editionDate;

    @SerializedName("source_id")
    private int sourceId;

    @SerializedName("source_name")
    private String sourceName;

    @SerializedName("source_short_name")
    private String sourceShortName;

    @SerializedName("source")
    private Source source;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public String getPdfUrl() { return pdfUrl; }
    public String getEditionDate() { return editionDate; }
    public int getSourceId() { return sourceId; }
    public String getSourceName() { return sourceName != null ? sourceName : (source != null ? source.getName() : ""); }
    public String getSourceShortName() { return sourceShortName != null ? sourceShortName : (source != null ? source.getShortName() : getSourceName()); }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }
}
