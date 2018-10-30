package com.admin.shopkeeper.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Administrator on 2017/6/28 0028.
 * {
 * "GUID":"0de1a360-d95f-495b-9a00-9306a5a68753",
 * "Name":"不放糖",
 * "NO":"5",
 * "PatientId":null,
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef"
 * }
 * <p>
 * "GUID":"0359f6b6-b305-4fc0-88b6-094e357f214a",
 * "ProtuctID":"d8c46756-4078-43bd-81ef-414bce4adb8f",
 * "Name":"麻辣",
 * "No":"1",
 * "RESTAURANTID":"4B176F0E-0553-4094-8181-5048641B20EF"
 */
@Entity
public class KouWei implements Serializable, MultiItemEntity {
    static final long serialVersionUID = 42L;
    @Id
    @SerializedName("GUID")
    private String guId;

    @SerializedName("Name")
    private String name;

    @SerializedName("NO")
    private String no;

    @SerializedName("PatientId")
    private String patientId;

    @SerializedName("RESTAURANTID")
    private String restaurantId;

    @SerializedName("Type")
    private boolean own;

    @Transient
    public static final int NORMAL = 0;
    @Transient
    public static final int EDIT = 1;

    public void setType(int type) {
        this.type = type;
    }

    @Transient
    private int type;


    @Transient
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Generated(hash = 2091330491)
    public KouWei(String guId, String name, String no, String patientId,
                  String restaurantId, boolean own) {
        this.guId = guId;
        this.name = name;
        this.no = no;
        this.patientId = patientId;
        this.restaurantId = restaurantId;
        this.own = own;
    }

    @Generated(hash = 326179362)
    public KouWei() {
    }

    public String getGuId() {
        return guId;
    }

    public void setGuId(String guId) {
        this.guId = guId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public boolean getOwn() {
        return this.own;
    }

    public void setOwn(boolean own) {
        this.own = own;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;
        KouWei entity = (KouWei) obj;
        return this.guId.equals(entity.guId);

    }

    @Override
    public String toString() {
        return "KouWei{" +
                "guId='" + guId + '\'' +
                ", name='" + name + '\'' +
                ", no='" + no + '\'' +
                ", patientId='" + patientId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", own=" + own +
                ", type=" + type +
                ", selected=" + selected +
                '}';
    }
}
