package com.admin.shopkeeper.ui.activity.activityOfBoss.deletefood;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.DeleteFoodAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MealBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.setFood.SetFoodActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DeleteFoodActivity extends BaseActivity<DeleteFoodPresenter> implements IDeleteFoodView {

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

    private DeleteFoodAdapter adapter;
    int page = 1;
    private MealBean mealBean;

    @Override
    protected void initPresenter() {
        presenter = new DeleteFoodPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_delete_food;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        mealBean = (MealBean) getIntent().getSerializableExtra("bean");

        toolbar.setTitle("删除菜品");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new DeleteFoodAdapter(R.layout.item_deletefood);
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener((adapter1, view, position) -> {
//            showDeletePop(adapter.getData().get(position));
//        });
        refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            presenter.getData("11", page, "", mealBean.getId());
        });
        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getData("11", page, "", mealBean.getId());
        }, recyclerView);

        presenter.getData("11", page, "", mealBean.getId());

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

    @OnClick(R.id.tv_cancel)
    public void cancelClick() {
        for (FoodBean bean : adapter.getData()) {
            bean.setDelete(false);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_bind)
    public void bindClick() {
        String ids = "";
        for (FoodBean bean : adapter.getData()) {
            if (bean.isDelete()) {
                ids += bean.getId() + ",";
            }
        }

        if (!TextUtils.isEmpty(ids)) {
            ids = ids.substring(0, ids.length() - 1);
        }

        presenter.deleteFood(ids, mealBean);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add:
                Intent intent = new Intent(this, SetFoodActivity.class);
                intent.putExtra("bean", mealBean);
                startActivityForResult(intent, 101);
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
        presenter.getData("11", page, "", mealBean.getId());
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

    public void showDeletePop(FoodBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否删除该菜品？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //presenter.deleteFood(bean, mealBean);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getData("11", page, "", mealBean.getId());
        }
    }
}
