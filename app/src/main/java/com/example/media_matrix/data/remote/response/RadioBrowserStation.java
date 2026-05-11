package com.example.media_matrix.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Single radio station from Radio Browser API (https://de1.api.radio-browser.info)
 * Maps to our domain model: RadioStream
 */
public class RadioBrowserStation {

    @SerializedName("stationuuid")
    private String stationUuid;

    @SerializedName("name")
    private String name;

    @SerializedName("url_resolved")
    private String urlResolved;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("favicon")
    private String favicon;

    @SerializedName("country")
    private String country;

    @SerializedName("language")
    private String language;

    @SerializedName("tags")
    private String tags;

    @SerializedName("votes")
    private int votes;

    @SerializedName("clickcount")
    private int clickCount;

    @SerializedName("clicktrend")
    private int clickTrend;

    @SerializedName("bitrate")
    private int bitrate;

    @SerializedName("codec")
    private String codec;

    @SerializedName("lastcheckok")
    private int lastCheckOk; // 1 = online, 0 = offline

    // Getters
    public String getStationUuid() { return stationUuid; }
    public String getName() { return name; }
    public String getUrlResolved() { return urlResolved; }
    public String getHomepage() { return homepage; }
    public String getFavicon() { return favicon; }
    public String getCountry() { return country; }
    public String getLanguage() { return language; }
    public String getTags() { return tags; }
    public int getVotes() { return votes; }
    public int getClickCount() { return clickCount; }
    public int getBitrate() { return bitrate; }
    public String getCodec() { return codec; }
    public boolean isOnline() { return lastCheckOk == 1; }
}
