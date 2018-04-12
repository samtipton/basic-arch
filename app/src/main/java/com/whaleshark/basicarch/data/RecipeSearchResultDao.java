package com.whaleshark.basicarch.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.whaleshark.basicarch.model.RecipeSearchResult;

/**
 * @author stipton
 */

@Dao
abstract public class RecipeSearchResultDao {

    @Query("SELECT * from RecipeSearchResult WHERE queryString = :query")
    public abstract LiveData<RecipeSearchResult> loadResult(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RecipeSearchResult item);
}
