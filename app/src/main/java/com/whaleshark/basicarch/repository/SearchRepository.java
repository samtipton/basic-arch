package com.whaleshark.basicarch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.whaleshark.basicarch.AppExecutors;
import com.whaleshark.basicarch.api.FoodPuppyService;
import com.whaleshark.basicarch.data.RecipeDao;
import com.whaleshark.basicarch.data.RecipeDb;
import com.whaleshark.basicarch.data.RecipeSearchResultDao;
import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.model.RecipeSearchResponse;
import com.whaleshark.basicarch.model.RecipeSearchResult;
import com.whaleshark.basicarch.util.RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

/**
 * @author stipton
 */

@Singleton
public class SearchRepository {

    private AppExecutors appExecutors;
    private FoodPuppyService foodPuppyService;
    private RecipeSearchResultDao recipeSearchResultDao;
    private RecipeDao recipeDao;

    private RateLimiter<String> searchRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    public SearchRepository(AppExecutors appExecutors, FoodPuppyService foodPuppyService, RecipeDb recipeDb) {
        this.appExecutors = appExecutors;
        this.foodPuppyService = foodPuppyService;
        this.recipeSearchResultDao = recipeDb.getSearchResultDao();
        this.recipeDao = recipeDb.getRecipeDao();
    }

    public LiveData<List<Recipe>> searchRecipes(final String query) {

        return new NetworkBoundResource<List<Recipe>, RecipeSearchResponse>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull RecipeSearchResponse item) {
                ArrayList<Integer> ids = new ArrayList<>(item.results.size());

                for (Recipe recipe : item.results) {
                    int id = recipe.title.hashCode();
                    ids.add(id);
                    recipe.id = id;
                }

                recipeDao.insert(item.results);
                recipeSearchResultDao.insert(new RecipeSearchResult(query, ids));
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Recipe> data) {
                return data == null || data.isEmpty() || searchRateLimit.shouldFetch(query);
            }

            @NonNull
            @Override
            protected LiveData<List<Recipe>> loadFromDb() {

                MediatorLiveData<List<Recipe>> result = new MediatorLiveData<>();

                result.addSource(recipeSearchResultDao.loadResult(query), searchResult -> {

                    if (searchResult != null) {
                        result.addSource(recipeDao.loadByIds(searchResult.recipeIds), recipes -> {
                            result.postValue(recipes);
                        });
                    }
                    else {
                        result.postValue(null);
                    }
                });

                return result;
            }

            @NonNull
            @Override
            protected Call<RecipeSearchResponse> createCall() {
                return foodPuppyService.searchRecipes(query);
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
                searchRateLimit.reset(query);
            }
        }.asLiveData();
    }
}
