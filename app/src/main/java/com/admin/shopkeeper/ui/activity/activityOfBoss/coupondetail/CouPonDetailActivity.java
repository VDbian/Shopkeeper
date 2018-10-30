package com.admin.shopkeeper.ui.activity.activityOfBoss.coupondetail;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.CouponManagerAdapter;
import com.admin.shopkeeper.adapter.GuizeAdapter;
import com.admin.shopkeeper.adapter.ShopAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.ShopSelectDialog;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.GuizeBean;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.addCoupon.AddCouponActivity;
import com.admin.shopkeeper.weight.FullyLinearLayoutManager;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CouPonDetailActivity extends BaseActivity<CouponDetailPresenter> implements ICouponDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_man)
    TextView tvMan;
    @BindView(R.id.tv_give)
    TextView tvGive;
    @BindView(R.id.tv_jifen)
    TextView tvJifen;
    @BindView(R.id.tv_starttime)
    TextView tvStart;
    @BindView(R.id.tv_enttime)
    TextView tvEnd;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_pici)
    TextView tvPici;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.listview)
    RecyclerView listView;

    private String titleStr;
    private CouponManageBean bean;
    private ShopAdapter adapter;
    private GuizeAdapter guizeAdapter;

    @Override
    protected void initPresenter() {
        presenter = new CouponDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cou_pon_detail;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        titleStr = getIntent().getStringExtra(Config.PARAM1);
        bean = (CouponManageBean) getIntent().getSerializableExtra(Config.PARAM2);
        toolbar.setTitle("券详情页面");

        tvPici.setText(bean.getPiCi());
        tvName.setText(bean.getName());
        tvMan.setText(String.valueOf(bean.getSumPrice()));
        tvGive.setText(String.valueOf(bean.getPrice()));
        tvJifen.setText(String.valueOf(bean.getCode()));
        tvStart.setText(bean.getBeginTime());
        tvEnd.setText(bean.getEndTime());
        tvAmount.setText(bean.getCounts());

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        guizeAdapter = new GuizeAdapter(R.layout.item_guize);
        listView.setAdapter(guizeAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._1sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        adapter = new ShopAdapter(R.layout.item_shop);
        recyclerView.setAdapter(adapter);

        presenter.getShopDetail(bean);
        presenter.getDetail(bean);
    }

    @OnClick(R.id.btn_edit)
    public void editClick() {
        Intent intent = new Intent(this, AddCouponActivity.class);
        intent.putExtra(Config.PARAM2, bean);
        intent.putExtra(Config.PARAM1, titleStr);
        startActivityForResult(intent, 101);
    }

    @OnClick(R.id.btn_setshop)
    public void shopClick() {
        presenter.getShopData(bean);
    }

    @OnClick(R.id.btn_delete)
    public void deleteClick() {
        presenter.deleteCoupon(bean.getPiCi());
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
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void deleteSuccess(String msg) {
        showSuccessToast(msg);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void shopsuccess(CouponManageBean bean, List<ShopBean> shopBeen) {
        if (shopBeen == null || shopBeen.size() == 0) {
            showFailToast("暂无商家列表");
            return;
        }

        for (int i = 0; i < shopBeen.size(); i++) {
            if (bean.getMerchantID().contains(shopBeen.get(i).getGuid())) {
                shopBeen.get(i).setSelect(true);
            }
        }

        ShopSelectDialog.Builder builder = new ShopSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("设置商家");
        builder.setReasons(shopBeen);
        builder.setButtonClick(new ShopSelectDialog.OnButtonClick() {


            @Override
            public void onOk(String text, String value) {
                presenter.setShopData(bean, value);
            }
        });
        builder.creater().show();
    }

    @Override
    public void shopdetail(List<ShopBean> shopBeen) {
        adapter.setNewData(shopBeen);
    }

    @Override
    public void showDetail(List<GuizeBean> list) {
        guizeAdapter.setNewData(list);
    }
}
