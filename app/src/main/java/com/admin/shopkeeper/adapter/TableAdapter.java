package com.admin.shopkeeper.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.utils.CalendarUtils;
import com.admin.shopkeeper.utils.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;

/**
 * Created by admin on 2017/4/19.
 */

public class TableAdapter extends BaseQuickAdapter<TableEntity, BaseViewHolder> {
    public TableAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setMerge(boolean merge) {
        isMerge = merge;
    }

    private boolean isMerge;


    @Override
    protected void convert(BaseViewHolder helper, TableEntity item) {
        helper.setText(R.id.tv_table_id, item.getTableName());
        helper.setVisible(R.id.radioButton, isMerge);
        helper.setChecked(R.id.radioButton, item.isSelector());

        switch (item.getOpen()) {
            case "2":
                //已经开桌
                helper.setText(R.id.tv_table_status, "已开台");
                helper.setBackgroundRes(R.id.tv_table_status, R.drawable.selector_table_status_1);
                helper.setVisible(R.id.tv_table_people, true);

                helper.setText(R.id.tv_table_people, String.valueOf(item.getPersonCounts()) + "人");
                helper.setText(R.id.tv_table_extra, "￥" + item.getPrice());
                helper.setText(R.id.tv_table_time, StringUtils.friendly_time(CalendarUtils.stringToDate(item.getKaiTime())));

                helper.setVisible(R.id.tv_table_extra, true);
                helper.setVisible(R.id.tv_table_time, true);

                helper.setBackgroundRes(R.id.item_bg, R.drawable.bg_table_open);
                helper.setBackgroundColor(R.id.tv_table_extra, Color.WHITE);
                helper.setTextColor(R.id.tv_table_extra, Color.parseColor("#28bbb2"));
                helper.setBackgroundColor(R.id.tv_table_time, Color.parseColor("#28bbb2"));
                break;
            case "1":
                helper.setText(R.id.tv_table_status, "已下单");
                helper.setBackgroundRes(R.id.tv_table_status, R.drawable.selector_table_status_2);
                helper.setTextColor(R.id.tv_table_extra, ContextCompat.getColor(mContext, R.color.orderStatusNormal_2));
                helper.setText(R.id.tv_table_people, String.valueOf(item.getPersonCounts()) + "人");
                helper.setText(R.id.tv_table_extra, "￥" + item.getPrice());
                helper.setText(R.id.tv_table_time, StringUtils.friendly_time(CalendarUtils.stringToDate(item.getKaiTime())));
                helper.setVisible(R.id.tv_table_people, true);

                helper.setVisible(R.id.tv_table_extra, true);
                helper.setVisible(R.id.tv_table_time, true);

                helper.setBackgroundRes(R.id.item_bg, R.drawable.bg_table_order);
                helper.setBackgroundColor(R.id.tv_table_extra, Color.WHITE);
                helper.setTextColor(R.id.tv_table_extra, Color.parseColor("#eeb205"));
                helper.setBackgroundColor(R.id.tv_table_time, Color.parseColor("#eeb205"));
                //已下单
                break;
            case "0":
                //空闲
                helper.setText(R.id.tv_table_extra, "空闲");
                helper.setBackgroundRes(R.id.tv_table_status, R.drawable.selector_table_status_0);
                helper.setVisible(R.id.tv_table_people, false);

                helper.setVisible(R.id.tv_table_extra, true);
                helper.setVisible(R.id.tv_table_time, false);

                helper.setBackgroundRes(R.id.item_bg, R.drawable.bg_table_free);
                helper.setBackgroundColor(R.id.tv_table_extra, Color.TRANSPARENT);
                helper.setTextColor(R.id.tv_table_extra, Color.WHITE);
                break;
            case "4":
                //已经开桌
                helper.setText(R.id.tv_table_status, "已开台");
                helper.setBackgroundRes(R.id.tv_table_status, R.drawable.selector_table_status_1);
                helper.setVisible(R.id.tv_table_people, true);

                helper.setText(R.id.tv_table_people, String.valueOf(item.getPersonCounts()) + "人");
                helper.setText(R.id.tv_table_extra, "￥" + item.getPrice());
                helper.setText(R.id.tv_table_time, StringUtils.friendly_time(CalendarUtils.stringToDate(item.getKaiTime())));

                helper.setVisible(R.id.tv_table_extra, true);
                helper.setVisible(R.id.tv_table_time, true);

                helper.setBackgroundRes(R.id.item_bg, R.drawable.bg_table_billing);
                helper.setBackgroundColor(R.id.tv_table_extra, Color.WHITE);
                helper.setTextColor(R.id.tv_table_extra, Color.parseColor("#27AE60"));
                helper.setBackgroundColor(R.id.tv_table_time, Color.parseColor("#35BB6D"));
                break;

        }

    }
}
