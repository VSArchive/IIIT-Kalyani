package com.iiit.iiitkalyani.Adapter;

import android.net.Uri;

public class Users {
    private String mName;
    private String mEmail;
    private Uri mImageUrl;

    public Users() {
        //empty constructor needed
    }

    public Users(String name, Uri image, String email) {
        mName = name;
        mEmail = email;
        mImageUrl = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Uri getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(Uri image) {
        mImageUrl = image;
    }
}