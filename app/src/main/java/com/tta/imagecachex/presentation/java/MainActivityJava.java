package com.tta.imagecachex.presentation.java;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tta.imagecachex.R;
import com.tta.imagecachex.domain.main.ImageCacheX;
import com.tta.imagecachex.domain.util.Constants;

import dagger.hilt.android.AndroidEntryPoint;

//this is proof of concept class that ImageCacheX library works from .java
@AndroidEntryPoint
public class MainActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_main_item_view);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewMain);
        ImageCacheX imageLoader = ImageCacheX.Companion.getInstance(getApplicationContext(), Constants.Companion.getDefaultCacheSize());
                imageLoader.displayImage(
                "https://zipoapps-storage-test.nyc3.digitaloceanspaces.com/2977101.jpg",
                imageView,
                R.drawable.image_loading
            );
    }
}
