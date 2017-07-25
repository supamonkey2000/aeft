package com.jmoore.aeft;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class imageView extends AppCompatActivity {

    public String file;

    public imageView(String str) {
        file = str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        //ImageView iv = (ImageView)findViewById(R.id.imageView);
        //iv.setImageURI(Uri.parse(file));
        System.out.println("IMAGEVIEWWORKS");
    }
}
