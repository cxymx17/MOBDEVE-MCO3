package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.database.DatabaseHelper;


/**
 * LoginActivity handles user authentication by checking the entered username and password
 * against the database. Upon successful login, it stores the logged-in user's username in
 * SharedPreferences and navigates to the UserProfileActivity. If the login credentials are
 * invalid, it displays a toast message indicating the same.
 */

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText loginUsername, loginPassword;

    /*
     * The onCreate() method is called when the activity is first created.
     * It initializes the activity's UI and sets up the login button click listener.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        myDb = new DatabaseHelper(this);
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);

        CardView loginButton = findViewById(R.id.LoginBtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    /*
     * The loginUser() method is responsible for authenticating the user
     * by checking the entered username and password against the database.
     * If the credentials are valid, it stores the username in SharedPreferences
     * and starts the UserProfileActivity. Otherwise, it displays an error message.
     */
    public void loginUser() {
        String username = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (myDb.checkUser(username, password)) {
            // Store the logged-in user's username in SharedPreferences
            SharedPreferences.Editor editor = getSharedPreferences("user_session", MODE_PRIVATE).edit();
            editor.putString("username", username);
            editor.apply();

            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            // Start the UserProfileActivity
            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

}
