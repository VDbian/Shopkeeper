package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.entity.PayBussinessBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/29.
 */

public class PayBussinessAdapter extends BaseQuickAdapter<PayBussinessBean, BaseViewHolder> {


    public PayBussinessAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayBussinessBean item) {

        switch (item.getPayType()){
            case "1":
                helper.setText(R.id.item_name, "现金支付");
                break;
            case "2":
                helper.setText(R.id.item_name, "银行卡");
                break;
            case "3":
                helper.setText(R.id.item_name, "主扫微信");
                break;
            case "4":
                helper.setText(R.id.item_name, "挂账");
                break;
            case "5":
                helper.setText(R.id.item_name, "会员卡");
                break;
            case "6":
                helper.setText(R.id.item_name, "被扫支付宝");
                break;
            case "7":
                helper.setText(R.id.item_name, "被扫微信");
                break;
            case "8":
                helper.setText(R.id.item_name, "美团");
                break;
            case "9":
                helper.setText(R.id.item_name, "大众点评");
                break;
            case "10":
                helper.setText(R.id.item_name, "主扫支付宝");
                break;
        }

        helper.setVisible(R.id.item_count, false);
        helper.setText(R.id.item_price, item.getPrice() + "");
    }

}
