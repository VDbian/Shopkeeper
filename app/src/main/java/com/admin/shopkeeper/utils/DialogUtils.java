package com.admin.shopkeeper.utils;

import android.content.Context;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.dialog.ProgressDialog;

public class DialogUtils {

    private DialogUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    private static ProgressDialog dialog;


    /**
     * 自定义主题
     *
     * @param context
     * @param theme
     */
    public static void showDialog(Context context, int theme) {
        dialog = new ProgressDialog(context, theme);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 默认主题
     *
     * @param context
     */
    public static void showDialog(Context context) {
        if (!(dialog != null && dialog.isShowing())) {
            dialog = new ProgressDialog(context, R.style.CustomDialog);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

    }

    public static void showDialog(Context context, String s) {
        if (!(dialog != null && dialog.isShowing())) {
            dialog = new ProgressDialog(context, R.style.CustomDialog, s);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    //隐藏提示框
    public static void hintDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}