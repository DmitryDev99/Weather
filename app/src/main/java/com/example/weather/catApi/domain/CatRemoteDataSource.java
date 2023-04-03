package com.example.weather.catApi.domain;

import android.os.Handler;

import com.example.weather.catApi.data.entity.CatResponse;
import com.example.weather.core.newtwork.callbacks.RequestListener;


public interface CatRemoteDataSource {
    void getCatLink(
       final Handler handler,
       final RequestListener<CatResponse[]> listener
    );
}
