package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.entity.WaiterBussinessBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/29.
 */

public class WaiterBussinessAdapter extends BaseQuickAdapter<WaiterBussinessBean, BaseViewHolder> {


    public WaiterBussinessAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaiterBussinessBean item) {
        helper.setText(R.id.item_name, item.getUsername() + "");
        helper.setText(R.id.item_count, item.getCounts() + "");
        helper.setText(R.id.item_price, item.getPrice() + "");
    }

}
