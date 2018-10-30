package com.admin.shopkeeper.ui.activity.activityOfBoss.basicSets;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.BasicSetBean;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.utils.DeviceUtils;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BasicSetsPresenter extends BasePresenter<IBasicSetsView> {

    public BasicSetsPresenter(Context context, IBasicSetsView iView) {
        super(context, iView);
    }

    public void getBasicSets() {
        DialogUtils.showDialog(context, "查询信息...");
        RetrofitHelper.getInstance()
                .getApi()
                .queryBasicSets("2", App.INSTANCE().getShopID())
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        BasicSetBean bean = new Gson().fromJson(stringModel.getResult(), BasicSetBean.class);
                        iView.success(bean);
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                });

    }

    public void commit(String payImage, String printSet, String productSize, String payType, String chengjindazhe,
                       String jiezhangPay, String guestShow, String password, String payTypeValue, String memberComUse, String unitePay ,String numberingrules , String countCoding ,String foodId , String foodName ) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .editBasicSet("1", payImage, printSet, productSize, payType, chengjindazhe, jiezhangPay, guestShow, password, payTypeValue, App.INSTANCE().getShopID(), memberComUse, unitePay ,numberingrules ,countCoding,foodId,foodName)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("提交成功");
                    } else {
                        iView.error("提交失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
                });
    }

    public void uploadImage(String imagePath) {
        File file = new File(imagePath);
        if (!file.exists()) {
            iView.error("提交失败");
            return;
        }

        List<MultipartBody.Part> images = new ArrayList<>();
        HashMap<String, RequestBody> map = new HashMap<>();

        String path = DeviceUtils.getimage(imagePath, 500);
        File imageFile = new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("FileData", imageFile.getName(), requestFile);
        images.add(body);

        map.put("RESTAURANTID", getValue(App.INSTANCE().getShopID()));
        map.put("type", getValue("2"));

        RetrofitHelper.getInstance()
                .getApi()
                .uploadImage(map, images)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.imagesuccess(imageFile.getName());
                    } else {
                        iView.error("提交失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
                });
    }

    private RequestBody getValue(String value) {
        return RequestBody.create(
                MediaType.parse("text/plain"), value);
    }
    public void getFood() {
        RetrofitHelper.getInstance()
                .getApi()
                .getFoodsList("1", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        FoodBean[] foods = new Gson().fromJson(stringModel.getResult(), FoodBean[].class);
                        iView.getFoodSuccess(Arrays.asList(foods));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    iView.error("加载失败");
                });

    }
}
