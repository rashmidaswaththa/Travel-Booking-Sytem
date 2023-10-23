package com.example.reservationsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<BookingItem> bookingItems;
    private Context context; // Store the Context

    public BookingAdapter(List<BookingItem> bookingItems, Context context) {
        this.bookingItems = bookingItems;
        this.context = context; // Store the Context object
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BookingItem item = bookingItems.get(position);

        // Bind data to views in the holder
        holder.passengerNameTextView.setText("Name: " + item.getPassengerName());
        holder.passengerNICTextView.setText("NIC: " + item.getPassengerNIC());
        holder.dateTextView.setText("Date: " + item.getDate());
        holder.fromToTextView.setText("From To: " + item.getPath());
        holder.trainTextView.setText("Train: " + item.getTrain());
        holder.seatCountTextView.setText("Seat No: " + String.valueOf(item.getSeatCount()));
        holder.idTextView.setText("Train: " + item.getId());

        int bookingId = item.getId();
        System.out.println("Ids: "+bookingId);

        // Compare the booking date with the current date
        Date currentDate = getCurrentDate();
        if (item.getDate().before(currentDate)) {
            // Booking date is before the current date, mark as completed
            holder.statusTextView.setText("Status: Completed");
            holder.statusTextView.setTextColor(ContextCompat.getColor(holder.statusTextView.getContext(), R.color.redColor));

            // Disable the "Update" button
            holder.updateButton.setEnabled(false);
        } else {
            // Booking date is in the future, mark as pending
            holder.statusTextView.setText("Status: Pending");
            holder.statusTextView.setTextColor(ContextCompat.getColor(holder.statusTextView.getContext(), R.color.greenColor));

            // Enable the "Update" button
            holder.updateButton.setEnabled(true);

            // Set a click listener for the "Update" button
            holder.updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the button click event, e.g., open the update detail form
                    openUpdateDetailForm(item, view.getContext());
                    Intent intent = new Intent(context, UpdateReservationActivity.class);
                    intent.putExtra("bookingItemID", item.getId());
                    intent.putExtra("bookingItemNIC", item.getPassengerNIC());

                    context.startActivity(intent);
                }
            });
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bookingId = item.getId();
                // Delete the booking item from the database
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                dbHelper.deleteBooking(bookingId);

                // Remove the item from the adapter's list
                bookingItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });

    }




    private Date getCurrentDate() {
        long currentTimeMillis = System.currentTimeMillis();
        return new Date(currentTimeMillis);
    }

    private void openUpdateDetailForm(BookingItem item, Context context) {
        Intent intent = new Intent(context, UpdateReservationActivity.class);
        intent.putExtra("bookingItem", String.valueOf(item));
        context.startActivity(intent);
    }



    @Override
    public int getItemCount() {
        return bookingItems.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        public TextView passengerNICTextView;
        public TextView passengerNameTextView;
        public TextView dateTextView;
        public TextView fromToTextView;
        public TextView trainTextView;
        public TextView seatCountTextView;
        public TextView statusTextView, idTextView;
        public Button updateButton, deleteButton;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            passengerNICTextView = itemView.findViewById(R.id.passengerNic);
            passengerNameTextView = itemView.findViewById(R.id.passengerName);
            dateTextView = itemView.findViewById(R.id.date);
            fromToTextView = itemView.findViewById(R.id.fromTo);
            trainTextView = itemView.findViewById(R.id.train);
            seatCountTextView = itemView.findViewById(R.id.seatCount);
            statusTextView = itemView.findViewById(R.id.status);
            updateButton = itemView.findViewById(R.id.update);
            deleteButton = itemView.findViewById(R.id.delete);
            idTextView = itemView.findViewById(R.id.id);
        }
    }

}



