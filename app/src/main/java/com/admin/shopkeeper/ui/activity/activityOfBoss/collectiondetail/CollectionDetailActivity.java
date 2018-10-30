package com.admin.shopkeeper.ui.activity.activityOfBoss.collectiondetail;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CollectionDetailAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.CollectionBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class CollectionDetailActivity extends BaseActivity<CollectionDetailPresenter> implements ICollectionDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_name)
    TextView tvName;
    @BindView(R.id.item_date)
    TextView tvDate;
    @BindView(R.id.item_sale)
    TextView tvSale;
    @BindView(R.id.item_charge)
    TextView tvCharge;
    @BindView(R.id.item_free)
    TextView tvFree;
    @BindView(R.id.item_real)
    TextView tvReal;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ShopCollectionBean bean;
    private CollectionDetailAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new CollectionDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("收款统计详情");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (ShopCollectionBean) getIntent().getSerializableExtra("bean");

        tvName.setText(bean.getNames());
        tvDate.setText(bean.getDinnerDate());
        tvSale.setText(String.valueOf(bean.getTotalMoney()));
        tvCharge.setText(String.valueOf(bean.getChongzhi()));
        tvFree.setText(String.valueOf(bean.getFreeMoney()));
        tvReal.setText(String.valueOf(bean.getChargeMoney()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new CollectionDetailAdapter(R.layout.item_collection_detail);
        recyclerView.setAdapter(adapter);

        if (TextUtils.isEmpty(bean.getDinnerDate())) {
            presenter.getDetail(bean.getStartTime(), bean.getEndTime(), bean.getShopId());
        } else {
            presenter.getDetail(bean.getDinnerDate(), bean.getShopId());
        }
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
    public void success(List<CollectionBean> datas) {
        adapter.setNewData(datas);
    }

}
