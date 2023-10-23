package com.example.reservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReserMainActivity extends AppCompatActivity {
    private TextView reservation, viewRes;
    String nic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser_main);
        reservation = findViewById(R.id.reser_txt);
        viewRes = findViewById(R.id.viewres_txt);

        Intent intent = getIntent();
        nic = intent.getStringExtra("userNIC");

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(ReserMainActivity.this, ReservationActivity.class);
                profileIntent.putExtra("userNIC", nic);
                startActivity(profileIntent);
            }
        });

        viewRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(ReserMainActivity.this, ViewReservationActivity.class);
                profileIntent.putExtra("userNIC", nic);
                startActivity(profileIntent);
            }
        });
    }
}