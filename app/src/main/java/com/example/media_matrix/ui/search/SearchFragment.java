package com.example.media_matrix.ui.search;

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
import com.example.media_matrix.ui.search.adapter.CategoryAdapter;
import com.example.media_matrix.ui.search.adapter.ReporterAdapter;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private ReporterAdapter reporterAdapter;

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
        categoryAdapter = new CategoryAdapter();
        binding.categoriesRecycler.setAdapter(categoryAdapter);
        binding.categoriesRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        reporterAdapter = new ReporterAdapter();
        binding.reportersRecycler.setAdapter(reporterAdapter);
        binding.reportersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
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
        if (query.isEmpty()) return;

        String scope = binding.chipGlobal.isChecked() ? "global" : "national";
        boolean verified = binding.chipVerified.isChecked();
        viewModel.search(query, scope, verified);

        // Show results, hide browse
        binding.searchResultsRecycler.setVisibility(View.VISIBLE);
        binding.categoriesTitle.setVisibility(View.GONE);
        binding.categoriesRecycler.setVisibility(View.GONE);
    }

    private void observeData() {
        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) categoryAdapter.setCategories(categories);
        });
        viewModel.getReporters().observe(getViewLifecycleOwner(), reporters -> {
            if (reporters != null) reporterAdapter.setReporters(reporters);
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
