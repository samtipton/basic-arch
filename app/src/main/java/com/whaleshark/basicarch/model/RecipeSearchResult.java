package com.whaleshark.basicarch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.whaleshark.basicarch.data.RecipeAppTypeConverters;

import java.util.List;

/**
 * @author stipton
 */

@Entity
@TypeConverters(RecipeAppTypeConverters.class)
public class RecipeSearchResult {

    public RecipeSearchResult(@NonNull String queryString, List<Integer> recipeIds) {
        this.queryString = queryString;
        this.recipeIds = recipeIds;
    }

    @NonNull
    @PrimaryKey
    public String queryString;
    public List<Integer> recipeIds;
}
