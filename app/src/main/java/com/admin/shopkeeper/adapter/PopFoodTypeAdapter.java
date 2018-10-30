package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class PopFoodTypeAdapter extends BaseQuickAdapter<MenuTypeEntity, BaseViewHolder> {


    public PopFoodTypeAdapter(@LayoutRes int layoutResId, @Nullable List<MenuTypeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuTypeEntity item) {
        helper.setText(R.id.textView, item.getProductTypeName());
        helper.setChecked(R.id.checkbox, item.isCheck());
    }

}
