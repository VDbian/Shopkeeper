package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.entity.SensitiveOpearation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class SensitiveOpearationAdapter extends BaseQuickAdapter<SensitiveOpearation, BaseViewHolder> {
    public SensitiveOpearationAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SensitiveOpearation item) {
        helper.setText(R.id.item_sensitive_id, "订单号："+item.getOrderNumber());
        if(item.getSensitiveState().equals("0")){
            helper.setText(R.id.item_order_state ,"撤销");
        }else{
            helper.setText(R.id.item_order_state ,"反结账");
        }
        helper.setText(R.id.item_sensitive_num, item.getTableNmae());
        helper.setText(R.id.item_sensitive_money, item.getPice()+"");
        helper.setText(R.id.item_sensitive_person, item.getUserName());
        helper.setText(R.id.item_sensitive_time, item.getTimes());
        helper.setText(R.id.item_sensitive_old_id, item.getId());
    }
}
