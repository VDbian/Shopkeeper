package com.admin.shopkeeper.ui.activity.activityOfBoss.free;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.FreeBean;
import com.admin.shopkeeper.utils.UIUtils;
import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class FreeAdapter extends PanelAdapter {

    private static final int TITLE_TYPE = 4;
    private static final int ROOM_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int ORDER_TYPE = 2;

    List<FreeBean> datas = new ArrayList<>();

    public FreeAdapter() {
    }

    @Override
    public int getRowCount() {
        return datas.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    public void setDatas(List<FreeBean> datas) {
        this.datas = datas;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        PanelViewHolder viewHolder = (PanelViewHolder) holder;
        if (row == 0) {
            setTitleView(viewHolder, column);
        } else {
            FreeBean bean = datas.get(row - 1);
            setDataView(viewHolder, column, bean);
            viewHolder.textView.setOnClickListener(view -> {
                if (lishener != null) {
                    lishener.onItemClick(row - 1);
                }
            });
        }
    }

    private void setDataView(PanelViewHolder holder, int column, FreeBean bean) {
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
                holder.textView.setText(TextUtils.isEmpty(bean.getNames()) ? App.INSTANCE().getShopName() : bean.getNames());
                break;
            case 1:
                holder.textView.setText(String.valueOf(bean.getFreeMoney()));
                break;
        }
    }

    int status = 0;

    private void setTitleView(PanelViewHolder holder, int column) {
        holder.textView.setTextColor(Color.parseColor("#888888"));
        holder.ll.setBackgroundColor(Color.parseColor("#f4f4f4"));
        holder.line.setVisibility(View.GONE);
        switch (column) {
            case 0:
                holder.textView.setText("门店名称");
                break;
            case 1:
                holder.textView.setText("优惠总金额");
                break;
        }

        if (column < 1) {
            UIUtils.setNullDrawable(holder.textView);
            return;
        }

        if (status == 0) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_default);
        }

        if (status % 3 == 0) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_default);
        } else if (status % 3 == 1) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_a_z);
        } else if (status % 3 == 2) {
            UIUtils.setDrawableRight(holder.textView, R.mipmap.sort_z_a);
        }

        holder.textView.setOnClickListener(view -> {
            if (lishener != null) {
                status++;
                lishener.onSort(column, status);
            }
        });
    }

    OnItemClickLishener lishener;

    public void setOnItemClickListener(OnItemClickLishener listener) {
        this.lishener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PanelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_panel2, parent, false));
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
