package com.example.reservationsystem;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {
    private EditText nic, email, name, contact, password, cpassword;
    private Button register, login;
    private ImageView profile;
    boolean passvisible;
    private byte[] imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nic = findViewById(R.id.sign_nic);
        email = findViewById(R.id.sign_email);
        name = findViewById(R.id.sign_name);
        contact = findViewById(R.id.sign_contact);
        password = findViewById(R.id.sign_pw);
        cpassword = findViewById(R.id.sign_cpw);
        register = findViewById(R.id.reg);
        login = findViewById(R.id.loginbtn);
        profile = findViewById(R.id.imageProfile);

        password.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passvisible) {
                            // set drawable img here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            // for password hide
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_remove_red_eye_24, 0);
                            // for password show
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

        cpassword.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= cpassword.getRight() - cpassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = cpassword.getSelectionEnd();
                        if (passvisible) {
                            // set drawable img here
                            cpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            // for password hide
                            cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible = false;
                        } else {
                            cpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_remove_red_eye_24, 0);
                            // for password show
                            cpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible = true;
                        }
                        cpassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nicValue = nic.getText().toString();
                String emailValue = email.getText().toString();
                String nameValue = name.getText().toString();
                String contactValue = contact.getText().toString();
                String passwordValue = password.getText().toString();
                String confirmPasswordValue = cpassword.getText().toString();

                if (nicValue.isEmpty() || emailValue.isEmpty() || nameValue.isEmpty() || contactValue.isEmpty() || passwordValue.isEmpty() || confirmPasswordValue.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else if (passwordValue.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else if (!passwordValue.equals(confirmPasswordValue)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Create the database for writing
                    SQLiteDatabase database = new DatabaseHelper(RegisterActivity.this).getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_NIC, nicValue);
                    values.put(DatabaseHelper.COLUMN_EMAIL, emailValue);
                    values.put(DatabaseHelper.COLUMN_NAME, nameValue);
                    values.put(DatabaseHelper.COLUMN_CONTACT, contactValue);
                    values.put(DatabaseHelper.COLUMN_PASSWORD, passwordValue);
                    values.put(DatabaseHelper.COLUMN_IMAGE_BLOB, imageData);

                    // Insert the values into the register table
                    long newRowId = database.insert(DatabaseHelper.TABLE_REGISTER, null, values);

                    if (newRowId != -1) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                    // Close the database
//                    database.close();
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage = new Intent(Intent.ACTION_PICK);
                pickImage.setType("image/*");
                startActivityForResult(pickImage, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    imageData = getBytes(inputStream); // Convert image to byte array
                    // You can also load and display the selected image using Glide here
                    Glide.with(this).load(imageUri).into(profile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
