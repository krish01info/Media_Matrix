package com.example.media_matrix.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemTrendingArticleBinding;
import com.example.media_matrix.domain.model.Article;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {

    private List<Article> articles = new ArrayList<>();
    private OnArticleClickListener listener;

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    public void setOnArticleClickListener(OnArticleClickListener listener) {
        this.listener = listener;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles != null ? articles : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrendingArticleBinding binding = ItemTrendingArticleBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTrendingArticleBinding binding;

        ViewHolder(ItemTrendingArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Article article) {
            binding.trendingTitle.setText(article.getTitle());

            double score = article.getTruthScore();
            binding.trendingTruthScore.setText(String.format("%.0f%% VERIFIED", score));

            if (article.getImageUrl() != null) {
                Glide.with(binding.getRoot().getContext())
                        .load(article.getImageUrl())
                        .transform(new CenterCrop())
                        .placeholder(R.color.surface_light)
                        .into(binding.trendingImage);
            }

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onArticleClick(article);
            });
        }
    }
}
