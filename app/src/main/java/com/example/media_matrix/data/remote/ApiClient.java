package com.example.media_matrix.data.remote;

import com.example.media_matrix.data.local.PreferenceManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton providing Retrofit clients for external APIs.
 */
public class ApiClient {

    private static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
    private static final String RADIO_BROWSER_URL = "https://de1.api.radio-browser.info/json/";

    private static volatile ApiClient instance;

    private final NewsApiService newsApiService;
    private final RadioBrowserApiService radioBrowserApiService;
    private final ApiService apiService;

    private PreferenceManager preferenceManager;

    private ApiClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // BODY level logging helps us see why the API returns empty data
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // 1. NewsAPI
        Retrofit newsRetrofit = new Retrofit.Builder()
                .baseUrl(NEWS_API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newsApiService = newsRetrofit.create(NewsApiService.class);

        // 2. Radio Browser
        Retrofit radioRetrofit = new Retrofit.Builder()
                .baseUrl(RADIO_BROWSER_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        radioBrowserApiService = radioRetrofit.create(RadioBrowserApiService.class);

        // 3. Legacy Placeholder
        apiService = newsRetrofit.create(ApiService.class);
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

    public NewsApiService getNewsApiService() { return newsApiService; }
    public RadioBrowserApiService getRadioBrowserApiService() { return radioBrowserApiService; }
    
    @Deprecated
    public ApiService getApiService() { return apiService; }

    public void setPreferenceManager(PreferenceManager pm) { this.preferenceManager = pm; }
    public PreferenceManager getPreferenceManager() { return preferenceManager; }
    public String getBaseUrl() { return NEWS_API_BASE_URL; }
}
