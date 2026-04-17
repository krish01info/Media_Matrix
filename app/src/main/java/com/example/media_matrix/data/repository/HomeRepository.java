package com.example.media_matrix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.ApiService;
import com.example.media_matrix.data.remote.response.HomeFeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {

    private static final String TAG = "HomeRepository";
    private final ApiService apiService;

    public HomeRepository() {
        apiService = ApiClient.getInstance().getApiService();
    }

    public LiveData<HomeFeedResponse> getHomeFeed() {
        MutableLiveData<HomeFeedResponse> data = new MutableLiveData<>();
        Log.d(TAG, "getHomeFeed: Calling API...");
        
        apiService.getHomeFeed().enqueue(new Callback<HomeFeedResponse>() {
            @Override
            public void onResponse(Call<HomeFeedResponse> call, Response<HomeFeedResponse> response) {
                Log.d(TAG, "onResponse: Code " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: Success!");
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "onResponse: Failed with code " + response.code());
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<HomeFeedResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error calling API", t);
                data.setValue(null);
            }
        });
        return data;
    }
}
