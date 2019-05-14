package com.whaleshark.basicarch.api

import com.whaleshark.basicarch.model.RecipeSearchResult

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodPuppyService {
    @GET("api/")
    fun listRepos(@Query("q") query: String): Call<RecipeSearchResult>
}
