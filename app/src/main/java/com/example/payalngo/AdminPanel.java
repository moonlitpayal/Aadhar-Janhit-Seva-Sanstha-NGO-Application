/*package com.example.payalngo;

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
}*/





package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {

    private CardView cardAboutUsers, cardContactUs, cardDonations, cardVolunteer, cardAddContent, cardDeleteContent, cardAddBanner, cardVerifyDonations;
    private Button logoutButton;
    private SessionManager sessionManager;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_panel);

        sessionManager = new SessionManager(getApplicationContext());
        dbRef = FirebaseDatabase.getInstance().getReference("Donations");

        // Initialization (Matching your XML IDs)
        cardAboutUsers = findViewById(R.id.card_about_us);
        cardContactUs = findViewById(R.id.card_contact_us);
        cardDonations = findViewById(R.id.card_donations);
        cardVolunteer = findViewById(R.id.card_volunteers);
        cardAddContent = findViewById(R.id.card_add_content);
        cardDeleteContent = findViewById(R.id.card_delete_content);
        cardAddBanner = findViewById(R.id.card_add_banner);
        cardVerifyDonations = findViewById(R.id.card_verify_donations);
        logoutButton = findViewById(R.id.logoutButton);

        // Click Listeners
        cardAboutUsers.setOnClickListener(v -> startActivity(new Intent(this, AboutUsersActivity.class)));
        cardContactUs.setOnClickListener(v -> startActivity(new Intent(this, AllMessagesActivity.class)));
        cardDonations.setOnClickListener(v -> startActivity(new Intent(this, DonationsActivity.class)));
        cardVolunteer.setOnClickListener(v -> startActivity(new Intent(this, VolunteerRequest.class)));
        cardAddContent.setOnClickListener(v -> startActivity(new Intent(this, AddContentActivity.class)));
        cardDeleteContent.setOnClickListener(v -> startActivity(new Intent(this, DeleteContentActivity.class)));
        cardAddBanner.setOnClickListener(v -> startActivity(new Intent(this, AddBannerActivity.class)));

        // ⭐ NEW: Verification Logic using Popup (No new Activity needed)
        cardVerifyDonations.setOnClickListener(v -> {
            showPendingDonationsDialog();
        });

        logoutButton.setOnClickListener(v -> {
            sessionManager.logoutUser();
            finish();
        });
    }

    private void showPendingDonationsDialog() {
        ArrayList<String> pendingList = new ArrayList<>();
        ArrayList<String> donationKeys = new ArrayList<>();

        dbRef.orderByChild("Status").equalTo("Pending Verification")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            Toast.makeText(AdminPanel.this, "No Pending Donations Found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DataSnapshot data : snapshot.getChildren()) {
                            String name = data.child("Name").getValue(String.class);
                            String amount = String.valueOf(data.child("Amount").getValue());
                            String utr = data.child("TransactionId").getValue(String.class);

                            pendingList.add("User: " + name + "\nAmt: ₹" + amount + "\nUTR: " + utr);
                            donationKeys.add(data.getKey());
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminPanel.this);
                        builder.setTitle("Verify Pending Payments");

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminPanel.this,
                                android.R.layout.simple_list_item_1, pendingList);

                        builder.setAdapter(adapter, (dialog, which) -> {
                            // Clicking an item opens Approve/Reject choice
                            showApproveRejectDialog(donationKeys.get(which), pendingList.get(which));
                        });

                        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
                        builder.create().show();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(AdminPanel.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showApproveRejectDialog(String key, String details) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Action for Donation");
        builder.setMessage(details + "\n\nDo you want to Approve or Reject this payment?");

        builder.setPositiveButton("Approve", (dialog, which) -> {
            dbRef.child(key).child("Status").setValue("Completed")
                    .addOnSuccessListener(aVoid -> Toast.makeText(AdminPanel.this, "Payment Approved!", Toast.LENGTH_SHORT).show());
        });

        builder.setNegativeButton("Reject", (dialog, which) -> {
            dbRef.child(key).child("Status").setValue("Rejected")
                    .addOnSuccessListener(aVoid -> Toast.makeText(AdminPanel.this, "Payment Rejected", Toast.LENGTH_SHORT).show());
        });

        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}