package com.admin.shopkeeper.helper;


import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.api.ShopkeeperApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient;

    private RetrofitHelper() {
        initOkHttpClient();
    }

    /**
     * 静态内部类，实例化对象使用
     */
    private static class SingleRetrofitHelper {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    /**
     * 对外唯一实例的接口
     *
     * @return
     */
    public static RetrofitHelper getInstance() {
        return SingleRetrofitHelper.INSTANCE;
    }

    public ShopkeeperApi getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(ShopkeeperApi.class);
    }

    /**
     * 初始化OKHttpClient
     */
    private static void initOkHttpClient() {

        //日志过滤
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(5, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }
}
