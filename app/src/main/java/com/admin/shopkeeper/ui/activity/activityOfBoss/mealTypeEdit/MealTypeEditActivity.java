package com.admin.shopkeeper.ui.activity.activityOfBoss.mealTypeEdit;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.MealTypeBean;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class MealTypeEditActivity extends BaseActivity<MealTypeEditPresenter> implements IMealTypeEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etFoodName;
    @BindView(R.id.edit_sortno)
    EditText etSortNo;
    private MealTypeBean foodBean;

    @Override
    protected void initPresenter() {
        presenter = new MealTypeEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meal_type_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        foodBean = (MealTypeBean) getIntent().getSerializableExtra("food");
        if (foodBean == null) {
            toolbar.setTitle("新增套餐类别");
        } else {
            toolbar.setTitle("编辑套餐类别");
            updateView();
        }
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);
    }

    private void updateView() {
        etFoodName.setText(foodBean.getProductTypeName());
        etSortNo.setText(foodBean.getSortNum()+"");
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
                doSubmit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void doSubmit() {
        String foodNameStr = etFoodName.getText().toString().trim();
        if (TextUtils.isEmpty(foodNameStr)) {
            showFailToast("请输入套餐类别名称");
            etFoodName.requestFocus();
            return;
        }

        String sortNoStr = etSortNo.getText().toString().trim();
        if (TextUtils.isEmpty(sortNoStr)) {
            showFailToast("请输入套餐类别编号");
            etSortNo.requestFocus();
            return;
        }

        if (foodBean == null) {
            presenter.submit(foodNameStr , sortNoStr);
        } else {
            presenter.change(foodNameStr , sortNoStr , foodBean.getGuId());
//            presenter.submit( foodBean.getProductId(), foodNameStr, idStr, pinyinStr, unitStr, minUnitStr,
//                    menuTypeEntity.getProductTypeID(), menuTypeEntity.getProductTypeName(), price, imageName, printBean == null ? "" : printBean.getId(), state, remarkStr, "",
//                    count, warcount, chucaiType, merPrice, position + 1, dazheType,shuxing, showType);
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


}
