package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.database.DatabaseHelper;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.TopNavBarHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * UpdateWaterActivity allows users to update their daily water intake.
 * Users can add their water intake in increments of 250ml by clicking the Add button,
 * and the activity displays the updated water intake amount both in milliliters and
 * in terms of the number of glasses consumed out of the recommended 8 glasses per day.
 * The activity also visualizes the water intake progress using a vertical progress bar,
 * indicating the percentage of the recommended intake achieved. Users can save their
 * water intake, and the data is stored in the database. The activity retrieves the
 * previously saved water intake for the current day, if any, and displays it to the user.
 * Water intake can be updated or deleted, and the changes are reflected in the database
 * and visually updated in the UI. The activity provides navigation options to return to
 * the dashboard and supports automatic data reloading when the activity is resumed.
 */

public class UpdateWaterActivity extends AppCompatActivity {

    private TextView waterAmount;
    private TextView outOf;
    private ProgressBar progressBar;
    private DatabaseHelper databaseHelper;
    private int glassesConsumed = 0;
    private String lastDate = ""; // Track the last recorded date
    private int pendingWaterIntake = 0; // Temporary variable to store pending water intake

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_water);
        BottomNavbarHelper.setProfileIconClickListener(this);
        TopNavBarHelper.setCancelClickListener(this);

        // Set the text for the top navigation bar
        TextView textView = findViewById(R.id.textView);
        textView.setText("WATER");

        waterAmount = findViewById(R.id.waterAmount);
        outOf = findViewById(R.id.outOf);
        progressBar = findViewById(R.id.vertical_progress_bar);
        databaseHelper = new DatabaseHelper(this);

        // Load and display the current water intake, if any
        loadWaterIntake();

        // Set click listener for the Add button
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWaterIntake();
            }
        });

        // Set click listener for the Save button
        findViewById(R.id.tvSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWaterIntake();
            }
        });
    }

    /*
     * The onResume() method is overridden to reload water intake data whenever the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadWaterIntake();
    }

    /*
     * The loadWaterIntake() method retrieves water intake data for the current date from the database
     * and updates the UI elements accordingly.
     */
    private void loadWaterIntake() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());


        // Retrieve water intake for the current date from the database
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");
        int waterIntake = databaseHelper.getWaterIntake(currentDate, loggedInUsername);

        // Display the retrieved water intake
        waterAmount.setText(String.valueOf(waterIntake) + " ml");
        int glasses= (int) ((waterIntake / 250));
        outOf.setText(glasses+ " out of 8");

        // Calculate the percentage of intake relative to the recommended (2000 ml)
        int maxIntake = 2000; // Maximum intake in ml
        int progress = (int) ((waterIntake / (float) maxIntake) * 100);

        // Set the progress of the progress bar
        progressBar.setProgress(progress);

        // Update the last recorded date
        lastDate = currentDate;
    }

    /*
     * The addWaterIntake() method increments the pending water intake by 250ml and updates the UI
     * to reflect the change.
     */
    private void addWaterIntake() {

        // Calculate the percentage of intake relative to the recommended (2000 ml)
        int maxIntake = 2000; // Maximum intake in ml


        // Add 250ml to the pending water intake
        pendingWaterIntake += 250;


        // Update the displayed water amount
        int currentWaterAmount = Integer.parseInt(waterAmount.getText().toString().replace(" ml", ""));
        currentWaterAmount += 250;
        waterAmount.setText(String.valueOf(currentWaterAmount) + " ml");
        int glasses= (int) ((currentWaterAmount / 250));
        outOf.setText(String.valueOf(glasses)+ " out of 8");
        int progress = (int) ((currentWaterAmount/ (float) maxIntake) * 100);

        // Set the progress of the progress bar
        progressBar.setProgress(progress);
    }

    /*
     * The saveWaterIntake() method saves the total water intake (including pending intake) to the database
     * for the current date. It then reloads the water intake data to reflect the changes and updates the UI.
     */
    private void saveWaterIntake() {
        // Get the current logged-in username
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");

        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());

        // Retrieve the current water intake from the database
        int currentIntake = databaseHelper.getWaterIntake(currentDate, loggedInUsername);

        // Calculate the total intake by adding pending intake to current intake
        int newIntake = currentIntake + pendingWaterIntake;

        // Delete the existing record
        databaseHelper.deleteWaterIntake(loggedInUsername, currentDate);

        // Insert the updated water intake into the database
        boolean success = databaseHelper.addWaterIntake(loggedInUsername, currentDate, newIntake);

        if (success) {
            // Show toast message
            Toast.makeText(this, "Water intake updated successfully", Toast.LENGTH_SHORT).show();

            // Reload the data to display the updated water intake
            loadWaterIntake();

            // Create an intent to navigate back to the DashboardActivity
            Intent intent = new Intent(UpdateWaterActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update water intake", Toast.LENGTH_SHORT).show();
        }

        // Reset pending water intake
        pendingWaterIntake = 0;
    }
}
