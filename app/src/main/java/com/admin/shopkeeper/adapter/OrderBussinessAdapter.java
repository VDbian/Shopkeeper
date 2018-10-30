package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FoodBussinessBean;
import com.admin.shopkeeper.entity.OrderBussinessBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/29.
 */

public class OrderBussinessAdapter extends BaseQuickAdapter<OrderBussinessBean, BaseViewHolder> {


    public OrderBussinessAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBussinessBean item) {
        helper.setText(R.id.item_man, "满送优惠: " + item.getMan());
        helper.setText(R.id.item_frees, "卡券/积分: " + item.getFrees());
        helper.setText(R.id.item_member, "会员优惠: " + item.getMember());
        helper.setText(R.id.item_per, "权限优惠: " + item.getPer());
        helper.setText(R.id.item_yufu, "预付金额: " + item.getYufu());
        helper.setText(R.id.item_totle, "总金额: " + item.getTotle());
        helper.setText(R.id.item_free, "优惠金额: " + item.getFree());
        helper.setText(R.id.item_price, "应收金额: " + item.getPrice());
        if(item.getType().equals("1")){
            helper.setText(R.id.item_type, "订单类型: " + "预定菜品");
        }else if(item.getType().equals("2")){
            helper.setText(R.id.item_type, "订单类型: " + "预定桌位");
        }else if(item.getType().equals("3")){
            helper.setText(R.id.item_type, "订单类型: " + "外卖");
        }else if(item.getType().equals("4")){
            helper.setText(R.id.item_type, "订单类型: " + "快餐");
        }else if(item.getType().equals("5")){
            helper.setText(R.id.item_type, "订单类型: " + "扫码点餐");
        }else if(item.getType().equals("6")){
            helper.setText(R.id.item_type, "订单类型: " + "排队点餐");
        }else{
            helper.setText(R.id.item_type, "订单类型: " + "店内点餐");
        }

    }

}
