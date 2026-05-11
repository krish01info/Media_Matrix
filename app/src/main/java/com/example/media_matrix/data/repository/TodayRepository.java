package com.example.media_matrix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.BuildConfig;
import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.NewsApiMapper;
import com.example.media_matrix.data.remote.NewsApiService;
import com.example.media_matrix.data.remote.response.NewsApiResponse;
import com.example.media_matrix.data.remote.response.TodayFeedResponse;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.RegionalChart;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fetches Today feed data from NewsAPI.org directly — no backend required.
 */
public class TodayRepository {

    private static final String TAG = "TodayRepository";
    private static final String API_KEY = BuildConfig.NEWS_API_KEY;
    private final NewsApiService newsApiService;

    public TodayRepository() {
        newsApiService = ApiClient.getInstance().getNewsApiService();
    }

    public LiveData<TodayFeedResponse> getTodayFeed() {
        MutableLiveData<TodayFeedResponse> data = new MutableLiveData<>();
        Log.d(TAG, "getTodayFeed: Calling NewsAPI...");

        final List<Article> morningBrief = new ArrayList<>();
        final List<Article> developing = new ArrayList<>();
        final List<Article> trending = new ArrayList<>();
        final AtomicInteger pending = new AtomicInteger(2);

        // Call 1: General headlines for India
        newsApiService.getTopHeadlines("in", "general", 15, 1, API_KEY)
                .enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                            List<Article> all = NewsApiMapper.mapArticles(
                                    response.body().getArticles(), false, false, true, false);
                            int briefCount = Math.min(5, all.size());
                            for (int i = 0; i < briefCount; i++) {
                                morningBrief.add(all.get(i));
                            }
                            for (int i = briefCount; i < all.size(); i++) {
                                Article a = all.get(i);
                                a.setMorningBrief(false);
                                trending.add(a);
                            }
                        }
                        if (pending.decrementAndGet() == 0) {
                            postResult(data, morningBrief, developing, trending);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        if (pending.decrementAndGet() == 0) {
                            postResult(data, morningBrief, developing, trending);
                        }
                    }
                });

        // Call 2: Breaking news
        newsApiService.searchEverything("breaking news", "en", "publishedAt", null, 8, 1, API_KEY)
                .enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                            developing.addAll(NewsApiMapper.mapArticles(
                                    response.body().getArticles(), false, true, false, true));
                        }
                        if (pending.decrementAndGet() == 0) {
                            postResult(data, morningBrief, developing, trending);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        if (pending.decrementAndGet() == 0) {
                            postResult(data, morningBrief, developing, trending);
                        }
                    }
                });

        return data;
    }

    private void postResult(MutableLiveData<TodayFeedResponse> data,
                            List<Article> morningBrief,
                            List<Article> developing,
                            List<Article> trending) {
        
        List<RegionalChart> charts = new ArrayList<>();
        String[][] chartData = {
            {"Delhi NCR", "Air Quality Management Plan 2024", "12.5K Reach"},
            {"Mumbai", "Coastal Road Project Updates", "18.2K Reach"},
            {"Bangalore", "New Tech Hub in North Bengaluru", "15.7K Reach"},
            {"Hyderabad", "Pharma City Expansion", "9.8K Reach"},
            {"Chennai", "Automobile Export Surge", "11.2K Reach"}
        };

        for (int i = 0; i < chartData.length; i++) {
            RegionalChart c = new RegionalChart();
            c.setId(i + 1);
            c.setRank(i + 1);
            c.setTitle(chartData[i][0] + " • " + chartData[i][1]);
            c.setMetricLabel(chartData[i][2]);
            charts.add(c);
        }

        TodayFeedResponse response = TodayFeedResponse.fromLists(morningBrief, developing, trending, charts);
        data.postValue(response);
    }
}
