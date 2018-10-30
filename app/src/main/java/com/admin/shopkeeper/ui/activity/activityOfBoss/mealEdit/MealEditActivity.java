package com.admin.shopkeeper.ui.activity.activityOfBoss.mealEdit;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.EditMealTypeAdapter;
import com.admin.shopkeeper.adapter.SelectPrintAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.MealBean;
import com.admin.shopkeeper.entity.MealTypeBean;
import com.bumptech.glide.Glide;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickUtils;

public class MealEditActivity extends BaseActivity<MealEditPresenter> implements IMealEditView, OnPermissionCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_mealtype)
    Spinner foodSpinner;
    @BindView(R.id.edit_image)
    ImageView imageView;
    @BindView(R.id.edit_name)
    EditText etFoodName;
    @BindView(R.id.edit_price)
    EditText etPrice;
    @BindView(R.id.edit_merberprice)
    EditText etMerPrice;
    @BindView(R.id.edit_remark)
    EditText etRemark;
    @BindView(R.id.edit_dazhe)
    RadioGroup rgDazhe;
    @BindView(R.id.edit_show)
    RadioGroup rgShowSet;


    private MealBean foodBean;
    private ArrayAdapter<String> adapter;
    private EditMealTypeAdapter foodTypeAdapter;

    private String imagePath;
    private PermissionHelper permissionHelper;
    private SelectPrintAdapter printAdapter;

    @Override
    protected void initPresenter() {
        presenter = new MealEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meal_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        foodBean = (MealBean) getIntent().getSerializableExtra("food");
        if (foodBean == null) {
            toolbar.setTitle("新增套餐");
            ((RadioButton) rgShowSet.getChildAt(0)).setChecked(true);
            ((RadioButton) rgDazhe.getChildAt(0)).setChecked(true);
        } else {
            toolbar.setTitle("编辑套餐");
            updateView();
        }
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        permissionHelper = PermissionHelper.getInstance(this);

        foodTypeAdapter = new EditMealTypeAdapter(this);
        foodSpinner.setAdapter(foodTypeAdapter);


        presenter.getMealType();
    }

    private void updateView() {
        etFoodName.setText(foodBean.getName());
        etPrice.setText(foodBean.getPrice() + "");
        etRemark.setText(foodBean.getInfo());
        etMerPrice.setText(foodBean.getMemberPice() + "");

        if (foodBean.getCanDiscount() == 0) {
            ((RadioButton) rgDazhe.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgDazhe.getChildAt(1)).setChecked(true);
        }

        if (foodBean.getStatus() == 0) {
            ((RadioButton) rgShowSet.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgShowSet.getChildAt(1)).setChecked(true);
        }

        if (!TextUtils.isEmpty(foodBean.getMealImgName())) {
            Glide.with(this).load(Config.BASE_IMG + App.INSTANCE().getShopID() + "/pay/" + foodBean.getMealImgName()).into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.list_add_image);
        }
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
                if (TextUtils.isEmpty(imagePath)) {
                    doSubmit();
                } else {
                    presenter.editFood(imagePath);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void doSubmit() {
        String foodNameStr = etFoodName.getText().toString().trim();
        if (TextUtils.isEmpty(foodNameStr)) {
            showFailToast("请输入套餐名称");
            etFoodName.requestFocus();
            return;
        }

        String priceStr = etPrice.getText().toString().trim();
        if (TextUtils.isEmpty(priceStr)) {
            showFailToast("请输入套餐价格");
            etPrice.requestFocus();
            return;
        }

        double price = 0;

        try {
            price = Double.parseDouble(priceStr);
            if (price < 0) {
                showFailToast("请输入套餐价格");
                etPrice.requestFocus();
                return;
            }
        } catch (Exception e) {
            showFailToast("请输入套餐价格");
            etPrice.requestFocus();
            return;
        }


        MealTypeBean menuTypeEntity = (MealTypeBean) foodSpinner.getSelectedItem();
        if (menuTypeEntity == null) {
            showFailToast("请选择套餐类别");
            return;
        }

        String remarkStr = etRemark.getText().toString().trim();
        String merPriceStr = etMerPrice.getText().toString().trim();
        double merPrice = 0;
        try {
            if (!TextUtils.isEmpty(merPriceStr)) {
                merPrice = Double.parseDouble(merPriceStr);
            }
        } catch (Exception e) {
            showToast("请输入会员价格");
            return;
        }

        int dazheType = 0;
        if (((RadioButton) rgDazhe.getChildAt(0)).isChecked()) {
            dazheType = 0;
        } else {
            dazheType = 1;
        }

        int showType = 0;
        if (((RadioButton) rgShowSet.getChildAt(0)).isChecked()) {
            showType = 0;
        } else if (((RadioButton) rgShowSet.getChildAt(1)).isChecked()) {
            showType = 1;
        } else {
            showType = 2;
        }
        if (foodBean == null) {
            presenter.submit(menuTypeEntity.getFlag() + "", menuTypeEntity.getGuId(), foodNameStr,
                    price, showType, remarkStr, merPrice, dazheType, imageName);
        } else {
            presenter.change(foodBean.getId(), menuTypeEntity.getFlag() + "", menuTypeEntity.getGuId(), foodNameStr,
                    price, showType, remarkStr, merPrice, dazheType, imageName);
        }
    }

    @OnClick(R.id.edit_selectImage)
    public void selectImageClick() {
        permissionHelper
                .setForceAccepting(false)// true if you had like force reshowing the permission dialog on Deny (not recommended)
                .request(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {//已经预先做了null或size为0的判断
                imagePath = photos.get(0);
                imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {
                imagePath = photos.get(0);
                imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPickFail(String error) {
                showFailToast(error);
                imageView.setVisibility(View.GONE);
            }

            @Override
            public void onPickCancle() {
                showToast("取消选择");
                imageView.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    public void successOfGetType(List<MealTypeBean> menuTypeEntities) {
        foodTypeAdapter.setData(menuTypeEntities);
        foodSpinner.setSelection(0);
        if (foodBean != null) {
            for (int i = 0; i < menuTypeEntities.size(); i++) {
                if (menuTypeEntities.get(i).getGuId().equals(foodBean.getProductTypes())) {
                    foodSpinner.setSelection(i);
                    break;
                }
            }
        } else {
            foodSpinner.setSelection(0);
        }
    }

    String imageName = "";

    @Override
    public void imagesuccess(String imagename) {
        this.imageName = imagename;
        doSubmit();
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {
        PhotoPickUtils.startPick(this, false, 1, new ArrayList<>());
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        showFailToast("权限拒绝");
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {

    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {

    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {

    }

    @Override
    public void onNoPermissionNeeded() {

    }
}
