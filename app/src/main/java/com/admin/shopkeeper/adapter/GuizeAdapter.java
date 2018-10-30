package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.GuizeBean;
import com.admin.shopkeeper.entity.PayMeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */

public class GuizeAdapter extends BaseQuickAdapter<GuizeBean, BaseViewHolder> {
    public GuizeAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GuizeBean item) {
        switch (item.getName()) {
            case "1":
                helper.setText(R.id.item_name, "每次消费金额");
                break;
            case "2":
                helper.setText(R.id.item_name, "累计消费金额");
                break;
            case "3":
                helper.setText(R.id.item_name, "累计消费次数");
                break;
            case "4":
                helper.setText(R.id.item_name, "累计存值金额");
                break;
            case "5":
                helper.setText(R.id.item_name, "累计积分");
                break;
            case "6":
                helper.setText(R.id.item_name, "最后消费");
                break;
            case "7":
                helper.setText(R.id.item_name, "最后预定");
                break;
            case "8":
                helper.setText(R.id.item_name, "最后排队");
                break;
            case "9":
                helper.setText(R.id.item_name, "排队超时");
                break;
            default:
                helper.setText(R.id.item_name, "");
        }

        if (item.getType() != null) {
            switch (item.getType()) {
                case "1":
                    helper.setText(R.id.item_tiaojian, "大于" + item.getValue());
                    break;
                case "2":
                    helper.setText(R.id.item_tiaojian, "小于" + item.getValue());
                    break;
                case "3":
                    helper.setText(R.id.item_tiaojian, "等于" + item.getValue());
                    break;
                default:
                    helper.setText(R.id.item_tiaojian, "");
            }
        } else {
            helper.setText(R.id.item_tiaojian, "");
        }


        helper.setText(R.id.item_start, "开始时间：" + item.getStart().replace("00:00:00", "").replace("23:59:59", "").trim());
        helper.setText(R.id.item_end, "结束时间:" + item.getEnd().replace("00:00:00", "").replace("23:59:59", "").trim());
    }


}
