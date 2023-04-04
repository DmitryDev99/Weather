package com.example.weather.catApi.data.dataSource;

import static com.example.weather.catApi.data.dataSource.CatLocalDataSourcesImpl.CAT_FILE_FOLDER;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.weather.WeatherApplication;
import com.example.weather.catApi.data.entity.CatResponse;
import com.example.weather.catApi.domain.repositories.CatRemoteDataSource;
import com.example.weather.core.newtwork.NetworkProtocol;
import com.example.weather.core.newtwork.callbacks.RequestFileListener;
import com.example.weather.core.newtwork.parse.JsonReaderImpl;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

public class CatRemoteDataSourceImpl extends NetworkProtocol implements CatRemoteDataSource {
    private final Executor executor;
    public CatRemoteDataSourceImpl(Executor executor) {
        super("https://api.thecatapi.com/v1/images/search");
        this.executor = executor;
    }

    private void loadImageCat(
            String urlCat,
            @NonNull Handler handler,
            RequestFileListener listener
    ) throws IOException {
        File file = syncGetFile(
                urlCat,
                WeatherApplication.get().getDir(CAT_FILE_FOLDER, Context.MODE_PRIVATE),
                CatLocalDataSourcesImpl.CAT_FILE_NAME,
                percent ->
                        handler.post(() ->
                                listener.onProgress(percent)
                        )
        );
        handler.post(() -> listener.onResponse(file));
    }

    private CatResponse[] parseCatResponse(String response) throws JsonSyntaxException {
        return new JsonReaderImpl().parse(response, CatResponse[].class);
    }

    @Override
    public void getCat(Handler handler, RequestFileListener listener) {
        executor.execute(() -> {
            try {
                String response = syncGet();
                CatResponse[] parseData = parseCatResponse(response);
                String linkNewImage = parseData[0].getUrl();
                loadImageCat(linkNewImage, handler, listener);
            } catch (IOException e) {
                handler.post(() -> listener.onError(e));
            }
        });
    }
}
