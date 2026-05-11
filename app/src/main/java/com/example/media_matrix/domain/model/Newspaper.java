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

    @SerializedName("source_name")
    private String sourceName;

    @SerializedName("source_short_name")
    private String sourceShortName;

    @SerializedName("category")
    private String category;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public String getPdfUrl() { return pdfUrl; }
    public String getEditionDate() { return editionDate; }
    public String getSourceName() { return sourceName != null ? sourceName : ""; }
    public String getSourceShortName() { return sourceShortName != null ? sourceShortName : getSourceName(); }
    public String getCategory() { return category != null ? category : ""; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }
    public void setEditionDate(String editionDate) { this.editionDate = editionDate; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }
    public void setSourceShortName(String sourceShortName) { this.sourceShortName = sourceShortName; }
    public void setCategory(String category) { this.category = category; }
}
