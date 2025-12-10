package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class help extends AppCompatActivity {

    ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);

        // Window insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bottom nav initialization
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        // Bottom nav click listeners
        home.setOnClickListener(v -> {
            startActivity(new Intent(help.this, HomeActivity.class));
        });

        donation.setOnClickListener(v -> {
            startActivity(new Intent(help.this, donation.class));
        });

        story.setOnClickListener(v -> {
            startActivity(new Intent(help.this, storie.class));
        });

        aboutus.setOnClickListener(v -> {
            startActivity(new Intent(help.this, aboutUs.class));
        });

        profile.setOnClickListener(v -> {
            startActivity(new Intent(help.this, profile.class));
        });
    }
}
