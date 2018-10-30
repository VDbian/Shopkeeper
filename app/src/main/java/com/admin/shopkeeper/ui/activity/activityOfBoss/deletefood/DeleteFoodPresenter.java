package com.admin.shopkeeper.ui.activity.activityOfBoss.deletefood;

import android.content.Context;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.entity.FoodBean;
import com.admin.shopkeeper.entity.MealBean;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.ui.activity.activityOfBoss.setFood.ISetFoodView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class DeleteFoodPresenter extends BasePresenter<IDeleteFoodView> {


    public DeleteFoodPresenter(Context context, IDeleteFoodView iView) {
        super(context, iView);
    }

    public void getData(String type, int pageIndex, String product, String mealId) {
        RetrofitHelper.getInstance()
                .getApi()
                .getFoodOfNoBindList(type, 20, pageIndex, product, mealId, App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    if (stringModel.getCode().equals("1")) {
                        FoodBean[] foods = new Gson().fromJson(stringModel.getResult(), FoodBean[].class);
                        iView.success(Arrays.asList(foods));
                    } else {
                        iView.error("加载失败");
                    }

                }, throwable -> {
                    iView.error("加载失败");
                });
    }

    public void delete(MealBean bean) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .deleteMeal("7", bean.getId())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("删除成功");
                    } else {
                        iView.error("删除失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.printStackTrace();
                    iView.error("删除失败");
                });
    }

    public void deleteFood(String ids, MealBean mealBean) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .deleteFood("16", App.INSTANCE().getShopID(), ids)
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("删除成功");
                    } else {
                        iView.error("删除失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    throwable.printStackTrace();
                    iView.error("删除失败");
                });
    }
}
