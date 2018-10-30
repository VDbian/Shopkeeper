package com.admin.shopkeeper.ui.fragment.table;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.MsgEvent;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.TableAdapter;
import com.admin.shopkeeper.base.DelayFragment;
import com.admin.shopkeeper.dialog.CommonDialog;
import com.admin.shopkeeper.dialog.RadioDialog;
import com.admin.shopkeeper.entity.BillJson;
import com.admin.shopkeeper.entity.CommonDialogEntity;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.RadioEntity;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.entity.WeixinOrderBean;
import com.admin.shopkeeper.ui.activity.bill.BillActivity;
import com.admin.shopkeeper.ui.activity.orderDetail.OrderDetailActivity;
import com.admin.shopkeeper.ui.activity.orderFood.OrderFoodActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import timber.log.Timber;

import static com.admin.shopkeeper.ui.activity.bill.BillActivity.REQUEST_CODE;


public class TableFragment extends DelayFragment<TablePresenter> implements ITableView {

    public static final String ARG_PARAM1 = "param1";

    private int type = 0;
    private Order order;
    private OrderDetailFood orderDetailFood;
    private String cTableId;


    private String mParam1;
    private TableAdapter mAdapter;
    @BindView(R.id.ptrLayout)
    PtrFrameLayout ptrLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private double money;
    private String foodInfo;

    @BindView(R.id.button)
    AppCompatButton button;

    @OnClick(R.id.button)
    void onClick() {
        getOrderList();
    }


    private CommonDialog.Builder builder;

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_table;
    }

    @Override
    protected void initPresenter() {
        presenter = new TablePresenter(getActivity(), this);
        presenter.init();
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        initPTRLayout();
        initRecyclerView();
        /*完成初始化*/
        isPrepared = true;
        /*
        * 因为setUserVisibleHint会比onCreateView先执行，为了防止空指针错误，需要执行了咋样load();
        * */
        lazyLoad();
    }

    private void initRecyclerView() {
        mAdapter = new TableAdapter(R.layout.item_table);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TableEntity entity = mAdapter.getItem(position);
                assert entity != null;
                presenter.getTableData(entity.getRoomTableID(), position);
            }
        });
    }

    private void showChangeTable(int position, TableEntity entity, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage(s + entity.getTableName());
        builder.setPositiveButton("确定", (dialog, which) -> {
            if (type == 1) {
                presenter.changeTable(position, entity, order);
            } else if (type == 2) {
                presenter.merge(entity, order);
            } else if (type == 3) {
                presenter.trun(entity, orderDetailFood, cTableId);
            } else if (type == MsgEvent.kuaican) {
                presenter.KuaiSu(entity, foodInfo, "", "", "", "", "", "", money, entity.getRoomTableID(), entity.getTableName(), "4");
            } else if (type == MsgEvent.bindTable) {
                presenter.bindTable(order.getId(), entity.getRoomTableID(), entity.getTableName(), App.INSTANCE().getShopID(), App.INSTANCE().getUser().getName());
            }

        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void showDialog(TableEntity entity, int position) {
        List<RadioEntity> entities = new ArrayList<>();
        String[] arr = getResources().getStringArray(R.array.tableArray2);
        for (int i = 0; i < arr.length; i++) {
            RadioEntity e = new RadioEntity();
            e.setStr(arr[i]);
            if (i == 0) {
                e.setSelected(true);
            }
            entities.add(e);
        }

        RadioDialog.Builder radio = new RadioDialog.Builder(getActivity(), R.style.OrderDialogStyle);
        radio.setRadioEntities(entities);
        radio.setTitle(entity.getTableName());
        radio.setBtnStr("确定");
        radio.setButtonClick(p -> {
            switch (p) {
                case 0:
                    Intent intent = new Intent(getActivity(), OrderFoodActivity.class);
                    intent.putExtra(Config.PARAM1, OrderFoodActivity.P1);//堂点
                    intent.putExtra(Config.PARAM2, entity);
                    intent.putExtra(Config.PARAM3, position);
                    startActivityForResult(intent, 998);
                    break;
                case 1:
                    if (App.INSTANCE().getUser().getPermissionValue().contains("qingtai")) {
                        presenter.qingtai(position, entity.getBillID(), entity.getRoomTableID());
                    }else {
                        Toast.makeText(getActivity(),"没有清台权限",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            radio.dismiss();
        });
        radio.creater().show();
    }

    private void showKaiDan(TableEntity entity, int position) {
        builder = new CommonDialog.Builder(getActivity(), R.style.OrderDialogStyle);
        CommonDialogEntity dialog = new CommonDialogEntity();
        dialog.setTitle(entity.getTableName());
        dialog.setShowPeople(true);
        dialog.setShowCanJu(true);
        dialog.setMoreBtn(true);
        dialog.setLeftBtnText("开台");
        dialog.setRightBtnText("开台点菜");
        builder.setEntity(dialog);
        builder.setButtonClick(new CommonDialog.OnButtonClick() {
            @Override
            public void onLeftClick(CommonDialogEntity commonDialogEntity) {
                presenter.kaiTai(false, commonDialogEntity, entity.getRoomTableID(), position);
            }

            @Override
            public void onRightClick(CommonDialogEntity commonDialogEntity) {
                presenter.kaiTai(true, commonDialogEntity, entity.getRoomTableID(), position);
            }

            @Override
            public void onOneClick(CommonDialogEntity entity) {

            }
        });
        builder.creater().show();

    }

    private void initPTRLayout() {
        MaterialHeader header = new MaterialHeader(getContext());
        int c[] = {ContextCompat.getColor(getContext(), R.color.colorPrimary)};
        header.setColorSchemeColors(c);
        header.setPadding(0, PtrLocalDisplay.dp2px(20), 0, PtrLocalDisplay.dp2px(20));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        ptrLayout.setDurationToCloseHeader(1000);
        ptrLayout.setHeaderView(header);
        ptrLayout.addPtrUIHandler(header);
        ptrLayout.setPinContent(true);//设置为true时content的内容位置将不会改变

        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }


            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.getTables(mParam1);
            }
        });
    }

    @Override
    public void error(String message) {
        refreshComplete();
        Toasty.error(getContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    private void refreshComplete() {
        if (ptrLayout.isRefreshing()) {
            ptrLayout.refreshComplete();
        }
    }

    @Override
    public void showTable(List<TableEntity> list) {
        refreshComplete();
        Timber.d("type" + type);
        if (type == MsgEvent.ChangeTable) {
            List<TableEntity> newList = new ArrayList<>();
            for (TableEntity entity : list) {
                if ((entity.getOpen().equals("0"))) {
                    newList.add(entity);
                }
            }
            mAdapter.setNewData(newList);
        } else if (type == 2) {
            List<TableEntity> newList = new ArrayList<>();
            for (TableEntity entity : list) {
                if ((entity.getOpen().equals("2")) && !entity.getRoomTableID().equals(order.getTableId())) {
                    newList.add(entity);
                }
            }
            if (newList.size() == 0) {
                warning("该房间其他桌台还未下单！");
            }
            mAdapter.setNewData(newList);
        } else if (type == 3) {
            List<TableEntity> newList = new ArrayList<>();
            for (TableEntity entity : list) {
                if ((entity.getOpen().equals("2"))) {
                    newList.add(entity);
                }
            }
            mAdapter.setNewData(newList);
        } else if (type == MsgEvent.kuaican) {
            List<TableEntity> newList = new ArrayList<>();
            for (TableEntity entity : list) {
                if ((entity.getOpen().equals("0"))) {
                    newList.add(entity);
                }
            }
            mAdapter.setNewData(newList);
        } else if (type == MsgEvent.bindTable) {
            List<TableEntity> newList = new ArrayList<>();
            for (TableEntity entity : list) {
                if ((entity.getOpen().equals("0"))) {
                    newList.add(entity);
                }
            }
            mAdapter.setNewData(newList);
        } else mAdapter.setNewData(list);

    }

    @Override
    public void showTable(TableEntity entity, int position) {
        assert entity != null;
        mAdapter.setData(position, entity);
        switch (entity.getOpen()) {
            case "1":
                //已经开桌
                //presenter.toFoods();
                showDialog(entity, position);
                break;
            case "2":
                //已下单
                if (type == 2) {
                    entity.setSelector(!entity.isSelector());
                    mAdapter.setMerge(true);
                    mAdapter.notifyItemChanged(position, entity);
//                            showChangeTable(position, entity, "是否合并桌台：");
                } else if (type == 3) {
                    showChangeTable(position, entity, "是否转菜到桌台：");
                } else {
                    presenter.getOrder(entity, position);
                }
                break;
            case "0":
                //空闲
                if (type == 1) {
                    showChangeTable(position, entity, "是否换到桌台：");
                } else if (type == MsgEvent.bindTable) {
                    showChangeTable(position, entity, "是否绑定到桌台：");
                } else if (type == MsgEvent.kuaican) {
                    showChangeTable(position, entity, "是否快餐在桌台：");
                } else {
                    showKaiDan(entity, position);
                }
                break;
            case "4":
                presenter.getOrder(entity, position);
                break;
        }
    }

    @Override
    public void warning(String s) {
        refreshComplete();
        Toasty.warning(getContext(), s, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void success(boolean b, int position, String result, CommonDialogEntity dialogEntity) {
        builder.dismiss();
        Toasty.success(getContext(), "开台成功", Toast.LENGTH_SHORT, true).show();
        TableEntity entity = mAdapter.getItem(position);
        if (b) {
            assert entity != null;
            entity.setBillID(result);
            entity.setTableWareCount(dialogEntity.getPeopleNum() + "");
            Intent intent = new Intent(getActivity(), OrderFoodActivity.class);
            intent.putExtra(Config.PARAM1, OrderFoodActivity.P1);//堂点
            intent.putExtra(Config.PARAM2, entity);
            intent.putExtra(Config.PARAM3, position);
            startActivityForResult(intent, 998);
        } else {
            assert entity != null;
            ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
        }
    }

    WeixinOrderBean weixinOrderBean;

    @Override
    public void success(List<WeixinOrderBean> beans) {
        this.weixinOrderBean = beans.get(0);
        if (weixinOrderBean == null) {
            Toasty.error(getContext(), "获取数据失败", Toast.LENGTH_SHORT, true).show();
            //finish();
            return;
        }
    }

    @Override
    public void fail() {

    }

    @Override
    public void changeTableSuccess(int position, String tableName, String tableId) {
        Toasty.success(getContext(), "换桌成功", Toast.LENGTH_SHORT, true).show();

        order.setTableId(tableId);
        order.setTableName(tableName);
        MsgEvent msgEvent = new MsgEvent(MsgEvent.ChangeSuccess, order, position);
        EventBus.getDefault().post(msgEvent);
        getActivity().finish();
    }

    @Override
    public void qingtaiSuccess(int position) {
        Toasty.success(getContext(), "清台成功", Toast.LENGTH_SHORT, true).show();
        TableEntity e = mAdapter.getItem(position);
        assert e != null;
        e.setOpen("0");
        mAdapter.notifyItemChanged(position, e);
    }

    @Override
    public void trunSuccess() {
        Toasty.success(getActivity(), "转菜成功", Toast.LENGTH_SHORT, true).show();
        MsgEvent msgEvent = new MsgEvent(MsgEvent.TurnSuccess);
        EventBus.getDefault().post(msgEvent);
        getActivity().finish();
    }

    @Override
    public void orderSuccess(Order order, List<OrderDetailFood> orderDetailFoods, int position) {
        List<RadioEntity> entities = new ArrayList<>();
        String[] arr = getResources().getStringArray(R.array.tableArray4);
        for (int i = 0; i < arr.length; i++) {

            RadioEntity entity = new RadioEntity();
            entity.setStr(arr[i]);
            if (i == 0) {
                entity.setSelected(true);
            }
            entities.add(entity);
        }
        RadioDialog.Builder radio = new RadioDialog.Builder(getActivity(), R.style.OrderDialogStyle);
        radio.setBtnStr("确定");

//        <item>结账</item>
//        <item>加菜</item>
//        <item>详情</item>
//        <item>撤单</item>
//        <item>扫码结账</item>

        radio.setButtonClick(p -> {
            Intent intent;
            switch (p) {
                case 0:
                    if (App.INSTANCE().getUser().getPermissionValue().contains("jiesuan")) {
                        presenter.inBill(order, orderDetailFoods, position,false);
//                        intent = new Intent(getActivity(), BillActivity.class);
//                        double detailFood = 0;
////                        for (int i = 0; i < orderDetailFoods.size(); i++) {
////                            detailFood += orderDetailFoods.get(i).getPrice() * orderDetailFoods.get(i).getAmmount();
////                            detailFood += orderDetailFoods.get(i).getSeasonPrice() * orderDetailFoods.get(i).getAmmount();
////                        }
//                        for (int i = 0; i < orderDetailFoods.size(); i++) {
//                            detailFood += orderDetailFoods.get(i).getPrice() * (orderDetailFoods.get(i).getAmmount() - orderDetailFoods.get(i).getGiving());
//                            detailFood += orderDetailFoods.get(i).getSeasonPrice() * (orderDetailFoods.get(i).getAmmount() - orderDetailFoods.get(i).getGiving());
//                        }
//                        intent.putExtra(Config.PARAM2, detailFood);//总价
//                        intent.putExtra(Config.PARAM3, order);
//                        intent.putExtra(Config.PARAM4, (Serializable) orderDetailFoods);
//                        intent.putExtra(Config.PARAM1, position);
//                        intent.putExtra(Config.PARAM5, BillActivity.P1);
//                        startActivity(intent);
                    } else {
                        warning("没有结算权限");
                    }
                    break;
                case 1:
                    if (App.INSTANCE().getUser().getPermissionValue().contains("jiacai")) {
                        intent = new Intent(getActivity(), OrderFoodActivity.class);
                        intent.putExtra(Config.PARAM1, OrderFoodActivity.P3);
                        intent.putExtra(Config.PARAM2, order);
                        startActivityForResult(intent, 998);
                    } else {
                        warning("没有加菜权限");
                    }
                    break;
                case 2:
                    intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra(Config.PARAM1, order);
                    intent.putExtra(Config.PARAM2, (Serializable) orderDetailFoods);
                    intent.putExtra(Config.PARAM3, position);
                    intent.putExtra(Config.PARAM4, OrderDetailActivity.P2);
                    startActivityForResult(intent, 998);
                    break;

               /* case 3:
                    if (App.INSTANCE().getUser().getPermissionValue().contains("chedan")) {
                        AlertDialog.Builder undoBuilder = new AlertDialog.Builder(getActivity());
                        undoBuilder.setTitle("提醒！");
                        undoBuilder.setMessage("是否要进行撤单操作？");
                        undoBuilder.setPositiveButton("确定", (dialog, which) -> presenter.undo(order.getTableId(), order.getBillid(), position, order.getTableName(), order.getPayPrice() + ""));
                        undoBuilder.show();
                    } else {
                        warning("没有撤单权限");
                    }
                    break;*/
                case 3:
                    if (App.INSTANCE().getUser().getPermissionValue().contains("jiesuan")) {
                        presenter.inBill(order, orderDetailFoods, position,true);
                    } else {
                        warning("没有结算权限");
                    }


                    break;
            }
            radio.dismiss();
        });
        radio.setRadioEntities(entities);
        radio.setTitle("提示");
        radio.creater().show();


    }

    @Override
    public void undoSuccess(int position) {
        Toasty.success(getActivity(), "撤单成功", Toast.LENGTH_SHORT, true).show();
        TableEntity entity = mAdapter.getItem(position);
        assert entity != null;
        entity.setOpen("0");

        mAdapter.notifyItemChanged(position, entity);
    }

    @Override
    public void kuaisuSuccess(TableEntity entity, String s, double monery) {
        Toasty.success(getActivity(), "下单成功", Toast.LENGTH_SHORT, true).show();
        Intent intent = new Intent(getActivity(), BillActivity.class);
        intent.putExtra(Config.PARAM2, monery);//总价
        intent.putExtra(Config.PARAM3, entity);
        //intent.putExtra(Config.PARAM4, );
        intent.putExtra(Config.PARAM1, s);
        intent.putExtra(Config.PARAM5, BillActivity.P3);
        startActivityForResult(intent, 998);
        getActivity().finish();

    }

    @Override
    public void bindSuccess(String tableId, String tableName) {
        Toasty.success(getContext(), "绑定成功", Toast.LENGTH_SHORT, true).show();
        order.setTableId(tableId);
        order.setTableName(tableName);
        MsgEvent msgEvent = new MsgEvent(MsgEvent.bindTableSuccess, order);
        EventBus.getDefault().post(msgEvent);
        getActivity().finish();
    }

    @Override
    public void orderListSuccess(Order order, List<OrderDetailFood> orderDetailFoods) {
        Intent intent = new Intent(getActivity(), BillActivity.class);
        double detailFood = 0;
        for (int i = 0; i < orderDetailFoods.size(); i++) {
            detailFood += orderDetailFoods.get(i).getPrice() * orderDetailFoods.get(i).getAmmount();
        }
        intent.putExtra(Config.PARAM2, detailFood);//总价
        intent.putExtra(Config.PARAM3, order);
        intent.putExtra(Config.PARAM4, (Serializable) orderDetailFoods);
        intent.putExtra(Config.PARAM5, BillActivity.P5);
        startActivityForResult(intent, 998);

        getActivity().finish();
    }
    double total;
    double memberTotal;
  /*  public double getTotal(List<OrderDetailFood> orderDetailFoods) {
        double total = 0;
        for (OrderfoodEntity entity : orderDetailFoods) {
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
                        total += (entity.getNumber() - entity.getGivingnum()) * season.getPrice();
                    }
                }
            }
        }
        return total;
    }*/
    @Override
    public void inBillSuccess(Order order1, List<OrderDetailFood> orderDetailFoods, int position , boolean isScanBill) {
        Intent intent;
        if(isScanBill){
            presenter.getOrderData(order1.getBillid() , order1.getType());
            total = 0;
            memberTotal = 0;
            for (int i = 0; i < orderDetailFoods.size(); i++) {
                total += orderDetailFoods.get(i).getPrice() * (orderDetailFoods.get(i).getAmmount() - orderDetailFoods.get(i).getGiving());
                total += orderDetailFoods.get(i).getSeasonPrice() * (orderDetailFoods.get(i).getAmmount() - orderDetailFoods.get(i).getGiving());

                memberTotal += orderDetailFoods.get(i).getMemberPrice() * (orderDetailFoods.get(i).getAmmount() - orderDetailFoods.get(i).getGiving());
                memberTotal += orderDetailFoods.get(i).getSeasonPrice() * (orderDetailFoods.get(i).getAmmount() - orderDetailFoods.get(i).getGiving());
            }
            order = order1;
            intent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }else{
             intent = new Intent(getActivity(), BillActivity.class);
            double detailFood = 0;
            for (int i = 0; i < orderDetailFoods.size(); i++) {
                detailFood += orderDetailFoods.get(i).getPrice() * (orderDetailFoods.get(i).getAmmount() - orderDetailFoods.get(i).getGiving());
                detailFood += orderDetailFoods.get(i).getSeasonPrice() * (orderDetailFoods.get(i).getAmmount() - orderDetailFoods.get(i).getGiving());
            }
            intent.putExtra(Config.PARAM2, detailFood);//总价
            intent.putExtra(Config.PARAM3, order1);
            intent.putExtra(Config.PARAM4, (Serializable) orderDetailFoods);
            intent.putExtra(Config.PARAM1, position);
            intent.putExtra(Config.PARAM5, BillActivity.P1);
            startActivityForResult(intent, 998);
        }
    }

    @Override
    public void cancelSuccess() {
        if(isShowToast){
            Toasty.success(getContext(), "取消成功", Toast.LENGTH_SHORT, true).show();
        }
        presenter.getTables(mParam1);
    }

    @Override
    public void showCancelDialog(Order order) {
        if (!App.INSTANCE().getUser().getPermissionValue().contains("quxiaojiezhang")) {
            warning("该桌位结账中");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("是否取消结账？");
        builder.setPositiveButton("确定", (dialog, which) -> {
            presenter.cancelBill(order.getBillid());
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    public void lazyLoad() {
         /* 判断是否满足条件
                * */
        if (!isVisible || !isPrepared || mHasLoadedOnce) {
            return;
        }
        ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
        mHasLoadedOnce = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMsgEventWithSticky(MsgEvent event) {
        type = event.getType();
        switch (type) {
            case MsgEvent.ChangeTable:
                order = (Order) event.getData();
                break;
            case MsgEvent.merge:
                button.setVisibility(View.VISIBLE);
                order = (Order) event.getData();
                break;
            case MsgEvent.Turn_the_dish:
                orderDetailFood = (OrderDetailFood) event.getData();
                cTableId = (String) event.getOther();
                break;
            case MsgEvent.kuaican:
                foodInfo = (String) event.getData();
                money = (double) event.getOther();
                break;
            case MsgEvent.bindTable:
                order = (Order) event.getData();
                break;

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEventNoSticky(MsgEvent event) {
        type = event.getType();
        switch (type) {
            case MsgEvent.bill: //结账
                int i = (int) event.getData();

                TableEntity ebill = mAdapter.getItem(i);
                if (ebill != null && isVisible) {
                    ebill.setOpen("0");
                    mAdapter.notifyItemChanged(i, ebill);
                }
                break;
            case MsgEvent.TangDian:
                ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
                break;
            case MsgEvent.OrderTable:
                String s1 = (String) event.getData();
                int position1 = (int) event.getOther();
                TableEntity e1 = mAdapter.getItem(position1);
                if (e1 != null && isVisible) {
                    e1.setOpen(s1);
                    mAdapter.notifyItemChanged(position1, e1);
                }
                break;
            case MsgEvent.OrderTableChangeSuccess:
                int o = (int) event.getData();
                int n = (int) event.getOther();
                TableEntity oEntity = mAdapter.getItem(o);
                TableEntity nEntity = mAdapter.getItem(n);

                if (oEntity != null && nEntity != null && isVisible) {
                    oEntity.setOpen("0");
                    nEntity.setOpen("2");
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case MsgEvent.mergeSuccessFinish:
                button.setVisibility(View.GONE);
                ptrLayout.postDelayed(() -> ptrLayout.autoRefresh(), 100);
                break;
        }
    }

    public void getOrderList() {
        String name = "";
        for (TableEntity entity : mAdapter.getData()) {
            if (entity.isSelector()) {
                name += entity.getTableName() + ",";
                string += entity.getRoomTableID() + ",";
            }
        }
        if (!TextUtils.isEmpty(name)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("并单提示");
            builder.setMessage("是否并单处理" + name + order.getTableName());
            builder.setPositiveButton("确定", (dialog, which) -> {
                presenter.getOrderList(string + order.getTableId(), order);
            });
            builder.show();

        }
    }
    private void scanbill(String payType, String result, double money, String memberId) {
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

        presenter.bill(result, App.INSTANCE().getShopID(), order.getTableId(), money, 0, qStr
                , tStr, pStr, payType, 1, money, order.getTableName(), 0, "7", memberId);
    }
    @Override
    public void scanBillSuccess(String payType, String result, double money, String memberId, String str) {
        if (str.contains("支付中")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
            builder.setMessage("用户正在支付中，是否确认已支付");
            builder.setPositiveButton("确定", (dialog, which) -> {
                scanbill(payType, result, money, memberId);
            });
            builder.setCancelable(false);
            builder.setNegativeButton("取消", null);
            builder.show();
        } else {
            scanbill(payType, result, money, memberId);
        }
    }
    @Override
    public void billSuccess(String msg,String result){
        Toasty.success(getActivity(), msg, Toast.LENGTH_SHORT, true).show();
        presenter.getTables(mParam1);
    }
    boolean isShowToast = true;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 998) {
            presenter.getTables(mParam1);
        }
        if(requestCode == 5){
            isShowToast = false;
            presenter.cancelBill(order.getBillid());
        }
        if (data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle == null) {
            return;
        }
        if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
            String result = bundle.getString(CodeUtils.RESULT_STRING);
           /* if(weixinOrderBean != null){
                total = weixinOrderBean.getMemberpiceNew();
            }*/
            Log.d("dj","总价"+total +"会员价："+memberTotal);
            presenter.scanBill(result, total,memberTotal , order.getBillid());
            total=0;
        } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
            warning("解析二维码失败");
        }
    }

    private String string = "";

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
