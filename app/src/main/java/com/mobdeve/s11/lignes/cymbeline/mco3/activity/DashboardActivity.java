package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    private ProgressBar progressBar3;
    private ProgressBar progressBar4; // Add progressBar3
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

        // Find the progress bars
        progressBar = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar4 = findViewById(R.id.progressBar4); // Initialize progressBar3


        // Update the progress bars
        updateProgressBars();

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

        // Find the rectangle_13 view
        View rectangle13 = findViewById(R.id.rectangle_13);

        // Set OnClickListener to rectangle_13
        rectangle13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to update_water.xml page
                Intent intent = new Intent(DashboardActivity.this, UpdateWaterActivity.class);
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
        // Update the progress bars when the activity is resumed
        updateProgressBars();
    }

    // Call this method whenever you update the progress bars
    private void updateProgressBars() {
        // Update progress bars for sleep hours, brush count, and water intake
        updateProgressBar();
        updateBrushProgressBar();
        updateWaterProgressBar();

        // Update the overall progress percentage
        updateOverallProgress();
    }

    // Method to update the progress bar based on saved sleep hours
    private void updateProgressBar() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());

        // Retrieve sleep hours for the current date from the database
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");
        String sleepHours = databaseHelper.getSleepHours(currentDate, loggedInUsername);

        // Log retrieved sleep hours for debugging
        Log.d("DashboardActivity", "Sleep hours for " + currentDate + ": " + sleepHours);

        // Calculate the progress based on retrieved sleep hours
        int progress = 0;
        if (sleepHours != null) {
            // Calculate progress based on sleep hours (e.g., assuming 7 hours of sleep is maximum)
            int totalSleepHours = Integer.parseInt(sleepHours);
            int maxSleepHours = 7; // Maximum hours of sleep
            progress = (int) ((totalSleepHours / (float) maxSleepHours) * 100);
        }

        // Log progress for debugging
        Log.d("DashboardActivity", "Progress: " + progress);

        // Set the progress of the progress bar
        progressBar.setProgress(progress);

        // Set the rotation to start from the top
        progressBar.setRotation(-90);
    }

    // Method to update the progress bar based on saved brush count
    // Method to update the progress bar based on saved brush count
    private void updateBrushProgressBar() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());

        // Retrieve brush count for the current date from the database
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");
        int brushCount = databaseHelper.getBrushCount(loggedInUsername, currentDate);

        // Calculate the progress based on retrieved brush count
        int progress = (int) (((double) brushCount / 3.0) * 100);
        // Assuming maximum of 3 brushes

        // Set the progress of progressBar3
        progressBar4.setProgress(progress);

        // Set the text for the brush progress bar
        progressBar4.setIndeterminate(false);
        progressBar4.setMax(100);
        progressBar4.setProgress(progress);

        // Set the rotation to start from the top
        progressBar4.setRotation(-90);
        Log.d("DashboardActivity", "Brush count for " + currentDate + ": " + brushCount);

    }

    // Method to update the progress bar based on saved water intake
    private void updateWaterProgressBar() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());

        // Retrieve water intake for the current date from the database
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");
        int waterIntake = databaseHelper.getWaterIntake(currentDate, loggedInUsername);

        // Calculate the progress based on retrieved water intake
        int progress = (int) (((double) waterIntake / 2000.0) * 100);
        // Assuming maximum of 2000 ml intake

        // Set the progress of progressBar3
        progressBar3.setProgress(progress);

        // Set the text for the water progress bar
        progressBar3.setIndeterminate(false);
        progressBar3.setMax(100);
        progressBar3.setProgress(progress);

        // Set the rotation to start from the top
        progressBar3.setRotation(-90);

    }

    // Method to update the overall progress percentage
    private void updateOverallProgress() {
        // Calculate the overall progress based on the progress of sleep hours, brush count, and water intake
        int overallProgress = (progressBar.getProgress() + progressBar4.getProgress() + progressBar3.getProgress()) / 3;

        // Set the text of the overall progress TextView
        TextView overallPercent = findViewById(R.id.overallPercent);
        overallPercent.setText(overallProgress + "%");
    }


}
