package com.whaleshark.basicarch.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.whaleshark.basicarch.data.SearchRepository;
import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.util.Objects;

import java.util.List;

/**
 * @author stipton
 */

public class SearchViewModel extends ViewModel {

    private SearchRepository searchRepository = new SearchRepository();
    private MutableLiveData<String> query = new MutableLiveData<>();
    private LiveData<List<Recipe>> results;

    public SearchViewModel() {
        results = Transformations.switchMap(query, searchQuery -> searchRepository.searchRecipes(searchQuery));
    }

    public LiveData<List<Recipe>> getResults() {
        return results;
    }

    public void setQuery(String query) {
        if (!Objects.equals(this.query.getValue(), query)) {
            this.query.setValue(query);
        }
    }
}
