package com.whaleshark.basicarch.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.whaleshark.basicarch.model.Recipe;

import java.util.List;

/**
 * @author stipton
 */

@Dao
abstract public class RecipeDao {

    @Query("SELECT * from recipe WHERE id IN (:recipeIds)")
    public abstract LiveData<List<Recipe>> loadByIds(List<Integer> recipeIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<Recipe> results);

    @Query("SELECT * from recipe WHERE id = :id")
    public abstract LiveData<Recipe> loadById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Recipe recipe);
}
