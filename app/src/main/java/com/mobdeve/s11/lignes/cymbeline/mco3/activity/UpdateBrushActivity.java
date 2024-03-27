package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.database.DatabaseHelper;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

import java.text.DateFormat;
import java.util.Date;

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
        if (brushCount > 3) {
            brushCount = 3;
        }
        Toast.makeText(UpdateBrushActivity.this, "Brush Count: " + brushCount, Toast.LENGTH_SHORT).show();
    }

    private void decrementBrushCount() {
        brushCount--;
        if (brushCount < 0) {
            brushCount = 0;
        }
        Toast.makeText(UpdateBrushActivity.this, "Brush Count: " + brushCount, Toast.LENGTH_SHORT).show();
    }

    private void saveBrushCount() {
        // Get the current date
        String currentDate = getCurrentDate();

        // Save brush count to the database
        boolean success = databaseHelper.updateOrInsertBrushCount(currentDate, brushCount);

        if (success) {
            Toast.makeText(this, "Brush count saved successfully", Toast.LENGTH_SHORT).show();
            // Save brush state to SharedPreferences when the save button is clicked
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("brushCount", brushCount);
            for (int i = 0; i < brushFilled.length; i++) {
                editor.putBoolean("brush" + i, brushFilled[i]);
            }
            editor.apply();
        } else {
            Toast.makeText(this, "Failed to save brush count", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBrushState() {
        brushCount = sharedPreferences.getInt("brushCount", 0);
        for (int i = 0; i < brushFilled.length; i++) {
            brushFilled[i] = sharedPreferences.getBoolean("brush" + i, false);
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

    private String getCurrentDate() {
        // Get the current date
        return DateFormat.getDateInstance().format(new Date());
    }
}
