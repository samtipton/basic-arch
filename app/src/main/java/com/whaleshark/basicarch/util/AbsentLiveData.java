package com.whaleshark.basicarch.util;

import android.arch.lifecycle.LiveData;

/**
 * @author stipton
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }
    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
