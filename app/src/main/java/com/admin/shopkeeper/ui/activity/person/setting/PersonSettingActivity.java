package com.admin.shopkeeper.ui.activity.person.setting;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.ui.activity.person.info.PersonActivity;
import com.gyf.barlibrary.ImmersionBar;


import butterknife.BindView;
import butterknife.OnClick;


public class PersonSettingActivity extends BaseActivity<PersonSettingPresenter> implements IPersonSettingView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick({R.id.user_layout})
    void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.user_layout:
                intent = new Intent(PersonSettingActivity.this, PersonActivity.class);
                startActivity(intent);
                break;


        }
    }

    @Override
    protected void initPresenter() {
        presenter = new PersonSettingPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_setting;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("个人设置");
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        ImmersionBar.with(this).destroy();
    }
}
