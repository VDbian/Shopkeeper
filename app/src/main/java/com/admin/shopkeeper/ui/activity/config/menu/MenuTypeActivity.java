package com.admin.shopkeeper.ui.activity.config.menu;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.MenuTypeAdapter;
import com.admin.shopkeeper.adapter.RoomsAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.ui.activity.config.room.ConfigRoomActivity;
import com.admin.shopkeeper.utils.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class MenuTypeActivity extends BaseActivity<MenuTypePresenter> implements IMenuTypeView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ptrLayout)
    PtrFrameLayout ptrLayout;


    private MenuTypeAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Override
    protected void initPresenter() {

        presenter = new MenuTypePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu_type;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("菜单类别");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        initPTRLayout();
        initRecyclerView();
        if (TextUtils.isEmpty(App.INSTANCE().getShopID())) {
            warning("请先配置店铺ID");
            return;
        }

        presenter.getDB();
//
    }

    private void initRecyclerView() {

        adapter = new MenuTypeAdapter(R.layout.item_room);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .sizeResId(R.dimen._1sdp)
                .colorResId(R.color.gray)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .build());
        recyclerView.setAdapter(adapter);
    }

    private void initPTRLayout() {

        MaterialHeader header = new MaterialHeader(MenuTypeActivity.this);
        int c[] = {ContextCompat.getColor(MenuTypeActivity.this, R.color.colorPrimary)};
        header.setColorSchemeColors(c);
        header.setPadding(0, PtrLocalDisplay.dp2px(20), 0, PtrLocalDisplay.dp2px(20));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        ptrLayout.setDurationToCloseHeader(1000);
        ptrLayout.setHeaderView(header);
        ptrLayout.addPtrUIHandler(header);
        ptrLayout.setPinContent(true);//设置为true时content的内容位置将不会改变

        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.getFoodsTypes();
            }
        });
    }

    @Override
    public void error(String message) {

        refreshComplete();
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void warning(String s) {
        refreshComplete();
        Toasty.warning(this, s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void showMenus(List<MenuTypeEntity> entities) {
        refreshComplete();
        SPUtils.getInstance().put(SPUtils.PREFERENCE_MENU, true);
        if (entities.size() > 0) {
            adapter.setNewData(entities);
        }
    }

    private void refreshComplete() {
        if (ptrLayout.isRefreshing()) {
            ptrLayout.refreshComplete();
        }
    }

    @Override
    public void noDataWithDB() {
        ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
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
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
