package com.admin.shopkeeper.entity;

import java.util.List;


public class PermissionJson {

    public static class Permissions {
        private List<PermissionJsonBase> permission;

        public List<PermissionJsonBase> getPermission() {
            return permission;
        }

        public void setPermission(List<PermissionJsonBase> permissions) {
            this.permission = permissions;
        }
    }

    public static class PermissionJsonBase {
        private String permissionName;
        private String permissionValue;
        private String permissionID;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String permissionUrl;

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

        public String getPermissionID() {
            return permissionID;
        }

        public void setPermissionID(String permissionID) {
            this.permissionID = permissionID;
        }

        public String getPermissionUrl() {
            return permissionUrl;
        }

        public void setPermissionUrl(String permissionUrl) {
            this.permissionUrl = permissionUrl;
        }
    }
}
