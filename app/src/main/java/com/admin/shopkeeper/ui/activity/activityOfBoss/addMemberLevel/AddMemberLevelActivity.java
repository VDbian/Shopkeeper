package com.admin.shopkeeper.ui.activity.activityOfBoss.addMemberLevel;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.MemberLevelManageBean;
import com.admin.shopkeeper.entity.PrintBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Arrays;

import butterknife.BindView;

public class AddMemberLevelActivity extends BaseActivity<AddMemberLevelPresenter> implements IAddMemberLevelView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.member_level_shop_name)
    TextView etName;
    @BindView(R.id.member_level)
    EditText etMemberLevelName;
    @BindView(R.id.member_level_maximum)
    EditText etMax;
    @BindView(R.id.member_level_minimum)
    EditText etMin;

    private MemberLevelManageBean bean;
    private  String shopName;

    @Override
    protected void initPresenter() {
        presenter = new AddMemberLevelPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_member_level;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (MemberLevelManageBean) getIntent().getSerializableExtra(Config.PARAM2);
        shopName = getIntent().getStringExtra(Config.PARAM1);

        setSupportActionBar(toolbar);
        if(bean != null){
            toolbar.setTitle("编辑会员等级");
            etName.setText(bean.getShopName() + "-只读不可写");
            etMax.setText(bean.getMaxPoints());
            etMin.setText(bean.getMinPoints());
            etMemberLevelName.setText(bean.getName());
        }else {
            toolbar.setTitle("新增会员等级");
            if(shopName != null){
                etName.setText(shopName+ "-只读不可写");
            }else{
                etName.setText(App.INSTANCE().getShopName()+ "-只读不可写");
            }

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
                doSubmit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doSubmit() {
        String levelNameStr = etMemberLevelName.getText().toString().trim();
        if (TextUtils.isEmpty(levelNameStr)) {
            showToast("请输入会员等级");
            return;
        }
        String minStr = etMin.getText().toString().trim();
        if (TextUtils.isEmpty(minStr)) {
            showToast("请输入大于积分数");
            return;
        }
        String maxStr = etMax.getText().toString().trim();
        if (TextUtils.isEmpty(maxStr)) {
            showToast("请输入小于积分数");
            return;
        }
        if(bean != null){

        }
        presenter.submit(bean != null ?bean.getGuid():"", levelNameStr, minStr, maxStr);
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
