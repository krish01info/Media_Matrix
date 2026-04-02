package com.example.media_matrix.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.media_matrix.domain.model.User;
import com.google.gson.Gson;

public class PreferenceManager {

    private static final String PREF_NAME = "media_matrix_prefs";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_USER = "user_data";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_DARK_MODE = "dark_mode";

    private final SharedPreferences prefs;
    private final Gson gson;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Auth Tokens
    public void saveTokens(String accessToken, String refreshToken) {
        prefs.edit()
                .putString(KEY_ACCESS_TOKEN, accessToken)
                .putString(KEY_REFRESH_TOKEN, refreshToken)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public String getAccessToken() {
        return prefs.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getRefreshToken() {
        return prefs.getString(KEY_REFRESH_TOKEN, null);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // User
    public void saveUser(User user) {
        prefs.edit().putString(KEY_USER, gson.toJson(user)).apply();
    }

    public User getUser() {
        String json = prefs.getString(KEY_USER, null);
        if (json == null) return null;
        return gson.fromJson(json, User.class);
    }

    // Logout
    public void clearSession() {
        prefs.edit()
                .remove(KEY_ACCESS_TOKEN)
                .remove(KEY_REFRESH_TOKEN)
                .remove(KEY_USER)
                .putBoolean(KEY_IS_LOGGED_IN, false)
                .apply();
    }

    // Theme
    public void setDarkMode(boolean darkMode) {
        prefs.edit().putBoolean(KEY_DARK_MODE, darkMode).apply();
    }

    public boolean isDarkMode() {
        return prefs.getBoolean(KEY_DARK_MODE, true);
    }
}
