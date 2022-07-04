package com.example.themostusefulapp.Contact;

import java.io.InputStream;


public class Contact {
    private final String name;
    private final String number;
    private final InputStream photo;

    public Contact(String name, String number, InputStream photo) {
        this.name = name;
        this.number = number;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public InputStream getPhoto() {
        return photo;
    }
}