package com.example.media_matrix.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.media_matrix.BuildConfig;
import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.NewsApiMapper;
import com.example.media_matrix.data.remote.NewsApiService;
import com.example.media_matrix.data.remote.response.NewsApiResponse;
import com.example.media_matrix.data.remote.response.SearchResponse;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Category;
import com.example.media_matrix.domain.model.Reporter;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    private static final String TAG = "SearchRepository";
    private static final String API_KEY = BuildConfig.NEWS_API_KEY;
    private final NewsApiService newsApiService;

    public SearchRepository() {
        newsApiService = ApiClient.getInstance().getNewsApiService();
    }

    public LiveData<SearchResponse> search(String query, String scope, boolean verifiedOnly) {
        MutableLiveData<SearchResponse> data = new MutableLiveData<>();
        if (query == null || query.trim().isEmpty()) {
            data.setValue(SearchResponse.fromArticles(new ArrayList<>()));
            return data;
        }
        newsApiService.searchEverything(query, "en", "relevancy", null, 20, 1, API_KEY)
                .enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                            List<Article> articles = NewsApiMapper.mapArticles(response.body().getArticles());
                            data.setValue(SearchResponse.fromArticles(articles));
                        } else {
                            data.setValue(SearchResponse.fromArticles(new ArrayList<>()));
                        }
                    }
                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<List<Category>> getCategories() {
        MutableLiveData<List<Category>> data = new MutableLiveData<>();
        data.setValue(buildCategories());
        return data;
    }

    public LiveData<List<Reporter>> getReporters(boolean verifiedOnly) {
        MutableLiveData<List<Reporter>> data = new MutableLiveData<>();
        List<Reporter> list = new ArrayList<>();
        
        String[][] reportersData = {
            {"Sarah Jenkins", "Global Economics", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400&q=80", "9.8"},
            {"David Chen", "Tech & Future", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&q=80", "9.5"},
            {"Elena Rodriguez", "Political Analyst", "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=400&q=80", "9.2"},
            {"Marcus Thorne", "Investigative Journalist", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400&q=80", "9.7"}
        };

        for (int i = 0; i < reportersData.length; i++) {
            Reporter r = new Reporter();
            r.setId(i + 1);
            r.setName(reportersData[i][0]);
            r.setTitle(reportersData[i][1]);
            r.setAvatarUrl(reportersData[i][2]);
            r.setTruthScore(Double.parseDouble(reportersData[i][3]));
            r.setVerified(true);
            list.add(r);
        }
        data.setValue(list);
        return data;
    }

    public LiveData<SearchResponse> searchByCategory(String categorySlug) {
        MutableLiveData<SearchResponse> data = new MutableLiveData<>();
        newsApiService.getTopHeadlines("in", categorySlug, 20, 1, API_KEY)
                .enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                            List<Article> articles = NewsApiMapper.mapArticles(response.body().getArticles());
                            data.setValue(SearchResponse.fromArticles(articles));
                        } else {
                            data.setValue(SearchResponse.fromArticles(new ArrayList<>()));
                        }
                    }
                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    private List<Category> buildCategories() {
        String[][] cats = {
            {"General", "general", "https://images.unsplash.com/photo-1495020689067-958852a7765e?w=400&q=80"},
            {"Business", "business", "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=400&q=80"},
            {"Technology", "technology", "https://images.unsplash.com/photo-1518770660439-4636190af475?w=400&q=80"},
            {"Science", "science", "https://images.unsplash.com/photo-1507413245164-6160d8298b31?w=400&q=80"},
            {"Health", "health", "https://images.unsplash.com/photo-1505751172876-fa1923c5c528?w=400&q=80"},
            {"Sports", "sports", "https://images.unsplash.com/photo-1461896756970-47408175f583?w=400&q=80"},
            {"Entertainment", "entertainment", "https://images.unsplash.com/photo-1522869635100-9f4c5e86aa37?w=400&q=80"}
        };
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < cats.length; i++) {
            Category c = new Category();
            c.setId(i + 1);
            c.setName(cats[i][0]);
            c.setSlug(cats[i][1]);
            c.setIconUrl(cats[i][2]);
            c.setActive(true);
            list.add(c);
        }
        return list;
    }
}
