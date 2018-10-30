package com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangdetail;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.GuazhangDetailBean;
import com.admin.shopkeeper.entity.SeasonBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.guazhangedit.IGuaZhangEditView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GuaZhangDetailPresenter extends BasePresenter<IGuaZhangDetailView> {

    public GuaZhangDetailPresenter(Context context, IGuaZhangDetailView iView) {
        super(context, iView);
    }


    public void getData(GuaZhangBean bean) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance()
                .getApi()
                .getGuazhangDetail("5", bean.getGuid(), App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if(!stringModel.getResult().equals("0")){
                            GuazhangDetailBean[] printBeens = new Gson().fromJson(stringModel.getResult(), GuazhangDetailBean[].class);
                            iView.success(Arrays.asList(printBeens));
                        }else {
                            iView.success(new ArrayList<GuazhangDetailBean>());
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
    public void jieZhang(GuazhangDetailBean bean) {
        DialogUtils.showDialog(context, "加载中...");
        RetrofitHelper.getInstance()
                .getApi()
                .guazhangDetailOfBill("6", bean.getBillId())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("结账成功");
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
