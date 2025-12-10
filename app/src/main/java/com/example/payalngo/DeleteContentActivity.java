package com.example.payalngo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteContentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBanners, recyclerViewEvents, recyclerViewNews, recyclerViewStories, recyclerViewGallery;

    private DatabaseReference bannersRef, eventsRef, newsRef, storiesRef, galleryRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_content);

        // Initialize RecyclerViews
        recyclerViewBanners = findViewById(R.id.recyclerViewBanners);
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);
        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        recyclerViewStories = findViewById(R.id.recyclerViewStories);
        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);

        // Set up Layout Managers
        recyclerViewBanners.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStories.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGallery.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase Database References
        bannersRef = FirebaseDatabase.getInstance().getReference("Banners");
        eventsRef = FirebaseDatabase.getInstance().getReference("Events");
        newsRef = FirebaseDatabase.getInstance().getReference("News");
        storiesRef = FirebaseDatabase.getInstance().getReference("Stories");
        galleryRef = FirebaseDatabase.getInstance().getReference("Gallery");

        // Fetch data for all categories
        fetchData(bannersRef, recyclerViewBanners);
        fetchData(eventsRef, recyclerViewEvents);
        fetchData(newsRef, recyclerViewNews);
        fetchData(storiesRef, recyclerViewStories);
        fetchData(galleryRef, recyclerViewGallery);
    }

    private void fetchData(DatabaseReference ref, RecyclerView recyclerView) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ContentItem> itemList = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String id = itemSnapshot.getKey();
                    String title = "Item ID: " + id; // Default title

                    if (itemSnapshot.child("title").exists()) {
                        title = itemSnapshot.child("title").getValue(String.class);
                    } else if (itemSnapshot.child("imageURL").exists()) {
                        title = "Banner Image";
                    }

                    itemList.add(new ContentItem(id, title));
                }
                ContentAdapter adapter = new ContentAdapter(itemList, ref);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteContentActivity.this, "Failed to load content: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // A simple data model class for our RecyclerView
    static class ContentItem {
        public String id;
        public String title;

        public ContentItem(String id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    // RecyclerView Adapter
    class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

        private List<ContentItem> itemList;
        private DatabaseReference ref;

        public ContentAdapter(List<ContentItem> itemList, DatabaseReference ref) {
            this.itemList = itemList;
            this.ref = ref;
        }

        @NonNull
        @Override
        public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
            return new ContentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
            ContentItem item = itemList.get(position);
            holder.titleTextView.setText(item.title);
            holder.deleteButton.setOnClickListener(v -> deleteItem(item.id));
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        private void deleteItem(String id) {
            ref.child(id).removeValue()
                    .addOnSuccessListener(aVoid -> Toast.makeText(DeleteContentActivity.this, "Item deleted successfully.", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(DeleteContentActivity.this, "Failed to delete item: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }

        class ContentViewHolder extends RecyclerView.ViewHolder {
            TextView titleTextView;
            Button deleteButton;

            public ContentViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.itemTitle);
                deleteButton = itemView.findViewById(R.id.deleteBtn);
            }
        }
    }
}