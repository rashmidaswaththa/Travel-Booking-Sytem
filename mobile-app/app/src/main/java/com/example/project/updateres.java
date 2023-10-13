package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updateres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the "activity_updateres" layout.
        setContentView(R.layout.activity_updateres);

        //Initialize EditText and Buttons
        EditText from = findViewById(R.id.from);
        EditText to = findViewById(R.id.to);
        EditText dt = findViewById(R.id.date);
        Button back = findViewById(R.id.button06);
        EditText seats = findViewById(R.id.seats);
        // Initialize a Button for updating reservation information.
        Button updateres = findViewById(R.id.updateres);
        // Create an instance of the DBHelper class for database operations.
        DBHelper DB = new DBHelper(this);

        updateres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values for departure, arrival, date, and total seats.
                String departure = from.getText().toString();
                String arrival = to.getText().toString();
                String date = dt.getText().toString();
                String total_seats = seats.getText().toString();

                if (departure.equals("") || arrival.equals("") || date.equals("") || total_seats.equals(""))
                {
                    // Show a message if any of the required fields are empty.
                    Toast.makeText(updateres.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkid= DB.checkid(id);
                    if (checkid){
                        Boolean update = DB.updateres(id,departure,arrival,date,total_seats);
                        if (update){
                            // Show a success message and navigate to the main reservation screen.
                            Toast.makeText(updateres.this, "Reservation updated successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), resmain.class);
                            startActivity(intent);
                        }
                        else {
                            // Show an error message if the update fails.
                            Toast.makeText(updateres.this, "New entry not updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        // Show a message if the resrevation ID does not exist in the database.
                        Toast.makeText(updateres.this, "ID doesnot exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), resmain.class);
                startActivity(i);
            }
        });
    }
}