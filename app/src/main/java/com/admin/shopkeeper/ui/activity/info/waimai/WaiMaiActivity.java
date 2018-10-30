package com.admin.shopkeeper.ui.activity.info.waimai;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;


import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.activity.bill.BillActivity;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

public class WaiMaiActivity extends BaseActivity<WaiMaiPresenter> implements IWaiMaiView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.waimai_username)
    AppCompatEditText username;

    @BindView(R.id.waimai_phone)
    AppCompatEditText phone;

    @BindView(R.id.waimai_address)
    AppCompatEditText address;

    @BindView(R.id.waimai_date)
    AppCompatTextView date;

    @BindView(R.id.waimai_time)
    AppCompatTextView time;

    @BindView(R.id.waimai_remark)
    AppCompatEditText remark;


    private Calendar calendar;
    private double money;
    private String foodInfo;

    @OnClick({R.id.waimai_button, R.id.waimai_date, R.id.waimai_time})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.waimai_button:
                if (TextUtils.isEmpty(username.getText())) {
                    warning("请输入收货人姓名");
                    return;
                }
                if (TextUtils.isEmpty(phone.getText())) {
                    warning("请输入收货人电话");
                    return;
                }
                if (TextUtils.isEmpty(address.getText())) {
                    warning("请输入收货人地址");
                    return;
                }
                if (TextUtils.isEmpty(date.getText())) {
                    warning("请选择配送日期");
                    return;
                }
                if (TextUtils.isEmpty(time.getText())) {
                    warning("请选择配送时间");
                    return;
                }

                presenter.KuaiSu(foodInfo, date.getText().toString().trim(), time.getText().toString().trim(),
                        username.getText().toString().trim(),
                        address.getText().toString().trim(), phone.getText().toString().trim(),
                        remark.getText().toString().trim(), money, "", "", "3");
                break;
            case R.id.waimai_date:

                DatePickerDialog datePickerDialog = new DatePickerDialog(WaiMaiActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(year + "-" + (month + 1) + "-" + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.waimai_time:


                TimePickerDialog timePickerDialog = new TimePickerDialog(WaiMaiActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Timber.d(hourOfDay + "");
                        time.setText((hourOfDay > 10 ? hourOfDay + "" : ("0" + hourOfDay)) + ":" + (minute > 10 ? minute + "" : ("0" + minute)));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                break;
        }
    }


    @Override
    protected void initPresenter() {

        presenter = new WaiMaiPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wai_mai;
    }

    @Override
    public void initView() {

        EventBus.getDefault().register(this);
        toolbar.setTitle("外卖");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendar = Calendar.getInstance();

        foodInfo = getIntent().getStringExtra(Config.PARAM1);
        money = getIntent().getDoubleExtra(Config.PARAM2, 0);

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
    public void warning(String s) {
        Toasty.warning(this, s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void error(String string) {
        Toasty.error(this, string, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void kuaisuSuccess(String result) {
        Intent intent = new Intent(WaiMaiActivity.this, BillActivity.class);
        intent.putExtra(Config.PARAM2, money);//总价
        intent.putExtra(Config.PARAM4, result);
        intent.putExtra(Config.PARAM5, BillActivity.P1);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        if (event.getType() == MsgEvent.waimaiSuccess) {

            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.doDestroy();
    }
}
