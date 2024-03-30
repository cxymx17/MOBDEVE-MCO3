package com.mobdeve.s11.lignes.cymbeline.mco3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.database.DatabaseHelper;


/**
 * RegisterActivity allows users to register for a new account. It includes EditText fields
 * for entering the username, email address, birthday, and password. Upon clicking the
 * registration button, the registerUser method is called to validate the input fields
 * and insert the user data into the database using the DatabaseHelper class. If the
 * registration is successful, the user is redirected to the MainActivity.
 */

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText registerUsername, registerEmailAddress, registerBday, registerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        myDb = new DatabaseHelper(this);
        registerUsername = findViewById(R.id.registerUsername);
        registerEmailAddress = findViewById(R.id.registerEmailAddress);
        registerBday = findViewById(R.id.registerBday);
        registerPassword = findViewById(R.id.RegisterPassword);

        // Find the RegisterBtn button and set its onClick listener
        CardView registerButton = findViewById(R.id.RegisterBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(); // Call the registerUser method when the button is clicked
            }
        });
    }

    /*
     * The registerUser() method handles user registration.
     * It retrieves user input from the registration form fields, validates them,
     * and then checks if the user already exists in the database.
     * If the user does not exist, it inserts the user data into the database.
     * It displays appropriate Toast messages to inform the user about the registration status.
     * If registration is successful, it starts the MainActivity and finishes the RegisterActivity.
     */
    public void registerUser() {
        String username = registerUsername.getText().toString().trim();
        String emailAddress = registerEmailAddress.getText().toString().trim();
        String bday = registerBday.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();

        if (username.isEmpty() || emailAddress.isEmpty() || bday.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Check if the user already exists
            if (myDb.checkExistingUser(username)) {
                Toast.makeText(this, "User already registered", Toast.LENGTH_SHORT).show();
            } else {
                // User is not registered, proceed with registration
                boolean isInserted = myDb.insertData(username, password, emailAddress, bday);
                if (isInserted) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    // Start MainActivity after successful registration
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Finish the current activity to prevent going back to it when pressing back
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
