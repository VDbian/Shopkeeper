package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.ShopBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class SetOrLookFoodAdapter extends BaseQuickAdapter<ShopBean, BaseViewHolder> {


    public SetOrLookFoodAdapter(@LayoutRes int layoutResId ) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopBean item) {
        helper.setTextColor(R.id.item_shop_name, Color.parseColor("#333333"));

        helper.setText(R.id.item_shop_name, item.getName());
        helper.setText(R.id.item_set_food, "设置商品（类别）");
        helper.setText(R.id.item_look_food, "查看设置商品");

        helper.addOnClickListener(R.id.item_look_food);
        helper.addOnClickListener(R.id.item_set_food);
    }
}
