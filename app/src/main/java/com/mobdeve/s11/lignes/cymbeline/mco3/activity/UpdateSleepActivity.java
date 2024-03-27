package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.database.DatabaseHelper;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateSleepActivity extends AppCompatActivity {

    private EditText inputHours;
    private TextView hourDisplay;
    private ProgressBar progressBar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_sleep);
        BottomNavbarHelper.setProfileIconClickListener(this);

        // Set the text for the top navigation bar
        TextView textView = findViewById(R.id.textView);
        textView.setText("SLEEP");

        inputHours = findViewById(R.id.input_hours);
        hourDisplay = findViewById(R.id.hour_display);
        progressBar = findViewById(R.id.vertical_progress_bar);
        databaseHelper = new DatabaseHelper(this);

        // Load and display the current sleep hours, if any
        loadSleepHours();

        // Set click listener for the Save button
        findViewById(R.id.tvSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSleepHours();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSleepHours();
    }

    private void loadSleepHours() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());

        // Retrieve sleep hours for the current date from the database
        String sleepHours = databaseHelper.getSleepHours(currentDate);

        // Display the retrieved sleep hours
        if (sleepHours != null) {
            inputHours.setText(sleepHours);
            hourDisplay.setText(sleepHours + " hrs");

            // Calculate the percentage of hours saved relative to the maximum (7 hours)
            int hours = Integer.parseInt(sleepHours);
            int maxHours = 7; // Maximum number of hours
            int progress = (int) ((hours / (float) maxHours) * 100);

            // Set the progress of the progress bar
            progressBar.setProgress(progress);
        } else {
            // If no sleep hours are found, set the input field and display to empty
            inputHours.setText("");
            hourDisplay.setText("");
            progressBar.setProgress(0);
        }
    }


    private void saveSleepHours() {
        // Get the current logged-in username
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");

        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentDate = sdfDate.format(calendar.getTime());
        String currentTime = sdfTime.format(calendar.getTime());

        // Get the input hours
        String hours = inputHours.getText().toString().trim();

        // Check if the current time is before 11:59 PM for the current day
        if (currentTime.equals("23:59") || !hours.isEmpty()) {
            // Save sleep hours to the database
            boolean success;
            if (hours.isEmpty()) {
                // If sleep hours are empty, delete the record from the database
                success = databaseHelper.deleteSleepHours(currentDate);
            } else {
                // Update or insert sleep hours
                success = databaseHelper.updateOrInsertSleepHours(currentDate, hours);
            }

            if (success) {
                // Update the displayed sleep hours and progress bar
                loadSleepHours();
                Toast.makeText(this, "Sleep hours saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save sleep hours", Toast.LENGTH_SHORT).show();
            }
        } else {
            // User can input new data for the new day
            inputHours.setText("");
            Toast.makeText(this, "You can now input new data for the new day", Toast.LENGTH_SHORT).show();
        }
    }
}
