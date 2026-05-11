package com.example.media_matrix.ui.newspaper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.media_matrix.databinding.ActivityNewspaperReaderBinding;

public class NewspaperReaderActivity extends AppCompatActivity {

    public static final String EXTRA_NAME   = "newspaper_name";
    public static final String EXTRA_URL    = "newspaper_url";
    public static final String EXTRA_LOGO   = "newspaper_logo";

    private ActivityNewspaperReaderBinding binding;
    private String websiteUrl;
    private boolean pageLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewspaperReaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name    = getIntent().getStringExtra(EXTRA_NAME);
        websiteUrl     = getIntent().getStringExtra(EXTRA_URL);
        String logoUrl = getIntent().getStringExtra(EXTRA_LOGO);

        setupTopBar(name, logoUrl);
        setupWebView();

        if (websiteUrl != null && !websiteUrl.isEmpty()) {
            binding.readerWebView.loadUrl(websiteUrl);
        } else {
            showError();
        }
    }

    // ── Top Bar ────────────────────────────────────────────────────────

    private void setupTopBar(String name, String logoUrl) {
        binding.readerTitle.setText(name != null ? name : "Newspaper");
        binding.loadingTitle.setText(name != null ? name : "Newspaper");

        if (logoUrl != null && !logoUrl.isEmpty()) {
            Glide.with(this).load(logoUrl)
                    .placeholder(com.example.media_matrix.R.color.surface_light)
                    .into(binding.readerLogo);
            Glide.with(this).load(logoUrl)
                    .placeholder(com.example.media_matrix.R.color.surface_light)
                    .into(binding.loadingLogo);
        }

        binding.btnReaderClose.setOnClickListener(v -> finish());

        binding.btnOpenBrowser.setOnClickListener(v -> {
            if (websiteUrl != null) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl)));
            }
        });

        binding.btnRetry.setOnClickListener(v -> {
            binding.readerErrorState.setVisibility(View.GONE);
            binding.readerLoadingOverlay.setVisibility(View.VISIBLE);
            if (websiteUrl != null) binding.readerWebView.loadUrl(websiteUrl);
        });
    }

    // ── WebView ────────────────────────────────────────────────────────

    private void setupWebView() {
        WebSettings settings = binding.readerWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        settings.setUserAgentString(
                "Mozilla/5.0 (Linux; Android 12; Pixel 6) AppleWebKit/537.36 "
                + "(KHTML, like Gecko) Chrome/112.0.0.0 Mobile Safari/537.36");

        binding.readerWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.readerLoadingOverlay.setVisibility(View.VISIBLE);
                binding.readerErrorState.setVisibility(View.GONE);
                binding.readerProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pageLoaded = true;
                binding.readerProgressBar.setVisibility(View.GONE);
                // Short delay so the page can render before hiding the overlay
                binding.readerWebView.postDelayed(() -> {
                    if (binding != null)
                        binding.readerLoadingOverlay.setVisibility(View.GONE);
                }, 600);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                // Only show error for the main frame, not sub-resources
                if (request.isForMainFrame() && !pageLoaded) {
                    binding.readerLoadingOverlay.setVisibility(View.GONE);
                    binding.readerProgressBar.setVisibility(View.GONE);
                    binding.readerErrorState.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.readerWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                binding.readerProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    binding.readerProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    // ── Back navigation ────────────────────────────────────────────────

    @Override
    public void onBackPressed() {
        if (binding.readerWebView.canGoBack()) {
            binding.readerWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void showError() {
        binding.readerLoadingOverlay.setVisibility(View.GONE);
        binding.readerErrorState.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.readerWebView.stopLoading();
        binding.readerWebView.destroy();
        binding = null;
    }
}
