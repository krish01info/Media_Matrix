package com.example.media_matrix.ui.today.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemMorningBriefBinding;
import com.example.media_matrix.domain.model.Article;
import java.util.ArrayList;
import java.util.List;

public class MorningBriefAdapter extends RecyclerView.Adapter<MorningBriefAdapter.ViewHolder> {
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
        return new ViewHolder(ItemMorningBriefBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.bind(articles.get(position)); }
    @Override public int getItemCount() { return articles.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMorningBriefBinding binding;
        ViewHolder(ItemMorningBriefBinding binding) { super(binding.getRoot()); this.binding = binding; }

        void bind(Article article) {
            binding.briefTitle.setText(article.getTitle());
            binding.briefSummary.setText(article.getSummary() != null ? article.getSummary() : "");
            binding.briefCategory.setText(article.getCategoryName());
            if (article.getThumbnailUrl() != null) {
                Glide.with(binding.getRoot().getContext()).load(article.getThumbnailUrl()).placeholder(R.color.surface_light).into(binding.briefImage);
            }
            binding.getRoot().setOnClickListener(v -> { if (listener != null) listener.onArticleClick(article); });
        }
    }
}
