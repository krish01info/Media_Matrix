package com.example.media_matrix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.BuildConfig;
import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.NewsApiMapper;
import com.example.media_matrix.data.remote.NewsApiService;
import com.example.media_matrix.data.remote.response.HomeFeedResponse;
import com.example.media_matrix.data.remote.response.NewsApiResponse;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Newspaper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fetches home feed data from NewsAPI.org.
 * Ensures every section has data by using fallbacks and high-quality mock data.
 */
public class HomeRepository {

    private static final String TAG = "HomeRepository";
    private static final String API_KEY = BuildConfig.NEWS_API_KEY;
    private final NewsApiService newsApiService;

    public HomeRepository() {
        newsApiService = ApiClient.getInstance().getNewsApiService();
    }

    public LiveData<HomeFeedResponse> getHomeFeed() {
        MutableLiveData<HomeFeedResponse> data = new MutableLiveData<>();
        
        // Safety check: If API Key is missing, show high-quality mock data immediately
        if (API_KEY == null || API_KEY.isEmpty() || API_KEY.equals("null") || API_KEY.length() < 10) {
            Log.e(TAG, "API_KEY is missing or invalid! Showing Mock Data.");
            data.setValue(getFullMockFeed());
            return data;
        }

        final List<Article> featured = new ArrayList<>();
        final List<Article> trending = new ArrayList<>();
        final List<Article> topCharts = new ArrayList<>();
        final List<Newspaper> newspapers = new ArrayList<>();
        
        final AtomicInteger pending = new AtomicInteger(3);

        // 1. Featured & Trending (Top Headlines)
        newsApiService.getTopHeadlines(null, "general", 20, 1, API_KEY)
                .enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Article> all = NewsApiMapper.mapArticles(response.body().getArticles(), true, false, false, false);
                            if (!all.isEmpty()) {
                                int limit = Math.min(5, all.size());
                                for (int i = 0; i < limit; i++) featured.add(all.get(i));
                                for (int i = limit; i < all.size(); i++) {
                                    Article a = all.get(i);
                                    a.setFeatured(false);
                                    trending.add(a);
                                }
                            }
                        }
                        checkCompletion(pending, data, featured, trending, topCharts, newspapers);
                    }

                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        checkCompletion(pending, data, featured, trending, topCharts, newspapers);
                    }
                });

        // 2. Top Charts
        newsApiService.searchEverything("world", "en", "popularity", null, 10, 1, API_KEY)
                .enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            topCharts.addAll(NewsApiMapper.mapArticles(response.body().getArticles()));
                        }
                        checkCompletion(pending, data, featured, trending, topCharts, newspapers);
                    }

                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        checkCompletion(pending, data, featured, trending, topCharts, newspapers);
                    }
                });

        // 3. Today's Newspapers
        newsApiService.searchEverything("news OR journal", "en", "publishedAt", "nytimes.com,theguardian.com,reuters.com,wsj.com", 10, 1, API_KEY)
                .enqueue(new Callback<NewsApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Article> mapped = NewsApiMapper.mapArticles(response.body().getArticles());
                            for (Article a : mapped) {
                                Newspaper n = new Newspaper();
                                n.setId(a.getUuid().hashCode());
                                n.setSourceName(a.getSourceName());
                                n.setCoverImageUrl(a.getImageUrl());
                                n.setPdfUrl(a.getUuid());
                                newspapers.add(n);
                            }
                        }
                        checkCompletion(pending, data, featured, trending, topCharts, newspapers);
                    }

                    @Override
                    public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                        checkCompletion(pending, data, featured, trending, topCharts, newspapers);
                    }
                });

        return data;
    }

    private void checkCompletion(AtomicInteger pending, MutableLiveData<HomeFeedResponse> data,
                                List<Article> f, List<Article> t, List<Article> tc, List<Newspaper> n) {
        if (pending.decrementAndGet() == 0) {
            // Guarantee content even if some lists are empty
            if (f.isEmpty()) f.addAll(getMockArticles("FEATURED", 3));
            if (t.isEmpty()) t.addAll(getMockArticles("TRENDING", 5));
            if (tc.isEmpty()) tc.addAll(getMockArticles("TOP CHART", 5));
            if (n.isEmpty()) n.addAll(getMockNewspapers());
            
            data.postValue(HomeFeedResponse.fromLists(f, t, tc, n));
        }
    }

    private List<Article> getMockArticles(String categoryName, int count) {
        List<Article> list = new ArrayList<>();
        String[] titles = {
            "Global Markets React to New Economic Policies",
            "Tech Giants Announce Breakthrough in AI Safety",
            "Future of Sustainable Energy: A Special Report",
            "Space Exploration: New Frontier Discovered",
            "Health & Wellness: Trends for the Modern Era"
        };
        String[] images = {
            "https://images.unsplash.com/photo-1504711434969-e33886168f5c",
            "https://images.unsplash.com/photo-1485827404703-89b55fcc595e",
            "https://images.unsplash.com/photo-1473341304170-971dccb5ac1e",
            "https://images.unsplash.com/photo-1451187580459-43490279c0fa",
            "https://images.unsplash.com/photo-1505751172876-fa1923c5c528"
        };

        for (int i = 0; i < count; i++) {
            Article a = new Article();
            a.setUuid("mock_" + categoryName.replace(" ", "_") + "_" + i);
            a.setTitle(titles[i % titles.length]);
            a.setSummary("Discover the latest insights and verified reporting on " + categoryName.toLowerCase() + " topics from Media Matrix.");
            a.setImageUrl(images[i % images.length]);
            a.setSourceName("Media Matrix");
            a.setPublishedAt("2024-05-20T10:00:00Z");
            a.setInteractionCount((long) (Math.random() * 5000) + 1000);

            com.example.media_matrix.domain.model.Category cat = new com.example.media_matrix.domain.model.Category();
            cat.setName(categoryName);
            a.setCategory(cat);
            list.add(a);
        }
        return list;
    }

    private List<Newspaper> getMockNewspapers() {
        List<Newspaper> list = new ArrayList<>();
        String[][] data = {
            {"The New York Times", "https://images.unsplash.com/photo-1554469384-e58fac16e23a"},
            {"The Guardian", "https://images.unsplash.com/photo-1588681664899-f142ff2dc9b1"},
            {"Wall Street Journal", "https://images.unsplash.com/photo-1503551723145-6c040742065b"}
        };
        for (String[] item : data) {
            Newspaper n = new Newspaper();
            n.setSourceName(item[0]);
            n.setCoverImageUrl(item[1]);
            list.add(n);
        }
        return list;
    }

    private HomeFeedResponse getFullMockFeed() {
        return HomeFeedResponse.fromLists(
            getMockArticles("FEATURED", 3), 
            getMockArticles("TRENDING", 5), 
            getMockArticles("TOP CHART", 5), 
            getMockNewspapers()
        );
    }
}
