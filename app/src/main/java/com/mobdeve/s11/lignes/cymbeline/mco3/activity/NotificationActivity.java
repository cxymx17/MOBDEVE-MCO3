package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.model.Notification;
import com.mobdeve.s11.lignes.cymbeline.mco3.model.NotificationAdapter;
import com.mobdeve.s11.lignes.cymbeline.mco3.model.NotificationReceiver;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        // Set the text for the top navigation bar
        TextView textView = findViewById(R.id.textView);
        textView.setText("NOTIFICATIONS");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Notification> notificationList = getNotifications();

        // Initialize and set NotificationAdapter
                notificationAdapter = new NotificationAdapter(notificationList);
                recyclerView.setAdapter(notificationAdapter);

        // Schedule notifications
        scheduleNotifications();


        // Set up bottom navigation bar
        BottomNavbarHelper.setProfileIconClickListener(this);
    }

    // Method to schedule notifications
    private void scheduleNotifications() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Schedule morning brushing notification
        if (hour >= 6) {
            scheduleNotification("Time to brush your teeth (Morning)", 1, "6:00 AM");
        }

        // Schedule lunch brushing notification
        if (hour >= 12) {
            scheduleNotification("Time to brush your teeth (Noon)", 2, "12:00 PM");
        }

        // Schedule night brushing notification
        if (hour >= 22) {
            scheduleNotification("Time to brush your teeth (Night)", 3, "10:00 PM");
        }

        // Schedule morning water drinking notification
        if (hour >= 7) {
            scheduleNotification("Don't forget to drink enough water!", 4, "7:00 AM");
        }

        // Schedule night sleep notification
        if (hour >= 22) {
            scheduleNotification("Get enough sleep!", 5, "10:00 PM");
        }

    }

    private List<Notification> getNotifications() {
        List<Notification> notifications = new ArrayList<>();
        return notifications;
    }


    // Method to schedule a single notification
    private void scheduleNotification(String message, int notificationId, String time) {
        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        notificationIntent.putExtra("notificationId", notificationId);
        notificationIntent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long futureInMillis = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        }

        // Update the notification list to include the scheduled notification
        notificationAdapter.addNotification(new Notification("Reminder", message, time));
    }
}

