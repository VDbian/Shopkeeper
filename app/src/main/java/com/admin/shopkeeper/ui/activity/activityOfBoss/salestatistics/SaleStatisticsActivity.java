package com.admin.shopkeeper.ui.activity.activityOfBoss.salestatistics;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CollectionSelectDialog;
import com.admin.shopkeeper.dialog.FoodSelectDialog;
import com.admin.shopkeeper.dialog.TimeTypeDialog;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.SaleStatisticsProductBean;
import com.admin.shopkeeper.utils.Tools;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;
import com.gyf.barlibrary.ImmersionBar;
import com.kelin.scrollablepanel.library.ScrollablePanel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SaleStatisticsActivity extends BaseActivity<SaleStatisticsPresenter> implements ISaleStatisticsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_today)
    TextView tvDay;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.recyclerView)
    ScrollablePanel recyclerView;

    private PopupWindow popupWindow;

    int page = 1;
    private SaleStatisticsProductBean bean;

    List<ChainBean> chainBeens = new ArrayList<>();
    String shopId;
    private SaleStatisticsAdapter adapter;
    private SaleStatisticsBean totleBean;

    @Override
    protected void initPresenter() {
        presenter = new SaleStatisticsPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collectionstatistics;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        bean = (SaleStatisticsProductBean) getIntent().getSerializableExtra(Config.PARAM1);
        toolbar.setTitle(bean.getProductName());
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        shopId = App.INSTANCE().getShopID();
        chainBeens = App.INSTANCE().getChainBeans();

        adapter = new SaleStatisticsAdapter();
        recyclerView.setPanelAdapter(adapter);
        adapter.setOnItemClickListener(new SaleStatisticsAdapter.OnItemClickLishener() {
            @Override
            public void onItemClick(int raw) {

            }

            @Override
            public void onSort(int col, int status) {
                sort(col, status);
            }
        });


        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setProductName("全部");
        foodEntity.setProductID("");
        foodEntities.add(foodEntity);

        currentFood = foodEntity;

        presenter.getGoods();
        dayClick();
    }


    private void sort(int col, int status) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        datas.remove(totleBean);

        if (status % 3 == 1) {
            List<SaleStatisticsBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                switch (col) {
                    case 3:
                        return o1.getCounts() > o2.getCounts() ? 1 : -1;
                    case 4:
                        return o1.getTotalPrice() > o2.getTotalPrice() ? 1 : -1;
                    case 5:
                        return o1.getFreePrice() > o2.getFreePrice() ? 1 : -1;
                    default:
                        return o1.getChargeMoney() > o2.getChargeMoney() ? 1 : -1;
                }
            });
            newData.add(totleBean);
            adapter.setDatas(newData);
        } else if (status % 3 == 2) {

            List<SaleStatisticsBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                switch (col) {
                    case 3:
                        return o1.getCounts() > o2.getCounts() ? -1 : 1;
                    case 4:
                        return o1.getTotalPrice() > o2.getTotalPrice() ? -1 : 1;
                    case 5:
                        return o1.getFreePrice() > o2.getFreePrice() ? -1 : 1;
                    default:
                        return o1.getChargeMoney() > o2.getChargeMoney() ? -1 : 1;
                }
            });
            newData.add(totleBean);
            adapter.setDatas(newData);
        } else {
            datas.add(totleBean);
            adapter.setDatas(datas);
        }
        recyclerView.notifyDataSetChanged();
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

    @OnClick(R.id.tv_today)
    public void dayClick() {
        tvDay.setTextColor(Color.WHITE);
        tvDay.setBackgroundResource(R.drawable.bg_ract_green);
        tvWeek.setTextColor(Color.parseColor("#666666"));
        tvWeek.setBackgroundResource(R.drawable.bg_ract_white3);
        tvMonth.setTextColor(Color.parseColor("#666666"));
        tvMonth.setBackgroundResource(R.drawable.bg_ract_white3);

        startDate = new Date(System.currentTimeMillis());
        entDate = new Date(System.currentTimeMillis());
        tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "\n~" + Tools.formatNowDate("yyyy-MM-dd", entDate));

        presenter.getData(Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate), 0, currentFood.getProductID(), bean.getProductId(), shopId);
    }

    @OnClick(R.id.tv_week)
    public void weekClick() {
        tvWeek.setTextColor(Color.WHITE);
        tvWeek.setBackgroundResource(R.drawable.bg_ract_green);
        tvDay.setTextColor(Color.parseColor("#666666"));
        tvDay.setBackgroundResource(R.drawable.bg_ract_white3);
        tvMonth.setTextColor(Color.parseColor("#666666"));
        tvMonth.setBackgroundResource(R.drawable.bg_ract_white3);

        startDate = Tools.getBeginDayOfWeek();
        entDate = Tools.getEndDayOfWeek();
        tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "\n~" + Tools.formatNowDate("yyyy-MM-dd", entDate));

        presenter.getData(Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate), 0, currentFood.getProductID(), bean.getProductId(), shopId);
    }

    @OnClick(R.id.tv_month)
    public void monthClick() {
        tvMonth.setTextColor(Color.WHITE);
        tvMonth.setBackgroundResource(R.drawable.bg_ract_green);
        tvWeek.setTextColor(Color.parseColor("#666666"));
        tvWeek.setBackgroundResource(R.drawable.bg_ract_white3);
        tvDay.setTextColor(Color.parseColor("#666666"));
        tvDay.setBackgroundResource(R.drawable.bg_ract_white3);

        startDate = Tools.getBeginDayOfMonth();
        entDate = Tools.getEndDayOfMonth();
        tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "\n~" + Tools.formatNowDate("yyyy-MM-dd", entDate));

        presenter.getData(Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate), 0, currentFood.getProductID(), bean.getProductId(), shopId);
    }

    Date startDate;
    Date entDate;

    private void showSearch() {
        List<String> types = new ArrayList<>();
        types.add("营业时间");
        types.add("自定义时间");

        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_select_1, null);
        popupWindow = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        TextView etStartTime = (TextView) laheiView.findViewById(R.id.pop_starttime);
        TextView etEndTime = (TextView) laheiView.findViewById(R.id.pop_endtime);
        TextView tvTimeType = (TextView) laheiView.findViewById(R.id.pop_time_typw);
        TextView tvShop = (TextView) laheiView.findViewById(R.id.tv_shop);
        LinearLayout llFood = (LinearLayout) laheiView.findViewById(R.id.ll_food);
        TextView tvFood = (TextView) laheiView.findViewById(R.id.tv_food);

        llFood.setVisibility(View.VISIBLE);

        tvFood.setText(currentFood.getProductName());

        for (ChainBean chainBean : chainBeens) {
            if (shopId.toLowerCase().equals(chainBean.getMerchantId())) {
                tvShop.setText(chainBean.getNames());
            }
        }

        tvShop.setOnClickListener(v -> {
            if (chainBeens.size() == 0) {
                showToast("获取门店失败");
                return;
            }

            String selectText = tvShop.getText().toString().trim();

            CollectionSelectDialog.Builder builder = new CollectionSelectDialog.Builder(this, R.style.OrderDialogStyle);
            builder.setTitle("选择门店");
            builder.setReasons(chainBeens);
            builder.setSelect(selectText);
            builder.setSingleSelect(true);
            builder.setButtonClick((text, value) -> {
                tvShop.setText(text);
                shopId = value;
            });
            builder.creater().show();
        });


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
            if (foodEntities == null) {
                return;
            }
            FoodSelectDialog.Builder builder = new FoodSelectDialog.Builder(this, R.style.OrderDialogStyle);
            builder.setTitle("选择商品");
            builder.setFoods(foodEntities);
            builder.setButtonClick(new FoodSelectDialog.OnButtonClick() {

                @Override
                public void onOk(FoodEntity foodEntity, int position) {
                    tvFood.setText(foodEntity.getProductName());
                    currentFood = foodEntity;
                }

                @Override
                public void onCancel() {
                    tvFood.setText("全部");
                    currentFood = foodEntities.get(0);
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
            currentFood = foodEntities.get(0);
            popupWindow.dismiss();
        });

        View.OnClickListener onClickListener = v -> {

            String typestr = tvTimeType.getText().toString();

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
            presenter.getData(Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", entDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", entDate),
                    typestr.equals("营业时间") ? 0 : 1, currentFood.getProductID(), bean.getProductId(), shopId);

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
    }

    List<FoodEntity> foodEntities = new ArrayList<>();

    @Override
    public void foodSuccess(List<FoodEntity> foodEntities) {
        this.foodEntities.addAll(foodEntities);
    }

    List<SaleStatisticsBean> datas = new ArrayList<>();

    @Override
    public void success(List<SaleStatisticsBean> list) {
        datas = new ArrayList<>(list);

        totleBean = new SaleStatisticsBean();

        int count = 0;
        double sale = 0, free = 0, charge = 0;
        for (SaleStatisticsBean bean : list) {
            count += bean.getCounts();
            sale += bean.getTotalPrice();
            free += bean.getFreePrice();
            charge += bean.getChargeMoney();
        }

        totleBean.setProductName("合计信息");
        totleBean.setProductTypeName("");
        totleBean.setId("");
        totleBean.setCounts(count);
        totleBean.setTotalPrice(new BigDecimal(sale).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        totleBean.setFreePrice(new BigDecimal(free).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        totleBean.setChargeMoney(new BigDecimal(charge).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        datas.add(totleBean);
        adapter.setDatas(datas);
        recyclerView.notifyDataSetChanged();
    }

    @Override
    public void totle(List<SaleStatisticsBean> saleStatisticsBeen) {
        if (saleStatisticsBeen == null || saleStatisticsBeen.size() == 0) {
            return;
        }
        SaleStatisticsBean item = saleStatisticsBeen.get(0);

    }
}
