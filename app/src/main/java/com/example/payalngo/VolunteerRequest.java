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

public class VolunteerRequest extends AppCompatActivity {

    private ListView volunteerListView;
    private List<Map<String, String>> volunteerList;
    private SimpleAdapter adapter;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_request);

        volunteerListView = findViewById(R.id.volunteerListView);
        volunteerList = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Volunteers");

        adapter = new SimpleAdapter(
                this,
                volunteerList,
                R.layout.volunteer_list_view,
                new String[]{"name", "phone", "email", "skills", "interest"},
                new int[]{R.id.fullName, R.id.phone, R.id.email, R.id.skills, R.id.interest}
        );

        volunteerListView.setAdapter(adapter);

        fetchVolunteerData();
    }

    private void fetchVolunteerData() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                volunteerList.clear();
                if (!snapshot.exists()) {
                    Toast.makeText(VolunteerRequest.this, "No volunteer data found", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    return;
                }

                for (DataSnapshot snap : snapshot.getChildren()) {
                    String name = snap.child("name").getValue(String.class);
                    String email = snap.child("email").getValue(String.class);
                    String phone = snap.child("phone").getValue(String.class);
                    String skills = snap.child("skills").getValue(String.class);
                    String interest = snap.child("interest").getValue(String.class);

                    Map<String, String> volunteer = new HashMap<>();
                    volunteer.put("name", name != null ? name : "N/A");
                    volunteer.put("email", email != null ? email : "N/A");
                    volunteer.put("phone", phone != null ? phone : "N/A");
                    volunteer.put("skills", skills != null ? skills : "N/A");
                    volunteer.put("interest", interest != null ? interest : "N/A");

                    volunteerList.add(volunteer);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VolunteerRequest.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}