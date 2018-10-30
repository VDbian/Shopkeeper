package com.admin.shopkeeper.ui.activity.activityOfBoss.weightEdit;


import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.SaleBean;
import com.admin.shopkeeper.entity.WeightBean;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class WeightEditActivity extends BaseActivity<WeightEditPresenter> implements IWeightEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_price)
    EditText etPrice;
    @BindView(R.id.edit_weight)
    EditText etWeight;
    @BindView(R.id.edit_deviation)
    EditText etDeviation;
    @BindView(R.id.edit_isopen)
    RadioGroup rgIsOpen;
    @BindView(R.id.edit_state)
    RadioGroup rgState;

    private WeightBean bean;

    @Override
    protected void initPresenter() {
        presenter = new WeightEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weight_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (WeightBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改称斤菜品");

            etName.setText(bean.getName());
            etPrice.setText(bean.getPrice());
            etWeight.setText(bean.getWeight());
            etDeviation.setText(bean.getDeviation());

            if (bean.getIsopen().equals("1")) {
                ((RadioButton) rgIsOpen.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgIsOpen.getChildAt(1)).setChecked(true);
            }

            if (bean.getState().equals("1")) {
                ((RadioButton) rgState.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgState.getChildAt(1)).setChecked(true);
            }
        } else {
            toolbar.setTitle("添加称斤菜品");
            ((RadioButton) rgIsOpen.getChildAt(0)).setChecked(true);
            ((RadioButton) rgState.getChildAt(0)).setChecked(true);
        }
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
        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入名称");
            return;
        }

        String priceStr = etPrice.getText().toString().trim();
        if (TextUtils.isEmpty(priceStr)) {
            showToast("请输入单价");
            return;
        }

        String weightStr = etWeight.getText().toString().trim();
        if (TextUtils.isEmpty(weightStr)) {
            showToast("请输入重量");
            return;
        }

        String devStr = etDeviation.getText().toString().trim();
        if (TextUtils.isEmpty(devStr)) {
            showToast("请输入偏差率");
            return;
        }

        int isOpen = ((RadioButton) rgIsOpen.getChildAt(0)).isChecked() ? 1 : 2;
        int state = ((RadioButton) rgState.getChildAt(0)).isChecked() ? 1 : 2;

        presenter.commit(bean == null ? "" : bean.getGuId(), nameStr, priceStr, weightStr, isOpen, devStr, state);
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
