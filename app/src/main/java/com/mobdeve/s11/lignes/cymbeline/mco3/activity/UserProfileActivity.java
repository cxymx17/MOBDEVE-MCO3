package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.database.DatabaseHelper;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

public class UserProfileActivity extends AppCompatActivity {
    private TextView initialTextView;
    private DatabaseHelper databaseHelper;
    private TextView greetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        BottomNavbarHelper.setProfileIconClickListener(this);

        // Set the text for the top navigation bar
        TextView textView = findViewById(R.id.textView);
        textView.setText("USER PROFILE");

        initialTextView = findViewById(R.id.initialTextView);
        greetingTextView = findViewById(R.id.greetingTextView);
        databaseHelper = new DatabaseHelper(this);

        String userName = getUserNameFromDatabase();

        if (userName != null && !userName.isEmpty()) {
            // Get the first letter of the user's name and convert it to uppercase
            String initial = userName.substring(0, 1).toUpperCase();
            initialTextView.setText(initial);

            // Convert the username to uppercase
            String capitalizedUserName = userName.toUpperCase();
            String greetingMessage = "HI! " + capitalizedUserName;
            greetingTextView.setText(greetingMessage);
        } else {
            Toast.makeText(this, "Failed to retrieve username", Toast.LENGTH_SHORT).show();
        }

        CardView logoutButton = findViewById(R.id.LogoutBtn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the UserProfileActivity
                finish();
                // Start the MainActivity
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getUserNameFromDatabase() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String loggedInUsername = prefs.getString("username", null);
        return databaseHelper.getUserName(loggedInUsername);
    }
}
