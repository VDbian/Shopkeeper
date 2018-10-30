package com.admin.shopkeeper.ui.fragment.market;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.ui.fragment.desk.IDeskView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/29.
 */

class MarketPresenter extends BasePresenter<IMarketView> {


    MarketPresenter(Context context, IMarketView iView) {
        super(context, iView);
    }

}
