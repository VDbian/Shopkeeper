package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.HouseBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class HouseAdapter extends BaseQuickAdapter<HouseBean, BaseViewHolder> {


    public HouseAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HouseBean item) {
        helper.setText(R.id.item_shop, item.getShangJia());
        helper.setText(R.id.item_state, item.getState() == 1 ? "开启" : "关闭");
        helper.setText(R.id.item_name, item.getName());
    }
}
