package com.admin.shopkeeper.ui.activity.activityOfBoss.setFood;


import android.content.Intent;
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
import com.admin.shopkeeper.adapter.SetFoodAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MealBean;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SetFoodActivity extends BaseActivity<SetFoodPresenter> implements ISetFoodView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_food_search)
    AppCompatEditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private SetFoodAdapter adapter;
    int page = 1;
    private MealBean mealBean;
    private int type;

    @Override
    protected void initPresenter() {
        presenter = new SetFoodPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_food;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        mealBean = (MealBean) getIntent().getSerializableExtra("bean");
        type = getIntent().getIntExtra("type", 0);

        toolbar.setTitle(type == 0 ? "绑定菜品" : "删除菜品");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new SetFoodAdapter(R.layout.item_setfood);
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener((adapter1, view, position) -> {
//            showDeletePop(adapter.getData().get(position));
//        });
        refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            presenter.getData(type == 0 ? "10" : "11", page, "", mealBean.getId());
        });
        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getData(type == 0 ? "10" : "11", page, "", mealBean.getId());
        }, recyclerView);

        presenter.getData(type == 0 ? "10" : "11", page, "", mealBean.getId());

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
                    ivClear.setVisibility(View.INVISIBLE);
                    adapter.setNewData(datas);
                } else {
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

    @OnClick(R.id.tv_cancel)
    public void cancelClick() {
        for (FoodBean bean : adapter.getData()) {
            bean.setCount(0);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_bind)
    public void bindClick() {
        String names = "";
        String ids = "";
        String counts = "";
        for (FoodBean bean : adapter.getData()) {
            if (bean.getCount() > 0) {
                names += bean.getProductName() + ",";
                ids += bean.getProductId() + ",";
                counts += bean.getCount() + ",";
            }
        }

        if (!TextUtils.isEmpty(names)) {
            names = names.substring(0, names.length() - 1);
            ids = ids.substring(0, ids.length() - 1);
            counts = counts.substring(0, counts.length() - 1);
        }

        presenter.addFood(names, ids, counts, mealBean);
    }

    private void searchFood(String str) {
        List<FoodBean> list = new ArrayList<>();
        for (FoodBean bean : datas) {
            if (bean.getProductName().contains(str)) {
                list.add(bean);
            }
        }
        adapter.setNewData(list);
        adapter.loadMoreComplete();
        adapter.loadMoreEnd();
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
    public void success(String msg) {
        showSuccessToast(msg);
        presenter.getData(type == 0 ? "10" : "11", page, "", mealBean.getId());
    }

    List<FoodBean> datas = new ArrayList<>();

    @Override
    public void success(List<FoodBean> foods) {
        if (page == 1) {
            datas.clear();
        }
        datas.addAll(foods);
        adapter.setNewData(datas);
        refreshLayout.setRefreshing(false);
        if (foods.size() < 20) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getData(type == 0 ? "10" : "11", page, "", mealBean.getId());
        }
    }
}
