package com.admin.shopkeeper.ui.activity.activityOfBoss.freedetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FreeDetailAdapter;
import com.admin.shopkeeper.adapter.HandoverDetailAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FreeBean;
import com.admin.shopkeeper.entity.FreeDetailBean;
import com.admin.shopkeeper.entity.HandoverBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.free.IFreeView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail.IJionDetailView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail.JionDetailPresenter;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.BindView;

public class FreeDetailActivity extends BaseActivity<FreeDetailPresenter> implements IFreeDetailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_money)
    TextView tvMoney;
    @BindView(R.id.item_shop)
    TextView tvShop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private FreeBean bean;
    private String start;
    private String end;
    private FreeDetailAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new FreeDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_free_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("优惠明细详情");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bean = (FreeBean) intent.getSerializableExtra("bean");
        start = intent.getStringExtra("start");
        end = intent.getStringExtra("end");

        tvShop.setText(bean.getNames());
        tvMoney.setText("￥" + String.valueOf(bean.getFreeMoney()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FreeDetailAdapter(R.layout.item_free_detail);
        recyclerView.setAdapter(adapter);

        presenter.getDetail(start, end, bean.getShopId());
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
    public void success(List<FreeDetailBean> list) {
        adapter.setNewData(list);
    }
}
