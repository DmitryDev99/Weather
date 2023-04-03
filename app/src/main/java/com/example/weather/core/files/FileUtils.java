package com.example.weather.core.files;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileUtils {
    @NonNull
    @Contract("_, _ -> new")
    public static File createNewEmptyFile(File fileDir, @NotNull String fileName) {
        return new File(fileDir, fileName);
    }
}
