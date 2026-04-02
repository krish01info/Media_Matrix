package com.example.media_matrix.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.ApiService;
import com.example.media_matrix.data.remote.response.HomeFeedResponse;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Newspaper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {

    private final ApiService apiService;

    public HomeRepository() {
        apiService = ApiClient.getInstance().getApiService();
    }

    public LiveData<HomeFeedResponse> getHomeFeed() {
        MutableLiveData<HomeFeedResponse> data = new MutableLiveData<>();
        apiService.getHomeFeed().enqueue(new Callback<HomeFeedResponse>() {
            @Override
            public void onResponse(Call<HomeFeedResponse> call, Response<HomeFeedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HomeFeedResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
