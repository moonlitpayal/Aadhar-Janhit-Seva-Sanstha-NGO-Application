package com.example.payalngo;

public class NewsItem {
    public String title, description, imageURL;

    public NewsItem() {} // For Supabase
    public NewsItem(String title, String description, String imageURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }
}