package com.admin.shopkeeper.utils;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.shopkeeper.R;

/**
 * Created by Administrator on 2017/8/27.
 */

public class ToastUtils {

    public static final int TOAST_SUCCESS = 1;
    public static final int TOAST_FAILE = 2;
    public static final int TOAST_TIPS = 3;

    public static Toast toast;

    /**
     * @param context
     * @param text
     * @param type 1:成功 2：失败 3：提示
     */
    public static void showToast(Context context, String text, int type) {
        show(context, text, type, Toast.LENGTH_SHORT);
    }

    private static void show(Context context, String text, int type, int duratio) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast, null);
        TextView t = (TextView) view.findViewById(R.id.toast_msg);
        ImageView imageView = (ImageView) view.findViewById(R.id.toast_image);
        t.setText(text);
        if (type == TOAST_SUCCESS) {
            imageView.setImageResource(R.mipmap.toast_success);
        } else if (type == TOAST_FAILE) {
            imageView.setImageResource(R.mipmap.toast_fail);
        } else {
            imageView.setImageResource(R.mipmap.toast_tips);
        }
        view.setBackgroundResource(R.drawable.bg_toast);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setDuration(duratio);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }
}
