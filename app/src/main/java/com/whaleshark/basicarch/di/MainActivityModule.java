package com.whaleshark.basicarch.di;

import com.whaleshark.basicarch.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author stipton
 */

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}

