package com.example.weather.core.newtwork.parse;

import androidx.annotation.Nullable;

import com.example.weather.core.newtwork.parse.JsonReader;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.jetbrains.annotations.NotNull;

public class JsonReaderImpl implements JsonReader {

    @Override
    public <T> T parse(@Nullable String json, @NotNull Class<T> clazz) throws JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}
