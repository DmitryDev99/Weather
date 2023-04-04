package com.example.weather.catApi.domain.repositories;

import android.os.Handler;

import com.example.weather.catApi.data.entity.CatResponse;
import com.example.weather.core.newtwork.callbacks.RequestFileListener;
import com.example.weather.core.newtwork.callbacks.RequestListener;

import java.io.File;


public interface CatRemoteDataSource {
    void getCat(Handler handler, RequestFileListener listener);
}
