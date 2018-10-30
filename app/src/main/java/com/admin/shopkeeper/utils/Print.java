package com.admin.shopkeeper.utils;


import android.util.Log;

import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by admin on 2017/7/7.
 */

public class Print {

    public static void rxPrint(String data) {
        Observable.just(data).map(s -> {
            socketDataArrivalHandler(s);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    public static void socketDataArrivalHandler(String data) {
        try {
            //调用打印机打印数据
            String[] enty = null;
            String[] ss = data.split("\\^");
            for (String s : ss) {
                // MessageBox.Show(s);
                enty = s.split("\\$");
                Log.d("ttt","打印字段长度length:"+enty.length);
                Timber.d(enty.length + "");
                if (enty.length >= 12) {
                    //后厨
                    if (enty[3].equals("1")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                            if (t) {
                                RetrofitHelper.getInstance()
                                        .getApi()
                                        .updatePrint("7",enty[4],enty[0])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                            if (t) {
                                RetrofitHelper.getInstance()
                                        .getApi()
                                        .updatePrint("7",enty[4],enty[0])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        }
                    }
                    //催菜
                    if (enty[3].equals("0")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                    //前台打印
                    if (enty[3].equals("2")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                    //帐单
                    if (enty[3].equals("3")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                    //外卖账单
                    if (enty[3].equals("7")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                    //快餐账单
                    if (enty[3].equals("8")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                    //退菜
                    if (enty[3].equals("4")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                    //交班单
                    if (enty[3].equals("5")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                    //外卖或者快餐
                    if (enty[3].equals("6")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                            if (t) {
                                RetrofitHelper.getInstance()
                                        .getApi()
                                        .updatePrint("7",enty[4],enty[0])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                            if (t) {
                                RetrofitHelper.getInstance()
                                        .getApi()
                                        .updatePrint("7",enty[4],enty[0])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        }
                    }
                    //排号打印
                    if (enty[3].equals("9")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                    //充值打印
                    if (enty[3].equals("10")) {
                        if (enty[10].equals("0")) {
                            PrintClass58 p = new PrintClass58();
                            boolean t = p.Print(s);
                        } else {
                            PrintClass p = new PrintClass();
                            boolean t = p.Print(s);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.d("ttt",ex.toString());
            ex.printStackTrace();
        }

    }
}
