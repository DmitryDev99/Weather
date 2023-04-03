package com.example.weather.core.newtwork.parse;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public interface JsonReader {
    <T> T parse(@Nullable String json, @NotNull Class<T> clazz);
}
