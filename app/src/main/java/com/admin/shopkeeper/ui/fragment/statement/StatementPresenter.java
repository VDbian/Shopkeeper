package com.admin.shopkeeper.ui.fragment.statement;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


class StatementPresenter extends BasePresenter<IStatementView> {

    StatementPresenter(Context context, IStatementView iView) {
        super(context, iView);
    }

    public void getChain() {
        RetrofitHelper.getInstance()
                .getApi()
                .getChain("15", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        ChainBean[] beens = new Gson().fromJson(stringModel.getResult(), ChainBean[].class);
                        iView.chainsuccess(Arrays.asList(beens));
                    }
                }, throwable -> {
                });
    }
}
