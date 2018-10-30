package com.admin.shopkeeper.ui.activity.config.id;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.utils.HideUtils;
import com.admin.shopkeeper.weight.CenterTitleToolbar;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ConfigIDActivity extends BaseActivity<ConfigIDPresenter> implements IConfigIDView {


    @BindView(R.id.toolbar)
    CenterTitleToolbar toolbar;

    @BindView(R.id.edit)
    AppCompatEditText editText;


    @OnClick(R.id.btn)
    void onClick() {
        String idStr = editText.getText().toString();
        if (TextUtils.isEmpty(idStr)) {
            return;
        }else{
            idStr = idStr.replaceAll(" ","");
        }
        presenter.saveID(idStr);
    }

    @Override
    protected void initPresenter() {
        presenter = new ConfigIDPresenter(this, this);
        presenter.init();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_id;
    }

    @Override
    public void initView() {
        HideUtils.init(this);
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("系统配置");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        presenter.getDB();
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
    }

    @Override
    public void saveSuccess() {
        Toasty.success(ConfigIDActivity.this, "保存成功", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void show(String id) {

        editText.setText(id);
    }
}
