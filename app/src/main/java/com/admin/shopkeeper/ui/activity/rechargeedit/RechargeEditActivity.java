package com.admin.shopkeeper.ui.activity.rechargeedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.WeightBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weightEdit.IWeightEditView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weightEdit.WeightEditPresenter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class RechargeEditActivity extends BaseActivity<RechargeEditPresenter> implements IRechargeEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_phone)
    EditText etPhone;

    @Override
    protected void initPresenter() {
        presenter = new RechargeEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        toolbar.setTitle("新增会员");
        setSupportActionBar(toolbar);
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
                submit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        String nameStr = etName.getText().toString().trim();
        String phoneStr = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入会员姓名");
            return;
        }

        if (TextUtils.isEmpty(phoneStr)) {
            showToast("请输入电话号码");
            return;
        }

        presenter.commit(phoneStr, nameStr);
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
