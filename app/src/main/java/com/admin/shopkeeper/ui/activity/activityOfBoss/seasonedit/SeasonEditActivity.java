package com.admin.shopkeeper.ui.activity.activityOfBoss.seasonedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.kouweiedit.IKouweiEditView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.kouweiedit.KouweiEditPresenter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class SeasonEditActivity extends BaseActivity<SeasonEditPresenter> implements ISeasonEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_price)
    EditText etPrice;

    private FoodBean foodBean;

    @Override
    protected void initPresenter() {
        presenter = new SeasonEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_season_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        toolbar.setTitle("添加佐料");
        setSupportActionBar(toolbar);

        foodBean = (FoodBean) getIntent().getSerializableExtra("food");
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
            showFailToast("请输入口味名称");
            return;
        }
        String priceStr = etPrice.getText().toString().trim();
        if (TextUtils.isEmpty(priceStr)) {
            showFailToast("请输入口味价格");
            return;
        }

        presenter.commit(nameStr, priceStr, foodBean.getProductId());
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
