package com.admin.shopkeeper.ui.fragment.desk;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.User;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/29.
 */

class DeskPresenter extends BasePresenter<IDeskView> {
    DeskPresenter(Context context, IDeskView iView) {
        super(context, iView);
    }

    void getTabelDB() {
        AppDbHelper.INSTANCE().getRooms(App.INSTANCE().getShopID())
                .compose(getFragmentLifecycleProvider().<List<RoomEntity>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(roomEntities -> iView.showDB(roomEntities), Throwable::printStackTrace);
    }

}
