package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户权限管理
 {\"Guid\":\"a25cf234-fb3f-46c7-942e-021a1efb76d2\",
 \"Userid\":\"4399ade8-1d91-4e8d-8143-654e59a8a9e1\"
 ,\"PermissionID\":\"85cba6c8-1d98-48ff-a83b-616f70b8e594\"
 ,\"PermissionName\":\"确认结账,确认免单,减免金额,权限打折,取消结账,反结账\"
 ,\"PermissionValue\":\"queren,quanxianmiandan,quanxianjianmian,quanxiandazhe,quxiaojiezhang,fanjiezhang\",
 \"Name\":\"结账\",\"PermissionUrl\":\"BillManagerNwe.aspx\"},
 {\"Guid\":\"40ef1ec8-5433-460c-9304-fcca87e0d64c\",\"Userid\":\"4399ade8-1d91-4e8d-8143-654e59a8a9e1\",\"PermissionID\":\"3cc8cb20-a156-479a-9113-6ba3254a1a5e\",\"PermissionName\":\"加菜,换桌,撤单,并单,后厨打印,前台打印,整单催菜,修改人数,账单结算,交班打印\",\"PermissionValue\":\"jiacai,huanzhuo,chedan,bindan,houchuprint,qiantaiprint,zhendancuicai,jiucancount,jiesuan,jiaoban\",\"Name\":\"开单\",\"PermissionUrl\":\"KaiDanNew.aspx\"}
 */

public class PermissionsOfUser implements Serializable {
    @SerializedName("Guid")
    private String guId;
    @SerializedName("Userid")
    private String userID;
    @SerializedName("PermissionID")
    private String permissionID;
    @SerializedName("PermissionName")
    private String permissionName;
    @SerializedName("PermissionValue")
    private String permissionValue;
    @SerializedName("Name")
    private String name;
    @SerializedName("PermissionUrl")
    private String permissionUrl;

    public PermissionsOfUser() {
    }

    public PermissionsOfUser(String guId, String userID, String permissionID, String permissionName, String permissionValue, String name, String permissionUrl) {
        this.guId = guId;
        this.userID = userID;
        this.permissionID = permissionID;
        this.permissionName = permissionName;
        this.permissionValue = permissionValue;
        this.name = name;
        this.permissionUrl = permissionUrl;
    }

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(String permissionID) {
        this.permissionID = permissionID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }
}
