package com.admin.shopkeeper.ui.activity.bill;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.BillAdapter;
import com.admin.shopkeeper.adapter.MenuListAdapter;
import com.admin.shopkeeper.adapter.PopSaleAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CouponLineDialog;
import com.admin.shopkeeper.dialog.DaZheDialog;
import com.admin.shopkeeper.dialog.GuaZhangDialog;
import com.admin.shopkeeper.dialog.JianmianDialog;
import com.admin.shopkeeper.entity.BillJson;
import com.admin.shopkeeper.entity.CardBean;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.DaZheEntity;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.entity.WeixinOrderBean;
import com.admin.shopkeeper.utils.Tools;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

public class BillActivity extends BaseActivity<BillPresenter> implements IBillView {

    public static final int P1 = 1; //桌台
    public static final int P2 = 2; //详细界面
    public static final int P3 = 3;//快餐，
    public static final int P4 = 4; // 外卖结算
    public static final int P5 = 5; // 合并
    public static final int P6 = 6; // 反结账

    public static final int REQUEST_CODE = 5;
    public static final int REQUEST_CODE_ZFB = 6;
    public static final int REQUEST_CODE_3MaHE1 = 7;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv1)
    AppCompatTextView tv1;
    @BindView(R.id.tv2)
    AppCompatTextView tv2;
    @BindView(R.id.tv3)
    AppCompatTextView tv3;
    @BindView(R.id.tv4)
    AppCompatTextView tv4;
    @BindView(R.id.tv5)
    AppCompatTextView tv5;
    @BindView(R.id.tv6)
    AppCompatTextView tv6;
    @BindView(R.id.jianMian)
    AppCompatTextView jianMian;
    @BindView(R.id.bill_dazhe_text)
    AppCompatTextView dazhe;
    @BindView(R.id.switchBtn)
    SwitchCompat switchCompat;
    @BindView(R.id.layout_6)
    RecyclerView recyclerView;
    @BindView(R.id.tv7)
    AppCompatTextView tv7;
    @BindView(R.id.bill_dazhe)
    LinearLayout llDazhe;
    @BindView(R.id.button_scan)
    AppCompatButton scanBtn;
    @BindView(R.id.bill_score_text)
    TextView tvScoreText;
    @BindView(R.id.bill_coupon_other_tv)
    TextView tvCouponOther;

    private int type;
    private String tabName;
    private int position;

    private String billId;
    private TableEntity tableEntity;

    private double youhuiMoney;//优惠
    private double youhuiMoneyOfMember = 0;//会员优惠
    private double couponLineYouhuiMoney;//线下券优惠
    private int scoreMoney = 0;
    private int scoreCount = 0;
    private int cardMoney = 0;
    private double needMoney = 0;

    private double idazhe = 0;
    private double ijianmian = 0;

    private double foodMoney = 0;

    private BillAdapter adapter;

    private Order order;
    private List<OrderDetailFood> list;
    private GuaZhangDialog guaZhangDialog;
    private CouponLineDialog couponLineDialog;

    MemberBean memberBean;
    String memberId = "";
    private PopupWindow saleWindow;
    List<CardBean> cardBeenList = new ArrayList<>();
    private PopSaleAdapter popSaleAdapter;
    private LinearLayout llMember;
    private LinearLayout llEdit;
    private RecyclerView popList;
    private TextView tvNo;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvScore;
    private TextView tvMoney;
    private EditText editText;

    private int poptype = 1;
    private boolean haveQXDaZhe;//是否进行权限打折或单个菜品打折
    private boolean haveOneDaZhe;//是否进行单个菜品打折
    private List<CouponLineDownBean> couponLineDownBeanList = new ArrayList<>();

    double mading = 0;
    double rounding = 0;
    double moMoney = 0;


    @OnClick(R.id.bill_print)
    public void printClick() {
        if (order == null || weixinOrderBean == null) {
            return;
        }
        presenter.print(order.getBillid(), order.getPeopleCount(), order.getTableId(), order.getTableName(),
                weixinOrderBean.getYuanjia(), getYinfuMoney(), getYouhuiMoney(), "8");
    }

    @OnClick(R.id.layout)
    public void cardClick() {
        View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_sale, null);
        saleWindow = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        llMember = (LinearLayout) laheiView.findViewById(R.id.pop_member);
        llEdit = (LinearLayout) laheiView.findViewById(R.id.ll_edit);
        popList = (RecyclerView) laheiView.findViewById(R.id.pop_list);
        tvNo = (TextView) laheiView.findViewById(R.id.text_no);
        tvName = (TextView) laheiView.findViewById(R.id.text_name);
        tvPhone = (TextView) laheiView.findViewById(R.id.text_phone);
        tvScore = (TextView) laheiView.findViewById(R.id.text_remain_score);
        tvMoney = (TextView) laheiView.findViewById(R.id.text_remain_money);
        TextView tvOk = (TextView) laheiView.findViewById(R.id.pop_ok);
        TextView tvCancel = (TextView) laheiView.findViewById(R.id.pop_cancel);
        editText = (EditText) laheiView.findViewById(R.id.pop_edit);
        EditText etSearch = (EditText) laheiView.findViewById(R.id.pop_edit_search);
        TextView tvSearch = (TextView) laheiView.findViewById(R.id.pop_btn_search);
        ImageView ivQrcode = (ImageView) laheiView.findViewById(R.id.pop_qrcode);

        popList.setLayoutManager(new LinearLayoutManager(this));
        popSaleAdapter = new PopSaleAdapter(R.layout.item_pop_sale, cardBeenList);
        popList.setAdapter(popSaleAdapter);
        popSaleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String scoreStr = editText.getText().toString();
                if (!TextUtils.isEmpty(scoreStr)) {
                    showToast("使用积分后不能使用卡券");
                    return;
                }

                CardBean cardBean = cardBeenList.get(position);
                if (cardBean.isSelect()) {
                    cardBean.setSelect(false);
                } else {
                    if (cardBean.getType().equals("1") || cardBean.getType().equals("2")) {
                        for (CardBean card : cardBeenList) {
                            card.setSelect(false);
                        }
                    } else {
                        for (CardBean card : cardBeenList) {
                            if (card.getType().equals("1") || card.getType().equals("2")) {
                                card.setSelect(false);
                            }
                        }
                    }
                    cardBean.setSelect(true);
                }
                popSaleAdapter.notifyDataSetChanged();
                cardMoney = 0;
                for (CardBean card : cardBeenList) {
                    if (card.isSelect()) {
                        cardMoney += card.getMoney();
                    }
                }
                if (cardMoney > 0) {
                    editText.setEnabled(false);
                } else {
                    editText.setEnabled(true);
                }
                initPay();
                getNeed();
                intText();
            }
        });

        if (poptype == 2) {
            tvOk.setText("立即支付");
            if (memberBean != null) {
                if (memberPayEntity.getMoney() > memberBean.getMoney()) {
                    showToast("会员余额不足");
                    memberPayEntity.setMoney(memberBean.getMoney());
                    adapter.notifyDataSetChanged();
                    getNeed();
                    intText();
                }
            }
        } else {
            //popList.setVisibility(View.VISIBLE);
            tvOk.setText("确定");
        }

        if (memberBean == null) {
            llMember.setVisibility(View.GONE);
            llEdit.setVisibility(View.GONE);
        } else {
            llMember.setVisibility(View.VISIBLE);

            if (memberBean.getRate() != 0 && poptype == 1) {
                llEdit.setVisibility(View.VISIBLE);
                editText.setHint("最多可使用" + ((int) getYinfuMoney() / memberBean.getRate()) + "积分");
                if (scoreCount > 0) {
                    editText.setText(scoreCount + "");
                }
            } else {
                llEdit.setVisibility(View.GONE);
            }

            tvNo.setText("会员编号：" + memberBean.getNo());
            tvName.setText("会员姓名：" + memberBean.getName());
            tvPhone.setText("手机号码：" + memberBean.getPhone());
            tvScore.setText("剩余积分：" + memberBean.getScore());
            tvMoney.setText("会员余额：" + memberBean.getMoney());
        }


        ivQrcode.setOnClickListener(v -> {
            Intent intent = new Intent(BillActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 8);
        });

        tvSearch.setOnClickListener(v -> {
            String idStr = etSearch.getText().toString().trim();
            if (TextUtils.isEmpty(idStr)) {
                showToast("请输入会员编号");
                return;
            }
            presenter.searchMember(billId == null ? order.getId() : billId, idStr);
        });

        tvOk.setOnClickListener(v -> {
            if (memberBean == null) {
                showToast("没有会员信息");
                return;
            }
            memberId = memberBean.getId();

            if (poptype == 2) {
                if (needMoney == 0) {
                    youhuiMoneyOfMember = weixinOrderBean.getYinfu() + weixinOrderBean.getMemberzenupice() - weixinOrderBean.getMemberpiceNew();
                    billClick();
                } else {
                    showToast("余额不足" + needMoney);
                }
                saleWindow.dismiss();
                return;
            }

            String scoreStr = editText.getText().toString().trim();
            if (!TextUtils.isEmpty(scoreStr)) {
                if (weixinOrderBean.getMemberpice() > 0 || memberBean.getRate() == 0) {
                    showToast("不能积分兑换");
                    return;
                }

                int scoreNum = Integer.parseInt(scoreStr);
                if (scoreNum > memberBean.getScore()) {
                    showToast("积分不足");
                    return;
                }

                scoreMoney = (int) (scoreNum * memberBean.getRate());
                if (scoreMoney > weixinOrderBean.getYinfu()) {
                    showToast("优惠金额不能大于应付金额");
                    return;
                }
                scoreCount = scoreNum;
                youhuiMoneyOfMember = weixinOrderBean.getYinfu() + weixinOrderBean.getMemberzenupice() - weixinOrderBean.getMemberpiceNew();
                initPay();
                getNeed();
                intText();
            } else {
                youhuiMoneyOfMember = weixinOrderBean.getYinfu() + weixinOrderBean.getMemberzenupice() - weixinOrderBean.getMemberpiceNew();
                initPay();
                getNeed();
                intText();
            }
            saleWindow.dismiss();
        });
        tvCancel.setOnClickListener(v -> {
            if (memberPayEntity != null) {
                memberPayEntity.setMoney(0);
                memberPayEntity.setSelected(false);
            }
//            if(poptype != 2){
            scoreMoney = 0;
            scoreCount = 0;
            cardMoney = 0;
            cardBeenList.clear();
            youhuiMoneyOfMember = weixinOrderBean.getYinfu() + weixinOrderBean.getMemberzenupice() - weixinOrderBean.getMemberpiceNew();
            initPay();
            getNeed();
            intText();
//            }
            saleWindow.dismiss();
        });

        saleWindow.setOutsideTouchable(true);
        saleWindow.setFocusable(true);
        saleWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        saleWindow.setOnDismissListener(() -> {
            poptype = 1;
            backgroundAlpha(1f);
        });
        saleWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    @OnClick(R.id.bill_dazhe)
    public void dazheClick() {
        if (App.INSTANCE().getUser().getPermissionValue().contains("quanxiandazhe")) {
            if (haveOneDaZhe) {
                warning("您已进行单个菜品打折");
            } else {
                presenter.getDazheList();
            }
        } else {
            warning("没有打折权限");
        }
    }

    @OnClick(R.id.bill_coupon_other_ll)
    public void billCouponOtherClick() {
        presenter.getLineDownInfo(1, "");
    }

    @OnClick(R.id.bill_jianmian)
    public void jianmianClick() {
        if (!App.INSTANCE().getUser().getPermissionValue().contains("quanxianjianmian")) {
            warning("没有减免权限");
            return;
        }

        BigDecimal yinfu = new BigDecimal(getYinfuMoney()).setScale(2, BigDecimal.ROUND_DOWN);

        JianmianDialog.Builder builder = new JianmianDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setHintStr("最高减免" + yinfu + "元");
        builder.setButtonClick(new JianmianDialog.OnButtonClick() {

            @Override
            public void onBtnClick(double i) {
                mading = 0;
                rounding = 0;
                moMoney = 0;
                ijianmian = i;
                jianMian.setText(ijianmian + "");
                youhuiMoney = ijianmian + idazhe;
                initPay();
                getNeed();
                intText();
            }

            @Override
            public void onItemClick(int i) {
                mading = 0;
                rounding = 0;
                switch (i) {
                    case 1:
                        youhuiMoney = idazhe;
                        if (moMoney >= getYinfuMoney() % 10 - getYinfuMoney() % 1) {
                            return;
                        }
                        moMoney += getYinfuMoney() % 10 - getYinfuMoney() % 1;
                        ijianmian = mading = moMoney;
                        jianMian.setText(new BigDecimal(ijianmian).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
                        youhuiMoney = ijianmian + idazhe;
                        initPay();
                        getNeed();
                        intText();
                        break;
                    case 2:
                        youhuiMoney = idazhe;
                        if (moMoney % 1 >= getYinfuMoney() % 1 - getYinfuMoney() % 0.1) {
                            return;
                        }
                        moMoney += getYinfuMoney() % 1 - getYinfuMoney() % 0.1;
                        ijianmian = mading = moMoney;
                        jianMian.setText(new BigDecimal(ijianmian).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
                        youhuiMoney = ijianmian + idazhe;
                        initPay();
                        getNeed();
                        intText();
                        break;
                    case 3:
                        youhuiMoney = idazhe;
                        if (moMoney % 0.1 >= getYinfuMoney() % 0.1) {
                            return;
                        }
                        moMoney += getYinfuMoney() % 0.1;
                        ijianmian = mading = moMoney;
                        jianMian.setText(new BigDecimal(ijianmian).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
                        youhuiMoney = ijianmian + idazhe;
                        initPay();
                        getNeed();
                        intText();
                        break;
                    case 4:
                        moMoney = 0;

                        youhuiMoney = idazhe;
                        double k = getYinfuMoney() % 1;
                        if (k >= 0.5) {
                            ijianmian = k - 1;
                            rounding = -ijianmian;
                        } else {
                            ijianmian = k;
                            mading = ijianmian;
                        }
                        jianMian.setText(new BigDecimal(ijianmian).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
                        youhuiMoney = ijianmian + idazhe;
                        initPay();
                        getNeed();
                        intText();
                        break;
                }
            }

            @Override
            public void onCancel() {
                mading = 0;
                rounding = 0;
                moMoney = 0;
                ijianmian = 0;
                youhuiMoney = ijianmian + idazhe;
                jianMian.setText(ijianmian + "");
                initPay();
                getNeed();
                intText();
            }
        });
        builder.creater().show();
    }

    @OnClick(R.id.button_scan)
    public void scanClick() {
        Intent intent = new Intent(BillActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_3MaHE1);
    }

    @OnClick(R.id.button)
    public void billClick() {
        if (!App.INSTANCE().getUser().getPermissionValue().contains("queren")) {
            warning("没有确认结账权限");
            return;
        }

        if (needMoney == 0) {
            List<BillJson.BillJsonBase> t = new ArrayList<>();
            BillJson.BillJsonBase base = new BillJson.BillJsonBase();
            t.add(base);
            if (scoreMoney > 0) {
                BillJson.BillJsonBase jsonBase = new BillJson.BillJsonBase();
                jsonBase.setType("1");
                jsonBase.setPice(scoreMoney + "");
                jsonBase.setSate("0");
                jsonBase.setGuid(System.currentTimeMillis() + "00");
                jsonBase.setPiceGuid(scoreCount + "");
                t.add(jsonBase);
            }
            for (int i = 0; i < cardBeenList.size(); i++) {
                CardBean bean = cardBeenList.get(i);
                if (bean.isSelect()) {
                    BillJson.BillJsonBase jsonBase = new BillJson.BillJsonBase();
                    if (bean.getType().equals("1") || bean.getType().equals("2")) {
                        jsonBase.setType("2");
                    } else {
                        jsonBase.setType("4");
                    }
                    jsonBase.setGuid(System.currentTimeMillis() + "0" + i);
                    jsonBase.setPiceGuid(bean.getId());
                    jsonBase.setPice(bean.getMoney() + "");
                    jsonBase.setSate("0");
                    t.add(jsonBase);
                }
            }

            BillJson.TeacherJson teacherJson = new BillJson.TeacherJson();
            teacherJson.setTeacher(t);
            String tStr = new Gson().toJson(teacherJson);

            BillJson.Quanxian quanxian = new BillJson.Quanxian();
            List<BillJson.BillJsonBase> q = new ArrayList<>();
            q.add(base);
            //免单 3
            if (idazhe > 0) {
                BillJson.BillJsonBase d = new BillJson.BillJsonBase();
                d.setGuid(System.currentTimeMillis() + "");
                d.setPice(idazhe + "");
                if (haveOneDaZhe) {
                    d.setType("6");
                } else {
                    d.setType("1");
                }
                q.add(d);
            }
            if (ijianmian > 0) {
                BillJson.BillJsonBase j = new BillJson.BillJsonBase();
                j.setGuid(System.currentTimeMillis() + "");
                j.setPice(ijianmian + "");
                j.setType("2");
                q.add(j);
            }
            if (switchCompat.isChecked()) {
                BillJson.BillJsonBase m = new BillJson.BillJsonBase();
                m.setGuid(System.currentTimeMillis() + "");
                m.setPice(weixinOrderBean.getYinfu() + "");
                m.setType("3");
                q.add(m);
            }
            if (couponLineYouhuiMoney > 0) {
                for (int k = 0; k < couponLineDownBeanList.size(); k++) {
                    CouponLineDownBean entity = couponLineDownBeanList.get(k);
                    if (entity != null) {
                        BillJson.BillJsonBase pe = new BillJson.BillJsonBase();
                        pe.setGuid(System.currentTimeMillis() + "");
                        pe.setPice(entity.getPice() + "");
                        pe.setPiceGuid("");
                        pe.setSate(entity.getName() + "");
                        pe.setType(5 + "");
                        pe.setIsSql(entity.getGuid() + "");
                        q.add(pe);
                    }
                }
            }
            quanxian.setQuanxian(q);
            String qStr = new Gson().toJson(quanxian);
            Log.d("ttt", "quanxian:" + qStr);

            BillJson.Pays pays = new BillJson.Pays();
            List<BillJson.BillJsonBase> p = new ArrayList<>();
            p.add(base);

            for (int k = 0; k < adapter.getItemCount(); k++) {
                PayMeEntity entity = adapter.getItem(k);
                if (entity != null && entity.isSelected()) {
                    BillJson.BillJsonBase pe = new BillJson.BillJsonBase();
                    pe.setGuid(System.currentTimeMillis() + "");
                    pe.setPice(entity.getMoney() + "");
                    pe.setPiceGuid(entity.getGuid() + "");
                    p.add(pe);
                }
            }

            pays.setQuanxian(p);
            String pStr = new Gson().toJson(pays);

            double result = getYinfuMoney();
            double free = getYouhuiMoney() + weixinOrderBean.getYufupice() + weixinOrderBean.getYouhui();

            Log.i("ttt", "totleMoney:" + result);

            memberId = memberBean == null ? "" : memberBean.getId();
            if (type == P3) {
                if (!TextUtils.isEmpty(tabName)) {
                    presenter.bill(billId, App.INSTANCE().getShopID(), tableEntity != null ? tableEntity.getRoomTableID() : "",
                            memberId, weixinOrderBean.getYuanjia(), weixinOrderBean.getCanju(), qStr, tStr, pStr, 1, result, tabName,
                            free, "4", guiId, "", mading, rounding);
                } else {
                    presenter.bill(billId, App.INSTANCE().getShopID(), tableEntity != null ? tableEntity.getRoomTableID() : "",
                            memberId, weixinOrderBean.getYuanjia(), weixinOrderBean.getCanju(), qStr, tStr, pStr, 1, result, tableEntity != null ? tableEntity.getTableName() : "",
                            free, "4", guiId, "", mading, rounding);
                }
            } else if (type == P4) {
                presenter.bill(billId, App.INSTANCE().getShopID(), tableEntity != null ? tableEntity.getRoomTableID() : "",
                        memberId, weixinOrderBean.getYuanjia(), weixinOrderBean.getCanju(), qStr
                        , tStr, pStr, 1, result, tableEntity != null ? tableEntity.getTableName() : "", free, "3", guiId, "", mading, rounding);
            } else if (type == P6) {
                presenter.rebill(order.getBillid(), App.INSTANCE().getShopID(), order.getTableId(), memberId, weixinOrderBean.getYuanjia(), weixinOrderBean.getCanju(), qStr
                        , tStr, pStr, order.getType().equals("7") ? "7" : "4", free, order.getBillid(), result, mading, rounding);
            } else {
                presenter.bill(order.getBillid(), App.INSTANCE().getShopID(), order.getTableId(), memberId, weixinOrderBean.getYuanjia(), weixinOrderBean.getCanju(), qStr
                        , tStr, pStr, order.getPeopleCount(), result, order.getTableName(), free, "7", guiId, "", mading, rounding);
            }
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new BillPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        toolbar.setTitle("结算");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type = getIntent().getIntExtra(Config.PARAM5, 0);
        switch (type) {
            case P1://桌台
                scanBtn.setVisibility(View.VISIBLE);
                position = getIntent().getIntExtra(Config.PARAM1, 0);
                foodMoney = getIntent().getDoubleExtra(Config.PARAM2, 0);
                order = (Order) getIntent().getSerializableExtra(Config.PARAM3);
                list = (List<OrderDetailFood>) getIntent().getSerializableExtra(Config.PARAM4);
                billId = order.getBillid();
                break;
            case P2:
                position = getIntent().getIntExtra(Config.PARAM1, 0);
                foodMoney = getIntent().getDoubleExtra(Config.PARAM2, 0);
                order = (Order) getIntent().getSerializableExtra(Config.PARAM3);
                list = (List<OrderDetailFood>) getIntent().getSerializableExtra(Config.PARAM4);
                billId = order.getBillid();
                break;
            case P5://并单
                foodMoney = getIntent().getDoubleExtra(Config.PARAM2, 0);
                order = (Order) getIntent().getSerializableExtra(Config.PARAM3);
                list = (List<OrderDetailFood>) getIntent().getSerializableExtra(Config.PARAM4);
                billId = order.getBillid();
                break;
            case P3://快餐
                scanBtn.setVisibility(View.VISIBLE);
                foodMoney = (int) getIntent().getDoubleExtra(Config.PARAM2, 0);
                tableEntity = (TableEntity) getIntent().getSerializableExtra(Config.PARAM3);
                billId = getIntent().getStringExtra(Config.PARAM1);
                tabName = getIntent().getStringExtra("TabName");
                break;
            case P6://反结账
                foodMoney = getIntent().getDoubleExtra(Config.PARAM2, 0);
                order = (Order) getIntent().getSerializableExtra(Config.PARAM3);
                billId = order.getBillid();
                break;
        }
        Log.i("ttt", "order:" + order);

        if (type == P3 || type == P4) {
            if (tableEntity != null) {
                toolbar.setTitle("结算-" + tableEntity.getTableName());
            }
        } else {
            if (tableEntity != null) {
                toolbar.setTitle("结算-" + order.getTableName());
            }
        }
        initRVIew();

        if (App.INSTANCE().getUser().getPermissionValue().contains("quanxianmiandan")) {
            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    dazhe.setText("");
                    jianMian.setText("");
                    llDazhe.setEnabled(false);
                    jianMian.setEnabled(false);
                    youhuiMoney = weixinOrderBean.getYinfu();
                    idazhe = 0;
                    ijianmian = 0;
                } else {
                    youhuiMoney = 0;
                    jianMian.setEnabled(true);
                    llDazhe.setEnabled(true);
                }


                initPay();
                getNeed();
                intText();
            });
        }

        presenter.getFoodDetail(billId);
        if (order == null) {
            presenter.getOrderData(billId, "4");
        } else {
            presenter.getOrderData(billId, order.getType());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (type == P1) {
                presenter.cancelBill(billId);
            } else {
                finish();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private Double zhifubaoScanMoney;

    private void initRVIew() {
        adapter = new BillAdapter(R.layout.item_bill);
        recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(BillActivity.this).sizeResId(R.dimen._20sdp)
                .colorResId(R.color.white).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(BillActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((baseQuickAdapter, view, p) -> {
            PayMeEntity e = adapter.getItem(p);
            switch (e.getName()) {
                case "主扫微信":
                    final Intent[] intent = {new Intent(BillActivity.this, CaptureActivity.class)};
                    startActivityForResult(intent[0], REQUEST_CODE);
                    break;
                case "主扫支付宝":
                    AlertDialog.Builder builder = new AlertDialog.Builder(BillActivity.this);
                    builder.setTitle("设置金额");
                    AppCompatEditText editText = new AppCompatEditText(BillActivity.this);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editText.setHint("最多输入" + (needMoney + e.getMoney()));
                    editText.setText((needMoney + e.getMoney() + ""));
                    editText.setSelection(editText.length());
                    builder.setView(editText);
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            e.setMoney(0);
                            e.setSelected(false);
                            getNeed();
                            intText();
                            //adapter.notifyItemChanged(p, e);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setPositiveButton("确定", null);
                    AlertDialog dialog = builder.create();
                    if (!switchCompat.isChecked()) {
                        if (needMoney == 0 && e.isSelected()) {
                            dialog.show();
                        } else if (needMoney > 0) {
                            dialog.show();
                        }
                    }
                    if (dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                                error("请输入正确的价格");
                            } else if (Double.parseDouble(editText.getText().toString().trim()) > needMoney + e.getMoney()) {
                                error("最多输入" + (needMoney + e.getMoney()));
                            } else {
                                double d = Double.parseDouble(editText.getText().toString());
                                if (d <= e.getMoney() && d >= 0) {
                                    if (d == 0) {
                                        e.setSelected(false);
                                    } else {
                                        e.setSelected(true);
                                        zhifubaoScanMoney = d;
                                        Intent intent1 = new Intent(BillActivity.this, CaptureActivity.class);
                                        startActivityForResult(intent1, REQUEST_CODE_ZFB);
                                    }
                                    e.setMoney(d);
                                    getNeed();
                                    intText();
                                    //adapter.notifyItemChanged(p, e);
                                    adapter.notifyDataSetChanged();
                                } else if (d <= (needMoney + e.getMoney()) && d >= 0) {
                                    if (d == 0) {
                                        e.setSelected(false);
                                    } else {
                                        e.setSelected(true);
                                        zhifubaoScanMoney = d;
                                        Intent intent1 = new Intent(BillActivity.this, CaptureActivity.class);
                                        startActivityForResult(intent1, REQUEST_CODE_ZFB);
                                    }
                                    e.setMoney(d);
                                    getNeed();
                                    intText();
                                    //adapter.notifyItemChanged(p, e);
                                    adapter.notifyDataSetChanged();
                                }
                                dialog.dismiss();
                            }
                        });
                    }
//                    startActivityForResult(new Intent(BillActivity.this, CaptureActivity.class), REQUEST_CODE_ZFB);
                    break;
                case "被扫微信":
                    if (!switchCompat.isChecked()) {
                        if (needMoney == 0 && e.isSelected()) {
                            presenter.getimage(p, e);
                        } else if (needMoney > 0) {
                            presenter.getimage(p, e);
                        }
                    }
                    break;
                case "挂账":
                    presenter.guazhang(e);
                    break;
                case "会员卡":
                    List<PayMeEntity> data = adapter.getData();
                    for (PayMeEntity payMeEntity : data) {
                        payMeEntity.setMoney(0);
                        payMeEntity.setSelected(false);
                    }
                    e.setMoney(getYinfuMoney());
                    e.setSelected(true);
                    getNeed();
                    intText();
                    adapter.notifyDataSetChanged();
                    memberPayEntity = e;
                    poptype = 2;
                    cardClick();
                    break;
                default:
                    builder = new AlertDialog.Builder(BillActivity.this);
                    builder.setTitle("设置金额");
                    editText = new AppCompatEditText(BillActivity.this);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editText.setHint("最多输入" + (needMoney + e.getMoney()));
                    editText.setText((needMoney + e.getMoney() + ""));
                    editText.setSelection(editText.length());
                    builder.setView(editText);
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            e.setMoney(0);
                            e.setSelected(false);
                            getNeed();
                            intText();
                            //adapter.notifyItemChanged(p, e);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setPositiveButton("确定", null);
                    dialog = builder.create();
                    if (!switchCompat.isChecked()) {
                        if (needMoney == 0 && e.isSelected()) {
                            dialog.show();
                        } else if (needMoney > 0) {
                            dialog.show();
                        }
                    }
                    if (dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                                error("请输入正确的价格");
                            } else if (Double.parseDouble(editText.getText().toString().trim()) > needMoney + e.getMoney()) {
                                error("最多输入" + (needMoney + e.getMoney()));
                            } else {
                                double d = Double.parseDouble(editText.getText().toString());
                                if (d <= e.getMoney() && d >= 0) {
                                    if (d == 0) {
                                        e.setSelected(false);
                                    } else {
                                        e.setSelected(true);
                                    }
                                    e.setMoney(d);
                                    getNeed();
                                    intText();
                                    //adapter.notifyItemChanged(p, e);
                                    adapter.notifyDataSetChanged();
                                } else if (d <= (needMoney + e.getMoney()) && d >= 0) {
                                    if (d == 0) {
                                        e.setSelected(false);
                                    } else {
                                        e.setSelected(true);
                                    }
                                    e.setMoney(d);
                                    getNeed();
                                    intText();
                                    //adapter.notifyItemChanged(p, e);
                                    adapter.notifyDataSetChanged();
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                    break;
            }
        });
    }

    PayMeEntity memberPayEntity;

    @OnClick(R.id.bill_detail)
    public void foodDetailClick() {
        if (list == null || list.size() == 0) {
            presenter.getFoodDetail(billId);
        } else {
            View laheiView = LayoutInflater.from(this).inflate(R.layout.pop_fooddetail, null);
            PopupWindow laheiPop = new PopupWindow(laheiView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            RecyclerView recyclerView = (RecyclerView) laheiView.findViewById(R.id.pop_recycler);
            MenuListAdapter menuListAdapter = new MenuListAdapter(R.layout.item_menu_list, BillActivity.this, true);
            /*View header = LayoutInflater.from(this).inflate(R.layout.item_menu_list, (ViewGroup) recyclerView.getParent(), false);
            menuListAdapter.addHeaderView(header);*/

            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                    .color(Color.parseColor("#EEEEEE")).build());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(menuListAdapter);
            menuListAdapter.setNewData(list);
            menuListAdapter.setOnItemChildClickListener((adapter1, view, position1) -> {
                if (view.getId() == R.id.etSale) {
                    if (!App.INSTANCE().getUser().getPermissionValue().contains("quanxiandazhe")) {
                        warning("没有打折权限");
                        return;
                    }
                    OrderDetailFood item = menuListAdapter.getData().get(position1);
                    AlertDialog.Builder builder = new AlertDialog.Builder(BillActivity.this);
                    builder.setTitle("设置折扣");
                    View view1 = LayoutInflater.from(BillActivity.this).inflate(R.layout.dialog_bill_da_zhe, null);
                    AppCompatImageView imageView = (AppCompatImageView) view1.findViewById(R.id.imageView);
                    AppCompatEditText editText = (AppCompatEditText) view1.findViewById(R.id.editText);
                    builder.setView(view1);
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Tools.hideSoftKeyboard(BillActivity.this, editText);
                            item.setSale(0);
                            dialog.dismiss();
                            menuListAdapter.notifyDataSetChanged();

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            Tools.hideSoftKeyboard(BillActivity.this, editText);
                            int saleNum = 0;
                            if (!TextUtils.isEmpty(editText.getText().toString())) {
                                saleNum = Integer.parseInt(editText.getText().toString());
                            }
                            if (saleNum > 0 && saleNum < 100) {
                                item.setSale(saleNum);

                                for (OrderDetailFood orderDetailFood : list) {
                                    if (orderDetailFood.getSale() > 0) {
                                        idazhe += orderDetailFood.getPrice() * ((100.0 - orderDetailFood.getSale()) / 100.0);
                                    }
                                }
                                if (idazhe > 0) {
                                    haveOneDaZhe = true;
                                    ijianmian = 0;
                                    jianMian.setText("");
                                    youhuiMoney = (ijianmian + idazhe);

                                    initPay();
                                    getNeed();
                                    intText();
                                }
                            } else {
                                warning("请输入正确的打折数");
                                item.setSale(0);
                            }
                            dialog.dismiss();
                            menuListAdapter.notifyDataSetChanged();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            laheiPop.setOutsideTouchable(true);
            laheiPop.setFocusable(true);
            laheiPop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
            laheiPop.setOnDismissListener(() -> {
                backgroundAlpha(1f);
            });
            backgroundAlpha(0.5f);
            laheiPop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                    | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    @Override
    public void showDetail(Order order, List<OrderDetailFood> orderDetailFoods) {
        Log.i("ttt", "--order:" + order);
        if (this.order == null) {
            this.order = order;
        }
        if (orderDetailFoods == null || orderDetailFoods.size() == 0) {
            return;
        }
        list = orderDetailFoods;

        intText();
    }

    private void getNeed() {
        if (weixinOrderBean == null) {
            return;
        }
        double result = getYinfuMoney();
        double count = 0;
        for (PayMeEntity entity : adapter.getData()) {
            count += entity.getMoney();
        }
        needMoney = result - count;
    }

    private double getYouhuiMoney() {
        return youhuiMoney + cardMoney + scoreMoney + couponLineYouhuiMoney + youhuiMoneyOfMember;
    }

    private double getYinfuMoney() {
        DecimalFormat df = new DecimalFormat("0.00");
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        nf.setMaximumFractionDigits(2);
        double yinFu = weixinOrderBean.getYuanjia() - weixinOrderBean.getYufupice() - weixinOrderBean.getYouhui() - getYouhuiMoney();
        Log.d("ttt", "预付：" + weixinOrderBean.getYufupice() + "优惠：" + weixinOrderBean.getYouhui() + "餐具：" + weixinOrderBean.getCanju());
        Log.d("ttt", "应付：" + df.format(yinFu));
        double yinfu = Double.parseDouble(df.format(yinFu));
        if (yinfu < 0) {
            return 0;
        } else {
            return yinfu;
        }
    }

    private void intText() {
        if (weixinOrderBean == null) {
            return;
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        BigDecimal yuanjia = new BigDecimal(weixinOrderBean.getYuanjia()).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal canju = new BigDecimal(weixinOrderBean.getCanju()).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal youhui = new BigDecimal(getYouhuiMoney() + weixinOrderBean.getYufupice() + weixinOrderBean.getYouhui()).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal yintui = new BigDecimal(-weixinOrderBean.getYintui()).setScale(2, BigDecimal.ROUND_DOWN);
//        BigDecimal yinfu = new BigDecimal(getYinfuMoney()).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal need = new BigDecimal(needMoney).setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal yuFuPice = new BigDecimal(weixinOrderBean.getYufupice()).setScale(2, BigDecimal.ROUND_DOWN);

        tv1.setText("原价：￥" + nf.format(weixinOrderBean.getYuanjia()));
        tv2.setText("餐具：￥" + nf.format(weixinOrderBean.getCanju()));
        tv3.setText("优惠：￥" + nf.format(getYouhuiMoney() + weixinOrderBean.getYouhui()));
        if(weixinOrderBean.getYintui()<0){
            tv5.setText("应退：￥" + nf.format(-weixinOrderBean.getYintui()));
        }else {
            tv5.setText("应退：￥" + nf.format(weixinOrderBean.getYintui()));
        }
        tv4.setText("应付：￥" + getYinfuMoney());
        tv6.setText("还需支付：￥" + nf.format(needMoney));
        tv7.setText("预定金：￥" + nf.format(weixinOrderBean.getYufupice()));
    }

    private void initPay() {
        if (weixinOrderBean == null) {
            return;
        }
//        double result = weixinOrderBean.getYuanjia() - weixinOrderBean.getYufupice() - weixinOrderBean.getYouhui() - getYouhuiMoney();
        double result = getYinfuMoney();
//        Log.d("dj", "开始：" + result);
//        BigDecimal resultBD = new BigDecimal(result).setScale(2, BigDecimal.ROUND_DOWN);
//        result = Double.parseDouble(resultBD + "");
//        Log.d("dj", "后来：" + result);
        String[] payTypes = getResources().getStringArray(R.array.payType);
        List<PayMeEntity> datas = new ArrayList<>();
        for (int i = 0; i < payTypes.length; i++) {
            if (App.INSTANCE().getUser().getCashPayType().contains(i + 1 + "")) {
                String anA = payTypes[i];
                if (anA.equals("现金")) {
                    datas.add(new PayMeEntity(anA, true, result, i + 1));
                } else {
                    datas.add(new PayMeEntity(anA, false, i + 1));
                }
            }

        }
        adapter.setNewData(datas);
    }

    String guiId = "";

    @Override
    public void successOfGetCouponLine(List<CouponLineDownBean> couponLineDownBeans) {
        CouponLineDialog.Builder builder = new CouponLineDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("请选择线下券");
        builder.setReasons(couponLineDownBeans);
        builder.setBtnStr("确定");
        builder.setText((needMoney + ""));
        couponLineDialog = builder.creater();
        couponLineYouhuiMoney = 0.0;
        builder.setButtonClick(new CouponLineDialog.OnButtonClick() {
            @Override
            public void onBtnClick(List<CouponLineDownBean> list) {
                if (list == null) {
//                    couponLineYouhuiMoney += bean.getPice();
//                    tvCouponOther.setText(String.valueOf(couponLineYouhuiMoney));
                    /* if (money == 0) {
                        guiId = "";
                    } else {
                        guiId = bean.getGuid();
                    }
                    getNeed();
                    intText();
                    adapter.notifyDataSetChanged();*/
                } else {
                    couponLineDownBeanList = list;
                    String couponId = "";
                    for (CouponLineDownBean bean : list) {
                        couponLineYouhuiMoney += bean.getPice();
                        couponId += bean.getGuid() + ",";
                    }
                    couponId = couponId.substring(0, couponId.length() - 1);

                    BillJson.Quanxian quanxian = new BillJson.Quanxian();
                    List<BillJson.BillJsonBase> q = new ArrayList<>();
                    if (couponLineYouhuiMoney > 0) {
                        for (int k = 0; k < couponLineDownBeanList.size(); k++) {
                            CouponLineDownBean entity = couponLineDownBeanList.get(k);
                            if (entity != null) {
                                BillJson.BillJsonBase pe = new BillJson.BillJsonBase();
                                pe.setGuid(System.currentTimeMillis() + "");
                                pe.setPice(entity.getPice() + "");
                                pe.setPiceGuid("");
                                pe.setSate(entity.getName() + "");
                                pe.setType(5 + "");
                                pe.setIsSql(entity.getGuid() + "");
                                q.add(pe);
                            }
                        }
                    }
                    quanxian.setQuanxian(q);
                    String qStr = new Gson().toJson(quanxian);
                    presenter.getOtherYouhui(couponId, order.getBillid(), couponLineYouhuiMoney, getYinfuMoney() + couponLineYouhuiMoney, qStr);
                    guiId = "";
                   /* initPay();
                    getNeed();
                    intText();
                    adapter.notifyDataSetChanged();*/
                }
                couponLineDialog.dismiss();
            }

            @Override
            public void onCancel() {
                guiId = "";
                couponLineYouhuiMoney = 0.0;
                initPay();
                tvCouponOther.setText(String.valueOf(couponLineYouhuiMoney));
                getNeed();
                intText();
                adapter.notifyDataSetChanged();
            }
        });
        couponLineDialog.show();
    }

    @Override
    public void guazhangSuccess(List<GuaZhangBean> result, PayMeEntity entity) {
        GuaZhangDialog.Builder builder = new GuaZhangDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("设置金额");
        builder.setReasons(result);
        builder.setBtnStr("确定");
        builder.setHint("最多输入" + (needMoney + entity.getMoney()));
        builder.setText((needMoney + entity.getMoney() + ""));
        guaZhangDialog = builder.creater();
        builder.setButtonClick(new GuaZhangDialog.OnButtonClick() {
            @Override
            public void onBtnClick(double money, GuaZhangBean bean) {
                if (money <= (needMoney + entity.getMoney()) && money >= 0) {
                    if (money == 0) {
                        entity.setSelected(false);
                        guiId = "";
                    } else {
                        guiId = bean.getGuid();
                        entity.setSelected(true);
                    }
                    entity.setMoney(money);
                    getNeed();
                    intText();
                    adapter.notifyDataSetChanged();
                } else {
                    guiId = "";
                    entity.setMoney(0);
                    entity.setSelected(false);
                    getNeed();
                    intText();
                    adapter.notifyDataSetChanged();
                }
                guaZhangDialog.dismiss();
            }

            @Override
            public void onCancel() {
                guiId = "";
                entity.setMoney(0);
                entity.setSelected(false);
                getNeed();
                intText();
                adapter.notifyDataSetChanged();
            }
        });
        guaZhangDialog.show();
    }

    @Override
    public void saleSuccess(MemberBean member, List<CardBean> cardBeens) {
        this.memberBean = member;
        cardBeenList.clear();
        if (weixinOrderBean == null || (weixinOrderBean.getMansonpice() == 0 && weixinOrderBean.getKaquanpice() == 0)) {
            cardBeenList.addAll(cardBeens);
        }
        //cardBeenList.addAll(cardBeens);

        if (poptype == 2) {
            if (memberPayEntity.getMoney() > memberBean.getMoney()) {
                showToast("会员余额不足");
                memberPayEntity.setMoney(memberBean.getMoney());
                adapter.notifyDataSetChanged();
                getNeed();
                intText();
            }
        }

        llMember.setVisibility(View.VISIBLE);
        if (memberBean.getRate() != 0 && poptype == 1) {
            llEdit.setVisibility(View.VISIBLE);
            editText.setHint("最多可兑换" + (int) ((getYinfuMoney() + scoreMoney) / memberBean.getRate()) + "积分");
            if (scoreCount > 0) {
                editText.setText(scoreCount + "");
            }
        } else {
            llEdit.setVisibility(View.GONE);
        }

        tvNo.setText("会员编号：" + memberBean.getNo());
        tvName.setText("会员姓名：" + memberBean.getName());
        tvPhone.setText("手机号码：" + memberBean.getPhone());
        tvScore.setText("剩余积分：" + memberBean.getScore());
        tvMoney.setText("会员余额：" + memberBean.getMoney());

        popSaleAdapter.notifyDataSetChanged();
    }

    WeixinOrderBean weixinOrderBean;

    @Override
    public void success(List<WeixinOrderBean> beans) {
        this.weixinOrderBean = beans.get(0);
        if (weixinOrderBean == null) {
            showFailToast("获取数据失败");
            //finish();
            return;
        }
        initPay();
        getNeed();
        intText();
    }

    @Override
    public void fail() {
        showFailToast("获取数据失败");
        //finish();
    }

    @Override
    public void success(String msg) {
        showSuccessToast(msg);
    }

    @Override
    public void cancelSuccess() {
        finish();
    }


    @Override
    public void warning(String s) {
        Toasty.warning(this, s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void error(String s) {
        Toasty.error(this, s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void billSuccess(String s, String result) {
        Toasty.success(this, s, Toast.LENGTH_SHORT, true).show();
        if (type == P1) {
            MsgEvent event = new MsgEvent(MsgEvent.bill, position);
            EventBus.getDefault().post(event);
        } else if (type == P2) {
            MsgEvent event = new MsgEvent(MsgEvent.billSuccess);
            EventBus.getDefault().post(event);
        } else if (type == P3) {
            MsgEvent msgEvent = new MsgEvent(MsgEvent.kuaicanSuccess);
            EventBus.getDefault().post(msgEvent);
        } else if (type == P4) {
            MsgEvent msgEvent = new MsgEvent(MsgEvent.waimaiSuccess);
            EventBus.getDefault().post(msgEvent);
        } else if (type == P5) {
            MsgEvent msgEvent = null;
            if (order != null) {
                msgEvent = new MsgEvent(MsgEvent.mergeSuccess, order.getTableId());
            } else if (tableEntity != null) {
                msgEvent = new MsgEvent(MsgEvent.mergeSuccess, tableEntity.getRoomTableID());
            }
            EventBus.getDefault().post(msgEvent);
        } else if (type == P6) {
            MsgEvent msgEvent = new MsgEvent(MsgEvent.reBillSuccess, order.getTableId());
            EventBus.getDefault().post(msgEvent);
        }
        finish();
    }

    @Override
    public void canJuFei(double i) {
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMsgEvent(MsgEvent event) {
        idazhe = 0;
        if (event.getType() == MsgEvent.oneSale) {
            for (OrderDetailFood orderDetailFood : list) {
                if (orderDetailFood.getSale() > 0) {
                    idazhe += orderDetailFood.getPrice() * ((100 - orderDetailFood.getSale()) / 100);
                }
            }
            if (idazhe > 0) {

                haveOneDaZhe = true;
                ijianmian = 0;
                jianMian.setText("");
                youhuiMoney = (ijianmian + idazhe);

                initPay();
                getNeed();
                intText();
            }
        }
    }

    @Override
    public void dazheSucccess(double aDouble) {
        Toasty.success(this, "打折优惠价格为" + aDouble, Toast.LENGTH_SHORT, true).show();
        idazhe = aDouble;
        Log.d("dj", "dazhe:" + aDouble);
        if (aDouble > 0) {
            haveQXDaZhe = true;
            ijianmian = 0;
            jianMian.setText("");
            youhuiMoney = (ijianmian + idazhe);

            initPay();
            getNeed();
            intText();
        }
    }

    @Override
    public void otherYouhuiSucccess(double aDouble) {
        Log.d("dj", "otherYouhui:" + aDouble);
        couponLineYouhuiMoney = aDouble;
        tvCouponOther.setText(String.valueOf(couponLineYouhuiMoney));
        initPay();
        getNeed();
        intText();
        adapter.notifyDataSetChanged();
    }

    private void scanBill(String payType, String result, double money, String memberId) {
        List<BillJson.BillJsonBase> t = new ArrayList<>();
        BillJson.BillJsonBase base = new BillJson.BillJsonBase();
        t.add(base);
        BillJson.TeacherJson teacherJson = new BillJson.TeacherJson();
        teacherJson.setTeacher(t);
        String tStr = new Gson().toJson(teacherJson);


        BillJson.Quanxian quanxian = new BillJson.Quanxian();
        List<BillJson.BillJsonBase> q = new ArrayList<>();
        q.add(base);
        //免单 3
        if (idazhe > 0) {
            BillJson.BillJsonBase d = new BillJson.BillJsonBase();
            d.setGuid(System.currentTimeMillis() + "");
            d.setPice(idazhe + "");
            if (haveOneDaZhe) {
                d.setType("6");
            } else {
                d.setType("1");
            }
            q.add(d);
        }
        if (ijianmian > 0) {
            BillJson.BillJsonBase j = new BillJson.BillJsonBase();
            j.setGuid(System.currentTimeMillis() + "");
            j.setPice(ijianmian + "");
            j.setType("2");
            q.add(j);
        }
        if (switchCompat.isChecked()) {
            BillJson.BillJsonBase m = new BillJson.BillJsonBase();
            m.setGuid(System.currentTimeMillis() + "");
            m.setPice(youhuiMoney + "");
            m.setType("3");
            q.add(m);
        }
        if (couponLineYouhuiMoney > 0) {
            for (int k = 0; k < couponLineDownBeanList.size(); k++) {
                CouponLineDownBean entity = couponLineDownBeanList.get(k);
                if (entity != null) {
                    BillJson.BillJsonBase pe = new BillJson.BillJsonBase();
                    pe.setGuid(System.currentTimeMillis() + "");
                    pe.setPice(entity.getPice() + "");
                    pe.setPiceGuid("");
                    pe.setSate(entity.getName() + "");
                    pe.setType(5 + "");
                    pe.setIsSql(entity.getGuid() + "");
                    q.add(pe);
                }
            }
        }
        quanxian.setQuanxian(q);
        String qStr = new Gson().toJson(quanxian);


        BillJson.Pays pays = new BillJson.Pays();
        List<BillJson.BillJsonBase> p = new ArrayList<>();
        p.add(base);

        BillJson.BillJsonBase pe = new BillJson.BillJsonBase();
        pe.setGuid(System.currentTimeMillis() + "");
        pe.setPice(getYinfuMoney() + "");
        pe.setPiceGuid(payType);
        p.add(pe);
        pays.setQuanxian(p);
        String pStr = new Gson().toJson(pays);
        if (TextUtils.isEmpty(memberId)) {
            memberId = memberBean == null ? "" : memberBean.getId();
        }

        double free = getYouhuiMoney() + weixinOrderBean.getYufupice() + weixinOrderBean.getYouhui();


        if (type == P3) {
            presenter.bill(billId, App.INSTANCE().getShopID(), tableEntity != null ? tableEntity.getRoomTableID() : "",
                    memberId, foodMoney, weixinOrderBean.getCanju(), qStr
                    , tStr, pStr, 1, getYinfuMoney(), tableEntity != null ? tableEntity.getTableName() : "", free, "4", guiId, payType, mading, rounding);
        } else if (type == P1) {
            presenter.bill(billId, App.INSTANCE().getShopID(), order != null ? order.getTableId() : "", memberId, foodMoney, weixinOrderBean.getCanju(), qStr
                    , tStr, pStr, 1, getYinfuMoney(), order != null ? order.getTableName() : "", free, "7", guiId, payType, mading, rounding);
        }
    }

    @Override
    public void scanBillSuccess(String payType, String result, double money, final String memberId, String str) {
        if (str.contains("支付中")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("用户正在支付中，是否确认已支付");
            AlertDialog.Builder 确定 = builder.setPositiveButton("确定", (dialog, which) -> {
                scanBill(payType, result, money, memberId);
            });
            builder.setCancelable(false);
            builder.setNegativeButton("取消", null);
            builder.show();
        } else {
            scanBill(payType, result, money, memberId);
        }

    }

    @Override
    public void weixinSuccess() {

        double result = weixinOrderBean.getYuanjia() - weixinOrderBean.getYufupice() - weixinOrderBean.getYouhui() - getYouhuiMoney();

        List<BillJson.BillJsonBase> t = new ArrayList<>();
        BillJson.BillJsonBase base = new BillJson.BillJsonBase();
        t.add(base);
        BillJson.TeacherJson teacherJson = new BillJson.TeacherJson();
        teacherJson.setTeacher(t);
        String tStr = new Gson().toJson(teacherJson);


        BillJson.Quanxian quanxian = new BillJson.Quanxian();
        List<BillJson.BillJsonBase> q = new ArrayList<>();
        q.add(base);
        //免单 3
        if (idazhe > 0) {
            BillJson.BillJsonBase d = new BillJson.BillJsonBase();
            d.setGuid(System.currentTimeMillis() + "");
            d.setPice(idazhe + "");
            if (haveOneDaZhe) {
                d.setType("6");
            } else {
                d.setType("1");
            }
            q.add(d);
        }
        if (ijianmian > 0) {
            BillJson.BillJsonBase j = new BillJson.BillJsonBase();
            j.setGuid(System.currentTimeMillis() + "");
            j.setPice(ijianmian + "");
            j.setType("2");
            q.add(j);
        }
        if (switchCompat.isChecked()) {
            BillJson.BillJsonBase m = new BillJson.BillJsonBase();
            m.setGuid(System.currentTimeMillis() + "");
            m.setPice(youhuiMoney + "");
            m.setType("3");
            q.add(m);
        }
        if (couponLineYouhuiMoney > 0) {
            for (int k = 0; k < couponLineDownBeanList.size(); k++) {
                CouponLineDownBean entity = couponLineDownBeanList.get(k);
                if (entity != null) {
                    BillJson.BillJsonBase pe = new BillJson.BillJsonBase();
                    pe.setGuid(System.currentTimeMillis() + "");
                    pe.setPice(entity.getPice() + "");
                    pe.setPiceGuid("");
                    pe.setSate(entity.getName() + "");
                    pe.setType(5 + "");
                    pe.setIsSql(entity.getGuid() + "");
                    q.add(pe);
                }
            }
        }
        quanxian.setQuanxian(q);
        String qStr = new Gson().toJson(quanxian);


        BillJson.Pays pays = new BillJson.Pays();
        List<BillJson.BillJsonBase> p = new ArrayList<>();
        p.add(base);

        BillJson.BillJsonBase pe = new BillJson.BillJsonBase();
        pe.setGuid(System.currentTimeMillis() + "");
        pe.setPice(result + "");
        pe.setPiceGuid("7");
        p.add(pe);
        pays.setQuanxian(p);
        String pStr = new Gson().toJson(pays);


        memberId = memberBean == null ? "" : memberBean.getId();

        double free = getYouhuiMoney() + weixinOrderBean.getYufupice() + weixinOrderBean.getYouhui();


        if (type == P3) {
            presenter.bill(billId, App.INSTANCE().getShopID(), tableEntity != null ? tableEntity.getRoomTableID() : "",
                    memberId, foodMoney, weixinOrderBean.getCanju(), qStr
                    , tStr, pStr, 1, result, tableEntity != null ? tableEntity.getTableName() : "", free, "4", guiId, "", mading, rounding);
        } else if (type == P4) {
            presenter.bill(billId, App.INSTANCE().getShopID(), tableEntity != null ? tableEntity.getRoomTableID() : "", memberId, foodMoney, weixinOrderBean.getCanju(), qStr
                    , tStr, pStr, 1, result, tableEntity != null ? tableEntity.getTableName() : "", free, "3", guiId, "", mading, rounding);
        } else {
            presenter.bill(order.getBillid(), App.INSTANCE().getShopID(), order.getTableId(), memberId, foodMoney, weixinOrderBean.getCanju(), qStr
                    , tStr, pStr, order.getPeopleCount(), result, order.getTableName(), free, "7", guiId, "", mading, rounding);
        }
    }

    @Override
    public void getImageSuccess(int p, String result, PayMeEntity e) {
        Timber.d("sssssssssss" + result);
        AlertDialog.Builder builder = new AlertDialog.Builder(BillActivity.this);
        builder.setTitle("设置金额");
        View view = LayoutInflater.from(BillActivity.this).inflate(R.layout.dialog_code_image, null);
        AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.imageView);
        AppCompatEditText editText = (AppCompatEditText) view.findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("最多输入" + (needMoney + e.getMoney()));
        editText.setText((needMoney + e.getMoney() + ""));
        Glide.with(BillActivity.this).load(result).into(imageView);
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                e.setMoney(0);
                e.setSelected(false);
                getNeed();
                intText();
                //adapter.notifyItemChanged(p, e);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setPositiveButton("确定", null);
        AlertDialog dialog = builder.create();
        if (!switchCompat.isChecked()) {
            if (needMoney == 0 && e.isSelected()) {
                dialog.show();
            } else if (needMoney > 0) {
                dialog.show();
            }
        }

        if (dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    error("请输入正确的价格");
                } else if (Double.parseDouble(editText.getText().toString()) > needMoney + e.getMoney()) {
                    error("最多输入" + (needMoney + e.getMoney()));
                } else {
                    double d = Double.parseDouble(editText.getText().toString());
                    if (d <= e.getMoney() && d >= 0) {
                        if (d == 0) {
                            e.setSelected(false);
                        } else {
                            e.setSelected(true);
                        }
                        e.setMoney(d);

                        getNeed();
                        intText();
                        //adapter.notifyItemChanged(p, e);
                        adapter.notifyDataSetChanged();
                    } else if (d <= (needMoney + e.getMoney()) && d >= 0) {
                        if (d == 0) {
                            e.setSelected(false);
                        } else {
                            e.setSelected(true);
                        }
                        e.setMoney(d);
                        getNeed();

                        intText();
                        //adapter.notifyItemChanged(p, e);
                        adapter.notifyDataSetChanged();
                    }

                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void showDazhe(List<DaZheEntity> daZheEntityList) {
        DaZheDialog.Builder builder = new DaZheDialog.Builder(this, R.style.OrderDialogStyle);
        builder.setTitle("打折");
        builder.setReasons(daZheEntityList);
        builder.setBtnStr("确定");
        builder.setButtonClick(new DaZheDialog.OnButtonClick() {
            @Override
            public void onBtnClick(int i) {
                if (type == 3) {
                    presenter.getDazhe(billId, i, "");
                } else presenter.getDazhe(order.getBillid(), i, "");

                dazhe.setText(i + "");
                builder.dismiss();
            }

            @Override
            public void onItemClick(int i, String dazheId) {
                if (type == 3) {
                    presenter.getDazhe(billId, i, dazheId);
                } else presenter.getDazhe(order.getBillid(), i, dazheId);
                dazhe.setText(i + "");
                builder.dismiss();
            }

            @Override
            public void onCancel() {
                dazhe.setText("");
                idazhe = 0;
                youhuiMoney = ijianmian + idazhe;
                initPay();
                getNeed();
                intText();
                builder.dismiss();
            }
        });
        builder.creater().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bill, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (type == P1) {
                    presenter.cancelBill(billId);
                } else {
                    finish();
                }
                break;
            case R.id.action_detail:
                foodDetailClick();
                break;
            case R.id.action_card:
                cardBeenList.clear();
                cardMoney = 0;
                getNeed();
                intText();
                initPay();
                break;
            case R.id.action_score:
                scoreMoney = 0;
                getNeed();
                intText();
                initPay();
                break;
            case R.id.action_dazhe:
                idazhe = 0;
                youhuiMoney = ijianmian;
                getNeed();
                intText();
                initPay();
                dazhe.setText("0");
                break;
            case R.id.action_jianmian:
                ijianmian = 0;
                youhuiMoney = ijianmian + idazhe;
                jianMian.setText(ijianmian + "");
                intText();
                initPay();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        if (requestCode == 5) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                presenter.weixinbill(billId, result, getYinfuMoney());
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                warning("解析二维码失败");
            }
        } else if (requestCode == REQUEST_CODE_ZFB) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                presenter.scanBill(result, zhifubaoScanMoney, billId);
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                warning("解析二维码失败");
            }
        } else if (requestCode == 8) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                presenter.searchMember(billId, result);
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                warning("解析二维码失败");
            }
        } else if (requestCode == REQUEST_CODE_3MaHE1) {
            Bundle bundle = data.getExtras();
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                presenter.scanBill(result, getYinfuMoney(), billId);
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                warning("解析二维码失败");
            }
        }
    }
}
