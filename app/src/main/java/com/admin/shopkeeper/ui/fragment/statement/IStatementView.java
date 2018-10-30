package com.admin.shopkeeper.ui.fragment.statement;

import com.admin.shopkeeper.base.IBaseView;
import com.admin.shopkeeper.entity.ChainBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface IStatementView extends IBaseView {
    void chainsuccess(List<ChainBean> chainBeans);
}
