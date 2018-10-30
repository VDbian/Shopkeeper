package com.admin.shopkeeper.ui.activity.config.id;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.utils.SPUtils;


/**
 * Created by guxiaogasumi on 2017/6/12.
 */

public class ConfigIDPresenter extends BasePresenter<IConfigIDView> {


    public ConfigIDPresenter(Context context, IConfigIDView iView) {
        super(context, iView);

    }

    public void getDB() {
        iView.show(SPUtils.getInstance().getString(SPUtils.PREFERENCE_SHOP_ID));
    }

    public void saveID(String id) {
        SPUtils.getInstance().put(SPUtils.PREFERENCE_SHOP_ID, id);
        iView.saveSuccess();
        App.INSTANCE().setShopID(id);

    }
}
