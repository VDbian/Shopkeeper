package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.HouseBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class DeskTypeAdapter extends BaseQuickAdapter<DeskTypeBean, BaseViewHolder> {


    public DeskTypeAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeskTypeBean item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_info, item.getPersonCountInfo());
        helper.setText(R.id.item_type, item.getTypes());
    }
}
