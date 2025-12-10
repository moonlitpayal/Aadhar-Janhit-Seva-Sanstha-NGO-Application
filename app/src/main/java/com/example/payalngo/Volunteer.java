package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Volunteer extends AppCompatActivity {

    private EditText nameInput, phoneInput, emailInput, skillsInput, interestInput;
    private Button submitBtn;
    private TextView volunteerWelcome;

    private DatabaseReference volunteerDatabaseReference;
    private DatabaseReference usersDatabaseReference;
    private SessionManager sessionManager;

    // Bottom navigation views
    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volunteer);

        // ✅ Fix for navigation bar collision
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), systemBars.top, v.getPaddingRight(), systemBars.bottom);
            return insets;
        });

        // Initialize SessionManager and Firebase
        sessionManager = new SessionManager(getApplicationContext());
        volunteerDatabaseReference = FirebaseDatabase.getInstance().getReference("Volunteers");
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // UI setup
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        emailInput = findViewById(R.id.emailInput);
        skillsInput = findViewById(R.id.skillsInput);
        interestInput = findViewById(R.id.interestInput);
        submitBtn = findViewById(R.id.submitBtn);
        volunteerWelcome = findViewById(R.id.volunteerWelcome);

        submitBtn.setOnClickListener(view -> submitForm());

        // Load user details using the new reliable method
        loadUserDetailsAndPrefillForm();

        // Setup bottom navigation
        setupBottomNavigation();
    }

    private void loadUserDetailsAndPrefillForm() {
        String userType = sessionManager.getUserType();

        if (userType != null && userType.equals(SessionManager.USER_TYPE_GUEST)) {
            volunteerWelcome.setText("Welcome, Guest!");
            return;
        }

        String userId = sessionManager.getUserId();

        if (userId != null) {
            usersDatabaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullName = snapshot.child("Full Name").getValue(String.class);
                        String phone = snapshot.child("Phone").getValue(String.class);
                        String email = snapshot.child("Email").getValue(String.class);

                        // Set Welcome Message
                        if (fullName != null && !fullName.isEmpty()) {
                            volunteerWelcome.setText("Welcome, " + fullName + "!");
                        } else {
                            volunteerWelcome.setText("Welcome!");
                        }

                        // Pre-fill form fields
                        nameInput.setText(fullName);
                        phoneInput.setText(phone);
                        emailInput.setText(email);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(Volunteer.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void submitForm() {
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String skills = skillsInput.getText().toString().trim();
        String interest = interestInput.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(skills) || TextUtils.isEmpty(interest)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String volunteerId = volunteerDatabaseReference.push().getKey();
        VolunteerData data = new VolunteerData(name, phone, email, skills, interest);

        if (volunteerId != null) {
            volunteerDatabaseReference.child(volunteerId).setValue(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Thank you for volunteering!", Toast.LENGTH_LONG).show();
                    clearForm();
                } else {
                    Toast.makeText(this, "Failed to submit. Try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void clearForm() {
        nameInput.setText("");
        phoneInput.setText("");
        emailInput.setText("");
        skillsInput.setText("");
        interestInput.setText("");
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        home.setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, profile.class)));
    }
}