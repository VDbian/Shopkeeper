package com.admin.shopkeeper.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import android.widget.TextView;

import com.admin.shopkeeper.R;

public class UIUtils {
    public static int dpToPixels(Context c, int dp) {
        return (int) (c.getResources().getDisplayMetrics().density * dp);
    }

    public static int pixelsToDp(Context c, int pixel) {
        return (int) ((float) pixel / c.getResources().getDisplayMetrics().density);
    }

    public static void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        context.getWindow().setAttributes(lp);
    }

    public static void setDrawableTop(TextView textView, int drawable) {
        Drawable top = textView.getContext().getResources().getDrawable(drawable);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
    }

    public static void setDrawableRight(TextView textView, int drawable) {
        Drawable top = textView.getContext().getResources().getDrawable(drawable);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, top, null);
    }

    public static void setNullDrawable(TextView textView) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }
}