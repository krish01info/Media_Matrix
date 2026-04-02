package com.example.media_matrix.ui.today.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.media_matrix.databinding.ItemRegionalChartBinding;
import com.example.media_matrix.domain.model.RegionalChart;
import java.util.ArrayList;
import java.util.List;

public class RegionalChartAdapter extends RecyclerView.Adapter<RegionalChartAdapter.ViewHolder> {
    private List<RegionalChart> charts = new ArrayList<>();

    public void setCharts(List<RegionalChart> charts) {
        this.charts = charts != null ? charts : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRegionalChartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.bind(charts.get(position)); }
    @Override public int getItemCount() { return charts.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRegionalChartBinding binding;
        ViewHolder(ItemRegionalChartBinding binding) { super(binding.getRoot()); this.binding = binding; }

        void bind(RegionalChart chart) {
            binding.regionalRank.setText(chart.getFormattedRank());
            binding.regionalTitle.setText(chart.getTitle());
            binding.regionalMetric.setText(chart.getMetricLabel() != null ? chart.getMetricLabel() : "");
        }
    }
}
