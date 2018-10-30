package com.admin.shopkeeper.ui.activity.orderDetail;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.CommonDialogEntity;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.RetreatReason;
import com.admin.shopkeeper.entity.TPayType;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.Print;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by zeng on 2017/4/23.
 */

class OrderDetailPresenter extends BasePresenter<IOrderDetailView> {
    OrderDetailPresenter(Context context, IOrderDetailView iView) {
        super(context, iView);
    }


//    Type	类型
//    DETAILID	退菜明细ID
//    Id	店铺ID
//    billid	订单ID
//    tableid	桌位ID
//    Name	操作人员
//    TableName	桌位名称
//    count	数量
//    pice	价格
//    tuicount	退菜数量
//    zencount	重量
//    zidinyi	自定义原因
//    dirtoatal	退菜原因ID
//    toasttoatal	原因名称

    void retreatFood(String orderID, String id, OrderDetailFood p, String tableid, String tableName, int count, double pice, double tuicount
            , double zoncount, String zidinyi, String dirtoatal, String toasttoatal) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance()
                .getApi()
                .retreatFood(App.INSTANCE().getShopID(), "3", id, orderID, tableid,
                        App.INSTANCE().getUser().getName(), tableName, count, pice, tuicount, zoncount, zidinyi, dirtoatal, toasttoatal)
                .compose(getFragmentLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("0")) {
                                iView.warning(intModel.getMessage());
                            } else {
                                print(intModel.getResult());
                                iView.retreatFoodSuccess(p);
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning(intModel.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("退菜失败");
                });
    }

    private void print(String result) {
        new Thread(() -> Print.socketDataArrivalHandler(result)).start();
    }

    void undo(String tableId, String billid, String tableName, String price) {
        DialogUtils.showDialog(context, "数据提交中...");
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
                                iView.undoSuccess();
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

    void getOrderDetail(Order item) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance().getApi().getOrderDetail(App.INSTANCE().getShopID(), "9", item.getBillid())
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            String[] result = stringModel.getResult().split("\\^");
                            if (result.length > 1) {
                                OrderDetailFood[] detailFoods = new Gson().fromJson(result[0], OrderDetailFood[].class);
                                TPayType[] tPayTypes = new Gson().fromJson(result[1], TPayType[].class);
                                iView.toDetail(Arrays.asList(detailFoods), Arrays.asList(tPayTypes));
                            } else {
                                OrderDetailFood[] detailFoods = new Gson().fromJson(result[0], OrderDetailFood[].class);
                                iView.toDetail(Arrays.asList(detailFoods), new ArrayList<TPayType>());
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning(stringModel.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(stringModel.getMessage());
                            break;
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.fillInStackTrace();
                    iView.warning("获取订单详情失败");
                });
    }

    public void peopleNum(Order order, CommonDialogEntity entity) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance().getApi()
                .peopleNum(App.INSTANCE().getShopID(), "2", order.getTableId(), entity.getPeopleNum(), entity.getCanJuNum(), order.getBillid())

                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            iView.peopleNumSuccess(entity.getPeopleNum(), entity.getCanJuNum());
                            break;
                        case Config.REQUEST_FAILED:
                        case Config.REQUEST_ERROR:
                            iView.error(intModel.getMessage());
                            break;

                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("修改人数失败");
                });
    }


    //    Type	类型	2
//    DETAILID	订单详情ID
//    billid	开台ID
//    id	店铺ID
//    tableid	桌位ID
//    Name	操作人
//    TableName	桌台名称
    public void cuicai(String detailId, String billid, String tableid, String tableName) {
        DialogUtils.showDialog(context, "正在催菜");
        RetrofitHelper.getInstance().getApi().cuicai("2", detailId, billid, App.INSTANCE().getShopID(), tableid, App.INSTANCE().getUser().getName()
                , tableName).compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (stringModel.getResult().equals("0")) {
                                iView.warning(stringModel.getMessage());
                            } else {
                                print(stringModel.getResult());
                                iView.cuicaiSuccess();
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning(stringModel.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("催菜失败");
                });
    }

    //    Type	1	1
//    id	店铺ID
//    billid	订单ID
//    tableid	桌位ID
//    TableName	桌位名称
//    State	状态	 2
//    printsouce	是否本地打印	1是  2否
    public void hurry(String billId, String tableid, String tableName) {
        DialogUtils.showDialog(context, "正在催单");
        RetrofitHelper.getInstance().getApi().hurry("1", "1", App.INSTANCE().getShopID(), billId, tableid, tableName, "2", "1", "2")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).
                subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (stringModel.getResult().equals("0")) {
                                iView.warning("催单失败");
                            } else {
                                print(stringModel.getResult());
                                iView.cuicaiSuccess();
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("催单失败");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.warning(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("催单失败");
                });

    }

    //    Type	接口类型	2
//    ID	店铺ID
//    OrderID	 订单ID
    public void cancel(String orderId) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance().getApi().cancel("2", App.INSTANCE().getShopID(), orderId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:

                            if (stringModel.getResult().equals("0")) {
                                iView.warning("取消订单失败");
                            } else {
                                iView.cancelSuccess();
                            }
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("取消订单失败");
                            break;
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("取消订单失败");
                });

    }


    //    Type	接口类型	1
//    Id	店铺ID
//    OrderId	订单ID
//    BILLID	账单ID
//    Types	类别	3快餐   4外卖
    public void confir(String orderId, String billId, String type) {
        DialogUtils.showDialog(context, "确认中");
        RetrofitHelper.getInstance().getApi().confirm("1", App.INSTANCE().getShopID(), orderId, billId, type)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (stringModel.getResult().equals("0")) {
                                iView.warning("确认订单失败");
                            } else {
                                print(stringModel.getResult());
                                iView.confirmSuccess();
                            }
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("确认订单失败");
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("确认订单失败");
                });
    }

    //    Type	接口状态	7
//    ID	店铺ID
//    OrderId	账单ID
    public void ofinis(String orderId) {

        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance().getApi().oFinish("7", App.INSTANCE().getShopID(), orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (stringModel.getResult().equals("0")) {
                                iView.warning("完成订单失败");
                            } else {
                                iView.finishSuccess();
                            }
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("完成订单失败");
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("完成订单失败");
                });

    }

    public void printAfter(String billid, String tableId, String tableName, int personcount) {
        DialogUtils.showDialog(context, "请求中");
        RetrofitHelper.getInstance().getApi()
                .printAfter("1", "1", App.INSTANCE().getShopID(), billid, tableId, tableName, personcount, "6", "0", App.INSTANCE().getUser().getName())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    Timber.d(stringModel.getResult());
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        print(stringModel.getResult());
                    } else {
                        iView.warning("后厨打印失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("后厨打印失败");
                });
    }

    public void getReason(OrderDetailFood orderDetailFood) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance().getApi().getReason(App.INSTANCE().getShopID(), "7").compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<StringModel>() {
                    @Override
                    public void accept(StringModel stringModel) throws Exception {
                        Timber.d(stringModel + "");
                        DialogUtils.hintDialog();
                        if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                            RetreatReason[] reason = new Gson().fromJson(stringModel.getResult(), RetreatReason[].class);
                            iView.showReason(new ArrayList<>(Arrays.asList(reason)), orderDetailFood);
                        } else {
                            iView.warning("获取退菜原因失败");
                        }
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("获取退菜原因失败");
                });
    }

    public void givingFood(OrderDetailFood entity) {
        DialogUtils.showDialog(context, "请求中");
        RetrofitHelper.getInstance().getApi()
                .kaiDan("14", entity.getDetailId(), entity.getGiving())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    Timber.d(stringModel.getResult());
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        iView.givingSuccess();
                        print(stringModel.getResult());
                    } else {
                        iView.warning("赠送失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("赠送失败");
                });
    }

    public void print(String billid, int personcount, String tableid, String tablename, double priceold, double price, double free, String state) {
        RetrofitHelper.getInstance()
                .getApi()
                .print("3", App.INSTANCE().getShopID(), "1", state, billid, App.INSTANCE().getUser().getName(),
                        personcount, tableid, tablename, priceold, price, free, "1")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    Timber.d(stringModel.toString());
                    if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        if (stringModel.getResult().equals("0")) {
                            iView.warning("打印失败");
                        } else {
                            iView.message("打印成功");
                            print(stringModel.getResult());
                        }
                    } else {
                        iView.warning("打印失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("打印失败");
                });
    }

    public void getOrder(String roomTableID) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance().getApi()
                .getOrder("9", roomTableID)
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
                            iView.orderSuccess(order, Arrays.asList(detailFoods));
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
}
