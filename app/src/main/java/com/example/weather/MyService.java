package com.example.weather;

import static com.example.weather.catApi.presentation.MainActivity.CAT_FILE_NAME;
import static com.example.weather.catApi.presentation.MainActivity.SP_NAME_KEY;
import static com.example.weather.catApi.presentation.MainActivity.SP_NAME_PREF;
import static com.example.weather.notifications.NotificationIdentifier.TEST_FOREGROUND_SERVICE_CHANNEL;
import static com.example.weather.notifications.NotificationIdentifier.TEST_FOREGROUND_SERVICE_CHANNEL_NAME;
import static com.example.weather.notifications.NotificationIdentifier.TEST_FOREGROUND_SERVICE_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.core.app.NotificationCompat;
import androidx.core.os.HandlerCompat;

import com.example.weather.catApi.data.dataSource.CatRemoteDataSourceImpl;
import com.example.weather.catApi.data.entity.CatResponse;
import com.example.weather.catApi.domain.repositories.CatRemoteDataSource;
import com.example.weather.core.newtwork.callbacks.RequestFileListener;
import com.example.weather.core.newtwork.callbacks.RequestListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyService extends Service {

    private final ExecutorService mainIOPool = Executors.newSingleThreadExecutor();
    public final Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel(String.valueOf(TEST_FOREGROUND_SERVICE_CHANNEL), TEST_FOREGROUND_SERVICE_CHANNEL_NAME);
//        PendingIntent pendingIntent = PendingIntent
        Notification notification = new NotificationCompat.Builder(this, String.valueOf(TEST_FOREGROUND_SERVICE_CHANNEL))
                .setContentText(getString(R.string.test_foreground_service_text))
                .setSmallIcon(R.drawable.ic_cat_placeholder)
                .addAction(R.drawable.ic_cat_placeholder, getString(R.string.main_screen_refresh_image_cat), null)
                .setOngoing(true)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(TEST_FOREGROUND_SERVICE_ID, notification);
//        setNewImageCat();
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel(String channelId, String channelName){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_NONE);
            NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            service.createNotificationChannel(chan);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}