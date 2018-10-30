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
import com.admin.shopkeeper.entity.DaZheEntity;
import com.admin.shopkeeper.weight.MarginDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class DaZheDialog extends AppCompatDialog {

    public DaZheDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {


        private DaZheDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;
        AppCompatButton cancelBtn;
        DaZhaAdapter daZhaAdapter;

        private String title;
        private String btnStr;
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


        public DaZheDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_da_zhe, null);
            dialog = new DaZheDialog(context, theme, view);

            oneBtn = (AppCompatButton) view.findViewById(R.id.OneBtn);
            cancelBtn = (AppCompatButton) view.findViewById(R.id.btn_cancel);
            AppCompatTextView titletv = (AppCompatTextView) view.findViewById(R.id.title);
            titletv.setText(title);
            AppCompatImageButton button = (AppCompatImageButton) view.findViewById(R.id.tipBtn);
            button.setOnClickListener(v -> dismiss());

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);
            AppCompatEditText editText = (AppCompatEditText) view.findViewById(R.id.editText);
            oneBtn.setText(btnStr);
            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClick != null) {
                        if (TextUtils.isEmpty(editText.getText())) {
                            Toasty.warning(context, "打折数不为空", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if (Integer.parseInt(editText.getText().toString().trim()) < 1 || Integer.parseInt(editText.getText().toString().trim()) > 99) {
                            Toasty.warning(context, "请输入正确的打折数", Toast.LENGTH_SHORT, true).show();
                            return;
                        }

                        buttonClick.onBtnClick(Integer.parseInt(editText.getText().toString().trim()));
                    }
                }
            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClick != null) {
                        buttonClick.onCancel();
                        dismiss();
                    }
                }
            });


            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            daZhaAdapter = new DaZhaAdapter(R.layout.item_da_zhe_dialog, getReasons());
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(daZhaAdapter);

            daZhaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (buttonClick != null) {
                        buttonClick.onItemClick(Integer.parseInt(daZhaAdapter.getData().get(position).getCount()),daZhaAdapter.getData().get(position).getGuid());
                    }
                }
            });


            oneBtn.setText(btnStr);


//            changeBtn();
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

        void onItemClick(int i , String id);

        void onCancel();
    }
}
