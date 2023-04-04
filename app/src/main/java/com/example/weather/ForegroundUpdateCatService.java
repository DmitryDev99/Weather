package com.example.weather;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.example.weather.notifications.NotificationIdentifier.TEST_FOREGROUND_SERVICE_CHANNEL;
import static com.example.weather.notifications.NotificationIdentifier.TEST_FOREGROUND_SERVICE_CHANNEL_NAME;
import static com.example.weather.notifications.NotificationIdentifier.TEST_FOREGROUND_SERVICE_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class ForegroundUpdateCatService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel(String.valueOf(TEST_FOREGROUND_SERVICE_CHANNEL), TEST_FOREGROUND_SERVICE_CHANNEL_NAME);
        Intent startUpdateCatIntent = new Intent(this, UpdateCatService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                this.getApplicationContext(),
                0,
                startUpdateCatIntent,
                FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE
        );
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.ic_launcher_foreground,
                getString(R.string.main_screen_refresh_image_cat),
                pendingIntent
                ).build();
        Notification notification = new NotificationCompat.Builder(this, String.valueOf(TEST_FOREGROUND_SERVICE_CHANNEL))
                .setContentText(getString(R.string.test_foreground_service_text))
                .setSmallIcon(R.drawable.ic_cat_placeholder)
                .addAction(action)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(TEST_FOREGROUND_SERVICE_ID, notification);
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
}