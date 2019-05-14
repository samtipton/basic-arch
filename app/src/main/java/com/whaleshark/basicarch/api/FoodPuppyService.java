package com.whaleshark.basicarch.api;

import com.whaleshark.basicarch.model.RecipeSearchResult;
import com.whaleshark.basicarch.util.LiveDataCallAdapterFactory;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodPuppyService {
    @GET("api/")
    Call<RecipeSearchResult> searchRecipes(@Query("q") String query);

    class Factory {
        public static FoodPuppyService create() {
            return new Retrofit.Builder()
                    .baseUrl("http://www.recipepuppy.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .build()
                    .create(FoodPuppyService.class);
        }
    }
}
