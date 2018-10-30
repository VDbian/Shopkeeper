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

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.SingleDialogAdapter;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class CardDialog extends AppCompatDialog {

    public CardDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private CardDialog dialog;
        private Context context;
        private int theme;
        SingleDialogAdapter adapter;

        private String title;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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

        public CardDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_member, null);
            dialog = new CardDialog(context, theme, view);

            Button oneBtn = (Button) view.findViewById(R.id.OneBtn);
            Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
            TextView titletv = (TextView) view.findViewById(R.id.dialog_title);
            TextView tvNo = (TextView) view.findViewById(R.id.text_no);
            TextView tvName = (TextView) view.findViewById(R.id.text_name);
            TextView tvPhone = (TextView) view.findViewById(R.id.text_phone);
            TextView tvScore = (TextView) view.findViewById(R.id.text_remain_score);
            TextView tvMoney = (TextView) view.findViewById(R.id.text_remain_money);
            EditText etScore = (EditText) view.findViewById(R.id.edit_score);

            titletv.setText(title);
            if (TextUtils.isEmpty(content)) {
                return dialog;
            }

            //王月@17778063182@170912111453435214@500@495.00@947230e5-a8dc-4c22-8e31-215b33a375c8
            String[] split = content.split("@");
            if (split.length < 6) {
                return dialog;
            }
            tvNo.setText(split[2]);
            tvName.setText(split[0]);
            tvPhone.setText(split[1]);
            tvScore.setText(split[3]);
            tvMoney.setText(split[4]);

            oneBtn.setOnClickListener(v -> {
                dismiss();
            });

            okBtn.setOnClickListener(v -> {
                if (buttonClick != null) {
                    String moneyStr = etScore.getText().toString().trim();
                    buttonClick.onOk(content, moneyStr);
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
        void onOk(String text, String money);
    }
}
