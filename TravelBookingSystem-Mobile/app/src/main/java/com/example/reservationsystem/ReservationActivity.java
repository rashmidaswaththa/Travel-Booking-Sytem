//package com.example.reservationsystem;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Locale;
//
//public class ReservationActivity extends AppCompatActivity {
//    private TextView dateView;
//    private Calendar calendar;
//    private Spinner destination, train;
//    private Button booking;
//
//    String[] TrainRoad = {"Select Destination","Pettah - Galle","Pettah - Kandy", "Kandy - pettah", "Colombo - Nanuoya", "Jaffna - Colombo"};
//    String[] Trains = {"Select Train","Sagarika", "Ruhunu Kumari", "Galu Kumari", "Udarata Menike","Podi Menike"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reservation);
//        dateView = findViewById(R.id.date_view);
//        calendar = Calendar.getInstance();
//        destination = findViewById(R.id.spinner1);
//        train = findViewById(R.id.spinner2);
//        booking = findViewById(R.id.booking_btn);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReservationActivity.this, android.R.layout.simple_spinner_item,TrainRoad);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        destination.setAdapter(adapter);
//
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ReservationActivity.this, android.R.layout.simple_spinner_item,Trains);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        train.setAdapter(adapter1);
//
//        dateView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//            }
//        });
//
//        dateView.setCompoundDrawablePadding(10); // Add padding between text and drawable
//    }
//
//    private void showDatePickerDialog() {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener,
//                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.show();
//    }
//
//    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            calendar.set(year, month, dayOfMonth);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//            String selectedDate = dateFormat.format(calendar.getTime());
//            dateView.setText(selectedDate);
//        }
//    };
//}

package com.example.reservationsystem;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReservationActivity extends AppCompatActivity {
    private TextView dateView, nicView, nameView, seatCountView;
    private Calendar calendar;
    private Spinner destination, train;
    private Button booking;

    String[] TrainRoad = {"Select Destination", "Pettah - Galle", "Pettah - Kandy", "Kandy - Pettah", "Colombo - Nanuoya", "Jaffna - Colombo"};
    String[] Trains = {"Select Train", "Sagarika", "Ruhunu Kumari", "Galu Kumari", "Udarata Menike", "Podi Menike"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        dateView = findViewById(R.id.date_view);
        nicView = findViewById(R.id.nic_view);
        nameView = findViewById(R.id.name_view);
        seatCountView = findViewById(R.id.count_view);
        calendar = Calendar.getInstance();
        destination = findViewById(R.id.spinner1);
        train = findViewById(R.id.spinner2);
        booking = findViewById(R.id.booking_btn);

        Intent intent = getIntent();
        String nic = intent.getStringExtra("userNIC");

        nicView.setText("  "+nic);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReservationActivity.this, android.R.layout.simple_spinner_item, TrainRoad);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destination.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ReservationActivity.this, android.R.layout.simple_spinner_item, Trains);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        train.setAdapter(adapter1);



        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        dateView.setCompoundDrawablePadding(10); // Add padding between text and drawable

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the data from UI elements
                String nic = nicView.getText().toString();
                String name = nameView.getText().toString();
                String selectedDate = dateView.getText().toString();
                String selectedDestination = destination.getSelectedItem().toString();
                String selectedTrain = train.getSelectedItem().toString();
                String seatCount = seatCountView.getText().toString();

                // Check if any of the required fields are empty
                if (nic.isEmpty() || name.isEmpty() || selectedDate.isEmpty() || selectedDestination.equals("Select Destination") || selectedTrain.equals("Select Train") || seatCount.isEmpty()) {
                    Toast.makeText(ReservationActivity.this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert the data into the 'booking' table
                    long result = new DatabaseHelper(ReservationActivity.this)
                            .insertBooking(nic, name, selectedDate, selectedDestination, selectedTrain, Integer.parseInt(seatCount));

                    if (result != -1) {
                        nameView.setText("");
                        dateView.setText("");
                        destination.setAdapter(adapter);
                        train.setAdapter(adapter1);
                        seatCountView.setText("");
                        Toast.makeText(ReservationActivity.this, "Booking confirmed.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ReservationActivity.this, "Booking failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = dateFormat.format(calendar.getTime());
            dateView.setText(selectedDate);
        }
    };
}
