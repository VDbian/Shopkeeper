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
import com.admin.shopkeeper.entity.OrderfoodEntity;


/**
 * Created by admin on 2017/6/9.
 */

public class GiveDialog extends AppCompatDialog {

    private GiveDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private GiveDialog dialog;
        private Context context;
        private int theme;

        AppCompatImageButton reduce;
        AppCompatTextView numberText;
        AppCompatButton button;
        int num = 1;

        private OnButtonClick buttonClick;


        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public OrderfoodEntity getEntity() {
            return entity;
        }

        public void setEntity(OrderfoodEntity entity) {
            this.entity = entity;
        }

        private OrderfoodEntity entity;

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public GiveDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_give, null);
            dialog = new GiveDialog(context, theme, view);

            if (entity == null) {
                return dialog;
            }

            button = (AppCompatButton) view.findViewById(R.id.OneBtn);
            button.setText("确定");

            AppCompatTextView tipBtn = (AppCompatTextView) view.findViewById(R.id.tipBtn);
            tipBtn.setOnClickListener(v -> {
                dismiss();
            });
            AppCompatTextView title = (AppCompatTextView) view.findViewById(R.id.foodName);

            if (entity.isType()) {
                title.setText(entity.getPackageName());
            } else {
                title.setText(entity.getTitle());
            }

            reduce = (AppCompatImageButton) view.findViewById(R.id.reduce);//减号
            AppCompatImageButton add = (AppCompatImageButton) view.findViewById(R.id.add);//加号
            numberText = (AppCompatTextView) view.findViewById(R.id.numberText);

            num = 1;
            if (entity.getGivingnum() > 0) {
                num = entity.getGivingnum();
            }
            numberText.setText(String.valueOf(num));
            reduce.setOnClickListener(v -> {
                if (num < 1) {
                    return;
                }
                num -= 1;
                numberText.setText(String.valueOf(num));
            });
            add.setOnClickListener(v -> {
                if (entity.isShowWeight()) {
                    num += 1;
                } else {
                    if (num < entity.getNumber()) {
                        num += 1;
                    }
                }
                numberText.setText(String.valueOf(num));
            });

            button.setOnClickListener(v -> {
                if (buttonClick != null) {
                    entity.setGivingnum(num);
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

        void onBtnClick(OrderfoodEntity entity);
    }


}
