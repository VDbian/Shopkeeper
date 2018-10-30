package com.admin.shopkeeper.ui.activity.activityOfBoss.couponLineDown;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CouponLineDownAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.editCouponLineDown.EditCouponLineDownActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.setOrLookFood.SetOrLookFoodActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CouponLineDownActivity extends BaseActivity<CouponPresenter> implements ICouponLineDownView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;


    int page = 1;
    private CouponLineDownAdapter adapter;
    private PopupWindow laheiPop;

    @Override
    protected void initPresenter() {
        presenter = new CouponPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_line_down;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("其他券管理");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            presenter.getLineDownInfo(page, "");
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new CouponLineDownAdapter(R.layout.item_coupon_line_down);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showDeletePop(adapter.getData().get(position));
        });

        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getLineDownInfo(page, "");
        }, recyclerView);

        presenter.getLineDownInfo(page, "");
    }

    public void showDeletePop(CouponLineDownBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_coupon, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });
        TextView tv = (TextView) laheiView.findViewById(R.id.pop_setshop);
        tv.setText("设置与查看设置商品（类别）");
        laheiView.findViewById(R.id.pop_setshop).setOnClickListener(view -> {
            Intent intent = new Intent(this, SetOrLookFoodActivity.class);
            intent.putExtra("bean", bean);
            startActivityForResult(intent, 101);
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.delete(bean.getGuid());
        });
        laheiView.findViewById(R.id.pop_detail).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditCouponLineDownActivity.class);
            intent.putExtra("bean", bean);
            startActivityForResult(intent, 101);
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditCouponLineDownActivity.class);
            intent.putExtra("bean", bean);
            startActivityForResult(intent, 101);
            laheiPop.dismiss();
        });

        laheiPop.setOutsideTouchable(true);
        laheiPop.setFocusable(true);
        laheiPop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        laheiPop.setOnDismissListener(() -> {
            backgroundAlpha(1f);
        });
        laheiPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
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
                startActivityForResult(EditCouponLineDownActivity.class, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            page = 1;
            presenter.getLineDownInfo(1, "");
        }
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);

        refreshLayout.setRefreshing(false);
        adapter.loadMoreEnd();

        if (laheiPop != null && laheiPop.isShowing()) {
            laheiPop.dismiss();
        }
    }

    List<CouponLineDownBean> datas = new ArrayList<>();

    @Override
    public void success(List<CouponLineDownBean> data) {
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

        if (laheiPop != null && laheiPop.isShowing()) {
            laheiPop.dismiss();
        }
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        page = 1;
        presenter.getLineDownInfo(page, "");
    }

   /* @Override
    public void shopsuccess(CouponLineDownBean bean, List<ShopBean> shopBeen) {
        if (shopBeen == null || shopBeen.size() == 0) {
            showFailToast("暂无商家列表");
            return;
        }

        for (int i = 0; i < shopBeen.size(); i++) {
            if (bean.getGuid().contains(shopBeen.get(i).getGuid())) {
                shopBeen.get(i).setSelect(true);
            }
        }

        ShopSelectDialog.Builder builder = new ShopSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("设置商家");
        builder.setReasons(shopBeen);
        builder.setButtonClick(new ShopSelectDialog.OnButtonClick() {


            @Override
            public void onOk(String text, String value) {
                presenter.setShopData1(bean, value);

            }
        });
        builder.creater().show();
    }*/
}
