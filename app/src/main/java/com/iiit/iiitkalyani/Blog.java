package com.iiit.iiitkalyani;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class Blog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        Intent intent = getIntent();
        Uri img = intent.getData();
        String title = intent.getStringExtra("title");
        String des = intent.getStringExtra("des");
        ImageView imageView = findViewById(R.id.image);
        Glide.with(this).load(img).into(imageView);
        TextView tit = findViewById(R.id.title);
        tit.setText(title);

        TextView desc = findViewById(R.id.des);
        desc.setText(des);

    }
}