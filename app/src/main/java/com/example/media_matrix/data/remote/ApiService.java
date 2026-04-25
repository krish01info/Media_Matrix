package com.example.media_matrix.data.remote;

import com.example.media_matrix.data.remote.response.*;
import com.example.media_matrix.domain.model.*;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // ===== Auth =====
    @POST("auth/login")
    Call<AuthResponse> login(@Body Map<String, String> body);

    @POST("auth/register")
    Call<AuthResponse> register(@Body Map<String, String> body);

    @POST("auth/refresh")
    Call<AuthResponse> refreshToken(@Body Map<String, String> body);

    @POST("auth/logout")
    Call<Void> logout(@Body Map<String, String> body);

    @POST("auth/google")
    Call<AuthResponse> googleAuth(@Body Map<String, String> body);

    // ===== Home Screen =====
    @GET("home/feed")
    Call<HomeFeedResponse> getHomeFeed();

    @GET("home/featured")
    Call<ApiListResponse<Article>> getFeaturedArticles();

    @GET("home/trending")
    Call<ApiListResponse<Article>> getTrendingArticles();

    @GET("home/top-charts")
    Call<ApiListResponse<Article>> getTopCharts();

    @GET("home/newspapers")
    Call<ApiListResponse<Newspaper>> getNewspapers();

    // ===== Today Screen =====
    @GET("today/feed")
    Call<TodayFeedResponse> getTodayFeed();

    @GET("today/morning-brief")
    Call<ApiListResponse<Article>> getMorningBrief();

    @GET("today/developing")
    Call<ApiListResponse<Article>> getDeveloping();

    @GET("today/trending")
    Call<ApiListResponse<Article>> getTodayTrending();

    @GET("today/regional-charts")
    Call<ApiListResponse<RegionalChart>> getRegionalCharts(@Query("region") String region);

    // ===== Global Affairs =====
    @GET("global/feed")
    Call<GlobalFeedResponse> getGlobalFeed();

    @GET("global/highlights")
    Call<ApiListResponse<Article>> getGlobalHighlights();

    @GET("global/trending-topics")
    Call<ApiListResponse<TrendingTopic>> getTrendingTopics();

    @GET("global/compare-coverage")
    Call<ApiListResponse<CompareCoverageResponse>> getCompareCoverage();

    @GET("global/independent-voices")
    Call<ApiListResponse<Reporter>> getIndependentVoices();

    @GET("global/regional-pulse")
    Call<ApiListResponse<Article>> getRegionalPulse(@Query("region") String region);

    // ===== Radio =====
    @GET("radio/feed")
    Call<RadioFeedResponse> getRadioFeed();

    @GET("radio/streams")
    Call<ApiListResponse<RadioStream>> getRadioStreams();

    @GET("radio/streams/{id}")
    Call<ApiResponse<RadioStream>> getRadioStream(@Path("id") int id);

    @GET("radio/podcasts")
    Call<ApiListResponse<Podcast>> getPodcasts();

    // ===== Search =====
    @GET("search")
    Call<SearchResponse> search(@QueryMap Map<String, String> params);

    @GET("search/categories")
    Call<ApiListResponse<Category>> getCategories();

    @GET("search/reporters")
    Call<ApiListResponse<Reporter>> getReporters(@QueryMap Map<String, String> params);

    // ===== Articles =====
    @GET("articles/{uuid}")
    Call<ApiResponse<Article>> getArticle(@Path("uuid") String uuid);

    @GET("articles/by-category/{slug}")
    Call<ApiListResponse<Article>> getArticlesByCategory(@Path("slug") String slug);

    @GET("articles/by-source/{slug}")
    Call<ApiListResponse<Article>> getArticlesBySource(@Path("slug") String slug);

    @GET("articles/by-reporter/{slug}")
    Call<ApiListResponse<Article>> getArticlesByReporter(@Path("slug") String slug);

    // ===== User =====
    @GET("user/profile")
    Call<ApiResponse<User>> getProfile();

    @PUT("user/profile")
    Call<ApiResponse<User>> updateProfile(@Body Map<String, String> body);

    @GET("user/bookmarks")
    Call<ApiListResponse<Article>> getBookmarks();

    @POST("user/bookmarks/{uuid}")
    Call<Void> bookmarkArticle(@Path("uuid") String uuid);

    @DELETE("user/bookmarks/{uuid}")
    Call<Void> removeBookmark(@Path("uuid") String uuid);

    @GET("user/history")
    Call<ApiListResponse<Article>> getHistory();
}
