package com.admin.shopkeeper.ui.activity.activityOfBoss.changeShopAddress;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ChangeShopAddressActivity extends BaseActivity<ChangeShopAddressPresenter> implements IChangeShopAddressView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.change_shop_address_et)
    EditText addressEditText;
    private String strAddress;


    @Override
    protected void initPresenter() {
        presenter = new ChangeShopAddressPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_shop_address;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("商家地址");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);


        addressEditText.setHint(App.INSTANCE().getShopAddress());
        //addressEditText.setSelection(addressEditText.length());
    }

    @OnClick(R.id.toolbar)
    public void setToolBarClick() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_sure:
                if (!TextUtils.isEmpty(addressEditText.getText().toString())&&!TextUtils.isEmpty(addressEditText.getText().toString().trim())) {
                    presenter.changeShopAddress(addressEditText.getText().toString());
                }else{
                    Toasty.info(getApplicationContext(),"商家名称不能为空",Toast.LENGTH_SHORT,true).show();
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_boss, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);

    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        App.INSTANCE().setShopAddress(addressEditText.getText().toString());

    }
}
