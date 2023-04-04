package com.example.weather.catApi.data.dataSource;

import android.content.Context;

import com.example.weather.WeatherApplication;
import com.example.weather.catApi.domain.repositories.CatLocalDataSource;

import java.io.File;

public class CatLocalDataSourcesImpl implements CatLocalDataSource {
    public static final String CAT_FILE_NAME = "anyCat";
    public static final String CAT_FILE_FOLDER = "picCats";
    @Override
    public File getCat() {
        File catDir = WeatherApplication.get().getDir(CAT_FILE_FOLDER, Context.MODE_PRIVATE);
        try {
            if (catDir.exists()) {
                File[] list = catDir.listFiles();
                if (list != null && list.length > 0) {
                    return list[0];
                } else {
                    return null;
                }
            } else {
                boolean tmp = catDir.mkdir();
                return null;
            }
        } catch (SecurityException e) {
            return null;
        }
    }
}
