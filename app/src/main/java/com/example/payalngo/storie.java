/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class storie extends AppCompatActivity {

    private TextView welcomeText;
    private RecyclerView storieRecyclerView;
    private StoriesAdapter storiesAdapter;
    private List<StorieItem> storieList;
    private DatabaseReference storiesRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;
    private LinearLayout noStoriesLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_storie);

        mAuth = FirebaseAuth.getInstance();
        storiesRef = FirebaseDatabase.getInstance().getReference("Stories");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        welcomeText = findViewById(R.id.welcome);
        storieRecyclerView = findViewById(R.id.storieRecyclerView);
        storieRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noStoriesLayout = findViewById(R.id.noStoriesLayout);

        storieList = new ArrayList<>();
        storiesAdapter = new StoriesAdapter(storieList);
        storieRecyclerView.setAdapter(storiesAdapter);

        fetchUserName();
        fetchStories();
        setupBottomNavigation();
    }

    private void fetchUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            welcomeText.setText("Welcome Guest!");
            return;
        }

        String userId = currentUser.getUid();
        userRef.child(userId).child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    welcomeText.setText("Welcome, " + name + "!");
                } else {
                    welcomeText.setText("Welcome!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                welcomeText.setText("Welcome!");
                Toast.makeText(storie.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchStories() {
        storiesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storieList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot storieSnapshot : snapshot.getChildren()) {
                        StorieItem storieItem = storieSnapshot.getValue(StorieItem.class);
                        if (storieItem != null) {
                            storieList.add(storieItem);
                        }
                    }
                    storiesAdapter.notifyDataSetChanged();
                    noStoriesLayout.setVisibility(View.GONE);
                } else {
                    noStoriesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(storie.this, "Failed to load stories: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noStoriesLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        setSelectedIcon(story);

        home.setOnClickListener(v -> startActivity(new Intent(storie.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(storie.this, donation.class)));
        story.setOnClickListener(v -> Toast.makeText(this, "Already in Success Stories", Toast.LENGTH_SHORT).show());
        aboutus.setOnClickListener(v -> startActivity(new Intent(storie.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(storie.this, profile.class)));
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }
}*/



package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class storie extends AppCompatActivity {

    private TextView welcomeText;
    private RecyclerView storieRecyclerView;
    private StoriesAdapter storiesAdapter;
    private List<StorieItem> storieList;
    private DatabaseReference storiesRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noStoriesLayout;

    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_storie);

        mAuth = FirebaseAuth.getInstance();
        storiesRef = FirebaseDatabase.getInstance().getReference("Stories");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        welcomeText = findViewById(R.id.welcome);
        storieRecyclerView = findViewById(R.id.storieRecyclerView);
        storieRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noStoriesLayout = findViewById(R.id.noStoriesLayout);

        storieList = new ArrayList<>();
        storiesAdapter = new StoriesAdapter(storieList);
        storieRecyclerView.setAdapter(storiesAdapter);

        fetchUserName();
        fetchStories();
        setupBottomNavigation();
    }

    private void fetchUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            welcomeText.setText("Welcome Guest!");
            return;
        }

        String userId = currentUser.getUid();
        userRef.child(userId).child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    welcomeText.setText("Welcome, " + name + "!");
                } else {
                    welcomeText.setText("Welcome!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                welcomeText.setText("Welcome!");
                Toast.makeText(storie.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchStories() {
        storiesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storieList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot storieSnapshot : snapshot.getChildren()) {
                        StorieItem storieItem = storieSnapshot.getValue(StorieItem.class);
                        if (storieItem != null) {
                            storieList.add(storieItem);
                        }
                    }
                    storiesAdapter.notifyDataSetChanged();
                    noStoriesLayout.setVisibility(View.GONE);
                } else {
                    noStoriesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(storie.this, "Failed to load stories: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noStoriesLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        setSelectedIcon(story);

        home.setOnClickListener(v -> startActivity(new Intent(storie.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(storie.this, donation.class)));
        story.setOnClickListener(v -> Toast.makeText(this, "Already in Success Stories", Toast.LENGTH_SHORT).show());
        aboutus.setOnClickListener(v -> startActivity(new Intent(storie.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(storie.this, profile.class)));
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }
}