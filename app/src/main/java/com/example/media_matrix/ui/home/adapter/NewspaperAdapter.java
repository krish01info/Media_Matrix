package com.example.media_matrix.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemNewspaperBinding;
import com.example.media_matrix.domain.model.Newspaper;

import java.util.ArrayList;
import java.util.List;

public class NewspaperAdapter extends RecyclerView.Adapter<NewspaperAdapter.ViewHolder> {

    private List<Newspaper> newspapers = new ArrayList<>();
    private OnNewspaperClickListener listener;

    public interface OnNewspaperClickListener {
        void onNewspaperClick(Newspaper newspaper);
    }

    public void setOnNewspaperClickListener(OnNewspaperClickListener l) {
        this.listener = l;
    }

    public void setNewspapers(List<Newspaper> newspapers) {
        this.newspapers = newspapers != null ? newspapers : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewspaperBinding binding = ItemNewspaperBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newspapers.get(position));
    }

    @Override
    public int getItemCount() {
        return newspapers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewspaperBinding binding;

        ViewHolder(ItemNewspaperBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Newspaper newspaper) {
            binding.newspaperName.setText(newspaper.getSourceName());

            // Category tag
            String cat = newspaper.getCategory();
            if (cat != null && !cat.isEmpty()) {
                binding.newspaperCategory.setText(cat);
                binding.newspaperCategory.setVisibility(android.view.View.VISIBLE);
            } else {
                binding.newspaperCategory.setVisibility(android.view.View.GONE);
            }

            // Logo image (logo URL stored in coverImageUrl)
            if (newspaper.getCoverImageUrl() != null && !newspaper.getCoverImageUrl().isEmpty()) {
                Glide.with(binding.getRoot().getContext())
                        .load(newspaper.getCoverImageUrl())
                        .placeholder(R.color.surface_light)
                        .error(R.drawable.ic_newspaper)
                        .into(binding.newspaperCover);
            } else {
                binding.newspaperCover.setImageResource(R.drawable.ic_newspaper);
            }

            // Click — open reader
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onNewspaperClick(newspaper);
            });
        }
    }
}
