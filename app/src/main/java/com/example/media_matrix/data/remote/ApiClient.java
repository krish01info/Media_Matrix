package com.example.media_matrix.data.remote;

import com.example.media_matrix.data.local.PreferenceManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // For physical device over USB: use 127.0.0.1 and run `adb reverse tcp:3000 tcp:3000`
    // For Android Emulator: use 10.0.2.2
    private static final String BASE_URL = "http://127.0.0.1:3000/api/";

    private static volatile ApiClient instance;
    private final ApiService apiService;
    private final OkHttpClient okHttpClient;
    private PreferenceManager preferenceManager;

    private ApiClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(this))
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) {
                    instance = new ApiClient();
                }
            }
        }
        return instance;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public void setPreferenceManager(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }
}
