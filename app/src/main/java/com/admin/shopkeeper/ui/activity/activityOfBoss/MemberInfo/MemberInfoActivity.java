package com.admin.shopkeeper.ui.activity.activityOfBoss.MemberInfo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.MemberInfoOfItemAdapter;
import com.admin.shopkeeper.adapter.OrderBussinessAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.MemberInfoBean;
import com.admin.shopkeeper.entity.MemberInfoOfItemBean;
import com.admin.shopkeeper.entity.OrderBussinessBean;
import com.admin.shopkeeper.utils.Tools;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberInfoActivity extends BaseActivity<MemberInfoPresenter> implements IMemberInfoView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MemberInfoOfItemAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new MemberInfoPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_info;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        Intent intent = getIntent();
        int type = intent.getIntExtra(Config.PARAM1,0);
        MemberInfoBean bean = (MemberInfoBean) intent.getSerializableExtra(Config.PARAM2);
        if(type == 2){
            toolbar.setTitle(bean.getMemberDepart()+"-消费记录");
        }else if(type == 3){
            toolbar.setTitle(bean.getMemberDepart()+"-充值记录");
        }else if(type == 4){
            toolbar.setTitle(bean.getMemberDepart()+"-积分记录");
        } else if (type == 5) {
            toolbar.setTitle(bean.getMemberDepart()+"-卡券记录");
        }
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemberInfoOfItemAdapter(R.layout.item_member_info ,type);
        recyclerView.setAdapter(adapter);

        presenter.getMemberInfoOfItem(type , bean.getId());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(List<MemberInfoOfItemBean> data) {
        adapter.setNewData(data);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }
}
