package com.whaleshark.basicarch.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.whaleshark.basicarch.repository.SearchRepository;
import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.util.AbsentLiveData;
import com.whaleshark.basicarch.util.Objects;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author stipton
 */

@Singleton
public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> query = new MutableLiveData<>();
    private LiveData<List<Recipe>> results;

    @Inject
    SearchViewModel(SearchRepository searchRepository) {
        results = Transformations.switchMap(query, searchQuery -> {
                if (TextUtils.isEmpty(searchQuery)) {
                    return AbsentLiveData.create();
                }
                else {
                    return searchRepository.searchRecipes(searchQuery);
                }
        });
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
