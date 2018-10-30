package com.admin.shopkeeper.ui.activity.rechargedetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CheckDialog;
import com.admin.shopkeeper.dialog.ListDialog;
import com.admin.shopkeeper.dialog.MutiSelectDialog;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.MutiBean;
import com.admin.shopkeeper.entity.QueueBean;
import com.admin.shopkeeper.entity.RechargeBean;
import com.admin.shopkeeper.entity.RechargeItemBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeDetailActivity extends BaseActivity<RechargeDetailPresenter> implements IRechargeDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_product)
    LinearLayout llProduct;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_score)
    TextView tvSroce;
    @BindView(R.id.et_money)
    EditText editText;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    private RechargeBean bean;
    private int type;
    private final String[] payStrs = {"现金", "银行卡", "主扫微信", "会员卡", "被扫支付宝", "被扫微信","美团券","主扫支付宝"};

    @Override
    protected void initPresenter() {
        presenter = new RechargeDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        toolbar.setTitle("充值");
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        bean = (RechargeBean) intent.getSerializableExtra("bean");

        if (type == 0) {
            llMoney.setVisibility(View.VISIBLE);
            llProduct.setVisibility(View.GONE);
        } else {
            llMoney.setVisibility(View.GONE);
            llProduct.setVisibility(View.VISIBLE);
            presenter.getProduct();
        }

        presenter.searchMember(bean.getStaffTel());
//        payTypes.add(new MutiBean("现金", false, 1));
//        payTypes.add(new MutiBean("银行卡", false, 2));
//        payTypes.add(new MutiBean("主扫微信", false, 3));
//        payTypes.add(new MutiBean("会员卡", false, 5));
//        payTypes.add(new MutiBean("被扫支付宝", false, 6));
//        payTypes.add(new MutiBean("被扫微信", false, 7));
//        payTypes.add(new MutiBean("美团券", false, 8));
//        payTypes.add(new MutiBean("主扫支付宝", false, 10));
    }

    @OnClick(R.id.button)
    public void sumitClick() {
        String payTypeStr = tvPayType.getText().toString();
        if (type == 0) {
            String moneyStr = editText.getText().toString();
            if (TextUtils.isEmpty(moneyStr)) {
                showToast("请输入充值金额");
                return;
            }else if(TextUtils.isEmpty(payTypeStr)){
                showToast("请选择支付类型");
                return;
            }
            showCheckDialog(type, bean);
        } else {
            if (selectItem == null) {
                showToast("请选择充值产品");
                return;
            }else if(TextUtils.isEmpty(payTypeStr)){
                showToast("请选择支付类型");
                return;
            }
            showCheckDialog(type, bean);
        }
    }

    private void showCheckDialog(int i, RechargeBean bean) {
        CheckDialog.Builder builder = new CheckDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("验证校验码");
        builder.setButtonClick(new CheckDialog.OnButtonClick() {
            @Override
            public void onOk(String checkCode) {
                if (TextUtils.isEmpty(checkCode)) {
                    showToast("请输入校验码");
                    return;
                }

                presenter.check(checkCode, i, bean);
            }

            @Override
            public void onCancel() {

            }
        });
        builder.creater().show();
    }

    @OnClick(R.id.ll_product)
    public void productClick() {

        if (datas == null) {
            showToast("获取产品数据失败");
        }

        List<String> names = new ArrayList<>();
        for (RechargeItemBean bean : datas) {
            names.add(bean.getName());
        }

        SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("选择充值产品");
        builder.setReasons(names);
        builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, int position) {
                tvProduct.setText(text);
                selectItem = datas.get(position);
            }

            @Override
            public void onCancel() {

            }
        });
        builder.creater().show();
    }
    @OnClick(R.id.ll_pay_type)
    public void payTypeClick() {
        showDialog("快速支付", Arrays.asList(payStrs));
    }
    int payType;
    private void showDialog(String title, List<String> list) {
        ListDialog.Builder builder = new ListDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle(title);
        builder.setReasons(list);
        builder.setButtonClick(new ListDialog.OnButtonClick() {
//"现金", "银行卡", "主扫微信", "会员卡", "被扫支付宝", "被扫微信","美团券","主扫支付宝"
            @Override
            public void onItemClick(int i, String str) {
                switch (str) {
                    case "现金":
                        payType = 1;
                        tvPayType.setText(str);
                        break;
                    case "银行卡":
                        payType = 2;
                        tvPayType.setText(str);
                        break;
                    case "主扫微信":
                        payType = 3;
                        tvPayType.setText(str);
                        break;
                    case "会员卡":
                        payType = 5;
                        tvPayType.setText(str);
                        break;
                    case "被扫支付宝":
                        payType = 6;
                        tvPayType.setText(str);
                        break;
                    case "被扫微信":
                        payType = 7;
                        tvPayType.setText(str);
                        break;
                    case "美团券":
                        payType = 8;
                        tvPayType.setText(str);
                        break;
                    case "主扫支付宝":
                        payType = 10;
                        tvPayType.setText(str);
                        break;
                }
            }
        });
        builder.creater().show();
    }
    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        finish();
    }

    MemberBean memberBean;

    @Override
    public void saleSuccess(MemberBean memberBean) {
        this.memberBean = memberBean;
        tvName.setText(memberBean.getName());
        tvCard.setText(memberBean.getNo());
        tvMoney.setText(memberBean.getMoney() + "");
        tvPhone.setText(memberBean.getPhone());
        tvSroce.setText(memberBean.getScore() + "");
    }

    List<RechargeItemBean> datas;

    RechargeItemBean selectItem;

    @Override
    public void productSuccess(List<RechargeItemBean> rechargeItemBeen) {
        this.datas = rechargeItemBeen;
    }

    @Override
    public void checkSuccess(int type, RechargeBean bean) {
        if (type == 0) {
            String moneyStr = editText.getText().toString();
            presenter.moneyCommit(memberBean.getId(), moneyStr , payType);
        } else {
            presenter.productCommit(memberBean.getId(), selectItem.getGuid() , payType);
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
}
