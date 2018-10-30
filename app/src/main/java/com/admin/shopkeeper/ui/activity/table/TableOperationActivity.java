package com.admin.shopkeeper.ui.activity.table;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.ui.activity.messageList.MessageListActivity;
import com.admin.shopkeeper.ui.activity.orderFood.OrderFoodActivity;
import com.admin.shopkeeper.ui.fragment.desk.DeskFragment;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class TableOperationActivity extends BaseActivity<TableOperationPresenter> implements IOperationTableView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //        if (event.getType() == 1) {
//            toolbar.setTitle("换桌");
//        } else if (event.getType() == 2) {
//            toolbar.setTitle("并单");
//        } else if (event.getType() == 3) {
//            toolbar.setTitle("转菜");
//        }
    public static final int P1 = 1;//换桌
    public static final int P2 = 2;//并单
    public static final int P3 = 3;//转菜
    public static final int P4 = 4;//堂点
    public static final int P5 = 5;//快餐
    public static final int P6 = 6;//绑定桌位

    private int type;

    @Override
    protected void initPresenter() {
        presenter = new TableOperationPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_table_operation;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra(Config.PARAM1, 0);
        switch (type) {
            case 1:
                toolbar.setTitle("换桌");
                break;
            case P2:
                toolbar.setTitle("并单");
                break;
            case 3:
                toolbar.setTitle("转菜");
                break;
            case 4:
                toolbar.setTitle("堂点");
                break;
            case P5:
                toolbar.setTitle("快餐");
                break;
            case P6:
                toolbar.setTitle("绑定桌台");
                break;
            default:
                finish();
                break;

        }

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().add(R.id.operationContent, DeskFragment.newInstance("", "")).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_message:
                Intent intent = new Intent(TableOperationActivity.this, MessageListActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_table_operation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();


    }
}
