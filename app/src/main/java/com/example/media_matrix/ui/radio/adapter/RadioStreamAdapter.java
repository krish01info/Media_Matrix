package com.example.media_matrix.ui.radio.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemRadioStreamBinding;
import com.example.media_matrix.domain.model.RadioStream;
import java.util.ArrayList;
import java.util.List;

public class RadioStreamAdapter extends RecyclerView.Adapter<RadioStreamAdapter.ViewHolder> {
    private List<RadioStream> streams = new ArrayList<>();
    private OnStreamClickListener listener;

    public interface OnStreamClickListener { void onStreamClick(RadioStream stream); }
    public void setOnStreamClickListener(OnStreamClickListener l) { this.listener = l; }

    public void setStreams(List<RadioStream> streams) {
        this.streams = streams != null ? streams : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRadioStreamBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.bind(streams.get(position)); }
    @Override public int getItemCount() { return streams.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRadioStreamBinding binding;
        ViewHolder(ItemRadioStreamBinding binding) { super(binding.getRoot()); this.binding = binding; }

        void bind(RadioStream stream) {
            binding.streamTitle.setText(stream.getTitle());
            binding.streamListeners.setText(stream.getFormattedListeners());
            binding.streamLiveBadge.setVisibility(stream.isLive() ? View.VISIBLE : View.GONE);
            if (stream.getThumbnailUrl() != null) {
                Glide.with(binding.getRoot().getContext()).load(stream.getThumbnailUrl()).placeholder(R.color.surface_light).into(binding.streamImage);
            }
            binding.getRoot().setOnClickListener(v -> { if (listener != null) listener.onStreamClick(stream); });
        }
    }
}
