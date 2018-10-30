package com.admin.shopkeeper.ui.activity.activityOfBoss.shopcollection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CollectionAdapter;
import com.admin.shopkeeper.adapter.ReturnBussinessAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CollectionSelectDialog;
import com.admin.shopkeeper.dialog.TimeTypeDialog;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.collectiondetail.CollectionDetailActivity;
import com.admin.shopkeeper.utils.Tools;
import com.admin.shopkeeper.utils.UIUtils;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;
import com.gyf.barlibrary.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCollectionActivity extends BaseActivity<ShopCollectionPresenter> implements IShopCollectionView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sort_1)
    TextView tvSort1;
    @BindView(R.id.sort_2)
    TextView tvSort2;
    @BindView(R.id.sort_3)
    TextView tvSort3;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.tv_sale)
    TextView tvSale;
    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.tv_free)
    TextView tvFree;
    @BindView(R.id.tv_real)
    TextView tvReal;
    @BindView(R.id.tv_date)
    TextView tvDate;

    private PopupWindow popupWindow;
    private CollectionAdapter adapter;
    private int type;
    private String shopId;

    List<ChainBean> chainBeens = new ArrayList<>();

    @Override
    protected void initPresenter() {
        presenter = new ShopCollectionPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_collection;
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


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CollectionAdapter(R.layout.item_collection);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (type == 1) {
                Intent intent = new Intent(ShopCollectionActivity.this, CollectionDetailActivity.class);
                intent.putExtra("bean", adapter.getItem(position));
                startActivity(intent);
            } else {
                Intent intent = new Intent(ShopCollectionActivity.this, CollectionDetailActivity.class);
                ShopCollectionBean item = adapter.getItem(position);
                item.setStartTime(Tools.formatNowDate("yyyy-MM-dd", startDate));
                item.setEndTime(Tools.formatNowDate("yyyy-MM-dd", entDate));
                intent.putExtra("bean", item);
                startActivity(intent);
            }
        });


        shopId = App.INSTANCE().getShopID();
        chainBeens = App.INSTANCE().getChainBeans();

        startDate = new Date(System.currentTimeMillis());
        entDate = new Date(System.currentTimeMillis());

        tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "至" + Tools.formatNowDate("yyyy-MM-dd", entDate));

        presenter.getData(type, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", entDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", entDate), 0, shopId);
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
            if(chainBeens.size() == 0){
                showToast("获取门店失败");
                return;
            }

            String selectText = tvShop.getText().toString().trim();

            CollectionSelectDialog.Builder builder = new CollectionSelectDialog.Builder(this, R.style.OrderDialogStyle);
            builder.setTitle("选择门店");
            builder.setReasons(chainBeens);
            builder.setSelect(selectText);
            builder.setSingleSelect(type == 1);
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


            tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "至" + Tools.formatNowDate("yyyy-MM-dd", entDate));
            popupWindow.dismiss();
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

    @OnClick(R.id.tv_total)
    public void totalClick() {
        if (llTotal.getVisibility() != View.VISIBLE) {
            llTotal.setVisibility(View.VISIBLE);
            UIUtils.setDrawableRight(tvTotal, R.mipmap.list_arrow_up);
        } else {
            llTotal.setVisibility(View.GONE);
            UIUtils.setDrawableRight(tvTotal, R.mipmap.list_arrow_down);
        }
    }

    @OnClick(R.id.sort_1)
    public void sort1Click() {
        UIUtils.setDrawableRight(tvSort1, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_default);
        adapter.setNewData(datas);
    }

    int sort2 = 0;

    @OnClick(R.id.sort_2)
    public void sort2Click() {
        if (datas == null || datas.size() == 0) {
            return;
        }
        sort2++;
        if (sort2 % 3 == 1) {
            UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_a_z);

            List<ShopCollectionBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getTotalMoney() > o2.getTotalMoney() ? 1 : -1;
            });
            adapter.setNewData(newData);
        } else if (sort2 % 3 == 2) {
            UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_z_a);

            List<ShopCollectionBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getTotalMoney() > o2.getTotalMoney() ? -1 : 1;
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_default);
            adapter.setNewData(datas);
        }
        UIUtils.setDrawableRight(tvSort1, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_default);
    }

    int sort3 = 0;

    @OnClick(R.id.sort_3)
    public void sort3Click() {
        if (datas == null || datas.size() == 0) {
            return;
        }
        sort3++;
        if (sort3 % 3 == 1) {
            UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_a_z);

            List<ShopCollectionBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getChargeMoney() > o2.getChargeMoney() ? 1 : -1;
            });
            adapter.setNewData(newData);
        } else if (sort3 % 3 == 2) {
            UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_z_a);

            List<ShopCollectionBean> newData = new ArrayList<>();
            newData.addAll(datas);
            Collections.sort(newData, (o1, o2) -> {
                return o1.getChargeMoney() > o2.getChargeMoney() ? -1 : 1;
            });
            adapter.setNewData(newData);
        } else {
            UIUtils.setDrawableRight(tvSort3, R.mipmap.sort_default);
            adapter.setNewData(datas);
        }
        UIUtils.setDrawableRight(tvSort1, R.mipmap.sort_default);
        UIUtils.setDrawableRight(tvSort2, R.mipmap.sort_default);
    }


    @Override
    public void error(String msg) {
        showToast(msg);
    }

    List<ShopCollectionBean> datas;

    @Override
    public void success(List<ShopCollectionBean> data) {
        this.datas = data;
        adapter.setNewData(data);

        double sale = 0;
        double charge = 0;
        double free = 0;
        double real = 0;
        for (ShopCollectionBean bean : datas) {
            sale += bean.getTotalMoney();
            charge += bean.getChongzhi();
            free += bean.getFreeMoney();
            real += bean.getChargeMoney();

            if (type == 1) {
                bean.setNames(App.INSTANCE().getShopName());
                bean.setShopId(App.INSTANCE().getShopID());
            }
        }

        tvSale.setText(String.valueOf(sale));
        tvCharge.setText(String.valueOf(charge));
        tvFree.setText(String.valueOf(free));
        tvReal.setText(String.valueOf(real));

        llTotal.setVisibility(View.VISIBLE);
    }

}
