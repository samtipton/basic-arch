package com.whaleshark.basicarch.model

data class RecipeSearchResult(
    var title: String?,
    var version: Double?,
    var href: String?,
    var results: List<Recipe>?
)
