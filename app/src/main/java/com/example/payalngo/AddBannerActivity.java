package com.example.payalngo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddBannerActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView bannerPreview;
    private Button selectBannerBtn, uploadBannerBtn;
    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_banner);

        bannerPreview = findViewById(R.id.bannerPreview);
        selectBannerBtn = findViewById(R.id.selectBannerBtn);
        uploadBannerBtn = findViewById(R.id.uploadBannerBtn);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("banners");
        databaseReference = FirebaseDatabase.getInstance().getReference("Banners");

        selectBannerBtn.setOnClickListener(v -> selectImage());
        uploadBannerBtn.setOnClickListener(v -> uploadBanner());
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Banner Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            bannerPreview.setImageURI(imageUri);
            bannerPreview.setVisibility(View.VISIBLE);
        }
    }

    private void uploadBanner() {
        // ✅ New check: Agar user logged-in nahi hai, toh upload mat karo.
        //FirebaseUser user = mAuth.getCurrentUser();
        //if (user == null) {
            //Toast.makeText(this, "You must be logged in to upload a banner.", Toast.LENGTH_SHORT).show();
           // return;
       // }

        if (imageUri != null) {
            StorageReference fileRef = storageReference.child(UUID.randomUUID().toString());
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            saveBannerToDatabase(imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveBannerToDatabase(String imageUrl) {
        String bannerId = databaseReference.push().getKey();
        if (bannerId != null) {
            Map<String, String> bannerData = new HashMap<>();
            bannerData.put("imageURL", imageUrl);
            databaseReference.child(bannerId).setValue(bannerData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Banner uploaded successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}