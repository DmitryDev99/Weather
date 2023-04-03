package com.example.weather.catApi.domain;

import android.os.Handler;

import com.example.weather.core.newtwork.callbacks.RequestFileListener;

import java.io.File;

public interface CatImageRemoteDataSource {
    void getImageCat(
        final File fileDir,
        final String fileName,
        final Handler handler,
        final RequestFileListener listener
    );
}
