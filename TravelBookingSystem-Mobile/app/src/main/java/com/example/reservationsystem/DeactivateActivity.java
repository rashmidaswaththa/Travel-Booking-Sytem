package com.example.reservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeactivateActivity extends AppCompatActivity {
    private EditText nic;
    private Button request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deactivate);
        nic = findViewById(R.id.nic_req);
        request = findViewById(R.id.request_btn);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNIC = nic.getText().toString();

                if (!userNIC.isEmpty()) {
                    // Update the user's status to "Active" in the database
                    boolean isActivated = new DatabaseHelper(DeactivateActivity.this)
                            .activateUser(userNIC);

                    if (isActivated) {
                        // Status updated successfully
                        Toast.makeText(DeactivateActivity.this, "User activated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DeactivateActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Status update failed
                        Toast.makeText(DeactivateActivity.this, "Activation failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // NIC is empty, show an error message or handle accordingly
                    Toast.makeText(DeactivateActivity.this, "Please enter a NIC", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}