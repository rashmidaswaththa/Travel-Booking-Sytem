//package com.example.reservationsystem;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import java.util.Calendar;
//
//public class UpdateReservationActivity extends AppCompatActivity {
//    private TextView dateView, nicView, nameView, seatCountView;
//    private Calendar calendar;
//    private Spinner destination, train;
//    private Button update;
//
//    String[] TrainRoad = {"Select Destination", "Pettah - Galle", "Pettah - Kandy", "Kandy - Pettah", "Colombo - Nanuoya", "Jaffna - Colombo"};
//    String[] Trains = {"Select Train", "Sagarika", "Ruhunu Kumari", "Galu Kumari", "Udarata Menike", "Podi Menike"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_reservation);
//        dateView = findViewById(R.id.date_view);
//        nicView = findViewById(R.id.nic_view);
//        nameView = findViewById(R.id.name_view);
//        seatCountView = findViewById(R.id.count_view);
//        calendar = Calendar.getInstance();
//        destination = findViewById(R.id.spinner1);
//        train = findViewById(R.id.spinner2);
//        update = findViewById(R.id.booking_btn);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateReservationActivity.this, android.R.layout.simple_spinner_item, TrainRoad);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        destination.setAdapter(adapter);
//
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(UpdateReservationActivity.this, android.R.layout.simple_spinner_item, Trains);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        train.setAdapter(adapter1);
//    }
//}

package com.example.reservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateReservationActivity extends AppCompatActivity {
    private TextView dateView, nicView, nameView, seatCountView;
    private Calendar calendar;
    private Spinner destination, train;
    private Button update;
    private String nicFromRecyclerView; // NIC from the RecyclerView item

    String[] TrainRoad = {"Select Destination", "Pettah - Galle", "Pettah - Kandy", "Kandy - Pettah", "Colombo - Nanuoya", "Jaffna - Colombo"};
    String[] Trains = {"Select Train", "Sagarika", "Ruhunu Kumari", "Galu Kumari", "Udarata Menike", "Podi Menike"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);
        dateView = findViewById(R.id.date_view);
        nicView = findViewById(R.id.nic_view);
        nameView = findViewById(R.id.name_view);
        seatCountView = findViewById(R.id.count_view);
        calendar = Calendar.getInstance();
        destination = findViewById(R.id.spinner1);
        train = findViewById(R.id.spinner2);
        update = findViewById(R.id.booking_btn);

        nicFromRecyclerView = getIntent().getStringExtra("bookingItemNIC");
        int idFromRecyclerView = getIntent().getIntExtra("bookingItemID", -1); // Default value of -1 if not found

        System.out.println("NIC is:"+ idFromRecyclerView);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateReservationActivity.this, android.R.layout.simple_spinner_item, TrainRoad);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destination.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(UpdateReservationActivity.this, android.R.layout.simple_spinner_item, Trains);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        train.setAdapter(adapter1);

        // Get the NIC from the RecyclerView item
        System.out.println("Nic is:"+ nicFromRecyclerView);

        // Set the NIC field to the retrieved NIC
        nicView.setText(nicFromRecyclerView);
        updateDateLabel();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get data from the views
                String updatedDate = dateView.getText().toString();
                String updatedNIC = nicView.getText().toString();
                String updatedName = nameView.getText().toString();
                String updatedSeatCount = seatCountView.getText().toString();
                String selectedDestination = destination.getSelectedItem().toString();
                String selectedTrain = train.getSelectedItem().toString();

                // Open a connection to the database
                DatabaseHelper dbHelper = new DatabaseHelper(UpdateReservationActivity.this);

                // Create ContentValues with updated values
                ContentValues values = new ContentValues();
                values.put("Date", updatedDate);
                values.put("Passenger_name", updatedName);
                values.put("Seat_count", updatedSeatCount);
                values.put("Path", selectedDestination);
                values.put("Train", selectedTrain);

                if (updatedName.isEmpty() || updatedDate.isEmpty() || selectedDestination.equals("Select Destination") || selectedTrain.equals("Select Train") || updatedSeatCount.isEmpty()) {
                    Toast.makeText(UpdateReservationActivity.this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Prepare the WHERE clause to update the correct booking based on the NIC
                    String whereClause = "Id = ?";
                    String[] whereArgs = {String.valueOf(idFromRecyclerView)};

                    // Update the booking in the database
                    int rowsUpdated = dbHelper.getWritableDatabase().update("booking", values, whereClause, whereArgs);

                    if (rowsUpdated > 0) {
                        // Successfully updated
                        Toast.makeText(UpdateReservationActivity.this, "Updated Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateReservationActivity.this, ReserMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Failed to update
                        Toast.makeText(UpdateReservationActivity.this, "Updated Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void updateDateLabel() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateView.setText(dateFormat.format(calendar.getTime()));
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
