package com.whaleshark.basicarch.di;

import android.app.Application;

import com.whaleshark.basicarch.RecipeApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * @author stipton
 */

@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class, MainActivityModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(RecipeApp recipeApp);
}
