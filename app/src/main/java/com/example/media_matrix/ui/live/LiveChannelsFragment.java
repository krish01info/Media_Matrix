package com.example.media_matrix.ui.live;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.media_matrix.R;
import com.example.media_matrix.databinding.FragmentLiveChannelsBinding;
import com.example.media_matrix.domain.model.LiveChannel;
import com.example.media_matrix.ui.live.adapter.LiveChannelAdapter;
import com.google.android.material.chip.Chip;

import java.util.List;

public class LiveChannelsFragment extends Fragment {

    private FragmentLiveChannelsBinding binding;
    private LiveChannelsViewModel viewModel;
    private LiveChannelAdapter channelAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLiveChannelsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LiveChannelsViewModel.class);

        setupWebView();
        setupRecyclerView();
        observeData();
        startLivePulseAnimation();
    }

    private void setupWebView() {
        WebView wv = binding.webViewPlayer;
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        
        // Use Hardware acceleration
        wv.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        // Standard desktop-like User Agent to ensure embed compatibility
        String desktopUA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36";
        settings.setUserAgentString(desktopUA);

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (binding != null) binding.playerLoadingOverlay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (binding != null) {
                    binding.playerLoadingOverlay.setVisibility(View.GONE);
                }
            }
        });

        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 70 && binding != null) {
                    binding.playerLoadingOverlay.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadChannel(LiveChannel channel) {
        if (binding == null) return;
        
        binding.playerLoadingOverlay.setVisibility(View.VISIBLE);

        // Build HTML with correct YouTube embed parameters
        // origin parameter is critical for WebView embeds
        String embedUrl = channel.getEmbedUrl() + "&origin=https://www.youtube.com&enablejsapi=1";
        
        String html = "<html><head><meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no'>" +
                "<style>body{margin:0;padding:0;background:#000; width:100vw; height:100vh;} iframe{width:100%;height:100%;border:none;}</style></head>" +
                "<body><iframe src='" + embedUrl + "' allow='autoplay; encrypted-media' allowfullscreen></iframe></body></html>";

        // Using loadDataWithBaseURL is crucial for YouTube embeds to work in WebView
        binding.webViewPlayer.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null);

        // Update now-playing info
        binding.nowPlayingChannel.setText(channel.getName());
        binding.nowPlayingCountry.setText(channel.getCountry() + " · " + channel.getLanguage());

        // Update Open in YouTube button
        binding.btnOpenYoutube.setOnClickListener(v -> {
            String ytUrl;
            if (channel.isChannelId()) {
                ytUrl = "https://www.youtube.com/channel/" + channel.getYoutubeVideoId() + "/live";
            } else {
                ytUrl = "https://www.youtube.com/watch?v=" + channel.getYoutubeVideoId();
            }
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ytUrl)));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ytUrl)));
            }
        });

        // Highlight selected item in list
        channelAdapter.setSelectedChannelId(channel.getId());
    }

    private void setupRecyclerView() {
        channelAdapter = new LiveChannelAdapter();
        channelAdapter.setOnChannelClickListener(channel -> {
            viewModel.selectChannel(channel);
        });
        binding.channelsRecycler.setAdapter(channelAdapter);
        binding.channelsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.channelsRecycler.setNestedScrollingEnabled(false);
    }

    private void buildCategoryChips(List<String> categories) {
        if (binding == null) return;
        binding.categoryChipsContainer.removeAllViews();

        for (String cat : categories) {
            Chip chip = new Chip(requireContext());
            chip.setText(cat);
            chip.setCheckable(true);
            chip.setChecked("All".equals(cat));
            chip.setChipBackgroundColorResource(R.color.surface_medium);
            chip.setCheckedIconVisible(false);
            chip.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.chip_text_selector));
            chip.setTextSize(12f);

            chip.setOnClickListener(v -> {
                viewModel.filterByCategory(cat);
                for (int i = 0; i < binding.categoryChipsContainer.getChildCount(); i++) {
                    Chip c = (Chip) binding.categoryChipsContainer.getChildAt(i);
                    c.setChecked(c.getText().toString().equals(cat));
                }
            });

            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMarginEnd(8);
            chip.setLayoutParams(lp);

            binding.categoryChipsContainer.addView(chip);
        }
    }

    private void observeData() {
        viewModel.getCategories().observe(getViewLifecycleOwner(), this::buildCategoryChips);

        viewModel.getChannels().observe(getViewLifecycleOwner(), channels -> {
            if (channels != null) channelAdapter.setChannels(channels);
        });

        viewModel.getSelectedChannel().observe(getViewLifecycleOwner(), channel -> {
            if (channel != null) loadChannel(channel);
        });
    }

    private void startLivePulseAnimation() {
        ObjectAnimator pulse = ObjectAnimator.ofFloat(binding.livePulseDot, "alpha", 1f, 0.2f);
        pulse.setDuration(700);
        pulse.setRepeatCount(ObjectAnimator.INFINITE);
        pulse.setRepeatMode(ObjectAnimator.REVERSE);
        pulse.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding != null) binding.webViewPlayer.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) binding.webViewPlayer.onResume();
    }

    @Override
    public void onDestroyView() {
        if (binding != null) {
            binding.webViewPlayer.stopLoading();
            binding.webViewPlayer.loadUrl("about:blank");
            binding.webViewPlayer.destroy();
        }
        super.onDestroyView();
        binding = null;
    }
}
