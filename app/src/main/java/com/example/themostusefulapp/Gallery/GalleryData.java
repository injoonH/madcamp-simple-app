package com.example.themostusefulapp.Gallery;

import java.io.InputStream;


public class GalleryData {

    InputStream imageView;

    public GalleryData(InputStream imageView) {
        this.imageView = imageView;
    }

    public InputStream getImageView() {
        return imageView;
    }

    public void setImageView(InputStream imageView) {
        this.imageView = imageView;
    }
}
