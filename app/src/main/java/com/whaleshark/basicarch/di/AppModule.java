package com.whaleshark.basicarch.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.whaleshark.basicarch.api.FoodPuppyService;
import com.whaleshark.basicarch.data.RecipeDb;
import com.whaleshark.basicarch.data.RecipeSearchResultDao;
import com.whaleshark.basicarch.util.LiveDataCallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author stipton
 */

@Module(includes = ViewModelModule.class)
class AppModule {

    @Provides
    FoodPuppyService provideFoodPuppyService() {
        return new Retrofit.Builder()
                .baseUrl("http://www.recipepuppy.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(FoodPuppyService.class);
    }

    @Provides
    RecipeDb provideRecipeDb(Application application) {
        return Room.databaseBuilder(application, RecipeDb.class, "recipe.db").build();
    }

    @Provides
    RecipeSearchResultDao provideRecipeSearchResultDao(RecipeDb recipeDb) {
        return recipeDb.getSearchResultDao();
    }
}
