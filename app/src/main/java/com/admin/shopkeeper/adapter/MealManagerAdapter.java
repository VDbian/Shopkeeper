package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MealBean;
import com.admin.shopkeeper.entity.MealTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
public class MealManagerAdapter extends BaseQuickAdapter<MealBean, BaseViewHolder> {

    private List<MealTypeBean> mealTypeBeanList;
    public MealManagerAdapter(@LayoutRes int layoutResId , List<MealTypeBean> list) {
        super(layoutResId);
        mealTypeBeanList = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, MealBean item) {
        helper.setText(R.id.item_name, item.getName());
        for(MealTypeBean  bean :mealTypeBeanList){
            if(bean.getGuId().equals(item.getProductTypes())){
                helper.setText(R.id.item_type, bean.getProductTypeName());
            }
        }
        helper.setText(R.id.item_price, item.getPrice() + "");
        helper.setText(R.id.item_member_price, item.getMemberPice() + "");

    }
}
