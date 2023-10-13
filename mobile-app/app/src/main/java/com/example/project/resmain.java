package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class resmain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the "activity_resmain" layout.
        setContentView(R.layout.activity_resmain);

        //Initializing add, update, delete, view, and logging out.
        Button add = findViewById(R.id.add);
        Button update=findViewById(R.id.update);
        Button delete=findViewById(R.id.delete);
        Button view = findViewById(R.id.view);
        Button end = findViewById(R.id.logout);

        // Create an instance of the DBHelper class for database operations.
        DBHelper DB = new DBHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the "addres" activity (add reservation screen).
                Intent intent = new Intent(getApplicationContext(), addres.class);
                // Start the "addres" activity.
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the "updateres" activity (update reservation screen).
                Intent intent = new Intent(getApplicationContext(), updateres.class);
                // Start the "updateres" activity.
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the "deleteres" activity (delete reservation screen).
                Intent intent = new Intent(getApplicationContext(), deleteres.class);
                // Start the "deleteres" activity.
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Query the database for all reservation records.
                Cursor result = DB.viewres();
                if (result.getCount()==0){
                    // Show a message if no reservation records exist.
                    Toast.makeText(resmain.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Build a string to display reservation details.
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()){
                    buffer.append("ID :"+result.getString(0)+"\n");
                    buffer.append("Departure :"+result.getString(1)+"\n");
                    buffer.append("Arrival :"+result.getString(2)+"\n");
                    buffer.append("Date :"+result.getString(3)+"\n");
                    buffer.append("Total Seats :"+result.getString(4)+"\n\n");
                }
                // Create an AlertDialog to display reservation details.
                AlertDialog.Builder builder = new AlertDialog.Builder(resmain.this);
                builder.setCancelable(true);
                builder.setTitle("ALL TRAIN RESERVATIONS MADE");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate back to the main activity (login screen).
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

            }
        }

