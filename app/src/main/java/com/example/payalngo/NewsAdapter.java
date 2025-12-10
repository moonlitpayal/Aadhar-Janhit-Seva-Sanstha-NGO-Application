package com.example.payalngo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;

    public NewsAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        if (newsList == null || position >= newsList.size()) {
            return;
        }

        NewsItem currentItem = newsList.get(position);
        holder.newsTitle.setText(currentItem.title);
        holder.newsDescription.setText(currentItem.description);

        if (currentItem.imageURL != null && !currentItem.imageURL.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(currentItem.imageURL)
                    .placeholder(R.drawable.image)
                    .into(holder.newsImage);
        }
    }

    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView newsTitle, newsDescription;
        public ImageView newsImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDescription = itemView.findViewById(R.id.newsDescription);
            newsImage = itemView.findViewById(R.id.newsImage);
        }
    }
}