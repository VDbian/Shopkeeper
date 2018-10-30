package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;


import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.OrderfoodEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import timber.log.Timber;

/**
 * Created by admin on 2017/7/2.
 */

public class BottomSheetFoodAdapter extends BaseQuickAdapter<OrderfoodEntity, BaseViewHolder> {


    public BottomSheetFoodAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderfoodEntity item) {

        helper.setText(R.id.foodName, item.getTitle());
        helper.setText(R.id.price, String.format(mContext.getString(R.string.string_money), item.getPrice()));
        helper.setText(R.id.unit, String.format(mContext.getString(R.string.string_unit), item.getUnit()));

        AppCompatTextView textView = helper.getView(R.id.describe);
        textView.setText("");

        if (item.isShowWeight()) {
            textView.append(item.getWeight() + "斤、");
        }
        if (!TextUtils.isEmpty(item.getSpeced())) {
            textView.append(item.getSpeced());
        }
        if (!TextUtils.isEmpty(item.getKouweied())) {
            textView.append(" " + item.getKouweied());
        }
        if (!TextUtils.isEmpty(item.getSeasoned())) {
            textView.append(" " + item.getSeasoned());
        }
        if (item.getGivingnum() > 0) {
            textView.append(" 赠送" + item.getGivingnum() + "份");
        }


//        if (item.isShowWeight() || (item.getKouWeis() != null && item.getKouWeis().size() > 0) ||
//                (item.getSpecs() != null && item.getSpecs().size() > 0)) {
        if (item.isShowWeight()) {
//            if (item.isNumLayout()) {
//                //helper.setVisible(R.id.layout, true);
//                helper.setVisible(R.id.add, true);
//                helper.setVisible(R.id.reduce, true);
//                helper.setVisible(R.id.numberText, true);
//                //helper.setVisible(R.id.button, false);
//                if (item.getWeight() > 0) {
//                    helper.setVisible(R.id.reduce, true);
//                    helper.setVisible(R.id.numberText, true);
//                    helper.setText(R.id.numberText, String.valueOf(item.getNumber()));
//                } else {
//                    helper.setVisible(R.id.reduce, false);
//                    helper.setVisible(R.id.numberText, false);
//                }
//            } else {
//                helper.setVisible(R.id.add, false);
//                helper.setVisible(R.id.reduce, false);
//                helper.setVisible(R.id.numberText, false);
//                //helper.setVisible(R.id.button, true);
//            }
            helper.setVisible(R.id.add, false);
            helper.setVisible(R.id.reduce, false);
            helper.setVisible(R.id.numberText, false);
        } else {
            helper.setVisible(R.id.add, true);
            if (item.getNumber() > 0) {
                helper.setVisible(R.id.reduce, true);
                helper.setVisible(R.id.numberText, true);
                helper.setText(R.id.numberText, String.valueOf(item.getNumber()));
            } else {
                helper.setVisible(R.id.reduce, false);
                helper.setVisible(R.id.numberText, false);
            }
            //helper.setVisible(R.id.layout, true);
//            helper.setVisible(R.id.add, true);
//            helper.setVisible(R.id.reduce, true);
//            helper.setVisible(R.id.numberText, true);
//            //helper.setVisible(R.id.button, false);
//            if (item.getNumber() > 0) {
//                helper.setVisible(R.id.reduce, true);
//                helper.setVisible(R.id.numberText, true);
//                helper.setText(R.id.numberText, String.valueOf(item.getNumber()));
//            } else {
//                helper.setVisible(R.id.reduce, false);
//                helper.setVisible(R.id.numberText, false);
//            }
        }

        helper.addOnClickListener(R.id.button);
        helper.addOnClickListener(R.id.editButton);
        helper.addOnClickListener(R.id.add);
        helper.addOnClickListener(R.id.reduce);
        helper.addOnClickListener(R.id.givingButton);
    }


}
