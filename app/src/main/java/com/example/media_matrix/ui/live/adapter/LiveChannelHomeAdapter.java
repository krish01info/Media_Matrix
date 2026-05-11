package com.example.media_matrix.ui.live.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemLiveChannelHomeBinding;
import com.example.media_matrix.domain.model.LiveChannel;

import java.util.ArrayList;
import java.util.List;

/** Compact adapter for the "Live Now" horizontal strip on the Home screen. */
public class LiveChannelHomeAdapter extends RecyclerView.Adapter<LiveChannelHomeAdapter.ViewHolder> {

    private List<LiveChannel> channels = new ArrayList<>();
    private OnChannelClickListener listener;

    public interface OnChannelClickListener {
        void onChannelClick(LiveChannel channel);
    }

    public void setOnChannelClickListener(OnChannelClickListener l) {
        this.listener = l;
    }

    public void setChannels(List<LiveChannel> channels) {
        this.channels = channels != null ? channels : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemLiveChannelHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(channels.get(position));
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLiveChannelHomeBinding binding;

        ViewHolder(ItemLiveChannelHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(LiveChannel channel) {
            binding.homeChannelName.setText(channel.getName());

            if (channel.getLogoUrl() != null && !channel.getLogoUrl().isEmpty()) {
                Glide.with(binding.getRoot().getContext())
                        .load(channel.getLogoUrl())
                        .placeholder(R.color.surface_light)
                        .error(R.drawable.ic_live_tv)
                        .into(binding.homeChannelLogo);
            } else {
                binding.homeChannelLogo.setImageResource(R.drawable.ic_live_tv);
            }

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onChannelClick(channel);
            });
        }
    }
}
