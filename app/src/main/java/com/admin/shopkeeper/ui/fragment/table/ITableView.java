package com.admin.shopkeeper.ui.fragment.table;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.CommonDialogEntity;
import com.admin.shopkeeper.entity.Order;
import com.admin.shopkeeper.entity.OrderDetailFood;
import com.admin.shopkeeper.entity.TableEntity;
import com.admin.shopkeeper.entity.WeixinOrderBean;

import java.util.List;

/**
 * Created by admin on 2017/4/20.
 */

public interface ITableView extends IBaseView {
    void error(String message);

    void showTable(List<TableEntity> list);

    void showTable(TableEntity entity, int position);

    void warning(String s);


    void success(boolean b, int position, String result, CommonDialogEntity entity);

    void success(List<WeixinOrderBean> weixinOrderBeen);

    void fail();

    void changeTableSuccess(int position, String tableName, String tableId);

    void qingtaiSuccess(int position);

    void trunSuccess();

    void orderSuccess(Order order, List<OrderDetailFood> orderDetailFoods, int position);

    void undoSuccess(int position);

    void kuaisuSuccess(TableEntity entity, String s, double monery);

    void bindSuccess(String tableId, String tableName);

    void orderListSuccess(Order order, List<OrderDetailFood> orderDetailFoods);

    void inBillSuccess(Order order, List<OrderDetailFood> orderDetailFoods, int position , boolean isScanBill);

    void cancelSuccess();

    void showCancelDialog(Order order);

    void scanBillSuccess(String payType,String result,double money , String memberId ,String str);

    void billSuccess(String msg,String result);
}

