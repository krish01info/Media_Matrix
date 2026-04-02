package com.example.media_matrix.ui.search.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.media_matrix.R;
import com.example.media_matrix.databinding.ItemReporterBinding;
import com.example.media_matrix.domain.model.Reporter;
import java.util.ArrayList;
import java.util.List;

public class ReporterAdapter extends RecyclerView.Adapter<ReporterAdapter.ViewHolder> {
    private List<Reporter> reporters = new ArrayList<>();

    public void setReporters(List<Reporter> reporters) {
        this.reporters = reporters != null ? reporters : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemReporterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.bind(reporters.get(position)); }
    @Override public int getItemCount() { return reporters.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemReporterBinding binding;
        ViewHolder(ItemReporterBinding binding) { super(binding.getRoot()); this.binding = binding; }

        void bind(Reporter reporter) {
            binding.reporterName.setText(reporter.getName());
            binding.reporterTitle.setText(reporter.getTitle() != null ? reporter.getTitle() : "");
            binding.reporterTruthScore.setText(reporter.getFormattedTruthScore());
            if (reporter.getAvatarUrl() != null) {
                Glide.with(binding.getRoot().getContext()).load(reporter.getAvatarUrl()).placeholder(R.drawable.ic_launcher_foreground).circleCrop().into(binding.reporterAvatar);
            }
        }
    }
}
