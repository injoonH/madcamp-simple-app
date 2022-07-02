package com.example.themostusefulapp;

import android.widget.ImageView;

import java.io.InputStream;

public class GalleryData {

    ImageView imageView;

    public GalleryData(ImageView imageView) {
        this.imageView = imageView;
    }

    public InputStream getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
