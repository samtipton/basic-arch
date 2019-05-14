package com.whaleshark.basicarch.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import com.whaleshark.basicarch.api.FoodPuppyService
import com.whaleshark.basicarch.model.Recipe
import com.whaleshark.basicarch.model.RecipeSearchResult

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author stipton
 */

class SearchRepository {

    private val foodPuppyService = FoodPuppyService.Factory.create()

    fun searchRecipes(query: String): LiveData<List<Recipe>> {
        val result = MutableLiveData<List<Recipe>>()

        foodPuppyService.searchRecipes(query).enqueue(object : Callback<RecipeSearchResult> {
            override fun onResponse(call: Call<RecipeSearchResult>, response: Response<RecipeSearchResult>) {
                result.postValue(response.body()!!.results)
            }

            override fun onFailure(call: Call<RecipeSearchResult>, t: Throwable) {
                result.postValue(null)
            }
        })

        return result
    }
}
