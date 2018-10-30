package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.GuazhangDetailBean;
import com.admin.shopkeeper.entity.HouseBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/8/30.
 */
public class GuazhangDetailAdapter extends BaseQuickAdapter<GuazhangDetailBean, BaseViewHolder> {


    public GuazhangDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GuazhangDetailBean item) {
        helper.setText(R.id.item_table, item.getTableName());
        helper.setText(R.id.item_money, item.getChargeMoney()+"");
        if(item.getUserIdGuaType().equals("0")){
            helper.setText(R.id.item_guazhang_type,"未结账");
        }else{
            helper.setText(R.id.item_guazhang_type, "已结账");
        }
        helper.setText(R.id.item_date, item.getInputTime());
    }
}
