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
import com.admin.shopkeeper.entity.DaZheEntity;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class JianmianDialog extends AppCompatDialog {

    public JianmianDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {


        private JianmianDialog dialog;
        private Context context;
        private int theme;

        private String title;
        private String btnStr;
        String hintStr;
        private double max;
        private List<DaZheEntity> reasons;

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public List<DaZheEntity> getReasons() {
            return reasons;
        }

        public void setReasons(List<DaZheEntity> reasons) {
            this.reasons = reasons;
        }

        private OnButtonClick buttonClick;

        public void setButtonClick(OnButtonClick buttonClick) {
            this.buttonClick = buttonClick;
        }

        public Builder(Context context, int theme) {
            this.context = context;
            this.theme = theme;
        }

        public String getHintStr() {
            return hintStr;
        }

        public void setHintStr(String hintStr) {
            this.hintStr = hintStr;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBtnStr() {
            return btnStr;
        }

        public void setBtnStr(String btnStr) {
            this.btnStr = btnStr;
        }


        public JianmianDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_jianmian, null);
            dialog = new JianmianDialog(context, theme, view);

            EditText editText = (EditText) view.findViewById(R.id.editText);
            TextView textView1 = (TextView) view.findViewById(R.id.tv_1);
            TextView textView2 = (TextView) view.findViewById(R.id.tv_2);
            TextView textView3 = (TextView) view.findViewById(R.id.tv_3);
            TextView textView4 = (TextView) view.findViewById(R.id.tv_4);

            Button btCancel = (Button) view.findViewById(R.id.btn_cancel);
            Button btOk = (Button) view.findViewById(R.id.OneBtn);

            if(!TextUtils.isEmpty(hintStr)){
                editText.setHint(hintStr);
            }

            btCancel.setOnClickListener(v -> {
                buttonClick.onCancel();
                dismiss();
            });

            textView1.setOnClickListener(view1 -> {
                buttonClick.onItemClick(1);
                dismiss();
            });
            textView2.setOnClickListener(view1 -> {
                buttonClick.onItemClick(2);
                dismiss();
            });
            textView3.setOnClickListener(view1 -> {
                buttonClick.onItemClick(3);
                dismiss();
            });
            textView4.setOnClickListener(view1 -> {
                buttonClick.onItemClick(4);
                dismiss();
            });

            btOk.setOnClickListener(view1 -> {
                if (buttonClick != null) {
                    if (TextUtils.isEmpty(editText.getText())) {
                        Toasty.warning(context, "请输入减免金额", Toast.LENGTH_SHORT, true).show();
                        return;
                    }

                    double i = Double.parseDouble(editText.getText().toString().trim());
                    if(i < 0){
                        Toasty.warning(context, "输入错误", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    buttonClick.onBtnClick(i);
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

        void onBtnClick(double i);

        void onItemClick(int i);

        void onCancel();
    }
}
