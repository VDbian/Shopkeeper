package com.admin.shopkeeper.ui.activity.activityOfBoss.addMemberLevel;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.utils.DialogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class AddMemberLevelPresenter extends BasePresenter<IAddMemberLevelView> {

    public AddMemberLevelPresenter(Context context, IAddMemberLevelView iView) {
        super(context, iView);
    }

    public void submit(String id, String name, String maxStr , String minStr) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .editMemberLevel(TextUtils.isEmpty(id) ? "3" : "4", id, name, maxStr , minStr , App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if(!TextUtils.isEmpty(id)){
                            iView.success("修改成功");
                        }else{
                            iView.success("添加成功");
                        }

                    } else {
                        if(!TextUtils.isEmpty(id)){
                            iView.error("修改失败");
                        }else{
                            iView.error("添加失败");
                        }
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    if(!TextUtils.isEmpty(id)){
                        iView.error("修改失败");
                    }else{
                        iView.error("添加失败");
                    }
                });
    }
}
