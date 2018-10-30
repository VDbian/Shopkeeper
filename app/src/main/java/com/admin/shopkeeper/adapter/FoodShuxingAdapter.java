package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.SeasonBean;
import com.admin.shopkeeper.entity.ShuxingBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class FoodShuxingAdapter extends BaseQuickAdapter<ShuxingBean, BaseViewHolder> {


    public FoodShuxingAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShuxingBean item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_price, item.getPrice()+"");
    }
}
