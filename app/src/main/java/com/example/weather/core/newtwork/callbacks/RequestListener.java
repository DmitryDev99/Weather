package com.example.weather.core.newtwork.callbacks;

public interface RequestListener<T> {
    void onResponse(T result);
    default void onError(Exception e) {}
}
