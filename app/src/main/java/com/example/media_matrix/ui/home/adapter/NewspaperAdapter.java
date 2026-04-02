package com.example.media_matrix.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemNewspaperBinding;
import com.example.media_matrix.domain.model.Newspaper;

import java.util.ArrayList;
import java.util.List;

public class NewspaperAdapter extends RecyclerView.Adapter<NewspaperAdapter.ViewHolder> {

    private List<Newspaper> newspapers = new ArrayList<>();

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
            binding.newspaperName.setText(newspaper.getSourceShortName());

            if (newspaper.getCoverImageUrl() != null) {
                Glide.with(binding.getRoot().getContext())
                        .load(newspaper.getCoverImageUrl())
                        .transform(new CenterCrop())
                        .placeholder(R.color.surface_light)
                        .into(binding.newspaperCover);
            }
        }
    }
}
