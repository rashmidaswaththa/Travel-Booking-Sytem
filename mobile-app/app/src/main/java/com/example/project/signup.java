package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the "activity_signup" layout.
        setContentView(R.layout.activity_signup);

        //Initializa buttons
        Button btnsignup = findViewById(R.id.btn1);
        Button btnsignin = findViewById(R.id.btn2);
        EditText et1 = findViewById(R.id.et1);
        EditText et2 = findViewById(R.id.et2);
        EditText et3 = findViewById(R.id.et3);
        EditText et4 = findViewById(R.id.et4);
        // Create an instance of the DBHelper class for database operations.
        DBHelper DB = new DBHelper(this);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input for full name, email, username, and password.
                String fullname = et1.getText().toString();
                String email = et2.getText().toString();
                String username = et3.getText().toString();
                String password = et4.getText().toString();

                if (fullname.equals("") ||email.equals("")||username.equals("")||password.equals(""))
                    // Show a message if any of the required fields are empty.
                    Toast.makeText(signup.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    // Check if the username already exists in the database.
                    Boolean checkuser = DB.checkusername(username);
                    if (!checkuser){
                        // If the username doesn't exist, proceed with user registration.
                            Boolean insert = DB.insertuser(fullname,email,username,password);
                            if (insert){
                                // Show a success message and navigate to the login screen.
                                Toast.makeText(signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), login.class);
                                startActivity(intent);
                            }
                            else {
                                // Show an error message if registration fails.
                                Toast.makeText(signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                    }
                    else {
                        // Show a message if the username already exists and provide a sign-in option.
                        Toast.makeText(signup.this, "User Already exists please signin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                    }
                }

            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });
    }
}