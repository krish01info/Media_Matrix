package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class Region {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    @SerializedName("parent_id")
    private Integer parentId;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public Integer getParentId() { return parentId; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSlug(String slug) { this.slug = slug; }
}
