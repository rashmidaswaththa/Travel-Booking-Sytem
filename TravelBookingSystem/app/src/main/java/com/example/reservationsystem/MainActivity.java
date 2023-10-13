//package com.example.reservationsystem;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.util.Base64;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//public class MainActivity extends AppCompatActivity {
//    private TextView welcome, view;
//    private ImageView profiles;
//    private static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
//    private byte[] imageData,decodedImage; // Store the image data as a byte array
//    private Bitmap bitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        welcome = findViewById(R.id.welcome_txt);
//        profiles = findViewById(R.id.profile_img);
//        view = findViewById(R.id.textView5);
//
//        Intent intent = getIntent();
//        String userName = intent.getStringExtra("userName");
//        imageData = getIntent().getByteArrayExtra("imageBase64");
//        decodedImage = Base64.decode(imageData, Base64.DEFAULT);
//        bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
//
//        // Set the welcome message
//        welcome.setText("Welcome, " + userName);
//        System.out.println("Image :"+imageData);
//        System.out.println("Images :"+bitmap);
//
//        if (imageData != null) {
//            // Check and request the storage permission
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION);
//                System.out.println("HI");
//            } else {
//                // Permission is already granted, load and set the profile photo
//                loadProfileImage();
//            }
//        } else {
//            // Use a default image if imageData is null
//            loadDefaultProfileImage();
//        }
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(intent);
////                finish();
//            }
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, load and set the profile photo
//                loadProfileImage();
//            } else {
//                // Permission denied, load a default image or handle accordingly
//                loadDefaultProfileImage();
//            }
//        }
//    }
//
//    private void loadProfileImage() {
//        if (decodedImage != null) {
//            // Resize the decoded image to 50x50 pixels
//            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
//            profiles.setImageBitmap(resizedBitmap);
//        } else {
//            // Handle the case where the decodedImage is null.
//            loadDefaultProfileImage();
//        }
//    }
//
//
//    private void loadDefaultProfileImage() {
//        // Use a default image
//        Drawable defaultProfileImage = getResources().getDrawable(R.drawable.human);
//        profiles.setImageDrawable(defaultProfileImage);
//    }
//}

package com.example.reservationsystem;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private TextView welcome, view;
    private ImageView profiles;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    private byte[] imageData, decodedImage;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome = findViewById(R.id.welcome_txt);
        profiles = findViewById(R.id.profile_img);
        view = findViewById(R.id.textView5);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String nic = intent.getStringExtra("userNIC");
        String contact = intent.getStringExtra("userContact");
        String email = intent.getStringExtra("userEmail");
        String status = intent.getStringExtra("userStatus");
        imageData = getIntent().getByteArrayExtra("imageBase64");
        decodedImage = Base64.decode(imageData, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

        // Set the welcome message
        welcome.setText("Welcome, " + userName);

        if (imageData != null) {
            // Check and request the storage permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION);
            } else {
                // Permission is already granted, load and set the profile photo
                loadProfileImage();
            }
        } else {
            // Use a default image if imageData is null
            loadDefaultProfileImage();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the user data to ProfileActivity
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                profileIntent.putExtra("userName", userName);
                profileIntent.putExtra("userNIC", nic);
                profileIntent.putExtra("userContact", contact);
                profileIntent.putExtra("userEmail", email);
                profileIntent.putExtra("userStatus", status);
                profileIntent.putExtra("imageData", imageData);
                startActivity(profileIntent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, load and set the profile photo
                loadProfileImage();
            } else {
                // Permission denied, load a default image or handle accordingly
                loadDefaultProfileImage();
            }
        }
    }

    private void loadProfileImage() {
        if (decodedImage != null) {
            // Resize the decoded image to 300x300 pixels
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
            profiles.setImageBitmap(resizedBitmap);
        } else {
            // Handle the case where the decodedImage is null.
            loadDefaultProfileImage();
        }
    }

    private void loadDefaultProfileImage() {
        // Use a default image
        Drawable defaultProfileImage = getResources().getDrawable(R.drawable.human);
        profiles.setImageDrawable(defaultProfileImage);
    }
}

