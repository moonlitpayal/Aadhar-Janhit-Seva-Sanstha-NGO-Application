package com.example.payalngo;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutUsersActivity extends AppCompatActivity {

    private ListView userListView;
    private List<Map<String, String>> userList;
    private DatabaseReference userRef;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_users);

        // Initialize ListView and Firebase
        userListView = findViewById(R.id.userListView);
        userList = new ArrayList<>();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        // Set up SimpleAdapter with key->TextView mapping
        adapter = new SimpleAdapter(
                this,
                userList,
                R.layout.item_list,
                new String[]{"Email", "Full Name", "Phone", "Username"},
                new int[]{R.id.emailText, R.id.nameText, R.id.phoneText, R.id.usernameText}
        );
        userListView.setAdapter(adapter);

        // Fetch data from Firebase
        fetchUserData();
    }

    private void fetchUserData() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();

                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    String email = userSnap.child("Email").getValue(String.class);
                    String name = userSnap.child("Full Name").getValue(String.class);
                    String phone = userSnap.child("Phone").getValue(String.class);
                    String username = userSnap.child("Username").getValue(String.class);

                    // Add a default value if data is null to prevent errors
                    String finalEmail = email != null ? email : "N/A";
                    String finalName = name != null ? name : "N/A";
                    String finalPhone = phone != null ? phone : "N/A";
                    String finalUsername = username != null ? username : "N/A";

                    Map<String, String> userMap = new HashMap<>();
                    userMap.put("Email", finalEmail);
                    userMap.put("Full Name", finalName);
                    userMap.put("Phone", finalPhone);
                    userMap.put("Username", finalUsername);
                    userList.add(userMap);
                }

                adapter.notifyDataSetChanged(); // Refresh UI
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AboutUsersActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}