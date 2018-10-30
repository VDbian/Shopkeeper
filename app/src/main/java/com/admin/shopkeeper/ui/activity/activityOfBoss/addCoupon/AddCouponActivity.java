package com.admin.shopkeeper.ui.activity.activityOfBoss.addCoupon;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.ProductAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.MutiSelectDialog;
import com.admin.shopkeeper.entity.CouponManageBean;
import com.admin.shopkeeper.entity.GuizeBean;
import com.admin.shopkeeper.entity.MutiBean;
import com.admin.shopkeeper.entity.ProductBean;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;
import com.gyf.barlibrary.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddCouponActivity extends BaseActivity<AddCouponPresenter> implements IAddCouponView,
        DatePicker.OnDateChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coupon_type)
    RadioGroup rgCouponType;
    @BindView(R.id.coupon_name)
    EditText etCouponName;
    @BindView(R.id.coupon_need)
    EditText etCouponNeedMoney;
    @BindView(R.id.coupon_give)
    EditText etCouponGiveMoney;
    @BindView(R.id.coupon_start_date)
    TextView tvCouponStartDate;
    @BindView(R.id.coupon_end_date)
    TextView tvCouponEndDate;
    @BindView(R.id.coupon_detail)
    EditText etCouponDetail;
    @BindView(R.id.radioGroup_waiMai)
    RadioGroup rgWaiMai;
    @BindView(R.id.use_type_text)
    TextView spinnerType;
    @BindView(R.id.coupon_money_limit)
    EditText etLimit;
    //最大购买量
    @BindView(R.id.coupon_max)
    EditText etMax;
    //退货提前天数
    @BindView(R.id.coupon_days)
    EditText etDays;
    //积分
    @BindView(R.id.coupon_jiFen)
    EditText etJifen;
    //数量
    @BindView(R.id.coupon_nums)
    EditText etNums;
    @BindView(R.id.edit_product)
    Spinner spinner;
    @BindView(R.id.ll_prduct)
    LinearLayout llProduct;
    @BindView(R.id.ll_type)
    LinearLayout llType;

    @BindView(R.id.ll_other)
    LinearLayout llOthet;
    @BindView(R.id.rg_tiaojian1)
    RadioGroup rgTiaojian1;
    @BindView(R.id.rg_tiaojian2)
    RadioGroup rgTiaojian2;
    @BindView(R.id.rg_tiaojian3)
    RadioGroup rgTiaojian3;
    @BindView(R.id.rg_tiaojian4)
    RadioGroup rgTiaojian4;
    @BindView(R.id.rg_tiaojian5)
    RadioGroup rgTiaojian5;
    @BindView(R.id.rg_tiaojian9)
    RadioGroup rgTiaojian6;
    @BindView(R.id.et_money1)
    EditText etMoney1;
    @BindView(R.id.et_money2)
    EditText etMoney2;
    @BindView(R.id.et_money3)
    EditText etMoney3;
    @BindView(R.id.et_money4)
    EditText etMoney4;
    @BindView(R.id.et_money5)
    EditText etMoney5;
    @BindView(R.id.et_money9)
    EditText etMoney9;
    @BindView(R.id.tv_start_1)
    TextView tvStart1;
    @BindView(R.id.tv_start_2)
    TextView tvStart2;
    @BindView(R.id.tv_start_3)
    TextView tvStart3;
    @BindView(R.id.tv_start_4)
    TextView tvStart4;
    @BindView(R.id.tv_start_5)
    TextView tvStart5;
    @BindView(R.id.tv_start_6)
    TextView tvStart6;
    @BindView(R.id.tv_start_7)
    TextView tvStart7;
    @BindView(R.id.tv_start_8)
    TextView tvStart8;
    @BindView(R.id.tv_start_9)
    TextView tvStart9;
    @BindView(R.id.tv_end_1)
    TextView tvEnd1;
    @BindView(R.id.tv_end_2)
    TextView tvEnd2;
    @BindView(R.id.tv_end_3)
    TextView tvEnd3;
    @BindView(R.id.tv_end_4)
    TextView tvEnd4;
    @BindView(R.id.tv_end_5)
    TextView tvEnd5;
    @BindView(R.id.tv_end_6)
    TextView tvEnd6;
    @BindView(R.id.tv_end_7)
    TextView tvEnd7;
    @BindView(R.id.tv_end_8)
    TextView tvEnd8;
    @BindView(R.id.tv_end_9)
    TextView tvEnd9;

    private String titleStr;
    private int year, month, day;
    //在TextView上显示的字符
    private StringBuffer date;
    //private final String[] useTypes = {"预定", "扫码点餐", "外卖", "快餐"};

    private List<MutiBean> useTypes = new ArrayList<>();
    private CouponManageBean bean;
    private ProductAdapter adapter;

    @Override
    protected void initPresenter() {
        presenter = new AddCouponPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_coupon;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        date = new StringBuffer();
        titleStr = getIntent().getStringExtra(Config.PARAM1);
        bean = (CouponManageBean) getIntent().getSerializableExtra(Config.PARAM2);

        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        if (bean == null) {
            toolbar.setTitle("新增券");

            ((RadioButton) rgCouponType.getChildAt(0)).setChecked(true);
            ((RadioButton) rgWaiMai.getChildAt(0)).setChecked(true);

            llOthet.setVisibility(View.GONE);
        } else {
            toolbar.setTitle("编辑券");

            etCouponName.setText(bean.getName());
            etCouponNeedMoney.setText(bean.getSumPrice() + "");
            etCouponGiveMoney.setText(bean.getPrice() + "");
            tvCouponStartDate.setText(bean.getBeginTime());
            tvCouponEndDate.setText(bean.getEndTime());
            etNums.setText(bean.getCounts());
            etDays.setText(bean.getCode());
            switch (bean.getTypeId()) {
                case "1":
                    ((RadioButton) rgCouponType.getChildAt(0)).setChecked(true);
                    break;
                case "2":
                    ((RadioButton) rgCouponType.getChildAt(1)).setChecked(true);
                    break;
                case "3":
                    ((RadioButton) rgCouponType.getChildAt(2)).setChecked(true);
                    break;
                case "4":
                    ((RadioButton) rgCouponType.getChildAt(3)).setChecked(true);
                    break;
                default:
                    break;
            }
        }
        setSupportActionBar(toolbar);

        rgWaiMai.setOnCheckedChangeListener((radioGroup, i) -> {
            if (((RadioButton) rgWaiMai.getChildAt(3)).isChecked()) {
                llOthet.setVisibility(View.VISIBLE);
            } else {
                llOthet.setVisibility(View.GONE);
            }
        });
        rgCouponType.setOnCheckedChangeListener((radioGroup, id) -> {
            if (id == ((RadioButton) radioGroup.getChildAt(0)).getId() ||
                    id == ((RadioButton) radioGroup.getChildAt(1)).getId()) {
                llProduct.setVisibility(View.GONE);
            } else if (id == ((RadioButton) radioGroup.getChildAt(2)).getId()) {
                llProduct.setVisibility(View.VISIBLE);
                adapter = new ProductAdapter(AddCouponActivity.this);
                spinner.setAdapter(adapter);
                presenter.getProductData();
            } else {
                llProduct.setVisibility(View.VISIBLE);
                adapter = new ProductAdapter(AddCouponActivity.this);
                spinner.setAdapter(adapter);
                presenter.getMealData();
            }
        });

        useTypes.add(new MutiBean("预定", false, 1));
        useTypes.add(new MutiBean("外卖", false, 3));
        useTypes.add(new MutiBean("快餐", false, 4));
        useTypes.add(new MutiBean("扫码点餐", false, 5));

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

    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    String useType = "";

    @OnClick(R.id.use_type_text)
    public void sizeClick() {
        MutiSelectDialog.Builder builder = new MutiSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("券适用类型");
        builder.setReasons(useTypes);
        builder.setButtonClick(new MutiSelectDialog.OnButtonClick() {


            @Override
            public void onOk(String text, String value) {
                useType = value;
                spinnerType.setText(text);
            }
        });
        builder.creater().show();
    }

    @OnClick(R.id.coupon_start_date)
    public void SetDateClick() {
        initDateTime();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                tvCouponStartDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day));
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

    @OnClick(R.id.coupon_end_date)
    public void SetEndDateClick() {
        initDateTime();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                tvCouponEndDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day));
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

    private void doSubmit() {
        String nameStr = etCouponName.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            showToast("请输入券名称");
            return;
        }
        String needStr = etCouponNeedMoney.getText().toString().trim();
        if (TextUtils.isEmpty(needStr)) {
            showToast("请输入满足金额");
            return;
        }
        String giveMoney = etCouponGiveMoney.getText().toString().trim();
        if (TextUtils.isEmpty(giveMoney)) {
            showToast("请输入赠送金额");
            return;
        }
        String startDate = tvCouponStartDate.getText().toString().trim();
        if (TextUtils.isEmpty(startDate)) {
            showToast("请选择开始日期");
            return;
        }
        String endDate = tvCouponEndDate.getText().toString().trim();
        if (TextUtils.isEmpty(endDate)) {
            showToast("请选择结束日期");
            return;
        }
        String detail = etCouponDetail.getText().toString().trim();
        if (TextUtils.isEmpty(detail)) {
            showToast("请输入详细信息");
            return;
        }
        int typeCoupon = 0;
        if (((RadioButton) rgCouponType.getChildAt(0)).isChecked()) {
            typeCoupon = 1;
        } else if (((RadioButton) rgCouponType.getChildAt(1)).isChecked()) {
            typeCoupon = 2;
        } else if (((RadioButton) rgCouponType.getChildAt(2)).isChecked()) {
            typeCoupon = 3;
        } else {
            typeCoupon = 4;
        }

        int typeWaimai = 0;
        if (((RadioButton) rgWaiMai.getChildAt(0)).isChecked()) {
            typeWaimai = 1;
        } else if (((RadioButton) rgWaiMai.getChildAt(1)).isChecked()) {
            typeWaimai = 2;
        } else if (((RadioButton) rgWaiMai.getChildAt(2)).isChecked()) {
            typeWaimai = 3;
        } else {
            typeWaimai = 4;
        }

        /*String limitStr = etLimit.getText().toString().trim();
        if (TextUtils.isEmpty(limitStr)) {
            showToast("请输入金额限制");
            return;
        }*/
        String maxStr = etMax.getText().toString().trim();
        if (TextUtils.isEmpty(maxStr)) {
            showToast("请输入最大购买数量");
            return;
        }
        String dayStr = etDays.getText().toString().trim();
        if (TextUtils.isEmpty(dayStr)) {
            showToast("请输入退货提前天数");
            return;
        }
        String jiFenStr = etDays.getText().toString().trim();
        if (TextUtils.isEmpty(jiFenStr)) {
            showToast("请输入积分");
            return;
        }
        String numStr = etNums.getText().toString().trim();
        if (TextUtils.isEmpty(numStr)) {
            showToast("请输入优惠券数量");
            return;
        }

        String productId = "";
        String productName = "";
        if (typeCoupon == 3) {
            ProductBean bean = (ProductBean) spinner.getSelectedItem();
            productId = bean.getId();
            productName = bean.getName();
        }

        GuizeBean bean1 = GuizeBean.getVoidBean();
        GuizeBean bean2 = GuizeBean.getVoidBean();
        GuizeBean bean3 = GuizeBean.getVoidBean();
        GuizeBean bean4 = GuizeBean.getVoidBean();
        GuizeBean bean5 = GuizeBean.getVoidBean();
        GuizeBean bean6 = GuizeBean.getVoidBean();
        GuizeBean bean7 = GuizeBean.getVoidBean();
        GuizeBean bean8 = GuizeBean.getVoidBean();
        GuizeBean bean9 = GuizeBean.getVoidBean();

        if (typeWaimai == 4) {
            bean1 = getGuize(rgTiaojian1, etMoney1, tvStart1, tvEnd1);
            if (bean1 == null) {
                return;
            }

            bean2 = getGuize(rgTiaojian2, etMoney2, tvStart2, tvEnd2);
            if (bean2 == null) {
                return;
            }

            bean3 = getGuize(rgTiaojian3, etMoney3, tvStart3, tvEnd3);
            if (bean3 == null) {
                return;
            }

            bean4 = getGuize(rgTiaojian4, etMoney4, tvStart4, tvEnd4);
            if (bean4 == null) {
                return;
            }

            bean5 = getGuize(rgTiaojian5, etMoney5, tvStart5, tvEnd5);
            if (bean5 == null) {
                return;
            }

            bean6 = getGuize(tvStart6, tvEnd6);
            if (bean6 == null) {
                return;
            }

            bean7 = getGuize(tvStart7, tvEnd7);
            if (bean7 == null) {
                return;
            }

            bean8 = getGuize(tvStart8, tvEnd8);
            if (bean8 == null) {
                return;
            }

            bean9 = getGuize(rgTiaojian6, etMoney9, tvStart9, tvEnd9);
            if (bean9 == null) {
                return;
            }
        }

        presenter.submit(typeCoupon + "", productId, productName, needStr, startDate, maxStr, detail, typeWaimai + "",
                nameStr, numStr, giveMoney, endDate, dayStr, jiFenStr, useType,
                bean1, bean2, bean3, bean4, bean5, bean6, bean7, bean8, bean9);

//            if (titleStr.equals("优惠券管理")) {
//
//                presenter.submit(nameStr, needStr, giveMoney, startDate, endDate, detail, typeCoupon, typeWaimai, useType, limitStr, maxStr, dayStr, jiFenStr, numStr);
//            } else if (bean.getTypeId().equals("4")) {
//                ProductBean productBean = (ProductBean) spinner.getSelectedItem();
//                if (productBean == null) {
//                    showFailToast("请绑定商品");
//                    return;
//                }
//                presenter.submitGroup(nameStr, needStr, giveMoney, startDate, endDate, detail, typeCoupon, typeWaimai, useType,
//                        limitStr, maxStr, dayStr, jiFenStr, numStr, productBean.getName(), productBean.getId());
//            } else {//商品券
//
//                ProductBean productBean = (ProductBean) spinner.getSelectedItem();
//                if (productBean == null) {
//                    showFailToast("请绑定商品");
//                    return;
//                }
//
//                presenter.submitCoupon(nameStr, needStr, giveMoney, startDate, endDate, detail, typeCoupon, typeWaimai, useType,
//                        limitStr, maxStr, dayStr, jiFenStr, numStr, productBean.getName(), productBean.getId());
//            }
    }

    public GuizeBean getGuize(RadioGroup radioGroup, EditText editText, TextView tvSart, TextView tvEnd) {
        GuizeBean bean = new GuizeBean();
        String valueStr = editText.getText().toString();
        String startStr = tvSart.getText().toString();
        String endStr = tvEnd.getText().toString();
        if (((RadioButton) radioGroup.getChildAt(0)).isChecked()) {
            bean.setType("1");
        } else if (((RadioButton) radioGroup.getChildAt(1)).isChecked()) {
            bean.setType("2");
        } else if (((RadioButton) radioGroup.getChildAt(2)).isChecked()) {
            bean.setType("3");
        } else if (!TextUtils.isEmpty(valueStr) || !TextUtils.isEmpty(startStr) || !TextUtils.isEmpty(endStr)) {
            showToast("请选择对应条件类型");
            return null;
        }


        if (TextUtils.isEmpty(valueStr) && (!TextUtils.isEmpty(startStr) || !TextUtils.isEmpty(endStr)|| !TextUtils.isEmpty(bean.getType()))) {
            showToast("请输入对应条件");
            return null;
        }
        bean.setValue(valueStr);

        if (TextUtils.isEmpty(startStr) && (!TextUtils.isEmpty(valueStr) || !TextUtils.isEmpty(endStr)|| !TextUtils.isEmpty(bean.getType()))) {
            showToast("请选择对应开始时间");
            return null;
        }
        bean.setStart(startStr);

        if (TextUtils.isEmpty(endStr) && (!TextUtils.isEmpty(valueStr) || !TextUtils.isEmpty(startStr)|| !TextUtils.isEmpty(bean.getType()))) {
            showToast("请选择对应结束时间");
            return null;
        }
        bean.setEnd(endStr);

        return bean;
    }

    public GuizeBean getGuize(TextView tvSart, TextView tvEnd) {
        GuizeBean bean = new GuizeBean();

        bean.setType("");
        bean.setValue("");

        String startStr = tvSart.getText().toString();
        String endStr = tvEnd.getText().toString();
        if (!TextUtils.isEmpty(endStr) && TextUtils.isEmpty(startStr)) {
            showToast("请选择对应开始时间");
            return null;
        }
        bean.setStart(startStr);


        if (!TextUtils.isEmpty(startStr) && TextUtils.isEmpty(endStr)) {
            showToast("请选择对应结束时间");
            return null;
        }
        bean.setEnd(endStr);

        return bean;
    }

    @OnClick(R.id.tv_start_1)
    public void start1Click() {
        showDatePicker(tvStart1);
    }

    @OnClick(R.id.tv_start_2)
    public void start2Click() {
        showDatePicker(tvStart2);
    }

    @OnClick(R.id.tv_start_3)
    public void start3Click() {
        showDatePicker(tvStart3);
    }

    @OnClick(R.id.tv_start_4)
    public void start4Click() {
        showDatePicker(tvStart4);
    }

    @OnClick(R.id.tv_start_5)
    public void start5Click() {
        showDatePicker(tvStart5);
    }

    @OnClick(R.id.tv_start_6)
    public void start6Click() {
        showDatePicker(tvStart6);
    }

    @OnClick(R.id.tv_start_7)
    public void start7Click() {
        showDatePicker(tvStart7);
    }

    @OnClick(R.id.tv_start_8)
    public void start8Click() {
        showDatePicker(tvStart8);
    }

    @OnClick(R.id.tv_start_9)
    public void start9Click() {
        showDatePicker(tvStart9);
    }

    @OnClick(R.id.tv_end_1)
    public void ent1Click() {
        showDatePicker(tvEnd1);
    }

    @OnClick(R.id.tv_end_2)
    public void ent2Click() {
        showDatePicker(tvEnd2);
    }

    @OnClick(R.id.tv_end_3)
    public void ent3Click() {
        showDatePicker(tvEnd3);
    }

    @OnClick(R.id.tv_end_4)
    public void ent4Click() {
        showDatePicker(tvEnd4);
    }

    @OnClick(R.id.tv_end_5)
    public void ent5Click() {
        showDatePicker(tvEnd5);
    }

    @OnClick(R.id.tv_end_6)
    public void ent6Click() {
        showDatePicker(tvEnd6);
    }

    @OnClick(R.id.tv_end_7)
    public void ent7Click() {
        showDatePicker(tvEnd7);
    }

    @OnClick(R.id.tv_end_8)
    public void ent8Click() {
        showDatePicker(tvEnd8);
    }

    @OnClick(R.id.tv_end_9)
    public void ent9Click() {
        showDatePicker(tvEnd9);
    }


    private void showDatePicker(TextView textView) {
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setYearLimt(10);
        dialog.setTitle("选择时间");
        dialog.setType(DateType.TYPE_YMD);
        dialog.setMessageFormat("yyyy-MM-dd HH:mm:ss");
        dialog.setOnChangeLisener(null);
        dialog.setOnSureLisener(date -> {
            textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
        });
        dialog.show();
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
    public void productsuccess(List<ProductBean> productBeen) {
        if (productBeen != null && productBeen.size() > 0) {
            adapter.setData(productBeen);
            spinner.setSelection(0);
        }
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
    }
}
