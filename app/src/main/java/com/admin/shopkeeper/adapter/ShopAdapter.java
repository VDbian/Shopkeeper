package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.ShopBean;
import com.admin.shopkeeper.entity.WeightBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class ShopAdapter extends BaseQuickAdapter<ShopBean, BaseViewHolder> {


    public ShopAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopBean item) {
        helper.setText(R.id.item_name, item.getName());
    }
}
