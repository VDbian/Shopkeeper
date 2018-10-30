package com.admin.shopkeeper.ui.activity.activityOfBoss.wxsetting;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.WechatBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.wechat.IWechatView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class WXSettingPresenter extends BasePresenter<IWXSettingView> {

    public WXSettingPresenter(Context context, IWXSettingView iView) {
        super(context, iView);
    }


    public void save(String personCenter, String weixinOrder, int jifenAdding, int jifenExchange, String yuding, String waimai, String kuaican, String tandian) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .saveWechat("2", personCenter, weixinOrder, jifenAdding, jifenExchange, yuding, waimai, kuaican, tandian, App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("保存成功");
                    } else {
                        iView.error("保存失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("保存失败");
                });
    }

    public void getData() {
        DialogUtils.showDialog(context, "数据获取中");
        RetrofitHelper.getInstance()
                .getApi()
                .getWechat("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        WechatBean bean = new Gson().fromJson(stringModel.getResult(), WechatBean.class);
                        iView.success(bean);
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }
}
