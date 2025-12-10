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

public class News extends AppCompatActivity {

    private TextView welcomeText;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsList;
    private DatabaseReference newsRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noNewsLayout;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);

        mAuth = FirebaseAuth.getInstance();
        newsRef = FirebaseDatabase.getInstance().getReference("News");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        welcomeText = findViewById(R.id.welcome);
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noNewsLayout = findViewById(R.id.noNewsLayout);

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(newsAdapter);

        fetchUserName();
        fetchNews();
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
                Toast.makeText(News.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchNews() {
        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newsList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot newsSnapshot : snapshot.getChildren()) {
                        NewsItem newsItem = newsSnapshot.getValue(NewsItem.class);
                        if (newsItem != null) {
                            newsList.add(newsItem);
                        }
                    }
                    newsAdapter.notifyDataSetChanged();
                    noNewsLayout.setVisibility(View.GONE);
                } else {
                    noNewsLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(News.this, "Failed to load news: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noNewsLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        contactus = findViewById(R.id.profileid);

        setSelectedIcon(null);

        home.setOnClickListener(v -> startActivity(new Intent(News.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(News.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(News.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(News.this, aboutUs.class)));
        contactus.setOnClickListener(v -> startActivity(new Intent(News.this, profile.class)));
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        contactus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));

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

public class News extends AppCompatActivity {

    private TextView welcomeText;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsList;
    private DatabaseReference newsRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noNewsLayout;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);

        mAuth = FirebaseAuth.getInstance();
        newsRef = FirebaseDatabase.getInstance().getReference("News");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        welcomeText = findViewById(R.id.welcome);
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noNewsLayout = findViewById(R.id.noNewsLayout);

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList);
        newsRecyclerView.setAdapter(newsAdapter);

        fetchUserName();
        fetchNews();
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
                Toast.makeText(News.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchNews() {
        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newsList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot newsSnapshot : snapshot.getChildren()) {
                        NewsItem newsItem = newsSnapshot.getValue(NewsItem.class);
                        if (newsItem != null) {
                            newsList.add(newsItem);
                        }
                    }
                    newsAdapter.notifyDataSetChanged();
                    noNewsLayout.setVisibility(View.GONE);
                } else {
                    noNewsLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(News.this, "Failed to load news: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noNewsLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        contactus = findViewById(R.id.profileid);

        setSelectedIcon(null);

        home.setOnClickListener(v -> startActivity(new Intent(News.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(News.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(News.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(News.this, aboutUs.class)));
        contactus.setOnClickListener(v -> startActivity(new Intent(News.this, profile.class)));
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        contactus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));

        if (selectedIcon != null) {
            selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
        }
    }
}