package com.admin.shopkeeper.ui.activity.info.yuding;

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class YuDingActivity extends BaseActivity<YuDingPresenter> implements IYuDingView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.yuding_username)
    AppCompatEditText username;

    @BindView(R.id.yuding_phone)
    AppCompatEditText phone;

    @BindView(R.id.yuding_money)
    AppCompatEditText money;

    @BindView(R.id.yuding_remark)
    AppCompatEditText remark;

    private String foodInfo;
    private double totalPrice;

    @OnClick(R.id.yuding_button)
    void onClick() {
        if (TextUtils.isEmpty(username.getText().toString())) {
            warning("请输入预定人姓名");
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            warning("请输入电话");
            return;
        }
        if (TextUtils.isEmpty(money.getText().toString())) {
            warning("请输入预定金额");
            return;
        }

        presenter.KuaiSu(foodInfo, "", "", username.getText().toString().trim(), "", phone.getText().toString().trim(), remark.getText().toString().isEmpty()?"":remark.getText().toString().trim(), Double.valueOf(money.getText().toString().trim()), "", "", "1",totalPrice);
    }


    @Override
    protected void initPresenter() {
        presenter = new YuDingPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yu_ding;
    }

    @Override
    public void initView() {

        toolbar.setTitle("预定");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        foodInfo = getIntent().getStringExtra(Config.PARAM1);
        totalPrice = getIntent().getDoubleExtra(Config.totalPrice,0);
//        totalPrice = Double.parseDouble(getIntent().getStringExtra(Config.totalPrice));
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
    public void kuaisuSuccess(String result) {
        Toasty.success(this, "预定成功", Toast.LENGTH_SHORT, true).show();
        MsgEvent msgEvent = new MsgEvent(MsgEvent.yudingSuccess);
        EventBus.getDefault().post(msgEvent);
        finish();
    }

    @Override
    public void error(String string) {
        Toasty.error(this, string, Toast.LENGTH_SHORT, true).show();
    }
}
