package com.admin.shopkeeper.ui.activity.activityOfBoss.collectionStatistics;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CollectionSelectDialog;
import com.admin.shopkeeper.dialog.TimeTypeDialog;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.collectiondetail.CollectionDetailActivity;
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

public class CollectionStatisticsActivity extends BaseActivity<CollectionStatisticsPresenter> implements ICollectionStatisticsView {

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
    private int type;
    private String shopId;

    List<ChainBean> chainBeens = new ArrayList<>();
    private CollectionStatisticsAdapter adapter;
    private ShopCollectionBean totleBean;

    @Override
    protected void initPresenter() {
        presenter = new CollectionStatisticsPresenter(this, this);
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

        type = getIntent().getIntExtra("type", 1);

        toolbar.setTitle(type == 1 ? "单店收款统计明细" : "连锁收款统计明细");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        adapter = new CollectionStatisticsAdapter();
        recyclerView.setPanelAdapter(adapter);

        adapter.setOnItemClickListener(new CollectionStatisticsAdapter.OnItemClickLishener() {
            @Override
            public void onItemClick(int raw) {
                if (raw == datas.size() - 1) {
                    return;
                }

                if (type == 1) {
                    Intent intent = new Intent(CollectionStatisticsActivity.this, CollectionDetailActivity.class);
                    ShopCollectionBean item = datas.get(raw);
                    item.setShopId(App.INSTANCE().getShopID());
                    intent.putExtra("bean", item);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CollectionStatisticsActivity.this, CollectionDetailActivity.class);
                    ShopCollectionBean item = datas.get(raw);
                    item.setStartTime(Tools.formatNowDate("yyyy-MM-dd", startDate));
                    item.setEndTime(Tools.formatNowDate("yyyy-MM-dd", entDate));
                    intent.putExtra("bean", item);
                    startActivity(intent);
                }
            }

            @Override
            public void onSort(int col, int status) {
                if (datas == null || datas.size() == 0) {
                    return;
                }
                datas.remove(totleBean);

                if (status % 3 == 1) {
                    List<ShopCollectionBean> newData = new ArrayList<>();
                    newData.addAll(datas);
                    Collections.sort(newData, (o1, o2) -> {
                        switch (col) {
                            case 3:
                                return o1.getTotalMoney() > o2.getTotalMoney() ? 1 : -1;
                            case 4:
                                return o1.getChongzhi() > o2.getChongzhi() ? 1 : -1;
                            case 5:
                                return o1.getFreeMoney() > o2.getFreeMoney() ? 1 : -1;
                            default:
                                return o1.getChargeMoney() > o2.getChargeMoney() ? 1 : -1;
                        }
                    });
                    newData.add(totleBean);
                    adapter.setDatas(newData);
                } else if (status % 3 == 2) {

                    List<ShopCollectionBean> newData = new ArrayList<>();
                    newData.addAll(datas);
                    Collections.sort(newData, (o1, o2) -> {
                        switch (col) {
                            case 3:
                                return o1.getTotalMoney() > o2.getTotalMoney() ? -1 : 1;
                            case 4:
                                return o1.getChongzhi() > o2.getChongzhi() ? -1 : 1;
                            case 5:
                                return o1.getFreeMoney() > o2.getFreeMoney() ? -1 : 1;
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

        });

        shopId = App.INSTANCE().getShopID();
        chainBeens = App.INSTANCE().getChainBeans();

        dayClick();
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

        presenter.getData(type, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate), 0, shopId);
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

        presenter.getData(type, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate), 0, shopId);
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

        presenter.getData(type, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate), 0, shopId);
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
            builder.setSingleSelect(type == 1 ? true :false);
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
            popupWindow.dismiss();
        });

        laheiView.findViewById(R.id.pop_ok).setOnClickListener(v -> {

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
            if (TextUtils.isEmpty(shopId)) {
                showToast("请选择门店");
                return;
            }

            presenter.getData(type, Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", entDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", entDate), typestr.equals("营业时间") ? 0 : 1, shopId);


            tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "\n~" + Tools.formatNowDate("yyyy-MM-dd", entDate));
            popupWindow.dismiss();

            tvMonth.setTextColor(Color.parseColor("#666666"));
            tvMonth.setBackgroundResource(R.drawable.bg_ract_white3);
            tvWeek.setTextColor(Color.parseColor("#666666"));
            tvWeek.setBackgroundResource(R.drawable.bg_ract_white3);
            tvDay.setTextColor(Color.parseColor("#666666"));
            tvDay.setBackgroundResource(R.drawable.bg_ract_white3);
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

    public void setDate(TextView textView) {
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setYearLimt(10);
        dialog.setTitle("选择时间");
        dialog.setType(DateType.TYPE_YMD);
        dialog.setMessageFormat("yyyy-MM-dd HH:mm:ss");
        dialog.setOnChangeLisener(null);
        dialog.setOnSureLisener(date -> {
            textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
        });
        dialog.show();
    }

    @Override
    public void error(String msg) {
        showToast(msg);
    }

    List<ShopCollectionBean> datas;

    @Override
    public void success(List<ShopCollectionBean> data) {
        datas = new ArrayList<>(data);

        totleBean = new ShopCollectionBean();
        totleBean.setNames("合计信息");
        totleBean.setDinnerDate(tvDate.getText().toString());

        double sale = 0;
        double charge = 0;
        double free = 0;
        double real = 0;
        for (ShopCollectionBean bean : data) {
            sale += bean.getTotalMoney();
            charge += bean.getChongzhi();
            free += bean.getFreeMoney();
            real += bean.getChargeMoney();
        }
        totleBean.setChargeMoney(new BigDecimal(real).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        totleBean.setFreeMoney(new BigDecimal(free).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        totleBean.setTotalMoney(new BigDecimal(sale).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        totleBean.setChongzhi(new BigDecimal(charge).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        datas.add(totleBean);
        adapter.setDatas(datas);
        recyclerView.notifyDataSetChanged();
    }

}
