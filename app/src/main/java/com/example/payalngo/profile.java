package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat; // This import is already here and is needed
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    private TextView welcomeText, fullNameText, emailText, usernameText, phoneText;
    private Button logoutButton, donationHisProfile, changePasswordButton;

    private CardView userDetailsLayout;
    private TextView guestMessageText;

    private DatabaseReference databaseReference;
    private SessionManager sessionManager;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(getApplicationContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        initializeViews();

        logoutButton.setOnClickListener(v -> {
            sessionManager.logoutUser();
            finish();
        });

        fetchUserProfile();
        setupBottomNavigation(); // This method now highlights the icon

        donationHisProfile.setOnClickListener(v -> startActivity(new Intent(profile.this, DonationHistory.class)));
        changePasswordButton.setOnClickListener(v -> startActivity(new Intent(profile.this, ChangePasswordActivity.class)));
    }

    private void initializeViews() {
        welcomeText = findViewById(R.id.welcome);
        fullNameText = findViewById(R.id.fullnameText);
        emailText = findViewById(R.id.emailText);
        usernameText = findViewById(R.id.usernameText);
        phoneText = findViewById(R.id.phoneText);
        logoutButton = findViewById(R.id.logoutButton);
        donationHisProfile = findViewById(R.id.btnDonationHistory);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        userDetailsLayout = findViewById(R.id.user_details_layout);
        guestMessageText = findViewById(R.id.guest_message_text);

        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile_icon = findViewById(R.id.profileid);
    }

    private void fetchUserProfile() {
        String userType = sessionManager.getUserType();

        if (userType != null && userType.equals(SessionManager.USER_TYPE_GUEST)) {
            userDetailsLayout.setVisibility(View.GONE);
            guestMessageText.setVisibility(View.VISIBLE);
            guestMessageText.setText("You are logged in as a Guest.\nPlease register to see profile details.");
            welcomeText.setText("Welcome, Guest");
            return;
        }

        String userId = sessionManager.getUserId();

        if (userId == null) {
            Toast.makeText(this, "Could not find User ID. Logging out.", Toast.LENGTH_LONG).show();
            sessionManager.logoutUser();
            finish();
            return;
        }

        userDetailsLayout.setVisibility(View.VISIBLE);
        guestMessageText.setVisibility(View.GONE);

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fullName = snapshot.child("Full Name").getValue(String.class);
                    String email = snapshot.child("Email").getValue(String.class);
                    String username = snapshot.child("Username").getValue(String.class);
                    String phone = snapshot.child("Phone").getValue(String.class);

                    welcomeText.setText("Welcome, " + (fullName != null ? fullName : "User"));
                    fullNameText.setText("Full Name: " + (fullName != null ? fullName : "N/A"));
                    emailText.setText("Email: " + (email != null ? email : "N/A"));
                    usernameText.setText("Username: " + (username != null ? username : "N/A"));
                    phoneText.setText("Phone: " + (phone != null ? phone : "N/A"));
                } else {
                    Toast.makeText(profile.this, "User data not found in database.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(profile.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBottomNavigation() {
        // ✅ YAHAN BADLAV KIYA GAYA HAI: Profile icon ko black color set karein
        // This line sets the color of the profile icon to black, indicating it is the active screen.
        profile_icon.setColorFilter(ContextCompat.getColor(this, android.R.color.black));

        home.setOnClickListener(v -> startActivity(new Intent(profile.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(profile.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(profile.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(profile.this, aboutUs.class)));
        profile_icon.setOnClickListener(v -> Toast.makeText(this, "You are already on Profile", Toast.LENGTH_SHORT).show());
    }
}