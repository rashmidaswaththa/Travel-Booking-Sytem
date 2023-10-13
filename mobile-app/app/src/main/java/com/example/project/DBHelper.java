package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    //define the database name
    public static final String DBNAME = "ProjectNew.db";

    // Constructor for DBHelper class
    public DBHelper(Context context) {
        super(context, "Project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        //create usertable
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, email TEXT, fullname TEXT)");

        //create reservation table
        MyDB.execSQL("create Table res(id INTEGER primary key autoincrement not null, departure TEXT, arrival TEXT, date TEXT, total_seats TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
       //drop the users table
        MyDB.execSQL("drop Table if exists users");
        //drop the reservation table
        MyDB.execSQL("drop Table if exists res");
    }

    public Boolean insertData(String fullname, String email, String username, String password)
    {
        //get a writable database
        SQLiteDatabase MyDB = this.getWritableDatabase();

        //create contentValues for data
        ContentValues contentValues = new ContentValues();

        //put data into ContentValues
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);

        //inseert data into DB
        long result = MyDB.insert("users",null,contentValues);
        return result != -1;
    }


    public boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        //return if the username exists
        return cursor.getCount() > 0;
    }

    public Boolean insertRes(String departure, String arrival, String date, String total_seats)
    {
        //get a writable database
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //create contentValues for data
        ContentValues contentValues = new ContentValues();

        //put data into ContentValues
        contentValues.put("departure", departure);
        contentValues.put("arrival", arrival);
        contentValues.put("date", date);
        contentValues.put("total_seats", total_seats);

        //insert reservation data into the database
        long result = MyDB.insert("res",null,contentValues);
        return result != -1;
    }

    // Update Reservation data in the 'res'
    public Boolean updateres(String id, String departure, String arrival, String date, String total_seats)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("departure", departure);
        contentValues.put("arrival", arrival);
        contentValues.put("date", date);
        contentValues.put("total_seats", total_seats);
        Cursor cursor = MyDB.rawQuery("Select * from res where id = ?", new String[] {id});
        if (cursor.getCount()>0){
            //update data in res table
        long result = MyDB.update("res", contentValues,"id=?", new String[] {id});
            return result != -1;
        }
        else
            {
                return false;
            }
    }

    // Delete reservation data from the 'res'
    public Boolean deleteres(String id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from res where id = ?", new String[] {id});
        if (cursor.getCount()>0){
            long result = MyDB.delete("res","id=?", new String[] {id});
            return result != -1;
        }
        else
            {
                return false;
            }
    }

    // Retrieve all records from the 'res'
    public Cursor viewres()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from res", null);
        return cursor;
    }



    public boolean checkid(String id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from res where id = ?", new String[] {id});
        return cursor.getCount() > 0;
    }

















}
