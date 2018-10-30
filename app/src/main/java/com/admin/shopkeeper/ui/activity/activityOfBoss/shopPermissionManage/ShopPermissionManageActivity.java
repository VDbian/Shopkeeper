package com.admin.shopkeeper.ui.activity.activityOfBoss.shopPermissionManage;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.ShopPermissionManageAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.ShopPermissionManageBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shopPermissionEdit.ShopPermissionEditActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class ShopPermissionManageActivity extends BaseActivity<ShopPermissionManagePresenter> implements IShopPermissionManageView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PopupWindow mPopupWindow;
    private ShopPermissionManageAdapter adapter;


    @Override
    protected void initPresenter() {
        presenter = new ShopPermissionManagePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_permission_manage;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("商家权限管理");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new ShopPermissionManageAdapter(R.layout.item_shop_permission_manage);
        recyclerView.setAdapter(adapter);
        presenter.getData();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showManagePop(String userId) {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_delete, null);
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        TextView editTV = (TextView) view.findViewById(R.id.pop_edit);
        TextView deleteTV = (TextView) view.findViewById(R.id.pop_delete);
        TextView cancelTV = (TextView) view.findViewById(R.id.pop_cancel);

        deleteTV.setVisibility(View.GONE);
        editTV.setText("编辑权限");
        cancelTV.setOnClickListener(v -> {
            mPopupWindow.dismiss();
        });
        editTV.setOnClickListener(v -> {
            Intent intent = new Intent(this , ShopPermissionEditActivity.class);
            intent.putExtra(Config.PARAM1 , userId);
            startActivity(intent);
        });

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        mPopupWindow.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(List<ShopPermissionManageBean> shopPermissionManageList) {
        adapter.setNewData(shopPermissionManageList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showManagePop(shopPermissionManageList.get(position).getUserID());
            }
        });
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }
}
