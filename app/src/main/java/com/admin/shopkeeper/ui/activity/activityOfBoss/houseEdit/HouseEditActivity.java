package com.admin.shopkeeper.ui.activity.activityOfBoss.houseEdit;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.EditFoodTypeAdapter;
import com.admin.shopkeeper.adapter.RoomTypeAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.RoomTypeBean;
import com.gyf.barlibrary.ImmersionBar;


import java.util.List;

import butterknife.BindView;

public class HouseEditActivity extends BaseActivity<HouseEditPresenter> implements IHouseEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_sortno)
    EditText etSortNo;
    @BindView(R.id.edit_room)
    Spinner roomSpinner;
    @BindView(R.id.edit_area)
    Spinner areaSpinner;
    @BindView(R.id.edit_price)
    EditText etPrice;
    @BindView(R.id.edit_counts)
    EditText etCount;
    @BindView(R.id.edit_state)
    RadioGroup rgState;


    private HouseBean bean;
    private RoomTypeAdapter roomTypeAdapter;

    @Override
    protected void initPresenter() {
        presenter = new HouseEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_house_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (HouseBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改房间");

            etName.setText(bean.getName());
            etSortNo.setText(bean.getSortno());
            etPrice.setText(bean.getPrice());
            etCount.setText(bean.getCounts());
            if (bean.getState() == 1) {
                ((RadioButton) rgState.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgState.getChildAt(1)).setChecked(true);
            }
        } else {
            toolbar.setTitle("添加房间");

            ((RadioButton) rgState.getChildAt(0)).setChecked(true);
        }
        setSupportActionBar(toolbar);

        roomTypeAdapter = new RoomTypeAdapter(this);
        roomSpinner.setAdapter(roomTypeAdapter);

        presenter.getRoomData();
        presenter.getAreaData();
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
            showToast("请输入房间名称");
            return;
        }

        String priceStr = etPrice.getText().toString().trim();
        if (TextUtils.isEmpty(priceStr)) {
            showToast("请输入起订价格");
            return;
        }

        String countStr = etCount.getText().toString().trim();
        if (TextUtils.isEmpty(countStr)) {
            showToast("请输入预定价格");
            return;
        }

        String sortNoStr = etSortNo.getText().toString().trim();
        String roomType = "";
        if (roomSpinner.getSelectedItemPosition() > 0) {
            roomType = roomTypeAdapter.getItem(roomSpinner.getSelectedItemPosition()).getId();
        }

        int state = ((RadioButton) rgState.getChildAt(0)).isChecked() ? 1 : 2;

        presenter.commit(bean == null ? "" : bean.getId(), sortNoStr, state, roomType,
                "", priceStr, countStr, nameStr);
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
    public void success(List<RoomTypeBean> data) {
        roomTypeAdapter.setData(data);
        if (bean != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId().equals(bean.getRoomTypeID())) {
                    roomSpinner.setSelection(i);
                    break;
                }
            }
        } else {
            roomSpinner.setSelection(0);
        }
    }
}
