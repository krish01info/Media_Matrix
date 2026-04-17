package com.example.media_matrix.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.media_matrix.data.repository.HomeRepository;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Newspaper;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private final HomeRepository repository;
    private final MutableLiveData<List<Article>> featured = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Article>> trending = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Article>> topCharts = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Newspaper>> newspapers = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public HomeViewModel() {
        repository = new HomeRepository();
        loadHomeFeed();
    }

    public void loadHomeFeed() {
        Log.d(TAG, "Loading home feed...");
        isLoading.setValue(true);
        repository.getHomeFeed().observeForever(response -> {
            isLoading.setValue(false);
            if (response != null && response.isSuccess()) {
                Log.d(TAG, "Home feed loaded successfully");
                
                List<Article> f = response.getFeatured();
                List<Article> t = response.getTrending();
                List<Article> tc = response.getTopCharts();
                List<Newspaper> n = response.getNewspapers();

                featured.setValue(f != null ? f : new ArrayList<>());
                trending.setValue(t != null ? t : new ArrayList<>());
                topCharts.setValue(tc != null ? tc : new ArrayList<>());
                newspapers.setValue(n != null ? n : new ArrayList<>());
            } else {
                Log.e(TAG, "Failed to load home feed. Response is null or success=false");
                error.setValue("Failed to load home feed");
            }
        });
    }

    public LiveData<List<Article>> getFeatured() { return featured; }
    public LiveData<List<Article>> getTrending() { return trending; }
    public LiveData<List<Article>> getTopCharts() { return topCharts; }
    public LiveData<List<Newspaper>> getNewspapers() { return newspapers; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

    public void refresh() {
        loadHomeFeed();
    }
}
