package com.admin.shopkeeper.ui.activity.rechargedetail;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.CardBean;
import com.admin.shopkeeper.entity.MemberBean;
import com.admin.shopkeeper.entity.RechargeBean;
import com.admin.shopkeeper.entity.RechargeItemBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.rechargeedit.IRechargeEditView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.Print;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class RechargeDetailPresenter extends BasePresenter<IRechargeDetailView> {


    public RechargeDetailPresenter(Context context, IRechargeDetailView iView) {
        super(context, iView);
    }

    public void searchMember(String idStr) {
        DialogUtils.showDialog(context, "获取数据中");
        RetrofitHelper.getInstance()
                .getApi()
                .getMemberDetail("15", "", App.INSTANCE().getShopID(), idStr)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("查询失败");
                        } else {
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
                            iView.saleSuccess(memberBean);
                        }
                    } else {
                        iView.error("查询失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("查询失败");
                });
    }

    public void getProduct() {
        RetrofitHelper.getInstance()
                .getApi()
                .getRecharge("6", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        RechargeItemBean[] beens = new Gson().fromJson(stringModel.getResult(), RechargeItemBean[].class);
                        iView.productSuccess(Arrays.asList(beens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void moneyCommit(String userId, String price , int payType) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .moneyCharge("9", userId, App.INSTANCE().getShopID(), price , payType ,App.INSTANCE().getUser().getName(),App.INSTANCE().getUser().getId())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        Log.d("ttt",stringModel.getResult());
                        printResult(stringModel.getResult());
                        iView.success("充值成功");
                    } else {
                        iView.error("充值失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("充值失败");
                });
    }

    public void productCommit(String userId, String cardId ,int payType) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .productCharge("7", userId, App.INSTANCE().getShopID(), cardId , payType , App.INSTANCE().getUser().getName(),App.INSTANCE().getUser().getId())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intModel -> {
                    DialogUtils.hintDialog();
                    if (intModel.getCode().equals("1")) {
                        Log.d("ttt",intModel.getResult());
                        printResult(intModel.getResult());
                        iView.success("充值成功");
                    } else {
                        iView.error("充值失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("充值失败");
                });
    }

    private void printResult(String result) {
        new Thread(() -> Print.socketDataArrivalHandler(result)).start();
//        Print.rxPrint(result);
    }

    public void check(String checkCode, int type, RechargeBean bean) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .checkCode("10", App.INSTANCE().getShopID(), checkCode)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getResult().equals("1")) {
                        iView.checkSuccess(type , bean);
                    } else {
                        iView.error("校验码错误");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("校验失败");
                });
    }
}
