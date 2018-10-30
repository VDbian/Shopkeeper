package com.admin.shopkeeper.ui.activity.orderFood;

import android.content.Context;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.admin.shopkeeper.entity.PayType;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.Print;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/6/30.
 */

public class OrderFoodPresenter extends BasePresenter<IOrderFoodView> {
    public OrderFoodPresenter(Context context, IOrderFoodView iView) {
        super(context, iView);
    }

    public void getDBFood() {
        AppDbHelper
                .INSTANCE()
                .getMenuTypes(App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().<List<MenuTypeEntity>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(menuTypeEntities -> {
                    if (menuTypeEntities.size() > 0) {
                        iView.success(menuTypeEntities);
                    } else {
                        iView.warning("请前往配置页面获取菜单列表");
                    }
                }, Throwable::printStackTrace);
    }
    //foodType加菜传1
    public void orderFood(String id,
                          String tableid,
                          String billid,
                          String info,
                          String userID,
                          String name,
                          String tableName,
                          String personCount,
                          double price,
                          String foodType) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance()
                .getApi()
                .orderFood("6", id, tableid, billid, info, userID, name, tableName, price, foodType, personCount, "")
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    switch (intModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (intModel.getResult().equals("0")) {
                                iView.warning("下单失败");
                            } else {
                                Log.e("vd",intModel.getResult());
                                print(intModel.getResult());
                                iView.orderSuccess("下单成功");
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

    private void print(String result) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Print.socketDataArrivalHandler(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    public void KuaiSu(String foodinfo, String pdata, String ptime, String names, String address, String phone, String remark,
                       double monery, String tablid, String tablename, String types, boolean isquick, boolean isScan , boolean isEditTabName,
                       String billType) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance()
                .getApi()
                .kuaiSu("0", "", App.INSTANCE().getShopID(), foodinfo, pdata, ptime, names, address,
                        phone, App.INSTANCE().getUser().getId(), App.INSTANCE().getUser().getName(),
                        remark, 0, tablid, tablename, types, "", monery ,billType)
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
                                print(stringModel.getResult());
                                iView.kuaisuSuccess(stringModel.getResult(), monery, isquick, isScan ,isEditTabName);
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

    public void reBillKuaiSu(String foodinfo, String pdata, String ptime, String names, String address, String phone, String remark,
                             double monery, String tablid, String tablename, String billId, String types) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance().getApi()
                .reBillKuaiSu("0", "", App.INSTANCE().getShopID(), foodinfo, pdata, ptime, names, address,
                        phone, App.INSTANCE().getUser().getId(), App.INSTANCE().getUser().getName(),
                        remark, monery, tablid, tablename, billId, types)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            iView.kuaisuSuccess(stringModel.getResult(), monery, false, false , false);
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

    public void queryFoods(String text) {
        AppDbHelper.INSTANCE().queryFoods(text).compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foodEntities -> {
                    if (foodEntities.size() == 0) {
                        iView.warning("没有查询到相关菜品");
                    }
                    iView.querySuccess(foodEntities);
                }, Throwable::printStackTrace);
    }

//    public void getSeasonData(FoodEntity entity, OrderfoodEntity orderfoodEntity) {
//        DialogUtils.showDialog(context, "加载中...");
//        RetrofitHelper.getInstance().getApi()
//                .getSeason(entity.getRestaurantID(), "8", entity.getProductID())
//                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(stringModel -> {
//                    DialogUtils.hintDialog();
//                    switch (stringModel.getCode()) {
//                        case Config.REQUEST_SUCCESS:
//                            Season[] seasons = new Gson().fromJson(stringModel.getResult(), Season[].class);
//                            if (seasons != null && seasons.length > 0) {
//                                List<Season> seasonList = Arrays.asList(seasons);
//                                if (!TextUtils.isEmpty(orderfoodEntity.getSeasoned())) {
//                                    for (int i = 0; i < seasonList.size(); i++) {
//                                        if (orderfoodEntity.getSeasoned().contains(seasonList.get(i).getName())) {
//                                            seasonList.get(i).setSelected(true);
//                                        }
//                                    }
//                                }
//                                orderfoodEntity.setSeasons(seasonList);
//                            }
//                            iView.showFoodEditDialog(entity, orderfoodEntity);
//                            break;
//                        case Config.REQUEST_FAILED:
//                            iView.warning(stringModel.getMessage());
//                            iView.showFoodEditDialog(entity, orderfoodEntity);
//                            break;
//                        case Config.REQUEST_ERROR:
//                            iView.error(stringModel.getMessage());
//                            iView.showFoodEditDialog(entity, orderfoodEntity);
//                            break;
//                    }
//
//                }, throwable -> {
//                    DialogUtils.hintDialog();
//                    iView.showFoodEditDialog(entity, orderfoodEntity);
//                });
//    }

    public void pushAllFood(FoodEntity entity) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance().getApi()
                .pushAllFood(entity.getRestaurantID(), "3")
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            AppDbHelper.INSTANCE().pushAllFood();
                            getDBFood();
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
                });
    }

    public void pushFood(FoodEntity entity) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance().getApi()
                .pushFood(entity.getRestaurantID(), "1", entity.getProductID())
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            AppDbHelper.INSTANCE().pushFood(1, entity.getProductID());
                            //getDBFood();
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
                });
    }

    public void putFood(FoodEntity entity) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance().getApi()
                .putFood(entity.getRestaurantID(), "2", entity.getProductID())
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            AppDbHelper.INSTANCE().pushFood(2, entity.getProductID());
                            //getDBFood();
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
                });
    }

    public void payWay(String shopID, String result, double money) {
        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance().getApi()
                .getPay(shopID, "13")
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            PayType[] payTypes = new Gson().fromJson(stringModel.getResult(), PayType[].class);
                            if (payTypes != null && payTypes.length > 0) {
                                iView.bill(payTypes[0].getPayType(), result, money,"","");
                            } else {
                                iView.error("支付失败");
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
                });
    }

    public void rebillTangdian(String tableId, String billId, String info, String tableName, double price, String fanBill) {
        DialogUtils.showDialog(context, "数据提交中...");
        Log.e("vd","6"+"**"+ App.INSTANCE().getShopID()+"**"+tableId+"**"+ billId+"**"+
                info+"**"+App.INSTANCE().getUser().getId()+"**"+ App.INSTANCE().getUser().getName()+"**"+
                tableName+"**"+ price+"**"+ "0"+"**"+ fanBill);

        RetrofitHelper.getInstance()
                .getApi()
                .rebillTangdian("0", App.INSTANCE().getShopID(), tableId, "4",
                        info, App.INSTANCE().getUser().getId(), App.INSTANCE().getUser().getName(),
                        tableName, price, "0", fanBill)
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    Log.e("vd",stringModel.getResult());
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            iView.kuaisuSuccess(stringModel.getResult(), price, false ,false,false);
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
                    iView.warning("下单失败");
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
                    if(stringModel.getCode().equals("1")){
                        if (stringModel.getResult().contains("SUCCESS")) {
                            String parType[] = stringModel.getResult().split("&");
                            iView.bill(parType[1] ,billId , price ,"" ,"");
                        }else if(stringModel.getResult().contains("FAILED")){
                            iView.warning("支付失败");
                        }else if(stringModel.getResult().contains("UNKNOWN")){
                            iView.warning("支付错误");
                        }else if(stringModel.getResult().contains("USERPAYING")){
                            iView.bill("3" ,billId , price ,"" ,"支付中");
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
                            iView.bill(parType[1] ,billId , price ,parType[0],"");
                        }
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.warning("支付失败");
                });
    }
}
