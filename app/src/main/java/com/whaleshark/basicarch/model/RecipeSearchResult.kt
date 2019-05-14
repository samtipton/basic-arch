package com.whaleshark.basicarch.model

data class RecipeSearchResult(
    var title: String? = null,
    var version: Double? = null,
    var href: String? = null,
    var results: List<Recipe>? = null
)
