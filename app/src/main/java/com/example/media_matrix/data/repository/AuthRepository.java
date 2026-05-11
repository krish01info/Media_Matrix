package com.example.media_matrix.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.media_matrix.data.local.PreferenceManager;
import com.example.media_matrix.data.remote.response.AuthResponse;
import com.example.media_matrix.domain.model.User;

/**
 * Local-only AuthRepository — no backend server required.
 *
 * Login and Register simply validate inputs locally and persist a session flag.
 * Google Sign-In still uses the device Credential Manager to get the ID token,
 * but the token is only decoded locally (display name / email) rather than
 * being sent to a backend server.
 *
 * This allows the app to work fully offline-auth without running Node.js / Docker.
 */
public class AuthRepository {

    private static final String TAG = "AuthRepository";
    private PreferenceManager preferenceManager;

    public AuthRepository() {
        // No ApiService needed
    }

    public void setPreferenceManager(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }

    /**
     * Local email/password login.
     * Accepts any non-empty email + password (≥ 6 chars) and stores the session.
     */
    public LiveData<AuthResponse> login(String email, String password) {
        MutableLiveData<AuthResponse> data = new MutableLiveData<>();

        if (email == null || email.trim().isEmpty()) {
            AuthResponse err = new AuthResponse();
            err.setErrorMessage("Please enter your email address.");
            data.setValue(err);
            return data;
        }
        if (password == null || password.length() < 6) {
            AuthResponse err = new AuthResponse();
            err.setErrorMessage("Password must be at least 6 characters.");
            data.setValue(err);
            return data;
        }

        Log.d(TAG, "Local login for: " + email);

        // Create a local user from the email
        User user = new User();
        user.setEmail(email);
        user.setUsername(email.split("@")[0]);

        if (preferenceManager != null) {
            preferenceManager.saveTokens("local_access_token", "local_refresh_token");
            preferenceManager.saveUser(user);
        }

        AuthResponse success = AuthResponse.localSuccess(user);
        data.setValue(success);
        return data;
    }

    /**
     * Local registration — stores any valid email/password combination.
     */
    public LiveData<AuthResponse> register(String username, String email, String password) {
        MutableLiveData<AuthResponse> data = new MutableLiveData<>();

        if (username == null || username.trim().isEmpty()) {
            AuthResponse err = new AuthResponse();
            err.setErrorMessage("Please enter a username.");
            data.setValue(err);
            return data;
        }
        if (email == null || email.trim().isEmpty()) {
            AuthResponse err = new AuthResponse();
            err.setErrorMessage("Please enter your email address.");
            data.setValue(err);
            return data;
        }
        if (password == null || password.length() < 6) {
            AuthResponse err = new AuthResponse();
            err.setErrorMessage("Password must be at least 6 characters.");
            data.setValue(err);
            return data;
        }

        Log.d(TAG, "Local register for: " + email);

        User user = new User();
        user.setEmail(email);
        user.setUsername(username.trim());

        if (preferenceManager != null) {
            preferenceManager.saveTokens("local_access_token", "local_refresh_token");
            preferenceManager.saveUser(user);
        }

        data.setValue(AuthResponse.localSuccess(user));
        return data;
    }

    /**
     * Local Google login — the Google ID token is NOT sent to a backend.
     * The display name and email are extracted from the credential and stored locally.
     *
     * @param displayName user's full name from GoogleIdTokenCredential
     * @param email       user's email from GoogleIdTokenCredential
     */
    public LiveData<AuthResponse> googleLogin(String displayName, String email) {
        MutableLiveData<AuthResponse> data = new MutableLiveData<>();

        Log.d(TAG, "Local Google login for: " + email);

        User user = new User();
        user.setEmail(email != null ? email : "");
        user.setUsername(displayName != null ? displayName : (email != null ? email.split("@")[0] : "user"));

        if (preferenceManager != null) {
            preferenceManager.saveTokens("google_local_token", "google_local_refresh");
            preferenceManager.saveUser(user);
        }

        data.setValue(AuthResponse.localSuccess(user));
        return data;
    }

    public void logout() {
        if (preferenceManager != null) {
            preferenceManager.clearSession();
        }
    }
}
