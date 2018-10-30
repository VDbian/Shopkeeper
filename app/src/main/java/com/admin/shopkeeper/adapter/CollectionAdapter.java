package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.admin.shopkeeper.entity.ShopCollectionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class CollectionAdapter extends BaseQuickAdapter<ShopCollectionBean, BaseViewHolder> {
    public CollectionAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCollectionBean item) {
        helper.setText(R.id.item_name, item.getNames());
        helper.setText(R.id.item_date, item.getDinnerDate());
        helper.setText(R.id.item_sale, String.valueOf(item.getTotalMoney()));
        helper.setText(R.id.item_charge, String.valueOf(item.getChongzhi()));
        helper.setText(R.id.item_free, String.valueOf(item.getFreeMoney()));
        helper.setText(R.id.item_real, String.valueOf(item.getChargeMoney()));
    }
}
