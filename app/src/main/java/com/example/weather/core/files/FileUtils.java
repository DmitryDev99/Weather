package com.example.weather.core.files;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileUtils {
    public static Bitmap fileToBitMap(@NotNull File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
}
