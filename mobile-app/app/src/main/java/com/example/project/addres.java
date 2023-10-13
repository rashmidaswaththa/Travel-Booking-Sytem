package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addres extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addres);

        // Initialize EditText fields and buttons.
        EditText from = findViewById(R.id.from);
        EditText to = findViewById(R.id.to);
        EditText dt = findViewById(R.id.date);
        EditText seats = findViewById(R.id.seats);
        Button addbus = findViewById(R.id.addbus);
        Button back1 =findViewById(R.id.button2);

        // Create an instance of the DBHelper class to interact with the database.
        DBHelper DB = new DBHelper(this);

        // Define a click listener for the "addbus" button
        addbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from the EditText fields.
                String departure = from.getText().toString();
                String arrival = to.getText().toString();
                String date = dt.getText().toString();
                String total_seats = seats.getText().toString();

                // Check if any of the input fields are empty.
                if (departure.equals("") || arrival.equals("") || date.equals("") || total_seats.equals(""))
                {
                    // Show a toast message if any field is empty.
                    Toast.makeText(addres.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // If all fields are filled, insert bus information into the database.
                    Boolean insert = DB.insertBus(departure,arrival,date,total_seats);
                        if (insert){
                            Toast.makeText(addres.this, "Bus has been added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), resmain.class);
                            startActivity(intent);
                        }
                        else {
                            // Show an error message if insertion fails.
                            Toast.makeText(addres.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the "resmain" activity.
                Intent intent = new Intent(getApplicationContext(), resmain.class);
                // Start the "resmain" activity.
                startActivity(intent);
            }
        });

    }
}