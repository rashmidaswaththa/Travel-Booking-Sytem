//package com.example.reservationsystem;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import java.util.List;
//
//public class ViewReservationActivity extends AppCompatActivity {
//    RecyclerView recyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_reservation);
//
//        Intent intent = getIntent();
//        String userNIC = intent.getStringExtra("userNIC");
////        System.out.println("NIC :"+userNIC);
//        List<BookingItem> bookingItems = new DatabaseHelper(this).getAllBookings(userNIC);
//        Log.d("ViewReservationActivity", "Number of booking items: " + bookingItems.size());
//
//        // Set up the RecyclerView and adapter
//        recyclerView = findViewById(R.id.recycleview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Create an instance of your custom BookingAdapter and pass the bookingItems
//        BookingAdapter bookingAdapter = new BookingAdapter(bookingItems);
//        recyclerView.setAdapter(bookingAdapter);
//    }
//}

package com.example.reservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class ViewReservationActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation);

        Intent intent = getIntent();
        String userNIC = intent.getStringExtra("userNIC");
        List<BookingItem> bookingItems = new DatabaseHelper(this).getAllBookings(userNIC);
        Log.d("ViewReservationActivity", "Number of booking items: " + bookingItems.size());

        // Set up the RecyclerView and adapter
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create an instance of your custom BookingAdapter and pass the bookingItems and the context
        BookingAdapter bookingAdapter = new BookingAdapter(bookingItems, this);
        recyclerView.setAdapter(bookingAdapter);
    }
}

