package com.example.media_matrix.ui.today.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.media_matrix.databinding.ItemDevelopingBinding;
import com.example.media_matrix.domain.model.Article;
import java.util.ArrayList;
import java.util.List;

public class DevelopingAdapter extends RecyclerView.Adapter<DevelopingAdapter.ViewHolder> {
    private List<Article> articles = new ArrayList<>();
    private OnArticleClickListener listener;

    public interface OnArticleClickListener { void onArticleClick(Article article); }
    public void setOnArticleClickListener(OnArticleClickListener l) { this.listener = l; }

    public void setArticles(List<Article> articles) {
        this.articles = articles != null ? articles : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDevelopingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.bind(articles.get(position)); }
    @Override public int getItemCount() { return articles.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDevelopingBinding binding;
        ViewHolder(ItemDevelopingBinding binding) { super(binding.getRoot()); this.binding = binding; }

        void bind(Article article) {
            binding.developingTitle.setText(article.getTitle());
            binding.developingSummary.setText(article.getSummary() != null ? article.getSummary() : "");
            binding.developingTime.setText(article.getTimeAgo());
            binding.getRoot().setOnClickListener(v -> { if (listener != null) listener.onArticleClick(article); });
        }
    }
}
