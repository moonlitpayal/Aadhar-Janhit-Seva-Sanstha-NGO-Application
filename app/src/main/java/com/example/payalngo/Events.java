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

public class Events extends AppCompatActivity {

    private TextView welcomeText;
    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private List<EventItem> eventList;
    private DatabaseReference eventsRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noEventsLayout;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);

        mAuth = FirebaseAuth.getInstance();
        eventsRef = FirebaseDatabase.getInstance().getReference("Events");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        welcomeText = findViewById(R.id.welcome);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noEventsLayout = findViewById(R.id.noEventsLayout);

        eventList = new ArrayList<>();
        eventsAdapter = new EventsAdapter(eventList);
        eventsRecyclerView.setAdapter(eventsAdapter);

        fetchUserName();
        fetchEvents();
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
                Toast.makeText(Events.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchEvents() {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                        EventItem eventItem = eventSnapshot.getValue(EventItem.class);
                        if (eventItem != null) {
                            eventList.add(eventItem);
                        }
                    }
                    eventsAdapter.notifyDataSetChanged();
                    noEventsLayout.setVisibility(View.GONE);
                } else {
                    noEventsLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Events.this, "Failed to load events: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noEventsLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        setSelectedIcon(null);

        home.setOnClickListener(v -> startActivity(new Intent(Events.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(Events.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(Events.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(Events.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(Events.this, profile.class)));
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

public class Events extends AppCompatActivity {

    private TextView welcomeText;
    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private List<EventItem> eventList;
    private DatabaseReference eventsRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noEventsLayout;

    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);

        mAuth = FirebaseAuth.getInstance();
        eventsRef = FirebaseDatabase.getInstance().getReference("Events");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        welcomeText = findViewById(R.id.welcome);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noEventsLayout = findViewById(R.id.noEventsLayout);

        eventList = new ArrayList<>();
        eventsAdapter = new EventsAdapter(eventList);
        eventsRecyclerView.setAdapter(eventsAdapter);

        fetchUserName();
        fetchEvents();
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
                Toast.makeText(Events.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchEvents() {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                        EventItem eventItem = eventSnapshot.getValue(EventItem.class);
                        if (eventItem != null) {
                            eventList.add(eventItem);
                        }
                    }
                    eventsAdapter.notifyDataSetChanged();
                    noEventsLayout.setVisibility(View.GONE);
                } else {
                    noEventsLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Events.this, "Failed to load events: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noEventsLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        setSelectedIcon(null);

        home.setOnClickListener(v -> startActivity(new Intent(Events.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(Events.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(Events.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(Events.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(Events.this, profile.class)));
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