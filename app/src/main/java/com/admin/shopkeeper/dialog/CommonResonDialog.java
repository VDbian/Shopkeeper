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

import com.admin.shopkeeper.adapter.ResaonAdapter;

import com.admin.shopkeeper.entity.RetreatReason;
import com.admin.shopkeeper.weight.MarginDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;
import timber.log.Timber;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class CommonResonDialog extends AppCompatDialog {

    public CommonResonDialog(Context context, int theme, View view) {
        super(context, theme);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(view);
    }

    public static class Builder {


        private CommonResonDialog dialog;
        private Context context;
        private int theme;
        AppCompatButton oneBtn;
        ResaonAdapter resaonAdapter;

        private String title;
        private String btnStr;
        private int max;
        private List<RetreatReason> reasons;

        public double getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public List<RetreatReason> getReasons() {
            return reasons;
        }

        public void setReasons(List<RetreatReason> reasons) {
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


        public CommonResonDialog creater() {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_reason, null);
            dialog = new CommonResonDialog(context, theme, view);

            oneBtn = (AppCompatButton) view.findViewById(R.id.OneBtn);
            AppCompatTextView titletv = (AppCompatTextView) view.findViewById(R.id.title);
            titletv.setText(title);
            AppCompatImageButton button = (AppCompatImageButton) view.findViewById(R.id.tipBtn);
            button.setOnClickListener(v -> dismiss());

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scrollView);
            AppCompatEditText editText = (AppCompatEditText) view.findViewById(R.id.editText);


            recyclerView.setLayoutManager(new GridLayoutManager(context, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            resaonAdapter = new ResaonAdapter(getReasons());
            resaonAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                @Override
                public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                    if (position == 0) {
                        return 3;
                    }
                    return 1;
                }
            });

            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(resaonAdapter);
            recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen._10sdp));
            recyclerView.setAdapter(resaonAdapter);

            resaonAdapter.setOnItemClickListener((adapter, view1, position) -> {
                if (position != 0) {
                    RetreatReason reason = resaonAdapter.getItem(position);
                    assert reason != null;
                    for (RetreatReason retreatReason : resaonAdapter.getData()) {
                        retreatReason.setSelector(false);
                    }
                    reason.setSelector(!reason.isSelector());
                    resaonAdapter.notifyDataSetChanged();
                }
            });


            oneBtn.setText(btnStr);
            oneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClick != null) {
                        String numStr = editText.getText().toString().trim();
                        if(TextUtils.isEmpty(numStr)){
                            Toasty.warning(context, "退菜数量不能为空", Toast.LENGTH_SHORT, true).show();
                            return;
                        }

                        try {
                            int num = Integer.parseInt(numStr);
                            if(num <= 0){
                                Toasty.warning(context, "请输入正确的数量", Toast.LENGTH_SHORT, true).show();
                                return;
                            }

                            if(num > getMax()){
                                Toasty.warning(context, "退菜数量不能大于菜品数量", Toast.LENGTH_SHORT, true).show();
                                return;
                            }
                            buttonClick.onBtnClick(resaonAdapter.getData(), num);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toasty.warning(context, "请输入正确的数量", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                }
            });
            return dialog;
        }

        private void changeBtn() {
//
            int c = 0;
            for (RetreatReason k : resaonAdapter.getData()) {
                if (k.isSelector()) {
                    c++;
                }
            }
            oneBtn.setEnabled(c > 0);
        }

        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    public interface OnButtonClick {

        void onBtnClick(List<RetreatReason> reasons, int v);
    }
}
