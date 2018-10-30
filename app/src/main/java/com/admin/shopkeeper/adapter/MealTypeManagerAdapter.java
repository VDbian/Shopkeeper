package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MealTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class MealTypeManagerAdapter extends BaseQuickAdapter<MealTypeBean, BaseViewHolder> {


    public MealTypeManagerAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MealTypeBean item) {
        helper.setText(R.id.item_name, item.getProductTypeName());
        helper.setText(R.id.item_order_number, item.getSortNum()+"");
    }
}
