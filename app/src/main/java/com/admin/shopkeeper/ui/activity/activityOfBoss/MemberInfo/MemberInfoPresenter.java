package com.admin.shopkeeper.ui.activity.activityOfBoss.MemberInfo;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.MemberInfoOfItemBean;
import com.admin.shopkeeper.entity.OrderBussinessBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MemberInfoPresenter extends BasePresenter<IMemberInfoView> {

    public MemberInfoPresenter(Context context, IMemberInfoView iView) {
        super(context, iView);
    }

    public void getMemberInfoOfItem(int type ,String userID) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getMemberInfo(type+"", userID)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if(stringModel.getResult().equals("0")){
                            iView.success("没有任何记录");
                        }else{
                            MemberInfoOfItemBean[] m = new Gson().fromJson(stringModel.getResult(), MemberInfoOfItemBean[].class);
                            iView.success(Arrays.asList(m));
                        }
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
