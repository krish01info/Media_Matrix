package com.example.media_matrix.ui.radio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.media_matrix.data.repository.RadioRepository;
import com.example.media_matrix.domain.model.Podcast;
import com.example.media_matrix.domain.model.RadioStream;
import java.util.List;

public class RadioViewModel extends ViewModel {
    private final RadioRepository repository;
    private MutableLiveData<List<RadioStream>> streams = new MutableLiveData<>();
    private MutableLiveData<List<Podcast>> podcasts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public RadioViewModel() {
        repository = new RadioRepository();
        loadRadioFeed();
    }

    public void loadRadioFeed() {
        isLoading.setValue(true);
        repository.getRadioFeed().observeForever(response -> {
            isLoading.setValue(false);
            if (response != null) {
                streams.setValue(response.getStreams());
                podcasts.setValue(response.getPodcasts());
            }
        });
    }

    public LiveData<List<RadioStream>> getStreams() { return streams; }
    public LiveData<List<Podcast>> getPodcasts() { return podcasts; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public void refresh() { loadRadioFeed(); }
}
