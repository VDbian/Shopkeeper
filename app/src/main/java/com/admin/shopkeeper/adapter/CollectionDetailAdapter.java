package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.CollectionBean;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class CollectionDetailAdapter extends BaseQuickAdapter<CollectionBean, BaseViewHolder> {
    public CollectionDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionBean item) {
        helper.setText(R.id.item_type, item.getPayType());
        helper.setText(R.id.item_pice, item.getPice());
        helper.setText(R.id.item_count, item.getCount());
    }
}
