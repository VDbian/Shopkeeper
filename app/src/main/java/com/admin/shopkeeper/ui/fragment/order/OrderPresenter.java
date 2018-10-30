package com.admin.shopkeeper.ui.fragment.order;

import android.content.Context;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.TPayType;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/29.
 */

public class OrderPresenter extends BasePresenter<IOrderView> {
    public OrderPresenter(Context context, IOrderView iView) {
        super(context, iView);
    }

    public void getOrderList(int status, int type, int index, int size) {
        String statusStr = "all";
        switch (status) {
            case 0:
                statusStr = "all";
                break;
            case 1:
                statusStr = "2";
                break;
            case 2:
                statusStr = "3";
                break;
        }
        Log.e("ttt",type+statusStr+index+size);

        RetrofitHelper.getInstance().getApi().getOrderList("0",
                App.INSTANCE().getShopID(),
                type == 0 ? "all" : String.valueOf(type),
                statusStr,
                index,
                size, "all")
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            Log.i("ttt", "---" + stringModel.getResult());
                            Order[] orders = new Gson().fromJson(stringModel.getResult(), Order[].class);
                            List<Order> test = new ArrayList<Order>();
                            test.addAll(Arrays.asList(orders));
                            iView.success(test);
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning(stringModel.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(stringModel.getMessage());
                            break;
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                    iView.error("获取订单列表失败");
                });
    }

    public void getOrderDetail(Order item, int position) {
        RetrofitHelper.getInstance().getApi().getOrderDetail(App.INSTANCE().getShopID(), "9", item.getBillid())
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            String[] result = stringModel.getResult().split("\\^");
                            if (result.length > 1) {
                                OrderDetailFood[] detailFoods = new Gson().fromJson(result[0], OrderDetailFood[].class);
                                TPayType[] tPayTypes = new Gson().fromJson(result[1], TPayType[].class);
                                iView.toDetail(item, Arrays.asList(detailFoods), Arrays.asList(tPayTypes), position);
                            } else {
                                OrderDetailFood[] detailFoods = new Gson().fromJson(result[0], OrderDetailFood[].class);
                                iView.toDetail(item, Arrays.asList(detailFoods), new ArrayList<TPayType>(), position);
                            }

//                            String[] result = stringModel.getResult().split("\\^");
//                            OrderDetailFood[] detailFoods = new Gson().fromJson(result[0], OrderDetailFood[].class);
//                            TPayType[] tPayTypes = new Gson().fromJson(result[1],TPayType[].class);
//                            iView.toDetail(item, Arrays.asList(detailFoods), Arrays.asList(tPayTypes), position);
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning(stringModel.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(stringModel.getMessage());
                            break;
                    }

                }, throwable -> {
                    iView.warning("获取订单详情失败");
                });
    }
}
