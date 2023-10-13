package com.example.project; //package declaration

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class deleteres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set content view to the "deleteres" layout.
        setContentView(R.layout.activity_deleteres);

        //Initialize an EditText for inputting reservation ID.
        EditText resid = findViewById(R.id.resid);
        // Initialize a Button for deleting a reservation entry.
        Button deletres = findViewById(R.id.deleteres);
        // Initialize a Button for returning to the main reservation screen.
        Button back = findViewById(R.id.button03);
        ); // Create an instance of the DBHelper class for database operations.
        DBHelper DB = new DBHelper(this);

        deleteres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the res ID entered by the user as a string.
                String id = resid.getText().toString();
                if (id.equals(""))
                {
                    // Show a toast message if the user did not enter a res ID.
                    Toast.makeText(deleteres.this, "Please Enter Reservation ID", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Check if the entered res ID exists in the database.
                    Boolean checkid= DB.checkid(id);
                    if (checkid){
                        // If the res ID exists, proceed to delete the entry.
                        Boolean delete = DB.deleteres(id);
                        if (delete){
                            // Show a success message and navigate to the main reservation screen.
                            Toast.makeText(deleteres.this, "Reservation deleted successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), resmain.class);
                            startActivity(intent);
                        }
                        else {
                            // Show an error message if the entry was not deleted.
                            Toast.makeText(deleteres.this, "Entry not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        // Show a message if the entered res ID does not exist in the database.
                        Toast.makeText(deleteres.this, "ID doesnot exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), resmain.class);
                startActivity(intent);
            }
        });

    }
}