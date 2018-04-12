package com.whaleshark.basicarch.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.whaleshark.basicarch.api.FoodPuppyService;
import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.model.RecipeSearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author stipton
 */

public class SearchRepository {

    private final FoodPuppyService foodPuppyService = FoodPuppyService.Factory.create();

    public LiveData<List<Recipe>> searchRecipes(String query) {
        MutableLiveData<List<Recipe>> result = new MutableLiveData<>();

        foodPuppyService.searchRecipes(query).enqueue(new Callback<RecipeSearchResult>() {
            @Override
            public void onResponse(Call<RecipeSearchResult> call, Response<RecipeSearchResult> response) {
                result.postValue(response.body().results);
            }

            @Override
            public void onFailure(Call<RecipeSearchResult> call, Throwable t) {
                result.postValue(null);
            }
        });

        return result;
    }
}
