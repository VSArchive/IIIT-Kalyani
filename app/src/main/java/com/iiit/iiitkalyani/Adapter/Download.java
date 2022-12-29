package com.iiit.iiitkalyani.Adapter;

import android.net.Uri;

public class Download {
    private String publisher;
    private String ImageUrl;
    private String title;
    private String description;
    private String ProfileUrl;

    public Download() {
        //empty constructor needed
    }

    public Download(String name, String imageUrl, String title, String description, String ProfileUrl) {
        this.publisher = name;
        this.ImageUrl = imageUrl;
        this.ProfileUrl = ProfileUrl;
        this.title = title;
        this.description = description;
    }

    public String getname() {
        return publisher;
    }

    public void setname(String name) {
        this.publisher = name;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getProfileUrl() {
        return ProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        ProfileUrl = profileUrl;
    }

    public Uri getImageUrl() {
        return Uri.parse(ImageUrl);
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}