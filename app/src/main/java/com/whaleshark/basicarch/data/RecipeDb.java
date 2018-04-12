package com.whaleshark.basicarch.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.whaleshark.basicarch.model.Recipe;
import com.whaleshark.basicarch.model.RecipeSearchResult;

/**
 * @author stipton
 */


@Database(entities = {RecipeSearchResult.class, Recipe.class}, version = 1)
public abstract class RecipeDb extends RoomDatabase {

    public abstract RecipeSearchResultDao getSearchResultDao();
    public abstract RecipeDao getRecipeDao();
}
