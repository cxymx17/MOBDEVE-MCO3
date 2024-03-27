package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.database.DatabaseHelper;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        BottomNavbarHelper.setProfileIconClickListener(this);

        // Set the text for the top navigation bar
        TextView textView = findViewById(R.id.textView);
        textView.setText("DASHBOARD");

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Find the progress bar
        progressBar = findViewById(R.id.progressBar2);

        // Update the progress bar
        updateProgressBar();

        // Find the rectangle_11 view
        View rectangle11 = findViewById(R.id.rectangle_11);

        // Set OnClickListener to rectangle_11
        rectangle11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to update_sleep.xml page
                Intent intent = new Intent(DashboardActivity.this, UpdateSleepActivity.class);
                startActivity(intent);
            }
        });

        // Find the rectangle_13_ek1 view
        View rectangle13Ek1 = findViewById(R.id.rectangle_13_ek1);

        // Set OnClickListener to rectangle_13_ek1
        rectangle13Ek1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to update_brush.xml page
                Intent intent = new Intent(DashboardActivity.this, UpdateBrushActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the progress bar when the activity is resumed
        updateProgressBar();
    }

    // Method to update the progress bar based on saved sleep hours
    private void updateProgressBar() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());

        // Retrieve sleep hours for the current date from the database
        String sleepHours = databaseHelper.getSleepHours(currentDate);

        // Calculate the progress based on retrieved sleep hours
        int progress = 0;
        if (sleepHours != null) {
            // Calculate progress based on sleep hours (e.g., assuming 8 hours of sleep is maximum)
            int totalSleepHours = Integer.parseInt(sleepHours);
            int maxSleepHours = 8; // Maximum hours of sleep
            progress = (int) ((totalSleepHours / (float) maxSleepHours) * 100);
        }

        // Set the progress of the progress bar
        progressBar.setProgress(progress);

        // Set the rotation to start from the top
        progressBar.setRotation(-90);
    }
}
