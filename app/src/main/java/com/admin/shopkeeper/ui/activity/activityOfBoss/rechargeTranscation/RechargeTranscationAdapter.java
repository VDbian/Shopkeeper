package com.admin.shopkeeper.ui.activity.activityOfBoss.rechargeTranscation;

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
import com.admin.shopkeeper.entity.MemberTranscationBean;
import com.admin.shopkeeper.utils.UIUtils;
import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class RechargeTranscationAdapter extends PanelAdapter {

    private static final int TITLE_TYPE = 4;
    private static final int ROOM_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int ORDER_TYPE = 2;

    List<MemberTranscationBean> datas = new ArrayList<>();

    public RechargeTranscationAdapter() {
    }

    @Override
    public int getRowCount() {
        return datas.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    public void setDatas(List<MemberTranscationBean> datas) {
        this.datas = datas;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        PanelViewHolder viewHolder = (PanelViewHolder) holder;
        if (row == 0) {
            setTitleView(viewHolder, column);
        } else {
            MemberTranscationBean bean = datas.get(row - 1);
            setDataView(viewHolder, column, bean);
            viewHolder.textView.setOnClickListener(view -> {
                if (lishener != null) {
                    lishener.onItemClick(row - 1);
                }
            });
        }
    }

    private void setDataView(PanelViewHolder holder, int column, MemberTranscationBean bean) {
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
                holder.textView.setText(TextUtils.isEmpty(bean.getShopName()) ? App.INSTANCE().getShopName() : bean.getShopName());
                break;
            case 1:
                if(!TextUtils.isEmpty(bean.getName())){
                    holder.textView.setText(bean.getName());
                }else{
                    holder.textView.setText("");
                }
                break;
            case 2:
                if(!TextUtils.isEmpty(bean.getPhone())){
                    holder.textView.setText(bean.getPhone());
                }else{
                    holder.textView.setText("");
                }
                break;
            case 3:
                holder.textView.setText(String.valueOf(bean.getRechargeMoney()));
                break;
            case 4:
                holder.textView.setText(String.valueOf(bean.getUsedMoney()));
                break;
            case 5:
                holder.textView.setText(String.valueOf(bean.getZonJian()));
                break;
            case 6:
                holder.textView.setText(String.valueOf(bean.getZonAdd()));
                break;
            case 7:
                holder.textView.setText(String.valueOf(bean.getYue()));
                break;
        }
    }

    int[] status = {0, 0, 0, 0,0};

    private void setTitleView(PanelViewHolder holder, int column) {
        holder.textView.setTextColor(Color.parseColor("#888888"));
        holder.ll.setBackgroundColor(Color.parseColor("#f4f4f4"));
        holder.line.setVisibility(View.GONE);
        switch (column) {
            case 0:
                holder.textView.setText("商家名称");
                break;
            case 1:
                holder.textView.setText("姓名");
                break;
            case 2:
                holder.textView.setText("手机号");
                break;
            case 3:
                holder.textView.setText("累积充值");
                break;
            case 4:
                holder.textView.setText("累积消费");
                break;
            case 5:
                holder.textView.setText("消费金额");
                break;
            case 6:
                holder.textView.setText("充值金额");
                break;
            case 7:
                holder.textView.setText("余额");
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
