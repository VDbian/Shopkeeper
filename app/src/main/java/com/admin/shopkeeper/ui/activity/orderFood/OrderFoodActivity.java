package com.admin.shopkeeper.ui.activity.orderFood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.BottomSheetFoodAdapter;
import com.admin.shopkeeper.adapter.ContactAdapter;
import com.admin.shopkeeper.adapter.MenuClassAdapter;
import com.admin.shopkeeper.adapter.OrderFoodAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.dialog.CommonKouWeiDialog;
import com.admin.shopkeeper.dialog.GiveDialog;
import com.admin.shopkeeper.dialog.OrderFoodDialog;
import com.admin.shopkeeper.entity.BillJson;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.OrderfoodEntity;
import com.admin.shopkeeper.entity.Season;
import com.admin.shopkeeper.entity.Spec;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.ui.activity.BigImageActivity;
import com.admin.shopkeeper.ui.activity.bill.BillActivity;
import com.admin.shopkeeper.ui.activity.info.waimai.WaiMaiActivity;
import com.admin.shopkeeper.ui.activity.info.yuding.YuDingActivity;
import com.admin.shopkeeper.ui.activity.messageList.MessageListActivity;
import com.admin.shopkeeper.ui.activity.table.TableOperationActivity;
import com.admin.shopkeeper.weight.QuickIndexBar;
import com.admin.shopkeeper.weight.TouchableRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import q.rorbin.badgeview.QBadgeView;
import timber.log.Timber;

public class OrderFoodActivity extends BaseActivity<OrderFoodPresenter> implements IOrderFoodView {

    public static final int REQUEST_CODE = 5;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public final static int P1 = 1;//堂点
    public final static int P2 = 2;//加菜
    public final static int P3 = 3;//桌台加菜
    public final static int P4 = 4;//外卖
    public final static int P5 = 5;//快餐
    public final static int P6 = 6;//预定
    public final static int P7 = 7;//反结账
    private int type;
    private int position;

    private TableEntity tableEntity;
    private Order order;

    @BindView(R.id.leftRecyclerView)
    RecyclerView left;
    @BindView(R.id.rightRecyclerView)
    TouchableRecyclerView rigth;
    @BindView(R.id.cart)
    AppCompatImageButton cartBtn;
    @BindView(R.id.button)
    AppCompatButton button;
    @BindView(R.id.quickBill)
    AppCompatButton btQuickBill;
    @BindView(R.id.scanBill)
    AppCompatButton btScanBill;
    @BindView(R.id.row_number)
    AppCompatEditText etRowNumber;
    @BindView(R.id.totalMoney)
    AppCompatTextView totalMoney;
    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomSheet;
    @BindView(R.id.queryView)
    RecyclerView queryView;
    @BindView(R.id.queryLayout)
    RelativeLayout queryLayout;
    @BindView(R.id.quickIndexBar)
    QuickIndexBar quickIndexBar;
    @BindView(R.id.top_search)
    FrameLayout clTopSearch;
    @BindView(R.id.food_search)
    AppCompatEditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;

    private BottomSheetFoodAdapter bottomAdapter;
    private QBadgeView badgeView;
    private MenuClassAdapter menuAdapter;
    private OrderFoodAdapter queryAdapter;
    private View bottomSheetView;
    private int currMenuPosition;
    private List<OrderfoodEntity> carts = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private List<OrderDetailFood> detailFoods;
    private Object total;
    double total1 = 0;
    private String tableName;

    @OnClick(R.id.button)
    void onClick(View view) {
        Intent intent;
        switch (type) {
            case P1:
                Log.i("ttt", "---" + tableEntity);
                presenter.orderFood(App.INSTANCE().getShopID(),
                        tableEntity.getRoomTableID(), tableEntity.getBillID(), getInfo(), App.INSTANCE().getUser().getId(),
                        App.INSTANCE().getUser().getName(), tableEntity.getTableName(), tableEntity.getTableWareCount(), getTotal(), "0");
                break;
            case P2:
                presenter.orderFood(App.INSTANCE().getShopID(),
                        order.getTableId(), order.getBillid(), getInfo(), App.INSTANCE().getUser().getId(),
                        App.INSTANCE().getUser().getName(), order.getTableName(), order.getPeopleCount() + "", getTotal(), "1");
                break;
            case P3:
                presenter.orderFood(App.INSTANCE().getShopID(),
                        order.getTableId(), order.getBillid(), getInfo(), App.INSTANCE().getUser().getId(),
                        App.INSTANCE().getUser().getName(), order.getTableName(), order.getPeopleCount() + "", getTotal(), "1");
                break;
            case P5:
                double total = 0;
                for (OrderfoodEntity entity : carts) {
                    if (entity.isShowWeight()) {
                        if (entity.getGivingnum() == 0) {
                            total += entity.getWeight() * entity.getPrice();
                        }
                    } else {
                        total += (entity.getNumber() - entity.getGivingnum()) * entity.getPrice();

                    }
                    if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                        for (Season season : entity.getSeasons()) {
                            if (season.isSelected()) {
                                //total += (entity.getNumber() - entity.getGivingnum()) * season.getPrice();
                                total += (entity.getNumber() - entity.getGivingnum()) * season.getPrice() * season.getCount();
                            }
                        }
                    }
                }
                total1 = total;
                if (App.INSTANCE().getUser().getOperaType().contains("3")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderFoodActivity.this);
                    builder.setTitle("设置桌号");
                    View view1 = LayoutInflater.from(OrderFoodActivity.this).inflate(R.layout.dialog_order_other_bill, null);
                    AppCompatImageView imageView = (AppCompatImageView) view1.findViewById(R.id.imageView);
                    AppCompatEditText editText = (AppCompatEditText) view1.findViewById(R.id.editText);
                    builder.setView(view1);
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(OrderFoodActivity.this, TableOperationActivity.class);
                            intent.putExtra(Config.PARAM1, TableOperationActivity.P5);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tableName = editText.getText().toString();
                            if (!TextUtils.isEmpty(tableName)) {
                                presenter.KuaiSu(getInfo(), "", "", "", "", "", "", total1,
                                        "", tableName, "4", false, false, true,"1");
                            } else {
                                Toasty.warning(OrderFoodActivity.this, "请输入桌号", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    MsgEvent event = new MsgEvent(MsgEvent.kuaican, getInfo(), total);
                    EventBus.getDefault().postSticky(event);
                    if (bottomSheet != null && bottomSheet.isSheetShowing()) {
                        bottomSheet.dismissSheet();
                    }

                } else if (App.INSTANCE().getUser().getOperaType().contains("2")) {
                    presenter.KuaiSu(getInfo(), "", "", "", "", "", "", total, "",
                            etRowNumber.getText().toString().isEmpty()?"":etRowNumber.getText().toString(), "4", false, false, false,"0");
                }

                break;
            case P4:
                double t = 0;
                for (OrderfoodEntity entity : carts) {
                    t += entity.getWeight() * entity.getPrice() + (entity.getNumber() - entity.getGivingnum()) * entity.getPrice();
                    if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                        for (Season season : entity.getSeasons()) {
                            if (season.isSelected()) {
                                //t += season.getPrice();
                                t += season.getPrice() * season.getCount();
                            }
                        }
                    }
                }
                Log.d("ttt", "外卖totalPrice:" + t);
                intent = new Intent(OrderFoodActivity.this, WaiMaiActivity.class);
                intent.putExtra(Config.PARAM1, getInfo());
                intent.putExtra(Config.PARAM2, t);
                startActivity(intent);
                break;
            case P6:
                total = 0;
                for (OrderfoodEntity entity : carts) {
                    if (entity.getWeight() > 0) {
                        if (entity.getGivingnum() == 0) {
                            total += entity.getWeight() * entity.getPrice();
                        }
                    } else {
                        total += (entity.getNumber() - entity.getGivingnum()) * entity.getPrice();
                    }
                    //total += entity.getWeight() * entity.getPrice() + (entity.getNumber() - entity.getGivingnum()) * entity.getPrice();
                    if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                        for (Season season : entity.getSeasons()) {
                            if (season.isSelected()) {
                                //total += season.getPrice();
                                total += season.getPrice() * season.getCount();
                            }
                        }
                    }
                }
                Log.d("ttt", "预定totalPrice:" + total);
                intent = new Intent(OrderFoodActivity.this, YuDingActivity.class);
                intent.putExtra(Config.PARAM1, getInfo());
                intent.putExtra(Config.totalPrice, total);
                startActivity(intent);
                break;
            case P7:
                total = 0;
                for (OrderfoodEntity entity : carts) {
                    if (entity.getWeight() > 0) {
                        if (entity.getGivingnum() == 0) {
                            total += entity.getWeight() * entity.getPrice();
                        }
                    } else {
                        total += (entity.getNumber() - entity.getGivingnum()) * entity.getPrice();
                    }
                    //total += entity.getWeight() * entity.getPrice() + (entity.getNumber() - entity.getGivingnum()) * entity.getPrice();
                    if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                        for (Season season : entity.getSeasons()) {
                            if (season.isSelected()) {
                                total += season.getPrice() * season.getCount();
                                //total += season.getPrice();
                            }
                        }
                    }
                }
                if (order.getType().equals("7")) {
                    presenter.rebillTangdian(order.getTableId(), order.getBillid(), getInfo(), order.getTableName(), total, order.getBillid());
                } else {
                    presenter.reBillKuaiSu(getInfo(), "", "", "", "", "",
                            order.getRemark(), total, order.getTableId(),
                            order.getTableName(), order.getBillid(), "0");
                }
                break;
        }
    }

    @OnClick(R.id.quickBill)
    public void quickClick() {
        double total = 0;
        for (OrderfoodEntity entity : carts) {
            total += entity.getWeight() * entity.getPrice() + (entity.getNumber() - entity.getGivingnum()) * entity.getPrice();
            if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                for (Season season : entity.getSeasons()) {
                    if (season.isSelected()) {
                        //total += season.getPrice();
                        total += season.getPrice() * season.getCount();
                    }
                }
            }
        }
        if(App.INSTANCE().getUser().getOperaType().contains("2")){
            etRowNumber.setVisibility(View.VISIBLE);
        }
        presenter.KuaiSu(getInfo(), "", "", "", "", "", "", total, "",
                etRowNumber.getText().toString().isEmpty()?"":etRowNumber.getText().toString(), "4", true, false,
                false,App.INSTANCE().getUser().getOperaType().contains("2")?"0":"1");
    }

    @OnClick(R.id.scanBill)
    public void scanClick() {
        presenter.KuaiSu(getInfo(), "", "", "", "", "", "", getTotal(), "",
                etRowNumber.getText().toString().isEmpty()?"":etRowNumber.getText().toString(), "4", false, true,
                false,App.INSTANCE().getUser().getOperaType().contains("2")?"0":"1");

    }

    private String getInfo() {
        String info = "";
        for (int i = 0; i < carts.size(); i++) {
            OrderfoodEntity orderfoodEntity = carts.get(i);
            double total = 0;
            String kouName = "";
            String kouId = "";
            String specName = "";
            String specId = "";
            String seasonName = "";
            String seasonId = "";
            double seasonPrice = 0;
            String seasonNum = "";

            if (orderfoodEntity.getKouWeis() != null) {
                for (int j = 0; j < orderfoodEntity.getKouWeis().size(); j++) {
                    KouWei kouWei = orderfoodEntity.getKouWeis().get(j);
                    if (kouWei.isSelected() && !TextUtils.isEmpty(kouWei.getName())) {
                        kouName += kouWei.getName() + "*";
                        kouId += kouWei.getGuId() + "*";
                    }
                }
            }
            if (orderfoodEntity.getSpecs() != null) {
                for (int j = 0; j < orderfoodEntity.getSpecs().size(); j++) {
                    Spec spec = orderfoodEntity.getSpecs().get(j);
                    if (spec.isSelected()) {
                        specId = spec.getGuId();
                        specName = spec.getName();
                        break;
                    }
                }
            }
            if (orderfoodEntity.getSeasons() != null) {
                for (int j = 0; j < orderfoodEntity.getSeasons().size(); j++) {
                    Season season = orderfoodEntity.getSeasons().get(j);
                    if (season.isSelected()) {
                        seasonName += season.getName() + "(" + season.getCount() + "份)" + "*";
                        seasonNum += season.getCount() + "*";
                        seasonId += season.getGuId() + "*";
                        //seasonPrice += season.getPrice();
                        seasonPrice += season.getPrice() * season.getCount();
                    }
                }
            }


            String name = "";
            String id = "";
            String number = "";
            if (orderfoodEntity.isType()) {
                total += (orderfoodEntity.getNumber() - orderfoodEntity.getGivingnum()) * orderfoodEntity.getOriginalPrice();
                if (kouId.endsWith("*") && kouName.endsWith("*")) {
                    kouId = kouId.substring(0, kouId.length() - 1);
                    kouName = kouName.substring(0, kouName.length() - 1);
                }

                if (seasonName.endsWith("*") && seasonId.endsWith("*")) {
                    seasonId = seasonId.substring(0, seasonId.length() - 1);
                    seasonName = seasonName.substring(0, seasonName.length() - 1);
                }
                name = TextUtils.isEmpty(orderfoodEntity.getTitle()) ? "" : orderfoodEntity.getTitle().replaceAll(",", "\\^");
                id = TextUtils.isEmpty(orderfoodEntity.getGuid()) ? "" : orderfoodEntity.getGuid();
                number = TextUtils.isEmpty(orderfoodEntity.getCounts()) ? "" : orderfoodEntity.getCounts().replaceAll(",", "\\^");
                info += orderfoodEntity.getPackageName() + "@"
                        + orderfoodEntity.getMemberPice() + "@"
                        + "-1" + "@"
                        + orderfoodEntity.getPrice() + "@"
                        + orderfoodEntity.getUnit() + "$"
                        + id + "$"
                        + total + "$"
                        + orderfoodEntity.getNumber() + "$"
                        + kouName + "$"
                        + kouId + "$"
                        + "1" + "$"
                        + orderfoodEntity.getOriginalPrice() + "$"
                        + name + "$"
                        + number + "$"
                        + 0 + "$"//$ 赠送份数
                        + seasonId + "$"//  作料ID
                        + seasonName + "$"// 作料名称
                        + seasonPrice + "$"//佐料价格
                        + seasonNum + ",";//佐料数量;
            } else {
                if (orderfoodEntity.isShowWeight()) {
                    //if (orderfoodEntity.getGivingnum() == 0) {
                    total += orderfoodEntity.getWeight() * orderfoodEntity.getPrice() * (orderfoodEntity.getNumber() - orderfoodEntity.getGivingnum());
                    //}
                } else {
                    total += orderfoodEntity.getNumber() * orderfoodEntity.getPrice();
                }
                if (kouId.endsWith("*") && kouName.endsWith("*")) {
                    kouId = kouId.substring(0, kouId.length() - 1);
                    kouName = kouName.substring(0, kouName.length() - 1);
                }
                name = orderfoodEntity.getTitle();
                id = orderfoodEntity.getFoodID();
                number = (orderfoodEntity.getNumber() > 0 ? orderfoodEntity.getNumber() + "" : "1");

                info += name + "@"
                        + orderfoodEntity.getMemberPice() + "@"
                        + "0" + "@"
                        + orderfoodEntity.getPrice() + "@"
                        + orderfoodEntity.getUnit() + "$"
                        + id + "$"
                        + total + "$"
//                        + (orderfoodEntity.getWeight() * orderfoodEntity.getPrice() + orderfoodEntity.getNumber() * orderfoodEntity.getPrice()) + "$"
                        + number + "$"
                        + kouName + "$"
                        + kouId + "$"
                        + (orderfoodEntity.getWeight() > 0 ? orderfoodEntity.getWeight() : "1") + "$"
                        + orderfoodEntity.getPrice() + "$"
                        + specName + "$"
                        + specId + "$"
                        + orderfoodEntity.getGivingnum() + "$"//$ 赠送份数
                        + seasonId + "$"//  作料ID
                        + seasonName + "$"// 作料名称
                        + seasonPrice + "$"//佐料价格
                        + seasonNum + ",";//佐料数量;
            }


        }
        if (info.endsWith(",")) {
            info = info.substring(0, info.length() - 1);
        }

        Log.i("ttt", "info:" + info);
        return info;
    }

    public static void moveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
        //manager.setStackFromEnd(true);
    }

    @Override
    protected void initPresenter() {
        presenter = new OrderFoodPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_food;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        type = getIntent().getIntExtra(Config.PARAM1, 0);

        switch (type) {
            case P1:
                toolbar.setTitle("堂点");
                tableEntity = (TableEntity) getIntent().getSerializableExtra(Config.PARAM2);
                position = getIntent().getIntExtra(Config.PARAM3, 0);
                button.setText("落单");
                break;
            case P2:
                toolbar.setTitle("加菜");
                order = (Order) getIntent().getSerializableExtra(Config.PARAM2);
                button.setText("落单");
                break;
            case P3:
                toolbar.setTitle("加菜");
                order = (Order) getIntent().getSerializableExtra(Config.PARAM2);
                button.setText("落单");
                break;
            case P4:
                toolbar.setTitle("外卖");
                break;
            case P5:
                toolbar.setTitle("快餐");
//                "现金", "银行卡", "微信支付", "会员卡", "线下支付宝", "线下微信"
                if (App.INSTANCE().getUser().getPayType().equals("1")) {
                    btQuickBill.setText("现金\n支付");
                } else if (App.INSTANCE().getUser().getPayType().equals("2")) {
                    btQuickBill.setText("银行卡\n支付");
                } else if (App.INSTANCE().getUser().getPayType().equals("3")) {
                    btQuickBill.setText("微信\n支付");
                } else if (App.INSTANCE().getUser().getPayType().equals("5")) {
                    btQuickBill.setText("会员卡\n支付");
                } else if (App.INSTANCE().getUser().getPayType().equals("6")) {
                    btQuickBill.setText("线下支\n付宝支付");
                } else if (App.INSTANCE().getUser().getPayType().equals("7")) {
                    btQuickBill.setText("线下\n微信支付");
                } else {
                    btQuickBill.setText("快速\n支付");
                }
                if(App.INSTANCE().getUser().getOperaType().contains("2")){
                    etRowNumber.setVisibility(View.VISIBLE);
                }
                btQuickBill.setVisibility(View.VISIBLE);
                btScanBill.setVisibility(View.VISIBLE);
                break;
            case P6:
                toolbar.setTitle("预定点餐");
                break;
            case P7:
                toolbar.setTitle("反结账");
                order = (Order) getIntent().getSerializableExtra("order");
                detailFoods = (List<OrderDetailFood>) getIntent().getSerializableExtra("foods");
                button.setText("落单");
                Log.i("ttt", "order:" + order);
                Log.i("ttt", "detailFoods:" + detailFoods);
                break;
        }
        if (type == P5) {

        }

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAdapter();

        presenter.getDBFood();

        cartBtn.setOnClickListener(v -> {
            queryLayout.setVisibility(View.GONE);
            showBottomSheet();
        });


        quickIndexBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {

            }

            @Override
            public void onLetterIndex(int position) {
                Log.i("ttt", "---position:" + position);
                if (position != -1) {
                    moveToPosition((LinearLayoutManager) rigth.getLayoutManager(), position);
                }
            }

            @Override
            public void onReset() {

            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    ivClear.setVisibility(View.INVISIBLE);
                    queryAdapter.getData().clear();
                    queryAdapter.notifyDataSetChanged();
                    queryLayout.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                    presenter.queryFoods(s.toString().trim());
                }
            }
        });
    }

    @OnClick(R.id.iv_clear)
    public void clearClick() {
        etSearch.setText("");
    }

    private void initAdapter() {
        menuAdapter = new MenuClassAdapter(R.layout.item_menu_classification);
        left.setLayoutManager(new LinearLayoutManager(OrderFoodActivity.this));
        left.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener((adapter, view, position) -> {
            MenuTypeEntity type = menuAdapter.getItem(position);
            assert type != null;
            if (!type.isSelected()) {
                for (MenuTypeEntity m : menuAdapter.getData()) {
                    m.setSelected(false);
                }
                currMenuPosition = position;
                type.setSelected(true);
                for (int i = 0; i < QuickIndexBar.LETTERS.length; i++) {
                    QuickIndexBar.INDEX[i] = 0;
                    for (int j = 0; j < type.getFoods().size(); j++) {
                        FoodEntity foodEntity = type.getFoods().get(j);
                        if (!TextUtils.isEmpty((foodEntity.getPinYin())) && (foodEntity.getPinYin().substring(0, 1).equalsIgnoreCase(QuickIndexBar.LETTERS[i]))) {
                            QuickIndexBar.INDEX[i] = j;
                            break;
                        } else {
                            QuickIndexBar.INDEX[i] = -1;
                        }
                    }
                }
                contactAdapter.setDatas(type.getFoods());
                if (type.getProductTypeName().equals("套餐")) {
                    Log.i("ttt", "---" + type.getFoods());
                }
                menuAdapter.notifyDataSetChanged();
            }

        });
        rigth.setLayoutManager(new LinearLayoutManager(OrderFoodActivity.this));
        rigth.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.gray)
                .sizeResId(R.dimen._1sdp)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .build());
        contactAdapter = new ContactAdapter(this, new ContactAdapter.OnItemClickLishener() {

            @Override
            public void add(FoodEntity foodEntity, int position) {
                assert foodEntity != null;
                int num = foodEntity.getNumber();
                OrderfoodEntity entity;
                if (num < 99) {
                    foodEntity.setNumber(num + 1);
                } else {
                    foodEntity.setNumber(99);
                }
                contactAdapter.notifyDataSetChanged();

                entity = new OrderfoodEntity();
                entity.setFoodPosition(position);
                entity.setMenuPosition(currMenuPosition);
                if (foodEntity.getType()) {
                    entity.setUnit("份");
                } else {
                    entity.setUnit(foodEntity.getUnit());
                }
                entity.setTitle(foodEntity.getProductName());
                entity.setType(foodEntity.getType());
                entity.setGuid(foodEntity.getGuid());
                entity.setCounts(foodEntity.getCounts());
                entity.setPackageName(foodEntity.getPackageName());
                entity.setFoodID(foodEntity.getProductID());//设置id；
                entity.setNumber(1);//设置份数
                entity.setPrice(foodEntity.getPrice());
                entity.setOriginalPrice(foodEntity.getPrice());
                entity.setMemberPice(foodEntity.getMemberPice());
                if (!TextUtils.isEmpty(foodEntity.getProductShuXing()) && foodEntity.getProductShuXing().equals("1")) {
                    entity.setShowWeight(true);
                }
                carts.add(entity);
                showCart();
            }

            @Override
            public void reduce(FoodEntity foodEntity, int position) {
                assert foodEntity != null;

                for (int i = 0; i < carts.size(); i++) {
                    OrderfoodEntity orderfoodEntity = carts.get(i);
                    if (orderfoodEntity.getFoodID().equals(foodEntity.getProductID())) {
                        foodEntity.setNumber(foodEntity.getNumber() - orderfoodEntity.getNumber());
                        carts.remove(orderfoodEntity);
                        break;
                    }
                }
                contactAdapter.notifyItemChanged(position, foodEntity);
                showCart();
            }

            @Override
            public void button(FoodEntity entity, int position) {
                showCommmonDialog(entity, position, false);
            }

            @Override
            public void pushAll(FoodEntity entity, int position) {
                presenter.pushAllFood(entity);
            }

            @Override
            public void push(FoodEntity entity, int position) {
                presenter.pushFood(entity);
            }

            @Override
            public void put(FoodEntity entity, int position) {
                presenter.putFood(entity);
            }
        });
        rigth.setAdapter(contactAdapter);

        queryAdapter = new OrderFoodAdapter(R.layout.item_order_food);
        queryView.setAdapter(queryAdapter);
        queryView.setLayoutManager(new LinearLayoutManager(OrderFoodActivity.this));
        queryView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.gray)
                .sizeResId(R.dimen._1sdp)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .build());
        queryAdapter.setOnItemChildClickListener((adapter, view, position1) -> {

            FoodEntity foodEntity = queryAdapter.getItem(position1);
            assert foodEntity != null;
            int num = foodEntity.getNumber();
            FoodEntity f = null;
            OrderfoodEntity entity;
            switch (view.getId()) {
                case R.id.add:
                    if (num < 99) {
                        foodEntity.setNumber(num + 1);
                    } else {
                        foodEntity.setNumber(99);
                    }
                    queryAdapter.notifyItemChanged(position1, foodEntity);
                    entity = new OrderfoodEntity();
                    for (int i = 0; i < menuAdapter.getData().size(); i++) {
                        MenuTypeEntity m = menuAdapter.getItem(i);
                        if (m.getProductTypeID().equals(foodEntity.getProductTypeID())) {
                            entity.setMenuPosition(i);
                            break;
                        }
                    }
                    for (int i = 0; i < contactAdapter.getDatas().size(); i++) {
                        f = contactAdapter.getItem(i);
                        if (f.getProductID().equals(foodEntity.getProductID())) {
                            f.setNumber(foodEntity.getNumber());
                            entity.setFoodPosition(i);
                            break;
                        }
                    }
                    if (foodEntity.getType()) {
                        entity.setUnit("份");
                    } else {

                        entity.setUnit(foodEntity.getUnit());
                    }
                    entity.setTitle(foodEntity.getProductName());
                    entity.setType(foodEntity.getType());
                    entity.setGuid(foodEntity.getGuid());
                    entity.setCounts(foodEntity.getCounts());
                    entity.setPackageName(foodEntity.getPackageName());
                    entity.setFoodID(foodEntity.getProductID());//设置id；
                    entity.setNumber(foodEntity.getNumber());//设置份数
                    entity.setPrice(foodEntity.getPrice());
                    entity.setUnit(foodEntity.getUnit());
//                    if (foodEntity.getProductShuXing().equals("0")) {
//                        entity.setKouWeis(AppDbHelper.INSTANCE().getKouWeis(App.INSTANCE().getShopID(), foodEntity.getProductID()));
//                    }
//                    if (foodEntity.getProductShuXing().equals("1")) {
//                        entity.setShowWeight(true);
//                    }
                    entity.setMemberPice(foodEntity.getMemberPice());
                    entity.setOriginalPrice(foodEntity.getPrice());

                    if (!carts.contains(entity)) {
                        entity.setNumLayout(true);
                        carts.add(entity);
                    } else {
                        for (int i = 0; i < carts.size(); i++) {
                            OrderfoodEntity o = carts.get(i);
                            if (o.equals(entity)) {
                                entity.setKouWeis(o.getKouWeis());
                                entity.setKouweied(o.getKouweied());
                                entity.setNumLayout(o.isNumLayout());
                                carts.set(i, entity);
                                break;
                            }
                        }

                    }

                    if (entity.getMenuPosition() == currMenuPosition && f != null) {
                        contactAdapter.notifyItemChanged(entity.getFoodPosition(), f);
                    }

                    showCart();

                    break;
                case R.id.reduce:
                    foodEntity.setNumber(num - 1);
                    queryAdapter.notifyItemChanged(position1, foodEntity);

                    entity = new OrderfoodEntity();
                    for (int i = 0; i < menuAdapter.getData().size(); i++) {
                        MenuTypeEntity m = menuAdapter.getItem(i);
                        if (m.getProductTypeID().equals(foodEntity.getProductTypeID())) {

                            entity.setMenuPosition(i);
                            break;
                        }
                    }

                    for (int i = 0; i < contactAdapter.getDatas().size(); i++) {
                        f = contactAdapter.getItem(i);
                        if (f.getProductID().equals(foodEntity.getProductID())) {
                            f.setNumber(foodEntity.getNumber());
                            entity.setFoodPosition(i);
                            break;
                        }
                    }
                    if (foodEntity.getType()) {
                        entity.setUnit("份");
                    } else {
                        entity.setUnit(foodEntity.getUnit());
                    }
                    entity.setTitle(foodEntity.getProductName());
                    entity.setType(foodEntity.getType());
                    entity.setGuid(foodEntity.getGuid());
                    entity.setCounts(foodEntity.getCounts());
                    entity.setPackageName(foodEntity.getPackageName());
                    entity.setFoodID(foodEntity.getProductID());//设置id；
                    entity.setNumber(foodEntity.getNumber());//设置份数
                    entity.setPrice(foodEntity.getPrice());
                    entity.setUnit(foodEntity.getUnit());
                    entity.setMemberPice(foodEntity.getMemberPice());
                    entity.setOriginalPrice(foodEntity.getPrice());
                    if (foodEntity.getProductShuXing().equals("1")) {
                        entity.setShowWeight(true);
                    }
                    Timber.d("number" + entity.getNumber());

                    if (entity.getNumber() == 0) {
                        entity.setNumLayout(true);
                        carts.remove(entity);
                    } else {
                        for (int i = 0; i < carts.size(); i++) {
                            OrderfoodEntity o = carts.get(i);
                            if (o.equals(entity)) {

                                entity.setKouWeis(o.getKouWeis());
                                entity.setKouweied(o.getKouweied());
                                entity.setNumLayout(o.isNumLayout());
                                carts.set(i, entity);
                                break;
                            }
                        }
                    }
                    if (entity.getMenuPosition() == currMenuPosition && f != null) {
                        contactAdapter.notifyItemChanged(entity.getFoodPosition(), f);
                    }
                    showCart();
                    break;
                case R.id.button:
                    showCommmonDialog(foodEntity, position1, true);
                    break;
                case R.id.imageView:
                    Intent intent = new Intent(OrderFoodActivity.this, BigImageActivity.class);
                    intent.putExtra(Config.PARAM1, foodEntity.getProductFile());
                    startActivity(intent);
                    break;
            }
        });

    }

    private void showCommmonDialog(FoodEntity item, int position, boolean isQuery) {
        OrderFoodDialog.Builder builder = new OrderFoodDialog.Builder(OrderFoodActivity.this, R.style.OrderDialogStyle);
        OrderfoodEntity dialog = new OrderfoodEntity();

        if (isQuery) {
            for (int i = 0; i < menuAdapter.getData().size(); i++) {
                MenuTypeEntity m = menuAdapter.getItem(i);
                if (m.getProductTypeID().equals(item.getProductTypeID())) {
                    dialog.setMenuPosition(i);
                    break;
                }
            }

            for (int i = 0; i < contactAdapter.getDatas().size(); i++) {
                if (contactAdapter.getItem(i).getProductID().equals(item.getProductID())) {
                    dialog.setFoodPosition(i);
                    break;
                }
            }

        } else {
            dialog.setMenuPosition(currMenuPosition);
            dialog.setFoodPosition(position);
        }

        if (item.getType()) {
            dialog.setUnit("份");
        } else {

            dialog.setUnit(item.getUnit());
        }
        dialog.setTitle(item.getProductName());
        dialog.setPrice(item.getPrice());
        dialog.setOriginalPrice(item.getPrice());

        //        item.getTasteType();//口味 1。弹出口味   0.不弹出口
//        item.getProductShuXing();//1是称斤 2是规格菜品 0是默认菜品
        Timber.d("-----" + item.getTasteType());
        if(item.getTasteType() != null){
            if (item.getTasteType().equals("1")) {
                List<KouWei> kouWeiList = AppDbHelper.INSTANCE().getOwnKouWeis(App.INSTANCE().getShopID(), item.getProductID());
                kouWeiList.addAll(AppDbHelper.INSTANCE().getKouWeis(App.INSTANCE().getShopID(), item.getProductID()));
                KouWei k = new KouWei();
                k.setType(KouWei.EDIT);
                k.setGuId("");
                kouWeiList.add(0, k);
                dialog.setKouWeis(kouWeiList);
            }
        }

        switch (item.getProductShuXing()) {
            case "1":
                dialog.setShowWeight(true);
                break;
            case "2":
                List<Spec> specList = new ArrayList<>();
                for (Spec spec : item.getSpecList()) {
                    Spec s = new Spec();
                    s.setName(spec.getName());
                    s.setSelected(spec.isSelected());
                    s.setFood(spec.getFood());
                    s.setGuId(spec.getGuId());
                    s.setPrice(spec.getPrice());
                    s.setProtuctID(spec.getProtuctID());
                    s.setRestaurantID(spec.getRestaurantID());
                    s.setProtuctName(spec.getProtuctName());
                    specList.add(s);
                }
                dialog.setSpecs(specList);
                dialog.setNumLayout(true);
                break;
          /*  case "0":
                dialog.setNumLayout(true);
                List<KouWei> kouWeis = AppDbHelper.INSTANCE().getKouWeis(App.INSTANCE().getShopID(), item.getProductID());
                if (kouWeis.size() > 0) {
                    KouWei k = new KouWei();
                    k.setType(KouWei.EDIT);
                    k.setGuId("");
                    kouWeis.add(0, k);
                }
                dialog.setKouWeis(kouWeis);
                break;*/
            case "3":
                dialog.setNumLayout(true);
                List<Season> seasonList = new ArrayList<>();
                List<Season> seasons = AppDbHelper.INSTANCE().getSeason(App.INSTANCE().getShopID(), item.getProductID());
                for (Season season : seasons) {
                    seasonList.add(season);
                }
                dialog.setSeasons(seasonList);
                break;
        }
        dialog.setNumLayout(true);
        List<KouWei> kouWeis = AppDbHelper.INSTANCE().getKouWeis(App.INSTANCE().getShopID(), item.getProductID());
        if (kouWeis.size() > 0) {
            KouWei k = new KouWei();
            k.setType(KouWei.EDIT);
            k.setGuId("");
            kouWeis.add(0, k);
        }
        dialog.setKouWeis(kouWeis);

        if (dialog.getSeasons() == null || dialog.getSeasons().size() == 0) {
            dialog.setSeasons(AppDbHelper.INSTANCE().getSeason(App.INSTANCE().getShopID(), item.getProductID()));
        }

        dialog.setType(item.getType());
        dialog.setGuid(item.getGuid());
        dialog.setCounts(item.getCounts());
        dialog.setPackageName(item.getPackageName());
        dialog.setFoodID(item.getProductID());//一定要把id传进去
        dialog.setBtnStr("确定");
        dialog.setMoreBtn(false);

        dialog.setMemberPice(item.getMemberPice());

        builder.setEntity(dialog);
        builder.setButtonClick(new OrderFoodDialog.OnButtonClick() {
            @Override
            public void onLeftBtnClick(OrderfoodEntity entity) {

            }

            @Override
            public void onRightBtnClick(OrderfoodEntity entity) {

            }

            @Override
            public void onBtnClick(OrderfoodEntity entity) {
                carts.add(entity);
                int i = 0;
                if (isQuery) {
                    if (entity.getMenuPosition() == currMenuPosition) {
                        FoodEntity e = contactAdapter.getDatas().get(entity.getFoodPosition());
                        i = e.getNumber();
                        e.setNumber(i + 1);
                        contactAdapter.notifyItemChanged(entity.getFoodPosition());
                    } else {
                        int mp = 0;
                        int fp = 0;
                        for (int k = 0; k < menuAdapter.getData().size(); k++) {

                            if (menuAdapter.getItem(k).getProductTypeID().equals(item.getProductTypeID())) {
                                mp = k;
                                for (int j = 0; j < menuAdapter.getItem(k).getFoods().size(); j++) {
                                    if (menuAdapter.getItem(k).getFoods().get(j).getProductID().equals(item.getProductID())) {
                                        fp = j;
                                        break;
                                    }
                                }

                                break;
                            }
                        }


                        Timber.d("mp" + mp + "fp" + fp);
                        FoodEntity e = menuAdapter.getData().get(mp).getFoods().get(fp);
                        i = e.getNumber();
                        e.setNumber(i + 1);
                    }

                } else {
                    i = item.getNumber();
                    item.setNumber(i + 1);
                    contactAdapter.notifyItemChanged(position, item);
                }

                if (entity.getKouWeis() != null && entity.getKouWeis().size() > 0) {
                    String wei = "";
                    for (KouWei kouWei : entity.getKouWeis()) {

                        if (kouWei.isSelected()) {
                            wei += kouWei.getName() + "、";
                        }
                    }
                    entity.setKouweied(wei);
                }

                if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                    String seasonStr = "";
                    for (Season season : entity.getSeasons()) {
                        if (season.isSelected()) {
                            //seasonStr += season.getName() + "(￥" + season.getPrice() + ")、";
                            seasonStr += season.getName() + "(￥" + season.getPrice() * season.getCount() + ")、";
                        }
                    }
                    entity.setSeasoned(seasonStr);
                }

                if (entity.getSpecs() != null && entity.getSpecs().size() > 0) {

                    String spec = "";
                    for (int j = 0; j < entity.getSpecs().size(); j++) {
                        if (entity.getSpecs().get(j).isSelected()) {
                            spec = entity.getSpecs().get(j).getName();
                            break;
                        }
                    }
                    entity.setSpeced(spec);
                }
                showCart();
                builder.dismiss();

            }
        });
        builder.creater().show();
    }

    private void showCart() {
        if (badgeView == null) {
            badgeView = new QBadgeView(this);
            badgeView.bindTarget(cartBtn);
        }
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
        button.setEnabled(carts.size() > 0);
        btQuickBill.setEnabled(carts.size() > 0);
        btScanBill.setEnabled(carts.size() > 0);
        double total = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (OrderfoodEntity entity : carts) {
            entity.getMenuPosition();

            if (map.get(entity.getMenuPosition()) != null) {
                int value = map.get(entity.getMenuPosition());
                map.put(entity.getMenuPosition(), entity.getNumber() + value);
            } else {
                map.put(entity.getMenuPosition(), entity.getNumber());
            }
            count += entity.getNumber();

            if (entity.isShowWeight()) {
                //if (entity.getGivingnum() == 0) {
                total += entity.getWeight() * entity.getPrice() * (entity.getNumber() - entity.getGivingnum());
                // }
            } else {
                total += ((entity.getNumber() - entity.getGivingnum()) * entity.getPrice());
            }

            if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                for (Season season : entity.getSeasons()) {
                    if (season.isSelected()) {
                        //total += season.getPrice() * (entity.getNumber() - entity.getGivingnum());
                        total += season.getPrice() * season.getCount() * (entity.getNumber() - entity.getGivingnum());
                    }
                }
            }

        }
        badgeView.setBadgeNumber(count);
        Timber.d("total" + total);
        if (carts.size() == 0) {
            showBottomSheet();
        }

        totalMoney.setText(String.format(getResources().getString(R.string.string_money), total));
        for (int i = 0; i < menuAdapter.getData().size(); i++) {
            MenuTypeEntity entity = menuAdapter.getData().get(i);
            entity.setCount(0);
            if (map.get(i) != null) {
                entity.setCount(map.get(i));
            }
        }

        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MsgEvent event;
                if (type == P1) {
                    event = new MsgEvent(MsgEvent.TangDian, "1", position);
                    EventBus.getDefault().post(event);
                }
                finish();
                break;
            case R.id.action_message:
                Intent intent = new Intent(OrderFoodActivity.this, MessageListActivity.class);
                startActivity(intent);
                break;
            case R.id.action_search:
                if (clTopSearch.getVisibility() != View.VISIBLE) {
                    clTopSearch.setVisibility(View.VISIBLE);
                } else {
                    clTopSearch.setVisibility(View.GONE);
                    queryLayout.setVisibility(View.GONE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
        EventBus.getDefault().unregister(this);
        left = null;
        rigth = null;
        menuAdapter = null;
        contactAdapter = null;
        ImmersionBar.with(this).destroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        switch (event.getType()) {
            case MsgEvent.kuaicanSuccess:
                presenter.getDBFood();
                carts.clear();
                showCart();
                break;
            case MsgEvent.waimaiSuccess:
                presenter.getDBFood();
                carts.clear();
                showCart();
                break;
            case MsgEvent.yudingSuccess:
                finish();
                break;
            case MsgEvent.reBillSuccess:
                finish();
                break;
        }
    }

    @Override
    public void warning(String s) {
        Toasty.warning(this, s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void success(List<MenuTypeEntity> menuTypeEntities) {
        for (int i = 0; i < QuickIndexBar.LETTERS.length; i++) {
            QuickIndexBar.INDEX[i] = 0;

            for (int j = 0; j < menuTypeEntities.get(0).getFoods().size(); j++) {
                FoodEntity foodEntity = menuTypeEntities.get(0).getFoods().get(j);
                if (!TextUtils.isEmpty((foodEntity.getPinYin())) && (foodEntity.getPinYin().substring(0, 1).equalsIgnoreCase(QuickIndexBar.LETTERS[i]))) {
                    QuickIndexBar.INDEX[i] = j;
                    break;
                } else {
                    QuickIndexBar.INDEX[i] = -1;
                }
            }
        }

        if (type == P7 && detailFoods != null && detailFoods.size() > 0) {
            for (int i = 0; i < menuTypeEntities.size(); i++) {
                MenuTypeEntity menuTypeEntity = menuTypeEntities.get(i);
                for (int j = 0; j < menuTypeEntity.getFoods().size(); j++) {
                    FoodEntity foodEntity = menuTypeEntity.getFoods().get(j);
                    if (TextUtils.isEmpty(foodEntity.getProductName())) {
                        continue;
                    }
                    for (int n = 0; n < detailFoods.size(); n++) {
                        OrderDetailFood detailFood = detailFoods.get(n);
                        if (foodEntity.getProductName().equals(detailFood.getProductNmae())) {
                            addCart(foodEntity, detailFood, j, i);
                        }
                    }
                }
            }
            contactAdapter.notifyDataSetChanged();
            if (bottomAdapter != null) {
                bottomAdapter.notifyDataSetChanged();
            }
            showCart();
        }

//        if (menuTypeEntities.get(0).getFoods().size() == 0) {
//            menuTypeEntities.remove(0);
//        }
        menuTypeEntities.get(0).setSelected(true);
        menuAdapter.setNewData(menuTypeEntities);
        contactAdapter.setDatas(menuTypeEntities.get(0).getFoods());
    }

    private void addCart(FoodEntity foodEntity, OrderDetailFood detailFood, int position, int currMenuPosition) {
        assert foodEntity != null;

        Log.i("ttt", "----addCart:" + detailFood);

        OrderfoodEntity entity = new OrderfoodEntity();
        entity.setFoodPosition(position);
        entity.setMenuPosition(currMenuPosition);
        if (foodEntity.getType()) {
            entity.setUnit("份");
        } else {
            entity.setUnit(foodEntity.getUnit());
        }
        entity.setTitle(foodEntity.getProductName());
        entity.setType(foodEntity.getType());
        entity.setGuid(foodEntity.getGuid());
        entity.setCounts(foodEntity.getCounts());
        entity.setPackageName(foodEntity.getPackageName());
        entity.setFoodID(foodEntity.getProductID());//设置id；
        if (foodEntity.getProductShuXing().equals("1")) {
            entity.setWeight(detailFood.getWeight());
            entity.setNumber(1);
            foodEntity.setNumber(foodEntity.getNumber() + 1);
        } else {
            entity.setNumber((int) detailFood.getAmmount());//设置份数
            foodEntity.setNumber(foodEntity.getNumber() + entity.getNumber());
        }
        entity.setPrice(foodEntity.getPrice());
        entity.setMemberPice(foodEntity.getMemberPice());
        entity.setOriginalPrice(foodEntity.getPrice());
        entity.setGivingnum(detailFood.getGiving());
        entity.setNumLayout(true);
        if (foodEntity.getProductShuXing().equals("0")) {
            entity.setKouWeis(AppDbHelper.INSTANCE().getKouWeis(App.INSTANCE().getShopID(), foodEntity.getProductID()));
            if (!TextUtils.isEmpty(detailFood.getRemark())) {
                for (int i = 0; i < entity.getKouWeis().size(); i++) {
                    if (detailFood.getRemark().contains(entity.getKouWeis().get(i).getName())) {
                        entity.getKouWeis().get(i).setSelected(true);
                    }
                }
                entity.setKouweied(detailFood.getRemark());
            }
        }
        if (foodEntity.getProductShuXing().equals("1")) {
            entity.setShowWeight(true);
            entity.setWeight(detailFood.getWeight());
        }
        if (!TextUtils.isEmpty(detailFood.getProductshuxin())) {
            entity.setSpecs(foodEntity.getSpecList());
            for (int i = 0; i < entity.getSpecs().size(); i++) {
                if (entity.getSpecs().get(i).getGuId().equals(detailFood.getProductshuxingId())) {
                    entity.getSpecs().get(i).setSelected(true);
                }
            }
            entity.setSpeced(detailFood.getProductshuxin());
            entity.setPrice(detailFood.getMemberPrice());
        }

        if (!TextUtils.isEmpty(detailFood.getSeasonName())) {
            List<Season> seasonList = AppDbHelper.INSTANCE().getSeason(App.INSTANCE().getShopID(), foodEntity.getProductID());
            for (Season season : seasonList) {
                if (detailFood.getSeasonID().contains(season.getGuId())) {
                    String[] split = detailFood.getSeasonID().split("\\*");
                    String[] split2 = detailFood.getSeasonSum().split("\\*");
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].equals(season.getGuId())) {
                            season.setCount(Integer.parseInt(split2[i]));
                            season.setSelected(true);
                        }
                    }
                }
            }
//            Season season = new Season();
//            season.setGuId(detailFood.getSeasonID());
//            season.setProtuctID(detailFood.getSeasonID());
//            season.setName(detailFood.getSeasonName());
//            season.setProtuctName(detailFood.getSeasonName());
//            season.setPrice(detailFood.getSeasonPrice());
//            season.setRestaurantId(order.getRestaurantID());
//            season.setSelected(true);
//            List<Season> seasons = new ArrayList<>();
//            seasons.add(season);
            entity.setSeasons(seasonList);
            entity.setSeasoned(detailFood.getSeasonName());
        }
        carts.add(entity);
    }

    @Override
    public void error(String string) {
        Toasty.error(this, string, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void orderSuccess(String s) {
        Toasty.success(this, s, Toast.LENGTH_SHORT, true).show();
        MsgEvent event;
        if (type == P1) {
            Log.i("ttt", "---orderSuccess");
            event = new MsgEvent(MsgEvent.TangDian, "2", position);
            EventBus.getDefault().post(event);
            finish();
        } else if (type == P2) {
            event = new MsgEvent(MsgEvent.AddFood);
            EventBus.getDefault().post(event);
            finish();
        } else if (type == P3) {
            finish();
        }
    }

    @Override
    public void billSuccess(String msg, String result) {
        Toasty.success(this, msg, Toast.LENGTH_SHORT, true).show();
        presenter.getDBFood();
        carts.clear();
        showCart();
    }

    private void scanOrQuickbill(String payType, String result, double money, String memberId) {
        List<BillJson.BillJsonBase> t = new ArrayList<>();
        BillJson.BillJsonBase base2 = new BillJson.BillJsonBase();
        t.add(base2);

        BillJson.BillJsonBase base = new BillJson.BillJsonBase();
        base.setGuid(String.valueOf(System.currentTimeMillis()) + result);
        base.setPice(String.valueOf(money));
        base.setPiceGuid(payType);
        base.setSate("0");
        base.setType("1");
        base.setIsSql("1");
        t.add(base);

        BillJson.TeacherJson teacherJson = new BillJson.TeacherJson();
        List<BillJson.BillJsonBase> youHui = new ArrayList<>();
        youHui.add(base2);
        teacherJson.setTeacher(youHui);
        String tStr = new Gson().toJson(teacherJson);
        Log.i("ttt", "---tStr:" + tStr);

        BillJson.Quanxian quanxian = new BillJson.Quanxian();
        List<BillJson.BillJsonBase> q = new ArrayList<>();
        q.add(base2);
        quanxian.setQuanxian(q);
        String qStr = new Gson().toJson(quanxian);
        Log.i("ttt", "---qStr:" + qStr);
        BillJson.Pays pays = new BillJson.Pays();
        List<BillJson.BillJsonBase> p = new ArrayList<>();
        p.add(base);
        pays.setQuanxian(p);
        String pStr = new Gson().toJson(pays);
        Log.i("ttt", "---pStr:" + pStr);

        presenter.bill(result, App.INSTANCE().getShopID(), "", money, 0, qStr
                , tStr, pStr, payType, 1, money, "", 0, "4", memberId);
    }

    @Override
    public void bill(String payType, String result, double money, String memberId, String str) {
        if (str.contains("支付中")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("用户正在支付中，是否确认已支付");
            builder.setPositiveButton("确定", (dialog, which) -> {
                scanOrQuickbill(payType, result, money, memberId);
            });
            builder.setCancelable(false);
            builder.setNegativeButton("取消", null);
            builder.show();
        } else {
            scanOrQuickbill(payType, result, money, memberId);
        }
    }

    String billId;

    @Override
    public void kuaisuSuccess(String result, double money, boolean isquick, boolean isScan, boolean isEditTabName) {
        if (!isquick && !isScan) {
            Toasty.success(this, "下单成功", Toast.LENGTH_SHORT, true).show();
        }
        Intent intent;
        if (type == P5) {
            if (isquick) {
                presenter.payWay(App.INSTANCE().getShopID(), result, money);
            } else if (isScan) {
                billId = result;
                intent = new Intent(OrderFoodActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            } else if (isEditTabName) {
                intent = new Intent(OrderFoodActivity.this, BillActivity.class);
                intent.putExtra(Config.PARAM2, money);//总价
                intent.putExtra(Config.PARAM1, result);
                intent.putExtra(Config.PARAM5, BillActivity.P3);
                intent.putExtra("TabName", tableName);
                startActivity(intent);
            } else {
                intent = new Intent(OrderFoodActivity.this, BillActivity.class);
                intent.putExtra(Config.PARAM2, money);//总价
                intent.putExtra(Config.PARAM1, result);
                intent.putExtra(Config.PARAM5, BillActivity.P3);
                startActivity(intent);
            }
        } else if (type == P4) {
        } else if (type == P6) {

        } else if (type == P7) {
            intent = new Intent(OrderFoodActivity.this, BillActivity.class);
            intent.putExtra(Config.PARAM2, money);//总价
            intent.putExtra(Config.PARAM4, result);
            intent.putExtra(Config.PARAM3, order);
            intent.putExtra(Config.PARAM5, BillActivity.P6);
            startActivity(intent);
        }
    }

    @Override
    public void querySuccess(List<FoodEntity> foodEntities) {
        for (FoodEntity foodEntity : foodEntities) {
            for (OrderfoodEntity orderfoodEntity : carts) {
                if (orderfoodEntity.getFoodID().equals(foodEntity.getProductID())) {
                    foodEntity.setNumber(orderfoodEntity.getNumber());
                }
            }
        }
        queryAdapter.setNewData(foodEntities);
        if (foodEntities.size() > 0) {
            queryLayout.setVisibility(View.VISIBLE);
        } else {
            queryLayout.setVisibility(View.GONE);
        }
    }

    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_order_food, bottomSheet, false);
        RecyclerView bottomSheetRecyclerView = (RecyclerView) view.findViewById(R.id.bottomSheetRecyclerView);
        TextView textView = (TextView) view.findViewById(R.id.batch);

        textView.setOnClickListener(v -> {
            CommonKouWeiDialog.Builder comm = new CommonKouWeiDialog.Builder(OrderFoodActivity.this, R.style.OrderDialogStyle);
            List<KouWei> kouWeiList = new ArrayList<KouWei>();
            for (KouWei kouWei : AppDbHelper.INSTANCE().getKouWeis(App.INSTANCE().getShopID(), false)) {
                KouWei k = new KouWei();
                k.setName(kouWei.getName());
                k.setSelected(kouWei.isSelected());
                k.setRestaurantId(kouWei.getRestaurantId());
                k.setPatientId(kouWei.getPatientId());
                k.setOwn(kouWei.getOwn());
                k.setGuId(kouWei.getGuId());
                k.setNo(kouWei.getNo());
                k.setType(kouWei.getItemType());
                kouWeiList.add(k);
            }
            KouWei k = new KouWei();
            k.setType(KouWei.EDIT);
            k.setGuId("");
            kouWeiList.add(0, k);
            comm.setKouWeis(kouWeiList);
            comm.setButtonClick(new CommonKouWeiDialog.OnButtonClick() {
                @Override
                public void onBtnClick(List<KouWei> kouWeis) {
                    for (OrderfoodEntity entity : carts) {
                        if (entity.getKouWeis() == null || entity.getKouWeis().size() == 0) {
                            List<KouWei> kouWeiList = AppDbHelper.INSTANCE().getKouWeis(App.INSTANCE().getShopID(), entity.getFoodID());
                            if (kouWeiList.size() > 0) {
                                KouWei k = new KouWei();
                                k.setType(KouWei.EDIT);
                                k.setGuId("");
                                kouWeiList.add(0, k);
                            }
                            entity.setKouWeis(kouWeiList);
                            entity.setEdit(true);
                            entity.setNumLayout(true);
                        }
                        for (KouWei kouWei : kouWeis) {
                            for (KouWei k : entity.getKouWeis()) {
                                if (kouWei.getGuId().equals(k.getGuId())) {
                                    k.setSelected(kouWei.isSelected());
                                }

                                if (k.getItemType() == KouWei.EDIT && kouWei.getItemType() == KouWei.EDIT) {
                                    k.setSelected(kouWei.isSelected());
                                    k.setName(kouWei.getName());
                                }
                            }
                        }

                        String wei = "";
                        if (entity.getKouWeis() != null) {
                            for (KouWei kouWei : entity.getKouWeis()) {
                                if (kouWei.isSelected()) {
                                    wei += kouWei.getName() + "、";
                                }
                            }
                        }
                        entity.setKouweied(wei);

                        if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                            String seasonStr = "";
                            for (Season season : entity.getSeasons()) {
                                if (season.isSelected()) {
                                    //seasonStr += season.getName() + "(￥" + season.getPrice() + ")、";
                                    seasonStr += season.getName() + "(" + season.getCount() + "份)、";
                                }
                            }
                            entity.setSeasoned(seasonStr);
                        }

                        String spec = "";
                        if (entity.getSpecs() != null) {
                            for (int j = 0; j < entity.getSpecs().size(); j++) {
                                if (entity.getSpecs().get(j).isSelected()) {
                                    spec = entity.getSpecs().get(j).getName();
                                    break;
                                }
                            }
                        }
                        entity.setSpeced(spec);


                    }
                    bottomAdapter.notifyDataSetChanged();
                    showCart();

                    comm.dismiss();
                }
            });
            comm.setBtnStr("确定");
            comm.setTitle("批量口味");
            comm.creater().show();
        });
        bottomSheetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottomAdapter = new BottomSheetFoodAdapter(R.layout.item_bottom_sheet);
        bottomSheetRecyclerView.setAdapter(bottomAdapter);

        bottomAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            OrderfoodEntity entity = bottomAdapter.getItem(position);
            FoodEntity foodEntity = menuAdapter.getItem(entity.getMenuPosition()).getFoods().get(entity.getFoodPosition());
            int num;
            switch (view1.getId()) {
                case R.id.add:
                    num = entity.getNumber();
                    if (num < 99) {
                        entity.setNumber(num + 1);
                        foodEntity.setNumber(foodEntity.getNumber() + 1);
                    } else {
                        entity.setNumber(99);
                        foodEntity.setNumber(99);
                    }
                    //}
                    bottomAdapter.notifyItemChanged(position, entity);
                    if (entity.getMenuPosition() == currMenuPosition) {
                        contactAdapter.notifyItemChanged(entity.getFoodPosition(), foodEntity);
                    }
                    showCart();
                    break;

                case R.id.reduce:
                    num = entity.getNumber();
                    if (num == 1) {
                        carts.remove(position);
                        bottomAdapter.notifyItemRemoved(position);
                        foodEntity.setNumber(foodEntity.getNumber() - 1);
                        if (entity.getMenuPosition() == currMenuPosition) {
                            contactAdapter.notifyItemChanged(entity.getFoodPosition(), foodEntity);
                        }
                    } else if (num > 1) {
                        entity.setNumber(num - 1);
                        if (entity.getGivingnum() > entity.getNumber()) {
                            entity.setGivingnum(entity.getNumber());
                        }
                        foodEntity.setNumber(num - 1);
                        bottomAdapter.notifyItemChanged(position, entity);
                        if (entity.getMenuPosition() == currMenuPosition) {
                            contactAdapter.notifyItemChanged(entity.getFoodPosition(), foodEntity);
                        }
                    }

                    showCart();
                    break;
                case R.id.givingButton:
                    GiveDialog.Builder builder2 = new GiveDialog.Builder(OrderFoodActivity.this, R.style.OrderDialogStyle);
                    builder2.setEntity(entity);
                    builder2.setButtonClick(new GiveDialog.OnButtonClick() {

                        @Override
                        public void onBtnClick(OrderfoodEntity entity) {
                            bottomAdapter.notifyDataSetChanged();
                            showCart();
                        }
                    });
                    builder2.creater().show();
                    break;
                case R.id.editButton:
                    //presenter.getSeasonData(foodEntity, entity);
                    showFoodEditDialog(foodEntity, entity);
                    break;
                case R.id.button:
                    OrderFoodDialog.Builder builder = new OrderFoodDialog.Builder(OrderFoodActivity.this, R.style.OrderDialogStyle);
                    entity.setMoreBtn(true);
                    entity.setLeftStr("删除");
                    entity.setRightStr("确认");
                    entity.setEdit(true);
                    builder.setEntity(entity);
                    builder.setButtonClick(new OrderFoodDialog.OnButtonClick() {
                        @Override
                        public void onLeftBtnClick(OrderfoodEntity entity) {
                            carts.remove(entity);
                            bottomAdapter.notifyItemRemoved(position);
                            foodEntity.setNumber(0);
                            if (entity.getMenuPosition() == currMenuPosition) {
                                contactAdapter.notifyItemChanged(entity.getFoodPosition(), foodEntity);
                            }
                            showCart();
                            builder.dismiss();
                        }

                        @Override
                        public void onRightBtnClick(OrderfoodEntity entity) {

                            String wei = "";
                            if (entity.getKouWeis() != null) {
                                for (KouWei kouWei : entity.getKouWeis()) {
                                    if (kouWei.isSelected()) {
                                        wei += kouWei.getName() + "、";
                                    }
                                }
                            }
                            entity.setKouweied(wei);

                            if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                                String seasonStr = "";
                                for (Season season : entity.getSeasons()) {
                                    if (season.isSelected()) {
                                        //seasonStr += season.getName() + "(￥" + season.getPrice() + ")、";
                                        seasonStr += season.getName() + "(￥" + season.getPrice() * season.getCount() + ")、";
                                    }
                                }
                                entity.setSeasoned(seasonStr);
                            }

                            String spec = "";
                            if (entity.getSpecs() != null) {
                                for (int j = 0; j < entity.getSpecs().size(); j++) {
                                    if (entity.getSpecs().get(j).isSelected()) {
                                        spec = entity.getSpecs().get(j).getName();
                                        break;
                                    }
                                }
                            }
                            entity.setSpeced(spec);

                            Timber.d(entity.getKouweied() + entity.getSpeced() + "1111111111");
                            bottomAdapter.notifyItemChanged(position, entity);
                            showCart();
                            builder.dismiss();
                        }

                        @Override
                        public void onBtnClick(OrderfoodEntity entity) {

                        }
                    });

                    builder.creater().show();
                    break;
            }
        });

        return view;

    }

    public void showFoodEditDialog(FoodEntity foodEntity, OrderfoodEntity entity) {
        Log.i("ttt", "----foodEntity:" + foodEntity);
        Log.i("ttt", "----entity:" + entity);
        OrderFoodDialog.Builder builder1 = new OrderFoodDialog.Builder(OrderFoodActivity.this, R.style.OrderDialogStyle);
        entity.setMoreBtn(true);
        entity.setLeftStr("删除");
        entity.setRightStr("确认");
        entity.setEdit(true);
        entity.setNumLayout(true);
        if (entity.getKouWeis() == null || entity.getKouWeis().size() == 0) {
            List<KouWei> kouWeis = AppDbHelper.INSTANCE().getKouWeis(App.INSTANCE().getShopID(), foodEntity.getProductID());
            if (kouWeis.size() > 0) {
                KouWei k = new KouWei();
                k.setType(KouWei.EDIT);
                k.setGuId("");
                kouWeis.add(0, k);
            }
            entity.setKouWeis(kouWeis);
        }

        if (entity.getSeasons() == null || entity.getSeasons().size() == 0) {
            entity.setSeasons(AppDbHelper.INSTANCE().getSeason(App.INSTANCE().getShopID(), foodEntity.getProductID()));
        }

        builder1.setEntity(entity);
        builder1.setButtonClick(new OrderFoodDialog.OnButtonClick() {
            @Override
            public void onLeftBtnClick(OrderfoodEntity entity) {
                carts.remove(entity);
                bottomAdapter.notifyDataSetChanged();
                foodEntity.setNumber(0);
                if (entity.getMenuPosition() == currMenuPosition) {
                    contactAdapter.notifyItemChanged(entity.getFoodPosition(), foodEntity);
                }
                showCart();
                builder1.dismiss();
            }

            @Override
            public void onRightBtnClick(OrderfoodEntity entity) {
                String wei = "";
                if (entity.getKouWeis() != null) {
                    for (KouWei kouWei : entity.getKouWeis()) {
                        if (kouWei.isSelected()) {
                            wei += kouWei.getName() + "、";
                        }
                    }
                }
                entity.setKouweied(wei);

                if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                    String seasonStr = "";
                    for (Season season : entity.getSeasons()) {
                        if (season.isSelected()) {
                            //seasonStr += season.getName() + "(￥" + season.getPrice() + ")、";
                            seasonStr += season.getName() + "(￥" + season.getPrice() * season.getCount() + ")、";
                        }
                    }
                    entity.setSeasoned(seasonStr);
                }

                String spec = "";
                if (entity.getSpecs() != null) {
                    for (int j = 0; j < entity.getSpecs().size(); j++) {
                        if (entity.getSpecs().get(j).isSelected()) {
                            spec = entity.getSpecs().get(j).getName();
                            break;
                        }
                    }
                }
                entity.setSpeced(spec);

                //bottomAdapter.notifyItemChanged(position, entity);
                bottomAdapter.notifyDataSetChanged();
                showCart();
                builder1.dismiss();
            }

            @Override
            public void onBtnClick(OrderfoodEntity entity) {

            }
        });

        builder1.creater().show();
    }

    private void showBottomSheet() {
        if (bottomSheetView == null) {
            bottomSheetView = createBottomSheetView();
        }
        if (bottomSheet.isSheetShowing()) {
            bottomSheet.dismissSheet();
        } else {
            if (carts.size() != 0) {
                bottomAdapter.setNewData(carts);
                bottomSheet.showWithSheetView(bottomSheetView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_food, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        MsgEvent mevent;
        if (type == P1) {
            Log.i("ttt", "---onBackPressed");
            mevent = new MsgEvent(MsgEvent.TangDian, "1", position);
            EventBus.getDefault().post(mevent);
        }
        finish();
    }

    public double getTotal() {
        double total = 0;
        for (OrderfoodEntity entity : carts) {
            if (entity.isShowWeight()) {
                if (entity.getGivingnum() == 0) {
                    total += entity.getWeight() * entity.getPrice();
                }
            } else {
                total += (entity.getNumber() - entity.getGivingnum()) * entity.getPrice();

            }
            if (entity.getSeasons() != null && entity.getSeasons().size() > 0) {
                for (Season season : entity.getSeasons()) {
                    if (season.isSelected()) {
                        //total += (entity.getNumber() - entity.getGivingnum()) * season.getPrice();
                        total += (entity.getNumber() - entity.getGivingnum()) * season.getPrice() * season.getCount();
                    }
                }
            }
        }
        return total;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle == null) {
            return;
        }
        if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
            String result = bundle.getString(CodeUtils.RESULT_STRING);
            presenter.scanBill(result, getTotal(), billId);
        } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
            warning("解析二维码失败");
        }
    }
}
