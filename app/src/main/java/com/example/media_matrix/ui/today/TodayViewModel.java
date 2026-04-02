package com.example.media_matrix.ui.today;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.media_matrix.data.repository.TodayRepository;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.RegionalChart;

import java.util.List;

public class TodayViewModel extends ViewModel {

    private final TodayRepository repository;
    private MutableLiveData<List<Article>> morningBrief = new MutableLiveData<>();
    private MutableLiveData<List<Article>> developing = new MutableLiveData<>();
    private MutableLiveData<List<Article>> trending = new MutableLiveData<>();
    private MutableLiveData<List<RegionalChart>> regionalCharts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public TodayViewModel() {
        repository = new TodayRepository();
        loadTodayFeed();
    }

    public void loadTodayFeed() {
        isLoading.setValue(true);
        repository.getTodayFeed().observeForever(response -> {
            isLoading.setValue(false);
            if (response != null) {
                morningBrief.setValue(response.getMorningBrief());
                developing.setValue(response.getDeveloping());
                trending.setValue(response.getTrending());
                regionalCharts.setValue(response.getRegionalCharts());
            }
        });
    }

    public LiveData<List<Article>> getMorningBrief() { return morningBrief; }
    public LiveData<List<Article>> getDeveloping() { return developing; }
    public LiveData<List<Article>> getTrending() { return trending; }
    public LiveData<List<RegionalChart>> getRegionalCharts() { return regionalCharts; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }

    public void refresh() { loadTodayFeed(); }
}
