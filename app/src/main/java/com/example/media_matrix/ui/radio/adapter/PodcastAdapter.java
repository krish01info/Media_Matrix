package com.example.media_matrix.ui.radio.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemPodcastBinding;
import com.example.media_matrix.domain.model.Podcast;
import java.util.ArrayList;
import java.util.List;

public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.ViewHolder> {
    private List<Podcast> podcasts = new ArrayList<>();
    private OnPodcastClickListener listener;

    public interface OnPodcastClickListener { void onPodcastClick(Podcast podcast); }
    public void setOnPodcastClickListener(OnPodcastClickListener l) { this.listener = l; }

    public void setPodcasts(List<Podcast> podcasts) {
        this.podcasts = podcasts != null ? podcasts : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPodcastBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.bind(podcasts.get(position)); }
    @Override public int getItemCount() { return podcasts.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPodcastBinding binding;
        ViewHolder(ItemPodcastBinding binding) { super(binding.getRoot()); this.binding = binding; }

        void bind(Podcast podcast) {
            binding.podcastTitle.setText(podcast.getTitle());
            binding.podcastMeta.setText(podcast.getSourceName() + " • " + podcast.getFormattedDuration());
            if (podcast.getThumbnailUrl() != null) {
                Glide.with(binding.getRoot().getContext()).load(podcast.getThumbnailUrl()).placeholder(R.color.surface_light).into(binding.podcastImage);
            }
            binding.getRoot().setOnClickListener(v -> { if (listener != null) listener.onPodcastClick(podcast); });
        }
    }
}
