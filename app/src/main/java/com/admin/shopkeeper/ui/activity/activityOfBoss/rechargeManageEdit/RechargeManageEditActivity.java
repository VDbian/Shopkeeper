package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeManageEdit;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.RechargeManageBean;
import com.gyf.barlibrary.ImmersionBar;


import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class RechargeManageEditActivity extends BaseActivity<RechargeManageEditPresenter> implements IRechargeManageEditView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_recharge_name)
    EditText etName;
    @BindView(R.id.edit_recharge_money)
    EditText etRechargeMoney;
    @BindView(R.id.edit_recharge_give_money)
    EditText etGiveMoney;
    @BindView(R.id.radioGroup_type)
    RadioGroup rgType;
    private RechargeManageBean rechargeManageBean;

    @Override
    protected void initPresenter() {
        presenter = new RechargeManageEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_manage_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        rechargeManageBean = (RechargeManageBean) getIntent().getSerializableExtra(Config.PARAM1);
        if (rechargeManageBean == null) {
            toolbar.setTitle("增加充值方案");
        } else {
            toolbar.setTitle("编辑充值方案");
            updateView();
        }
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);
    }

    private void updateView() {
        etName.setText(rechargeManageBean.getName());
        etRechargeMoney.setText(rechargeManageBean.getRechargeMoney());
        etGiveMoney.setText(rechargeManageBean.getGiveMoney());
        if (rechargeManageBean.getType().equals("1")) {
            ((RadioButton) rgType.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgType.getChildAt(1)).setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                doSubmit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void doSubmit() {
        String nameStr = etName.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            Toasty.warning(this, "请设置充值方案名称").show();
            etName.requestFocus();
            return;
        }

        String rechargeMoneyStr = etRechargeMoney.getText().toString().trim();
        if (TextUtils.isEmpty(rechargeMoneyStr)) {
            showFailToast("请设置充值金额");
            etRechargeMoney.requestFocus();
            return;
        }
        String giveMoneyStr = etGiveMoney.getText().toString().trim();
        if (TextUtils.isEmpty(rechargeMoneyStr)) {
            showFailToast("请设置赠送金额");
            etGiveMoney.requestFocus();
            return;
        }

        int stateType = 0;
        if (((RadioButton) rgType.getChildAt(0)).isChecked()) {
            stateType = 1;
        } else {
            stateType = 2;
        }
        presenter.submit((rechargeManageBean == null) ? "" : rechargeManageBean.getGuid(), nameStr, rechargeMoneyStr,giveMoneyStr , stateType);

    }


    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        setResult(RESULT_OK);
        finish();
    }


}
