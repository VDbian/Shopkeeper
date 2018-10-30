package com.admin.shopkeeper.base;

/**
 * Created by Administrator on 2017/6/15 0015.
 * {"code":"0","result":"","message":""}
 */

public class BaseModel {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
