package com.example.weather.catApi.data.dataSource;

import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.weather.catApi.domain.CatImageRemoteDataSource;
import com.example.weather.core.newtwork.NetworkProtocol;
import com.example.weather.core.newtwork.callbacks.RequestFileListener;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

public class CatImageRemoteDataSourceImpl extends NetworkProtocol implements CatImageRemoteDataSource {
    private final Executor executor;

    public CatImageRemoteDataSourceImpl(
        @Nullable String baseUrl,
        Executor executor
    ) {
        super(baseUrl);
        this.executor = executor;
    }

    @Override
    public void getImageCat(File fileDir, String fileName, Handler handler, RequestFileListener listener) {
        executor.execute(() -> {
            try {
                File file = syncGetFile(
                    fileDir,
                    fileName,
                    percent ->
                        handler.post(() ->
                            listener.onProgress(percent)
                    )
                );
                handler.post(() -> listener.onResponse(file));
            } catch (IOException e) {
                handler.post(() -> listener.onError(e));
            }
        });
    }
}
