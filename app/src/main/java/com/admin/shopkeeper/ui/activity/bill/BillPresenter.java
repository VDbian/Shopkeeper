package com.admin.shopkeeper.ui.activity.bill;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.CanJu;
import com.admin.shopkeeper.entity.CardBean;
import com.admin.shopkeeper.entity.CouponLineDownBean;
import com.admin.shopkeeper.entity.DaZheEntity;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.admin.shopkeeper.entity.WeixinOrderBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.Print;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

class BillPresenter extends BasePresenter<IBillView> {

    BillPresenter(Context context, IBillView iView) {
        super(context, iView);
    }

    void getDazhe(String billid, int dazhe, String daId) {
        DialogUtils.showDialog(context, "获取数据中...");
        RetrofitHelper.getInstance()
                .getApi()
                .getDazhe("24", billid, App.INSTANCE().getShopID(), "0", dazhe, daId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("0")) {
                                iView.warning("存在无法打折菜品");
                            } else
                                iView.dazheSucccess(Double.parseDouble(intModel.getResult()));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.error("获取打折优惠价格错误");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                    Timber.d(intModel.toString());
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("获取折后价格错误");
                });
    }

    void getOtherYouhui(String couponId, String billid, double couponPice, double YingFu, String json) {
        DialogUtils.showDialog(context, "获取数据中...");
        RetrofitHelper.getInstance()
                .getApi()
                .getOherYouhui("23", couponId, billid, App.INSTANCE().getShopID(), couponPice, YingFu, json)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("0")) {
                                iView.warning("获取其他优惠价格错误");
                            } else
                                iView.otherYouhuiSucccess(Double.parseDouble(intModel.getResult()));
                            break;
                        case Config.REQUEST_FAILED:
                            iView.error("获取其他优惠价格错误");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                    Timber.d(intModel.toString());
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("获取其他价格错误");
                });
    }

    //    Type	类型	3
//    id	账单主键
//    Rid	商家主键
//    MenberID	会员主键	“”
//    TableId	桌位主键
//    zon	总金额
//    can	餐具费
//    pei	配送费	0
//    dabao	打包费	0
//    types	类别	7 1.预定菜品2预定桌位3外卖4快餐5扫码点餐6排队点餐7店内点餐
//    jsonObjquanxian	权限促销
//    jsonObj	积分或优惠券促销
//    PayType	支付类型	1
//    jsonPay	组合支付
//    GuaID	挂账人主键	“”
//    PersonMonery	会员余额	“”
//    Changeid	结账人
//    Username	结账人
//    zonweight	总重量	“”
//    zonprice	总价格	“”
//    zonstate	总状态	“”
    public void bill(String id, String Rid, String tableId,
                     String memberId, double zon, double can, String jsonObjquanxian,
                     String jsonObj, String jsonPay, int peoplecount, double price, String tablename, double free,
                     String types, String guiId, String payType, double maling, double rounding) {
        DialogUtils.showDialog(context, "结账中...");
        RetrofitHelper.getInstance().getApi()
                .bill("3", id, Rid, memberId, tableId, zon, can, 0, 0, types, jsonObjquanxian, jsonObj, payType, jsonPay,
                        guiId, "", App.INSTANCE().getUser().getId(), App.INSTANCE().getUser().getName(), "", "", "",
                        peoplecount, price, tablename, maling, rounding, free)
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

    public void rebill(String id, String Rid, String TableId, String memberId, double zon, double can, String jsonObjquanxian,
                       String jsonObj, String jsonPay, String types, double free, String fanBill, double price, double maling, double rounding) {
        DialogUtils.showDialog(context, "结账中...");
        RetrofitHelper.getInstance().getApi()
                .reBill("3", id, Rid, memberId, TableId, zon, can, 0, 0, types, jsonObjquanxian, jsonObj, "4", jsonPay,
                        "", "", App.INSTANCE().getUser().getId(), App.INSTANCE().getUser().getName(), "", "", "", free, price, maling, rounding, fanBill)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    Log.i("ttt", "---" + intModel.toString());
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("0")) {
                                iView.warning("反结账失败");
                            } else {
                                iView.billSuccess("反结账成功", intModel.getResult());
                                printResult(intModel.getResult());
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("反结账失败");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    Log.i("ttt", "---" + throwable.getMessage());
                    throwable.printStackTrace();
                    DialogUtils.hintDialog();
                    iView.warning("反结账失败");
                });
    }

    private void printResult(String result) {
        new Thread(() -> Print.socketDataArrivalHandler(result)).start();
    }

    //    type	接口类型	3账单/5并单
//    id	商家ID
//    printsouce	是否本地打印	1
//    Sate	状态	7堂点 3外卖  4快餐
//    	订单ID	  并单以,号分割
//    Name	操作人员名称
//    personcount	人员数量
//    tableid	桌位id
//    tablename	桌位名称
//    priceold	总金额
//    price	实收金额
//    free	优惠
//    PayType	支付状态
//    public void print(String billid, int personcount, String tableid,
//                      String tablename, double priceold, double price, double free) {
//        RetrofitHelper.getInstance().getApi().print("3", App.INSTANCE().getShopID(), "1", "7", billid, App.INSTANCE().getUser().getName(),
//                personcount, tableid, tablename, priceold, price, free, "", "1")
//                .compose(getActivityLifecycleProvider().bindToLifecycle())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(stringModel -> {
//
//                    Timber.d(stringModel.toString());
//                }, throwable -> {
//
//                });
//    }

    void getCanJu() {
        DialogUtils.showDialog(context, "获取数据中...");
        RetrofitHelper.getInstance().getApi().getCanJu("6", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (stringModel.getResult().equals("0")) {
                                iView.canJuFei(0);
                            } else {
                                CanJu canJu = new Gson().fromJson(stringModel.getResult(), CanJu.class);
                                iView.canJuFei(canJu.getPrice());
                            }
                            break;
                        case Config.REQUEST_FAILED:
                        case Config.REQUEST_ERROR:
                            iView.error("获取餐具费失败");
                            break;
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("获取餐具费失败");
                });

    }

    //    type	接口类型	9
//    id	店铺ID
//    OrderID	账单ID
//    Types	微信交易编码
//    Price	交易金额
    public void weixinbill(String orderId, String weixinResult, double price) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance().getApi()
                .weixinBill("9", App.INSTANCE().getShopID(), orderId, weixinResult, (int) (price * 100))
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StringModel>() {
                    @Override
                    public void accept(StringModel stringModel) throws Exception {
                        if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                            JSONObject jsonObject = new JSONObject(stringModel.getResult());
                            if (jsonObject.optString("result_code").equals("SUCCESS")) {
                                iView.weixinSuccess();
                                DialogUtils.hintDialog();
                            } else if (jsonObject.optString("result_code").equals("FAIL")) {
                                iView.error(jsonObject.optString("err_code_des"));
                                if (jsonObject.optString("err_code").equals("USERPAYING")) {//用户输入密码
                                    queryOrder(orderId);

                                } else {
                                    DialogUtils.hintDialog();
                                }
                            } else {
                                DialogUtils.hintDialog();
                                iView.error("微信扫描结算失败");
                            }
                        } else {
                            iView.error("微信扫描结算失败");
                            DialogUtils.hintDialog();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        DialogUtils.hintDialog();
                        iView.error("微信扫描结算失败");
                    }
                });
    }

    public void queryOrder(String orderId) {
        Timber.d("dddddddddddddddddddddddddddd");
        RetrofitHelper.getInstance().getApi().query("10", App.INSTANCE().getShopID(), orderId)

                .delay(5, TimeUnit.SECONDS).compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {

                    if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        JSONObject jsonObject = new JSONObject(stringModel.getResult());
                        String result_code = jsonObject.optString("result_code");
                        String return_code = jsonObject.optString("return_code ");
                        String trade_state = jsonObject.optString("trade_state ");
                        if (result_code.equals("SUCCESS") && return_code.equals("SUCCESS")) {
                            switch (trade_state) {
                                case "SUCCESS":
                                    DialogUtils.hintDialog();
                                    iView.weixinSuccess();
                                    break;
                                case "REFUND":
                                    iView.warning("转入退款");
                                    break;
                                case "NOTPAY":
                                    iView.warning("未支付");
                                    break;
                                case "CLOSED":
                                    iView.warning("已关闭");
                                    break;
                                case "REVOKED":
                                    iView.warning("已撤销");
                                    break;
                                case "USERPAYING":
                                    queryOrder(orderId);
                                    break;
                                case "PAYERROR":
                                    iView.warning("支付失败");
                                    break;
                            }
                        } else if (jsonObject.optString("result_code").equals("FAIL")) {
                            DialogUtils.hintDialog();
                            if (jsonObject.optString("err_code").equals("ORDERNOTEXIST")) {
                                iView.warning("订单不存在");
                            } else {
                                iView.warning("查询订单失败");
                            }
                        }

                    } else {
                        DialogUtils.hintDialog();
                        iView.warning("查询订单失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("查询订单失败");
                });
    }

    public void getimage(int p, PayMeEntity e) {
        DialogUtils.showDialog(context);
        RetrofitHelper.getInstance().getApi().getImage("11", App.INSTANCE().getShopID()).compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StringModel>() {
                    @Override
                    public void accept(StringModel stringModel) throws Exception {
                        DialogUtils.hintDialog();
                        switch (stringModel.getCode()) {
                            case Config.REQUEST_SUCCESS:
                                iView.getImageSuccess(p, stringModel.getResult(), e);
                                break;
                            case Config.REQUEST_FAILED:
                            case Config.REQUEST_ERROR:
                                iView.error("获取二维码图片失败");
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        DialogUtils.hintDialog();
                        throwable.printStackTrace();
                        iView.error("获取二维码图片失败");
                    }
                });
    }

    public void getDazheList() {
        DialogUtils.showDialog(context, "获取数据中");
        RetrofitHelper.getInstance()
                .getApi()
                .getDazheList(App.INSTANCE().getShopID(), "12")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals(Config.REQUEST_SUCCESS)) {
                        DaZheEntity[] daZheEntities = new Gson().fromJson(stringModel.getResult(), DaZheEntity[].class);
                        iView.showDazhe(Arrays.asList(daZheEntities));
                    } else {
                        iView.showDazhe(new ArrayList<DaZheEntity>());
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.showDazhe(new ArrayList<DaZheEntity>());
                });
    }

    public void getFoodDetail(String billId) {
        DialogUtils.showDialog(context, "获取数据中");
        RetrofitHelper.getInstance()
                .getApi()
                .getOrderDetail(App.INSTANCE().getShopID(), "15", billId)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        String[] split = stringModel.getResult().split("∞");
                        if (split.length > 1) {
                            Order[] order = new Gson().fromJson(split[0], Order[].class);
                            OrderDetailFood[] detailFoods = new Gson().fromJson(split[1], OrderDetailFood[].class);
                            iView.showDetail(order.length > 0 ? order[0] : null, Arrays.asList(detailFoods));
                        } else {
                            Order[] order = new Gson().fromJson(split[0], Order[].class);
                            iView.showDetail(order.length > 0 ? order[0] : null, new ArrayList<OrderDetailFood>());
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    DialogUtils.hintDialog();
                });
    }

    public void guazhang(PayMeEntity entity) {
        DialogUtils.showDialog(context, "获取数据中");
        RetrofitHelper.getInstance()
                .getApi()
                .getGuazhangData(App.INSTANCE().getShopID(), "17")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("挂账失败");
                        } else {
                            GuaZhangBean[] guaZhangBeen = new Gson().fromJson(stringModel.getResult(), GuaZhangBean[].class);
                            iView.guazhangSuccess(Arrays.asList(guaZhangBeen), entity);
                        }

                    } else {
                        iView.error("挂账失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("挂账失败");
                });
    }

    public void searchMember(String orderId, String idStr) {
        DialogUtils.showDialog(context, "获取数据中");
        RetrofitHelper.getInstance()
                .getApi()
                .searchMember("15", orderId, App.INSTANCE().getShopID(), idStr)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("查询失败");
                        } else {
                            //王月@17778063182@170912111453435214@471@485.00@947230e5-a8dc-4c22-8e31-215b33a375c8/@@@@^@@@@^@@@@^@@@@^@@@@^@@@@
                            String[] split = stringModel.getResult().split("/");
                            String[] memberStrs = split[0].split("@");
                            MemberBean memberBean = new MemberBean();
                            memberBean.setNo(memberStrs[2]);
                            memberBean.setName(memberStrs[0]);
                            memberBean.setPhone(memberStrs[1]);
                            memberBean.setScore(Integer.parseInt(memberStrs[3]));
                            memberBean.setMoney(Double.parseDouble(memberStrs[4]));
                            memberBean.setId(memberStrs[5]);
                            memberBean.setRate(Double.parseDouble(memberStrs[6]));

                            //笑笑@满30送5块@5.00@908fe556-e5b7-4446-ac26-0848c991061c@1^
                            List<CardBean> cardBeanList = new ArrayList<CardBean>();
                            if (split.length >= 2 && !TextUtils.isEmpty(split[1])) {
                                String[] strings = split[1].split("\\^");
                                for (String str : strings) {
                                    String[] cardStr = str.split("@");
                                    CardBean cardBean = new CardBean();
                                    cardBean.setId(cardStr[3]);
                                    cardBean.setMoney(Double.parseDouble(cardStr[2]));
                                    cardBean.setType(cardStr[4]);
                                    cardBean.setName(cardStr[1]);
                                    cardBean.setUsername(cardStr[0]);
                                    cardBeanList.add(cardBean);
                                }
                            }
                            iView.saleSuccess(memberBean, cardBeanList);
                        }
                    } else {
                        iView.error("查询失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("查询失败");
                });
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
                            iView.success("打印成功");
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

    private void print(String result) {
        new Thread(() -> Print.socketDataArrivalHandler(result)).start();
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

    public void scanBill(String code, double price, String billId) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance()
                .getApi()
                .scanBill("21", code, price, App.INSTANCE().getShopID(), billId)
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().contains("SUCCESS")) {
                            String parType[] = stringModel.getResult().split("&");
                            iView.scanBillSuccess(parType[1], billId, price, "", "");
                        } else if (stringModel.getResult().contains("FAILED")) {
                            iView.warning("支付失败");
                        } else if (stringModel.getResult().contains("UNKNOWN")) {
                            iView.warning("支付错误");
                        } else if (stringModel.getResult().contains("USERPAYING")) {
                            iView.scanBillSuccess("3", billId, price, "", "用户正在支付中");
                        } else if (stringModel.getResult().contains("ORDERPAID")) {
                            iView.warning("订单已支付");
                        } else if (stringModel.getResult().contains("AUTHCODEEXPIRE")) {
                            iView.warning("二维码已过期");
                        } else if (stringModel.getResult().contains("NOTENOUGH")) {
                            iView.warning("余额不足");
                        } else if (stringModel.getResult().contains("OUT_TRADE_NO_USED")) {
                            iView.warning("订单号重复");
                        } else if (stringModel.getResult().contains("QITA")) {
                            iView.warning("其他错误");
                        } else if (stringModel.getResult().contains("CODEUNKNOWN")) {
                            iView.warning("二维码错误");
                        } else {
                            String parType[] = stringModel.getResult().split("&");
                            iView.scanBillSuccess(parType[1], billId, price, parType[0], "");
                        }
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("支付失败");
                });
    }

    public void getLineDownInfo(int index, String productName) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getLineDownInfo("5", App.INSTANCE().getShopID(), 20, index, "")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        CouponLineDownBean[] couponLineDownBeen = new Gson().fromJson(stringModel.getResult(), CouponLineDownBean[].class);
                        iView.successOfGetCouponLine(Arrays.asList(couponLineDownBeen));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
