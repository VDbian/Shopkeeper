package com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangedit;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class GuazhangEditActivity extends BaseActivity<GuaZhangEditPresenter> implements IGuaZhangEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_name)
    EditText etName;
    @BindView(R.id.edit_phone)
    EditText etPhone;
    @BindView(R.id.edit_remark)
    EditText etRemark;

    private GuaZhangBean bean;

    @Override
    protected void initPresenter() {
        presenter = new GuaZhangEditPresenter(this,this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guazhang_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (GuaZhangBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改挂账人");

            etName.setText(bean.getName());
            etPhone.setText(bean.getPhone());
            etRemark.setText(bean.getRemark());
        } else {
            toolbar.setTitle("添加挂账人");
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
            showToast("请输入挂账人名称");
            return;
        }

        String phoneStr = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr)) {
            showToast("请输入挂账人电话");
            return;
        }

        String remarkStr = etRemark.getText().toString().trim();

        presenter.commit(bean == null ? "" : bean.getGuid(), nameStr, phoneStr, remarkStr);
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
