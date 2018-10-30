package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MemberInfoOfItemBean;
import com.admin.shopkeeper.entity.OrderBussinessBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


public class MemberInfoOfItemAdapter extends BaseQuickAdapter<MemberInfoOfItemBean, BaseViewHolder> {

    int mType;

    public MemberInfoOfItemAdapter(@LayoutRes int layoutResId, int type) {
        super(layoutResId);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberInfoOfItemBean item) {
        if (item.getTimes() != null) {
            helper.setText(R.id.item_time, "消费时间" + '\t' + item.getTimes());
        }
        if (mType == 4) {
            helper.setText(R.id.item_money, "积分减少" + '\t' + item.getCounts());
            if (item.getCategory().equals("1")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买优惠券");
            } else if (item.getCategory().equals("2")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "微信充值");
            } else if (item.getCategory().equals("3")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "消费");
            } else if (item.getCategory().equals("4")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "充值赠送");
            } else if (item.getCategory().equals("5")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买代金券");
            } else if (item.getCategory().equals("6")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买商品券");
            } else if (item.getCategory().equals("7")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买团购券");
            } else if (item.getCategory().equals("8")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "店内充值");
            }
            helper.setText(R.id.item_time, "操作时间\t" + item.getTimes());
            helper.setVisible(R.id.item_type_pay, false);
        } else if (mType == 3) {
            helper.setText(R.id.item_money, "充值金额" + '\t' + item.getPrice());
            if (item.getCategory().equals("1")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买优惠券");
            } else if (item.getCategory().equals("2")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "微信充值");
            } else if (item.getCategory().equals("3")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "消费");
            } else if (item.getCategory().equals("4")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "充值赠送");
            } else if (item.getCategory().equals("5")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买代金券");
            } else if (item.getCategory().equals("6")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买商品券");
            } else if (item.getCategory().equals("7")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买团购券");
            } else if (item.getCategory().equals("8")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "店内充值");
            }
            helper.setText(R.id.item_time, "充值时间\t" + item.getTimes());
            helper.setVisible(R.id.item_type_pay, false);
        } else if (mType == 2) {
            if (item.getCategory().equals("1")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买优惠券");
            } else if (item.getCategory().equals("2")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "微信充值");
            } else if (item.getCategory().equals("3")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "积分兑换代金券");
            } else if (item.getCategory().equals("4")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "消费");
            } else if (item.getCategory().equals("5")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "充值赠送");
            } else if (item.getCategory().equals("6")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买代金券");
            } else if (item.getCategory().equals("7")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买商品券");
            } else if (item.getCategory().equals("8")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "购买团购券");
            } else if (item.getCategory().equals("9")) {
                helper.setText(R.id.item_type_consume, "消费类别\t" + "店内充值");
            }
            helper.setText(R.id.item_money, "消费金额" + '\t' + item.getPrice());
            if (item.getPayType() != null) {
                if (item.getPayType().equals("1")) {
                    helper.setText(R.id.item_type_pay, "支付类别\t" + "微信支付");
                } else if (item.getPayType().equals("2")) {
                    helper.setText(R.id.item_type_pay, "支付类别\t" + "余额支付");
                } else if (item.getPayType().equals("3")) {
                    helper.setText(R.id.item_type_pay, "支付类别\t" + "积分支付");
                } else if (item.getPayType().equals("4")) {
                    helper.setText(R.id.item_type_pay, "支付类别\t" + "店内支付");
                } else {
                    helper.setVisible(R.id.item_type_pay, false);
                }
            }
            helper.setText(R.id.item_time, "消费时间" + '\t' + item.getTimes());
        } else {
            if (item.getType().equals("1")) {
                helper.setText(R.id.item_time, "获取方式" + '\t' + "积分兑换");
            } else if (item.getType().equals("2")) {
                helper.setText(R.id.item_time, "获取方式" + '\t' + "赠送");
            } else if (item.getType().equals("3")) {
                helper.setText(R.id.item_time, "获取方式" + '\t' + "现金购买");
            } else {
                helper.setText(R.id.item_time, "获取方式" + '\t' + item.getIntergialTypeID());
            }
            if (item.getIntergialTypeID().equals("1")) {
                helper.setText(R.id.item_money, "卡券类别" + '\t' + "优惠券");
            } else if (item.getIntergialTypeID().equals("2")) {
                helper.setText(R.id.item_money, "卡券类别" + '\t' + "代金券");
            } else if (item.getIntergialTypeID().equals("3")) {
                helper.setText(R.id.item_money, "卡券类别" + '\t' + "商品券");
            } else {
                helper.setText(R.id.item_money, "卡券类别" + '\t' + "团购券");
            }
            helper.setText(R.id.item_type_consume, "获取时间\t" + item.getEnterTime());
            helper.setText(R.id.item_type_pay, "使用时间\t" + item.getUserTime());
        }
        /**1.第一次关注赠送积分 2.积分兑换优惠券  3.积分兑换代金券 4.签到赠送积分
         * 5.积分抵现 6.充值赠送 7.消费金额换算积分 8.积分兑换商品券  9积分兑换团购券
         */
        //1.微信支付  2.余额支付  3.积分支付 4.店内支付

    }

}
