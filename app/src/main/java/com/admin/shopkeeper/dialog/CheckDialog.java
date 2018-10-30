package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.SingleDialogAdapter;
import com.admin.shopkeeper.entity.MemberBean;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class CheckDialog extends AppCompatDialog {

    public CheckDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private CheckDialog dialog;
        private Context context;
        private int theme;
        private String title;
        private OnButtonClick buttonClick;

        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public CheckDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_check, null);
            dialog = new CheckDialog(context, theme, view);

            Button oneBtn = (Button) view.findViewById(R.id.OneBtn);
            Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
            TextView titletv = (TextView) view.findViewById(R.id.dialog_title);
            EditText etScore = (EditText) view.findViewById(R.id.edit_score);

            titletv.setText(title);
            oneBtn.setOnClickListener(v -> {
                if (buttonClick != null) {
                    buttonClick.onCancel();
                }
                dismiss();
            });

            okBtn.setOnClickListener(v -> {
                if (buttonClick != null) {
                    String moneyStr = etScore.getText().toString().trim();
                    buttonClick.onOk(moneyStr);
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
        void onOk(String checkCode);

        void onCancel();
    }
}
