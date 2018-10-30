package com.admin.shopkeeper.ui.activity.config.room;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.RoomsAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.ui.activity.config.room.ConfigRoomPresenter;
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

public class ConfigRoomActivity extends BaseActivity<ConfigRoomPresenter> implements IConfigRoomView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.ptrLayout)
    PtrFrameLayout ptrLayout;

    private RoomsAdapter adapter;

    @Override
    protected void initPresenter() {

        presenter = new ConfigRoomPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_room;
    }

    @Override
    public void initView() {


        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("房间列表");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        initPTRLayout();
        initRecyclerView();

        if (TextUtils.isEmpty(App.INSTANCE().getShopID())) {
            warning("请先配置店铺ID");
        }
        presenter.getDB();

    }

    private void initRecyclerView() {
        adapter = new RoomsAdapter(R.layout.item_room);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .sizeResId(R.dimen._1sdp)
                .colorResId(R.color.gray)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .build());
        recyclerView.setAdapter(adapter);
    }

    private void initPTRLayout() {

        MaterialHeader header = new MaterialHeader(ConfigRoomActivity.this);
        int c[] = {ContextCompat.getColor(ConfigRoomActivity.this, R.color.colorPrimary)};
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
                if (TextUtils.isEmpty(App.INSTANCE().getShopID())) {
                    warning("请先配置店铺ID");
                } else {
                    presenter.getServers();
                }
            }
        });

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
    public void error(String message) {
        refreshComplete();
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void showRooms(List<RoomEntity> roomEntities) {
        refreshComplete();
        SPUtils.getInstance().put(SPUtils.PREFERENCE_ROOM, true);
        if (roomEntities.size() > 0) {
            adapter.setNewData(roomEntities);
        }
        Toasty.normal(this, "刷新成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void warning(String message) {
        refreshComplete();

        Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show();
    }

    private void refreshComplete() {
        if (ptrLayout.isRefreshing()) {
            ptrLayout.refreshComplete();
        }
    }

    @Override
    public void noDataWithDB() {
        SPUtils.getInstance().put(SPUtils.PREFERENCE_ROOM, true);
        ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
    }
}
