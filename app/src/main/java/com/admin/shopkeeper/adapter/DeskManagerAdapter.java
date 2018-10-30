package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.DeskBean;
import com.admin.shopkeeper.entity.HouseBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class DeskManagerAdapter extends BaseQuickAdapter<DeskBean, BaseViewHolder> {


    public DeskManagerAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeskBean item) {
        helper.setText(R.id.item_room, item.getFangjian());
        helper.setText(R.id.item_name, item.getTableName());
        helper.setText(R.id.item_count, item.getPersonCount());
        helper.setText(R.id.item_sortno, item.getSortNo() + "");
    }
}
