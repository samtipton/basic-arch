package com.whaleshark.basicarch.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

import com.whaleshark.basicarch.data.SearchRepository
import com.whaleshark.basicarch.model.Recipe
import com.whaleshark.basicarch.util.Objects

/**
 * @author stipton
 */

class SearchViewModel : ViewModel() {

    private val searchRepository = SearchRepository()
    private val query = MutableLiveData<String>()
    val results: LiveData<List<Recipe>>

    init {
        results = Transformations.switchMap(query) { searchQuery -> searchRepository.searchRecipes(searchQuery) }
    }

    fun setQuery(query: String) {
        if (!Objects.equals(this.query.value, query)) {
            this.query.value = query
        }
    }
}
