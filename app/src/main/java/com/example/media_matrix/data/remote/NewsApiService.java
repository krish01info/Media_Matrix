package com.example.media_matrix.data.remote;

import com.example.media_matrix.data.remote.response.NewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface for NewsAPI.org (https://newsapi.org/v2/)
 */
public interface NewsApiService {

    @GET("top-headlines")
    Call<NewsApiResponse> getTopHeadlines(
            @Query("country") String country,
            @Query("category") String category,
            @Query("pageSize") int pageSize,
            @Query("page") int page,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<NewsApiResponse> searchEverything(
            @Query("q") String query,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("domains") String domains,
            @Query("pageSize") int pageSize,
            @Query("page") int page,
            @Query("apiKey") String apiKey
    );
}
