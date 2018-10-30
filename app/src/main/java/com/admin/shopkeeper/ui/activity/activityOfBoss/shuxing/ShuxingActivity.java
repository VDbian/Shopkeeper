package com.admin.shopkeeper.ui.activity.activityOfBoss.shuxing;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FoodSeasonAdapter;
import com.admin.shopkeeper.adapter.FoodShuxingAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.SeasonBean;
import com.admin.shopkeeper.entity.ShuxingBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.kouwei.IKouweiView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.kouwei.KouweiPresenter;
import com.admin.shopkeeper.ui.activity.activityOfBoss.seasonedit.SeasonEditActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.shuxingedit.ShuxingEditActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class ShuxingActivity extends BaseActivity<ShuxingPresenter> implements IShuxingView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private PopupWindow laheiPop;
    private FoodShuxingAdapter adapter;
    private FoodBean foodBean;

    @Override
    protected void initPresenter() {
        presenter = new ShuxingPresenter(this,this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shuxing;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("规格");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        foodBean = (FoodBean) getIntent().getSerializableExtra("bean");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new FoodShuxingAdapter(R.layout.item_shuxing);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showDeletePop(adapter.getData().get(position));
        });

        presenter.getData(foodBean);
    }

    public void showDeletePop(ShuxingBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_delete_edit, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.delete(foodBean, bean);
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
                Intent intent = new Intent(this, ShuxingEditActivity.class);
                intent.putExtra("food", foodBean);
                startActivityForResult(intent, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getData(foodBean);
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
    public void success(List<ShuxingBean> datas) {
        adapter.setNewData(datas);
    }
}
