package com.iiit.iiitkalyani;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;

public class Blog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        Slidr.attach(this);

        Intent intent = getIntent();
        Uri img = intent.getData();
        String title = intent.getStringExtra("title");
        String des = intent.getStringExtra("des");
        ImageView imageView = findViewById(R.id.image);
        Picasso.get().load(img).placeholder(R.drawable.loading).into(imageView);
        TextView tit = findViewById(R.id.title);
        tit.setText(title);

        TextView desc = findViewById(R.id.des);
        desc.setText(des);

    }
}
