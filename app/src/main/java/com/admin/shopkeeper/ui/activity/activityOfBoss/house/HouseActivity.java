package com.admin.shopkeeper.ui.activity.activityOfBoss.house;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.HouseAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.houseEdit.HouseEditActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class HouseActivity extends BaseActivity<HousePresenter> implements IHouseView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PopupWindow laheiPop;
    private HouseAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new HousePresenter(this,this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_house;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("房间管理");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new HouseAdapter(R.layout.item_house);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showDeletePop(adapter.getData().get(position));
        });

        presenter.getData();
    }

    public void showDeletePop(HouseBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_delete, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.delete(bean);
        });
        laheiView.findViewById(R.id.pop_edit).setOnClickListener(v -> {
            Intent intent = new Intent(this, HouseEditActivity.class);
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
                startActivityForResult(HouseEditActivity.class, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getData();
        }
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        if (laheiPop != null) {
            laheiPop.dismiss();
        }
    }

    @Override
    public void success(List<HouseBean> datas) {
        adapter.setNewData(datas);
    }

}
