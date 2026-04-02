package com.example.media_matrix.ui.radio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.media_matrix.R;
import com.example.media_matrix.databinding.FragmentRadioBinding;
import com.example.media_matrix.ui.player.PlayerActivity;
import com.example.media_matrix.ui.radio.adapter.PodcastAdapter;
import com.example.media_matrix.ui.radio.adapter.RadioStreamAdapter;

public class RadioFragment extends Fragment {
    private FragmentRadioBinding binding;
    private RadioViewModel viewModel;
    private RadioStreamAdapter streamAdapter;
    private PodcastAdapter podcastAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRadioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RadioViewModel.class);
        setupAdapters();
        setupSwipeRefresh();
        observeData();
    }

    private void setupAdapters() {
        streamAdapter = new RadioStreamAdapter();
        streamAdapter.setOnStreamClickListener(stream -> {
            Intent intent = new Intent(getContext(), PlayerActivity.class);
            intent.putExtra("stream_title", stream.getTitle());
            intent.putExtra("stream_url", stream.getStreamUrl());
            intent.putExtra("stream_image", stream.getThumbnailUrl());
            intent.putExtra("stream_source", stream.getSourceName());
            intent.putExtra("is_live", stream.isLive());
            startActivity(intent);
        });
        binding.streamsRecycler.setAdapter(streamAdapter);
        binding.streamsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        podcastAdapter = new PodcastAdapter();
        podcastAdapter.setOnPodcastClickListener(podcast -> {
            Intent intent = new Intent(getContext(), PlayerActivity.class);
            intent.putExtra("stream_title", podcast.getTitle());
            intent.putExtra("stream_url", podcast.getAudioUrl());
            intent.putExtra("stream_image", podcast.getThumbnailUrl());
            intent.putExtra("stream_source", podcast.getSourceName());
            intent.putExtra("is_live", false);
            startActivity(intent);
        });
        binding.podcastsRecycler.setAdapter(podcastAdapter);
        binding.podcastsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.primary, null));
        binding.swipeRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.surface_medium, null));
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void observeData() {
        viewModel.getStreams().observe(getViewLifecycleOwner(), streams -> {
            if (streams != null) streamAdapter.setStreams(streams);
        });
        viewModel.getPodcasts().observe(getViewLifecycleOwner(), podcasts -> {
            if (podcasts != null) podcastAdapter.setPodcasts(podcasts);
        });
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading != null && isLoading);
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
