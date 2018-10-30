package com.admin.shopkeeper.ui.activity.activityOfBoss.mealEdit;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.MealTypeBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
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

public class MealEditPresenter extends BasePresenter<IMealEditView> {

    public MealEditPresenter(Context context, IMealEditView iView) {
        super(context, iView);
    }

    public void submit(String types, String typeId, String name,
                        double price,  int status,
                       String remark, double memberPice, int canDiscount, String imageName) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addMeal("5",App.INSTANCE().getShopID(), name,
                        types, typeId, price, status, remark, memberPice,
                       canDiscount ,imageName)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
//                        if (TextUtils.isEmpty(productId)) {
                            iView.success("添加成功");
//                        } else {
//                            iView.success("修改成功");
//                        }
                    } else {
                        iView.error("提交失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
                });
    }
    public void change(String mealID , String types, String typeId, String name,
                       double price,  int status,
                       String remark, double memberPice, int canDiscount, String imageName) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .changeMeal("6",mealID, name,
                        types, typeId, price, status, remark, memberPice,
                        canDiscount ,imageName)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
//                        if (TextUtils.isEmpty(productId)) {
                        iView.success("修改成功");
//                        } else {
//                            iView.success("修改成功");
//                        }
                    } else {
                        iView.error("提交失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
                });
    }

    public void editFood(String imagePath) {


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
        map.put("type", getValue("1"));

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
    public void getMealType() {
        DialogUtils.showDialog(context, "数据加载中");
        RetrofitHelper.getInstance()
                .getApi()
                .getMealsList("12", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        MealTypeBean[] foods = new Gson().fromJson(stringModel.getResult(), MealTypeBean[].class);
                        iView.successOfGetType(Arrays.asList(foods));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });

    }

}
