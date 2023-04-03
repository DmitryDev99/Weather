package com.example.weather.core.newtwork.callbacks;

import android.net.Uri;

import androidx.annotation.IntRange;

import java.io.File;

public interface RequestFileListener {
    void onResponse(File file);
    default void onProgress(@IntRange(from = 0, to = 100) int percent) {}
    default void onError(Exception e) {}
}
