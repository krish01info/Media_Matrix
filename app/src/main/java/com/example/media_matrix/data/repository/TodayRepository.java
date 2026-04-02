package com.example.media_matrix.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.ApiService;
import com.example.media_matrix.data.remote.response.TodayFeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayRepository {

    private final ApiService apiService;

    public TodayRepository() {
        apiService = ApiClient.getInstance().getApiService();
    }

    public LiveData<TodayFeedResponse> getTodayFeed() {
        MutableLiveData<TodayFeedResponse> data = new MutableLiveData<>();
        apiService.getTodayFeed().enqueue(new Callback<TodayFeedResponse>() {
            @Override
            public void onResponse(Call<TodayFeedResponse> call, Response<TodayFeedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TodayFeedResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
