package com.example.weather.core.newtwork.callbacks;

import android.net.Uri;

import androidx.annotation.IntRange;

public interface ProgressDownloadListener {
    void onProgress(@IntRange(from = 0, to = 100) int percent);
}
