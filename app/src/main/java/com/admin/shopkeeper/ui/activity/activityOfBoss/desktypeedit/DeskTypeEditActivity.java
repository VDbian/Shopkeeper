package com.admin.shopkeeper.ui.activity.activityOfBoss.desktypeedit;


import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.PersonTypeAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.PersonBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.desktype.IDeskTypeView;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

import butterknife.BindView;

public class DeskTypeEditActivity extends BaseActivity<DeskTypeEditPresenter> implements IDeskTypeEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_type)
    EditText etType;
    @BindView(R.id.edit_people)
    Spinner spinner;

    private DeskTypeBean bean;
    private PersonTypeAdapter personTypeAdapter;

    @Override
    protected void initPresenter() {
        presenter = new DeskTypeEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_desk_type_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (DeskTypeBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改房间类型");

            etName.setText(bean.getName());
            etType.setText(bean.getTypes());
        } else {
            toolbar.setTitle("添加房间类型");
        }
        setSupportActionBar(toolbar);

        personTypeAdapter = new PersonTypeAdapter(this);
        spinner.setAdapter(personTypeAdapter);

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
            showToast("请输入房间名称");
            return;
        }

        String typeStr = etType.getText().toString().trim();
        if (TextUtils.isEmpty(typeStr)) {
            showToast("请输入排序标识");
            return;
        }

        String personId = "";
        String personInfo = "";
        if (spinner.getSelectedItemPosition() >= 0) {
            personId = personTypeAdapter.getItem(spinner.getSelectedItemPosition()).getGuid();
            personInfo = personTypeAdapter.getItem(spinner.getSelectedItemPosition()).getName();
        }

        presenter.commit(bean == null ? "" : bean.getGuid(), personId, personInfo, typeStr, nameStr);
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
    public void personSuccess(List<PersonBean> data) {
        personTypeAdapter.setData(data);
        spinner.setSelection(0);
        if (bean != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getName().equals(bean.getPersonCountInfo())) {
                    spinner.setSelection(i);
                    break;
                }
            }
        } else {
            spinner.setSelection(0);
        }
    }
}
