package com.admin.shopkeeper.adapter;

import android.content.Context;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FindFoodCouponDownBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


public class FindFoodAdapter extends BaseQuickAdapter<FindFoodCouponDownBean, BaseViewHolder> {
    private Context mContext;
    public FindFoodAdapter(int layoutResId , Context context) {
        super(layoutResId);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FindFoodCouponDownBean item) {
        helper.setText(R.id.foodName, item.getProductName());
    }
}
