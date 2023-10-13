package com.example.reservationsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity {
    private EditText nic, password;
    private Chip login;
    private Button sign;
    boolean passvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nic = findViewById(R.id.log_nic);
        password = findViewById(R.id.log_pw);
        login = findViewById(R.id.log_btn);
        sign = findViewById(R.id.sign_btn);

        password.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = password.getSelectionEnd();
                        if(passvisible){
                            //set draweble img here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            //for password hide
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible = false;
                        }else{
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_remove_red_eye_24, 0);
                            //for password show
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nicValue = nic.getText().toString();
                String passwordValue = password.getText().toString();

                // Create or open the database for reading
                SQLiteDatabase database = new DatabaseHelper(LoginActivity.this).getReadableDatabase();

                // Define the columns you want to retrieve
                String[] projection = {
                        DatabaseHelper.COLUMN_NAME, // Add the name column
                        DatabaseHelper.COLUMN_PASSWORD,
                        DatabaseHelper.COLUMN_IMAGE_BLOB,
                        DatabaseHelper.COLUMN_STATUS,
                        DatabaseHelper.COLUMN_CONTACT,
                        DatabaseHelper.COLUMN_EMAIL
                };

                // Define the WHERE clause to filter by NIC
                String selection = DatabaseHelper.COLUMN_NIC + " = ?";
                String[] selectionArgs = { nicValue };

                // Query the database
                Cursor cursor = database.query(
                        DatabaseHelper.TABLE_REGISTER,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

                if (cursor.moveToFirst()) {
                    // NIC found in the database
                    @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));

                    if (storedPassword.equals(passwordValue)) {
                        // Password is correct
                        @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                        @SuppressLint("Range") byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_BLOB));
                        @SuppressLint("Range") String userStatus = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS));
                        @SuppressLint("Range") String userContactValue = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT));
                        @SuppressLint("Range") String userEmailValue = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));

                        // Convert the image blob to Base64
                        String imageBase64 = Base64.encodeToString(imageBlob, Base64.DEFAULT);

                        // Decide which activity to open based on the user's status
//                        Class<?> targetActivityClass = userStatus.equals("Active") ? MainActivity.class : ProfileActivity.class;
                        if(userStatus.equals("Active")){
                            // Pass the user data to the target activity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userName", userName); // Pass user data to ProfileActivity
                            intent.putExtra("userNIC", nicValue);
                            intent.putExtra("userContact", userContactValue);
                            intent.putExtra("userEmail", userEmailValue);
                            intent.putExtra("userStatus", userStatus);
                            intent.putExtra("imageBase64", imageBase64.getBytes());
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(LoginActivity.this, DeactivateActivity.class);
                            intent.putExtra("userName", userName);
                            intent.putExtra("imageBase64", imageBase64.getBytes());
                            startActivity(intent);
                            finish();
                        }



                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "NIC not found", Toast.LENGTH_SHORT).show();
                }

                // Close the database and cursor
                cursor.close();
                database.close();
            }
        });


    }
}