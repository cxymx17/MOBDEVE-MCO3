package com.mobdeve.s11.lignes.cymbeline.mco3.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobdeve.s11.lignes.cymbeline.mco3.R;

import java.util.Map;

/**
 * Service class for handling Firebase Cloud Messaging (FCM) messages.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains data payload
        if (remoteMessage.getData().size() > 0) {
            // Handle data payload
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String body = data.get("body");

            // Process the data
            // For example, you can display a notification
            sendNotification(title, body);
        }

        // Check if the message contains notification payload
        if (remoteMessage.getNotification() != null) {
            // Handle notification payload
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            String title = notification.getTitle();
            String body = notification.getBody();

            // Process the notification
            // For example, you can display a notification
            sendNotification(title, body);
        }
    }

    /**
     * Method to display a notification.
     *
     * @param title the title of the notification
     * @param body  the body of the notification
     */
    private void sendNotification(String title, String body) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "notify")
                .setSmallIcon(R.drawable.notification) // Set your small icon here
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel creation (for Android O and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify",
                    "FreshStart",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }


}


