package com.admin.shopkeeper.ui.activity.activityOfBoss.orderManageDetail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.admin.shopkeeper.utils.Tools;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class OrderManageDetailActivity extends BaseActivity<OrderManageDetailPresenter> implements IOrderManageDetailView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PopupWindow laheiPop;
    private PopupWindow popupWindow;
    private String titleStr;
    @BindView(R.id.item_order_id)
    TextView tvRrderID;
    @BindView(R.id.item_order_type)
    TextView tvType;
    @BindView(R.id.item_order_total)
    TextView tvTotal;
    @BindView(R.id.item_order_youhui)
    TextView tvYouhui;
    @BindView(R.id.item_order_jieZhang)
    TextView tvJieZhang;
    @BindView(R.id.item_order_person)
    TextView tvPerson;
    @BindView(R.id.item_order_time)
    TextView tvTime;

    OrderManageDetailAdapter adapter;
    OrderManage bean;
    int page = 0;
    @Override
    protected void initPresenter() {
        presenter = new OrderManageDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_manage_detail;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        bean = (OrderManage) getIntent().getSerializableExtra("bean");
        page = getIntent().getIntExtra(Config.PARAM1 , 0);
        toolbar.setTitle("订单管理详情");

        tvRrderID.setText("订单号："+bean.getOrderNumber());
        switch (bean.getType()){
            case "1":
                tvType.setText("预定菜品");
                break;
            case "2":
                tvType.setText("预定桌位");
                break;
            case "3":
                tvType.setText("外卖");
                break;
            case "4":
                tvType.setText("快餐");
                break;
            case "5":
                tvType.setText("扫码点餐");
                break;
            case "6":
                tvType.setText("排队点餐");
                break;
            case "7":
                tvType.setText("店内点餐");
                break;
            default:
                break;
        }
        tvTotal.setText("￥" +bean.getTotalMoney());
        tvYouhui.setText("￥" +bean.getFreeMoney());
        tvJieZhang.setText("￥" + bean.getChargeMoney());
        tvPerson.setText(bean.getUsername());
        tvTime.setText(bean.getJieZhangTime());
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

        presenter.getOrderManageDetail(page, bean.getId());


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
