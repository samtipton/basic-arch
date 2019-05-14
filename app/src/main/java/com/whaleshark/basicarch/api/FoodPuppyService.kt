package com.whaleshark.basicarch.api

import com.whaleshark.basicarch.model.RecipeSearchResult
import com.whaleshark.basicarch.util.LiveDataCallAdapterFactory

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodPuppyService {
    @GET("api/")
    fun searchRecipes(@Query("q") query: String): Call<RecipeSearchResult>

    object Factory {
        fun create(): FoodPuppyService {
            return Retrofit.Builder()
                    .baseUrl("http://www.recipepuppy.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()
                    .create(FoodPuppyService::class.java!!)
        }
    }
}
