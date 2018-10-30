package com.admin.shopkeeper.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.ui.activity.BigImageActivity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class DeleteFoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {


    public DeleteFoodAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setText(R.id.item_name, item.getProductName());
        helper.setText(R.id.item_count, item.getProductCount() + "");

        CheckBox checkBox = helper.getView(R.id.item_check);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setDelete(isChecked);
        });
        checkBox.setChecked(item.isDelete());

    }
}
