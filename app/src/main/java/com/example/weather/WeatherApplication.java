package com.example.weather;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WeatherApplication extends Application {
    private static WeatherApplication instance;
    public static WeatherApplication get() {
        return instance;
    }
    public final ExecutorService mainIOPool = Executors.newFixedThreadPool(4);
    public final Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue();

    public ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            NUMBER_OF_CORES,
            NUMBER_OF_CORES,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue
    );
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
