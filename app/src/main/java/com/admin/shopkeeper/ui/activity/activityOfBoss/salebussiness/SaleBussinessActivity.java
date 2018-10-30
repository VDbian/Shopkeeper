package com.admin.shopkeeper.ui.activity.activityOfBoss.salebussiness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FoodBussinessAdapter;
import com.admin.shopkeeper.adapter.SaleBussinessAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.foodbussiness.FoodBussinessPresenter;
import com.admin.shopkeeper.ui.activity.activityOfBoss.foodbussiness.IFoodBussinessView;
import com.admin.shopkeeper.utils.Tools;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class SaleBussinessActivity extends BaseActivity<SaleBussinessPresenter> implements ISaleBussinessView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SaleBussinessAdapter adapter;
    private String type;

    @Override
    protected void initPresenter() {
        presenter = new SaleBussinessPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sale_bussiness;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        type = getIntent().getStringExtra("type");

        if (type.equals("8")) {
            toolbar.setTitle("热销菜品前10");
        } else {
            toolbar.setTitle("滞销菜品前20");
        }
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new SaleBussinessAdapter(this);
        recyclerView.setAdapter(adapter);

        presenter.getData(type, "1999-01-01", Tools.formatNowDate("yyyy-MM-dd"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(List<FoodBussinessBean> data) {
        adapter.setDatas(data);
    }
}
