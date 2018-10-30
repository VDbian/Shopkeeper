package com.admin.shopkeeper.ui.activity.info.yuding;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class YuDingPresenter extends BasePresenter<IYuDingView> {
    public YuDingPresenter(Context context, IYuDingView iView) {
        super(context, iView);
    }

    public void KuaiSu(String foodinfo, String pdata, String ptime, String names, String address, String phone, String remark,
                       double monery, String tablid, String tablename, String types, double price) {

        DialogUtils.showDialog(context, "数据提交中...");
        RetrofitHelper.getInstance().getApi().kuaiSu("0", "", App.INSTANCE().getShopID(), foodinfo, pdata, ptime, names, address,
                phone, App.INSTANCE().getUser().getId(), App.INSTANCE().getUser().getName(),
                remark, monery, tablid, tablename, types, "", price,"")
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
                                iView.kuaisuSuccess(stringModel.getResult());
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
}
