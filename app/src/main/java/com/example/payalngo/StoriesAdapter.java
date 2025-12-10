package com.example.payalngo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {

    private List<StorieItem> storieList;

    public StoriesAdapter(List<StorieItem> storieList) {
        this.storieList = storieList;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storie_item_layout, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        if (storieList == null || position >= storieList.size()) {
            return;
        }

        StorieItem currentItem = storieList.get(position);
        holder.storieTitle.setText(currentItem.title);
        holder.storieDescription.setText(currentItem.description);
    }

    @Override
    public int getItemCount() {
        return storieList != null ? storieList.size() : 0;
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        public TextView storieTitle, storieDescription;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            storieTitle = itemView.findViewById(R.id.storieTitle);
            storieDescription = itemView.findViewById(R.id.storieDescription);
        }
    }
}



/*package com.example.payalngo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {

    private List<StorieItem> storieList;

    public StoriesAdapter(List<StorieItem> storieList) {
        this.storieList = storieList;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storie_item_layout, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        if (storieList == null || position >= storieList.size()) {
            return;
        }

        StorieItem currentItem = storieList.get(position);
        holder.storieTitle.setText(currentItem.title);
        holder.storieDescription.setText(currentItem.description);

        if (currentItem.imageURL != null && !currentItem.imageURL.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(currentItem.imageURL)
                    .placeholder(R.drawable.image)
                    .into(holder.storieImage);
        } else {
            holder.storieImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return storieList != null ? storieList.size() : 0;
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        public TextView storieTitle, storieDescription;
        public ImageView storieImage;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            storieTitle = itemView.findViewById(R.id.storieTitle);
            storieDescription = itemView.findViewById(R.id.storieDescription);
            storieImage = itemView.findViewById(R.id.storieImage);
        }
    }
}*/