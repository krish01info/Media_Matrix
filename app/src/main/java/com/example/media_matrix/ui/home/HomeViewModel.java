package com.example.media_matrix.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.media_matrix.data.remote.response.HomeFeedResponse;
import com.example.media_matrix.data.repository.HomeRepository;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Newspaper;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final HomeRepository repository;
    private MutableLiveData<List<Article>> featured = new MutableLiveData<>();
    private MutableLiveData<List<Article>> trending = new MutableLiveData<>();
    private MutableLiveData<List<Article>> topCharts = new MutableLiveData<>();
    private MutableLiveData<List<Newspaper>> newspapers = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> error = new MutableLiveData<>();

    public HomeViewModel() {
        repository = new HomeRepository();
        loadHomeFeed();
    }

    public void loadHomeFeed() {
        isLoading.setValue(true);
        repository.getHomeFeed().observeForever(response -> {
            isLoading.setValue(false);
            if (response != null) {
                featured.setValue(response.getFeatured());
                trending.setValue(response.getTrending());
                topCharts.setValue(response.getTopCharts());
                newspapers.setValue(response.getNewspapers());
            } else {
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
