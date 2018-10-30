package com.admin.shopkeeper.ui.activity.activityOfBoss.deskedit;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.HouseTypeAdapter;
import com.admin.shopkeeper.adapter.PersonTypeAdapter;
import com.admin.shopkeeper.adapter.RoomTypeAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.DeskBean;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.PersonBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.BindView;

public class DeskEditActivity extends BaseActivity<DeskEditPresenter> implements IDeskEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_count)
    EditText etCount;
    @BindView(R.id.edit_sortno)
    EditText etSortNo;
    @BindView(R.id.edit_weixin)
    RadioGroup rgWeixin;
    @BindView(R.id.edit_room)
    Spinner spinner;
    @BindView(R.id.edit_people)
    Spinner personSpinner;

    private DeskBean bean;
    private HouseTypeAdapter houseTypeAdapter;
    private PersonTypeAdapter personTypeAdapter;

    @Override
    protected void initPresenter() {
        presenter = new DeskEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_desk_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (DeskBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改桌位");

            etName.setText(bean.getTableName());
            etSortNo.setText(bean.getSortNo() + "");
            etCount.setText(bean.getPersonCount());

            if (bean.getWeiXinType().equals("1")) {
                ((RadioButton) rgWeixin.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgWeixin.getChildAt(0)).setChecked(false);
            }
        } else {
            toolbar.setTitle("添加桌位");
        }
        setSupportActionBar(toolbar);

        houseTypeAdapter = new HouseTypeAdapter(this);
        spinner.setAdapter(houseTypeAdapter);

        personTypeAdapter = new PersonTypeAdapter(this);
        personSpinner.setAdapter(personTypeAdapter);

        presenter.getRoomData();
        presenter.getPersonData();
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
            showToast("请输入桌位名称");
            return;
        }

        String countStr = etCount.getText().toString().trim();
        if (TextUtils.isEmpty(countStr)) {
            showToast("请输入桌位人数");
            return;
        }

        String sortNoStr = etSortNo.getText().toString().trim();
        if (TextUtils.isEmpty(sortNoStr)) {
            showToast("请输入桌位序号");
            return;
        }

        int weixin = ((RadioButton) rgWeixin.getChildAt(0)).isChecked() ? 1 : 2;


        String roomId = "";
        if (spinner.getSelectedItemPosition() >= 0) {
            roomId = houseTypeAdapter.getItem(spinner.getSelectedItemPosition()).getId();
        }

        String personId = "";
        if (spinner.getSelectedItemPosition() >= 0) {
            personId = personTypeAdapter.getItem(spinner.getSelectedItemPosition()).getGuid();
        }

        presenter.commit(bean == null ? "" : bean.getRoomtableId(), nameStr, countStr, sortNoStr, roomId, personId, weixin);
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
    public void success(List<HouseBean> datas) {
        houseTypeAdapter.setData(datas);
        if (bean != null) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getName().equals(bean.getFangjian())) {
                    spinner.setSelection(i);
                    break;
                }
            }
        } else {
            spinner.setSelection(0);
        }
    }

    @Override
    public void personSuccess(List<PersonBean> datas) {
        personTypeAdapter.setData(datas);
        personSpinner.setSelection(0);
    }
}
