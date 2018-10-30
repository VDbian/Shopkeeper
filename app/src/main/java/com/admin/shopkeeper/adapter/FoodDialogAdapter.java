package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodEntity;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class FoodDialogAdapter extends BaseQuickAdapter<FoodEntity, BaseViewHolder> {
    public FoodDialogAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public FoodDialogAdapter(@LayoutRes int layoutResId, @Nullable List<FoodEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodEntity item) {
        helper.setText(R.id.item_text, item.getProductName());
    }
}
