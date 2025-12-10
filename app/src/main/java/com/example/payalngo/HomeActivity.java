/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView welcome;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ViewFlipper viewFlipper;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home); // Make sure the layout file is named home.xml

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        welcome = findViewById(R.id.welcometext);
        viewFlipper = findViewById(R.id.banner1);
        viewFlipper.startFlipping();

        // Bottom Navigation initialization
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        contactus = findViewById(R.id.profileid);

        // GridLayout Tab initialization
        LinearLayout contactTab = findViewById(R.id.Hcontact);
        LinearLayout donationTab = findViewById(R.id.Hdonate);
        LinearLayout profileTab = findViewById(R.id.Hprofile);
        LinearLayout aboutUsTab = findViewById(R.id.Habout);
        LinearLayout storiesTab = findViewById(R.id.Hstories);
        LinearLayout volunteerTab = findViewById(R.id.Hvolunteer);
        LinearLayout eventTab = findViewById(R.id.Hevents);
        LinearLayout newsTab = findViewById(R.id.Hnews);
        LinearLayout helpTab = findViewById(R.id.Hhelp);
        LinearLayout galleryTab = findViewById(R.id.Hgallery);

        // Grid Click Events
        contactTab.setOnClickListener(v -> {
            Toast.makeText(this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ContactUS.class));
        });

        donationTab.setOnClickListener(v -> {
            Toast.makeText(this, "Donate Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, donation.class));
        });

        profileTab.setOnClickListener(v -> {
            Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, profile.class));
        });

        aboutUsTab.setOnClickListener(v -> {
            Toast.makeText(this, "About Us Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, aboutUs.class));
        });

        storiesTab.setOnClickListener(v -> {
            Toast.makeText(this, "Success Stories Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, storie.class));
        });

        volunteerTab.setOnClickListener(v -> {
            Toast.makeText(this, "Volunteer Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Volunteer.class));
        });

        eventTab.setOnClickListener(v -> {
            Toast.makeText(this, "Events Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Events.class));
        });

        newsTab.setOnClickListener(v -> {
            Toast.makeText(this, "News Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, News.class));
        });

        helpTab.setOnClickListener(v -> {
            Toast.makeText(this, "Help & Support Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, help.class));
        });

        galleryTab.setOnClickListener(v -> {
            Toast.makeText(this, "Gallery Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Gallery.class));
        });

        // Bottom Navigation Events
        home.setOnClickListener(v -> {
            Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show();
        });

        donation.setOnClickListener(v -> {
            startActivity(new Intent(this, donation.class));
        });

        story.setOnClickListener(v -> {
            startActivity(new Intent(this, storie.class));
        });

        aboutus.setOnClickListener(v -> {
            startActivity(new Intent(this, aboutUs.class));
        });

        contactus.setOnClickListener(v -> {
            startActivity(new Intent(this, profile.class));
        });

        // Load welcome name from Firebase
        fetchUserName();
    }

    private void fetchUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            welcome.setText("Welcome Guest!");
            Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        databaseReference.child(userId).child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    welcome.setText("Welcome, " + name + "!");
                } else {
                    welcome.setText("Welcome Guest!");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                welcome.setText("Welcome Guest!");
                Toast.makeText(getApplicationContext(), "Failed to load name: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}*/

/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView welcome;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ViewFlipper viewFlipper;
    private DatabaseReference bannersRef;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    // GridLayout Tabs
    private LinearLayout contactTab, donationTab, profileTab, aboutUsTab, storiesTab, volunteerTab, eventTab, newsTab, helpTab, galleryTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Dynamic Banners setup
        bannersRef = FirebaseDatabase.getInstance().getReference("Banners");
        fetchBanners();

        // Initialize UI elements
        welcome = findViewById(R.id.welcometext);
        viewFlipper = findViewById(R.id.banner1);

        // Initialize all navigation tabs
        initializeNavigationTabs();

        // Set up all click listeners
        setupNavigationEvents();

        // Load welcome name from Firebase
        fetchUserName();
    }

    private void fetchBanners() {
        bannersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewFlipper.removeAllViews();
                for (DataSnapshot bannerSnapshot : snapshot.getChildren()) {
                    String imageUrl = bannerSnapshot.child("imageURL").getValue(String.class);
                    if (imageUrl != null) {
                        ImageView bannerImage = new ImageView(HomeActivity.this);
                        bannerImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        Glide.with(HomeActivity.this)
                                .load(imageUrl)
                                .apply(new RequestOptions().centerCrop())
                                .into(bannerImage);

                        viewFlipper.addView(bannerImage);
                    }
                }
                viewFlipper.startFlipping();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to load banners.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeNavigationTabs() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);
        contactTab = findViewById(R.id.Hcontact);
        donationTab = findViewById(R.id.Hdonate);
        profileTab = findViewById(R.id.Hprofile);
        aboutUsTab = findViewById(R.id.Habout);
        storiesTab = findViewById(R.id.Hstories);
        volunteerTab = findViewById(R.id.Hvolunteer);
        eventTab = findViewById(R.id.Hevents);
        newsTab = findViewById(R.id.Hnews);
        helpTab = findViewById(R.id.Hhelp);
        galleryTab = findViewById(R.id.Hgallery);
    }

    private void setupNavigationEvents() {
        contactTab.setOnClickListener(v -> startActivity(new Intent(this, ContactUS.class)));
        donationTab.setOnClickListener(v -> startActivity(new Intent(this, donation.class)));
        profileTab.setOnClickListener(v -> startActivity(new Intent(this, profile.class)));
        aboutUsTab.setOnClickListener(v -> startActivity(new Intent(this, aboutUs.class)));
        storiesTab.setOnClickListener(v -> startActivity(new Intent(this, storie.class)));
        volunteerTab.setOnClickListener(v -> startActivity(new Intent(this, Volunteer.class)));
        eventTab.setOnClickListener(v -> startActivity(new Intent(this, Events.class)));
        newsTab.setOnClickListener(v -> startActivity(new Intent(this, News.class)));
        helpTab.setOnClickListener(v -> startActivity(new Intent(this, help.class)));
        galleryTab.setOnClickListener(v -> startActivity(new Intent(this, Gallery.class)));

        home.setOnClickListener(v -> {
            setSelectedIcon(home);
            Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show();
        });
        donation.setOnClickListener(v -> {
            setSelectedIcon(donation);
            startActivity(new Intent(this, donation.class));
        });
        story.setOnClickListener(v -> {
            setSelectedIcon(story);
            startActivity(new Intent(this, storie.class));
        });
        aboutus.setOnClickListener(v -> {
            setSelectedIcon(aboutus);
            startActivity(new Intent(this, aboutUs.class));
        });
        profile.setOnClickListener(v -> {
            setSelectedIcon(profile);
            startActivity(new Intent(this, profile.class));
        });

        setSelectedIcon(home);
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }


    private void fetchUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            welcome.setText("Welcome Guest!");
            return;
        }

        String userId = currentUser.getUid();
        databaseReference.child(userId).child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    welcome.setText("Welcome, " + name + "!");
                } else {
                    welcome.setText("Welcome Guest!");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                welcome.setText("Welcome Guest");
            }
        });
    }
}*/

/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView welcome;
    private DatabaseReference databaseReference;
    private ViewFlipper viewFlipper;
    private DatabaseReference bannersRef;

    // ✅ Step 1: SessionManager ko declare karein
    private SessionManager sessionManager;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    // GridLayout Tabs
    private LinearLayout contactTab, donationTab, profileTab, aboutUsTab, storiesTab, volunteerTab, eventTab, newsTab, helpTab, galleryTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // ✅ Step 2: SessionManager ko initialize karein
        sessionManager = new SessionManager(getApplicationContext());

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Dynamic Banners setup
        bannersRef = FirebaseDatabase.getInstance().getReference("Banners");
        fetchBanners();

        // Initialize UI elements
        welcome = findViewById(R.id.welcometext);
        viewFlipper = findViewById(R.id.banner1);

        // Initialize all navigation tabs
        initializeNavigationTabs();

        // Set up all click listeners
        setupNavigationEvents();

        // ✅ Step 3: Purane function ki jagah naya function call karein
        loadUserDetails();
    }

    private void fetchBanners() {
        bannersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewFlipper.removeAllViews();
                for (DataSnapshot bannerSnapshot : snapshot.getChildren()) {
                    String imageUrl = bannerSnapshot.child("imageURL").getValue(String.class);
                    if (imageUrl != null) {
                        ImageView bannerImage = new ImageView(HomeActivity.this);
                        bannerImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        Glide.with(HomeActivity.this)
                                .load(imageUrl)
                                .apply(new RequestOptions().centerCrop())
                                .into(bannerImage);

                        viewFlipper.addView(bannerImage);
                    }
                }
                viewFlipper.startFlipping();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to load banners.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeNavigationTabs() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);
        contactTab = findViewById(R.id.Hcontact);
        donationTab = findViewById(R.id.Hdonate);
        profileTab = findViewById(R.id.Hprofile);
        aboutUsTab = findViewById(R.id.Habout);
        storiesTab = findViewById(R.id.Hstories);
        volunteerTab = findViewById(R.id.Hvolunteer);
        eventTab = findViewById(R.id.Hevents);
        newsTab = findViewById(R.id.Hnews);
        helpTab = findViewById(R.id.Hhelp);
        galleryTab = findViewById(R.id.Hgallery);
    }

    private void setupNavigationEvents() {
        contactTab.setOnClickListener(v -> startActivity(new Intent(this, ContactUS.class)));
        donationTab.setOnClickListener(v -> startActivity(new Intent(this, donation.class)));
        profileTab.setOnClickListener(v -> startActivity(new Intent(this, profile.class)));
        aboutUsTab.setOnClickListener(v -> startActivity(new Intent(this, aboutUs.class)));
        storiesTab.setOnClickListener(v -> startActivity(new Intent(this, storie.class)));
        volunteerTab.setOnClickListener(v -> startActivity(new Intent(this, Volunteer.class)));
        eventTab.setOnClickListener(v -> startActivity(new Intent(this, Events.class)));
        newsTab.setOnClickListener(v -> startActivity(new Intent(this, News.class)));
        helpTab.setOnClickListener(v -> startActivity(new Intent(this, help.class)));
        galleryTab.setOnClickListener(v -> startActivity(new Intent(this, Gallery.class)));

        home.setOnClickListener(v -> {
            setSelectedIcon(home);
            Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show();
        });
        donation.setOnClickListener(v -> {
            setSelectedIcon(donation);
            startActivity(new Intent(this, donation.class));
        });
        story.setOnClickListener(v -> {
            setSelectedIcon(story);
            startActivity(new Intent(this, storie.class));
        });
        aboutus.setOnClickListener(v -> {
            setSelectedIcon(aboutus);
            startActivity(new Intent(this, aboutUs.class));
        });
        profile.setOnClickListener(v -> {
            setSelectedIcon(profile);
            startActivity(new Intent(this, profile.class));
        });

        setSelectedIcon(home);
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }



    private void loadUserDetails() {
        // User ka type get karo
        String userType = sessionManager.getUserType();

        // Check karo ki user GUEST toh nahi hai
        if (userType != null && userType.equals(SessionManager.USER_TYPE_GUEST)) {
            welcome.setText("Welcome, Guest!");
            return; // Guest ke case mein aage kuch nahi karna
        }

        // Agar REGULAR user hai, toh session se UID lo
        String userId = sessionManager.getUserId();

        // Agar UID maujood hai toh Firebase se data fetch karo
        if (userId != null) {
            // Hum "Users" node ke andar uss user ki ID se data dhoond rahe hain
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // "Full Name" get karo
                        String fullName = snapshot.child("Full Name").getValue(String.class);

                        if (fullName != null && !fullName.isEmpty()) {
                            welcome.setText("Welcome, " + fullName + "!");
                        } else {
                            // Agar naam nahi mila toh fallback message
                            welcome.setText("Welcome, User!");
                        }
                    } else {
                        // Agar Database mein user ki entry nahi hai
                        welcome.setText("Welcome!");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Error hone par
                    Toast.makeText(HomeActivity.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                    welcome.setText("Welcome!");
                }
            });
        } else {
            // Agar session mein UID nahi hai (Guest ya koi error)
            welcome.setText("Welcome, Guest!");
        }
    }
}*/

/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView welcome;
    private DatabaseReference databaseReference;
    private ViewFlipper viewFlipper;
    private DatabaseReference bannersRef;

    // ✅ Step 1: SessionManager ko declare karein
    private SessionManager sessionManager;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    // GridLayout Tabs
    private LinearLayout contactTab, donationTab, profileTab, aboutUsTab, storiesTab, volunteerTab, eventTab, newsTab, helpTab, galleryTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // ✅ Step 2: SessionManager ko initialize karein
        sessionManager = new SessionManager(getApplicationContext());

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Dynamic Banners setup
        bannersRef = FirebaseDatabase.getInstance().getReference("Banners");
        fetchBanners();

        // Initialize UI elements
        welcome = findViewById(R.id.welcometext);
        viewFlipper = findViewById(R.id.banner1);

        // Initialize all navigation tabs
        initializeNavigationTabs();

        // Set up all click listeners
        setupNavigationEvents();

        // ✅ Step 3: Purane function ki jagah naya function call karein
        loadUserDetails();
    }

    private void fetchBanners() {
        bannersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewFlipper.removeAllViews();
                for (DataSnapshot bannerSnapshot : snapshot.getChildren()) {
                    String imageUrl = bannerSnapshot.child("imageURL").getValue(String.class);
                    if (imageUrl != null) {
                        ImageView bannerImage = new ImageView(HomeActivity.this);
                        bannerImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        Glide.with(HomeActivity.this)
                                .load(imageUrl)
                                .apply(new RequestOptions().centerCrop())
                                .into(bannerImage);

                        viewFlipper.addView(bannerImage);
                    }
                }
                viewFlipper.startFlipping();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to load banners.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeNavigationTabs() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);
        contactTab = findViewById(R.id.Hcontact);
        donationTab = findViewById(R.id.Hdonate);
        profileTab = findViewById(R.id.Hprofile);
        aboutUsTab = findViewById(R.id.Habout);
        storiesTab = findViewById(R.id.Hstories);
        volunteerTab = findViewById(R.id.Hvolunteer);
        eventTab = findViewById(R.id.Hevents);
        newsTab = findViewById(R.id.Hnews);
        helpTab = findViewById(R.id.Hhelp);
        galleryTab = findViewById(R.id.Hgallery);
    }

    private void setupNavigationEvents() {
        contactTab.setOnClickListener(v -> startActivity(new Intent(this, ContactUS.class)));
        donationTab.setOnClickListener(v -> startActivity(new Intent(this, donation.class)));
        profileTab.setOnClickListener(v -> startActivity(new Intent(this, profile.class)));
        aboutUsTab.setOnClickListener(v -> startActivity(new Intent(this, aboutUs.class)));
        storiesTab.setOnClickListener(v -> startActivity(new Intent(this, storie.class)));
        volunteerTab.setOnClickListener(v -> startActivity(new Intent(this, Volunteer.class)));
        eventTab.setOnClickListener(v -> startActivity(new Intent(this, Events.class)));
        newsTab.setOnClickListener(v -> startActivity(new Intent(this, News.class)));
        helpTab.setOnClickListener(v -> startActivity(new Intent(this, help.class)));
        galleryTab.setOnClickListener(v -> startActivity(new Intent(this, Gallery.class)));

        home.setOnClickListener(v -> {
            setSelectedIcon(home);
            Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show();
        });
        donation.setOnClickListener(v -> {
            setSelectedIcon(donation);
            startActivity(new Intent(this, donation.class));
        });
        story.setOnClickListener(v -> {
            setSelectedIcon(story);
            startActivity(new Intent(this, storie.class));
        });
        aboutus.setOnClickListener(v -> {
            setSelectedIcon(aboutus);
            startActivity(new Intent(this, aboutUs.class));
        });
        profile.setOnClickListener(v -> {
            setSelectedIcon(profile);
            startActivity(new Intent(this, profile.class));
        });

        setSelectedIcon(home);
    }

    private void setSelectedIcon(ImageView selectedIcon) {
        home.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        donation.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        story.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        aboutus.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        profile.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_default));
        selectedIcon.setColorFilter(ContextCompat.getColor(this, R.color.nav_item_color_selected));
    }



    private void loadUserDetails() {
        // User ka type get karo
        String userType = sessionManager.getUserType();

        // Check karo ki user GUEST toh nahi hai
        if (userType != null && userType.equals(SessionManager.USER_TYPE_GUEST)) {
            welcome.setText("Welcome, Guest!");
            return; // Guest ke case mein aage kuch nahi karna
        }

        // Agar REGULAR user hai, toh session se UID lo
        String userId = sessionManager.getUserId();

        // Agar UID maujood hai toh Firebase se data fetch karo
        if (userId != null) {
            // Hum "Users" node ke andar uss user ki ID se data dhoond rahe hain
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // "Full Name" get karo
                        String fullName = snapshot.child("Full Name").getValue(String.class);

                        if (fullName != null && !fullName.isEmpty()) {
                            welcome.setText("Welcome, " + fullName + "!");
                        } else {
                            // Agar naam nahi mila toh fallback message
                            welcome.setText("Welcome, User!");
                        }
                    } else {
                        // Agar Database mein user ki entry nahi hai
                        welcome.setText("Welcome!");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Error hone par
                    Toast.makeText(HomeActivity.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                    welcome.setText("Welcome!");
                }
            });
        } else {
            // Agar session mein UID nahi hai (Guest ya koi error)
            welcome.setText("Welcome, Guest!");
        }
    }
}*/




/*package com.example.payalngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView welcome;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ViewFlipper viewFlipper;
    private DatabaseReference bannersRef;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    // GridLayout Tabs
    private LinearLayout contactTab, donationTab, profileTab, aboutUsTab, storiesTab, volunteerTab, eventTab, newsTab, helpTab, galleryTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        welcome = findViewById(R.id.welcometext);
        viewFlipper = findViewById(R.id.banner1);

        // Dynamic Banners setup
        bannersRef = FirebaseDatabase.getInstance().getReference("Banners");
        fetchBanners();

        // Initialize all navigation tabs
        initializeNavigationTabs();

        // Set up all click listeners
        setupNavigationEvents();

        // Load welcome name from Firebase
        fetchUserName();
    }

    private void fetchBanners() {
        bannersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewFlipper.removeAllViews();
                if (snapshot.exists()) {
                    for (DataSnapshot bannerSnapshot : snapshot.getChildren()) {
                        String imageUrl = bannerSnapshot.child("imageURL").getValue(String.class);
                        if (imageUrl != null) {
                            ImageView bannerImage = new ImageView(HomeActivity.this);
                            bannerImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            Glide.with(HomeActivity.this)
                                    .load(imageUrl)
                                    .apply(new RequestOptions().centerCrop())
                                    .into(bannerImage);

                            viewFlipper.addView(bannerImage);
                        }
                    }
                    viewFlipper.startFlipping();
                } else {
                    // Placeholder if no banners are available
                    ImageView defaultBanner = new ImageView(HomeActivity.this);
                    defaultBanner.setImageResource(R.drawable.placeholder_banner);
                    defaultBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    viewFlipper.addView(defaultBanner);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to load banners.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeNavigationTabs() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);
        contactTab = findViewById(R.id.Hcontact);
        donationTab = findViewById(R.id.Hdonate);
        profileTab = findViewById(R.id.Hprofile);
        aboutUsTab = findViewById(R.id.Habout);
        storiesTab = findViewById(R.id.Hstories);
        volunteerTab = findViewById(R.id.Hvolunteer);
        eventTab = findViewById(R.id.Hevents);
        newsTab = findViewById(R.id.Hnews);
        helpTab = findViewById(R.id.Hhelp);
        galleryTab = findViewById(R.id.Hgallery);
    }

    private void setupNavigationEvents() {
        contactTab.setOnClickListener(v -> startActivity(new Intent(this, ContactUS.class)));
        donationTab.setOnClickListener(v -> startActivity(new Intent(this, donation.class)));
        profileTab.setOnClickListener(v -> startActivity(new Intent(this, profile.class)));
        aboutUsTab.setOnClickListener(v -> startActivity(new Intent(this, aboutUs.class)));
        storiesTab.setOnClickListener(v -> startActivity(new Intent(this, storie.class)));
        volunteerTab.setOnClickListener(v -> startActivity(new Intent(this, Volunteer.class)));
        eventTab.setOnClickListener(v -> startActivity(new Intent(this, Events.class)));
        newsTab.setOnClickListener(v -> startActivity(new Intent(this, News.class)));
        helpTab.setOnClickListener(v -> startActivity(new Intent(this, help.class)));
        galleryTab.setOnClickListener(v -> startActivity(new Intent(this, Gallery.class)));

        home.setOnClickListener(v -> {
            setSelectedIcon(home);
            Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show();
        });
        donation.setOnClickListener(v -> {
            setSelectedIcon(donation);
            startActivity(new Intent(this, donation.class));
        });
        story.setOnClickListener(v -> {
            setSelectedIcon(story);
            startActivity(new Intent(this, storie.class));
        });
        aboutus.setOnClickListener(v -> {
            setSelectedIcon(aboutus);
            startActivity(new Intent(this, aboutUs.class));
        });
        profile.setOnClickListener(v -> {
            setSelectedIcon(profile);
            startActivity(new Intent(this, profile.class));
        });

        setSelectedIcon(home);
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

    private void fetchUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            welcome.setText("Welcome Guest!");
            return;
        }

        String userId = currentUser.getUid();
        databaseReference.child(userId).child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    welcome.setText("Welcome, " + name + "!");
                } else {
                    welcome.setText("Welcome Guest!");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                welcome.setText("Welcome Guest");
            }
        });
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
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError; // ✅ Yeh import zaroori hai
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView welcome;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ViewFlipper viewFlipper;
    private DatabaseReference bannersRef;

    // Bottom Navigation Items
    private ImageView home, donation, story, aboutus, profile;

    // GridLayout Tabs
    private LinearLayout contactTab, donationTab, profileTab, aboutUsTab, storiesTab, volunteerTab, eventTab, newsTab, helpTab, galleryTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        welcome = findViewById(R.id.welcometext);
        viewFlipper = findViewById(R.id.banner1);

        // Dynamic Banners setup
        bannersRef = FirebaseDatabase.getInstance().getReference("Banners");
        fetchBanners();

        // Initialize all navigation tabs
        initializeNavigationTabs();

        // Set up all click listeners
        setupNavigationEvents();

        // Load welcome name from Firebase
        fetchUserName();
    }

    private void fetchBanners() {
        bannersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewFlipper.removeAllViews();
                if (snapshot.exists()) {
                    for (DataSnapshot bannerSnapshot : snapshot.getChildren()) {
                        String imageUrl = bannerSnapshot.child("imageURL").getValue(String.class);
                        if (imageUrl != null) {
                            ImageView bannerImage = new ImageView(HomeActivity.this);
                            bannerImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            Glide.with(HomeActivity.this)
                                    .load(imageUrl)
                                    .apply(new RequestOptions().centerCrop())
                                    .into(bannerImage);

                            viewFlipper.addView(bannerImage);
                        }
                    }
                    viewFlipper.startFlipping();
                } else {
                    // Placeholder if no banners are available
                    ImageView defaultBanner = new ImageView(HomeActivity.this);
                    defaultBanner.setImageResource(R.drawable.placeholder_banner);
                    defaultBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    viewFlipper.addView(defaultBanner);
                    viewFlipper.startFlipping();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to load banners.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeNavigationTabs() {
        home = findViewById(R.id.homeid);
        donation = findViewById(R.id.donationid);
        story = findViewById(R.id.storieid);
        aboutus = findViewById(R.id.abouttusid);
        profile = findViewById(R.id.profileid);
        contactTab = findViewById(R.id.Hcontact);
        donationTab = findViewById(R.id.Hdonate);
        profileTab = findViewById(R.id.Hprofile);
        aboutUsTab = findViewById(R.id.Habout);
        storiesTab = findViewById(R.id.Hstories);
        volunteerTab = findViewById(R.id.Hvolunteer);
        eventTab = findViewById(R.id.Hevents);
        newsTab = findViewById(R.id.Hnews);
        helpTab = findViewById(R.id.Hhelp);
        galleryTab = findViewById(R.id.Hgallery);
    }

    private void setupNavigationEvents() {
        contactTab.setOnClickListener(v -> startActivity(new Intent(this, ContactUS.class)));
        donationTab.setOnClickListener(v -> startActivity(new Intent(this, donation.class)));
        profileTab.setOnClickListener(v -> startActivity(new Intent(this, profile.class)));
        aboutUsTab.setOnClickListener(v -> startActivity(new Intent(this, aboutUs.class)));
        storiesTab.setOnClickListener(v -> startActivity(new Intent(this, storie.class)));
        volunteerTab.setOnClickListener(v -> startActivity(new Intent(this, Volunteer.class)));
        eventTab.setOnClickListener(v -> startActivity(new Intent(this, Events.class)));
        newsTab.setOnClickListener(v -> startActivity(new Intent(this, News.class)));
        helpTab.setOnClickListener(v -> startActivity(new Intent(this, help.class)));
        galleryTab.setOnClickListener(v -> startActivity(new Intent(this, Gallery.class)));

        home.setOnClickListener(v -> {
            setSelectedIcon(home);
            Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show();
        });
        donation.setOnClickListener(v -> {
            setSelectedIcon(donation);
            startActivity(new Intent(this, donation.class));
        });
        story.setOnClickListener(v -> {
            setSelectedIcon(story);
            startActivity(new Intent(this, storie.class));
        });
        aboutus.setOnClickListener(v -> {
            setSelectedIcon(aboutus);
            startActivity(new Intent(this, aboutUs.class));
        });
        profile.setOnClickListener(v -> {
            setSelectedIcon(profile);
            startActivity(new Intent(this, profile.class));
        });

        setSelectedIcon(home);
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


    private void fetchUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            welcome.setText("Welcome Guest!");
            return;
        }

        String userId = currentUser.getUid();
        databaseReference.child(userId).child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    welcome.setText("Welcome, " + name + "!");
                } else {
                    welcome.setText("Welcome Guest!");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                welcome.setText("Welcome Guest");
            }
        });
    }
}

