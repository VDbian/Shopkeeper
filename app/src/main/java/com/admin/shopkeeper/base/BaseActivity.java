package com.admin.shopkeeper.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.utils.SPUtils;
import com.admin.shopkeeper.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


/**
 * Created by zeng on 2016/11/7.
 */
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();
    protected T presenter;
    protected Unbinder unbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        initPresenter();
        Timber.i("onCreate");
    }

    protected abstract void initPresenter();

    protected abstract int getLayoutId();

    protected void initTheme() {
        if (App.INSTANCE().getUser() != null
                && SPUtils.getInstance().getBoolean(SPUtils.PREFERENCE_LOGIN, false)) {
            if (App.INSTANCE().getUser().getRoleID().equals("2")) {
                getApplication().setTheme(R.style.BossTheme);
            } else {
                getApplication().setTheme(R.style.AppTheme);
            }
        } else {
            getApplication().setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.i("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.i("onResume");

    }

    @Override
    protected void onPause() {

        super.onPause();
        Timber.i("onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.i("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Timber.i("onRestart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        Timber.i("onDestroy");
    }

    /**
     * 弹出成功Toast
     *
     * @param text
     */
    public void showSuccessToast(String text) {
        ToastUtils.showToast(this, text, ToastUtils.TOAST_SUCCESS);
    }

    /**
     * 弹出失败Toast
     *
     * @param text
     */
    public void showFailToast(String text) {
        ToastUtils.showToast(this, text, ToastUtils.TOAST_FAILE);
    }

    /**
     * 弹出提示Toast
     *
     * @param text
     */
    public void showToast(String text) {
        ToastUtils.showToast(this, text, ToastUtils.TOAST_TIPS);
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }
}
