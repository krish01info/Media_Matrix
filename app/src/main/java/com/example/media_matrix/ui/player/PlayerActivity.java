package com.example.media_matrix.ui.player;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {
    private ActivityPlayerBinding binding;
    private ExoPlayer player;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("stream_title");
        String streamUrl = getIntent().getStringExtra("stream_url");
        String imageUrl = getIntent().getStringExtra("stream_image");
        String source = getIntent().getStringExtra("stream_source");
        boolean isLive = getIntent().getBooleanExtra("is_live", false);

        binding.playerTitle.setText(title != null ? title : "");
        binding.playerSource.setText(source != null ? source : "");
        binding.playerLiveBadge.setVisibility(isLive ? View.VISIBLE : View.GONE);

        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).placeholder(R.color.surface_medium).into(binding.playerArt);
        }

        binding.btnBack.setOnClickListener(v -> finish());

        // Initialize ExoPlayer
        if (streamUrl != null) {
            player = new ExoPlayer.Builder(this).build();
            MediaItem mediaItem = MediaItem.fromUri(streamUrl);
            player.setMediaItem(mediaItem);
            player.prepare();
        }

        binding.btnPlayPause.setOnClickListener(v -> {
            if (player == null) return;
            if (isPlaying) {
                player.pause();
                binding.btnPlayPause.setImageResource(R.drawable.ic_play);
            } else {
                player.play();
                binding.btnPlayPause.setImageResource(R.drawable.ic_pause);
            }
            isPlaying = !isPlaying;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
