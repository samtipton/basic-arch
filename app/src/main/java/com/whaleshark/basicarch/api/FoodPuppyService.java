package com.whaleshark.basicarch.api;

import com.whaleshark.basicarch.model.RecipeSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodPuppyService {
    @GET("api/")
    Call<RecipeSearchResult> searchRecipes(@Query("q") String query);
}
