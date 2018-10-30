package com.admin.shopkeeper.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.bumptech.glide.Glide;

public class BigImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        String url = getIntent().getStringExtra(Config.PARAM1);
        Glide.with(this).load(Config.BASE_IMG + App.INSTANCE().getShopID() + "/" + url).into(imageView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return false;
    }
}
