package com.admin.shopkeeper.ui.activity.activityOfBoss.changeShopName;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.activity.messageList.MessageListActivity;
import com.admin.shopkeeper.ui.activity.orderFood.OrderFoodActivity;
import com.admin.shopkeeper.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ChangeShopNameActivity extends BaseActivity<ChangeShopNamePresenter> implements IChangeShopNameView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.change_shop_name_et)
    EditText changeShopNameET;


    @Override
    protected void initPresenter() {
        presenter = new ChangeShopNamePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_shop_name;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("商家名称");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);


        changeShopNameET.setHint(App.INSTANCE().getShopName());


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
                if(!TextUtils.isEmpty(changeShopNameET.getText().toString())&&!TextUtils.isEmpty(changeShopNameET.getText().toString().trim())){
                    presenter.changeShopName(changeShopNameET.getText().toString());
                }else{
                    Toasty.info(this , "商家名称不能为空",Toast.LENGTH_SHORT,true).show();
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
        App.INSTANCE().setShopName(changeShopNameET.getText().toString());
    }
}
