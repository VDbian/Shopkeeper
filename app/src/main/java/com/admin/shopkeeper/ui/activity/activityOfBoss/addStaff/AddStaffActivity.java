package com.admin.shopkeeper.ui.activity.activityOfBoss.addStaff;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.DepartmentAdapter;
import com.admin.shopkeeper.adapter.EditFoodTypeAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.DepartmentBean;
import com.admin.shopkeeper.entity.StaffManageBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class AddStaffActivity extends BaseActivity<AddStaffPresenter> implements IAddStaffView,
        DatePicker.OnDateChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_account)
    EditText etAccount;
    @BindView(R.id.edit_password)
    EditText etPassword;
    @BindView(R.id.radioGroup_role)
    RadioGroup rgRole;
    @BindView(R.id.radioGroup_sex)
    RadioGroup rgSex;
    @BindView(R.id.textview_birthday_date)
    TextView tvDate;
    @BindView(R.id.edit_department_id)
    Spinner spinnerDepartMent;
    private int year, month, day;
    //在TextView上显示的字符
    private StringBuffer date;
    private DepartmentAdapter departmentAdapter;

    private StaffManageBean staffManageBean;

    @Override
    protected void initPresenter() {
        presenter = new AddStaffPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_staff;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        staffManageBean = (StaffManageBean) getIntent().getSerializableExtra(Config.PARAM1);
        if (staffManageBean == null) {
            toolbar.setTitle("增加员工");
        } else {
            toolbar.setTitle("编辑员工");
            updateView();
        }
        date = new StringBuffer();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        departmentAdapter = new DepartmentAdapter(this);
        spinnerDepartMent.setAdapter(departmentAdapter);



        presenter.getDepartMentId();
    }

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        if (staffManageBean != null) {
            year = Integer.parseInt(staffManageBean.getBirthday().substring(0, 4));
            month = Integer.parseInt(staffManageBean.getBirthday().substring(5, 7));
            day = Integer.parseInt(staffManageBean.getBirthday().substring(8, 10));
        } else {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

    }

    private void updateView() {
        etAccount.setText(staffManageBean.getUserName());
        etPassword.setText(staffManageBean.getPassword());
        tvDate.setText(staffManageBean.getBirthday().substring(0, staffManageBean.getBirthday().length() - 9));
        if (staffManageBean.getSex() == 1) {
            ((RadioButton) rgSex.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) rgSex.getChildAt(1)).setChecked(true);
        }
        if (staffManageBean.getRoleID() == 2) {
            ((RadioButton) rgRole.getChildAt(0)).setChecked(true);
        } else if (staffManageBean.getRoleID() == 4) {
            ((RadioButton) rgRole.getChildAt(1)).setChecked(true);
        } else if (staffManageBean.getRoleID() == 5) {
            ((RadioButton) rgRole.getChildAt(2)).setChecked(true);
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
        String acconutStr = etAccount.getText().toString().trim();
        if (TextUtils.isEmpty(acconutStr)) {
            Toasty.warning(this, "请设置员工账号").show();
            etAccount.requestFocus();
            return;
        }

        String passwordStr = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(passwordStr)) {
            showFailToast("请设置员工密码");
            etPassword.requestFocus();
            return;
        }

        String birthdayStr = tvDate.getText().toString().trim();
        DepartmentBean departmentBean = (DepartmentBean) spinnerDepartMent.getSelectedItem();
        /*if (TextUtils.isEmpty(birthdayStr)) {
            showFailToast("请设置员工生日");
            tvDate.requestFocus();
            return;
        }*/

        int stateRole = 0;
        if (((RadioButton) rgRole.getChildAt(0)).isChecked()) {
            stateRole = 2;
        } else if (((RadioButton) rgRole.getChildAt(1)).isChecked()) {
            stateRole = 4;
        } else {
            stateRole = 5;
        }
        int stateSex = 0;
        if (((RadioButton) rgRole.getChildAt(0)).isChecked()) {
            stateSex = 1;
        } else {
            stateSex = 2;
        }
        presenter.submit((staffManageBean == null) ? "" : staffManageBean.getUserID(), acconutStr, passwordStr, stateRole, stateSex, birthdayStr, departmentBean.getId());

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
    public void success(List<DepartmentBean> departmentBeen) {
        departmentAdapter.setData(departmentBeen);
        if (staffManageBean != null) {
            for (int i = 0; i < departmentBeen.size(); i++) {
                if (departmentBeen.get(i).getId().equals(staffManageBean.getDepartmentId())) {
                    spinnerDepartMent.setSelection(i);
                    break;
                }
            }
        } else {
            spinnerDepartMent.setSelection(0);
        }
    }

    @OnClick(R.id.textview_birthday_date)
    public void SetDateClick() {
        initDateTime();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                tvDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this, R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month - 1, day, this);
    }

    /**
     * 日期改变的监听事件
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
    }
}
