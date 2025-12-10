package com.example.payalngo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutUsFragment extends Fragment {

    private TextView welcomeText;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 👇 Fragment ka layout inflate karte hain
        View view = inflater.inflate(R.layout.activity_about_us, container, false);

        // Firebase aur Views ko initialize karo
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        welcomeText = view.findViewById(R.id.welcome);

        // Welcome name fetch karo
        fetchUserName();

        return view;
    }

    private void fetchUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            welcomeText.setText("Welcome Guest!");
            return;
        }

        String userId = currentUser.getUid();

        databaseReference.child(userId).child("Full Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null && !name.isEmpty()) {
                    welcomeText.setText("Welcome, " + name + "!");
                } else {
                    welcomeText.setText("Welcome!");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                welcomeText.setText("Welcome!");
            }
        });
    }
}