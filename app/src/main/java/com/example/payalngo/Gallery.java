/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {

    private GridView galleryGrid;
    private GalleryAdapter galleryAdapter;
    private List<GalleryItem> galleryList;
    private DatabaseReference galleryRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noGalleryLayout;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mAuth = FirebaseAuth.getInstance();
        galleryRef = FirebaseDatabase.getInstance().getReference("Gallery");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        galleryGrid = findViewById(R.id.galleryGrid);
        noGalleryLayout = findViewById(R.id.noGalleryLayout);

        galleryList = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(this, galleryList);
        galleryGrid.setAdapter(galleryAdapter);

        fetchGallery();
        setupBottomNavigation();
    }

    private void fetchGallery() {
        galleryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                galleryList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        GalleryItem galleryItem = itemSnapshot.getValue(GalleryItem.class);
                        if (galleryItem != null) {
                            galleryList.add(galleryItem);
                        }
                    }
                    galleryAdapter.notifyDataSetChanged();
                    noGalleryLayout.setVisibility(View.GONE);
                } else {
                    noGalleryLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Gallery.this, "Failed to load gallery: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noGalleryLayout.setVisibility(View.VISIBLE);
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

        home.setOnClickListener(v -> startActivity(new Intent(Gallery.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(Gallery.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(Gallery.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(Gallery.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(Gallery.this, profile.class)));
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


/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {

    private GridView galleryGrid;
    private GalleryAdapter galleryAdapter;
    private List<GalleryItem> galleryList;
    private DatabaseReference galleryRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noGalleryLayout;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mAuth = FirebaseAuth.getInstance();
        galleryRef = FirebaseDatabase.getInstance().getReference("Gallery");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        galleryGrid = findViewById(R.id.galleryGrid);
        noGalleryLayout = findViewById(R.id.noGalleryLayout);

        galleryList = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(this, galleryList);
        galleryGrid.setAdapter(galleryAdapter);

        fetchGallery();
        setupBottomNavigation();
    }

    private void fetchGallery() {
        galleryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                galleryList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        GalleryItem galleryItem = itemSnapshot.getValue(GalleryItem.class);
                        if (galleryItem != null) {
                            galleryList.add(galleryItem);
                        }
                    }
                    galleryAdapter.notifyDataSetChanged();
                    noGalleryLayout.setVisibility(View.GONE);
                } else {
                    noGalleryLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Gallery.this, "Failed to load gallery: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noGalleryLayout.setVisibility(View.VISIBLE);
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

        home.setOnClickListener(v -> startActivity(new Intent(Gallery.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(Gallery.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(Gallery.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(Gallery.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(Gallery.this, profile.class)));
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



/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {

    private GridView galleryGrid;
    private GalleryAdapter galleryAdapter;
    private List<GalleryItem> galleryList;
    private DatabaseReference galleryRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noGalleryLayout;

    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mAuth = FirebaseAuth.getInstance();
        galleryRef = FirebaseDatabase.getInstance().getReference("Gallery");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        galleryGrid = findViewById(R.id.galleryGrid);
        noGalleryLayout = findViewById(R.id.noGalleryLayout);

        galleryList = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(this, galleryList);
        galleryGrid.setAdapter(galleryAdapter);

        fetchGallery();
        setupBottomNavigation();
    }

    private void fetchGallery() {
        galleryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                galleryList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        GalleryItem galleryItem = itemSnapshot.getValue(GalleryItem.class);
                        if (galleryItem != null) {
                            galleryList.add(galleryItem);
                        }
                    }
                    galleryAdapter.notifyDataSetChanged();
                    noGalleryLayout.setVisibility(View.GONE);
                } else {
                    noGalleryLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Gallery.this, "Failed to load gallery: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noGalleryLayout.setVisibility(View.VISIBLE);
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

        home.setOnClickListener(v -> startActivity(new Intent(Gallery.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(Gallery.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(Gallery.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(Gallery.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(Gallery.this, profile.class)));
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {

    private GridView galleryGrid;
    private GalleryAdapter galleryAdapter;
    private List<GalleryItem> galleryList;
    private DatabaseReference galleryRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private LinearLayout noGalleryLayout;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mAuth = FirebaseAuth.getInstance();
        galleryRef = FirebaseDatabase.getInstance().getReference("Gallery");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        galleryGrid = findViewById(R.id.galleryGrid);
        noGalleryLayout = findViewById(R.id.noGalleryLayout);

        galleryList = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(this, galleryList);
        galleryGrid.setAdapter(galleryAdapter);

        fetchGallery();
        setupBottomNavigation();
    }

    private void fetchGallery() {
        galleryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                galleryList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        GalleryItem galleryItem = itemSnapshot.getValue(GalleryItem.class);
                        if (galleryItem != null) {
                            galleryList.add(galleryItem);
                        }
                    }
                    galleryAdapter.notifyDataSetChanged();
                    noGalleryLayout.setVisibility(View.GONE); // ✅ Data hone par message hide
                } else {
                    noGalleryLayout.setVisibility(View.VISIBLE); // ✅ Data na hone par message show
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Gallery.this, "Failed to load gallery: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                noGalleryLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupBottomNavigation() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

        setSelectedIcon(null); // Gallery ka icon bottom navigation mein nahi hai

        home.setOnClickListener(v -> startActivity(new Intent(Gallery.this, HomeActivity.class)));
        donation.setOnClickListener(v -> startActivity(new Intent(Gallery.this, donation.class)));
        story.setOnClickListener(v -> startActivity(new Intent(Gallery.this, storie.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(Gallery.this, aboutUs.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(Gallery.this, profile.class)));
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);

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