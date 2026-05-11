package com.example.media_matrix.ui.live;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.media_matrix.data.local.LiveChannelDataSource;
import com.example.media_matrix.domain.model.LiveChannel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LiveChannelsViewModel extends ViewModel {

    private final MutableLiveData<List<LiveChannel>> channels = new MutableLiveData<>();
    private final MutableLiveData<List<String>> categories = new MutableLiveData<>();
    private final MutableLiveData<String> selectedCategory = new MutableLiveData<>("All");
    private final MutableLiveData<LiveChannel> selectedChannel = new MutableLiveData<>();

    private List<LiveChannel> allChannels;

    public LiveChannelsViewModel() {
        allChannels = LiveChannelDataSource.getChannels();

        // Build unique category list preserving order
        Set<String> catSet = new LinkedHashSet<>();
        catSet.add("All");
        for (LiveChannel ch : allChannels) catSet.add(ch.getCategory());
        categories.setValue(new ArrayList<>(catSet));

        channels.setValue(allChannels);

        // Auto-select first channel
        if (!allChannels.isEmpty()) selectedChannel.setValue(allChannels.get(0));
    }

    public void filterByCategory(String category) {
        selectedCategory.setValue(category);
        if ("All".equalsIgnoreCase(category)) {
            channels.setValue(allChannels);
        } else {
            List<LiveChannel> filtered = new ArrayList<>();
            for (LiveChannel ch : allChannels) {
                if (ch.getCategory().equalsIgnoreCase(category)) filtered.add(ch);
            }
            channels.setValue(filtered);
        }
    }

    public void selectChannel(LiveChannel channel) {
        selectedChannel.setValue(channel);
    }

    public LiveData<List<LiveChannel>> getChannels() { return channels; }
    public LiveData<List<String>> getCategories() { return categories; }
    public LiveData<String> getSelectedCategory() { return selectedCategory; }
    public LiveData<LiveChannel> getSelectedChannel() { return selectedChannel; }
}
