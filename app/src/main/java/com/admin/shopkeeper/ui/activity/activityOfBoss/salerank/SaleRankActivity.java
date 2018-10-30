package com.admin.shopkeeper.ui.activity.activityOfBoss.salerank;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.salebussiness.SaleBussinessActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

public class SaleRankActivity extends BaseActivity<SaleRankPresenter> implements ISaleRankView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initPresenter() {
        presenter = new SaleRankPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sale_rank;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("热销排行");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);
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

    @OnClick(R.id.sale_hot)
    public void hotClick() {
        Intent intent = new Intent(this, SaleBussinessActivity.class);
        intent.putExtra("type", "8");
        startActivity(intent);
    }

    @OnClick(R.id.sale_low)
    public void lowClick() {
        Intent intent = new Intent(this, SaleBussinessActivity.class);
        intent.putExtra("type", "9");
        startActivity(intent);
    }
}
