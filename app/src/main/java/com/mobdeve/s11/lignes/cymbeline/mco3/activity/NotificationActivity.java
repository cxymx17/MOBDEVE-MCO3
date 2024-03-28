package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.model.Notification;
import com.mobdeve.s11.lignes.cymbeline.mco3.model.NotificationAdapter;
import java.util.ArrayList;
import java.util.List;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

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

        // Sample notification data
        List<Notification> notificationList = getSampleNotifications();

        // Initialize and set NotificationAdapter
        notificationAdapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(notificationAdapter);

        // Set up bottom navigation bar
        BottomNavbarHelper.setProfileIconClickListener(this);
    }

    // Method to provide sample notification data
    private List<Notification> getSampleNotifications() {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("New Message", "You have received a new message", "10:00 AM"));
        notifications.add(new Notification("Reminder", "Don't forget to attend the meeting", "11:30 AM"));
        // Add more sample notifications as needed
        return notifications;
    }
}
