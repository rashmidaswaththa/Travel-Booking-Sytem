
package com.example.reservationsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
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

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_REGISTER + " (" +
                    COLUMN_NIC + " TEXT PRIMARY KEY, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CONTACT + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_IMAGE_BLOB + " BLOB, " +
                    COLUMN_STATUS + " TEXT DEFAULT 'Active');";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
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
}

