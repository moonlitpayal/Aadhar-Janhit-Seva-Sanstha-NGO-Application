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

public class DonationsActivity extends AppCompatActivity {

    private ListView DonationsListView;
    private List<Map<String, String>> DonationsList;
    private DatabaseReference DonationsRef;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);

        DonationsListView = findViewById(R.id.DonationsAdmin);
        DonationsList = new ArrayList<>();
        DonationsRef = FirebaseDatabase.getInstance().getReference("Donations");

        adapter = new SimpleAdapter(
                this,
                DonationsList,
                R.layout.donation_list_view,
                new String[]{"Name", "Message", "Amount", "Date"},
                new int[]{R.id.nameeText, R.id.MsgText, R.id.amountText, R.id.timestampText}
        );
        DonationsListView.setAdapter(adapter);

        fetchDonationData();
    }

    private void fetchDonationData() {
        DonationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonationsList.clear();

                for (DataSnapshot donationSnap : snapshot.getChildren()) {
                    String name = donationSnap.child("Name").getValue(String.class);
                    String message = donationSnap.child("Message").getValue(String.class);
                    String amount = String.valueOf(donationSnap.child("Amount").getValue());
                    String date = donationSnap.child("Date").getValue(String.class); //

                    Map<String, String> donationMap = new HashMap<>();
                    donationMap.put("Name", name != null ? name : "N/A");
                    donationMap.put("Message", message != null ? message : "N/A");
                    donationMap.put("Amount", "₹" + (amount != null ? amount : "0"));
                    donationMap.put("Date", date != null ? date : "N/A");

                    DonationsList.add(donationMap);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DonationsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}