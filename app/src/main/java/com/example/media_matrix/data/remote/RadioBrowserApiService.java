package com.example.media_matrix.data.remote;

import com.example.media_matrix.data.remote.response.RadioBrowserStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface for Radio Browser API (https://de1.api.radio-browser.info/json/)
 * Completely free, no API key required.
 */
public interface RadioBrowserApiService {

    // Fetch news/talk radio stations ordered by votes (popularity)
    @GET("stations/bytag/news")
    Call<List<RadioBrowserStation>> getNewStationsByTag(
            @Query("limit") int limit,
            @Query("order") String orderBy,
            @Query("reverse") boolean reverse,
            @Query("hidebroken") boolean hideBroken
    );

    // Search stations by name (used for radio search)
    @GET("stations/byname/news")
    Call<List<RadioBrowserStation>> searchStationsByName(
            @Query("limit") int limit,
            @Query("order") String orderBy,
            @Query("reverse") boolean reverse
    );
}
