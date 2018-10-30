package com.admin.shopkeeper.ui.activity.activityOfBoss.sensitiveOpearation;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.SaleStatisticsAdapter;
import com.admin.shopkeeper.adapter.SensitiveOpearationAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.FoodSelectDialog;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.dialog.TimeTypeDialog;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.SensitiveOpearation;
import com.admin.shopkeeper.ui.activity.activityOfBoss.orderManage.OrderManageActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.orderManageDetail.OrderManageDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.sensitiveOpearationDetail.SensitiveDetailActivity;
import com.admin.shopkeeper.utils.Tools;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;
import com.gyf.barlibrary.ImmersionBar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class SensitiveOpearationActivity extends BaseActivity<SensitiveOpearationPresenter> implements ISensitiveOpearationView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private PopupWindow popupWindow;

    int page = 1;
    private SensitiveOpearationAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new SensitiveOpearationPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gift_statistics;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("订单敏感操作");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SensitiveOpearationAdapter(R.layout.item_sensitive_opearation);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getData(page, Tools.formatNowDate("yyyy-MM-dd", startDate),
                        Tools.formatNowDate("yyyy-MM-dd", entDate),
                        Tools.formatNowDate("HH:mm:ss", startDate),
                        Tools.formatNowDate("HH:mm:ss", entDate),
                        0, 0);
            }
        });
        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getData(page, Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", entDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", entDate),
                    0, 0);
        }, recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SensitiveOpearationActivity.this , SensitiveDetailActivity.class);
                intent.putExtra("bean", (Serializable) adapter.getItem(position));
                intent.putExtra(Config.PARAM1,page);
                startActivity(intent);
            }
        });


        startDate = new Date(System.currentTimeMillis());
        entDate = new Date(System.currentTimeMillis());

        presenter.getData(page, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate),
                0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search:
                showSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Date startDate;
    Date entDate;

    private void showSearch() {
        List<String> types = new ArrayList<>();
        types.add("营业时间");
        types.add("自定义时间");
        List<String> sensitiveTypes = new ArrayList<>();
        sensitiveTypes.add("撤销");
        sensitiveTypes.add("反结账");

        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_select_1, null);
        popupWindow = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        TextView etStartTime = (TextView) laheiView.findViewById(R.id.pop_starttime);
        TextView etEndTime = (TextView) laheiView.findViewById(R.id.pop_endtime);
        TextView tvTimeType = (TextView) laheiView.findViewById(R.id.pop_time_typw);
        TextView tvShop = (TextView) laheiView.findViewById(R.id.tv_shop);
        TextView tvFoodChoice = (TextView) laheiView.findViewById(R.id.tv_food_choice);
        tvFoodChoice.setText("敏感操作");
        LinearLayout llFood = (LinearLayout) laheiView.findViewById(R.id.ll_food);
        TextView tvFood = (TextView) laheiView.findViewById(R.id.tv_food);

        llFood.setVisibility(View.VISIBLE);

        tvFood.setText("撤销");
        tvShop.setText(App.INSTANCE().getShopName());


        tvTimeType.setOnClickListener(v -> {
            TimeTypeDialog.Builder builder = new TimeTypeDialog.Builder(this, R.style.OrderDialogStyle);
            builder.setTitle("选择时间");
            builder.setReasons(Tools.getTimeType());
            builder.setSelect(tvTimeType.getText().toString());
            builder.setButtonClick(new TimeTypeDialog.OnButtonClick() {

                @Override
                public void onOk(String text) {
                    tvTimeType.setText(text);
                }

            });
            builder.creater().show();
        });

        tvFood.setOnClickListener(v -> {
            SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
            builder.setTitle("敏感操作");
            builder.setReasons(sensitiveTypes);
            builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

                @Override
                public void onOk(String text, int position) {
                    tvFood.setText(text);
                }

                @Override
                public void onCancel() {

                }
            });
            builder.creater().show();
        });


        etStartTime.setOnClickListener(v -> {
            String typestr = tvTimeType.getText().toString();

            DatePickDialog dialog = new DatePickDialog(this);
            dialog.setYearLimt(10);
            dialog.setTitle("选择时间");
            dialog.setType(typestr.equals("营业时间") ? DateType.TYPE_YMD : DateType.TYPE_ALL);
            dialog.setMessageFormat("yyyy-MM-dd HH:mm:ss");
            dialog.setOnChangeLisener(null);
            dialog.setOnSureLisener(date -> {
                startDate = date;
                etStartTime.setText(new SimpleDateFormat(typestr.equals("营业时间") ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss").format(date));
            });
            dialog.show();
        });

        etEndTime.setOnClickListener(v -> {
            String typestr = tvTimeType.getText().toString();
            DatePickDialog dialog = new DatePickDialog(this);
            dialog.setYearLimt(10);
            dialog.setTitle("选择时间");
            dialog.setType(typestr.equals("营业时间") ? DateType.TYPE_YMD : DateType.TYPE_ALL);
            dialog.setMessageFormat("yyyy-MM-dd HH:mm:ss");
            dialog.setOnChangeLisener(null);
            dialog.setOnSureLisener(date -> {
                entDate = date;

                etEndTime.setText(new SimpleDateFormat(typestr.equals("营业时间") ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss").format(date));
            });
            dialog.show();
        });

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            startDate = new Date(System.currentTimeMillis() - 60 * 60 * 24 * 30 * 1000L);
            entDate = new Date(System.currentTimeMillis());
            popupWindow.dismiss();
        });

        View.OnClickListener onClickListener = v -> {

            String typestr = tvTimeType.getText().toString();
            String sensitiveStr = tvFood.getText().toString();
            if (startDate == null) {
                showToast("请选择开始时间");
                return;
            }
            if (entDate == null) {
                showToast("请选择结束时间");
                return;
            }

            if (Tools.comparaDate(startDate, entDate)) {
                showToast("筛选时间出错");
                return;
            }

            if (Tools.checkDate(startDate, entDate)) {
                showToast("筛选时间不能大于一个月");
                return;
            }


            page = 1;
            presenter.getData(page, Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", entDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", entDate),
                    typestr.equals("营业时间") ? 0 : 1, sensitiveStr.equals("撤销") ? 0 : 1);

            popupWindow.dismiss();
        };
        laheiView.findViewById(R.id.pop_ok).setOnClickListener(onClickListener);

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

    FoodEntity currentFood;

    @Override
    public void error(String msg) {
        showFailToast(msg);
        refreshLayout.setRefreshing(false);
        adapter.loadMoreEnd();
    }


    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }

    List<SensitiveOpearation> datas = new ArrayList<>();

    @Override
    public void success(List<SensitiveOpearation> list) {
        if (page == 1) {
            datas.clear();
        }
        datas.addAll(list);
        adapter.setNewData(datas);
        refreshLayout.setRefreshing(false);
        if (list.size() < 20) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
    }
}
