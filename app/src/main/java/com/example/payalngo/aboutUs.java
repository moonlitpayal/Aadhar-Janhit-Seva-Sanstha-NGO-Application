/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class aboutUs extends AppCompatActivity {

    private TextView welcomeText;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);

        // Firebase init
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Bind Views
        welcomeText = findViewById(R.id.welcome);

        // Setup navigation
        setupBottomNavigation();

        // Set welcome name
        fetchUserName();
    }

    private void fetchUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            welcomeText.setText("Welcome Guest!");
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        databaseReference.child(userId).child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    welcomeText.setText("Welcome, " + name + "!");
                } else {
                    welcomeText.setText("Welcome!");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                welcomeText.setText("Welcome!");
                Toast.makeText(getApplicationContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        home.setOnClickListener(v -> startActivity(new Intent(aboutUs.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(aboutUs.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(aboutUs.this, storie.class)));
        aboutus.setOnClickListener(v -> Toast.makeText(this, "Already on About Us", Toast.LENGTH_SHORT).show());
        profile.setOnClickListener(v -> startActivity(new Intent(aboutUs.this, profile.class)));
    }
}*/

package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

// ✅ FirebaseAuth and FirebaseUser imports are no longer needed
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class aboutUs extends AppCompatActivity {

    private TextView welcomeText;
    private DatabaseReference databaseReference;

    // ✅ Step 1: Declare SessionManager
    private SessionManager sessionManager;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);

        // ✅ Step 2: Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Bind Views
        welcomeText = findViewById(R.id.welcome);

        // Setup navigation
        setupBottomNavigation();

        // ✅ Step 3: Call the new, reliable function
        loadUserDetails();
    }

    /**
     * ✅ FINAL LOGIC: This new function uses SessionManager to fetch user details.
     */
    private void loadUserDetails() {
        String userType = sessionManager.getUserType();

        // Check if the user is a GUEST
        if (userType != null && userType.equals(SessionManager.USER_TYPE_GUEST)) {
            welcomeText.setText("Welcome, Guest!");
            return;
        }

        // If it's a REGULAR user, get the UID from the session
        String userId = sessionManager.getUserId();

        if (userId != null) {
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullName = snapshot.child("Full Name").getValue(String.class);
                        if (fullName != null && !fullName.isEmpty()) {
                            welcomeText.setText("Welcome, " + fullName + "!");
                        } else {
                            welcomeText.setText("Welcome!");
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    welcomeText.setText("Welcome!");
                    Toast.makeText(getApplicationContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Fallback for safety
            welcomeText.setText("Welcome, Guest!");
        }
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        setSelectedIcon(aboutus); // Is screen ka icon selected hai

        home.setOnClickListener(v -> {
            startActivity(new Intent(aboutUs.this, HomeActivity.class));
        });
        donation.setOnClickListener(v -> {
            startActivity(new Intent(aboutUs.this, donation.class));
        });
        story.setOnClickListener(v -> {
            startActivity(new Intent(aboutUs.this, storie.class));
        });
        aboutus.setOnClickListener(v -> {
            Toast.makeText(this, "Already on About Us", Toast.LENGTH_SHORT).show();
        });
        profile.setOnClickListener(v -> {
            startActivity(new Intent(aboutUs.this, profile.class));
        });
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        // Sabhi icons ko default color do
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));

        // Selected icon ko black color do
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }
}