package com.admin.shopkeeper.ui.activity.activityOfBoss.mansong;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.admin.shopkeeper.adapter.FreeAdapter;
import com.admin.shopkeeper.adapter.MansongAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FreeBean;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.MansongBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.free.FreeActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.free.FreePresenter;
import com.admin.shopkeeper.ui.activity.activityOfBoss.free.IFreeView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.freedetail.FreeDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhang.GuaZhangActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangdetail.GuaZhangDetailActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangedit.GuazhangEditActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.mansongedit.MansongEditActivity;
import com.admin.shopkeeper.utils.Tools;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MansongActivity extends BaseActivity<MansongPresenter> implements IMansongView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;


    int page = 1;
    private MansongAdapter adapter;
    private PopupWindow laheiPop;

    @Override
    protected void initPresenter() {
        presenter = new MansongPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mansong;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("微信满送规则");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            presenter.getData(page, "");
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new MansongAdapter(R.layout.item_mansong);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showDeletePop(adapter.getData().get(position));
        });

        adapter.setOnLoadMoreListener(() -> {
            page++;
            presenter.getData(page, "");
        }, recyclerView);

        presenter.getData(page, "");
    }

    public void showDeletePop(MansongBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_delete, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.delete(bean.getGuid());
        });
        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, MansongEditActivity.class);
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
                startActivityForResult(MansongEditActivity.class, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            page = 1;
            presenter.getData(1, "");
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

    List<MansongBean> datas = new ArrayList<>();

    @Override
    public void success(List<MansongBean> data) {
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
        presenter.getData(page, "");
    }
}
