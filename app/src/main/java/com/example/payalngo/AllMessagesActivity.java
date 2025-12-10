package com.example.payalngo;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.util.*;

public class AllMessagesActivity extends AppCompatActivity {

    private ListView MsgListView;
    private List<Map<String, String>> MsgList;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_messages); // ✅ your activity layout

        MsgListView = findViewById(R.id.MessagesAdmin);
        MsgList = new ArrayList<>();

        adapter = new SimpleAdapter(
                this,
                MsgList,
                R.layout.msg_list_view, // ✅ item layout
                new String[]{"Message", "Name"},
                new int[]{R.id.nameeText, R.id.MsgText}
        );
        MsgListView.setAdapter(adapter);

        DatabaseReference MsgRef = FirebaseDatabase.getInstance().getReference("ContactUSData");

        MsgRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MsgList.clear();

                if (!snapshot.exists()) {
                    Toast.makeText(AllMessagesActivity.this, "No messages found", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (DataSnapshot snap : snapshot.getChildren()) {
                    String msg = snap.child("Message").getValue(String.class);
                    String name = snap.child("Name").getValue(String.class);

                    if (name != null && msg != null) {
                        Map<String, String> map = new HashMap<>();
                        map.put("Message", msg);
                        map.put("Name", name);
                        MsgList.add(map);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AllMessagesActivity.this,
                        "Error: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
