package com.admin.shopkeeper.push;

import android.content.Context;
import android.content.Intent;

import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

public class MyPushIntentService extends UmengMessageService {
    private static final String TAG = MyPushIntentService.class.getName();

    @Override
    public void onMessage(Context context, Intent intent) {
        try {

            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);


            UMessage msg = new UMessage(new JSONObject(message));

            if (msg.display_type.equals("notification")) {
                UmLog.d(TAG, "message=" + message);    //消息体
                UmLog.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
                UmLog.d(TAG, "title=" + msg.title);    //通知标题
                UmLog.d(TAG, "text=" + msg.text);    //通知内容


                PushHelper.notify(context, msg);


            } else if (msg.display_type.equals("custom")) {
                UmLog.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            }


            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            //完全自定义消息的点击统计
            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);


        } catch (Exception e) {
            UmLog.e(TAG, e.getMessage());
        }
    }


}