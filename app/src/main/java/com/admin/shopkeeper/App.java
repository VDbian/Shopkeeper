package com.admin.shopkeeper;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.db.DaoManager;
import com.admin.shopkeeper.entity.ChainBean;
import com.admin.shopkeeper.entity.User;
import com.admin.shopkeeper.push.MyPushIntentService;
import com.admin.shopkeeper.utils.SPUtils;
import com.google.gson.annotations.SerializedName;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by admin on 2017/3/27.
 */

public class App extends Application {


    @SuppressLint("StaticFieldLeak")
    private static App instance;

    public static App INSTANCE() {
        return instance;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    /**
     * 收银
     */
    private String shopID;
    private User user;
    /**
     * 老板
     */
    private String shopName;
    private String shopAddress;
    private String userNameOfBoss;

    public String getUserNameOfBoss() {
        return userNameOfBoss;
    }

    public void setUserNameOfBoss(String userNameOfBoss) {
        this.userNameOfBoss = userNameOfBoss;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    private PushAgent mPushAgent;

    public static final String UPDATE_STATUS_ACTION = "com.admin.shopkeeper.action.UPDATE_STATUS";

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SpeechUtility.createUtility(this, "appid=59c1e9f6");

        if (BuildConfig.DEBUG) {
            /** 初始化日志*/
            if (BuildConfig.DEBUG) {
                Timber.plant(new Timber.DebugTree());
            }
        }

        CrashReport.initCrashReport(getApplicationContext(), "e8f2d605af", false);

        ZXingLibrary.initDisplayOpinion(this);
        sContext = getApplicationContext();
        instance = this;
        initPush();
        //初始化数据库
        DaoManager.getInstance();
    }

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static Context getAPPContext() {
        return sContext;
    }

    private void initPush() {
        mPushAgent = PushAgent.getInstance(this);
        //设置debug模式
        mPushAgent.setDebugMode(false);

        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });
        //此处是完全自定义处理设置
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);

    }

    //设置别名
    public void setAlias(String id, String type) {
        mPushAgent.addExclusiveAlias(id, type, (b, s) -> Timber.d(s + "---->" + (b ? "绑定成功" : "绑定失败")));
    }

    //移除别名
    public void removeAlias(String id, String type) {
        mPushAgent.removeAlias(id, type, (b, s) -> Log.d("---->", b ? "解绑成功" : "解绑失败"));
    }

    //关闭推送
    public void disablePush() {
        mPushAgent.disable(new IUmengCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }

    //打开推送
    public void enablePush() {
        mPushAgent.enable(new IUmengCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }

    List<ChainBean> chainBeans = new ArrayList<>();

    public List<ChainBean> getChainBeans() {
        for (ChainBean chainBean : chainBeans) {
            chainBean.setSelect(false);
        }
        if (chainBeans.size() == 0) {
            chainBeans.add(new ChainBean(shopID, shopName));
        }
        return chainBeans;
    }

    public void setChainBeans(List<ChainBean> chainBeans) {
        this.chainBeans.clear();
        this.chainBeans.addAll(chainBeans);

    }
}
