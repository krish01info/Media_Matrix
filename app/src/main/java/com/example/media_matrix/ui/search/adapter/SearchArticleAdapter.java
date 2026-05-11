package com.example.media_matrix.ui.search.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.media_matrix.databinding.ItemSearchArticleBinding;
import com.example.media_matrix.domain.model.Article;
import java.util.ArrayList;
import java.util.List;

public class SearchArticleAdapter extends RecyclerView.Adapter<SearchArticleAdapter.ViewHolder> {
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

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSearchArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.bind(articles.get(position)); }
    @Override public int getItemCount() { return articles.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSearchArticleBinding binding;
        ViewHolder(ItemSearchArticleBinding binding) { super(binding.getRoot()); this.binding = binding; }

        void bind(Article article) {
            binding.articleTitle.setText(article.getTitle());
            binding.articleSource.setText(article.getSourceName());
            binding.articleTime.setText(article.getTimeAgo());
            
            Glide.with(binding.getRoot().getContext())
                    .load(article.getImageUrl())
                    .centerCrop()
                    .into(binding.articleImage);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onArticleClick(article);
            });
        }
    }
}
