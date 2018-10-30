package com.admin.shopkeeper.ui.fragment.table;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.CommonDialogEntity;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.entity.WeixinOrderBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.Print;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by admin on 2017/4/20.
 */

class TablePresenter extends BasePresenter<ITableView> {
    TablePresenter(Context context, ITableView iView) {
        super(context, iView);
    }

    void getTables(String id) {
        RetrofitHelper.getInstance()
                .getApi()
                .getTabels("0", id, App.INSTANCE().getShopID(), 1, 1000)
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(model -> {
                    switch (model.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            TableEntity[] entities = new Gson().fromJson(model.getResult(), TableEntity[].class);
                            List<TableEntity> list = Arrays.asList(entities);
                            if (list.size() > 0) {
                                iView.showTable(Arrays.asList(entities));
                            } else {
                                //iView.warning("桌台列表为空");
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.error(model.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> iView.warning("获取桌台列表失败"));
    }

    void kaiTai(boolean b, CommonDialogEntity entity, String roomTableID, int postion) {
        DialogUtils.showDialog(context, "请稍后...");
        RetrofitHelper.getInstance().getApi().kaiTai(App.INSTANCE().getShopID(), "1", roomTableID,
                entity.getTitle(), entity.getPeopleNum(), entity.getPeopleNum(),
                App.INSTANCE().getUser().getId(),
                App.INSTANCE().getUser().getName()).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                            DialogUtils.hintDialog();
                            switch (stringModel.getCode()) {
                                case Config.REQUEST_SUCCESS:
                                    if (!TextUtils.isEmpty(stringModel.getResult())) {
                                        iView.success(b, postion, stringModel.getResult(), entity);
                                    }
                                    break;
                                case Config.REQUEST_FAILED:
                                    iView.error(stringModel.getMessage());
                                    break;
                                case Config.REQUEST_ERROR:
                                    iView.error(context.getString(R.string.string_request_error));
                                    break;
                            }
                        }, throwable -> {
                            DialogUtils.hintDialog();
                            iView.error("失败");
                        }
                );
    }

    public void toFoods() {
        AppDbHelper.INSTANCE().getMenuTypes(App.INSTANCE().getShopID())
                .compose(getFragmentLifecycleProvider().<List<MenuTypeEntity>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menuTypeEntities -> {

                    if (menuTypeEntities.size() > 0) {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


    void changeTable(int position, TableEntity entity, Order order) {
        DialogUtils.showDialog(context, "换桌中...");
        RetrofitHelper.getInstance().getApi()
                .changeTable("2", entity.getRoomTableID(), entity.getTableName(), order.getTableId(), order.getBillid())
                .compose(getFragmentLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
//                            返回值	0	操作失败
//                            返回值	2	桌位已经开台
//                            返回值	1	操作成功
                            switch (intModel.getResult()) {
                                case "0":
                                    iView.error("换桌失败" + intModel.getMessage());
                                    break;
                                case "1":
                                    iView.changeTableSuccess(position, entity.getTableName(), order.getTableId());
                                    break;
                            }
                            break;
                        case Config.REQUEST_FAILED:
                        case Config.REQUEST_ERROR:
                            iView.error(intModel.getMessage());
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.printStackTrace();
                    iView.error("换桌失败");
                });
    }

    void merge(TableEntity entity, Order order) {

        RetrofitHelper.getInstance().getApi().merge();

    }

    void trun(TableEntity entity, OrderDetailFood orderDetailFood, String cTableId) {

        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance().getApi().trun("2", App.INSTANCE().getShopID(), entity.getRoomTableID(), orderDetailFood.getDetailId())
                .compose(getFragmentLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("1")) {
                                iView.trunSuccess();
                            } else {
                                iView.error(intModel.getMessage());
                            }
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.error("转菜失败");
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.printStackTrace();
                    iView.error("转菜失败");
                });
    }

    void qingtai(int position, String billID, String roomTableID) {

        DialogUtils.showDialog(context, "清台中...");
        RetrofitHelper.getInstance().getApi().qingtai(App.INSTANCE().getShopID(), "3", roomTableID, billID, App.INSTANCE().getUser().getName(), App.INSTANCE().getUser().getId())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("1")) {
                                iView.qingtaiSuccess(position);
                            } else {
                                iView.warning("清台失败");
                            }
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error("清台失败");
                            break;
                        case Config.REQUEST_FAILED:
                            iView.error("清台失败");
                            break;
                    }
                }, throwable -> {
                    iView.error("清台失败");
                    DialogUtils.hintDialog();
                });
    }

    public void getOrder(TableEntity entity, int position) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance().getApi()
                .getOrder("9", entity.getRoomTableID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            String[] result = stringModel.getResult().split("∞");
                            Order order = new Gson().fromJson(result[0], Order.class);
                            OrderDetailFood[] detailFoods = new Gson().fromJson(result[1], OrderDetailFood[].class);
                            if (entity.getOpen().equals("4")) {
                                iView.showCancelDialog(order);
                            } else {
                                iView.orderSuccess(order, Arrays.asList(detailFoods), position);
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("获取订单失败");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.printStackTrace();
                    iView.error("获取订单失败");
                });
    }

    void undo(String tableId, String billid, int position, String tableName, String price) {
        DialogUtils.showDialog(context, "撤单中...");
        RetrofitHelper.getInstance()
                .getApi()
                .undo("4", tableId, billid, App.INSTANCE().getShopID(), price, tableName, App.INSTANCE().getUser().getName(), App.INSTANCE().getUser().getId())
                .compose(getFragmentLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("0")) {
                                iView.error(intModel.getMessage());
                            } else {
                                print(intModel.getResult());
                                iView.undoSuccess(position);
                            }
                            break;
                        case Config.REQUEST_ERROR:
                        case Config.REQUEST_FAILED:
                            iView.error(intModel.getMessage());
                            break;
                    }
                }, throwable -> {
                    iView.error("撤单失败");
                    DialogUtils.hintDialog();
                });
    }

    private void print(String result) {
        new Thread(() -> Print.socketDataArrivalHandler(result)).start();
    }

    public void KuaiSu(TableEntity entity, String foodinfo, String pdata, String ptime, String names, String address, String phone, String remark,
                       double monery, String tablid, String tablename, String types) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance()
                .getApi()
                .kuaiSu("0", "", App.INSTANCE().getShopID(), foodinfo, pdata, ptime, names, address,
                        phone, App.INSTANCE().getUser().getId(), App.INSTANCE().getUser().getName(), remark,
                        0, tablid, tablename, types, "", monery,"")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (stringModel.getResult().equals("0")) {
                                iView.warning("下单失败");
                            } else {
                                iView.kuaisuSuccess(entity, stringModel.getResult(), monery);
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("下单失败");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.printStackTrace();
                    iView.warning("下单失败");
                });

    }

    //    type	接口状态	4
//    OrderID	 订单ID
//    TableName	桌位名称
//    TableID	桌位ID
    public void bindTable(String orderId, String tableId, String tableName, String id, String name) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance()
                .getApi()
                .bindTable("4", orderId, tableId, id, "1", name, tableName)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (stringModel.getResult().equals("0")) {
                                iView.error("绑定失败");
                            } else {
                                iView.bindSuccess(tableId, tableName);
                                print(stringModel.getResult());
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.error(stringModel.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    iView.error("绑定失败");
                    DialogUtils.hintDialog();
                });
    }

    //    type	接口类型	12
//    id	店铺ID
//    TABLEID	桌位ID	多个逗号分隔
    public void getOrderList(String tableId, Order masterOrder) {
        DialogUtils.showDialog(context, "获取订单列表");
        RetrofitHelper.getInstance()
                .getApi()
                .getMergeOrderList("12", App.INSTANCE().getShopID(), tableId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            String[] result = stringModel.getResult().split("∞");
                            Order[] orders = new Gson().fromJson(result[0], Order[].class);
                            OrderDetailFood[] detailFoods = new Gson().fromJson(result[1], OrderDetailFood[].class);

                            Order order = new Order();
                            int tableWareCount = 0;
                            int peopleCount = 0;
                            String billId = "";
                            String oTableId = "";
                            String oTableName = "";
                            String id = "";
                            double price = 0;
                            for (int i = 0; i < orders.length; i++) {
                                Order o = orders[i];
                                tableWareCount += o.getTableWareCount();
                                peopleCount += o.getPeopleCount();
                                price += o.getPayPrice();
                                if (i == orders.length - 1) {
                                    id += o.getId();
                                    billId += o.getBillid();
                                    oTableId += o.getTableId();
                                    oTableName += o.getTableName();
                                } else {
                                    id += o.getId() + ",";
                                    billId += o.getBillid() + ",";
                                    oTableId += o.getTableId() + ",";
                                    oTableName += o.getTableName() + ",";
                                }

                            }
                            order.setId(id);
                            order.setTableWareCount(tableWareCount);
                            order.setPeopleCount(peopleCount);
                            order.setBillid(billId);
                            order.setTableId(oTableId);
                            order.setType(masterOrder.getType());
                            order.setPayPrice(price);
                            Timber.d("oTableName" + oTableName);
                            order.setTableName(oTableName);
                            iView.orderListSuccess(order, Arrays.asList(detailFoods));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("获取订单失败");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error("数据库查询失败");
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("获取订单失败");
                });
    }

    public void inBill(Order order, List<OrderDetailFood> orderDetailFoods, int position, boolean isScanBill) {
        RetrofitHelper.getInstance()
                .getApi()
                .inBill("18", order.getBillid())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        iView.inBillSuccess(order, orderDetailFoods, position ,isScanBill);
                    } else {
                        iView.error("修改状态失败");
                    }
                }, throwable -> {
                    iView.error("修改状态失败");
                });
    }

    public void cancelBill(String billId) {
        RetrofitHelper.getInstance()
                .getApi()
                .inBill("19", billId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        iView.cancelSuccess();
                    } else {
                        iView.error("修改状态失败");
                    }
                }, throwable -> {
                    iView.error("修改状态失败");
                });
    }

    public void getTableData(String tableId, int position) {
        RetrofitHelper.getInstance()
                .getApi()
                .getTableData("20", App.INSTANCE().getShopID(), tableId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        TableEntity[] entities = new Gson().fromJson(stringModel.getResult(), TableEntity[].class);
                        if (entities == null || entities.length == 0) {
                            iView.error("获取数据失败");
                        } else {
                            iView.showTable(entities[0], position);
                        }
                    } else {
                        iView.error("获取数据失败");
                    }
                }, throwable -> {
                    iView.error("获取数据失败");
                });
    }

    public void scanBill(String code, double price,double memberPrice, String billId) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance()
                .getApi()
                .scanBill("21", code, price, App.INSTANCE().getShopID(), billId)
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if(stringModel.getCode().equals("1")){
                        if (stringModel.getResult().contains("SUCCESS")) {
                            String parType[] = stringModel.getResult().split("&");
                            if(parType[1].equals("5")){
                                iView.scanBillSuccess(parType[1] ,billId , memberPrice ,"" ,"");
                            }else{
                                iView.scanBillSuccess(parType[1] ,billId , price ,"" ,"");
                            }
                        }else if(stringModel.getResult().contains("FAILED")){
                            iView.warning("支付失败");
                        }else if(stringModel.getResult().contains("UNKNOWN")){
                            iView.warning("支付错误");
                        }else if(stringModel.getResult().contains("USERPAYING")){
                            iView.scanBillSuccess("3" ,billId , price ,"" ,"支付中");
                        }else if(stringModel.getResult().contains("ORDERPAID")){
                            iView.warning("订单已支付");
                        }else if(stringModel.getResult().contains("AUTHCODEEXPIRE")){
                            iView.warning("二维码已过期");
                        }else if(stringModel.getResult().contains("NOTENOUGH")){
                            iView.warning("余额不足");
                        }else if(stringModel.getResult().contains("OUT_TRADE_NO_USED")){
                            iView.warning("订单号重复");
                        }else if(stringModel.getResult().contains("QITA")){
                            iView.warning("其他错误");
                        }else if(stringModel.getResult().contains("CODEUNKNOWN")){
                            iView.warning("二维码错误");
                        }else{
                            String parType[] = stringModel.getResult().split("&");
                            iView.scanBillSuccess(parType[1] ,billId , price ,parType[0],"");
                        }
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("支付失败");
                });
    }

    public void bill(String id, String Rid, String TableId, double zon, double can, String jsonObjquanxian,
                     String jsonObj, String jsonPay, String payType, int peoplecount, double price, String tablename, double free, String types , String memberID) {
        DialogUtils.showDialog(context, "结账中...");
        RetrofitHelper.getInstance()
                .getApi()
                .bill("3", id, Rid, memberID, TableId, zon, can, 0, 0, types, jsonObjquanxian, jsonObj, payType, jsonPay,
                        "", "", App.INSTANCE().getUser().getId(), App.INSTANCE().getUser().getName(), "", "", "", peoplecount,
                        price, tablename, free,0,0)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("0")) {
                                iView.warning("结账失败");
                            } else {
                                iView.billSuccess("结账成功", intModel.getResult());
                                printResult(intModel.getResult());
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("结账失败");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("结账失败");
                });
    }
    private void printResult(String result) {
        new Thread(() -> Print.socketDataArrivalHandler(result)).start();
    }
    public void getOrderData(String billId, String types) {
        RetrofitHelper.getInstance()
                .getApi()
                .getOrderData("17", App.INSTANCE().getShopID(), billId, types)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        WeixinOrderBean[] beans = new Gson().fromJson(stringModel.getResult(), WeixinOrderBean[].class);
                        iView.success(Arrays.asList(beans));
                    } else {
                        iView.fail();
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.printStackTrace();
                    iView.fail();
                });
    }
}
