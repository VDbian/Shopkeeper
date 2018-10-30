package com.admin.shopkeeper.entity;

import android.text.TextUtils;
import android.util.Log;

import com.admin.shopkeeper.R;
import com.admin.shopkeeper.utils.StringUtils;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

import com.admin.greendao.gen.DaoSession;
import com.admin.greendao.gen.SpecDao;
import com.admin.greendao.gen.MenuTypeEntityDao;
import com.admin.greendao.gen.FoodEntityDao;
import com.admin.greendao.gen.KouWeiDao;

/**
 * Created by admin on 2017/6/22.
 * {
 * "PRODUCTID":"01444b32-fa76-484f-bcf6-f1a1c6489521",
 * "RESTAURANTID":"4b176f0e-0553-4094-8181-5048641b20ef",
 * "ID":"726",
 * "PRODUCTNAME":"炝炒小白菜",
 * "PINYIN":"qcxbc",
 * "UNIT":"份",
 * "MINUNIT":"1",
 * "PRODUCTTYPEID":"f4dfa854-4eee-43b7-b8f7-12c55c4535a6",
 * "PRODUCTTYPENAME":"川式家常菜",
 * "PRICE":"12",
 * "PRODUCTFile":"炝炒小白菜.jpg",
 * "PRODUCTIMAGE":null,
 * "STATE":1,
 * "REMARK":"",
 * "TasteID":"0de1a360-d95f-495b-9a00-9306a5a68753",
 * "IsDaZhe":null,
 * "DaZhe":null,
 * "WarCount":"0",
 * "IsClose":"0",
 * "IsCloseName":null,
 * "ProductCount":100000,
 * "ChuCaiType":"0",
 * "CanDiscount":1,
 * "MemberPice":12,
 * "SalesType":1,
 * "AccordIng":"0",
 * "ProtuctShuXing":"2",
 * "ProductGive":"0"
 * TasteType    1。弹出口味   0.不弹出口
 * <p>
 * }
 */
@Entity
public class FoodEntity implements Serializable {
    static final long serialVersionUID = 42L;

    @Id
    @SerializedName("PRODUCTID")
    private String productID;

    @ToOne(joinProperty = "productTypeID")
    private MenuTypeEntity menuType;

    @ToMany(referencedJoinProperty = "protuctID")
    private List<Spec> specList;

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    @SerializedName("RESTAURANTID")
    private String restaurantID;

    @SerializedName("ID")
    private String id;

    @SerializedName("PRODUCTNAME")
    private String productName;

    @SerializedName("PINYIN")
    private String pinYin;

    @SerializedName("UNIT")
    private String unit;

    @SerializedName("MINUNIT")
    private String minUnit;

    @SerializedName("PRODUCTTYPEID")
    private String productTypeID;

    @SerializedName("PRODUCTTYPENAME")
    private String productTypeName;

    @SerializedName(value = "PRICE", alternate = {"Price"})
    private double price;

    @SerializedName("PRODUCTFile")
    private String productFile;

    @SerializedName("PRODUCTIMAGE")
    private String productImage;

    @SerializedName("STATE")
    private int state;

    @SerializedName("REMARK")
    private String remark;

    @SerializedName("TasteID")
    private String tasteID;

    @SerializedName("IsDaZhe")
    private String daZheIs;

    @SerializedName("DaZhe")
    private String daZhe;

    @SerializedName("WarCount")
    private String warCount;

    @SerializedName("IsClose")
    private String closeIs;

    @SerializedName("IsCloseName")
    private String closeNameIs;

    @SerializedName("ProductCount")
    private int productCount;

    @SerializedName("ChuCaiType")
    private String chuCaiType;

    @SerializedName("CanDiscount")
    private int canDiscount;

    @SerializedName("MemberPice")
    private double memberPice;

    @SerializedName("SalesType")
    private int salesType;

    @SerializedName("AccordIng")
    private String accordIng;

    @SerializedName("ProtuctShuXing")
    private String productShuXing;

    @SerializedName("ProductGive")
    private String productGive;

    @SerializedName("TasteType")
    private String tasteType;

    @SerializedName("Type")
    private boolean type;

    @SerializedName("Guid")
    private String guid;

    @SerializedName("PackageName")
    private String packageName;

    @SerializedName("counts")
    private String counts;

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

    public void setType(boolean type) {
        this.type = type;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTasteType() {
        return tasteType;
    }


    public void setTasteType(String tasteType) {
        this.tasteType = tasteType;
    }

    @Transient
    private int number;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 587034604)
    private transient FoodEntityDao myDao;

    @Generated(hash = 1350502118)
    public FoodEntity(String productID, String restaurantID, String id, String productName, String pinYin, String unit, String minUnit,
            String productTypeID, String productTypeName, double price, String productFile, String productImage, int state, String remark,
            String tasteID, String daZheIs, String daZhe, String warCount, String closeIs, String closeNameIs, int productCount,
            String chuCaiType, int canDiscount, double memberPice, int salesType, String accordIng, String productShuXing, String productGive,
            String tasteType, boolean type, String guid, String packageName, String counts) {
        this.productID = productID;
        this.restaurantID = restaurantID;
        this.id = id;
        this.productName = productName;
        this.pinYin = pinYin;
        this.unit = unit;
        this.minUnit = minUnit;
        this.productTypeID = productTypeID;
        this.productTypeName = productTypeName;
        this.price = price;
        this.productFile = productFile;
        this.productImage = productImage;
        this.state = state;
        this.remark = remark;
        this.tasteID = tasteID;
        this.daZheIs = daZheIs;
        this.daZhe = daZhe;
        this.warCount = warCount;
        this.closeIs = closeIs;
        this.closeNameIs = closeNameIs;
        this.productCount = productCount;
        this.chuCaiType = chuCaiType;
        this.canDiscount = canDiscount;
        this.memberPice = memberPice;
        this.salesType = salesType;
        this.accordIng = accordIng;
        this.productShuXing = productShuXing;
        this.productGive = productGive;
        this.tasteType = tasteType;
        this.type = type;
        this.guid = guid;
        this.packageName = packageName;
        this.counts = counts;
    }

    @Generated(hash = 2051124127)
    public FoodEntity() {
    }

    @Generated(hash = 1300405910)
    private transient String menuType__resolvedKey;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPinYin() {
        if (!TextUtils.isEmpty(pinYin)) {
            pinYin = pinYin.replace(productName.trim(), "");
        }
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMinUnit() {
        return minUnit;
    }

    public void setMinUnit(String minUnit) {
        this.minUnit = minUnit;
    }

    public String getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(String productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductFile() {
        return productFile;
    }

    public void setProductFile(String productFile) {
        this.productFile = productFile;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTasteID() {
        return tasteID;
    }

    public void setTasteID(String tasteID) {
        this.tasteID = tasteID;
    }

    public String getDaZheIs() {
        return daZheIs;
    }

    public void setDaZheIs(String daZheIs) {
        this.daZheIs = daZheIs;
    }

    public String getDaZhe() {
        return daZhe;
    }

    public void setDaZhe(String daZhe) {
        this.daZhe = daZhe;
    }

    public String getWarCount() {
        return warCount;
    }

    public void setWarCount(String warCount) {
        this.warCount = warCount;
    }

    public String getCloseIs() {
        return closeIs;
    }

    public void setCloseIs(String closeIs) {
        this.closeIs = closeIs;
    }

    public String getCloseNameIs() {
        return closeNameIs;
    }

    public void setCloseNameIs(String closeNameIs) {
        this.closeNameIs = closeNameIs;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getChuCaiType() {
        return chuCaiType;
    }

    public void setChuCaiType(String chuCaiType) {
        this.chuCaiType = chuCaiType;
    }

    public int getCanDiscount() {
        return canDiscount;
    }

    public void setCanDiscount(int canDiscount) {
        this.canDiscount = canDiscount;
    }

    public double getMemberPice() {
        return memberPice;
    }

    public void setMemberPice(double memberPice) {
        this.memberPice = memberPice;
    }

    public int getSalesType() {
        return salesType;
    }

    public void setSalesType(int salesType) {
        this.salesType = salesType;
    }

    public String getAccordIng() {
        return accordIng;
    }

    public void setAccordIng(String accordIng) {
        this.accordIng = accordIng;
    }

    public String getProductShuXing() {
        return productShuXing;
    }

    public void setProductShuXing(String productShuXing) {
        this.productShuXing = productShuXing;
    }

    public String getProductGive() {
        return productGive;
    }

    public void setProductGive(String productGive) {
        this.productGive = productGive;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1839168031)
    public synchronized void resetSpecList() {
        specList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 211642632)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFoodEntityDao() : null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 84790525)
    public List<Spec> getSpecList() {
        if (specList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SpecDao targetDao = daoSession.getSpecDao();
            List<Spec> specListNew = targetDao._queryFoodEntity_SpecList(productID);
            synchronized (this) {
                if (specList == null) {
                    specList = specListNew;
                }
            }
        }
        return specList;
    }


    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 281120431)
    public MenuTypeEntity getMenuType() {
        String __key = this.productTypeID;
        if (menuType__resolvedKey == null || menuType__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MenuTypeEntityDao targetDao = daoSession.getMenuTypeEntityDao();
            MenuTypeEntity menuTypeNew = targetDao.load(__key);
            synchronized (this) {
                menuType = menuTypeNew;
                menuType__resolvedKey = __key;
            }
        }
        return menuType;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 44820055)
    public void setMenuType(MenuTypeEntity menuType) {
        synchronized (this) {
            this.menuType = menuType;
            productTypeID = menuType == null ? null : menuType.getProductTypeID();
            menuType__resolvedKey = productTypeID;
        }
    }

    public boolean getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "FoodEntity{" +
                "productID='" + productID + '\'' +
                ", menuType=" + menuType +
                ", specList=" + specList +
                ", restaurantID='" + restaurantID + '\'' +
                ", id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", pinYin='" + pinYin + '\'' +
                ", unit='" + unit + '\'' +
                ", minUnit='" + minUnit + '\'' +
                ", productTypeID='" + productTypeID + '\'' +
                ", productTypeName='" + productTypeName + '\'' +
                ", price='" + price + '\'' +
                ", productFile='" + productFile + '\'' +
                ", productImage='" + productImage + '\'' +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                ", tasteID='" + tasteID + '\'' +
                ", daZheIs='" + daZheIs + '\'' +
                ", daZhe='" + daZhe + '\'' +
                ", warCount='" + warCount + '\'' +
                ", closeIs='" + closeIs + '\'' +
                ", closeNameIs='" + closeNameIs + '\'' +
                ", productCount=" + productCount +
                ", chuCaiType='" + chuCaiType + '\'' +
                ", canDiscount=" + canDiscount +
                ", memberPice=" + memberPice +
                ", salesType=" + salesType +
                ", accordIng='" + accordIng + '\'' +
                ", productShuXing='" + productShuXing + '\'' +
                ", productGive='" + productGive + '\'' +
                ", tasteType=" + tasteType +
                ", type=" + type +
                ", guid='" + guid + '\'' +
                ", packageName='" + packageName + '\'' +
                ", counts='" + counts + '\'' +
                ", number=" + number +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                ", menuType__resolvedKey='" + menuType__resolvedKey + '\'' +
                '}';
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

