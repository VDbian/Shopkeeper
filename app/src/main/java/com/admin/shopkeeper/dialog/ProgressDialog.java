package com.admin.shopkeeper.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.admin.shopkeeper.R;

public class ProgressDialog extends android.app.ProgressDialog {
    public ProgressDialog(Context context) {
        super(context);
    }

    private String string;

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public ProgressDialog(Context context, int theme, String string) {
        super(context, theme);
        this.string = string;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
//设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        /*setCancelable(mCancelable);
        setCanceledOnTouchOutside(mCanceledOnTouchOutside);*/

        setContentView(R.layout.load_dialog);
        TextView tv_load_dialog = (TextView) findViewById(R.id.tv_load_dialog);
        if (TextUtils.isEmpty(string)) {
            tv_load_dialog.setVisibility(View.GONE);
        } else {
            tv_load_dialog.setText(string);
        }
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void setCancelable(boolean flag) {

        super.setCancelable(flag);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

}