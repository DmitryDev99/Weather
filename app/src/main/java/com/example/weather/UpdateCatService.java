package com.example.weather;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;

import com.example.weather.catApi.data.dataSource.CatRemoteDataSourceImpl;
import com.example.weather.catApi.domain.useCase.LoadNewCatUseCase;
import com.example.weather.core.newtwork.callbacks.RequestFileListener;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateCatService extends Service {

    public final ExecutorService ioThread = Executors.newSingleThreadExecutor();
    public final Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LoadNewCatUseCase catUseCase = new LoadNewCatUseCase(
                new CatRemoteDataSourceImpl(ioThread)
        );
        catUseCase.invoke(mainThreadHandler, new RequestFileListener() {
            @Override
            public void onResponse(File file) {
                stopThisService();
            }
            @Override
            public void onError(Exception e) {
                stopThisService();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    private void stopThisService() {
        this.stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
