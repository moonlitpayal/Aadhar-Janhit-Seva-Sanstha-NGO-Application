package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonationHistory extends AppCompatActivity {

    private LinearLayout historyContainer;
    private TextView totalAmount;
    private DatabaseReference donationref;
    private int totalDonated = 0;

    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donation_history);

        // System bar handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI References
        historyContainer = findViewById(R.id.DonationH);
        totalAmount = findViewById(R.id.TotalDonateH);

        // Firebase reference
        donationref = FirebaseDatabase.getInstance().getReference().child("donations");

        fetchDonationHistory();
        setupBottomNavigation(); // 👇 Navigation setup
    }

    private void fetchDonationHistory() {
        donationref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalDonated = 0;
                historyContainer.removeAllViews();

                if (!snapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "No Donation History Found", Toast.LENGTH_LONG).show();
                    return;
                }

                for (DataSnapshot donationSnap : snapshot.getChildren()) {
                    Object amountObj = donationSnap.child("amount").getValue();
                    String date = donationSnap.child("date").getValue(String.class);

                    int amount = 0;
                    if (amountObj instanceof Long) {
                        amount = ((Long) amountObj).intValue();
                    } else if (amountObj instanceof Integer) {
                        amount = (Integer) amountObj;
                    }

                    totalDonated += amount;

                    if (date != null) {
                        TextView donationView = new TextView(DonationHistory.this);
                        donationView.setText("₹" + amount + " on " + date);
                        donationView.setPadding(16, 16, 16, 16);
                        donationView.setTextSize(16);
                        donationView.setGravity(Gravity.CENTER_HORIZONTAL);
                        historyContainer.addView(donationView);
                    }
                }

                totalAmount.setText("Total Donated: ₹" + totalDonated);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DonationHistory.this, "Failed to load donation history", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        home.setOnClickListener(v -> startActivity(new Intent(DonationHistory.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(DonationHistory.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(DonationHistory.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(DonationHistory.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(DonationHistory.this, profile.class)));
    }
}
