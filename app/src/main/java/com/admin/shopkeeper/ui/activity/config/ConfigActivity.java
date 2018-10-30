package com.admin.shopkeeper.ui.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.ui.activity.config.id.ConfigIDActivity;
import com.admin.shopkeeper.ui.activity.config.menu.MenuTypeActivity;
import com.admin.shopkeeper.ui.activity.config.room.ConfigRoomActivity;
import com.admin.shopkeeper.ui.activity.foodsList.FoodsListActivity;
import com.admin.shopkeeper.ui.activity.meal.MealActivity;
import com.gyf.barlibrary.ImmersionBar;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("系统配置");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();


        findViewById(R.id.layoutID).setOnClickListener(v -> {
            Intent intent = new Intent(ConfigActivity.this, ConfigIDActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.layoutRoom).setOnClickListener(v -> {
            Intent intent = new Intent(ConfigActivity.this, ConfigRoomActivity.class);
            startActivity(intent);
        });


        findViewById(R.id.layoutMenu).setOnClickListener(v -> {
            Intent intent = new Intent(ConfigActivity.this, FoodsListActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.layoutMeal).setOnClickListener(v -> {
            Intent intent = new Intent(ConfigActivity.this, MealActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
