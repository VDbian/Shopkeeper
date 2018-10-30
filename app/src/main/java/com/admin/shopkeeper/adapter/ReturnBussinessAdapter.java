package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.entity.ReturnBussinessBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/29.
 */

public class ReturnBussinessAdapter extends BaseQuickAdapter<ReturnBussinessBean, BaseViewHolder> {


    public ReturnBussinessAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReturnBussinessBean item) {
        helper.setText(R.id.item_name, item.getTablename() + "");
        helper.setText(R.id.item_count, item.getCounts() + "");
        helper.setText(R.id.item_price, item.getPrice() + "");
    }

}
