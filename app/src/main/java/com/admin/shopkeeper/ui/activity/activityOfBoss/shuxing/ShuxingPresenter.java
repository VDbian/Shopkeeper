package com.admin.shopkeeper.ui.activity.activityOfBoss.shuxing;

import android.content.Context;

import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.KouweiBean;
import com.admin.shopkeeper.entity.ShuxingBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.kouwei.IKouweiView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ShuxingPresenter extends BasePresenter<IShuxingView> {

    public ShuxingPresenter(Context context, IShuxingView iView) {
        super(context, iView);
    }

    public void getData(FoodBean bean) {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getShuxing("1", bean.getProductId())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (!stringModel.getResult().equals("0")) {
                            ShuxingBean[] printBeens = new Gson().fromJson(stringModel.getResult(), ShuxingBean[].class);
                            iView.success(Arrays.asList(printBeens));
                        } else {
                            iView.success(new ArrayList<ShuxingBean>());
                        }
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });

    }

    public void delete(FoodBean foodBean, ShuxingBean bean) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .deleteShuxing("3", bean.getGuid())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("删除成功");
                        getData(foodBean);
                    } else {
                        iView.error("删除失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("删除失败");
                });

    }
}
