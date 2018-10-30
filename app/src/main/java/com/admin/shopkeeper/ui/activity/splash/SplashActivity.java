package com.admin.shopkeeper.ui.activity.splash;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.boss.BossMainActivity;
import com.admin.shopkeeper.ui.activity.home.HomeActivity;
import com.admin.shopkeeper.ui.activity.login.LoginActivity;
import com.admin.shopkeeper.utils.SPUtils;
import com.admin.shopkeeper.utils.Tools;
import com.gyf.barlibrary.ImmersionBar;

import es.dmoral.toasty.Toasty;


public class SplashActivity extends BaseActivity<SplashPresenter> implements ISplashView {

    @Override
    protected void initPresenter() {
        presenter = new SplashPresenter(this, this);
        presenter.init();
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .init();

        presenter.getUpdate();
        presenter.getUser();
        presenter.getConfig();
    }

    @Override
    protected void initTheme() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
        ImmersionBar.with(this).init();
    }

    @Override
    public void update(int code) {
        if (code > Tools.getVersionCode(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("更新提示");
            builder.setMessage("请下载最新版本。");
            builder.setPositiveButton("确定", (dialog, which) -> {
                goToMarket();
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            if (App.INSTANCE().getUser() != null && SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_LOGIN, false)) {
                if (App.INSTANCE().getUser().getRoleID().equals("2")) {
                    startActivity(BossMainActivity.class);
                } else {
                    startActivity(HomeActivity.class);
                }
            } else {
                startActivity(LoginActivity.class);
            }
            finish();
        }
    }

    public void goToMarket() {
        Uri uri = Uri.parse("market://details?id=com.admin.shopkeeper");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            goToMarket.setClassName("com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");
            goToMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(goToMarket);
        }catch (Exception e){
            Toasty.warning(SplashActivity.this, "请先安装应用宝，再下载最新版本", Toast.LENGTH_SHORT, true).show();
            e.printStackTrace();
        }
    }

    @Override
    public void error(String msg) {
        showToast(msg);
    }
}
