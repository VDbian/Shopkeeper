package com.admin.shopkeeper.push;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.ui.activity.orderList.OrderListActivity;
import com.admin.shopkeeper.utils.NotifyUtils;
import com.admin.shopkeeper.utils.Print;
import com.umeng.message.entity.UMessage;

import org.json.JSONObject;


public class PushHelper {


    public static void notify(Context context, UMessage msg) {


        JSONObject jsonObject = new JSONObject(msg.extra);

        String type = jsonObject.optString("TYPE");

        if (type.equals("0")) {
            Intent intent = new Intent(context, OrderListActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pIntent = PendingIntent.getActivity(context,
                    1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notify(pIntent, 1, context, R.mipmap.ic_launcher, msg.title, msg.text, msg.ticker);
        } else if (type.equals("1")) {
            new Thread(() -> {
                try {

                    JSONObject jsonObject1 = new JSONObject(msg.extra);
                    Print.socketDataArrivalHandler(jsonObject1.optString("KEY"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }


    private static void notify(PendingIntent pIntent, int type, Context mContext, int icon, String title, String content, String ticker) {

        //实例化工具类，并且调用接口
        NotifyUtils notify = new NotifyUtils(mContext, type);
        notify.notify_normal_singline(pIntent, icon, ticker, title, content, false, true, true);
    }

}