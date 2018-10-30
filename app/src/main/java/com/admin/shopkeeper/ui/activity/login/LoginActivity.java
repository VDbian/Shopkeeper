package com.admin.shopkeeper.ui.activity.login;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;

import com.admin.shopkeeper.ui.activity.activityOfBoss.boss.BossMainActivity;
import com.admin.shopkeeper.ui.activity.config.ConfigActivity;
import com.admin.shopkeeper.ui.activity.home.HomeActivity;
import com.admin.shopkeeper.utils.HideUtils;
import com.admin.shopkeeper.utils.SPUtils;
import com.admin.shopkeeper.weight.CenterTitleToolbar;
import com.gyf.barlibrary.ImmersionBar;


import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    @BindView(R.id.button_login)
    AppCompatButton button_login;

    @BindView(R.id.toolbar)
    CenterTitleToolbar toolbar;

    @BindView(R.id.username_login)
    AppCompatEditText username;

    @BindView(R.id.password_login)
    AppCompatEditText password;

    @OnClick({R.id.button_login})
    void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_login:

                if (TextUtils.isEmpty(username.getText())) {
                    Toasty.warning(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText())) {
                    Toasty.warning(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                if (TextUtils.isEmpty(App.INSTANCE().getShopID())) {
                    Toasty.warning(LoginActivity.this, "请在配置页面配置店铺ID", Toast.LENGTH_SHORT, true).show();
                    return;
                }


//                if (!SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_ROOM, false)) {
////                if (!AppDbHelper.INSTANCE().getRoomCount(App.INSTANCE().getShopID())) {
//
//                    Toasty.warning(LoginActivity.this, "请在配置页面获取房间列表", Toast.LENGTH_SHORT, true).show();
//                    return;
//                }
//
//                if (!SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_MENU, false)) {
////                if (!AppDbHelper.INSTANCE().getMenuFoodCount(App.INSTANCE().getShopID())) {
//                    Toasty.warning(LoginActivity.this, "请在配置页面获取菜单列表", Toast.LENGTH_SHORT, true).show();
//                    return;
//
//                }
//                if (!SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_MEAL, false)) {
////                if (!AppDbHelper.INSTANCE().getMenuMealCount(App.INSTANCE().getShopID())) {
//                    Toasty.warning(LoginActivity.this, "请在配置页面获取套餐列表", Toast.LENGTH_SHORT, true).show();
//                    return;
//
//                }


//                DialogUtils.showDialog(this,"登录中。。。");
                presenter.login(username.getText().toString().trim(), password.getText().toString().trim());

                break;
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        HideUtils.init(this);
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .init();

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.nav_setting);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initTheme() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(LoginActivity.this, ConfigActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showError(String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void showFailed(String failed) {
        Toasty.warning(this, failed, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void loginSuccess() {
        if (App.INSTANCE().getUser().getRoleID().equals("2")) {
            startActivity(BossMainActivity.class);
        }else {
//            if (!SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_ROOM, false)) {
////                if (!AppDbHelper.INSTANCE().getRoomCount(App.INSTANCE().getShopID())) {
//
//                Toasty.warning(LoginActivity.this, "请在配置页面获取房间列表", Toast.LENGTH_SHORT, true).show();
//                return;
//            }

            if (!SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_MENU, false)) {
//                if (!AppDbHelper.INSTANCE().getMenuFoodCount(App.INSTANCE().getShopID())) {
                Toasty.warning(LoginActivity.this, "请在配置页面获取菜单列表", Toast.LENGTH_SHORT, true).show();
                return;

            }
            if (!SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_MEAL, false)) {
//                if (!AppDbHelper.INSTANCE().getMenuMealCount(App.INSTANCE().getShopID())) {
                Toasty.warning(LoginActivity.this, "请在配置页面获取套餐列表", Toast.LENGTH_SHORT, true).show();
                return;

            }
            startActivity(HomeActivity.class);
        }
        finish();
    }
}
