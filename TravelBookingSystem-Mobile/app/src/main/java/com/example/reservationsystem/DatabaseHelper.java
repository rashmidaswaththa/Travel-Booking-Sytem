//
//package com.example.reservationsystem;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//
//import java.io.ByteArrayOutputStream;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//    private static final String DATABASE_NAME = "reservation.db";
//    private static final int DATABASE_VERSION = 1;
//
//    public static final String TABLE_REGISTER = "register";
//    public static final String COLUMN_NIC = "nic";
//    public static final String COLUMN_EMAIL = "email";
//    public static final String COLUMN_NAME = "name";
//    public static final String COLUMN_CONTACT = "contact";
//    public static final String COLUMN_PASSWORD = "password";
//    public static final String COLUMN_IMAGE_BLOB = "image_blob";
//    public static final String COLUMN_STATUS = "status"; // New "Status" column
//
//    private static final String TABLE_CREATE =
//            "CREATE TABLE " + TABLE_REGISTER + " (" +
//                    COLUMN_NIC + " TEXT PRIMARY KEY, " +
//                    COLUMN_EMAIL + " TEXT, " +
//                    COLUMN_NAME + " TEXT, " +
//                    COLUMN_CONTACT + " TEXT, " +
//                    COLUMN_PASSWORD + " TEXT, " +
//                    COLUMN_IMAGE_BLOB + " BLOB, " +
//                    COLUMN_STATUS + " TEXT DEFAULT 'Active');";
//
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(TABLE_CREATE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
//        onCreate(db);
//    }
//
//    public boolean updateUser(String nic, String name, String contact, String email, byte[] imageBlob) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, name);
//        values.put(COLUMN_CONTACT, contact);
//        values.put(COLUMN_EMAIL, email);
//        values.put(COLUMN_IMAGE_BLOB, imageBlob);
//
//        // Specify the WHERE clause to update the row with a matching NIC
//        String whereClause = COLUMN_NIC + " = ?";
//        String[] whereArgs = { nic };
//
//        int rowsAffected = db.update(TABLE_REGISTER, values, whereClause, whereArgs);
//        db.close();
//        return rowsAffected > 0;
//    }
//
//    public boolean updateUserWithoutImage(String userNIC, String updatedName, String updatedContact, String updatedEmail) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, updatedName);
//        values.put(COLUMN_CONTACT, updatedContact);
//        values.put(COLUMN_EMAIL, updatedEmail);
//
//        // Specify the WHERE clause to update the row with a matching NIC
//        String whereClause = COLUMN_NIC + " = ?";
//        String[] whereArgs = { userNIC };
//
//        int rowsAffected = db.update(TABLE_REGISTER, values, whereClause, whereArgs);
//        db.close();
//        return rowsAffected > 0;
//    }
//
//    public boolean updateUserStatus(String nic, String newStatus) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_STATUS, newStatus); // Assuming COLUMN_STATUS is the name of the status column
//
//        String whereClause = COLUMN_NIC + " = ?";
//        String[] whereArgs = { nic };
//
//        int rowsUpdated = db.update(TABLE_REGISTER, values, whereClause, whereArgs);
//
//        // Close the database
//        db.close();
//
//        // Check if the update was successful (rowsUpdated > 0)
//        return rowsUpdated > 0;
//    }
//
//    public boolean activateUser(String nic) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_STATUS, "Active"); // Assuming COLUMN_STATUS is the name of the status column
//
//        String whereClause = COLUMN_NIC + " = ?";
//        String[] whereArgs = { nic };
//
//        int rowsUpdated = db.update(TABLE_REGISTER, values, whereClause, whereArgs);
//
//        // Close the database
//        db.close();
//
//        // Check if the update was successful (rowsUpdated > 0)
//        return rowsUpdated > 0;
//    }
//
//
//}
//

package com.example.reservationsystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "reservation.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_REGISTER = "register";
    public static final String COLUMN_NIC = "nic";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IMAGE_BLOB = "image_blob";
    public static final String COLUMN_STATUS = "status"; // New "Status" column

    // New table for bookings
    public static final String TABLE_BOOKING = "booking";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_PASSENGER_NIC = "Passenger_NIC";
    public static final String COLUMN_PASSENGER_NAME = "Passenger_name";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_PATH = "Path";
    public static final String COLUMN_TRAIN = "Train";
    public static final String COLUMN_SEAT_COUNT = "Seat_count";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_REGISTER + " (" +
                    COLUMN_NIC + " TEXT PRIMARY KEY, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CONTACT + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_IMAGE_BLOB + " BLOB, " +
                    COLUMN_STATUS + " TEXT DEFAULT 'Active');";

    private static final String TABLE_BOOKING_CREATE =
            "CREATE TABLE " + TABLE_BOOKING + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PASSENGER_NIC + " TEXT, " +
                    COLUMN_PASSENGER_NAME + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_PATH + " TEXT, " +
                    COLUMN_TRAIN + " TEXT, " +
                    COLUMN_SEAT_COUNT + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_BOOKING_CREATE); // Create the "booking" table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING); // Drop the "booking" table
        onCreate(db);
    }

    public boolean updateUser(String nic, String name, String contact, String email, byte[] imageBlob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_IMAGE_BLOB, imageBlob);

        // Specify the WHERE clause to update the row with a matching NIC
        String whereClause = COLUMN_NIC + " = ?";
        String[] whereArgs = { nic };

        int rowsAffected = db.update(TABLE_REGISTER, values, whereClause, whereArgs);
        db.close();
        return rowsAffected > 0;
    }

    public boolean updateUserWithoutImage(String userNIC, String updatedName, String updatedContact, String updatedEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, updatedName);
        values.put(COLUMN_CONTACT, updatedContact);
        values.put(COLUMN_EMAIL, updatedEmail);

        // Specify the WHERE clause to update the row with a matching NIC
        String whereClause = COLUMN_NIC + " = ?";
        String[] whereArgs = { userNIC };

        int rowsAffected = db.update(TABLE_REGISTER, values, whereClause, whereArgs);
        db.close();
        return rowsAffected > 0;
    }

    public boolean updateUserStatus(String nic, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, newStatus);

        String whereClause = COLUMN_NIC + " = ?";
        String[] whereArgs = { nic };

        int rowsUpdated = db.update(TABLE_REGISTER, values, whereClause, whereArgs);
        db.close();
        return rowsUpdated > 0;
    }

    public long insertBooking(String passengerNIC, String passengerName, String date, String path, String train, int seatCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSENGER_NIC, passengerNIC);
        contentValues.put(COLUMN_PASSENGER_NAME, passengerName);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_PATH, path);
        contentValues.put(COLUMN_TRAIN, train);
        contentValues.put(COLUMN_SEAT_COUNT, seatCount);

        // Insert the data and get the auto-generated primary key
        return db.insert(TABLE_BOOKING, null, contentValues);
    }


    public boolean activateUser(String nic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, "Active");

        String whereClause = COLUMN_NIC + " = ?";
        String[] whereArgs = { nic };

        int rowsUpdated = db.update(TABLE_REGISTER, values, whereClause, whereArgs);
        db.close();
        return rowsUpdated > 0;
    }

//    public List<BookingItem> getBookingsForUser(String userNIC) {
//        List<BookingItem> bookings = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = {COLUMN_ID, COLUMN_PASSENGER_NAME, COLUMN_DATE, COLUMN_PATH, COLUMN_TRAIN, COLUMN_SEAT_COUNT, COLUMN_PASSENGER_NIC};
//        String selection = COLUMN_PASSENGER_NIC + " = ?";
//        String[] selectionArgs = {userNIC};
//        Cursor cursor = db.query(TABLE_BOOKING, columns, selection, selectionArgs, null, null, null);
//
//        Log.d("getBookingsForUser", "Query: " + selection);
//        Log.d("getBookingsForUser", "Selection Args: " + Arrays.toString(selectionArgs));
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                // Log.d("BookingItem", "Found Booking for User: " + userNIC);
//                System.out.println("Nic: " + userNIC);
//
//                for (int i = 0; i < cursor.getCount(); i++) {
//                    @SuppressLint("Range") String passengerNIC = cursor.getString(cursor.getColumnIndex(COLUMN_PASSENGER_NIC));
//                    @SuppressLint("Range") String passengerName = cursor.getString(cursor.getColumnIndex(COLUMN_PASSENGER_NAME));
//                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
//                    @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(COLUMN_PATH));
//                    @SuppressLint("Range") String train = cursor.getString(cursor.getColumnIndex(COLUMN_TRAIN));
//                    @SuppressLint("Range") int seatCount = cursor.getInt(cursor.getColumnIndex(COLUMN_SEAT_COUNT));
//                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
//
//                    BookingItem bookingItem = new BookingItem(passengerNIC, passengerName, date, path, train, seatCount, id);
//                    bookings.add(bookingItem);
//
//                    // Move to the next row
//                    cursor.moveToNext();
//                }
//            } while (!cursor.isAfterLast());
//
//            cursor.close();
//        } else {
//            Log.d("getBookingsForUser", "No Booking found for User: " + userNIC);
//        }
//
//        return bookings;
//    }


    public List<BookingItem> getAllBookings(String userNIC) {
        List<BookingItem> bookings = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_PASSENGER_NAME, COLUMN_DATE, COLUMN_PATH, COLUMN_TRAIN, COLUMN_SEAT_COUNT, COLUMN_PASSENGER_NIC};
        // No need for the NIC-based filter
        Cursor cursor = db.query(TABLE_BOOKING, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String passengerNIC = cursor.getString(cursor.getColumnIndex(COLUMN_PASSENGER_NIC));
                @SuppressLint("Range") String passengerName = cursor.getString(cursor.getColumnIndex(COLUMN_PASSENGER_NAME));
                @SuppressLint("Range") Date date = Date.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(COLUMN_PATH));
                @SuppressLint("Range") String train = cursor.getString(cursor.getColumnIndex(COLUMN_TRAIN));
                @SuppressLint("Range") int seatCount = cursor.getInt(cursor.getColumnIndex(COLUMN_SEAT_COUNT));
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));

                BookingItem bookingItem = new BookingItem(passengerNIC, passengerName, date, path, train, seatCount,id);
                    bookings.add(bookingItem);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.d("BookingItem", "No Bookings found.");
        }

        return bookings;
    }

    public void deleteBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = new String[] { String.valueOf(bookingId) };
        db.delete("booking", whereClause, whereArgs);
        db.close();
    }


}

