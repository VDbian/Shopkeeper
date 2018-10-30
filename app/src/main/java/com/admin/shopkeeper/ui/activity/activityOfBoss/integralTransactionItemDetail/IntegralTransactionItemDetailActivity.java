package com.admin.shopkeeper.ui.activity.activityOfBoss.integralTransactionItemDetail;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.IntegralTransactionItemDetailAdapter;
import com.admin.shopkeeper.adapter.RechargeTransactionDetailAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.IntegralDetailTableBean;
import com.admin.shopkeeper.entity.IntegralTranscationItemDetailBean;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.entity.RechargeTranscationItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class IntegralTransactionItemDetailActivity extends BaseActivity<IntegralTransactionItemDetailPresenter> implements IIntegralTransactionItemDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String startTime;
    private String endTime;

    private IntegralDetailTableBean bean;
    private IntegralTransactionItemDetailAdapter adapter;
    int pageIndex = 1;
    @Override
    protected void initPresenter() {
        presenter = new IntegralTransactionItemDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_transaction_item_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("积分交易表详情");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (IntegralDetailTableBean) getIntent().getSerializableExtra("bean");
        startTime = getIntent().getStringExtra(Config.PARAM1);
        endTime = getIntent().getStringExtra(Config.PARAM2);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new IntegralTransactionItemDetailAdapter(R.layout.item_integral_transaction_detail);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageIndex++;
                presenter.getDetail(pageIndex ,startTime, endTime,bean.getUserID(),bean.getShopID());
            }
        }, recyclerView);

        recyclerView.setAdapter(adapter);
        presenter.getDetail(pageIndex ,startTime, endTime,bean.getUserID(),bean.getShopID());
       /* if (TextUtils.isEmpty(bean.getDinnerDate())) {

        } else {
            presenter.getDetail(bean.getDinnerDate(), bean.getShopId());
        }*/
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
    public void success(List<IntegralTranscationItemDetailBean> datas) {
        adapter.setNewData(datas);
    }

}
