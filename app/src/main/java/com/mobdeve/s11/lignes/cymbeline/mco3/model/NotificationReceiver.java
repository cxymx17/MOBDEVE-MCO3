package com.mobdeve.s11.lignes.cymbeline.mco3.model;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;

/**
 * NotificationReceiver is a BroadcastReceiver responsible for receiving notifications and showing them to the user.
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "default";

    /**
     * Shows a notification with the specified title and message.
     *
     * @param context the context
     * @param title   the title of the notification
     * @param message the message of the notification
     */
    public static void showNotification(Context context, String title, String message) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request the missing permissions, or handle the case where the permission is not granted
            return;
        }
        notificationManager.notify(123, builder.build());
    }

    /**
     * Creates a notification channel for the app.
     *
     * @param context the context
     */
    private static void createNotificationChannel(Context context) {
        // Create notification channel if not already created
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Default Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle receiving of notifications, if needed
    }
}