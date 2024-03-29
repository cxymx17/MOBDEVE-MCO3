package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.database.DatabaseHelper;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.TopNavBarHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateBrushActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private int brushCount = 0;
    private boolean[] brushFilled = {false, false, false}; // Array to track whether each brush is filled
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_brush);
        BottomNavbarHelper.setProfileIconClickListener(this);
        TopNavBarHelper.setCancelClickListener(this);

        // Set the text for the top navigation bar
        TextView textView = findViewById(R.id.textView);
        textView.setText("BRUSH");

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("BrushPreferences", MODE_PRIVATE);

        // Find the views for brushes
        ImageView brush1 = findViewById(R.id.brush1);
        ImageView brush2 = findViewById(R.id.brush2);
        ImageView brush3 = findViewById(R.id.brush3);

        // Load the state of brushes from SharedPreferences
        loadBrushState();

        // Retrieve the latest brush count from the database and log it
        retrieveAndLogLatestBrushCount();

        // Set OnClickListener for brush1
        brush1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBrush(0, brush1);
            }
        });

        // Set OnClickListener for brush2
        brush2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBrush(1, brush2);
            }
        });

        // Set OnClickListener for brush3
        brush3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBrush(2, brush3);
            }
        });

        // Set OnClickListener for the Save button in the top navigation bar
        findViewById(R.id.tvSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBrushCount();
            }
        });
    }


    private void retrieveAndLogLatestBrushCount() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());

        // Get the current logged-in username
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");

        // Retrieve the latest brush count from the database
        int latestBrushCount = databaseHelper.getBrushCount(loggedInUsername, currentDate);

        // Log the latest retrieved brush count
        Log.d("UpdateBrushActivity", "Latest retrieved brush count from database: " + latestBrushCount);
    }

    private void toggleBrush(int index, ImageView brush) {
        if (brushFilled[index]) {
            brush.setImageResource(R.drawable.original_brush);
            brushFilled[index] = false;
            decrementBrushCount();
        } else {
            brush.setImageResource(R.drawable.filled_brush);
            brushFilled[index] = true;
            incrementBrushCount();
        }
    }

    private void incrementBrushCount() {
        brushCount++;
        displayBrushCount();
    }

    private void decrementBrushCount() {
        brushCount--;
        displayBrushCount();
    }

    private void displayBrushCount() {
        Toast.makeText(UpdateBrushActivity.this, "Brush Count: " + brushCount, Toast.LENGTH_SHORT).show();
    }

    private void saveBrushCount() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());

        // Get the current logged-in username
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");

        // Log the brush count before saving
        Log.d("UpdateBrushActivity", "Brush count to be saved: " + brushCount);

        // Save brush count to the database
        boolean success = databaseHelper.updateOrInsertBrushCount(loggedInUsername, currentDate, brushCount);

        if (success) {
            Toast.makeText(this, "Brush count saved successfully", Toast.LENGTH_SHORT).show();
            // Save brush state to SharedPreferences when the save button is clicked
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(loggedInUsername + "_brushCount", brushCount);
            for (int i = 0; i < brushFilled.length; i++) {
                editor.putBoolean(loggedInUsername + "_brush" + i, brushFilled[i]);
            }
            editor.apply();

            // Log the saved brush count
            int savedBrushCount = databaseHelper.getBrushCount(loggedInUsername, currentDate);
            Log.d("UpdateBrushActivity", "Saved brush count in database: " + savedBrushCount);
            Intent intent = new Intent(UpdateBrushActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to save brush count", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadBrushState() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String loggedInUsername = sharedPreferences.getString("username", "");
        String currentDate = sdf.format(calendar.getTime());

        brushCount = sharedPreferences.getInt(loggedInUsername + "_brushCount", 0);
        for (int i = 0; i < brushFilled.length; i++) {
            brushFilled[i] = sharedPreferences.getBoolean(loggedInUsername + "_brush" + i, false);
            ImageView brush = null;
            switch (i) {
                case 0:
                    brush = findViewById(R.id.brush1);
                    break;
                case 1:
                    brush = findViewById(R.id.brush2);
                    break;
                case 2:
                    brush = findViewById(R.id.brush3);
                    break;
            }
            if (brushFilled[i]) {
                brush.setImageResource(R.drawable.filled_brush);
            } else {
                brush.setImageResource(R.drawable.original_brush);
            }
        }
    }


}
