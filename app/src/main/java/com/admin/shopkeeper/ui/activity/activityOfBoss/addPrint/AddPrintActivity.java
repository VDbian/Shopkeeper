package com.admin.shopkeeper.ui.activity.activityOfBoss.addPrint;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.entity.PrintBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Arrays;

import butterknife.BindView;

public class AddPrintActivity extends BaseActivity<AddPrintPresenter> implements IAddPrinyView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_printtype)
    Spinner spinner;
    @BindView(R.id.edit_types)
    Spinner typeSpinner;
    @BindView(R.id.print_name)
    EditText etName;
    @BindView(R.id.print_address)
    EditText etAddress;
    @BindView(R.id.print_port)
    EditText etPort;
    @BindView(R.id.edit_qiedao)
    RadioGroup rgQiedao;
    @BindView(R.id.edit_cuttype)
    RadioGroup rgCutType;
    @BindView(R.id.edit_guige)
    RadioGroup rgGuige;
    @BindView(R.id.print_way)
    Spinner spinnerPrintWay;

    private PrintBean bean;

    @Override
    protected void initPresenter() {
        presenter = new AddPrintPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_print;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);

        bean = (PrintBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            toolbar.setTitle("修改打印机");

            etName.setText(bean.getName());
            etAddress.setText(bean.getIpAddress());
            etPort.setText(bean.getPort());
        } else {
            toolbar.setTitle("添加打印机");
        }
        setSupportActionBar(toolbar);


        String[] saleTypes = getResources().getStringArray(R.array.printType);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(saleTypes));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        String[] saleTypes1 = getResources().getStringArray(R.array.printType2);
        ArrayAdapter adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(saleTypes1));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter1);

        String[] saleTypes2 = getResources().getStringArray(R.array.printType3);
        ArrayAdapter adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(saleTypes2));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrintWay.setAdapter(adapter2);

        if (bean != null) {

            spinner.setSelection(bean.getPrintType());
            typeSpinner.setSelection(bean.getTypes());
            spinnerPrintWay.setSelection(bean.getPrintWay());
            if (bean.getQiedao().equals("1")) {
                ((RadioButton) rgQiedao.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgQiedao.getChildAt(1)).setChecked(true);
            }

            if (bean.getCutType().equals("0")) {
                ((RadioButton) rgCutType.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgCutType.getChildAt(1)).setChecked(true);
            }

            if (bean.getPrintSpec().equals("0")) {
                ((RadioButton) rgGuige.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton) rgGuige.getChildAt(1)).setChecked(true);
            }
        } else {
            ((RadioButton) rgQiedao.getChildAt(0)).setChecked(true);
            ((RadioButton) rgCutType.getChildAt(0)).setChecked(true);
            ((RadioButton) rgGuige.getChildAt(0)).setChecked(true);
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
        String nameStr = etName.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入打印机名称");
            return;
        }

        String addressStr = etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(addressStr)) {
            showToast("请输入打印机地址");
            return;
        }

        String portStr = etPort.getText().toString().trim();
        if (TextUtils.isEmpty(portStr)) {
            showToast("请输入打印机端口");
            return;
        }

        int position = spinner.getSelectedItemPosition();
        int typePosition = typeSpinner.getSelectedItemPosition();
        int printWay = spinnerPrintWay.getSelectedItemPosition();

        int qiedao = ((RadioButton) rgQiedao.getChildAt(0)).isChecked() ? 1 : 2;
        int cutType = ((RadioButton) rgCutType.getChildAt(0)).isChecked() ? 0 : 1;
        int guige = ((RadioButton) rgGuige.getChildAt(0)).isChecked() ? 0 : 1;


        if (bean == null) {
            presenter.submit("", nameStr, portStr, addressStr, position, qiedao, typePosition, cutType, guige , printWay);
        } else {
            presenter.submit(bean.getId(), nameStr, portStr, addressStr, position, qiedao, typePosition, cutType, guige , printWay);
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
