/*package com.example.payalngo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddContentActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Spinner contentTypeSpinner;
    private EditText titleInput, descInput;
    private ImageView imagePreview;
    private Button selectImageBtn, submitBtn;

    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);

        contentTypeSpinner = findViewById(R.id.contentTypeSpinner);
        titleInput = findViewById(R.id.titleInput);
        descInput = findViewById(R.id.descInput);
        imagePreview = findViewById(R.id.imagePreview);
        selectImageBtn = findViewById(R.id.selectImageBtn);
        submitBtn = findViewById(R.id.submitBtn);

        storageReference = FirebaseStorage.getInstance().getReference("content_images");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.content_types,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contentTypeSpinner.setAdapter(adapter);

        contentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("Stories") || selected.equals("Gallery")) {
                    selectImageBtn.setVisibility(View.VISIBLE);
                    imagePreview.setVisibility(imageUri != null ? View.VISIBLE : View.GONE);
                } else {
                    selectImageBtn.setVisibility(View.GONE);
                    imagePreview.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        selectImageBtn.setOnClickListener(v -> selectImage());
        submitBtn.setOnClickListener(v -> uploadContent());
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
            imagePreview.setVisibility(View.VISIBLE);
        }
    }

    private void uploadContent() {
        String title = titleInput.getText().toString().trim();
        String description = descInput.getText().toString().trim();
        String contentType = contentTypeSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contentType.equals("Stories") || contentType.equals("Gallery")) {
            if (imageUri != null) {
                uploadImageAndSaveData(title, description, contentType);
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        } else {
            saveDataToDatabase(title, description, "", contentType);
        }
    }

    private void uploadImageAndSaveData(String title, String description, String contentType) {
        StorageReference ref = storageReference.child(UUID.randomUUID().toString());
        ref.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageURL = uri.toString();
                        saveDataToDatabase(title, description, imageURL, contentType);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddContentActivity.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveDataToDatabase(String title, String description, String imageURL, String contentType) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(contentType);
        String contentId = databaseReference.push().getKey();

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("title", title);
        contentMap.put("description", description);

        if (!TextUtils.isEmpty(imageURL)) {
            contentMap.put("imageURL", imageURL);
        }

        if (contentId != null) {
            databaseReference.child(contentId).setValue(contentMap)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AddContentActivity.this, "Content uploaded successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddContentActivity.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}*/




package com.example.payalngo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddContentActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Spinner contentTypeSpinner;
    private EditText titleInput, descInput;
    private ImageView imagePreview;
    private Button selectImageBtn, submitBtn;

    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);

        contentTypeSpinner = findViewById(R.id.contentTypeSpinner);
        titleInput = findViewById(R.id.titleInput);
        descInput = findViewById(R.id.descInput);
        imagePreview = findViewById(R.id.imagePreview);
        selectImageBtn = findViewById(R.id.selectImageBtn);
        submitBtn = findViewById(R.id.submitBtn);

        storageReference = FirebaseStorage.getInstance().getReference("image_content");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.content_types,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contentTypeSpinner.setAdapter(adapter);

        contentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("Gallery")) {
                    selectImageBtn.setVisibility(View.VISIBLE);
                    imagePreview.setVisibility(imageUri != null ? View.VISIBLE : View.GONE);
                } else {
                    selectImageBtn.setVisibility(View.GONE);
                    imagePreview.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        selectImageBtn.setOnClickListener(v -> selectImage());
        submitBtn.setOnClickListener(v -> uploadContent());
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
            imagePreview.setVisibility(View.VISIBLE);
        }
    }

    private void uploadContent() {
        String title = titleInput.getText().toString().trim();
        String description = descInput.getText().toString().trim();
        String contentType = contentTypeSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contentType.equals("Gallery")) {
            if (imageUri != null) {
                uploadImageAndSaveData(title, description, contentType);
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        } else {
            saveDataToDatabase(title, description, "", contentType);
        }
    }

    private void uploadImageAndSaveData(String title, String description, String contentType) {
        StorageReference ref = storageReference.child(UUID.randomUUID().toString());
        ref.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageURL = uri.toString();
                        saveDataToDatabase(title, description, imageURL, contentType);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddContentActivity.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveDataToDatabase(String title, String description, String imageURL, String contentType) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(contentType);
        String contentId = databaseReference.push().getKey();

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("title", title);
        contentMap.put("description", description);

        if (!TextUtils.isEmpty(imageURL)) {
            contentMap.put("imageURL", imageURL);
        }

        if (contentId != null) {
            databaseReference.child(contentId).setValue(contentMap)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AddContentActivity.this, "Content uploaded successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddContentActivity.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}

