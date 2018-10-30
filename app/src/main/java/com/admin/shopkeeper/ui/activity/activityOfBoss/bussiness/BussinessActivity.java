package com.admin.shopkeeper.ui.activity.activityOfBoss.bussiness;


import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.desk.DeskBussinessActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.foodbussiness.FoodBussinessActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.orderBussiness.OrderBussinessActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.paybussiness.PayBussinessActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.waiterbussiness.WaiterBussinessActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

public class BussinessActivity extends BaseActivity<BussinessPresenter> implements IBussinessView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initPresenter() {
        presenter = new BussinessPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bussiness;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("营业收款");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);
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

    @OnClick(R.id.bussiness_desk)
    public void deskClick() {
        startActivity(DeskBussinessActivity.class);
    }

    @OnClick(R.id.bussiness_food)
    public void foodClick() {
        startActivity(FoodBussinessActivity.class);
    }

    @OnClick(R.id.bussiness_service)
    public void serviceClick() {
        startActivity(WaiterBussinessActivity.class);
    }

    @OnClick(R.id.bussiness_payfor)
    public void payforClick() {
        startActivity(PayBussinessActivity.class);
    }

    @OnClick(R.id.bussiness_order)
    public void orderClick() {
        startActivity(OrderBussinessActivity.class);
    }
}
