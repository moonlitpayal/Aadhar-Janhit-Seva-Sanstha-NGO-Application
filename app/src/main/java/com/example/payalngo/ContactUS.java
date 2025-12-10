/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;

public class ContactUS extends AppCompatActivity {

    private EditText CName, CPhone, CMessage;
    private Button CSubmit;
    private DatabaseReference contactDatabaseReference;
    private DatabaseReference usersDatabaseReference;
    private TextView welcomeText;

    // ✅ Step 1: Declare SessionManager
    private SessionManager sessionManager;

    // Bottom navigation views
    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_us);

        // ✅ Step 2: Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        // Firebase setup
        contactDatabaseReference = FirebaseDatabase.getInstance().getReference("ContactUSData");
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // UI setup
        CName = findViewById(R.id.Cname);
        CPhone = findViewById(R.id.Cphone);
        CMessage = findViewById(R.id.Cmessage);
        CSubmit = findViewById(R.id.Csubmit);
        welcomeText = findViewById(R.id.welcometext);

        CSubmit.setOnClickListener(v -> ContactUsData());

        // ✅ Step 3: Call the new, reliable function
        loadUserDetailsAndPrefillForm();

        setupBottomNavigation();
    }

    /**
     * ✅ FINAL LOGIC: This new function uses SessionManager to fetch user details
     * and pre-fill the form.
     */
    /*private void loadUserDetailsAndPrefillForm() {
        String userType = sessionManager.getUserType();

        // Check if the user is a GUEST
        if (userType != null && userType.equals(SessionManager.USER_TYPE_GUEST)) {
            welcomeText.setText("Welcome, Guest!");
            // You can optionally pre-fill guest if you want
            // CName.setText("Guest");
            return;
        }

        // If it's a REGULAR user, get the UID from the session
        String userId = sessionManager.getUserId();

        if (userId != null) {
            usersDatabaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullName = snapshot.child("Full Name").getValue(String.class);
                        String phone = snapshot.child("Phone").getValue(String.class);

                        // Set Welcome Message
                        if (fullName != null && !fullName.isEmpty()) {
                            welcomeText.setText("Welcome, " + fullName + "!");
                        } else {
                            welcomeText.setText("Welcome!");
                        }

                        // Pre-fill form fields
                        CName.setText(fullName);
                        CPhone.setText(phone);

                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(ContactUS.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void ContactUsData() {
        String name = CName.getText().toString().trim();
        String phone = CPhone.getText().toString().trim();
        String message = CMessage.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = contactDatabaseReference.push().getKey();
        if (id != null) {
            HashMap<String, String> contactMap = new HashMap<>();
            contactMap.put("Name", name);
            contactMap.put("Phone", phone);
            contactMap.put("Message", message);

            contactDatabaseReference.child(id).setValue(contactMap)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                        CName.setText("");
                        CPhone.setText("");
                        CMessage.setText("");
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        }
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        setSelectedIcon(null); // No icon is selected on Contact Us page

        home.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, profile.class)));
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        if (selectedIcon != null) {
            selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
        }
    }
}*/

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

import java.util.HashMap;

public class ContactUS extends AppCompatActivity {

    private EditText CName, CPhone, CMessage;
    private Button CSubmit;
    private DatabaseReference contactDatabaseReference;
    private DatabaseReference usersDatabaseReference;
    private TextView welcomeText;

    private SessionManager sessionManager;

    // Bottom navigation views
    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_us);

        // ✅✅✅ THIS IS THE FIX FOR THE NAVIGATION PROBLEM ✅✅✅
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), systemBars.top, v.getPaddingRight(), systemBars.bottom);
            return insets;
        });
        // ✅✅✅ END OF FIX ✅✅✅

        sessionManager = new SessionManager(getApplicationContext());

        // Firebase setup
        contactDatabaseReference = FirebaseDatabase.getInstance().getReference("ContactUSData");
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // UI setup
        CName = findViewById(R.id.Cname);
        CPhone = findViewById(R.id.Cphone);
        CMessage = findViewById(R.id.Cmessage);
        CSubmit = findViewById(R.id.Csubmit);
        welcomeText = findViewById(R.id.welcometext);

        CSubmit.setOnClickListener(v -> ContactUsData());

        loadUserDetailsAndPrefillForm();
        setupBottomNavigation();
    }

    private void loadUserDetailsAndPrefillForm() {
        String userType = sessionManager.getUserType();

        if (userType != null && userType.equals(SessionManager.USER_TYPE_GUEST)) {
            welcomeText.setText("Welcome, Guest!");
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

                        if (fullName != null && !fullName.isEmpty()) {
                            welcomeText.setText("Welcome, " + fullName + "!");
                        } else {
                            welcomeText.setText("Welcome!");
                        }

                        CName.setText(fullName);
                        CPhone.setText(phone);
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(ContactUS.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void ContactUsData() {
        String name = CName.getText().toString().trim();
        String phone = CPhone.getText().toString().trim();
        String message = CMessage.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = contactDatabaseReference.push().getKey();
        if (id != null) {
            HashMap<String, String> contactMap = new HashMap<>();
            contactMap.put("Name", name);
            contactMap.put("Phone", phone);
            contactMap.put("Message", message);

            contactDatabaseReference.child(id).setValue(contactMap)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                        CName.setText("");
                        CPhone.setText("");
                        CMessage.setText("");
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        }
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        setSelectedIcon(null);

        home.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(ContactUS.this, profile.class)));
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        if (selectedIcon != null) {
            selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
        }
    }
}