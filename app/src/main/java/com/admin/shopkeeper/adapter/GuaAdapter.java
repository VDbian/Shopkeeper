package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.entity.HouseBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class GuaAdapter extends BaseQuickAdapter<GuaZhangBean, BaseViewHolder> {


    public GuaAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GuaZhangBean item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_phone, item.getPhone());
        helper.setText(R.id.item_remark, item.getRemark());
    }
}
