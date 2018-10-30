package com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangdetail;

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
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.FoodSeasonAdapter;
import com.admin.shopkeeper.adapter.GuazhangDetailAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.GuazhangDetailBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhang.GuaZhangActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangedit.GuazhangEditActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class GuaZhangDetailActivity extends BaseActivity<GuaZhangDetailPresenter> implements IGuaZhangDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private GuaZhangBean bean;
    private GuazhangDetailAdapter adapter;
    private PopupWindow laheiPop;

    @Override
    protected void initPresenter() {
        presenter = new GuaZhangDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gua_zhang_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("挂账详情");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (GuaZhangBean) getIntent().getSerializableExtra("bean");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new GuazhangDetailAdapter(R.layout.item_guazhang_detail);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GuazhangDetailBean bean = (GuazhangDetailBean) adapter.getData().get(position);
                if (bean.getUserIdGuaType().equals("0")){
                    showJieZhang((GuazhangDetailBean) adapter.getData().get(position));
                }
            }
        });
        recyclerView.setAdapter(adapter);

        presenter.getData(bean);
    }

    private void showJieZhang(GuazhangDetailBean bean) {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_delete_edit, null);
        laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        laheiView.findViewById(R.id.pop_cancel).setOnClickListener(v -> {
            laheiPop.dismiss();
        });
        TextView tvJieZhang = (TextView) laheiView.findViewById(R.id.pop_delete);
        tvJieZhang.setText("结账");
        laheiView.findViewById(R.id.pop_delete).setOnClickListener(v -> {
            presenter.jieZhang(bean);
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
        return super.onCreateOptionsMenu(menu);
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
        presenter.getData(bean);
    }

    @Override
    public void success(List<GuazhangDetailBean> datas) {
        adapter.setNewData(datas);
    }
}
