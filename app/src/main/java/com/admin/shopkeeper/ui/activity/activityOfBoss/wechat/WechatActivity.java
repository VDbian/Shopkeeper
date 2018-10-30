package com.admin.shopkeeper.ui.activity.activityOfBoss.wechat;

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
import com.admin.shopkeeper.dialog.MutiSelectDialog;
import com.admin.shopkeeper.dialog.SingleSelectDialog;
import com.admin.shopkeeper.entity.MutiBean;
import com.admin.shopkeeper.entity.WechatBean;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WechatActivity extends BaseActivity<WechatPresenter> implements IWechatView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_yuding)
    TextView tvYuding;
    @BindView(R.id.text_kuaican)
    TextView tvKuaican;
    @BindView(R.id.text_waimai)
    TextView tvWaimai;
    @BindView(R.id.text_saoma)
    TextView tvTandian;
    @BindView(R.id.item_qrcode)
    RadioGroup rgQrCode;
    @BindView(R.id.item_rate)
    EditText etRate;
    @BindView(R.id.text_weixin)
    TextView tvFunctions;
    @BindView(R.id.text_day)
    TextView tvDays;
    @BindView(R.id.text_type)
    TextView tvType;


    String yuding = "";
    String kuaican = "";
    String waimai = "";
    String tandian = "";
    String function = "";
    int days = 0;
    String foods = "";

    List<MutiBean> selectTypeStr = new ArrayList<>();
    List<MutiBean> selectFunctionsStr = new ArrayList<>();
    List<MutiBean> selectFoodsStr = new ArrayList<>();

    @Override
    protected void initPresenter() {
        presenter = new WechatPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechat;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.bosscolorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("微信管理");
        toolbar.setNavigationIcon(R.mipmap.navigation_icon_repeat);
        setSupportActionBar(toolbar);

        selectTypeStr.add(new MutiBean("积分兑换", false, 1));
        selectTypeStr.add(new MutiBean("优惠券", false, 2));
        selectTypeStr.add(new MutiBean("团购(商品)券", false, 4));

        selectTypeStr.add(new MutiBean("无", false, 5));


        selectFunctionsStr.add(new MutiBean("预定", false, 1));
        selectFunctionsStr.add(new MutiBean("扫码点餐", false, 2));
        selectFunctionsStr.add(new MutiBean("自助点餐", false, 3));
        selectFunctionsStr.add(new MutiBean("远程排队", false, 5));

        selectFoodsStr.add(new MutiBean("只预定桌位", false, 1));
        selectFoodsStr.add(new MutiBean("预定点餐", false, 2));

        presenter.getData();
    }


    @OnClick(R.id.btn_save)
    public void saveClick() {
        String rateStr = etRate.getText().toString();
        if (TextUtils.isEmpty(rateStr)) {
            showToast("请输入微信预定预付比例");
            return;
        }
        if(days < 3){
            showToast("请选择微信提前预定天数");
            return;
        }

        int qeCodePay = ((RadioButton) rgQrCode.getChildAt(0)).isChecked() ? 1 : 0;

        presenter.save(days, rateStr, qeCodePay, function, foods, yuding, waimai, kuaican, tandian);
    }

    @OnClick(R.id.text_day)
    public void daysClick() {
        List<String> types = new ArrayList<>();
        types.add("3天");
        types.add("4天");
        types.add("5天");
        types.add("6天");
        types.add("7天");
        types.add("8天");

        SingleSelectDialog.Builder builder = new SingleSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("微信提前预定天数");
        builder.setReasons(types);
        builder.setButtonClick(new SingleSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, int position) {
                tvDays.setText(text);
                days = position + 3;
            }

            @Override
            public void onCancel() {
                days = 0;
                tvType.setText("");
            }
        });
        builder.creater().show();
    }

    @OnClick(R.id.text_weixin)
    public void functionClick() {
        MutiSelectDialog.Builder builder = new MutiSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("微信功能");
        builder.setReasons(selectFunctionsStr);
        builder.setSelect(function);
        builder.setButtonClick(new MutiSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, String value) {
                tvFunctions.setText(text);
                function = value;
            }
        });
        builder.creater().show();
    }

    @OnClick(R.id.text_type)
    public void foodClick() {
        MutiSelectDialog.Builder builder = new MutiSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("预定类型");
        builder.setReasons(selectFoodsStr);
        builder.setSelect(foods);
        builder.setButtonClick(new MutiSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, String value) {
                tvType.setText(text);
                foods = value;
            }
        });
        builder.creater().show();
    }

    @OnClick(R.id.ll_yuding)
    public void yudingClick() {
        showSelectDialog("微信预定", yuding);
    }

    @OnClick(R.id.ll_kuaican)
    public void kuaicanClick() {
        showSelectDialog("快餐", kuaican);
    }

    @OnClick(R.id.ll_waimai)
    public void waimaiClick() {
        showSelectDialog("外卖", waimai);
    }

    @OnClick(R.id.ll_saoma)
    public void tandianClick() {
        showSelectDialog("扫码点餐", tandian);
    }

    private void showSelectDialog(String title, String selectText) {
        MutiSelectDialog.Builder builder = new MutiSelectDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle(title);
        builder.setReasons(selectTypeStr);
        builder.setSelect(selectText);
        builder.setButtonClick(new MutiSelectDialog.OnButtonClick() {

            @Override
            public void onOk(String text, String value) {
                if (title.equals("微信预定")) {
                    yuding = value;
                    tvYuding.setText(text);
                } else if (title.equals("快餐")) {
                    kuaican = value;
                    tvKuaican.setText(text);
                } else if (title.equals("外卖")) {
                    waimai = value;
                    tvWaimai.setText(text);
                } else {
                    tandian = value;
                    tvTandian.setText(text);
                }
            }
        });
        builder.creater().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
    public void error(String msg) {
        showFailToast(msg);
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }

    @Override
    public void success(WechatBean bean) {
        etRate.setText(String.valueOf(bean.getPrepaid()));
        if (bean.getQrcodePay().equals("0")) {
            ((RadioButton) rgQrCode.getChildAt(1)).setChecked(true);
        } else {
            ((RadioButton) rgQrCode.getChildAt(0)).setChecked(true);
        }
        days = bean.getDays();
        tvDays.setText(bean.getDays() + "天");

        yuding = bean.getWeixinYuding();
        kuaican = bean.getWeixinKuaican();
        waimai = bean.getWeixinWaimai();
        tandian = bean.getTandian();
        function = bean.getFunctions();
        foods = bean.getFoodOrDes();

        String functionStr = "";
        for (MutiBean mutiBean : selectFunctionsStr) {
            if (function.contains(mutiBean.getValue() + "")) {
                functionStr += mutiBean.getText() + ",";
            }
        }
        if (!TextUtils.isEmpty(functionStr)) {
            tvFunctions.setText(functionStr.substring(0, functionStr.length() - 1));
        }

        String foodStr = "";
        for (MutiBean mutiBean : selectFoodsStr) {
            if (foods.contains(mutiBean.getValue() + "")) {
                foodStr += mutiBean.getText() + ",";
            }
        }
        if (!TextUtils.isEmpty(foodStr)) {
            tvType.setText(foodStr.substring(0, foodStr.length() - 1));
        }

        String yudingStr = "";
        String kuaicanStr = "";
        String waimaiStr = "";
        String tandianStr = "";
        for (MutiBean mutiBean : selectTypeStr) {
            if (yuding.contains(mutiBean.getValue() + "")) {
                yudingStr += mutiBean.getText() + ",";
            }
            if (kuaican.contains(mutiBean.getValue() + "")) {
                kuaicanStr += mutiBean.getText() + ",";
            }
            if (waimai.contains(mutiBean.getValue() + "")) {
                waimaiStr += mutiBean.getText() + ",";
            }
            if (tandian.contains(mutiBean.getValue() + "")) {
                tandianStr += mutiBean.getText() + ",";
            }
        }
        if (!TextUtils.isEmpty(yudingStr)) {
            tvYuding.setText(yudingStr.substring(0, yudingStr.length() - 1));
        }
        if (!TextUtils.isEmpty(kuaicanStr)) {
            tvKuaican.setText(kuaicanStr.substring(0, kuaicanStr.length() - 1));
        }
        if (!TextUtils.isEmpty(waimaiStr)) {
            tvWaimai.setText(waimaiStr.substring(0, waimaiStr.length() - 1));
        }
        if (!TextUtils.isEmpty(tandianStr)) {
            tvTandian.setText(tandianStr.substring(0, tandianStr.length() - 1));
        }
    }
}
