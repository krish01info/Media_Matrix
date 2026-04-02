package com.example.media_matrix.ui.search.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.media_matrix.databinding.ItemCategoryCardBinding;
import com.example.media_matrix.domain.model.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categories = new ArrayList<>();
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener { void onCategoryClick(Category category); }
    public void setOnCategoryClickListener(OnCategoryClickListener l) { this.listener = l; }

    public void setCategories(List<Category> categories) {
        this.categories = categories != null ? categories : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCategoryCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.bind(categories.get(position)); }
    @Override public int getItemCount() { return categories.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryCardBinding binding;
        ViewHolder(ItemCategoryCardBinding binding) { super(binding.getRoot()); this.binding = binding; }

        void bind(Category category) {
            binding.categoryName.setText(category.getName());

            // Apply gradient from backend
            try {
                int startColor = Color.parseColor(category.getGradientStart() != null ? category.getGradientStart() : "#1E3A5F");
                int endColor = Color.parseColor(category.getGradientEnd() != null ? category.getGradientEnd() : "#0F1F33");
                GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{startColor, endColor});
                gradient.setCornerRadius(48f);
                binding.categoryGradient.setBackground(gradient);
            } catch (Exception e) {
                binding.categoryGradient.setBackgroundColor(Color.parseColor("#1E3A5F"));
            }

            binding.getRoot().setOnClickListener(v -> { if (listener != null) listener.onCategoryClick(category); });
        }
    }
}
