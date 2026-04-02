package com.example.media_matrix.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.data.remote.ApiClient;
import com.example.media_matrix.data.remote.ApiService;
import com.example.media_matrix.data.remote.response.ApiListResponse;
import com.example.media_matrix.data.remote.response.SearchResponse;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.domain.model.Category;
import com.example.media_matrix.domain.model.Reporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private final ApiService apiService;

    public SearchRepository() {
        apiService = ApiClient.getInstance().getApiService();
    }

    public LiveData<SearchResponse> search(String query, String scope, boolean verifiedOnly) {
        MutableLiveData<SearchResponse> data = new MutableLiveData<>();
        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        if (scope != null) params.put("scope", scope);
        if (verifiedOnly) params.put("verified", "true");

        apiService.search(params).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Category>> getCategories() {
        MutableLiveData<List<Category>> data = new MutableLiveData<>();
        apiService.getCategories().enqueue(new Callback<ApiListResponse<Category>>() {
            @Override
            public void onResponse(Call<ApiListResponse<Category>> call, Response<ApiListResponse<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiListResponse<Category>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Reporter>> getReporters(boolean verifiedOnly) {
        MutableLiveData<List<Reporter>> data = new MutableLiveData<>();
        Map<String, String> params = new HashMap<>();
        if (verifiedOnly) params.put("verified", "true");

        apiService.getReporters(params).enqueue(new Callback<ApiListResponse<Reporter>>() {
            @Override
            public void onResponse(Call<ApiListResponse<Reporter>> call, Response<ApiListResponse<Reporter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiListResponse<Reporter>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
