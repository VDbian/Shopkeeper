package com.admin.shopkeeper.ui.activity.recharge;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.RechargeAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CheckDialog;
import com.admin.shopkeeper.entity.RechargeBean;
import com.admin.shopkeeper.ui.activity.rechargedetail.RechargeDetailActivity;
import com.admin.shopkeeper.ui.activity.rechargeedit.RechargeEditActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity<RechargePresenter> implements IRechargeView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private RechargeAdapter adapter;

    int page = 1;

    @Override
    protected void initPresenter() {
        presenter = new RechargePresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("线下充值");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new RechargeAdapter(R.layout.item_recharge);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showPop(adapter.getData().get(position));
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                presenter.getData(page);
            }
        }, recyclerView);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getData(page);
            }
        });


        presenter.getData(page);

    }

    public void showPop(RechargeBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_recharge, null);
        PopupWindow popupWindow = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        laheiView.findViewById(R.id.pop_recharge).setOnClickListener(v -> {
            gotoRechargeDetailActivity(0, bean);
            popupWindow.dismiss();
        });
        laheiView.findViewById(R.id.pop_recharge_product).setOnClickListener(v -> {
            gotoRechargeDetailActivity(1, bean);
            popupWindow.dismiss();
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        popupWindow.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    private void gotoRechargeDetailActivity(int i, RechargeBean bean) {
        Intent intent = new Intent(RechargeActivity.this, RechargeDetailActivity.class);
        intent.putExtra("type", i);
        intent.putExtra("bean", bean);
        startActivity(intent);
    }


    @OnClick(R.id.fl_search)
    public void searchClick() {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_search, null);
        PopupWindow searchPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        EditText etName = (EditText) laheiView.findViewById(R.id.pop_name);
        EditText etPhone = (EditText) laheiView.findViewById(R.id.pop_phone);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setNewData(datas);
                searchPop.dismiss();
            }
        });

        laheiView.findViewById(R.id.pop_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = etName.getText().toString().trim();
                String phoneStr = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(nameStr) && TextUtils.isEmpty(phoneStr)) {
                    adapter.setNewData(datas);
                } else {
                    presenter.serchData(nameStr, phoneStr);
                }
                searchPop.dismiss();
            }
        });

        searchPop.setOutsideTouchable(true);
        searchPop.setFocusable(true);
        searchPop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        searchPop.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        searchPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    private void selectDatas(String nameStr, String phoneStr) {
        List<RechargeBean> list = new ArrayList<>();
        for (RechargeBean bean : datas) {
            if (TextUtils.isEmpty(nameStr) && bean.getStaffTel().equals(phoneStr)) {
                list.add(bean);
            } else if (TextUtils.isEmpty(phoneStr) && bean.getStaffDepart().contains(nameStr)) {
                list.add(bean);
            } else if (bean.getStaffTel().equals(phoneStr) && bean.getStaffDepart().contains(nameStr)) {
                list.add(bean);
            }
        }
        adapter.setNewData(list);
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
                startActivityForResult(RechargeEditActivity.class, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
        adapter.loadMoreEnd();
        refreshLayout.setRefreshing(false);
    }

    List<RechargeBean> datas = new ArrayList<>();

    @Override
    public void success(List<RechargeBean> list) {
        if (page == 1) {
            this.datas.clear();
        }
        this.datas.addAll(list);
        adapter.setNewData(datas);
        adapter.loadMoreComplete();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void checkSuccess(int type, RechargeBean bean) {
        Intent intent = new Intent(RechargeActivity.this, RechargeDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("bean", bean);
        startActivity(intent);
    }

    @Override
    public void searchSuccess(List<RechargeBean> rechargeBeen) {
        this.datas.clear();
        this.datas.addAll(rechargeBeen);
        adapter.setNewData(datas);
        adapter.loadMoreEnd();
        refreshLayout.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            page = 1;
            presenter.getData(page);
        }
    }
}
