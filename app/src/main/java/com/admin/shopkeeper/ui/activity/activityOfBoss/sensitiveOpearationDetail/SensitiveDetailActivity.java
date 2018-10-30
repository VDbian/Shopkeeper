package com.admin.shopkeeper.ui.activity.activityOfBoss.sensitiveOpearationDetail;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.OrderManageDetailAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.OrderManage;
import com.admin.shopkeeper.entity.OrderManageDetail;
import com.admin.shopkeeper.entity.SensitiveOpearation;
import com.admin.shopkeeper.utils.Tools;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class SensitiveDetailActivity extends BaseActivity<SensitiveDetailPresenter> implements ISensitiveDetailView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PopupWindow laheiPop;
    private PopupWindow popupWindow;
    private String titleStr;
    @BindView(R.id.item_sensitive_id)
    TextView tvID;
    @BindView(R.id.item_order_state)
    TextView tvState;
    @BindView(R.id.item_sensitive_num)
    TextView tvNum;
    @BindView(R.id.item_sensitive_money)
    TextView tvTotal;
    @BindView(R.id.item_sensitive_person)
    TextView tvPerson;
    @BindView(R.id.item_sensitive_time)
    TextView tvTime;


    OrderManageDetailAdapter adapter;
    SensitiveOpearation bean;
    int page = 0;
    @Override
    protected void initPresenter() {
        presenter = new SensitiveDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sensitive_detail;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        bean = (SensitiveOpearation) getIntent().getSerializableExtra("bean");
        page = getIntent().getIntExtra(Config.PARAM1 , 0);
        toolbar.setTitle("订单敏感操作详情");

        tvID.setText("订单号："+bean.getOrderNumber());
        if(bean.getSensitiveState().equals("0")){
            tvState.setText("撤销");
        }else{
            tvState.setText("反结账");
        }
        tvNum.setText(bean.getTableNmae());
        tvTotal.setText(bean.getPice()+"");
        tvPerson.setText(bean.getUserName()+"");
        tvTime.setText(bean.getTimes());
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new OrderManageDetailAdapter(R.layout.item_order_manage_detail);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {

        });
        String startDate = Tools.formatLastMonthDate("yyyy-MM");
        String endDate = Tools.formatNowDate("yyyy-MM");

        presenter.getSensitiveDetail(bean.getId());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (resultCode == RESULT_OK) {
            presenter.getCouponInfo();
        }*/
        if (laheiPop != null && laheiPop.isShowing()) {
            laheiPop.dismiss();
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

    Date startDate;
    Date endDate;

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }

    @Override
    public void success(List<OrderManageDetail> list) {
        adapter.setNewData(list);
    }

}
