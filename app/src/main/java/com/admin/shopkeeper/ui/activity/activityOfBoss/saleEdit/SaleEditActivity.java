package com.admin.shopkeeper.ui.activity.activityOfBoss.saleEdit;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.SelectFoodDialog;
import com.admin.shopkeeper.dialog.SelectFoodTypeDialog;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.SaleBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SaleEditActivity extends BaseActivity<SaleEditPresenter> implements ISaleEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_count)
    EditText etCount;
    @BindView(R.id.tv_food)
    TextView tvFood;
    @BindView(R.id.tv_foodtype)
    TextView tvFoodType;

    private SaleBean bean;
    List<FoodBean> foods;
    List<MenuTypeEntity> types;
    List<FoodBean> selectFoods;
    List<MenuTypeEntity> selectFoodTypes;

    @Override
    protected void initPresenter() {
        presenter = new SaleEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sale_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (SaleBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改打折");

            etName.setText(bean.getName());
            etCount.setText(bean.getCount());
        } else {
            toolbar.setTitle("添加打折");
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                submit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        String nameStr = etName.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入打折名称");
            return;
        }

        String countStr = etCount.getText().toString().trim();
        if (TextUtils.isEmpty(countStr)) {
            showToast("请输入打折数量");
            return;
        }
        String ptypeid = "";
        String pid1 = "";
        if (selectFoods != null && selectFoods.size() > 0) {
            if (selectFoods.get(0).getProductName().equals("全选")) {
                for (int i = 1; i < selectFoods.size(); i++) {
                    pid1 += selectFoods.get(i).getId() + ",";
                }
            } else {
                for (FoodBean bean : selectFoods) {
                    pid1 += bean.getId() + ",";
                }
            }

            pid1 = pid1.substring(0, pid1.length() - 1);
        }

        if (selectFoodTypes != null && selectFoodTypes.size() > 0) {
            if (selectFoodTypes.get(0).getProductTypeName().equals("全选")) {
                for (int i = 1; i < selectFoodTypes.size(); i++) {
                    ptypeid += selectFoodTypes.get(i).getProductTypeID() + ",";
                }
            } else {
                for (MenuTypeEntity bean : selectFoodTypes) {
                    ptypeid += bean.getProductTypeID() + ",";
                }
            }

            ptypeid = ptypeid.substring(0, ptypeid.length() - 1);
        }

        Log.i("ttt", "pid:" + pid1);
        Log.i("ttt", "ptypeid:" + ptypeid);

        presenter.commit(bean == null ? "" : bean.getGuiId(), nameStr, countStr, ptypeid, pid1);
    }

    @OnClick(R.id.tv_food)
    public void foodClick() {
        if (foods == null || foods.size() == 0) {
            presenter.getFood();
        } else {
            SelectFoodDialog.Builder builder = new SelectFoodDialog.Builder(this, R.style.OrderDialogStyle);
            builder.setTitle("选择商品");
            builder.setDatas(foods);
            builder.setButtonClick(new SelectFoodDialog.OnButtonClick() {

                @Override
                public void onOk(List<FoodBean> list) {
                    selectFoods = list;
                    if (selectFoods == null || selectFoods.size() == 0) {
                        tvFood.setText("请选择商品");
                    } else if (selectFoods.get(0).getProductName().equals("全选")) {
                        tvFood.setText("全选");
                    } else {
                        String names = "";
                        for (FoodBean bean : selectFoods) {
                            names += bean.getProductName() + ",";
                        }

                        tvFood.setText(names.substring(0, names.length() - 1));
                    }
                }
            });
            builder.creater().show();
        }
    }

    @OnClick(R.id.tv_foodtype)
    public void foodTypeClick() {
        if (types == null || types.size() == 0) {
            presenter.getFoodType();
        } else {
            SelectFoodTypeDialog.Builder builder = new SelectFoodTypeDialog.Builder(this, R.style.OrderDialogStyle);
            builder.setTitle("选择商品");
            builder.setDatas(types);
            builder.setButtonClick(new SelectFoodTypeDialog.OnButtonClick() {

                @Override
                public void onOk(List<MenuTypeEntity> list) {
                    selectFoodTypes = list;
                    if (selectFoodTypes == null || selectFoodTypes.size() == 0) {
                        tvFoodType.setText("请选择商品类型");
                    } else if (selectFoodTypes.get(0).getProductTypeName().equals("全选")) {
                        tvFoodType.setText("全选");
                    } else {
                        String names = "";
                        for (MenuTypeEntity bean : selectFoodTypes) {
                            names += bean.getProductTypeName() + ",";
                        }

                        tvFoodType.setText(names.substring(0, names.length() - 1));
                    }
                }
            });
            builder.creater().show();
        }
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
    public void getFoodTypeSuccess(List<MenuTypeEntity> foodTypes) {
        this.types = new ArrayList<>(foodTypes);
        MenuTypeEntity entity = new MenuTypeEntity();
        entity.setProductTypeName("全选");
        types.add(0, entity);
        foodTypeClick();
    }

    @Override
    public void getFoodSuccess(List<FoodBean> foods) {
        this.foods = new ArrayList<>(foods);
        FoodBean bean = new FoodBean();
        bean.setProductName("全选");
        this.foods.add(0, bean);
        foodClick();
    }

}
