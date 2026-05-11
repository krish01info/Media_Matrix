package com.example.media_matrix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.RadioBrowserApiService;
import com.example.media_matrix.data.remote.RadioBrowserMapper;
import com.example.media_matrix.data.remote.response.RadioBrowserStation;
import com.example.media_matrix.data.remote.response.RadioFeedResponse;
import com.example.media_matrix.domain.model.RadioStream;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fetches live radio streams from Radio Browser API — free, no key required.
 * Returns news/talk stations ordered by votes (most popular first).
 */
public class RadioRepository {

    private static final String TAG = "RadioRepository";
    private final RadioBrowserApiService radioBrowserApiService;

    public RadioRepository() {
        radioBrowserApiService = ApiClient.getInstance().getRadioBrowserApiService();
    }

    public LiveData<RadioFeedResponse> getRadioFeed() {
        MutableLiveData<RadioFeedResponse> data = new MutableLiveData<>();
        Log.d(TAG, "getRadioFeed: Calling Radio Browser API...");

        radioBrowserApiService.getNewStationsByTag(30, "votes", true, true)
                .enqueue(new Callback<List<RadioBrowserStation>>() {
                    @Override
                    public void onResponse(Call<List<RadioBrowserStation>> call,
                                           Response<List<RadioBrowserStation>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<RadioStream> streams = RadioBrowserMapper.mapStations(response.body());
                            Log.d(TAG, "Loaded " + streams.size() + " radio stations");
                            data.setValue(RadioFeedResponse.fromStreams(streams));
                        } else {
                            Log.e(TAG, "Radio Browser call failed: " + response.code());
                            data.setValue(RadioFeedResponse.fromStreams(new ArrayList<>()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RadioBrowserStation>> call, Throwable t) {
                        Log.e(TAG, "Radio Browser network failure", t);
                        data.setValue(RadioFeedResponse.fromStreams(new ArrayList<>()));
                    }
                });
        return data;
    }
}
