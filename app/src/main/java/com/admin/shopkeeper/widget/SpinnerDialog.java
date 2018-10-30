package com.admin.shopkeeper.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.admin.shopkeeper.R;

/**
 * Created by Administrator on 2018/5/21.
 */

public class SpinnerDialog extends Dialog {
    public SpinnerDialog(Context context) {
        super(context, R.style.OrderDialogStyle);
        setContentView(R.layout.spinner);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        params.height = (int) (screenHeigh * 0.8);
        params.width = (int) (screenWidth * 0.8);
        window.setAttributes(params);
    }
}