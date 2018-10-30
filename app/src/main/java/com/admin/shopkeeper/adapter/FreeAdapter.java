package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FreeBean;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class FreeAdapter extends BaseQuickAdapter<FreeBean, BaseViewHolder> {
    public FreeAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeBean item) {
        helper.setText(R.id.item_sortno, (getData().indexOf(item)+1) + "");
        helper.setText(R.id.item_name, item.getNames());
        helper.setText(R.id.item_money, "￥" + String.valueOf(item.getFreeMoney()));
    }
}
