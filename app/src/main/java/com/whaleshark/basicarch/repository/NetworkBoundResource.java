package com.whaleshark.basicarch.repository;

/**
 * @author stipton
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.whaleshark.basicarch.AppExecutors;
import com.whaleshark.basicarch.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<ResultType> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(newData));
            }
        });
    }

    @MainThread
    private void setValue(ResultType newValue) {
        result.setValue(newValue);
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        Call<RequestType> apiResponse = createCall();

        MutableLiveData<Response<RequestType>> apiResult = new MutableLiveData<>();

        apiResponse.enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                apiResult.postValue(response);
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                apiResult.postValue(null);
            }
        });

        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(newData));
        result.addSource(apiResult, response -> {
            result.removeSource(apiResult);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(apiResult.getValue().body()));
                    appExecutors.mainThread().execute(() ->
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb(), newData -> setValue(newData))
                    );
                });
            } else {
                onFetchFailed();
                result.addSource(dbSource, newData -> setValue(null));
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<ResultType> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(RequestType response) {
        return response;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();
}
