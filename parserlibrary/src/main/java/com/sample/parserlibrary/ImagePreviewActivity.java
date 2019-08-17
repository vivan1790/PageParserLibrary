package com.sample.parserlibrary;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ImagePreviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.BLACK);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.setBackgroundColor(Color.BLACK);
        AppCompatImageView imagePreview = new TouchImageView(this);
        imagePreview.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imagePreview.setBackgroundColor(Color.TRANSPARENT);
        imagePreview.setImageBitmap(Utils.mCurrentPreviewImageBitmap);
        linearLayout.addView(imagePreview);
        getWindow().setNavigationBarColor(Color.BLACK);
        setContentView(linearLayout);
    }
}
