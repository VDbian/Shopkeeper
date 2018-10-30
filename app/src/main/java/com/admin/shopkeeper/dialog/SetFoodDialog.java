package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.DaZhaAdapter;
import com.admin.shopkeeper.utils.Tools;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class SetFoodDialog extends AppCompatDialog {

    public SetFoodDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {


        private SetFoodDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;
        DaZhaAdapter daZhaAdapter;

        private String title;
        private String name;


        private OnButtonClick buttonClick;

        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public SetFoodDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_setfood, null);
            dialog = new SetFoodDialog(context, theme, view);

            oneBtn = (AppCompatButton) view.findViewById(R.id.OneBtn);
            AppCompatButton btnCancel = (AppCompatButton) view.findViewById(R.id.btn_cancel);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            EditText editText = (EditText) view.findViewById(R.id.editText);
            TextView tvTitle = (TextView) view.findViewById(R.id.title);

            tvTitle.setText("套餐数量添加");
            oneBtn.setText("确定");
            btnCancel.setText("取消");
            editText.setText("1");

            tvName.setText(name);

            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tools.hideSoftKeyboard(context, editText);
                    if (buttonClick != null) {
                        if (TextUtils.isEmpty(editText.getText())) {
                            Toasty.warning(context, "数量不为空", Toast.LENGTH_SHORT, true).show();
                            return;
                        }

                        buttonClick.onBtnClick(Integer.parseInt(editText.getText().toString().trim()));
                        dialog.dismiss();
                    }
                }
            });

            view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
                Tools.hideSoftKeyboard(context, editText);
                dialog.dismiss();
                buttonClick.onCancel();
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

        void onBtnClick(int i);

        void onCancel();
    }
}
