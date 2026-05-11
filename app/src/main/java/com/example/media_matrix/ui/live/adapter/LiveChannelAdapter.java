package com.example.media_matrix.ui.live.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemLiveChannelBinding;
import com.example.media_matrix.domain.model.LiveChannel;

import java.util.ArrayList;
import java.util.List;

public class LiveChannelAdapter extends RecyclerView.Adapter<LiveChannelAdapter.ViewHolder> {

    private List<LiveChannel> channels = new ArrayList<>();
    private OnChannelClickListener listener;
    private String selectedChannelId = null;

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

    public void setSelectedChannelId(String id) {
        this.selectedChannelId = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemLiveChannelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        private final ItemLiveChannelBinding binding;

        ViewHolder(ItemLiveChannelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(LiveChannel channel) {
            binding.channelName.setText(channel.getName());
            binding.channelCountry.setText(channel.getCountry() + " • " + channel.getLanguage());
            binding.channelCategory.setText(channel.getCategory());

            // Show / hide live badge
            binding.channelLiveBadge.setVisibility(channel.isLive() ? View.VISIBLE : View.GONE);

            // Load logo with Glide
            if (channel.getLogoUrl() != null && !channel.getLogoUrl().isEmpty()) {
                Glide.with(binding.getRoot().getContext())
                        .load(channel.getLogoUrl())
                        .placeholder(R.color.surface_light)
                        .error(R.drawable.ic_live_tv)
                        .into(binding.channelLogo);
            } else {
                binding.channelLogo.setImageResource(R.drawable.ic_live_tv);
            }

            // Highlight selected channel
            boolean isSelected = channel.getId().equals(selectedChannelId);
            binding.selectionIndicator.setVisibility(isSelected ? View.VISIBLE : View.GONE);
            binding.channelCard.setCardBackgroundColor(
                    binding.getRoot().getContext().getColor(
                            isSelected ? R.color.surface_elevated : R.color.surface_medium));

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onChannelClick(channel);
            });
        }
    }
}
