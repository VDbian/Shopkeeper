package com.admin.shopkeeper.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.dialog.SetFoodDialog;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.ui.activity.BigImageActivity;
import com.admin.shopkeeper.utils.Tools;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class FoodManagerAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {


    public FoodManagerAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setText(R.id.item_name, item.getProductName());
        helper.setText(R.id.item_type, item.getProductTypeName());
        helper.setText(R.id.item_price, item.getPrice() + "");

        ImageView imageView = helper.getView(R.id.item_image);
        Glide.with(mContext)
                .load(Config.BASE_IMG + App.INSTANCE().getShopID() + "/" + item.getProductFile())
                .centerCrop().into(imageView);


        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BigImageActivity.class);
            intent.putExtra(Config.PARAM1, item.getProductFile());
            mContext.startActivity(intent);
        });
    }
}
