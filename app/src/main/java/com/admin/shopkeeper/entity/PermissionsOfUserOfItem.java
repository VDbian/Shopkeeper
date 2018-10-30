package com.admin.shopkeeper.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PermissionsOfUserOfItem implements Serializable {

    @SerializedName("PermissionName")
    private String permissionName;
    @SerializedName("PermissionValue")
    private String permissionValue;

    public PermissionsOfUserOfItem() {
    }

    public PermissionsOfUserOfItem(String permissionName, String permissionValue) {
        this.permissionName = permissionName;
        this.permissionValue = permissionValue;
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


}
