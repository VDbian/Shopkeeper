package com.admin.shopkeeper.ui.activity.activityOfBoss.edit;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.RetCauseBean;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class EditActivity extends BaseActivity<EditPresenter> implements IEditView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editText)
    EditText editText;
    private RetCauseBean bean;

    @Override
    protected void initPresenter() {
        presenter = new EditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (RetCauseBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改退菜原因");

            editText.setText(bean.getRemark());
            editText.setSelection(editText.length());
        } else {
            toolbar.setTitle("添加退菜原因");
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
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            showToast("请输入退菜原因");
            return;

        }
        if (bean != null) {
            presenter.modify(bean, text);
        } else {
            presenter.submit(text);
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
