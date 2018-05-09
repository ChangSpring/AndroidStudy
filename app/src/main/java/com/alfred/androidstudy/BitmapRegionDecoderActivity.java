package com.alfred.androidstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.androidstudy.widget.LargeTranslationImageView;

import java.io.InputStream;

public class BitmapRegionDecoderActivity extends AppCompatActivity {

    private LargeTranslationImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_region_decoder);

        imageView = (LargeTranslationImageView) findViewById(R.id.imageview);

        try {
            InputStream inputStream = getAssets().open("qm.jpg");
            imageView.setInputStream(inputStream);
            imageView.startHorizontalTranslateAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
