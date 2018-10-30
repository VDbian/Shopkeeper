package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTranscation;

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
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CollectionSelectDialog;
import com.admin.shopkeeper.dialog.TimeTypeDialog;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTransactionItemDetail.RechargeTranscationItemDetailActivity;
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

public class RechargeTranscationActivity extends BaseActivity<RechargeTranscationPresenter> implements IRechargeTranscationView {


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
    List<MemberTranscationBean> data;
    private PopupWindow popupWindow;
    private String titleStr;
    RechargeTranscationAdapter adapter;
    int pageIndex = 1;

    List<ChainBean> chainBeens = new ArrayList<>();
    String shopId;

    @Override
    protected void initPresenter() {
        presenter = new RechargeTranscationPresenter(this, this);
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
        toolbar.setTitle("充值交易表");

        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        adapter = new RechargeTranscationAdapter();
        recyclerView.setPanelAdapter(adapter);

        adapter.setOnItemClickListener(new RechargeTranscationAdapter.OnItemClickLishener() {
            @Override
            public void onItemClick(int raw) {
                Intent intent = new Intent(RechargeTranscationActivity.this, RechargeTranscationItemDetailActivity.class);
                intent.putExtra(Config.PARAM1, Tools.formatNowDate("yyyy-MM-dd", startDate));
                intent.putExtra(Config.PARAM2, Tools.formatNowDate("yyyy-MM-dd", endDate));
                intent.putExtra("bean", data.get(raw));
                startActivity(intent);
            }

            @Override
            public void onSort(int col, int status) {
                if (data == null || data.size() == 0) {
                    return;
                }
                data.remove(totleBean);

                if (status % 3 == 1) {
                    List<MemberTranscationBean> newData = new ArrayList<>();
                    newData.addAll(data);
                    Collections.sort(newData, (o1, o2) -> {
                        switch (col) {
                            case 3:
                                return Double.parseDouble(o1.getRechargeMoney()) > Double.parseDouble(o2.getRechargeMoney()) ? 1 : -1;
                            case 4:
                                return Double.parseDouble(o1.getUsedMoney()) > Double.parseDouble(o2.getUsedMoney()) ? 1 : -1;
                            case 5:
                                return Double.parseDouble(o1.getZonJian()) > Double.parseDouble(o2.getZonJian()) ? 1 : -1;
                            case 6:
                                return Double.parseDouble(o1.getZonAdd()) > Double.parseDouble(o2.getZonAdd()) ? 1 : -1;
                            default:
                                return Double.parseDouble(o1.getYue()) > Double.parseDouble(o2.getYue()) ? 1 : -1;

                        }
                    });
                    newData.add(totleBean);
                    adapter.setDatas(newData);
                } else if (status % 3 == 2) {

                    List<MemberTranscationBean> newData = new ArrayList<>();
                    newData.addAll(data);
                    Collections.sort(newData, (o1, o2) -> {
                        switch (col) {
                            case 3:
                                return Double.parseDouble(o1.getRechargeMoney()) > Double.parseDouble(o2.getRechargeMoney()) ? -1 : 1;
                            case 4:
                                return Double.parseDouble(o1.getUsedMoney()) > Double.parseDouble(o2.getUsedMoney()) ? -1 : 1;
                            case 5:
                                return Double.parseDouble(o1.getZonJian()) > Double.parseDouble(o2.getZonJian()) ? -1 : 1;
                            case 6:
                                return Double.parseDouble(o1.getZonAdd()) > Double.parseDouble(o2.getZonAdd()) ? -1 : 1;
                            default:
                                return Double.parseDouble(o1.getYue()) > Double.parseDouble(o2.getYue()) ? -1 : 1;

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

        presenter.getData(pageIndex, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", endDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", endDate),
                0, shopId);
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

        presenter.getData(pageIndex, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", endDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", endDate),
                0, shopId);
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

        presenter.getData(pageIndex, Tools.formatNowDate("yyyy-MM-dd", startDate),
                Tools.formatNowDate("yyyy-MM-dd", endDate),
                Tools.formatNowDate("HH:mm:ss", startDate),
                Tools.formatNowDate("HH:mm:ss", endDate),
                0, shopId);
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
                    Tools.formatNowDate("HH:mm:ss", endDate), typestr.equals("营业时间") ? 0 : 1, shopId);


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

    private MemberTranscationBean totleBean;

    @Override
    public void success(List<MemberTranscationBean> memberTranscationBeanList) {
        data = new ArrayList<>(memberTranscationBeanList);

        totleBean = new MemberTranscationBean();
        totleBean.setShopName("合计信息");

        double rechargeMoney = 0;//累积充值
        double UsedMoney = 0;//消费金额
        double zonJian = 0;//累积消费
        double zonAdd = 0;//充值金额
        double yue = 0;
        for (MemberTranscationBean bean : data) {
            rechargeMoney += Double.parseDouble(bean.getRechargeMoney());
            UsedMoney += Double.parseDouble(bean.getUsedMoney());
            zonJian += Double.parseDouble(bean.getZonJian());
            zonAdd += Double.parseDouble(bean.getZonAdd());
            yue += Double.parseDouble(bean.getYue());
        }
        totleBean.setRechargeMoney(new BigDecimal(rechargeMoney).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setUsedMoney(new BigDecimal(UsedMoney).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setZonJian(new BigDecimal(zonJian).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setZonAdd(new BigDecimal(zonAdd).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        totleBean.setYue(new BigDecimal(yue).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        data.add(totleBean);
        adapter.setDatas(data);
        recyclerView.notifyDataSetChanged();
    }

}