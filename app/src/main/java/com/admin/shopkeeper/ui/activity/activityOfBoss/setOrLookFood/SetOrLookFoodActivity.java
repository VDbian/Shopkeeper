package com.admin.shopkeeper.ui.activity.activityOfBoss.setOrLookFood;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.SetOrLookFoodAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.FindFoodCouponDialog;
import com.admin.shopkeeper.dialog.SetFoodCouponDialog;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.ShopBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class SetOrLookFoodActivity extends BaseActivity<SetOrLookFoodPresenter> implements ISetOrLookFoodView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SetOrLookFoodAdapter setOrLookFoodAdapter;
    private PopupWindow laheiPop;
    private CouponLineDownBean bean;
    private String[] shopName;
    List shopNameList = new ArrayList();
    List<FoodBean> foods;
    List<MenuTypeEntity> types;
    List<FoodBean> selectFoods;
    List<FindFoodCouponDownBean> selectedFoods = new ArrayList<>();
    List<MenuTypeEntity> selectFoodTypes;
    int page = 1;
    private FindFoodCouponDialog.Builder builder;

    @Override
    protected void initPresenter() {
        presenter = new SetOrLookFoodPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_or_look_food;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("设置或查看设置商品");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        bean = (CouponLineDownBean) getIntent().getSerializableExtra("bean");
        if (bean.getMerchantName() != null) {
            shopName = bean.getMerchantName().split(",");
        }
        presenter.getFood();
        presenter.getFoodType();
        //presenter.getSetFood(1, "", bean.getGuid());
        for (int i = 0; i < shopName.length; i++) {
            ShopBean shopBean = new ShopBean();
            shopBean.setName(shopName[i]);
            shopNameList.add(shopBean);
        }
        bean.getShopId();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(getResources().getColor(R.color.item_line_color))
                .build());
        setOrLookFoodAdapter = new SetOrLookFoodAdapter(R.layout.item_set_or_look_food);
        recyclerView.setAdapter(setOrLookFoodAdapter);
        if (shopNameList != null) {
            setOrLookFoodAdapter.setNewData(shopNameList);
        }
        setOrLookFoodAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_set_food) {
                    if (foods == null || foods.size() == 0 || types == null || types.size() == 0) {
                        presenter.getFood();
                        presenter.getFoodType();
                    } else {
                        SetFoodCouponDialog.Builder builder = new SetFoodCouponDialog.Builder(SetOrLookFoodActivity.this, foods, types, R.style.setFoodDialogStyle);
                        builder.setButtonClick(new SetFoodCouponDialog.OnButtonClick() {

                            @Override
                            public void onBtnClick(List<MenuTypeEntity> typeEntityList, List<FoodBean> foodBeanList) {
                                String typeIdString = "";
                                for (MenuTypeEntity bean : typeEntityList) {
                                    typeIdString += bean.getProductTypeID() + ",";
                                }
                                typeIdString = typeIdString.substring(0, typeIdString.length() - 1);

                                String foodIdString = "";
                                for (FoodBean bean : foodBeanList) {
                                    foodIdString += bean.getProductId() + ",";
                                }
                                foodIdString = foodIdString.substring(0, foodIdString.length() - 1);
                                presenter.save(foodIdString, typeIdString, bean.getGuid());
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        builder.creater().show();
                    }
                } else if (view.getId() == R.id.item_look_food) {
//                    if (selectedFoods == null || selectedFoods.size() == 0) {
//                        presenter.getSetFood(1, "", bean.getGuid());
//                    } else {
                    builder = new FindFoodCouponDialog.Builder(SetOrLookFoodActivity.this, R.style.setFoodDialogStyle, presenter);
                    builder.setOnRefreshListener(new FindFoodCouponDialog.OnRefreshListener() {

                        @Override
                        public void onRefresh() {
                            presenter.getSetFood(1, "", bean.getGuid());
                        }

                        @Override
                        public void onLoadMore() {
                            page++;
                            presenter.getSetFood(page, "", bean.getGuid());
                        }
                    });

                    builder.creater().show();
                    presenter.getSetFood(1, "", bean.getGuid());
                    //}

                }
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            presenter.getData();
        }
    }

    @Override
    public void error(String msg) {
        showFailToast(msg);
        if (builder != null) {
//            builder.refreshLayout.setRefreshing(false);
//            builder.adapter.loadMoreEnd();
            builder.errorRefresh();
        }
    }


    @Override
    public void success(String msg) {
        showSuccessToast(msg);
        if (laheiPop != null) {
            laheiPop.dismiss();
        }
    }

    @Override
    public void success(List<FindFoodCouponDownBean> datas) {
        if (page == 1) {
            selectedFoods.clear();
        }
        selectedFoods.addAll(datas);
        if (selectedFoods.size() == 0) {
            Toasty.warning(SetOrLookFoodActivity.this, "没有设置的商品").show();
        }

        if (builder != null) {
            builder.setDatas(selectedFoods);
//            builder.adapter.setNewData(selectedFoods);
//            builder.refreshLayout.setRefreshing(false);
//            if (selectFoods.size() < 20) {
//                builder.adapter.loadMoreEnd();
//            } else {
//                builder.adapter.loadMoreComplete();
//            }
        }
    }

    @Override
    public void getFoodTypeSuccess(List<MenuTypeEntity> foodTypes) {
        this.types = new ArrayList<>(foodTypes);
        MenuTypeEntity entity = new MenuTypeEntity();
        entity.setProductTypeName("全选");
        types.add(0, entity);
    }

    @Override
    public void getFoodSuccess(List<FoodBean> foods) {
        this.foods = new ArrayList<>(foods);
        FoodBean bean = new FoodBean();
        bean.setProductName("全选");
        this.foods.add(0, bean);
    }


}
