package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTransactionItemDetail;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CollectionDetailAdapter;
import com.admin.shopkeeper.adapter.RechargeTransactionDetailAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.CollectionBean;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.entity.RechargeDetailTableBean;
import com.admin.shopkeeper.entity.RechargeTranscationItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class RechargeTranscationItemDetailActivity extends BaseActivity<RechargeTranscationItemDetailPresenter> implements IRechargeTranscationItemDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_shop_name)
    TextView tvShopName;
    @BindView(R.id.item_date)
    TextView tvDate;
    @BindView(R.id.item_username)
    TextView tvUserName;
    @BindView(R.id.item_phone)
    TextView tvPhone;
    @BindView(R.id.item_cumulative_recharge)
    TextView tvCumulativeCharge;
    @BindView(R.id.item_cumulative_consume)
    TextView tvCumulativeConsume;
    @BindView(R.id.item_consume_money)
    TextView tvConsumeMoney;
    @BindView(R.id.item_recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.item_yue)
    TextView tvYuE;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private String startTime;
    private String endTime;

    private MemberTranscationBean bean;
    private RechargeTransactionDetailAdapter adapter;
    int pageIndex = 1;

    @Override
    protected void initPresenter() {
        presenter = new RechargeTranscationItemDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_recharge_item_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("充值交易表详情");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (MemberTranscationBean) getIntent().getSerializableExtra("bean");
        startTime = getIntent().getStringExtra(Config.PARAM1);
        endTime = getIntent().getStringExtra(Config.PARAM2);

        tvShopName.setText(bean.getShopName());
        tvUserName.setText(bean.getName());
        tvPhone.setText(bean.getPhone());
        tvCumulativeCharge.setText(bean.getRechargeMoney());
        tvCumulativeConsume.setText(bean.getUsedMoney());
        tvConsumeMoney.setText(bean.getZonJian());
        tvRechargeMoney.setText(bean.getZonAdd());
        tvYuE.setText(bean.getYue());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new RechargeTransactionDetailAdapter(R.layout.item_recharge_transaction_detail);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageIndex++;
                presenter.getDetail(pageIndex, startTime, endTime, bean.getUserID(), bean.getShopID());
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                presenter.getDetail(pageIndex, startTime, endTime, bean.getUserID(), bean.getShopID());
            }
        });
        presenter.getDetail(pageIndex, startTime, endTime, bean.getUserID(), bean.getShopID());

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
    public void success(List<RechargeTranscationItemBean> datas) {
        adapter.setNewData(datas);
        adapter.loadMoreComplete();
        adapter.loadMoreEnd();
        refreshLayout.setRefreshing(false);
    }

}
