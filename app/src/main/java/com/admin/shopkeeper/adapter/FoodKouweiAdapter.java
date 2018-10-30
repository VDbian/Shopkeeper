package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.HouseBean;
import com.admin.shopkeeper.entity.KouweiBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class FoodKouweiAdapter extends BaseQuickAdapter<KouweiBean, BaseViewHolder> {


    public FoodKouweiAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, KouweiBean item) {
        helper.setText(R.id.item_no, item.getNo());
        helper.setText(R.id.item_name, item.getName());
    }
}
