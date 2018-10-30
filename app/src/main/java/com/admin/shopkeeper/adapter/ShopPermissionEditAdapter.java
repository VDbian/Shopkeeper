package com.admin.shopkeeper.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.widget.CompoundButton;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.entity.PermissionsOfUser;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;




public class ShopPermissionEditAdapter extends BaseQuickAdapter<PermissionsOfUser, BaseViewHolder> {

    public ShopPermissionEditAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

//    public ShopPermissionEditAdapter(@LayoutRes int layoutResId, @Nullable List<PermissionsOfUser> data) {
//        super(layoutResId, data);
//    }
//
//    public ShopPermissionEditAdapter(@LayoutRes int layoutResId) {
//        super(layoutResId, data);
//    }

    @Override
    protected void convert(BaseViewHolder helper, PermissionsOfUser item) {
        helper.setText(R.id.permission_name,item.getPermissionName());
        if(!TextUtils.isEmpty(item.getPermissionValue().trim())){
            helper.setChecked(R.id.permission_acb,true);
        }else{
            helper.setChecked(R.id.permission_acb,false);
        }
        helper.setOnCheckedChangeListener(R.id.permission_acb, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //
                    if(item.getPermissionName().equals("确认结账")){
                        item.setPermissionValue("queren");
                    }else if(item.getPermissionName().equals("确认免单")){
                        item.setPermissionValue("quanxianmiandan");
                    }else if(item.getPermissionName().equals("减免金额")){
                        item.setPermissionValue("quanxianjianmian");
                    }else if(item.getPermissionName().equals("权限打折")){
                        item.setPermissionValue("quanxiandazhe");
                    }else if(item.getPermissionName().equals("加菜")){
                        item.setPermissionValue("jiacai");
                    }else if(item.getPermissionName().equals("换桌")){
                        item.setPermissionValue("huanzhuo");
                    }else if(item.getPermissionName().equals("撤单")){
                        item.setPermissionValue("chedan");
                    }else if(item.getPermissionName().equals("并单")){
                        item.setPermissionValue("bindan");
                    }else if(item.getPermissionName().equals("后厨打印")){
                        item.setPermissionValue("houchuprint");
                    }else if(item.getPermissionName().equals("前台打印")){
                        item.setPermissionValue("qiantaiprint");
                    }else if(item.getPermissionName().equals("整单催菜")){
                        item.setPermissionValue("zhendancuicai");
                    }else if(item.getPermissionName().equals("修改人数")){
                        item.setPermissionValue("jiucancount");
                    }else if(item.getPermissionName().equals("账单结算")){
                        item.setPermissionValue("jiesuan");
                    }else if(item.getPermissionName().equals("交班打印")){
                        item.setPermissionValue("jiaoban");
                    }else if(item.getPermissionName().equals("取消结账")){
                        item.setPermissionValue("quxiaojiezhang");
                    }else if(item.getPermissionName().equals("反结账")){
                        item.setPermissionValue("fanjiezhang");
                    }else if(item.getPermissionName().equals("清台")){
                        item.setPermissionValue("qingtai");
                    }
                }else{
                    item.setPermissionValue("");
                }
            }
        });
    }


    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }


}
