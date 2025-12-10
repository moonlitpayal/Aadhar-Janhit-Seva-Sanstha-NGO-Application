package com.example.payalngo;

public class BannerItem {
    public String imageUrl;

    public BannerItem() {
        // Default constructor required for Firebase
    }

    public BannerItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}