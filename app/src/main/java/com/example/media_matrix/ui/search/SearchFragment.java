package com.example.media_matrix.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.media_matrix.databinding.FragmentSearchBinding;
import com.example.media_matrix.domain.model.Article;
import com.example.media_matrix.ui.article.ArticleDetailActivity;
import com.example.media_matrix.ui.search.adapter.CategoryAdapter;
import com.example.media_matrix.ui.search.adapter.ReporterAdapter;
import com.example.media_matrix.ui.search.adapter.SearchArticleAdapter;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private ReporterAdapter reporterAdapter;
    private SearchArticleAdapter searchArticleAdapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        setupAdapters();
        setupSearch();
        observeData();
    }

    private void setupAdapters() {
        // Categories
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setOnCategoryClickListener(category -> {
            binding.searchInput.setText(category.getName());
            viewModel.searchByCategory(category.getSlug());
            showSearchResults();
        });
        binding.categoriesRecycler.setAdapter(categoryAdapter);
        binding.categoriesRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Reporters
        reporterAdapter = new ReporterAdapter();
        reporterAdapter.setOnReporterClickListener(reporter -> {
            binding.searchInput.setText(reporter.getName());
            viewModel.search(reporter.getName(), "global", true);
            showSearchResults();
        });
        binding.reportersRecycler.setAdapter(reporterAdapter);
        binding.reportersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // Search Results
        searchArticleAdapter = new SearchArticleAdapter();
        searchArticleAdapter.setOnArticleClickListener(this::openArticle);
        binding.searchResultsRecycler.setAdapter(searchArticleAdapter);
        binding.searchResultsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupSearch() {
        binding.searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });
    }

    private void performSearch() {
        String query = binding.searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            hideSearchResults();
            return;
        }

        String scope = binding.chipGlobal.isChecked() ? "global" : "national";
        boolean verified = binding.chipVerified.isChecked();
        viewModel.search(query, scope, verified);

        showSearchResults();
    }

    private void showSearchResults() {
        binding.searchResultsRecycler.setVisibility(View.VISIBLE);
        binding.categoriesTitle.setVisibility(View.GONE);
        binding.categoriesRecycler.setVisibility(View.GONE);
    }

    private void hideSearchResults() {
        binding.searchResultsRecycler.setVisibility(View.GONE);
        binding.categoriesTitle.setVisibility(View.VISIBLE);
        binding.categoriesRecycler.setVisibility(View.VISIBLE);
    }

    private void observeData() {
        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) categoryAdapter.setCategories(categories);
        });
        
        viewModel.getReporters().observe(getViewLifecycleOwner(), reporters -> {
            if (reporters != null) reporterAdapter.setReporters(reporters);
        });

        viewModel.getSearchResults().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getArticles() != null) {
                searchArticleAdapter.setArticles(response.getArticles());
            }
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

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
