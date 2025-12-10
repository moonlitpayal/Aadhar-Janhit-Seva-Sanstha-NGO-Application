package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
// Remove the direct import for FirebaseAuth as SessionManager will handle it.
// import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity {

    private CardView cardAboutUsers, cardContactUs, cardDonations, cardVolunteer, cardAddContent, cardDeleteContent, cardAddBanner;
    private Button logoutButton;

    // ✅ Step 1: Declare the SessionManager
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_panel);

        // ✅ Step 2: Initialize the SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        // --- Your existing code for finding views ---
        cardAboutUsers = findViewById(R.id.card_about_us);
        cardContactUs = findViewById(R.id.card_contact_us);
        cardDonations = findViewById(R.id.card_donations);
        cardVolunteer = findViewById(R.id.card_volunteers);
        cardAddContent = findViewById(R.id.card_add_content);
        cardDeleteContent = findViewById(R.id.card_delete_content);
        cardAddBanner = findViewById(R.id.card_add_banner);
        logoutButton = findViewById(R.id.logoutButton);

        cardAboutUsers.setOnClickListener(v -> startActivity(new Intent(this, AboutUsersActivity.class)));
        cardContactUs.setOnClickListener(v -> startActivity(new Intent(this, AllMessagesActivity.class)));
        cardDonations.setOnClickListener(v -> startActivity(new Intent(this, DonationsActivity.class)));
        cardVolunteer.setOnClickListener(v -> startActivity(new Intent(this, VolunteerRequest.class)));
        cardAddContent.setOnClickListener(v -> startActivity(new Intent(this, AddContentActivity.class)));
        cardDeleteContent.setOnClickListener(v -> startActivity(new Intent(this, DeleteContentActivity.class)));
        cardAddBanner.setOnClickListener(v -> startActivity(new Intent(this, AddBannerActivity.class)));

        logoutButton.setOnClickListener(v -> {
            sessionManager.logoutUser();
            finish(); // Finish AdminPanel activity after starting the logout process
        });
    }
}