package com.admin.shopkeeper.ui.activity.activityOfBoss.jiondetail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.HandoverDetailAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.GuazhangDetailBean;
import com.admin.shopkeeper.entity.HandoverBean;
import com.admin.shopkeeper.entity.HandoverDetailBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.BindView;

public class JionDetailActivity extends BaseActivity<JionDetailPresenter> implements IJionDetailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_shop)
    TextView tvShop;
    @BindView(R.id.item_time)
    TextView tvDate;
    @BindView(R.id.item_user)
    TextView tvUser;
    @BindView(R.id.item_totle)
    TextView tvTotle;
    @BindView(R.id.item_stand)
    TextView tvStand;
    @BindView(R.id.item_money)
    TextView tvMoney;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private HandoverBean bean;
    private HandoverDetailAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new JionDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jion_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("交接班报表详情");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (HandoverBean) getIntent().getSerializableExtra("bean");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HandoverDetailAdapter(R.layout.item_handover_detail);
        recyclerView.setAdapter(adapter);

        tvShop.setText(App.INSTANCE().getShopName());
        tvDate.setText(bean.getbTime() + "\n" + bean.getTime());
        tvUser.setText(bean.getUsername());
        tvTotle.setText("￥" + bean.getPrice());
        tvStand.setText("￥" + bean.getPrice());
        tvMoney.setText("￥" + bean.getPrice());

        presenter.getData(bean.getGuid());
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
        showToast(msg);
    }

    @Override
    public void success(String msg) {
        showToast(msg);
    }

    @Override
    public void success(List<HandoverDetailBean> datas) {
        adapter.setNewData(datas);
    }
}
