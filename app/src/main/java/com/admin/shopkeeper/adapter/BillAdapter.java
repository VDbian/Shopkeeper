package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.admin.shopkeeper.utils.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class BillAdapter extends BaseQuickAdapter<PayMeEntity, BaseViewHolder> {
    public BillAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayMeEntity item) {
        helper.getView(R.id.root).setSelected(item.isSelected());
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.money, "￥" + (item.getMoney()));
    }
}
