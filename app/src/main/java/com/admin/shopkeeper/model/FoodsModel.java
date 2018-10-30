package com.admin.shopkeeper.model;

import com.admin.shopkeeper.base.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/6/27.
 */

public class FoodsModel extends BaseModel {


    private Result result;

    public Result getResuit() {
        return result;
    }

    public String getFood() {
        return getResuit().getFood();
    }

    public void setResuit(Result result) {
        this.result = result;
    }

    public String getSpec() {
        return getResuit().getSpec();
    }

    public String getKouWei() {
        return getResuit().getKouWei();
    }

    public String getSeason() {
        return getResuit().getSeason();
    }

    public String getFoodType() {
        return getResuit().getFoodType();
    }

    public String getProductKouWei() {
        return getResuit().getProductKouWei();
    }

    class Result {

        private String food;

        @SerializedName("Spec")
        private String spec;

        @SerializedName("Kouwei")
        private String kouWei;

        @SerializedName("Season")
        private String season;

        @SerializedName("foodType")
        private String foodType;

        @SerializedName("ProductKouWei")
        private String productKouWei;

        public String getProductKouWei() {
            return productKouWei;
        }

        public void setProductKouWei(String productKouWei) {
            this.productKouWei = productKouWei;
        }

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getKouWei() {
            return kouWei;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public String getFoodType() {
            return foodType;
        }

        public void setFoodType(String foodType) {
            this.foodType = foodType;
        }

        public void setKouWei(String kouWei) {
            this.kouWei = kouWei;
        }
    }
}
