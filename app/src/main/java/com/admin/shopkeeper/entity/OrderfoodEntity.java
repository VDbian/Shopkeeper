package com.admin.shopkeeper.entity;

import java.util.List;

/**
 * Created by admin on 2017/6/30.
 */

public class OrderfoodEntity {
    private String foodID;//id
    private String title;
    private String unit;//单位
    private double price;//价格
    private double originalPrice;//原价
    private double memberPice;//会员价格

    public double getMemberPice() {
        return memberPice;
    }

    public void setMemberPice(double memberPice) {
        this.memberPice = memberPice;
    }

    private boolean isNumLayout;//是否显示加减号layout
    private boolean isShowWeight;//是否显示称重layout

    private List<Spec> specs;//规格列表
    private List<Season> seasons; //加料列表
    private List<KouWei> kouWeis;//口味列表
    private String btnStr;//按钮
    private String leftStr;//左边按钮
    private String rightStr;//右边按钮
    private boolean isMoreBtn;//是否是按钮组

    private int number = 1;//份数
    private int givingnum = 0;
    private double weight;//重量

    private String speced;//被选中的规格
    private String kouweied;//被选中的口味
    private String seasoned;//被选中的口味

    private boolean isEdit;//是否编辑

    private int foodPosition;//位置
    private int menuPosition;//菜品类别位置


    private boolean type;
    private String guid;
    private String packageName;
    private String counts;

    public int getGivingnum() {
        return givingnum;
    }

    public void setGivingnum(int givingnum) {
        this.givingnum = givingnum;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public String getLeftStr() {
        return leftStr;
    }

    public void setLeftStr(String leftStr) {
        this.leftStr = leftStr;
    }

    public String getRightStr() {
        return rightStr;
    }

    public void setRightStr(String rightStr) {
        this.rightStr = rightStr;
    }

    public boolean isMoreBtn() {
        return isMoreBtn;
    }

    public void setMoreBtn(boolean moreBtn) {
        isMoreBtn = moreBtn;
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setMenuPosition(int menuPosition) {
        this.menuPosition = menuPosition;
    }

    public int getFoodPosition() {
        return foodPosition;
    }

    public void setFoodPosition(int foodPosition) {
        this.foodPosition = foodPosition;
    }

    public String getSpeced() {
        return speced;
    }

    public void setSpeced(String speced) {
        this.speced = speced;
    }

    public String getKouweied() {
        return kouweied;
    }

    public void setKouweied(String kouweied) {
        this.kouweied = kouweied;
    }

    public String getSeasoned() {
        return seasoned;
    }

    public void setSeasoned(String seasoned) {
        this.seasoned = seasoned;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public int getNumber() {
        return number;
    }

    public int getAllNum() {
        return number + givingnum;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBtnStr() {
        return btnStr;
    }

    public void setBtnStr(String btnStr) {
        this.btnStr = btnStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isNumLayout() {
        return isNumLayout;
    }

    public void setNumLayout(boolean numLayout) {
        isNumLayout = numLayout;
    }

    public boolean isShowWeight() {
        return isShowWeight;
    }

    public void setShowWeight(boolean showWeight) {
        isShowWeight = showWeight;
    }

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }

    public List<KouWei> getKouWeis() {
        return kouWeis;
    }

    public void setKouWeis(List<KouWei> kouWeis) {
        this.kouWeis = kouWeis;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;
        OrderfoodEntity entity = (OrderfoodEntity) obj;
        return this.foodID.equals(entity.foodID);

    }
}
