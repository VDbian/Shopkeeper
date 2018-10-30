package com.admin.shopkeeper.ui.activity.activityOfBoss.couponDetailTable;

import android.content.Intent;
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
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CollectionSelectDialog;
import com.admin.shopkeeper.dialog.TimeTypeDialog;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.CouponDetailTableBean;
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

public class CouponDetailTableActivity extends BaseActivity<CouponDetailTablePresenter> implements ICouponDetailTableView {


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
    private PopupWindow laheiPop;
    private PopupWindow popupWindow;
    List<CouponDetailTableBean> data;
    private String titleStr;
    NewCouponDetailAdapter adapter;
    int pageIndex = 1;


    List<ChainBean> chainBeens = new ArrayList<>();
    String shopId;


    @Override
    protected void initPresenter() {
        presenter = new CouponDetailTablePresenter(this, this);
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
        toolbar.setTitle("微信券明细表");

        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        adapter = new NewCouponDetailAdapter();
        recyclerView.setPanelAdapter(adapter);

        adapter.setOnItemClickListener(new NewCouponDetailAdapter.OnItemClickLishener() {
            @Override
            public void onItemClick(int raw) {

            }

            @Override
            public void onSort(int col, int status) {
                if (data == null || data.size() == 0) {
                    return;
                }
                data.remove(totleBean);

                if (status % 3 == 1) {
                    List<CouponDetailTableBean> newData = new ArrayList<>();
                    newData.addAll(data);
                    Collections.sort(newData, (o1, o2) -> {
                        switch (col) {
                            case 2:
                                return Integer.parseInt(o1.getCouponPerson()) > Integer.parseInt(o2.getCouponPerson()) ? 1 : -1;
                            case 3:
                                return Integer.parseInt(o1.getGiveConponVolume()) > Integer.parseInt(o2.getGiveConponVolume()) ? 1 : -1;
                            case 4:
                                return Integer.parseInt(o1.getUseCoupon()) > Integer.parseInt(o2.getUseCoupon()) ? 1 : -1;
                            case 5:
                                return Double.parseDouble(o1.getDiscount()) > Double.parseDouble(o2.getDiscount()) ? 1 : -1;
                            case 6:
                                return Double.parseDouble(o1.getFreePriceByCoupon()) > Double.parseDouble(o2.getFreePriceByCoupon()) ? 1 : -1;
                            case 7:
                                return Integer.parseInt(o1.getUseCoupon()) > Integer.parseInt(o2.getUseCoupon()) ? 1 : -1;
                            default:
                                return Double.parseDouble(o1.getConsumeByCoupon()) > Double.parseDouble(o2.getConsumeByCoupon()) ? 1 : -1;

                        }
                    });
                    newData.add(totleBean);
                    adapter.setDatas(newData);
                } else if (status % 3 == 2) {

                    List<CouponDetailTableBean> newData = new ArrayList<>();
                    newData.addAll(data);
                    Collections.sort(newData, (o1, o2) -> {
                        switch (col) {
                            case 2:
                                return Integer.parseInt(o1.getCouponPerson()) < Integer.parseInt(o2.getCouponPerson()) ? 1 : -1;
                            case 3:
                                return Integer.parseInt(o1.getGiveConponVolume()) < Integer.parseInt(o2.getGiveConponVolume()) ? 1 : -1;
                            case 4:
                                return Integer.parseInt(o1.getUseCoupon()) < Integer.parseInt(o2.getUseCoupon()) ? 1 : -1;
                            case 5:
                                return Double.parseDouble(o1.getDiscount()) < Double.parseDouble(o2.getDiscount()) ? 1 : -1;
                            case 6:
                                return Double.parseDouble(o1.getFreePriceByCoupon()) < Double.parseDouble(o2.getFreePriceByCoupon()) ? 1 : -1;
                            case 7:
                                return Integer.parseInt(o1.getUseCoupon()) < Integer.parseInt(o2.getUseCoupon()) ? 1 : -1;
                            default:
                                return Double.parseDouble(o1.getConsumeByCoupon()) < Double.parseDouble(o2.getConsumeByCoupon()) ? 1 : -1;

                        }
                    });
                    newData.add(totleBean);
                    adapter.setDatas(newData);
                } else {
                    data.add(totleBean);
                    adapter.setDatas(data);
                }
                recyclerView.notifyDataSetChanged();

            }
        });

        shopId = App.INSTANCE().getShopID();
        chainBeens = App.INSTANCE().getChainBeans();

        dayClick();
//        presenter.getData(pageIndex, startDate, endDate, "00:00:00", "23:59:59", 0,shopId);


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
        endDate = new Date(System.currentTimeMillis());
        tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "\n~" + Tools.formatNowDate("yyyy-MM-dd", endDate));

        presenter.getData(pageIndex ,Tools.formatNowDate("yyyy-MM-dd", startDate), Tools.formatNowDate("yyyy-MM-dd", endDate),"00:00:00", "23:59:59", 0,shopId);
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
        endDate = Tools.getEndDayOfWeek();
        tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "\n~" + Tools.formatNowDate("yyyy-MM-dd", endDate));

        presenter.getData(pageIndex ,Tools.formatNowDate("yyyy-MM-dd", startDate), Tools.formatNowDate("yyyy-MM-dd", endDate),"00:00:00", "23:59:59", 0,shopId);

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
        endDate = Tools.getEndDayOfMonth();
        tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "\n~" + Tools.formatNowDate("yyyy-MM-dd", endDate));

        presenter.getData(pageIndex ,Tools.formatNowDate("yyyy-MM-dd", startDate), Tools.formatNowDate("yyyy-MM-dd", endDate),"00:00:00", "23:59:59", 0,shopId);

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
    Date endDate;

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
            if(chainBeens.size() == 0){
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
                endDate = date;
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
            if (endDate == null) {
                showToast("请选择结束时间");
                return;
            }

            if (Tools.comparaDate(startDate, endDate)) {
                showToast("筛选时间出错");
                return;
            }

            if (Tools.checkDate(startDate, endDate)) {
                showToast("筛选时间不能大于一个月");
                return;
            }
            presenter.getData(pageIndex, Tools.formatNowDate("yyyy-MM-dd", startDate),
                    Tools.formatNowDate("yyyy-MM-dd", endDate),
                    Tools.formatNowDate("HH:mm:ss", startDate),
                    Tools.formatNowDate("HH:mm:ss", endDate), typestr.equals("营业时间") ? 0 : 1,shopId);
//            tvDate.setText(Tools.formatNowDate("yyyy-MM-dd", startDate) + "至" + Tools.formatNowDate("yyyy-MM-dd", endDate));
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


    int defaultType = 0;



    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }

    private CouponDetailTableBean totleBean;
    @Override
    public void success(List<CouponDetailTableBean> couponDetailTableBeenList) {
        data = new ArrayList<>(couponDetailTableBeenList);

        totleBean = new CouponDetailTableBean();
        totleBean.setShopName("合计信息");

        int couponPers = 0;//券获取人数
        int volume = 0;//券发放量
        int use = 0;//券使用量
        double diacount = 0;//券优惠金额
        double free = 0;//累积抵用金额
        double consume = 0;//带动消费
        for (CouponDetailTableBean bean : data) {
            couponPers += Integer.parseInt(bean.getCouponPerson());
            volume += Integer.parseInt(bean.getGiveConponVolume());
            use += Integer.parseInt(bean.getUseCoupon());
            diacount += Double.parseDouble(bean.getDiscount());
            free += Double.parseDouble(bean.getFreePriceByCoupon());
            consume += Double.parseDouble(bean.getConsumeByCoupon());
        }
        totleBean.setCouponPerson(new BigDecimal(couponPers).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setGiveConponVolume(new BigDecimal(volume).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setUseCoupon(new BigDecimal(use).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setDiscount(new BigDecimal(diacount).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setFreePriceByCoupon(new BigDecimal(free).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setConsumeByCoupon(new BigDecimal(consume).setScale(0, BigDecimal.ROUND_HALF_UP).toString());

        data.add(totleBean);
        adapter.setDatas(data);
        recyclerView.notifyDataSetChanged();
    }

}
