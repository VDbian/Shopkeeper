package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MutiBean;
import com.admin.shopkeeper.entity.OrderManage;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class OrderManageAdapter extends BaseQuickAdapter<OrderManage, BaseViewHolder> {
    List<MutiBean> payTypeList = new ArrayList<>();
    String payTypeStr = "";

    public OrderManageAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
        payTypeList.add(new MutiBean("现金", false, 1));
        payTypeList.add(new MutiBean("银行卡", false, 2));
        payTypeList.add(new MutiBean("主扫微信", false, 3));
        payTypeList.add(new MutiBean("挂账", false, 4));
        payTypeList.add(new MutiBean("会员卡", false, 5));
        payTypeList.add(new MutiBean("被扫支付宝", false, 6));
        payTypeList.add(new MutiBean("被扫微信", false, 7));
        payTypeList.add(new MutiBean("美团券", false, 8));
        payTypeList.add(new MutiBean("大众点评券", false, 9));
        payTypeList.add(new MutiBean("主扫支付宝", false, 10));
    }

    //Type  1.预定菜品 2 预定桌位  3.外卖  4.快餐 5.扫码点餐 6.排队点餐 7.店内点餐
    @Override
    protected void convert(BaseViewHolder helper, OrderManage item) {
        helper.setText(R.id.item_order_id, "订单号：" + item.getOrderNumber());
//        helper.setText(R.id.item_order_state, item.getOrderSate());
        switch (item.getType()) {
            case "1":
                helper.setText(R.id.item_order_type, "预定菜品");
                break;
            case "2":
                helper.setText(R.id.item_order_type, "预定桌位");
                break;
            case "3":
                if(!TextUtils.isEmpty(App.INSTANCE().getUser().getMasterType())){
                    if(App.INSTANCE().getUser().getMasterType().equals("1")){
                        helper.setText(R.id.item_order_type, "商家采购");
                    }else{
                        helper.setText(R.id.item_order_type, "外卖");
                    }
                }else{
                    helper.setText(R.id.item_order_type, "外卖");
                }
                break;
            case "4":
                helper.setText(R.id.item_order_type, "快餐");
                break;
            case "5":
                helper.setText(R.id.item_order_type, "扫码点餐");
                break;
            case "6":
                helper.setText(R.id.item_order_type, "排队点餐");
                break;
            case "7":
                helper.setText(R.id.item_order_type, "店内点餐");
                break;
            default:
                break;
        }


        for (MutiBean mutiBean : payTypeList) {
            if (!TextUtils.isEmpty(item.getPayType())) {
                if (item.getPayType().contains(mutiBean.getValue() + "")) {
                    payTypeStr += mutiBean.getText() + ",";
                }
            }
        }
        if (!TextUtils.isEmpty(payTypeStr)) {
            helper.setText(R.id.item_order_pay_type, payTypeStr.substring(0, payTypeStr.length() - 1));
            payTypeStr = "";
        } else {
            helper.setVisible(R.id.item_order_pay_type, false);
        }
        helper.setText(R.id.item_order_total, "￥" + item.getTotalMoney());
        helper.setText(R.id.item_order_youhui, "￥" + item.getFreeMoney());
        helper.setText(R.id.item_order_jieZhang, "￥" + item.getChargeMoney());
        helper.setText(R.id.item_order_person, item.getUsername());
        if (!TextUtils.isEmpty(item.getTableName())) {
            helper.setText(R.id.item_order_name, item.getTableName());
        } else {
            helper.setVisible(R.id.item_order_name, false);
        }
        helper.setText(R.id.item_order_time, item.getJieZhangTime());
    }
}
