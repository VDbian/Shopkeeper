package com.admin.shopkeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.admin.shopkeeper.entity.DepartmentBean;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */

public class DepartmentAdapter extends BaseAdapter {

    Context context;
    List<DepartmentBean> datas;
    private final LayoutInflater inflater;

    public DepartmentAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public DepartmentBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        TextView textView = (TextView) convertView;

        textView.setText(getItem(position).getName());
        return convertView;
    }

    public void setData(List<DepartmentBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

}
