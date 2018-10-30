package com.admin.shopkeeper.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zeng on 2017/4/25.
 */

public class FoodAdapter extends BaseQuickAdapter<FoodEntity, BaseViewHolder> {
    public FoodAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodEntity item) {

        if (item.getType()) {
            helper.setText(R.id.foodName, item.getPackageName());
            helper.setText(R.id.unit, String.format(mContext.getString(R.string.string_unit), "份"));
        } else {
            helper.setText(R.id.foodName, item.getProductName());
            helper.setText(R.id.unit, String.format(mContext.getString(R.string.string_unit), item.getUnit()));
        }

        helper.setText(R.id.price, String.format(mContext.getString(R.string.string_money), item.getPrice()));


        Glide.with(mContext)
                .load(Config.BASE_IMG + App.INSTANCE().getShopID() + "/" + item.getProductFile())
                .centerCrop()
                .into((AppCompatImageView) helper.getView(R.id.imageView));
    }
}
