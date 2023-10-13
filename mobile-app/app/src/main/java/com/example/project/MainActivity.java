package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the "activity_main" layout.
        setContentView(R.layout.activity_main);

        // Initialize a Button for user login.
        Button user = findViewById(R.id.user);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the "login" activity (user login screen).
                Intent intent = new Intent(getApplicationContext(), login.class);
                // Start the "login" activity.
                startActivity(intent);
            }
        });

    }
}