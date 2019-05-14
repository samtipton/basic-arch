package com.whaleshark.basicarch.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.whaleshark.basicarch.api.FoodPuppyService;
import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.model.RecipeSearchResult;
import com.whaleshark.basicarch.util.LiveDataCallAdapterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author stipton
 */

public class SearchViewModel extends ViewModel {

    private final FoodPuppyService foodPuppyService = FoodPuppyService.Factory.create();

    private MutableLiveData<String> query = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> resultsMediator = new MediatorLiveData<>();

    public SearchViewModel() {

        resultsMediator.addSource(query, query -> {
            foodPuppyService.searchRecipes(query).enqueue(new Callback<RecipeSearchResult>() {
                @Override
                public void onResponse(Call<RecipeSearchResult> call, Response<RecipeSearchResult> response) {
                    resultsMediator.postValue(response.body() != null ? response.body().results : null);
                }

                @Override
                public void onFailure(Call<RecipeSearchResult> call, Throwable t) {
                    resultsMediator.postValue(null);
                }
            });
        });
    }

    public LiveData<List<Recipe>> getResults() {
        return resultsMediator;
    }

    public void setQuery(String query) {
        if (this.query.getValue() == null || !this.query.getValue().equals(query)) {
            this.query.setValue(query);
        }
    }
}
