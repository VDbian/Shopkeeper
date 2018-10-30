package com.admin.shopkeeper.ui.activity.foodsList;


import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FoodAdapter;
import com.admin.shopkeeper.adapter.MenuClassAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.utils.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class FoodsListActivity extends BaseActivity<FoodsListPresenter> implements IFoodsListView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.leftRecyclerView)
    RecyclerView left;
    @BindView(R.id.rightRecyclerView)
    RecyclerView rigth;
    @BindView(R.id.ptrLayout)
    PtrFrameLayout ptrLayout;

    private MenuClassAdapter menuAdapter;
    private FoodAdapter foodAdapter;

    @Override
    protected void initPresenter() {
        presenter = new FoodsListPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_food_list;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("菜单列表");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        initPTRLayout();
        initAdapter();


        presenter.getDBFood();
    }

    private void initPTRLayout() {
        MaterialHeader header = new MaterialHeader(FoodsListActivity.this);
        int c[] = {ContextCompat.getColor(FoodsListActivity.this, R.color.colorPrimary)};
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
                    presenter.getFoods();
                    //presenter.getMeal();
                }

            }
        });

    }

    private void initAdapter() {
        menuAdapter = new MenuClassAdapter(R.layout.item_menu_classification);

        left.setLayoutManager(new LinearLayoutManager(FoodsListActivity.this));
        left.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener((adapter, view, position) -> {


            MenuTypeEntity type = menuAdapter.getItem(position);
            assert type != null;
            if (!type.isSelected()) {
                for (MenuTypeEntity m : menuAdapter.getData()) {
                    m.setSelected(false);
                }
                type.setSelected(true);
                foodAdapter.setNewData(type.getFoods());

            }
            menuAdapter.notifyDataSetChanged();


        });


        foodAdapter = new FoodAdapter(R.layout.item_food);
        rigth.setLayoutManager(new LinearLayoutManager(FoodsListActivity.this));
        rigth.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.gray)
                .sizeResId(R.dimen._1sdp)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp).build());
        rigth.setAdapter(foodAdapter);

        left.addOnScrollListener(listener);
        rigth.addOnScrollListener(listener);
    }

    private OnScrollListener listener = new OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int topRowVerticalPosition =
                    (recyclerView == null || recyclerView.getChildCount() == 0)
                            ? 0 : recyclerView.getChildAt(0).getTop();
            ptrLayout.setEnabled(topRowVerticalPosition >= 0);
        }
    };


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
    public void warning(String s) {
        refreshComplete();
        Toasty.warning(this, s, Toast.LENGTH_SHORT, true).show();
    }


    @Override
    public void success(List<MenuTypeEntity> menuTypeEntities) {
        if (menuTypeEntities.size() > 0) {
            refreshComplete();
            SPUtils.getInstance().put(SPUtils.PREFERENCE_MEAL, true);
            SPUtils.getInstance().put(SPUtils.PREFERENCE_MENU, true);
            menuTypeEntities.get(0).setSelected(true);
            menuAdapter.setNewData(menuTypeEntities);
            foodAdapter.setNewData(menuTypeEntities.get(0).getFoods());
            Toasty.normal(this, "刷新成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getService() {
        ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
    }

    private void refreshComplete() {
        if (ptrLayout.isRefreshing()) {
            ptrLayout.refreshComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
        ImmersionBar.with(this).destroy();
    }
}
