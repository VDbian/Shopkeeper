package com.admin.shopkeeper.ui.activity.activityOfBoss.bindfood;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FindFoodAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;
import com.admin.shopkeeper.entity.SaleBean;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BindFoodActivity extends BaseActivity<BindFoodPresenter> implements IBindFoodView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    SaleBean bean;

    @BindView(R.id.et_food_search)
    AppCompatEditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;

    int page = 1;
    private FindFoodAdapter adapter;
    boolean isSearch = false;


    @Override
    protected void initPresenter() {
        presenter = new BindFoodPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_food;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("查看设置商品");
        toolbar.setNavigationIcon(R.mipmap.person_advar_man);
        setSupportActionBar(toolbar);

        bean = (SaleBean) getIntent().getSerializableExtra("bean");
        refreshLayout.setOnRefreshListener(() -> {
            if(isSearch){
                return;
            }
            page = 1;
            presenter.getFood(bean, "");
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new FindFoodAdapter(R.layout.item_find_food, this);
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getFood(bean, "");
        }, recyclerView);


        presenter.getFood(bean, "");

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    isSearch = false;
                    ivClear.setVisibility(View.INVISIBLE);
                    adapter.setNewData(datas);
                } else {
                    isSearch = true;
                    ivClear.setVisibility(View.VISIBLE);
                    searchFood(s.toString().trim());
                }
            }
        });
        etSearch.clearFocus();
    }

    @OnClick(R.id.iv_clear)
    public void clearClick() {
        etSearch.setText("");
    }

    private void searchFood(String str) {
        List<FindFoodCouponDownBean> list = new ArrayList<>();
        for (FindFoodCouponDownBean bean : datas) {
            if (bean.getProductName().contains(str)) {
                list.add(bean);
            }
        }
        adapter.setNewData(list);
        refreshLayout.setRefreshing(false);
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
        refreshLayout.setRefreshing(false);
        adapter.loadMoreEnd();
    }

    List<FindFoodCouponDownBean> datas = new ArrayList<>();

    @Override
    public void success(List<FindFoodCouponDownBean> data) {
        if (page == 1) {
            datas.clear();
        }
        datas.addAll(data);
        adapter.setNewData(datas);
        refreshLayout.setRefreshing(false);
        if (data.size() < 20) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
    }
}
