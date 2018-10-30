package com.admin.shopkeeper.ui.activity.person.info;

import android.content.DialogInterface;
import android.support.annotation.BinderThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.KeyboardPatch;


import butterknife.BindView;
import butterknife.OnClick;

public class PersonActivity extends BaseActivity<PersonPresenter> implements IPersonView {


    @BindView(R.id.gender)
    AppCompatTextView gender;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    CoordinatorLayout root;

    @OnClick({R.id.sex_layout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.sex_layout:

                AlertDialog.Builder builder = new AlertDialog.Builder(PersonActivity.this);
                builder.setSingleChoiceItems(R.array.genderArray, TextUtils.isEmpty(gender.getText().toString().trim()) ? -1 : gender.getText().toString().trim().equals("男") ? 0 : 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender.setText(getResources().getStringArray(R.array.genderArray)[which]);


                        dialog.dismiss();
                    }
                });

                builder.show();

                break;
        }
    }

    @Override
    protected void initPresenter() {

        presenter = new PersonPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        KeyboardPatch.patch(this, root).enable();
        toolbar.setTitle("个人信息");
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
