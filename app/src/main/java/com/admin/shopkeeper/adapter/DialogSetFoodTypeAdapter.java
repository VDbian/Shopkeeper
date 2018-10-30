package com.admin.shopkeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.MenuTypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class DialogSetFoodTypeAdapter extends BaseAdapter {

    private List<MenuTypeEntity> oList = new ArrayList<>();
    private Context context;

    public DialogSetFoodTypeAdapter(Context context, List<MenuTypeEntity> list) {
        this.context = context;
        oList = list;
    }

    public void setData(ArrayList<MenuTypeEntity> list) {
        oList = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return oList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return oList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_list_item_multiple_choice, null);
            holder = new ViewHolder();
            holder.nameView = (CheckedTextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameView.setText(oList.get(position).getProductTypeName());
        if(oList.get(position).isCheck()){
            holder.nameView.setChecked(true);
        }else{
            holder.nameView.setChecked(false);
        }
        return convertView;
    }

    class ViewHolder {
        CheckedTextView nameView;
    }
}
