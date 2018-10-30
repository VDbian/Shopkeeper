package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.DeskOpenBean;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class DeskOpenAdapter extends BaseQuickAdapter<DeskOpenBean, BaseViewHolder> {
    public DeskOpenAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeskOpenBean item) {
        helper.setText(R.id.item_shop, item.getName());
        helper.setText(R.id.item_date, item.getName());
        helper.setText(R.id.item_count, String.valueOf(item.getPersonCount()));
        helper.setText(R.id.item_kaitai, String.valueOf(item.getKaitai() * 100) + "%");
        helper.setText(R.id.item_shangzuo, String.valueOf(item.getShangzuo() * 100) + "%");
        helper.setText(R.id.item_fantai, String.valueOf(item.getFantai() * 100) + "%");
    }
}
