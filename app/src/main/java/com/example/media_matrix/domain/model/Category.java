package com.example.media_matrix.domain.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    @SerializedName("description")
    private String description;

    @SerializedName("icon_url")
    private String iconUrl;

    @SerializedName("gradient_start")
    private String gradientStart;

    @SerializedName("gradient_end")
    private String gradientEnd;

    @SerializedName("display_order")
    private int displayOrder;

    @SerializedName("is_active")
    private boolean isActive;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getDescription() { return description; }
    public String getIconUrl() { return iconUrl; }
    public String getGradientStart() { return gradientStart; }
    public String getGradientEnd() { return gradientEnd; }
    public int getDisplayOrder() { return displayOrder; }
    public boolean isActive() { return isActive; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setDescription(String description) { this.description = description; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }
    public void setGradientStart(String gradientStart) { this.gradientStart = gradientStart; }
    public void setGradientEnd(String gradientEnd) { this.gradientEnd = gradientEnd; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
    public void setActive(boolean active) { isActive = active; }
}
