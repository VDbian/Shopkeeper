package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by admin on 2017/6/30.
 */

public class OrderFoodAdapter extends BaseQuickAdapter<FoodEntity, OrderFoodAdapter.ViewHolder> {


    public OrderFoodAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, FoodEntity item) {
        if (item.getType()) {
            helper.setText(R.id.foodName, item.getPackageName());
            helper.setText(R.id.unit, String.format(mContext.getString(R.string.string_unit), "份"));
        } else {
            helper.setText(R.id.foodName, item.getProductName());
            helper.setText(R.id.unit, String.format(mContext.getString(R.string.string_unit), item.getUnit()));
        }
        helper.setText(R.id.price, String.format(mContext.getString(R.string.string_money), item.getPrice()));
        if (!item.getProductShuXing().equals("0")) { //1是称斤 2是规格菜品 0是默认菜品
            helper.setVisible(R.id.layout, false);
            helper.setVisible(R.id.root, true);
            helper.badgeView.setBadgeNumber(item.getNumber());
        } else {
                if(item.getTasteType() !=null){
                    if (item.getTasteType().equals("1")) {
                        helper.setVisible(R.id.layout, false);
                        helper.setVisible(R.id.root, true);
                        helper.badgeView.setBadgeNumber(item.getNumber());
                    } else {
                        helper.setVisible(R.id.layout, true);
                        helper.setVisible(R.id.root, false);
                        if (item.getNumber() > 0) {
                            helper.setVisible(R.id.reduce, true);
                            helper.setVisible(R.id.numberText, true);
//                helper.setText(R.id.numberText, item.getNumber() >= 10 ? String.valueOf(item.getNumber()) : "0" + item.getNumber());
                            helper.setText(R.id.numberText, String.valueOf(item.getNumber()));
                        } else {
                            helper.setVisible(R.id.reduce, false);
                            helper.setVisible(R.id.numberText, false);
                        }
                    }
                }
        }

        helper.addOnClickListener(R.id.imageView);
        Glide.with(mContext).load(Config.BASE_IMG + App.INSTANCE().getShopID() + "/" + item.getProductFile())
                .centerCrop().into((AppCompatImageView) helper.getView(R.id.imageView));
        helper.addOnClickListener(R.id.button);
        helper.addOnClickListener(R.id.add);
        helper.addOnClickListener(R.id.reduce);
    }

    class ViewHolder extends BaseViewHolder {
        QBadgeView badgeView;

        public ViewHolder(View view) {
            super(view);

            badgeView = new QBadgeView(mContext);
            badgeView.bindTarget(view.findViewById(R.id.root));
            badgeView.setBadgeGravity(Gravity.TOP | Gravity.END);


        }
    }
}
