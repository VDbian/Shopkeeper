package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.admin.shopkeeper.weight.MarginDecoration;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class MemberDialog extends AppCompatDialog {

    public MemberDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {
        private MemberDialog dialog;
        private Context context;
        private int theme;
        SingleDialogAdapter adapter;

        private String title;
        private MemberBean bean;

        public MemberBean getBean() {
            return bean;
        }

        public void setBean(MemberBean bean) {
            this.bean = bean;
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

        public MemberDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_member, null);
            dialog = new MemberDialog(context, theme, view);

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
            if (bean == null) {
                return dialog;
            }


            tvNo.setText("会员编号：" + bean.getNo());
            tvName.setText("会员姓名：" + bean.getName());
            tvPhone.setText("手机号码：" + bean.getPhone());
            tvScore.setText("剩余积分：" + bean.getScore());
            tvMoney.setText("会员余额：" + bean.getMoney());

            oneBtn.setOnClickListener(v -> {
                if(buttonClick != null){
                    buttonClick.onCancel();
                }
                dismiss();
            });

            okBtn.setOnClickListener(v -> {
                if (buttonClick != null) {
                    String moneyStr = etScore.getText().toString().trim();
                    if (Integer.parseInt(moneyStr) > bean.getScore()) {
                        Toasty.warning(context, "输入积分大于剩余积分", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    buttonClick.onOk(bean, moneyStr);
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
        void onOk(MemberBean bean, String money);
        void onCancel();
    }
}
