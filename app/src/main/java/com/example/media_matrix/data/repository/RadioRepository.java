package com.example.media_matrix.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.ApiService;
import com.example.media_matrix.data.remote.response.RadioFeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RadioRepository {

    private final ApiService apiService;

    public RadioRepository() {
        apiService = ApiClient.getInstance().getApiService();
    }

    public LiveData<RadioFeedResponse> getRadioFeed() {
        MutableLiveData<RadioFeedResponse> data = new MutableLiveData<>();
        apiService.getRadioFeed().enqueue(new Callback<RadioFeedResponse>() {
            @Override
            public void onResponse(Call<RadioFeedResponse> call, Response<RadioFeedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RadioFeedResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
