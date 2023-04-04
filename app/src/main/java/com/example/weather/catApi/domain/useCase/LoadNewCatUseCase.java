package com.example.weather.catApi.domain.useCase;

import android.os.Handler;

import com.example.weather.catApi.domain.repositories.CatRemoteDataSource;
import com.example.weather.core.newtwork.callbacks.RequestFileListener;

public class LoadNewCatUseCase {
    private final CatRemoteDataSource repository;

    public LoadNewCatUseCase(CatRemoteDataSource repository) {
        this.repository = repository;
    }

    public void invoke(Handler handler, RequestFileListener listener) {
        repository.getCat(handler, listener);
    }
}
