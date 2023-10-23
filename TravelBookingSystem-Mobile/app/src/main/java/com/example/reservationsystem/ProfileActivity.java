package com.example.reservationsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    private TextView name, nic, contact, email, status, changeImg;
    private ImageView profile;
    private byte[] imageData, decodedImage, updatedImageData;
    private Bitmap bitmap;
    private Button update, deactivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.name_txt);
        nic = findViewById(R.id.nic_txt);
        contact = findViewById(R.id.cont_txt);
        email = findViewById(R.id.email_txt);
        status = findViewById(R.id.status_txt);
        profile = findViewById(R.id.imageView);
        update = findViewById(R.id.update_btn);
        deactivate = findViewById(R.id.deactivate_btn);
        changeImg = findViewById(R.id.change_txt);

        changeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage = new Intent(Intent.ACTION_PICK);
                pickImage.setType("image/*");
                startActivityForResult(pickImage, 1);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated user data
                String updatedName = name.getText().toString();
                String updatedContact = contact.getText().toString();
                String updatedEmail = email.getText().toString();
                String userNIC = nic.getText().toString();

                // Check if an updated image is available
                if (updatedImageData != null) {
                    // Update the user's data in the database with the updated image
                    boolean isUpdated = new DatabaseHelper(ProfileActivity.this)
                            .updateUser(userNIC, updatedName, updatedContact, updatedEmail, updatedImageData);

                    if (isUpdated) {
                        // Data updated successfully
                        showPopupMessage();
                    } else {
                        // Data update failed
                        Toast.makeText(ProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // No updated image selected, update the user's data without changing the image
                    boolean isUpdated = new DatabaseHelper(ProfileActivity.this)
                            .updateUserWithoutImage(userNIC, updatedName, updatedContact, updatedEmail);

                    if (isUpdated) {
                        // Data updated successfully
                        showPopupMessage();
                    } else {
                        // Data update failed
                        Toast.makeText(ProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNIC = nic.getText().toString();

                // Update the user's status to "Deactivate" in the database
                boolean isDeactivated = new DatabaseHelper(ProfileActivity.this)
                        .updateUserStatus(userNIC, "Deactivate");

                if (isDeactivated) {
                    // Status updated successfully
                    // You can show a toast message or perform other actions as needed
                    Toast.makeText(ProfileActivity.this, "User deactivated", Toast.LENGTH_SHORT).show();
//                    showPopupMessage();
                    Intent intent = new Intent(ProfileActivity.this, DeactivateActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Status update failed
                    // Handle the error, e.g., show an error message
                    Toast.makeText(ProfileActivity.this, "Deactivation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Retrieve the data sent from LoginActivity
        Intent intent = getIntent();
        if (intent != null) {
            String userName = intent.getStringExtra("userName");
            String userNIC = intent.getStringExtra("userNIC");
            String userContact = intent.getStringExtra("userContact");
            String userEmail = intent.getStringExtra("userEmail");
            String userStatus = intent.getStringExtra("userStatus");
            imageData = getIntent().getByteArrayExtra("imageData");
            decodedImage = Base64.decode(imageData, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

            // Set the retrieved data in the corresponding views
            name.setText(userName);
            nic.setText(userNIC);
            contact.setText(userContact);
            email.setText(userEmail);
            status.setText(userStatus);

            // Load and display the user's profile image (you can use the Base64 image data here)
            if (decodedImage != null) {
                // Resize the decoded image to 300x300 pixels
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
                profile.setImageBitmap(resizedBitmap);
            } else {
                // Handle the case where the decodedImage is null.
                Drawable defaultProfileImage = getResources().getDrawable(R.drawable.humans);
                profile.setImageDrawable(defaultProfileImage);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    // Convert the selected image to a byte array
                    updatedImageData = getBytes(inputStream);

                    // Load and display the selected image using Glide
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

    private void showPopupMessage() {
        // Create a dialog with a custom layout
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.popup_message, null);
        dialogBuilder.setView(dialogView);
        AlertDialog popupDialog = dialogBuilder.create();

        // Get references to the message text and the "Logout" button
        TextView messageText = dialogView.findViewById(R.id.message_text);
        Button logoutButton = dialogView.findViewById(R.id.logout_button);

        // Set the message text
        messageText.setText("You have to re-login to the system");

        // Set the click listener for the "Logout" button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the logout action here, e.g., navigate to the login screen and finish the current activity
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                // Dismiss the pop-up dialog
                popupDialog.dismiss();
            }
        });

        // Show the pop-up dialog
        popupDialog.show();
    }
}
