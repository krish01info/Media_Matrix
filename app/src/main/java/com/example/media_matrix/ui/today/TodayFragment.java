package com.example.media_matrix.ui.today;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.media_matrix.R;
import com.example.media_matrix.databinding.FragmentTodayBinding;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.ui.article.ArticleDetailActivity;
import com.example.media_matrix.ui.home.adapter.TrendingAdapter;
import com.example.media_matrix.ui.today.adapter.DevelopingAdapter;
import com.example.media_matrix.ui.today.adapter.MorningBriefAdapter;
import com.example.media_matrix.ui.today.adapter.RegionalChartAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodayFragment extends Fragment {
    private FragmentTodayBinding binding;
    private TodayViewModel viewModel;
    private MorningBriefAdapter morningBriefAdapter;
    private DevelopingAdapter developingAdapter;
    private TrendingAdapter trendingAdapter;
    private RegionalChartAdapter regionalChartAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTodayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TodayViewModel.class);

        // Set current date
        binding.todayDate.setText(new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(new Date()));

        setupAdapters();
        setupSwipeRefresh();
        observeData();
    }

    private void setupAdapters() {
        morningBriefAdapter = new MorningBriefAdapter();
        morningBriefAdapter.setOnArticleClickListener(this::openArticle);
        binding.morningBriefRecycler.setAdapter(morningBriefAdapter);
        binding.morningBriefRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        developingAdapter = new DevelopingAdapter();
        developingAdapter.setOnArticleClickListener(this::openArticle);
        binding.developingRecycler.setAdapter(developingAdapter);
        binding.developingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        trendingAdapter = new TrendingAdapter();
        trendingAdapter.setOnArticleClickListener(this::openArticle);
        binding.todayTrendingRecycler.setAdapter(trendingAdapter);
        binding.todayTrendingRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        regionalChartAdapter = new RegionalChartAdapter();
        binding.regionalChartsRecycler.setAdapter(regionalChartAdapter);
        binding.regionalChartsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.primary, null));
        binding.swipeRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.surface_medium, null));
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void observeData() {
        viewModel.getMorningBrief().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                morningBriefAdapter.setArticles(articles);
                binding.morningBriefCount.setText(articles.size() + " Stories");
            }
        });
        viewModel.getDeveloping().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) developingAdapter.setArticles(articles);
        });
        viewModel.getTrending().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) trendingAdapter.setArticles(articles);
        });
        viewModel.getRegionalCharts().observe(getViewLifecycleOwner(), charts -> {
            if (charts != null) regionalChartAdapter.setCharts(charts);
        });
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading != null && isLoading);
        });
    }

    private void openArticle(Article article) {
        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
        intent.putExtra("article_uuid", article.getUuid());
        intent.putExtra("article_title", article.getTitle());
        intent.putExtra("article_image", article.getImageUrl());
        intent.putExtra("article_source", article.getSourceName());
        intent.putExtra("article_summary", article.getSummary());
        startActivity(intent);
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
