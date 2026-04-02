package com.example.media_matrix.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media_matrix.databinding.ItemTopChartBinding;
import com.example.media_matrix.domain.model.Article;

import java.util.ArrayList;
import java.util.List;

public class TopChartsAdapter extends RecyclerView.Adapter<TopChartsAdapter.ViewHolder> {

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
        ItemTopChartBinding binding = ItemTopChartBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(articles.get(position), position + 1);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTopChartBinding binding;

        ViewHolder(ItemTopChartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Article article, int rank) {
            binding.chartRank.setText(String.format("%02d", rank));
            binding.chartTitle.setText(article.getTitle());
            binding.chartMeta.setText(article.getFormattedInteractions() + " Interactions • " + article.getTimeAgo());

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onArticleClick(article);
            });
        }
    }
}
