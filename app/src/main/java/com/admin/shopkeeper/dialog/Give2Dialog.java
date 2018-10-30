package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.OrderfoodEntity;


/**
 * Created by admin on 2017/6/9.
 */

public class Give2Dialog extends AppCompatDialog {

    private Give2Dialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private Give2Dialog dialog;
        private Context context;
        private int theme;

        AppCompatImageButton reduce;
        AppCompatTextView numberText;
        AppCompatButton button;
        int num = 0;

        private OnButtonClick buttonClick;


        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }


        public OrderDetailFood getEntity() {
            return entity;
        }

        public void setEntity(OrderDetailFood entity) {
            this.entity = entity;
        }

        private OrderDetailFood entity;

        public Give2Dialog creater() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_give, null);
            dialog = new Give2Dialog(context, theme, view);

            if (entity == null) {
                return dialog;
            }

            button = (AppCompatButton) view.findViewById(R.id.OneBtn);
            button.setText("确定");

            AppCompatTextView titleText = (AppCompatTextView) view.findViewById(R.id.foodName);
            titleText.setText("赠送菜品");
            AppCompatTextView tipBtn = (AppCompatTextView) view.findViewById(R.id.tipBtn);
            tipBtn.setOnClickListener(v -> {
                dismiss();
            });

            reduce = (AppCompatImageButton) view.findViewById(R.id.reduce);//减号
            AppCompatImageButton add = (AppCompatImageButton) view.findViewById(R.id.add);//加号
            numberText = (AppCompatTextView) view.findViewById(R.id.numberText);

            numberText.setText(String.valueOf(entity.getGiving()));
            reduce.setOnClickListener(v -> {
                if (entity.getGiving() <= 0) {
                    return;
                }
                entity.setGiving(entity.getGiving() - 1);
                numberText.setText(String.valueOf(entity.getGiving()));
            });
            add.setOnClickListener(v -> {
                if (entity.getGiving() < entity.getCount()) {
                    entity.setGiving(entity.getGiving()+1);
                }
                numberText.setText(String.valueOf(entity.getGiving()));
            });

            button.setOnClickListener(v -> {
                if (buttonClick != null) {
                    buttonClick.onBtnClick(entity);
                }
                dismiss();
            });

            return dialog;

        }

        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }

    }

    public interface OnButtonClick {

        void onBtnClick(OrderDetailFood orderDetailFood);
    }


}
