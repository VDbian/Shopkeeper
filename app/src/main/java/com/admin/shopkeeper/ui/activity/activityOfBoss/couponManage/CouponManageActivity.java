package com.admin.shopkeeper.ui.activity.activityOfBoss.couponManage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CouponManager2Adapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.ShopSelectDialog;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.addCoupon.AddCouponActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.coupondetail.CouPonDetailActivity;
import com.admin.shopkeeper.utils.UIUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CouponManageActivity extends BaseActivity<CouponManagePresenter> implements ICouponManageView,
        DatePicker.OnDateChangedListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coupon_manage_num)
    TextView tvNum;
    @BindView(R.id.coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.coupon_manage_need_money)
    TextView tvNeedMoney;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private CouponManager2Adapter adapter;
    private PopupWindow laheiPop;
    List<CouponManageBean> data = new ArrayList<>();
    private PopupWindow popupWindow;
    private String titleStr;
    private ArrayAdapter<String> arrayAdapter;
    int page = 1;

    @Override
    protected void initPresenter() {
        presenter = new CouponManagePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_manage;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        date = new StringBuffer();
        titleStr = getIntent().getStringExtra(Config.PARAM1);
        if (titleStr.equals("微信券管理")) {
            toolbar.setTitle("微信券管理");
        } else if (titleStr.equals("线下券管理")) {
            toolbar.setTitle("线下券管理");
        } else {
            toolbar.setTitle("商品券管理");
        }

        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new CouponManager2Adapter(R.layout.item_coupon_manage);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showDeletePop(adapter.getItem(position));
        });

        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getCouponInfo(page, "");
        }, recyclerView);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getCouponInfo(1, "");
            }
        });
        if (titleStr.equals("优惠券管理")) {
            presenter.getCouponInfo(1, "");
        } else if (titleStr.equals("线下券管理")) {
            presenter.getLineDownInfo(1);
        } else {
            presenter.getGroupCouponInfo();
            presenter.getRechargeInfo();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getCouponInfo(1, "");
        }
        if (laheiPop != null && laheiPop.isShowing()) {
            laheiPop.dismiss();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add:
                Intent intent = new Intent(this, AddCouponActivity.class);
                intent.putExtra(Config.PARAM1, titleStr);
                startActivityForResult(intent, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    int defaultType = 0;

    @OnClick(R.id.coupon_manage_num)
    public void setDefaultClick() {
        defaultType++;

        if (data == null) {
            return;
        }
        if (defaultType % 3 == 1) {
            UIUtils.setDrawableRight(tvNum, R.mipmap.sort_a_z);
            List<CouponManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getCounts()) > Integer.parseInt(o2.getCounts())) {
                    return 1;
                } else if (Integer.parseInt(o1.getCounts()) == Integer.parseInt(o2.getCounts())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setNewData(newData);
        } else if (defaultType % 3 == 2) {
            UIUtils.setDrawableRight(tvNum, R.mipmap.sort_z_a);
            List<CouponManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (Integer.parseInt(o1.getCounts()) > Integer.parseInt(o2.getCounts())) {
                    return -1;
                } else if (Integer.parseInt(o1.getCounts()) > Integer.parseInt(o2.getCounts())) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tvNum, R.mipmap.sort_default);
            adapter.setNewData(data);
        }
        UIUtils.setDrawableRight(tvCouponMoney, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvNeedMoney, R.mipmap.sort_default);
    }

    int nameType = 0;

    @OnClick(R.id.coupon_money)
    public void setNameClick() {
        nameType++;
        if (data == null) {
            return;
        }
        if (nameType % 3 == 1) {
            UIUtils.setDrawableRight(tvCouponMoney, R.mipmap.sort_a_z);
            List<CouponManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (o1.getPrice() > o2.getPrice()) {
                    return 1;
                } else if (o1.getPrice() == o2.getPrice()) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setNewData(newData);
        } else if (nameType % 3 == 2) {
            UIUtils.setDrawableRight(tvCouponMoney, R.mipmap.sort_z_a);
            List<CouponManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (o1.getPrice() > o2.getPrice()) {
                    return -1;
                } else if (o1.getPrice() == o2.getPrice()) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tvCouponMoney, R.mipmap.sort_default);
        }
        UIUtils.setDrawableRight(tvNum, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvNeedMoney, R.mipmap.sort_default);
    }

    int stateType = 0;

    @OnClick(R.id.coupon_manage_need_money)
    public void setStateClick() {
        stateType++;
        if (data == null) {
            return;
        }
        if (stateType % 3 == 1) {
            UIUtils.setDrawableRight(tvNeedMoney, R.mipmap.sort_a_z);
            List<CouponManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (o1.getSumPrice() > o2.getSumPrice()) {
                    return 1;
                } else if (o1.getSumPrice() == o2.getSumPrice()) {
                    return 0;
                } else {
                    return -1;
                }
            });
            adapter.setNewData(newData);
        } else if (stateType % 3 == 2) {
            UIUtils.setDrawableRight(tvNeedMoney, R.mipmap.sort_z_a);
            List<CouponManageBean> newData = new ArrayList<>();
            newData.addAll(data);
            Collections.sort(newData, (o1, o2) -> {
                if (o1.getSumPrice() > o2.getSumPrice()) {
                    return -1;
                } else if (o1.getSumPrice() == o2.getSumPrice()) {
                    return 0;
                } else {
                    return 1;
                }
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tvNeedMoney, R.mipmap.sort_default);
        }
        UIUtils.setDrawableRight(tvCouponMoney, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvNum, R.mipmap.sort_default);
    }

    @OnClick(R.id.coupon_manage_select)
    public void selectClick() {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_spinner_select, null);
        popupWindow = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        Spinner spinnerSelect = (Spinner) laheiView.findViewById(R.id.spinner_coupon_type);
        String[] couponTypes = getResources().getStringArray(R.array.couponType);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(couponTypes));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelect.setAdapter(arrayAdapter);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getCouponInfo(1, "");
                popupWindow.dismiss();
            }
        });

        laheiView.findViewById(R.id.pop_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = spinnerSelect.getSelectedItemPosition();
                if (position > 0) {
                    presenter.getCouponInfo(1, position + "");
                }
                popupWindow.dismiss();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        popupWindow.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
        refreshLayout.setRefreshing(false);
        adapter.loadMoreEnd();
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        if (titleStr.equals("优惠券管理")) {
            presenter.getCouponInfo(1, "");
        } else if (titleStr.equals("线下券管理")) {
            presenter.getLineDownInfo(1);
        } else {
            presenter.getGroupCouponInfo();
            presenter.getRechargeInfo();
        }
    }

    @Override
    public void success(List<CouponManageBean> couponManageBeanList) {
        if(page == 1){
            this.data.clear();
        }
        this.data.addAll(couponManageBeanList);
        adapter.setNewData(data);
        refreshLayout.setRefreshing(false);
        if (couponManageBeanList.size() < 20) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
        if (laheiPop != null && laheiPop.isShowing()) {
            laheiPop.dismiss();
        }
    }

    @Override
    public void shopsuccess(CouponManageBean bean, List<ShopBean> shopBeen) {
        if (shopBeen == null || shopBeen.size() == 0) {
            showFailToast("暂无商家列表");
            return;
        }

        for (int i = 0; i < shopBeen.size(); i++) {
            if (bean.getMerchantID().contains(shopBeen.get(i).getGuid())) {
                shopBeen.get(i).setSelect(true);
            }
        }

        ShopSelectDialog.Builder builder = new ShopSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("设置商家");
        builder.setReasons(shopBeen);
        builder.setButtonClick(new ShopSelectDialog.OnButtonClick() {


            @Override
            public void onOk(String text, String value) {
                presenter.setShopData1(bean, value);

            }
        });
        builder.creater().show();
    }

    public void showDeletePop(CouponManageBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_coupon, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });

        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.deleteCoupon(bean.getPiCi());
        });

        laheiView.findViewById(R.id.pop_detail).setOnClickListener(v -> {
            Intent intent = new Intent(this, CouPonDetailActivity.class);
            intent.putExtra(Config.PARAM2, bean);
            intent.putExtra(Config.PARAM1, titleStr);
            startActivityForResult(intent, 101);
            laheiPop.dismiss();
        });

        laheiView.findViewById(R.id.pop_setshop).setOnClickListener(v -> {
            presenter.getShopData(bean);
            laheiPop.dismiss();
        });

        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCouponActivity.class);
            intent.putExtra(Config.PARAM2, bean);
            intent.putExtra(Config.PARAM1, titleStr);
            startActivityForResult(intent, 101);
            laheiPop.dismiss();
        });

        laheiPop.setOutsideTouchable(true);
        laheiPop.setFocusable(true);
        laheiPop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        laheiPop.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        laheiPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

    }

    private StringBuffer date;
    private int year, month, day;

    public void selectDate(TextView tv) {
        initDateTime();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                tv.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month - 1, day, this);
    }

    /**
     * 日期改变的监听事件
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
    }

}
