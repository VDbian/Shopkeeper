package com.admin.shopkeeper.adapter;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.KouWei;
import com.admin.shopkeeper.entity.Season;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/6/30.
 */

public class MaterialAdapter extends BaseMultiItemQuickAdapter<Season, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MaterialAdapter(List<Season> data) {
        super(data);
        addItemType(KouWei.NORMAL, R.layout.item_guige);
        addItemType(KouWei.EDIT, R.layout.item_guige_edit);
    }

    @Override
    protected void convert(BaseViewHolder helper, Season item) {
        helper.setText(R.id.textView, item.getName() + " ￥" + item.getPrice());
        TextView textView = helper.getView(R.id.numberText);
        if (item.getItemType() == KouWei.EDIT) {
            AppCompatEditText editText = helper.getView(R.id.textView);
            helper.getView(R.id.textView).setSelected(item.isSelected());
            helper.getView(R.id.item_boot).setSelected(item.isSelected());
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    item.setSelected(!TextUtils.isEmpty(s));
                    item.setName(TextUtils.isEmpty(s) ? "" : s.toString());
                    helper.getView(R.id.textView).setSelected(item.isSelected());
                }
            });
            if (item.isSelected()) {
                textView.setText(item.getCount()+"");
                item.setCount(item.getCount());
            } else {
                textView.setText("0");
                item.setCount(0);
            }
        } else if (item.getItemType() == KouWei.NORMAL) {
            helper.getView(R.id.textView).setSelected(item.isSelected());
            helper.getView(R.id.item_boot).setSelected(item.isSelected());
            if (item.isSelected()) {
                textView.setText(item.getCount()+"");
                item.setCount(item.getCount());
            } else {
                textView.setText("0");
                item.setCount(0);
            }
        }

        helper.setOnClickListener(R.id.reduce, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(textView.getText().toString());
                if (number > 0) {
                    number--;
                }
                textView.setText(String.valueOf(number));
                item.setCount(number);
                if (number <= 0) {
                    item.setSelected(false);
                    helper.getView(R.id.textView).setSelected(item.isSelected());
                    helper.getView(R.id.item_boot).setSelected(item.isSelected());
                }

            }
        });
        helper.setOnClickListener(R.id.add, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(textView.getText().toString());
                number++;
                textView.setText(String.valueOf(number));
                item.setCount(number);
                item.setSelected(true);
                helper.getView(R.id.textView).setSelected(item.isSelected());
                helper.getView(R.id.item_boot).setSelected(item.isSelected());
            }
        });
    }
}
