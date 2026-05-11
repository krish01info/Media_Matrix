package com.example.media_matrix.ui.home;

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
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.media_matrix.R;
import com.example.media_matrix.data.local.NewspaperDataSource;
import com.example.media_matrix.databinding.FragmentHomeBinding;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.ui.article.ArticleDetailActivity;
import com.example.media_matrix.ui.home.adapter.FeaturedAdapter;
import com.example.media_matrix.ui.home.adapter.NewspaperAdapter;
import com.example.media_matrix.ui.home.adapter.TopChartsAdapter;
import com.example.media_matrix.ui.home.adapter.TrendingAdapter;
import com.example.media_matrix.ui.newspaper.NewspaperReaderActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private FeaturedAdapter featuredAdapter;
    private TrendingAdapter trendingAdapter;
    private TopChartsAdapter topChartsAdapter;
    private NewspaperAdapter newspaperAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupAdapters();
        setupSwipeRefresh();
        observeData();
    }

    private void setupAdapters() {
        // Featured carousel
        featuredAdapter = new FeaturedAdapter();
        featuredAdapter.setOnArticleClickListener(this::openArticle);
        binding.featuredPager.setAdapter(featuredAdapter);
        binding.featuredPager.setOffscreenPageLimit(3);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(24));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.9f + r * 0.1f);
        });
        binding.featuredPager.setPageTransformer(transformer);

        // Trending horizontal
        trendingAdapter = new TrendingAdapter();
        trendingAdapter.setOnArticleClickListener(this::openArticle);
        binding.trendingRecycler.setAdapter(trendingAdapter);
        binding.trendingRecycler.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Top Charts vertical
        topChartsAdapter = new TopChartsAdapter();
        topChartsAdapter.setOnArticleClickListener(this::openArticle);
        binding.topChartsRecycler.setAdapter(topChartsAdapter);
        binding.topChartsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // Newspapers horizontal — loaded from local curated data source
        newspaperAdapter = new NewspaperAdapter();
        newspaperAdapter.setOnNewspaperClickListener(newspaper -> {
            Intent intent = new Intent(getContext(), NewspaperReaderActivity.class);
            intent.putExtra(NewspaperReaderActivity.EXTRA_NAME, newspaper.getSourceName());
            intent.putExtra(NewspaperReaderActivity.EXTRA_URL, newspaper.getPdfUrl());
            intent.putExtra(NewspaperReaderActivity.EXTRA_LOGO, newspaper.getCoverImageUrl());
            startActivity(intent);
        });
        newspaperAdapter.setNewspapers(NewspaperDataSource.getNewspapers());
        binding.newspapersRecycler.setAdapter(newspaperAdapter);
        binding.newspapersRecycler.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeColors(
                getResources().getColor(com.example.media_matrix.R.color.primary, null));
        binding.swipeRefresh.setProgressBackgroundColorSchemeColor(
                getResources().getColor(com.example.media_matrix.R.color.surface_medium, null));
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void observeData() {
        viewModel.getFeatured().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) featuredAdapter.setArticles(articles);
        });

        viewModel.getTrending().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) trendingAdapter.setArticles(articles);
        });

        viewModel.getTopCharts().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) topChartsAdapter.setArticles(articles);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading != null && isLoading);
            binding.loadingProgress.setVisibility(isLoading != null && isLoading ? View.VISIBLE : View.GONE);
        });
    }

    private void openArticle(Article article) {
        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
        intent.putExtra("article_uuid", article.getUuid());
        intent.putExtra("article_title", article.getTitle());
        intent.putExtra("article_image", article.getImageUrl());
        intent.putExtra("article_source", article.getSourceName());
        intent.putExtra("article_summary", article.getSummary());
        intent.putExtra("article_time", article.getTimeAgo());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
