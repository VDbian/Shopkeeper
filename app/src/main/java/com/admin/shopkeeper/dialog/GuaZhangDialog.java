package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.adapter.DaZhaAdapter;
import com.admin.shopkeeper.adapter.GuaZhangAdapter;
import com.admin.shopkeeper.entity.DaZheEntity;
import com.admin.shopkeeper.entity.GuaZhangBean;
import com.admin.shopkeeper.weight.MarginDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class GuaZhangDialog extends AppCompatDialog {

    public GuaZhangDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {

        private GuaZhangDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;

        private String title;
        private String btnStr;
        private double max;
        private List<GuaZhangBean> reasons;
        private GuaZhangAdapter adapter;

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public List<GuaZhangBean> getReasons() {
            return reasons;
        }

        public void setReasons(List<GuaZhangBean> reasons) {
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

        String hint;

        public void setHint(String hint) {
            this.hint = hint;
        }

        String text;

        public void setText(String text) {
            this.text = text;
        }


        public GuaZhangDialog creater() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_guazhang, null);
            dialog = new GuaZhangDialog(context, theme, view);

            oneBtn = (AppCompatButton) view.findViewById(R.id.OneBtn);
            AppCompatTextView titletv = (AppCompatTextView) view.findViewById(R.id.title);
            titletv.setText(title);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);
            AppCompatEditText editText = (AppCompatEditText) view.findViewById(R.id.editText);
            AppCompatButton btnCancel = (AppCompatButton) view.findViewById(R.id.btn_cancel);
            btnCancel.setText("取消");
            oneBtn.setText(btnStr);
            editText.setHint(hint);
            editText.setText(text);
            editText.setSelection(editText.length());
            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClick != null) {
                        String str = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(str)) {
                            Toasty.warning(context, "请输入挂账金额", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        GuaZhangBean guaZhangBean = null;
                        for (int i = 0; i < reasons.size(); i++) {
                            if (reasons.get(i).isSelected()) {
                                guaZhangBean = reasons.get(i);
                            }
                        }
                        if (guaZhangBean == null) {
                            Toasty.warning(context, "请选择挂账人", Toast.LENGTH_SHORT, true).show();
                            return;
                        }

                        buttonClick.onBtnClick(Double.parseDouble(str), guaZhangBean);
                    }
                }
            });
            btnCancel.setOnClickListener(v -> {
                buttonClick.onCancel();
                dismiss();
            });


            recyclerView.setLayoutManager(new GridLayoutManager(context, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            adapter = new GuaZhangAdapter(R.layout.item_da_zhe_dialog, getReasons());
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
//                    if (buttonClick != null) {
//                        buttonClick.onItemClick(adapter.getData().get(position));
//                    }
                    List<GuaZhangBean> data = adapter.getData();
                    for (int i = 0; i > data.size(); i++) {
                        data.get(i).setSelected(false);
                    }
                    data.get(position).setSelected(true);
                    adapter.notifyDataSetChanged();
                }
            });

            oneBtn.setText(btnStr);
            return dialog;
        }


        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    public interface OnButtonClick {

        void onBtnClick(double money, GuaZhangBean bean);

        void onCancel();
    }
}
