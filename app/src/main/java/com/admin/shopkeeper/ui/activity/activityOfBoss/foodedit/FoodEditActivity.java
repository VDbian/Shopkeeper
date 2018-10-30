package com.admin.shopkeeper.ui.activity.activityOfBoss.foodedit;

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

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.EditFoodTypeAdapter;
import com.admin.shopkeeper.adapter.SelectPrintAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.PrintBean;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickUtils;

public class FoodEditActivity extends BaseActivity<FoodEditPresenter> implements IFoodEditView, OnPermissionCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_saletype)
    Spinner spinner;
    @BindView(R.id.edit_foodtype)
    Spinner foodSpinner;
    @BindView(R.id.edit_image)
    ImageView imageView;
    @BindView(R.id.edit_foodname)
    EditText etFoodName;
    /*@BindView(R.id.edit_pinyin)
    EditText etPinyin;*/
    @BindView(R.id.edit_unit)
    EditText etUnit;
    /*@BindView(R.id.edit_minunit)
    EditText etMinUnit;*/
    @BindView(R.id.edit_price)
    EditText etPrice;
    @BindView(R.id.edit_remark)
    EditText etRemark;
    /*@BindView(R.id.edit_count)
    EditText etCount;*/
    /*@BindView(R.id.edit_warcount)
    EditText etWarCount;*/
    @BindView(R.id.edit_merberprice)
    EditText etMerPrice;
    @BindView(R.id.edit_state)
    RadioGroup rgState;
    @BindView(R.id.edit_print)
    RadioGroup rgPrint;
    @BindView(R.id.edit_dazhe)
    RadioGroup rgDazhe;
    @BindView(R.id.edit_show)
    RadioGroup rgShowSet;
    @BindView(R.id.edit_shuxing)
    RadioGroup rgShuxing;
    @BindView(R.id.edit_id)
    EditText etId;
    @BindView(R.id.edit_selectprint)
    Spinner printSpinner;

    private FoodBean foodBean;
    private ArrayAdapter<String> adapter;
    private EditFoodTypeAdapter foodTypeAdapter;

    private String imagePath;
    private PermissionHelper permissionHelper;
    private SelectPrintAdapter printAdapter;

    @Override
    protected void initPresenter() {
        presenter = new FoodEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_food_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        foodBean = (FoodBean) getIntent().getSerializableExtra("food");
        if (foodBean == null) {
            toolbar.setTitle("添加菜品");
            ((RadioButton) rgState.getChildAt(0)).setChecked(true);
            ((RadioButton) rgShowSet.getChildAt(0)).setChecked(true);
            ((RadioButton) rgDazhe.getChildAt(0)).setChecked(true);
            ((RadioButton) rgPrint.getChildAt(0)).setChecked(true);
            ((RadioButton) rgShuxing.getChildAt(0)).setChecked(true);
        } else {
            toolbar.setTitle("编辑菜品");
            updateView();
        }
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        permissionHelper = PermissionHelper.getInstance(this);

        String[] saleTypes = getResources().getStringArray(R.array.saleType);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(saleTypes));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (foodBean != null) {
            spinner.setSelection(foodBean.getSalesType() - 1);
        }

        foodTypeAdapter = new EditFoodTypeAdapter(this);
        foodSpinner.setAdapter(foodTypeAdapter);

        printAdapter = new SelectPrintAdapter(this);
        printSpinner.setAdapter(printAdapter);

        presenter.getFoodType();
        presenter.getPrintData();
    }

    private void updateView() {
        etFoodName.setText(foodBean.getProductName());
        etId.setText(foodBean.getId());
//        etPinyin.setText(foodBean.getPinyin());
        etUnit.setText(foodBean.getUnit());
//        etMinUnit.setText(foodBean.getMinunit());
        etPrice.setText(foodBean.getPrice() + "");
        etRemark.setText(foodBean.getRemark());
//        etCount.setText(foodBean.getProductCount() + "");
        etMerPrice.setText(foodBean.getMemberPice() + "");
//        etWarCount.setText(foodBean.getWarCount());

        if (foodBean.getState() == 1) {
            ((RadioButton) rgState.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgState.getChildAt(1)).setChecked(true);
        }

        if (foodBean.getChuCaiType().equals("0")) {
            ((RadioButton) rgPrint.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgPrint.getChildAt(1)).setChecked(true);
        }

        if (foodBean.getCanDiscount() == 0) {
            ((RadioButton) rgDazhe.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgDazhe.getChildAt(1)).setChecked(true);
        }

        if (foodBean.getAccordIng().equals("0")) {
            ((RadioButton) rgShowSet.getChildAt(0)).setChecked(true);
        } else if (foodBean.getAccordIng().equals("1")) {
            ((RadioButton) rgShowSet.getChildAt(1)).setChecked(true);
        } else {
            ((RadioButton) rgShowSet.getChildAt(2)).setChecked(true);
        }

        if (foodBean.getProtuctShuXing() == null || foodBean.getProtuctShuXing().equals("0")) {
            ((RadioButton) rgShuxing.getChildAt(0)).setChecked(true);
        } else if (foodBean.getProtuctShuXing().equals("1")) {
            ((RadioButton) rgShuxing.getChildAt(1)).setChecked(true);
        } else {
            ((RadioButton) rgShuxing.getChildAt(2)).setChecked(true);
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
        String idStr = etId.getText().toString().trim();
        if (TextUtils.isEmpty(idStr)) {
            showFailToast("请输入商品ID");
            etId.requestFocus();
            return;
        }

        String foodNameStr = etFoodName.getText().toString().trim();
        if (TextUtils.isEmpty(foodNameStr)) {
            showFailToast("请输入商品名称");
            etFoodName.requestFocus();
            return;
        }
        /*String pinyinStr ="";
        StringBuffer sb = new StringBuffer();
        if(!TextUtils.isEmpty(foodNameStr)){
            pinyinStr = Tools.convertHanzi2Pinyin(foodNameStr,false);
            sb.append(pinyinStr);
            sb.append(foodNameStr);
        }
        pinyinStr = sb.toString();*/


        String unitStr = etUnit.getText().toString().trim();
        if (TextUtils.isEmpty(unitStr)) {
            showFailToast("请输入商品单位");
            etUnit.requestFocus();
            return;
        }

        /*String minUnitStr = etMinUnit.getText().toString().trim();
        if (TextUtils.isEmpty(minUnitStr)) {
            showFailToast("请输入商品最小单位");
            etMinUnit.requestFocus();
            return;
        }*/

        String priceStr = etPrice.getText().toString().trim();
        if (TextUtils.isEmpty(priceStr)) {
            showFailToast("请输入商品价格");
            etPrice.requestFocus();
            return;
        }

        double price = 0;

        try {
            price = Double.parseDouble(priceStr);
            if (price < 0) {
                showFailToast("请输入商品价格");
                etPrice.requestFocus();
                return;
            }
        } catch (Exception e) {
            showFailToast("请输入商品价格");
            etPrice.requestFocus();
            return;
        }


        MenuTypeEntity menuTypeEntity = (MenuTypeEntity) foodSpinner.getSelectedItem();
        if (menuTypeEntity == null) {
            showFailToast("请选择商品类别");
            return;
        }

        int position = spinner.getSelectedItemPosition();
        if (position < 0) {
            showFailToast("请选择促销类别");
            return;
        }
        int state = 0;
        if (((RadioButton) rgState.getChildAt(0)).isChecked()) {
            state = 1;
        } else {
            state = 2;
        }

        String remarkStr = etRemark.getText().toString().trim();

//        String countStr = etCount.getText().toString().trim();
//        int count = 0;
//        try {
//            if (!TextUtils.isEmpty(countStr)) {
//                count = Integer.parseInt(countStr);
//            }
//        } catch (Exception e) {
//            showToast("请输入菜品库存");
//            return;
//        }
        /*String warcountStr;
        if(TextUtils.isEmpty(etWarCount.getText().toString().trim())){
            warcountStr = etWarCount.getHint().toString().trim();
        }else{
            warcountStr = etWarCount.getText().toString().trim();
        }*/

       /* int warcount = 0;
        try {
            if (!TextUtils.isEmpty(warcountStr)) {
                warcount = Integer.parseInt(warcountStr);
            }
        } catch (Exception e) {
            showToast("请输入预警份数");
            return;
        }*/

        int chucaiType = 0;
        if (((RadioButton) rgPrint.getChildAt(0)).isChecked()) {
            chucaiType = 0;
        } else {
            chucaiType = 1;
        }

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

        int shuxing = 0;
        if (((RadioButton) rgShuxing.getChildAt(0)).isChecked()) {
            shuxing = 0;
        } else if (((RadioButton) rgShuxing.getChildAt(1)).isChecked()) {
            shuxing = 1;
        } else {
            shuxing = 2;
        }

        PrintBean printBean = (PrintBean) printSpinner.getSelectedItem();

        if (foodBean == null) {
            presenter.submit("ADD", "", foodNameStr, idStr, "", unitStr, "", menuTypeEntity.getProductTypeID(),
                    menuTypeEntity.getProductTypeName(), price, imageName, printBean == null ? "" : printBean.getId(), state, remarkStr, "", "", "", chucaiType,
                    merPrice, position + 1, dazheType,shuxing, showType);
        } else {
            presenter.submit("Update", foodBean.getProductId(), foodNameStr, idStr, "", unitStr, "",
                    menuTypeEntity.getProductTypeID(), menuTypeEntity.getProductTypeName(), price, imageName, printBean == null ? "" : printBean.getId(), state, remarkStr, "",
                    "", "", chucaiType, merPrice, position + 1, dazheType,shuxing, showType);
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
    public void getPrintSuccess(List<PrintBean> datas) {
        printAdapter.setData(datas);
        if (foodBean != null) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getId().equals(foodBean.getPrintId())) {
                    printSpinner.setSelection(i);
                    break;
                }
            }
        } else {
            printSpinner.setSelection(0);
        }
    }

    @Override
    public void getFoodSuccess(List<MenuTypeEntity> menuTypeEntities) {
        foodTypeAdapter.setData(menuTypeEntities);
        foodSpinner.setSelection(0);
        if (foodBean != null) {
            for (int i = 0; i < menuTypeEntities.size(); i++) {
                if (menuTypeEntities.get(i).getProductTypeID().equals(foodBean.getProductTypeId())) {
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
