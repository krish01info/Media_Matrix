package com.example.media_matrix.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.media_matrix.data.remote.response.SearchResponse;
import com.example.media_matrix.data.repository.SearchRepository;
import com.example.media_matrix.domain.model.Category;
import com.example.media_matrix.domain.model.Reporter;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private final SearchRepository repository;
    private MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private MutableLiveData<List<Reporter>> reporters = new MutableLiveData<>();
    private MutableLiveData<SearchResponse> searchResults = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSearching = new MutableLiveData<>(false);

    public SearchViewModel() {
        repository = new SearchRepository();
        loadCategories();
        loadReporters();
    }

    public void loadCategories() {
        repository.getCategories().observeForever(data -> { if (data != null) categories.setValue(data); });
    }

    public void loadReporters() {
        repository.getReporters(false).observeForever(data -> { if (data != null) reporters.setValue(data); });
    }

    public void search(String query, String scope, boolean verifiedOnly) {
        isSearching.setValue(true);
        repository.search(query, scope, verifiedOnly).observeForever(result -> {
            isSearching.setValue(false);
            searchResults.setValue(result);
        });
    }

    public LiveData<List<Category>> getCategories() { return categories; }
    public LiveData<List<Reporter>> getReporters() { return reporters; }
    public LiveData<SearchResponse> getSearchResults() { return searchResults; }
    public LiveData<Boolean> getIsSearching() { return isSearching; }
}
