package com.admin.shopkeeper.ui.activity.activityOfBoss.mansongedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MansongBean;
import com.admin.shopkeeper.ui.activity.activityOfBoss.kouweiedit.IKouweiEditView;
import com.admin.shopkeeper.ui.activity.activityOfBoss.kouweiedit.KouweiEditPresenter;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;
import com.gyf.barlibrary.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MansongEditActivity extends BaseActivity<MansongEditPresenter> implements IMansongEditView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_name)
    EditText etName;
    @BindView(R.id.item_tiaojian)
    EditText etTiaojian;
    @BindView(R.id.item_money)
    EditText etMoney;
    @BindView(R.id.item_starttime)
    TextView tvStart;
    @BindView(R.id.item_endtime)
    TextView tvEnd;
    @BindView(R.id.item_type)
    TextView tvType;
    @BindView(R.id.item_enable)
    RadioGroup rgEnable;
    private FoodBean foodBean;
    private MansongBean bean;

    @Override
    protected void initPresenter() {
        presenter = new MansongEditPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mansong_edit;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        toolbar.setTitle("添加满送规则");
        setSupportActionBar(toolbar);

        bean = (MansongBean) getIntent().getSerializableExtra("bean");
        if (bean == null) {
            toolbar.setTitle("添加满送规则");

            ((RadioButton) rgEnable.getChildAt(0)).setChecked(true);
        } else {
            toolbar.setTitle("修改满送规则");

            etName.setText(bean.getName());
            etTiaojian.setText(String.valueOf(bean.getTiaojian()));
            etMoney.setText(String.valueOf(bean.getMoney()));
            tvStart.setText(bean.getbTime().replace("00:00:00","").trim());
            tvEnd.setText(bean.geteTime().replace("23:59:59","").trim());
            if (TextUtils.isEmpty(bean.getApply())) {
                tvType.setText("预定");
            } else if (bean.getApply().equals("1")) {
                tvType.setText("预定");
            } else if (bean.getApply().equals("3")) {
                tvType.setText("外卖");
            } else if (bean.getApply().equals("4")) {
                tvType.setText("快餐");
            } else if (bean.getApply().equals("5")) {
                tvType.setText("扫码点餐");
            }

            if (bean.getIsBegin().equals("1")) {
                ((RadioButton) rgEnable.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgEnable.getChildAt(1)).setChecked(true);
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
                submit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        String nameStr = etName.getText().toString();
        String tiaojianStr = etTiaojian.getText().toString();
        String moneyStr = etMoney.getText().toString();
        String startStr = tvStart.getText().toString();
        String entStr = tvEnd.getText().toString();
        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入满送规则名称");
            return;
        }

        if (TextUtils.isEmpty(tiaojianStr)) {
            showToast("请输入满送条件");
            return;
        }
        if (TextUtils.isEmpty(moneyStr)) {
            showToast("请输入满送金额");
            return;
        }

        if (TextUtils.isEmpty(startStr)) {
            showToast("请选择开始日期");
            return;
        }

        if (TextUtils.isEmpty(entStr)) {
            showToast("请选择结束日期");
            return;
        }

        int enable = ((RadioButton) rgEnable.getChildAt(0)).isChecked() ? 1 : 2;

        String typeStr = tvType.getText().toString();
        int type = 1;
        if (TextUtils.isEmpty(typeStr)) {
            showToast("请选择适用类型");
            return;
        }

        if (typeStr.equals("预定")) {
            type = 1;
        } else if (typeStr.equals("外卖")) {
            type = 3;
        } else if (typeStr.equals("扫码")) {
            type = 4;
        } else if (typeStr.equals("点餐")) {
            type = 5;
        }
        presenter.commit(bean == null ? "" : bean.getGuid(), nameStr, tiaojianStr, moneyStr, startStr, entStr, type, enable);
    }

    @OnClick(R.id.item_starttime)
    public void startClick() {
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setYearLimt(10);
        dialog.setTitle("选择时间");
        dialog.setType(DateType.TYPE_YMD);
        dialog.setMessageFormat("yyyy-MM-dd");
        dialog.setOnChangeLisener(null);
        dialog.setOnSureLisener(date -> {
            tvStart.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
        });
        dialog.show();
    }

    @OnClick(R.id.item_endtime)
    public void endClick() {
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setYearLimt(10);
        dialog.setTitle("选择时间");
        dialog.setType(DateType.TYPE_YMD);
        dialog.setMessageFormat("yyyy-MM-dd");
        dialog.setOnChangeLisener(null);
        dialog.setOnSureLisener(date -> {
            tvEnd.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
        });
        dialog.show();
    }

    @OnClick(R.id.item_type)
    public void typeClick() {
        List<String> types = new ArrayList<>();
        types.add("预定");
        types.add("外卖");
        types.add("快餐");
        types.add("扫码点餐");

        SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("试用类型");
        builder.setReasons(types);
        builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, int position) {
                tvType.setText(text);
            }

            @Override
            public void onCancel() {
                tvType.setText("");
            }
        });
        builder.creater().show();
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
