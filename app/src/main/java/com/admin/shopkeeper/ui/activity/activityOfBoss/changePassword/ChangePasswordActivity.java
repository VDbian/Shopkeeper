package com.admin.shopkeeper.ui.activity.activityOfBoss.changePassword;


import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> implements IChangePasswordView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.old_password_et)
    EditText oldPassword;
    @BindView(R.id.new_password_et)
    EditText newPassword;
    @BindView(R.id.again_password_et)
    EditText againPassword;


    @Override
    protected void initPresenter() {
        presenter = new ChangePasswordPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("修改登录密码");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);


    }
    @OnClick(R.id.toolbar)
    public void setToolBarClick(){
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_sure:
                if(!TextUtils.isEmpty(oldPassword.getText().toString()) ){
                    if(!TextUtils.isEmpty(newPassword.getText().toString())){
                        if(newPassword.getText().toString().equals(againPassword.getText().toString())){
                            presenter.changePassword(oldPassword.getText().toString() , newPassword.getText().toString());
                        }else{
                            Toasty.warning(this,"两次输入的密码不等").show();
                        }
                    }else{
                        Toasty.warning(this,"新密码不能为空").show();
                    }
                }else{
                    Toasty.warning(this,"旧密码不能为空").show();
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
    }
}
