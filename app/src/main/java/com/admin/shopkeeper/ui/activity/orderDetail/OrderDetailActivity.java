package com.admin.shopkeeper.ui.activity.orderDetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.LabelAdapter;
import com.admin.shopkeeper.adapter.MenuListAdapter;
import com.admin.shopkeeper.base.BaseActivity;
import com.admin.shopkeeper.dialog.CommonDialog;
import com.admin.shopkeeper.dialog.CommonResonDialog;
import com.admin.shopkeeper.dialog.Give2Dialog;
import com.admin.shopkeeper.dialog.RadioDialog;
import com.admin.shopkeeper.entity.CommonDialogEntity;
import com.admin.shopkeeper.entity.Label;

import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.RadioEntity;
import com.admin.shopkeeper.entity.RetreatReason;
import com.admin.shopkeeper.entity.TPayType;
import com.admin.shopkeeper.ui.activity.bill.BillActivity;
import com.admin.shopkeeper.ui.activity.orderFood.OrderFoodActivity;
import com.admin.shopkeeper.ui.activity.table.TableOperationActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements IOrderDetailView {

    private String tableOpen;
    public static final int P1 = 1;//列表
    public static final int P2 = 2;//桌台
    public static final int P3 = 3;//消息


    private int p2_type = 0;
    private final int P2_CHANGE = 4;
    private final int P2_UNDO = 5;
    private int type;
    private int position;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.menuList)
    RecyclerView mMenuRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_order_id)
    AppCompatTextView orderId;
    @BindView(R.id.orderMoney)
    AppCompatTextView orderMoney;
    @BindView(R.id.orderTime)
    AppCompatTextView orderTime;
    @BindView(R.id.table_id)
    AppCompatTextView table_id;
    @BindView(R.id.orderOperator)
    AppCompatTextView orderOperator;
    @BindView(R.id.payType)
    AppCompatTextView tPayType;


    private LabelAdapter labelAdapter;

    private Order order;
    private ArrayList<OrderDetailFood> detailFoods;
    private List<TPayType> tPayTypes;//组合支付
    private MenuListAdapter menuListAdapter;

    @Override
    protected void initPresenter() {
        presenter = new OrderDetailPresenter(this, this);
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark, 0.4f)
                .titleBar(toolbar, true)
                .init();
        toolbar.setTitle("订单详情");
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type = getIntent().getIntExtra(Config.PARAM4, 0);
        order = (Order) getIntent().getSerializableExtra(Config.PARAM1);
        detailFoods = (ArrayList<OrderDetailFood>) getIntent().getSerializableExtra(Config.PARAM2);
        tPayTypes = (ArrayList<TPayType>) getIntent().getSerializableExtra(Config.PARAM5);
        position = getIntent().getIntExtra(Config.PARAM3, 0);

        Log.i("ttt", "---order:" + order);
        Log.i("ttt","---detailfood" + detailFoods);

        setView();

        GridLayoutManager manager = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(manager);

        labelAdapter = new LabelAdapter(R.layout.item_label);
        mRecyclerView.setAdapter(labelAdapter);
        labelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent;
                MsgEvent event;
                Label label = labelAdapter.getItem(position);
                assert label != null;
                switch (label.getType()) {
                    case Label.addfood://加菜
                        if (App.INSTANCE().getUser().getPermissionValue().contains("jiacai")) {
                            intent = new Intent(OrderDetailActivity.this, OrderFoodActivity.class);
                            intent.putExtra(Config.PARAM1, OrderFoodActivity.P2);
                            intent.putExtra(Config.PARAM2, order);
                            startActivity(intent);
                        } else {
                            warning("没有加菜权限");
                        }
                        break;
                    case Label.desk://换桌
                        if (App.INSTANCE().getUser().getPermissionValue().contains("huanzhuo")) {
                            intent = new Intent(OrderDetailActivity.this, TableOperationActivity.class);
                            intent.putExtra(Config.PARAM1, TableOperationActivity.P1);
                            startActivity(intent);
                            event = new MsgEvent(MsgEvent.ChangeTable, order);
                            EventBus.getDefault().postSticky(event);
                        } else {
                            warning("没有换桌权限");
                        }

                        break;
                    case Label.merge://合并
                        if (App.INSTANCE().getUser().getPermissionValue().contains("bindan")) {
                            intent = new Intent(OrderDetailActivity.this, TableOperationActivity.class);
                            intent.putExtra(Config.PARAM1, TableOperationActivity.P2);
                            startActivity(intent);
                            event = new MsgEvent(MsgEvent.merge, order);
                            EventBus.getDefault().postSticky(event);
                        } else {
                            warning("没有并单权限");
                        }

                        break;
                    case Label.undo://撤单
                        if (App.INSTANCE().getUser().getPermissionValue().contains("chedan")) {
                            AlertDialog.Builder undoBuilder = new AlertDialog.Builder(OrderDetailActivity.this);
                            undoBuilder.setTitle("提醒！");
                            undoBuilder.setMessage("是否要进行撤单操作？");
                            undoBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    presenter.undo(order.getTableId(), order.getBillid(), order.getTableName(), order.getPayPrice() + "");
                                }
                            });
                            undoBuilder.show();
                        } else {
                            warning("没有撤单权限");
                        }
                        break;
                    case Label.print_after:
                        if (App.INSTANCE().getUser().getPermissionValue().contains("houchuprint")) {
                            presenter.printAfter(order.getBillid(), order.getTableId(), order.getTableName(), order.getPeopleCount());
                        } else {
                            warning("没有后厨打印权限");
                        }

                        break;
                    case Label.person_number:
                        if (App.INSTANCE().getUser().getPermissionValue().contains("jiucancount")) {
                            peopleNum();
                        } else {
                            warning("没有修改就餐人数权限");
                        }

                        break;
                    case Label.bil:
                        if (App.INSTANCE().getUser().getPermissionValue().contains("jiesuan")) {
                            intent = new Intent(OrderDetailActivity.this, BillActivity.class);
                            double detailFood = 0;
                            List<OrderDetailFood> orderDetailFoods = menuListAdapter.getData();
                            for (int i = 0; i < orderDetailFoods.size(); i++) {
                                detailFood += orderDetailFoods.get(i).getPrice() * (orderDetailFoods.get(i).getCount() - orderDetailFoods.get(i).getGiving());
                                detailFood += orderDetailFoods.get(i).getSeasonPrice() * (orderDetailFoods.get(i).getCount() - orderDetailFoods.get(i).getGiving());
                            }
                            intent.putExtra(Config.PARAM2, detailFood);//总价
                            intent.putExtra(Config.PARAM3, order);
                            intent.putExtra(Config.PARAM4, (Serializable) orderDetailFoods);
                            intent.putExtra(Config.PARAM5, BillActivity.P2);
                            startActivity(intent);
                        } else {
                            warning("没有结算权限");
                        }

                        break;
                    //绑定桌位
                    case Label.bind_table:
                        intent = new Intent(OrderDetailActivity.this, TableOperationActivity.class);
                        intent.putExtra(Config.PARAM1, TableOperationActivity.P6);
                        startActivity(intent);
                        event = new MsgEvent(MsgEvent.bindTable, order);
                        EventBus.getDefault().postSticky(event);
                        break;
                    case Label.hurry:
                        if (App.INSTANCE().getUser().getPermissionValue().contains("zhendancuicai")) {
                            presenter.hurry(order.getBillid(), order.getTableId(), order.getTableName());
                        } else {
                            warning("没有整单催菜权限");
                        }
                        break;
                    case Label.cancel:
                        presenter.cancel(order.getId());
                        break;
                    case Label.before:
                        presenter.print(order.getBillid(), order.getPeopleCount(), order.getTableId(), order.getTableName(),
                                order.getPayPrice(), order.getPayPrice(), 0, "7");
                        break;
                    case Label.confirm:
                        presenter.confir(order.getId(), order.getBillid(), order.getType());
                        break;
                    case Label.finish:
                        presenter.ofinis(order.getId());
                        break;
                    case Label.rebill:
                        if (!App.INSTANCE().getUser().getPermissionValue().contains("fanjiezhang")) {
                            warning("没有反结账权限");
                            return;
                        }
                        Intent intent1 = new Intent(OrderDetailActivity.this, OrderFoodActivity.class);
                        intent1.putExtra("foods", detailFoods);
                        intent1.putExtra("order", order);
                        intent1.putExtra(Config.PARAM1, OrderFoodActivity.P7);
                        startActivity(intent1);
                        break;
                    case Label.consumption:
                        presenter.print(order.getBillid(), order.getPeopleCount(), order.getTableId(), order.getTableName(),
                                order.getPayPrice(), order.getPayPrice(), 0, "8");
                        break;
                }
            }
        });

        if (order.getState() == 2) {
            mRecyclerView.setVisibility(View.GONE);
        }

        initButton();

        menuListAdapter = new MenuListAdapter(R.layout.item_menu_list, OrderDetailActivity.this, false);
        View header = LayoutInflater.from(this).inflate(R.layout.item_menu_list, (ViewGroup) mMenuRecyclerView.getParent(), false);
        menuListAdapter.addHeaderView(header);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };

        mMenuRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .marginResId(R.dimen._30sdp, R.dimen._1sdp)
                .color(Color.parseColor("#EEEEEE")).build());
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMenuRecyclerView.setAdapter(menuListAdapter);
        menuListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (order.getState() == 3 || order.getState() == 2) {
                    return;
                }
                OrderDetailFood orderDetailFood = menuListAdapter.getItem(position);
                showFoodsDialog(orderDetailFood, position);
            }
        });

        menuListAdapter.setNewData(detailFoods);
    }


    private void peopleNum() {
        CommonDialog.Builder builder = new CommonDialog.Builder(this, R.style.OrderDialogStyle);
        CommonDialogEntity dialog = new CommonDialogEntity();
        dialog.setTitle("修改人数");
        dialog.setShowPeople(true);
        Timber.d("pe" + order.getPeopleCount() + "c" + order.getTableWareCount());
        dialog.setPeopleNum(order.getPeopleCount());
        dialog.setCanJuNum(order.getTableWareCount());

        dialog.setShowCanJu(true);
        dialog.setOneBtnText("确定");
        builder.setEntity(dialog);
        builder.setButtonClick(new CommonDialog.OnButtonClick() {
            @Override
            public void onLeftClick(CommonDialogEntity commonDialogEntity) {

                //presenter.kaiTai(false, commonDialogEntity, entity.getRoomTableID(), position);
            }

            @Override
            public void onRightClick(CommonDialogEntity commonDialogEntity) {
                //  presenter.kaiTai(true, commonDialogEntity, entity.getRoomTableID(), position);
            }

            @Override
            public void onOneClick(CommonDialogEntity entity) {

                presenter.peopleNum(order, entity);
                builder.dismiss();
            }
        });
        builder.creater().show();

    }

    private void showFoodsDialog(OrderDetailFood orderDetailFood, int p) {
        List<RadioEntity> entities = new ArrayList<>();
        String[] arr = getResources().getStringArray(R.array.orderDetailFood);
        for (int i = 0; i < arr.length; i++) {

            RadioEntity entity = new RadioEntity();
            entity.setStr(arr[i]);
            if (i == 0) {
                entity.setSelected(true);
            }
            entities.add(entity);

        }
        RadioDialog.Builder radio = new RadioDialog.Builder(OrderDetailActivity.this, R.style.OrderDialogStyle);
        radio.setTitle(orderDetailFood.getProductNmae());
        radio.setBtnStr("确定");
        radio.setRadioEntities(entities);
        radio.setButtonClick(position1 -> {
            switch (position1) {
                case 0:
                    Intent intent = new Intent(OrderDetailActivity.this, TableOperationActivity.class);
                    intent.putExtra(Config.PARAM1, TableOperationActivity.P3);
                    startActivity(intent);
                    MsgEvent event = new MsgEvent(MsgEvent.Turn_the_dish, orderDetailFood, order.getTableId());
                    EventBus.getDefault().postSticky(event);
                    break;
                case 1:
                    presenter.cuicai(orderDetailFood.getDetailId(), order.getBillid(), order.getTableId(), order.getTableName());
                    break;
                case 2:
                    presenter.getReason(orderDetailFood);
                    break;
                case 3:
                    Give2Dialog.Builder builder = new Give2Dialog.Builder(OrderDetailActivity.this, R.style.OrderDialogStyle);
                    builder.setEntity(orderDetailFood);
                    builder.setButtonClick(new Give2Dialog.OnButtonClick() {

                        @Override
                        public void onBtnClick(OrderDetailFood orderDetailFood) {
                            menuListAdapter.notifyDataSetChanged();
                            presenter.givingFood(orderDetailFood);
                        }
                    });
                    builder.creater().show();
                    break;
            }
            radio.dismiss();
        });
        radio.creater().show();
    }

    private void setView() {
        orderId.setText(String.format(getString(R.string.string_order_id), order.getOrderNumber()));
        orderMoney.setText(String.format(getString(R.string.string_order_money), order.getPayPrice()));
        orderTime.setText(String.format(getString(R.string.string_order_time), order.getRecordDate()));
        table_id.setText(String.format(getString(R.string.string_table_id), order.getTableName()));
        orderOperator.setText(String.format(getString(R.string.string_order_operator), order.getUsername()));
        StringBuilder builder = new StringBuilder();
        if (tPayTypes != null && tPayTypes.size() > 0) {
            for (int i = 0; i < tPayTypes.size(); i++) {
                switch (tPayTypes.get(i).getPayType()) {
                    case "1":
                        builder = builder.append("现金：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    case "2":
                        builder = builder.append("银行卡：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    case "3":
                        builder = builder.append("主扫微信：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    case "4":
                        builder = builder.append("挂账：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    case "5":
                        builder = builder.append("会员卡：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    case "6":
                        builder = builder.append("被扫支付宝：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    case "7":
                        builder = builder.append("被扫微信：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    case "8":
                        builder = builder.append("美团券：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    case "9":
                        builder = builder.append("大众点评券：" + tPayTypes.get(i).getPice() + " |");
                        break;
                    default:
                        builder = builder.append("主扫支付宝：" + tPayTypes.get(i).getPice() + " |");
                        break;
                }
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        if (!builder.toString().isEmpty()) {
            tPayType.setVisibility(View.VISIBLE);
            tPayType.setText(builder.toString());
        }

//        if (order.getPayIs().equals("1")) {
//            payStatus.setText("支付状态：已支付");
//        } else if (order.getPayIs().equals("2")) {
//            payStatus.setText("支付状态：未支付");
//        }
    }

    private void initButton() {
        List<Label> labels = new ArrayList<>();
        String orderType = order.getType();
        String orderSate = order.getOrderSate();

//        1、如果订单类型为“店内点餐（type=5）”或者“扫码点餐(type=7)”并且订单状态为“已确认（OrderSate=2）”
//        订单详细显示“加菜”“换桌”“撤单”“并单”“后厨打印”“整单催菜”“结算”“修改人数”
        if ((orderType.equals("5") || orderType.equals("7")) && orderSate.equals("2")) {
            labels.add(new Label("加菜", R.mipmap.icon_addfood, Label.addfood));
            labels.add(new Label("换桌", R.mipmap.icon_desk, Label.desk));
            labels.add(new Label("撤单", R.mipmap.icon_undo, Label.undo));
            labels.add(new Label("并单", R.mipmap.icon_merge, Label.merge));
            labels.add(new Label("商品补打", R.mipmap.icon_print_after, Label.print_after));
            labels.add(new Label("整单催菜", R.mipmap.icon_hurry, Label.hurry));
            labels.add(new Label("修改人数", R.mipmap.iocn_person_number, Label.person_number));
            labels.add(new Label("账单结算", R.mipmap.icon_bil, Label.bil));
        } else if ((orderType.equals("5") || orderType.equals("7") || orderType.equals("4")) && orderSate.equals("3")) {
//            2、如果订单类型为“店内点餐（type=5）”和“扫码点餐(type=7)”并且订单状态为“已完成（OrderSate=3）”
//            订单详细显示“打印消费单（就是补打消费单的意思）”
            //labels.add(new Label("前台打印", R.mipmap.icon_print_before, Label.before));
            labels.add(new Label("补打账单", R.mipmap.icon_print_before, Label.before));
        } else if (orderType.equals("3") && (orderSate.equals("1") || orderSate.equals("2"))) {
//            3、如果订单类型为“外卖（type=3）”并且订单状态为“待处理（OrderSate=1）”或者“已确认（OrderSate=2）”
//            订单详细显示“取消”“确认”“已完成”
            labels.add(new Label("取消", R.mipmap.icon_addfood, Label.cancel));
            labels.add(new Label("确认", R.mipmap.icon_desk, Label.confirm));
            labels.add(new Label("已发货", R.mipmap.icon_undo, Label.finish));
        } else if (orderType.equals("3") && orderSate.equals("3")) {
//            4、如果订单类型为“外卖（type=3）”并且订单状态为“已完成（OrderSate=3）”
//            订单详细显示“打印消费单（就是补打消费单的意思）”
            labels.add(new Label("补打账单", R.mipmap.icon_print_before, Label.before));
        } else if (orderType.equals("4") && (orderSate.equals("1") || orderSate.equals("2"))) {
//            5、如果订单类型为“快餐（type=4）”并且订单状态为“待处理（OrderSate=1）”或者“已确认（OrderSate=2）”
//            订单详细显示“取消”“确认”“已完成”
            labels.add(new Label("取消", R.mipmap.icon_addfood, Label.cancel));
            labels.add(new Label("确认", R.mipmap.icon_desk, Label.confirm));
            labels.add(new Label("已发货", R.mipmap.icon_undo, Label.finish));
        } else if (orderType.equals("1") && orderSate.equals("3")) {
//            6、如果订单类型为“快餐（type=4）”并且订单状态为“已完成（OrderSate=3）”
//            订单详细显示“打印消费单（就是补打消费单的意思）”
            labels.add(new Label("补打账单", R.mipmap.icon_print_before, Label.before));
        } else if ((orderType.equals("1") || orderType.equals("2")) && orderSate.equals("1")) {
//            7、如果订单类型为“预定桌位（type=1）”或者“预定菜品(type=2)”并且订单状态为“待处理（OrderSate=1）”
//            订单详细显示“绑定桌位”
            labels.add(new Label("绑定桌位", R.mipmap.iocn_person_number, Label.bind_table));
        } else if ((orderType.equals("1") || orderType.equals("2")) && orderSate.equals("2")) {
//            8、如果订单类型为“预定桌位（type=1）”或者“预定菜品(type=2)”并且订单状态为“已确认（OrderSate=2）”
//            订单详细显示“加菜”“换桌”“撤单”“并单”“后厨打印”“整单催菜”“结算”“修改人数”
            labels.add(new Label("加菜", R.mipmap.icon_addfood, Label.addfood));
            labels.add(new Label("换桌", R.mipmap.icon_desk, Label.desk));
            labels.add(new Label("撤单", R.mipmap.icon_undo, Label.undo));
            labels.add(new Label("并单", R.mipmap.icon_merge, Label.merge));
            labels.add(new Label("商品补打", R.mipmap.icon_print_after, Label.print_after));
            labels.add(new Label("整单催菜", R.mipmap.icon_hurry, Label.hurry));
            labels.add(new Label("修改人数", R.mipmap.iocn_person_number, Label.person_number));
            labels.add(new Label("账单结算", R.mipmap.icon_bil, Label.bil));
        } else if ((orderType.equals("1") || orderType.equals("2")) && orderSate.equals("3")) {
//            9、如果订单类型为“预定桌位（type=1）”或者“预定菜品(type=2)”并且订单状态为“已完成（OrderSate=3）”
//            订单详细显示“打印消费单（就是补打消费单的意思）”
            labels.add(new Label("补打账单", R.mipmap.icon_print_before, Label.before));
        } else if (orderType.equals("6")) {
//            10、如果订单类型为“排队点餐（意思就是在微信里面排队以后可以提前点餐）（type=6）”
//            订单详细显示“取消”“绑定桌位”
            labels.add(new Label("取消", R.mipmap.icon_addfood, Label.cancel));
            labels.add(new Label("绑定桌位", R.mipmap.iocn_person_number, Label.bind_table));
        } else if ((orderType.equals("1") || orderType.equals("2")) && orderSate.equals("7")) {
            labels.add(new Label("加菜", R.mipmap.icon_addfood, Label.addfood));
            labels.add(new Label("换桌", R.mipmap.icon_desk, Label.desk));
            labels.add(new Label("撤单", R.mipmap.icon_undo, Label.undo));
            labels.add(new Label("并单", R.mipmap.icon_merge, Label.merge));
            labels.add(new Label("商品补打", R.mipmap.icon_print_after, Label.print_after));
            labels.add(new Label("整单催菜", R.mipmap.icon_hurry, Label.hurry));
            labels.add(new Label("修改人数", R.mipmap.iocn_person_number, Label.person_number));
            labels.add(new Label("账单结算", R.mipmap.icon_bil, Label.bil));
        }

        if (!orderType.equals("3") && type != P2 &&
                !orderType.equals("1") && TextUtils.isEmpty(order.getBillIdMerger())) {
            labels.add(new Label("反结账", R.mipmap.anti_settlement, Label.rebill));
        }

        //labels.add(new Label("预打消费单", R.mipmap.icon_print_before, Label.consumption));
        labelAdapter.setNewData(labels);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                back();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void back() {
        MsgEvent event;
        if (type == P1) {
            event = new MsgEvent(MsgEvent.OrderList, order, position);
            EventBus.getDefault().post(event);
        } else if (type == P2) {
            switch (p2_type) {
                case P2_UNDO:
                    event = new MsgEvent(MsgEvent.OrderTable, tableOpen, position);
                    EventBus.getDefault().post(event);
                    break;
                case P2_CHANGE:
                    event = new MsgEvent(MsgEvent.OrderTableChangeSuccess, position, p);
                    EventBus.getDefault().post(event);
                    break;
            }
        }

        finish();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
        }
        return false;
    }

    private int p = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        int i = event.getType();
        if (i == MsgEvent.AddFood) {
            presenter.getOrderDetail(order);
        } else if (i == MsgEvent.ChangeSuccess) {
            order = (Order) event.getData();
            table_id.setText(String.format(getString(R.string.string_table_id), order.getTableName()));
            p = (int) event.getOther();
            p2_type = P2_CHANGE;
        } else if (i == MsgEvent.bindTableSuccess) {
            order = (Order) event.getData();
            table_id.setText(String.format(getString(R.string.string_table_id), order.getTableName()));
            setResult(RESULT_OK);
            finish();
        } else if (i == MsgEvent.billSuccess) {
            if (order.getOrderSource().equals("1")) {
                order.setOrderSate("3");
                order.setPayIs("1");
            }
            event = new MsgEvent(MsgEvent.OrderList, order, position);
            EventBus.getDefault().post(event);
            finish();
        } else if (i == MsgEvent.mergeSuccess) {
            event = new MsgEvent(MsgEvent.mergeSuccessFinish);
            EventBus.getDefault().post(event);
            finish();
        } else if (i == MsgEvent.TurnSuccess) {
            presenter.getOrderDetail(order);
        } else if (i == MsgEvent.reBillSuccess) {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void givingSuccess() {
        presenter.getOrder(order.getTableId());
    }

    @Override
    public void orderSuccess(Order order, List<OrderDetailFood> orderDetailFoods) {
        menuListAdapter.setNewData(orderDetailFoods);
    }

    @Override
    public void message(String s) {
        Toasty.normal(this, s, Toast.LENGTH_SHORT).show();
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
    public void toDetail(List<OrderDetailFood> orderDetailFoods, List<TPayType> tPayTypesList) {
        tPayTypes = tPayTypesList;
        menuListAdapter.setNewData(orderDetailFoods);
        double m = 0;
        for (OrderDetailFood o : orderDetailFoods) {
            m += (o.getPrice() * o.getAmmount());
        }
        order.setPayPrice(m);
        orderMoney.setText(String.format(getString(R.string.string_order_money), order.getPayPrice()));
        setView();
    }

    @Override
    public void undoSuccess() {
        order.setOrderSate("2");
        tableOpen = "0";
        p2_type = P2_UNDO;
        back();
    }

    @Override
    public void peopleNumSuccess(int peopleNum, int canJuNum) {
        Timber.d("pe" + peopleNum + "c" + canJuNum);
        Toasty.success(this, "修改人数成功", Toast.LENGTH_SHORT, true).show();
        order.setPeopleCount(peopleNum);
        order.setTableWareCount(canJuNum);

    }

    @Override
    public void retreatFoodSuccess(OrderDetailFood p) {
        presenter.getOrder(order.getTableId());
        Toasty.success(this, "退菜成功", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void cuicaiSuccess() {
        Toasty.success(this, "催菜成功", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void cancelSuccess() {
        Toasty.success(this, "取消订单成功", Toast.LENGTH_SHORT, true).show();
        MsgEvent event = new MsgEvent(MsgEvent.message, position);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void confirmSuccess() {
        Toasty.success(this, "确认订单成功", Toast.LENGTH_SHORT, true).show();
        MsgEvent event = new MsgEvent(MsgEvent.message, position);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void finishSuccess() {
        Toasty.success(this, "完成订单成功", Toast.LENGTH_SHORT, true).show();
        MsgEvent event = new MsgEvent(MsgEvent.message, position);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void showReason(List<RetreatReason> retreatReasons, OrderDetailFood orderDetailFood) {
        RetreatReason reason = new RetreatReason();
        reason.setType(RetreatReason.EDIT);
        reason.setGuid("");
        reason.setRestaurantId(App.INSTANCE().getShopID());
        retreatReasons.add(0, reason);
        CommonResonDialog.Builder builder = new CommonResonDialog.Builder(OrderDetailActivity.this, R.style.OrderDialogStyle);
        builder.setBtnStr("确定");
        builder.setMax(orderDetailFood.getCount());
        builder.setReasons(retreatReasons);
        builder.setButtonClick(new CommonResonDialog.OnButtonClick() {
            @Override
            public void onBtnClick(List<RetreatReason> reasons, int num) {
                RetreatReason r = new RetreatReason();
                for (int i = 1; i < reasons.size(); i++) {
                    r = reasons.get(i);
                    if (r.isSelector()) {
                        break;
                    }
                }
                presenter.retreatFood(order.getBillid(), orderDetailFood.getDetailId(), orderDetailFood, order.getTableId(),
                        order.getTableName(), orderDetailFood.getCount() - num, orderDetailFood.getPrice(),
                        num, orderDetailFood.getWeight(), reasons.get(0).getRemark(), r.getGuid(), r.getRemark());
                builder.dismiss();
            }
        });
        builder.setTitle("退菜原因");
        builder.creater().show();
    }
}
