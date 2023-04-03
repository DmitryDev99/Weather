package com.example.weather.catApi.data.dataSource;

import android.os.Handler;

import com.example.weather.catApi.data.entity.CatResponse;
import com.example.weather.catApi.domain.CatRemoteDataSource;
import com.example.weather.core.newtwork.NetworkProtocol;
import com.example.weather.core.newtwork.callbacks.RequestListener;
import com.example.weather.core.newtwork.parse.JsonReaderImpl;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.concurrent.Executor;

public class CatRemoteDataSourceImpl extends NetworkProtocol implements CatRemoteDataSource {
    private final Executor executor;
    public CatRemoteDataSourceImpl(Executor executor) {
        super("https://api.thecatapi.com/v1/images/search");
        this.executor = executor;
    }


    @Override
    public void getCatLink(
        final Handler handler,
        final RequestListener<CatResponse[]> listener
    ) {
        executor.execute(() -> {
            try {
                String response = syncGet();
                CatResponse[] parseData = parseCatResponse(response);
                handler.post(() -> listener.onResponse(parseData));
            } catch (IOException e) {
                handler.post(() -> listener.onError(e));
            }
        });
    }

    private CatResponse[] parseCatResponse(String response) throws JsonSyntaxException {
        return new JsonReaderImpl().parse(response, CatResponse[].class);
    }
}
