package com.admin.shopkeeper.ui.activity.activityOfBoss.returnanalysis;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.detailbussiness.DetailBussinessActivity;
import com.admin.shopkeeper.ui.activity.activityOfBoss.returnbussiness.ReturnBussinessActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

public class ReturnAnalysisActivity extends BaseActivity<ReturnAnalysisPresenter> implements IReturnAnalysisView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initPresenter() {
        presenter = new ReturnAnalysisPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_return_analysis;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("退菜分析");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

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

    @OnClick(R.id.return_all)
    public void allClick() {
        startActivity(ReturnBussinessActivity.class);
    }

    @OnClick(R.id.return_detaile)
    public void detailClick() {
        startActivity(DetailBussinessActivity.class);
    }
}
