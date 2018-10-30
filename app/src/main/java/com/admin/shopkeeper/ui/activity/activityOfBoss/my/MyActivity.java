package com.admin.shopkeeper.ui.activity.activityOfBoss.my;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.BossUserInfo;
import com.admin.shopkeeper.entity.User;
import com.admin.shopkeeper.ui.activity.activityOfBoss.changePassword.ChangePasswordActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.changeShopAddress.ChangeShopAddressActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.changeShopName.ChangeShopNameActivity;
import com.admin.shopkeeper.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MyActivity extends BaseActivity<MyPresenter> implements IMyView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.change_pwd_rl_my)
    RelativeLayout changePwd;
    @BindView(R.id.change_shop_address_rl_my)
    RelativeLayout changeShopAddress;
    @BindView(R.id.change_shop_name_rl_my)
    LinearLayout changeShopName;
    @BindView(R.id.account_tv_my)
    TextView accountTV;
    @BindView(R.id.shop_name_tv_my)
    TextView shopNameTV;
    @BindView(R.id.shop_address_tv_my)
    TextView shopAddressTV;

    private BossUserInfo bossUserInfo;

    @Override
    protected void initPresenter() {
        presenter = new MyPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("个人信息");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bossUserInfo = (BossUserInfo) intent.getSerializableExtra("BOSSUSERINFO");

        accountTV.setText(bossUserInfo.getName());
//        shopNameTV.setText(bossUserInfo.getShopNames());
//        shopAddressTV.setText(bossUserInfo.getAddress());

        presenter.getData();
    }

    @OnClick(R.id.change_pwd_rl_my)
    public void setChangePwd() {
        startActivity(ChangePasswordActivity.class);
    }

    @OnClick(R.id.change_shop_address_rl_my)
    public void setChangeShopAddress() {
        Intent intent = new Intent(this, ChangeShopAddressActivity.class);
        intent.putExtra(Config.PARAM1,bossUserInfo.getAddress());
        startActivity(intent);
    }

    @OnClick(R.id.change_shop_name_rl_my)
    public void setChangeShopName() {
        Intent intent = new Intent(this, ChangeShopNameActivity.class);
        intent.putExtra(Config.PARAM1,bossUserInfo.getShopNames());
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shopNameTV.setText(App.INSTANCE().getShopName());
        shopAddressTV.setText(App.INSTANCE().getShopAddress());
    }
}
