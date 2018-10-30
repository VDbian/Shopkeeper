package com.admin.shopkeeper.ui.fragment.more;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.Print;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/29.
 */

public class MorePresenter extends BasePresenter<IMoreView> {
    public MorePresenter(Context context, IMoreView iView) {
        super(context, iView);
    }

    public void jiaoBanPrint() {
//        type	接口类型	6
//        printsouce	是否本地打印	1是  2否
//        id	商家ID
//        Name	操作人员	 收银   或其他
//        price	金额
//        userid	操作人员ID
        DialogUtils.showDialog(context, "提交请求");
        RetrofitHelper.getInstance().getApi().jiaoBanPrint("6", "1", App.INSTANCE().getShopID(), App.INSTANCE().getUser().getName(), "",
                App.INSTANCE().getUser().getId())
                .compose(getFragmentLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    switch (stringModel.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            if (stringModel.getResult().equals("0")) {
                                iView.warning("获取打印数据失败");
                            } else {
                                print(stringModel.getResult());
                                iView.success("获取打印数据成功");
                                iView.toLogin();
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.warning("获取打印数据失败");
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getResources().getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("获取打印数据失败");
                });
    }

    private void print(String result) {
//        new Thread(() -> Print.socketDataArrivalHandler(result)).start();
        Print.rxPrint(result);
    }
}
