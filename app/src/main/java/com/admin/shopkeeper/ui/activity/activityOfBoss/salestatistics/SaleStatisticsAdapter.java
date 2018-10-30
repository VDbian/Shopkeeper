package com.admin.shopkeeper.ui.activity.activityOfBoss.salestatistics;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.SaleStatisticsBean;
import com.admin.shopkeeper.utils.UIUtils;
import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class SaleStatisticsAdapter extends PanelAdapter {


    List<SaleStatisticsBean> datas = new ArrayList<>();

    public SaleStatisticsAdapter() {
    }

    @Override
    public int getRowCount() {
        return datas.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    public void setDatas(List<SaleStatisticsBean> datas) {
        this.datas = datas;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        PanelViewHolder viewHolder = (PanelViewHolder) holder;
        if (row == 0) {
            setTitleView(viewHolder, column);
        } else {
            SaleStatisticsBean bean = datas.get(row - 1);
            setDataView(viewHolder, column, bean);
            viewHolder.textView.setOnClickListener(view -> {
                if (lishener != null) {
                    lishener.onItemClick(row - 1);
                }
            });
        }
    }

    private void setDataView(PanelViewHolder holder, int column, SaleStatisticsBean bean) {
        UIUtils.setNullDrawable(holder.textView);
        if (bean == datas.get(datas.size() - 1)) {
            holder.textView.setTextColor(Color.parseColor("#FF8208"));
            holder.ll.setBackgroundColor(Color.parseColor("#FDF8E4"));
            holder.line.setVisibility(View.GONE);
        } else {
            holder.textView.setTextColor(Color.parseColor("#333333"));
            holder.ll.setBackgroundColor(Color.WHITE);
            holder.line.setVisibility(View.VISIBLE);
        }

        switch (column) {
            case 0:
                holder.textView.setText(bean.getProductName());
                break;
            case 1:
                holder.textView.setText(bean.getProductTypeName());
                break;
            case 2:
                holder.textView.setText(bean.getId());
                break;
            case 3:
                holder.textView.setText(String.valueOf(bean.getCounts()));
                break;
            case 4:
                holder.textView.setText(String.valueOf(bean.getTotalPrice()));
                break;
            case 5:
                holder.textView.setText(String.valueOf(bean.getFreePrice()));
                break;
            case 6:
                holder.textView.setText(String.valueOf(bean.getChargeMoney()));
                break;
        }
    }

    int[] status = {0, 0, 0, 0};

    private void setTitleView(PanelViewHolder holder, int column) {
        holder.textView.setTextColor(Color.parseColor("#888888"));
        holder.ll.setBackgroundColor(Color.parseColor("#f4f4f4"));
        holder.line.setVisibility(View.GONE);
        switch (column) {
            case 0:
                holder.textView.setText("商品名称");
                break;
            case 1:
                holder.textView.setText("商品类别");
                break;
            case 2:
                holder.textView.setText("商品编码");
                break;
            case 3:
                holder.textView.setText("销售数量");
                break;
            case 4:
                holder.textView.setText("销售金额");
                break;
            case 5:
                holder.textView.setText("优惠金额");
                break;
            case 6:
                holder.textView.setText("优惠后金额");
                break;
        }

        if (column < 3) {
            UIUtils.setNullDrawable(holder.textView);
            return;
        }

        int index = column - 3;

        if (status[index] == 0) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_default);
        }

        if (status[index] % 3 == 0) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_default);
        } else if (status[index] % 3 == 1) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_a_z);
        } else if (status[index] % 3 == 2) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_z_a);
        }

        holder.textView.setOnClickListener(view -> {
            if (lishener != null) {
                status[index]++;

                lishener.onSort(column, status[index]);
            }
        });
    }

    OnItemClickLishener lishener;

    public void setOnItemClickListener(OnItemClickLishener listener) {
        this.lishener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PanelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_panel, parent, false));
    }

    private static class PanelViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        View line;
        LinearLayout ll;

        public PanelViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.title);
            line = view.findViewById(R.id.line);
            ll = (LinearLayout) view.findViewById(R.id.item_ll);
        }
    }

    interface OnItemClickLishener {
        void onItemClick(int raw);

        void onSort(int col, int status);
    }
}
