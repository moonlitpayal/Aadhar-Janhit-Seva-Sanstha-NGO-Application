/*package com.example.payalngo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private List<GalleryItem> galleryList;

    public GalleryAdapter(Context context, List<GalleryItem> galleryList) { // 👇 Constructor ab do arguments le raha hai
        this.context = context;
        this.galleryList = galleryList;
    }

    @Override
    public int getCount() {
        return galleryList.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gallery_item_layout, parent, false);
        }

        ImageView img = view.findViewById(R.id.galleryImage);
        TextView title = view.findViewById(R.id.galleryTitle);

        GalleryItem currentItem = galleryList.get(position);
        title.setText(currentItem.title);

        Glide.with(context)
                .load(currentItem.imageURL)
                .placeholder(R.drawable.image)
                .into(img);

        return view;
    }
}*/



/*package com.example.payalngo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private List<GalleryItem> galleryList;

    public GalleryAdapter(Context context, List<GalleryItem> galleryList) {
        this.context = context;
        this.galleryList = galleryList;
    }

    @Override
    public int getCount() {
        return galleryList.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gallery_item_layout, parent, false);
        }

        ImageView img = view.findViewById(R.id.galleryImage);
        TextView title = view.findViewById(R.id.galleryTitle);

        GalleryItem currentItem = galleryList.get(position);
        title.setText(currentItem.title);

        if (currentItem.imageURL != null && !currentItem.imageURL.isEmpty()) {
            Glide.with(context)
                    .load(currentItem.imageURL)
                    .placeholder(R.drawable.image)
                    .into(img);
        } else {
            img.setImageResource(R.drawable.image);
        }

        return view;
    }
}*/



package com.example.payalngo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private List<GalleryItem> galleryList;

    public GalleryAdapter(Context context, List<GalleryItem> galleryList) {
        this.context = context;
        this.galleryList = galleryList;
    }

    @Override
    public int getCount() {
        return galleryList.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gallery_item_layout, parent, false);
        }

        ImageView img = view.findViewById(R.id.galleryImage);
        TextView title = view.findViewById(R.id.galleryTitle);

        GalleryItem currentItem = galleryList.get(position);
        title.setText(currentItem.title);

        // ✅ Glide se image load karein
        if (currentItem.imageURL != null && !currentItem.imageURL.isEmpty()) {
            Glide.with(context)
                    .load(currentItem.imageURL)
                    .placeholder(R.drawable.image)
                    .into(img);
        } else {
            // ✅ Fallback: Agar URL nahi hai to local image dikhe
            img.setImageResource(R.drawable.image);
        }

        return view;
    }
}