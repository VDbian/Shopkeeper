package com.admin.shopkeeper.ui.activity.queueedit;

import android.content.Context;
import android.text.TextUtils;

import com.admin.shopkeeper.App;
import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.R;
import com.admin.shopkeeper.base.BasePresenter;
import com.admin.shopkeeper.db.AppDbHelper;
import com.admin.shopkeeper.entity.DeskBean;
import com.admin.shopkeeper.entity.DeskTypeBean;
import com.admin.shopkeeper.entity.QueueBean;
import com.admin.shopkeeper.entity.RoomEntity;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.helper.RetrofitHelper;
import com.admin.shopkeeper.model.StringModel;
import com.admin.shopkeeper.ui.activity.activityOfBoss.weightEdit.IWeightEditView;
import com.admin.shopkeeper.utils.DialogUtils;
import com.admin.shopkeeper.utils.Print;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/24.
 */

public class QueueEditPresenter extends BasePresenter<IQueueEditView> {

    public QueueEditPresenter(Context context, IQueueEditView iView) {
        super(context, iView);
    }

    public void commit(String guid, String name, String price, String weight, int isopen, String devition, int state) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .editWeight(TextUtils.isEmpty(guid) ? "2" : "3", guid, name,
                        price, weight, isopen, devition, state, App.INSTANCE().getShopID())
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

    void getTabelDB() {
        AppDbHelper.INSTANCE().getRooms(App.INSTANCE().getShopID())
                .compose(getFragmentLifecycleProvider().<List<RoomEntity>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(roomEntities -> iView.showDB(roomEntities), Throwable::printStackTrace);
    }

    public void getTableType() {
        RetrofitHelper.getInstance()
                .getApi()
                .getDesktype("6", App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        DeskTypeBean[] printBeens = new Gson().fromJson(stringModel.getResult(), DeskTypeBean[].class);
                        iView.tableTypeSuccess(Arrays.asList(printBeens));
                    } else {
                        iView.error("加载失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("加载失败");
                });
    }

    public void getTable() {
        RetrofitHelper.getInstance()
                .getApi()
                .getTabels("0", "all", App.INSTANCE().getShopID(), 1, 1000)
                .compose(getFragmentLifecycleProvider().<StringModel>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(model -> {
                    switch (model.getCode()) {
                        case Config.REQUEST_SUCCESS:
                            TableEntity[] entities = new Gson().fromJson(model.getResult(), TableEntity[].class);
                            List<TableEntity> list = Arrays.asList(entities);
                            if (list.size() > 0) {
                                iView.tableSuccess(Arrays.asList(entities));
                            } else {
                                //iView.warning("桌台列表为空");
                            }
                            break;
                        case Config.REQUEST_FAILED:
                            iView.error(model.getMessage());
                            break;
                        case Config.REQUEST_ERROR:
                            iView.error(context.getString(R.string.string_request_error));
                            break;
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    iView.error("获取桌台列表失败");
                });
    }

    public void add(DeskTypeBean selectDeskType) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .addQueue("2", selectDeskType.getGuid(), App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        iView.success("提交成功");
                        if (!stringModel.getResult().equals("0")) {
                            printResult(stringModel.getResult());
                        }
                    } else {
                        iView.error("提交失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
                });
    }

    public void bind(QueueBean queueBean, DeskTypeBean deskType, TableEntity deskBean) {
        DialogUtils.showDialog(context, "数据提交中");
        RetrofitHelper.getInstance()
                .getApi()
                .bindQueue("3", deskType.getGuid(), App.INSTANCE().getUser().getName(),
                        App.INSTANCE().getUser().getId(), deskBean.getRoomTableID(), deskBean.getTableName(),
                        "1", queueBean.getGuid(), App.INSTANCE().getShopID())
                .compose(getActivityLifecycleProvider().bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringModel -> {
                    DialogUtils.hintDialog();
                    if (stringModel.getCode().equals("1")) {
                        if (stringModel.getResult().equals("0")) {
                            iView.error("提交失败");
                        } else if (stringModel.getResult().equals("1")) {
                            iView.success("提交成功");
                        } else {
                            iView.success("提交成功");
                            printResult(stringModel.getResult());
                        }
                    } else {
                        iView.error("提交失败");
                    }
                }, throwable -> {
                    DialogUtils.hintDialog();
                    iView.error("提交失败");
                });
    }

    private void printResult(String result) {
        new Thread(() -> Print.socketDataArrivalHandler(result)).start();
    }
}
