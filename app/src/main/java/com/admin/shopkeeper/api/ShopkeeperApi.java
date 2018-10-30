package com.admin.shopkeeper.api;


import com.admin.shopkeeper.Config;
import com.admin.shopkeeper.model.FoodsModel;
import com.admin.shopkeeper.model.IntModel;
import com.admin.shopkeeper.model.StringModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by zeng on 2016/11/8.
 */

public interface ShopkeeperApi {

    /**
     * 登录
     * text1
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortLoginAshx.ashx")
    Observable<IntModel> login(
            @Field("LoginName") String loginName,
            @Field("ID") String id,
            @Field("Passd") String pass
    );


    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiDanAshx.ashx")
    Observable<StringModel> getRooms(
            @Field("Type") String type,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiDanAshx.ashx")
    Observable<StringModel> getTabels(
            @Field("Type") String type,
            @Field("leibie") String leibie,
            @Field("id") String id,
            @Field("Pindex1") int Pindex,
            @Field("Psize1") int Psize
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTakeFoodAshx.ashx")
    Observable<FoodsModel> getFoods(
            @Field("type") String type,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTakeFoodAshx.ashx")
    Observable<StringModel> getFoodsTypes(
            @Field("id") String id,
            @Field("type") String type,
            @Field("Pindex") int Pindex,
            @Field("Psize") int Psize
    );


    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiTaiAshx.ashx")
    Observable<StringModel> kaiTai(
            @Field("id") String id,
            @Field("type") String type,
            @Field("ltableid") String ltableid,
            @Field("itablename") String itablename,
            @Field("peoplecount") int peoplecount,
            @Field("canju") int canju,
            @Field("UserID") String userId,
            @Field("Name") String name
    );


    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortOrderManagerASHX.ashx")
    Observable<StringModel> getOrderList(
            @Field("Type") String type,
            @Field("id") String id,
            @Field("leibie") String leibie,
            @Field("state") String status,
            @Field("Pindex") int index,
            @Field("Psize") int size,
            @Field("Phone") String phone
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiDanAshx.ashx")
    Observable<StringModel> getOrderDetail(
            @Field("id") String shopID,
            @Field("Type") String type,
            @Field("BILLID") String id);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiDanAshx.ashx")
    Observable<StringModel> getGuazhangData(
            @Field("id") String shopID,
            @Field("Type") String type);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getMember(
            @Field("Type") String type,
            @Field("Phone") String phone);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getCard(
            @Field("Type") String type,
            @Field("id") String shopID,
            @Field("coue") String coue);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getScore(
            @Field("Type") String type,
            @Field("id") String shopID,
            @Field("integral") String integral);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> searchMember(
            @Field("Type") String type,
            @Field("id") String billId,
            @Field("rid") String rid,
            @Field("coue") String coue);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getOrderData(
            @Field("Type") String type,
            @Field("id") String shopId,
            @Field("BILLID") String billId,
            @Field("Types") String types);


    //    type	类型	2
//    TableId	转到的桌位ID
//    TableName	转到桌位名称
//    xtableid	要转桌位
//    billid	账单ID
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTurnFoodKaiDan.ashx")
    Observable<IntModel> changeTable(
            @Field("type") String type,
            @Field("TableId") String tableId,
            @Field("TableName") String tableName,
            @Field("xtableid") String xtableid,
            @Field("billid") String billid
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTurnFoodKaiDan.ashx")
    void merge();

    //
//    Type	类型	2
//    id	主键
//    TableId	转到座位ID
//    DETAILID	撤销桌位ID	搜索失败
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTurnFoodAshx.ashx")
    Observable<IntModel> trun(
            @Field("Type") String type,
            @Field("id") String id,
            @Field("TableId") String tableId,
            @Field("DETAILID") String detailId
    );


//    Type	类型
//    DETAILID	退菜明细ID
//    Id	店铺ID
//    billid	订单ID
//    tableid	桌位ID
//    Name	操作人员
//    TableName	桌位名称
//    count	数量
//    pice	价格
//    tuicount	退菜数量
//    zencount	重量
//    zidinyi	自定义原因
//    dirtoatal	退菜原因ID
//    toasttoatal	原因名称

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTakeFoodServer.ashx")
    Observable<IntModel> retreatFood(
            @Field("Id") String id,
            @Field("Type") String type,
            @Field("DETAILID") String detailId,
            @Field("billid") String billid,
            @Field("TABLEID") String tableId,
            @Field("Name") String name,
            @Field("TableName") String tableName,
            @Field("count") double count,
            @Field("pice") double pice,
            @Field("tuicount") double tuicount,
            @Field("zencount") double zencount,
            @Field("zidinyi") String zidinyi,
            @Field("dirtoatal") String dirtoatal,
            @Field("toasttoatal") String toasttoatal

    );


    //    type	类型	4
//    TableId	桌位ID
//    billid	账单ID
//    id	店铺ID
//    TableName	桌位名称
//    Username	操作人
//    USERID	操作人id
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiDanAshx.ashx")
    Observable<IntModel> undo(
            @Field("type") String type,
            @Field("TableId") String tableId,
            @Field("billid") String billid,
            @Field("id") String id,
            @Field("price") String price,
            @Field("TableName") String tableName,
            @Field("Username") String username,
            @Field("USERID") String userID
    );

    //    Type	类型	6
//    id		商店标识
//    tableid	桌位ID
//    billid	账单
//    info	菜品  	菜品用以下形式插入
//            桌位ID$菜品id$菜品名称$口味$口味主键$重量$价格$属性$属性名称
//    UserID	操作人员ID	菜品名称
//    Name	操作人
//    TableName	桌位名称	价格
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTakeFoodServer.ashx")
    Observable<IntModel> orderFood(
            @Field("Type") String type,
            @Field("id") String id,
            @Field("tableid") String tableid,
            @Field("billid") String billid,
            @Field(value = "info") String info,
            @Field("UserID") String userID,
            @Field("Name") String name,
            @Field("TableName") String TableName,
            @Field("APrice") double price,
            @Field("FoodType") String foodType,
            @Field("TableWareCount") String tableWareCount,
            @Field("FanBill") String fanBill
    );


    //    Type	类型	3
//    Id	店铺ID
//    tableid	桌位ID
//    billid	开台ID
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiTaiAshx.ashx")
    Observable<IntModel> qingtai(
            @Field("Id") String id,
            @Field("Type") String type,
            @Field("tableid") String tableId,
            @Field("billid") String billid,
            @Field("Username") String userName,
            @Field("USERID") String userId

    );


    //    type	类型	2
//    tableid	桌位ID
//    peoplecount	人数
//    canju	餐具数
//    billid	主键
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiTaiAshx.ashx")
    Observable<IntModel> peopleNum(
            @Field("id") String id,
            @Field("type") String type,
            @Field("tableid") String tableid,
            @Field("peoplecount") int peoplecount,
            @Field("canju") int canju,
            @Field("billid") String billid
    );


    //    Type	类型	6
//    Rid	商家店铺ID
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getCanJu(
            @Field("Type") String type,
            @Field("Rid") String shopID);


    //    Type	类型	8
//    id	店铺ID
//    leibie	类别
//    OrderSate	订单状态
//    Phone	电话
//    Pindex
//            Psize
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortOrderManagerASHX.ashx")
    Observable<StringModel> getMessage(
            @Field("Type") String type,
            @Field("id") String id,
            @Field("leibie") String leibie,
            @Field("OrderSate") String status,
            @Field("Phone") String phone,
            @Field("Pindex") int index,
            @Field("Psize") int size
    );

    //    Type	类型
//    id	订单ID
//    dazhe	打折数量
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<IntModel> getDazhe(
            @Field("Type") String s,
            @Field("id") String billid,
            @Field("mid") String shopId,
            @Field("chengdazhe") String chengdazhe,
            @Field("dazhe") int dazhe,
            @Field("daid") String daId
            );

    /**
     *
     * @param
     * @param billid
     * @param shopId
     * @param xiaPice 优惠金额
     * @param yinFu 应付金额
     * @param json 权限json
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<IntModel> getOherYouhui(
            @Field("Type") String type,
            @Field("daid") String couponid,
            @Field("bilid") String billid,
            @Field("id") String shopId,
            @Field("xiapice") double xiaPice,
            @Field("yingfu") double yinFu,
            @Field("jsonObjquanxian") String json
    );

    //    Type	类型	3
//    id	账单主键
//    Rid	商家主键
//    MenberID	会员主键	“”
//    TableId	桌位主键
//    zon	总金额
//    can	餐具费
//    pei	配送费	0
//    dabao	打包费	0
//    types	类别	7
//    jsonObjquanxian	权限促销
//    jsonObj	积分或优惠券促销
//    PayType	支付类型	1
//    jsonPay	组合支付
//    GuaID	挂账人主键	“”
//    PersonMonery	会员余额	“”
//    Changeid	结账人
//    Username	结账人
//    zonweight	总重量	“”
//    zonprice	总价格	“”
//    zonstate	总状态	“”
    //personcount
//    tablename
    //price
//    PayType
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> bill(
            @Field("Type") String type,
            @Field("id") String id,
            @Field("Rid") String rid,
            @Field("MenberID") String menberID,
            @Field("TableId") String tableId,
            @Field("zon") double zon,
            @Field("can") double can,
            @Field("pei") double pei,
            @Field("dabao") double dabao,
            @Field("types") String types,
            @Field("jsonObjquanxian") String jsonObjquanxian,
            @Field("jsonObj") String jsonObj,
            @Field("PayType") String payType,
            @Field("jsonPay") String jsonPay,
            @Field("GuaID") String guaID,
            @Field("PersonMonery") String personMonery,
            @Field("Changeid") String id1,
            @Field("Username") String name,
            @Field("zonweight") String zonweight,
            @Field("zonprice") String zonprice,
            @Field("zonstate") String zonstate,
            @Field("personcount") int personcount,
            @Field("price") double price,
            @Field("tablename") String tablename,
            @Field("MaLing") double maLing,
            @Field("Rounding") double rounding,
            @Field("free") double free);


    //    type	接口类型	3账单/5并单
//    id	商家ID
//    printsouce	是否本地打印	1
//    Sate	状态	7堂点 3外卖  4快餐
//    billid	订单ID	  并单以,号分割
//    Name	操作人员名称
//    personcount	人员数量
//    tableid	桌位id
//    tablename	桌位名称
//    priceold	总金额
//    price	实收金额
//    free	优惠
//    PayType	支付状态
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPrinterManager.ashx")
    Observable<StringModel> print(
            @Field("type") String s,
            @Field("id") String shopID,
            @Field("printsouce") String s1,
            @Field("Sate") String s2,
            @Field("billid") String billid,
            @Field("Name") String name,
            @Field("personcount") int personcount,
            @Field("tableid") String tableid,
            @Field("tablename") String tablename,
            @Field("priceold") double priceold,
            @Field("price") double price,
            @Field("free") double free,
            @Field("PayType") String s3);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortOrderManagerASHX.ashx")
    Observable<StringModel> getOrder(
            @Field("type") String s,
            @Field("Tableid") String roomTableID);

    //    Type	类型	0
//    dukabiaoji	读卡标记
//    id	商家ID
//    foodinfo	菜品详细
//    pdata	外卖日期
//    ptime	外卖时间
//    names	收货人姓名
//    address	收货人地址
//    phone	收货人电话
//    UserID	开单人主键
//    Name	开单人姓名
//    remark	备注
//    monery	预付款
//    tablid	桌位ID
//    tablename	桌位名称
//    types	支付状态
//    price 总价（菜品实际支付金额）
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPlaceOrderASHX.ashx")
    Observable<StringModel> kuaiSu(
            @Field("Type") String s,
            @Field("dukabiaoji") String s1,
            @Field("id") String shopID,
            @Field("foodinfo") String foodinfo,
            @Field("pdata") String pdata,
            @Field("ptime") String ptime,
            @Field("names") String names,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("UserID") String id,
            @Field("Name") String name,
            @Field("remark") String remark,
            @Field("monery") double monery,
            @Field("tablid") String tablid,
            @Field("tablename") String tablename,
            @Field("types") String types,
            @Field("FanBill") String fanBill,
            @Field("price") double price,
            @Field("BillType")  String billType);//字段 BillType 0=无桌位  1=有桌位

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPlaceOrderASHX.ashx")
    Observable<StringModel> reBillKuaiSu(
            @Field("Type") String s,
            @Field("dukabiaoji") String s1,
            @Field("id") String shopID,
            @Field("foodinfo") String foodinfo,
            @Field("pdata") String pdata,
            @Field("ptime") String ptime,
            @Field("names") String names,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("UserID") String id,
            @Field("Name") String name,
            @Field("remark") String remark,
            @Field("monery") double monery,
            @Field("tablid") String tablid,
            @Field("tablename") String tablename,
            @Field("FanBill") String fanBill,
            @Field("types") String types);


    //    type	接口状态	4
//    OrderID	 订单ID
//    TableName	桌位名称
//    TableID	桌位ID
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortOrderManagerASHX.ashx")
    Observable<StringModel> bindTable(
            @Field("type") String s,
            @Field("OrderID") String orderId,
            @Field("TableID") String tableId,
            @Field("id") String id,
            @Field("TableWareCount") String tableWareCount,
            @Field("Name") String name,
            @Field("TableName") String tableName);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> inBill(
            @Field("Type") String type,
            @Field("BILLId") String billID);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getTableData(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("ROOMTABLEID") String tableId);


    //    Type	类型	2
//    DETAILID	订单详情ID
//    billid	开台ID
//    id	店铺ID
//    tableid	桌位ID
//    Name	操作人
//    TableName	桌台名称

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTakeFoodServer.ashx")
    Observable<StringModel> cuicai(
            @Field("Type") String s,
            @Field("DETAILID") String detailId,
            @Field("billid") String billid,
            @Field("id") String shopID,
            @Field("tableid") String tableid,
            @Field("Name") String name,
            @Field("TableName") String tableName);

    //        type	接口类型	6
//        printsouce	是否本地打印	1是  2否
//        id	商家ID
//        Name	操作人员	 收银   或其他
//        price	金额
//        userid	操作人员ID
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPrinterManager.ashx")
    Observable<StringModel> jiaoBanPrint(
            @Field("type") String s, @Field("printsouce") String s1,
            @Field("id") String shopID,
            @Field("Name") String name,
            @Field("price") String s2,
            @Field("userid") String id);


    //    Type	1	1
//    id	店铺ID
//    billid	订单ID
//    tableid	桌位ID
//    TableName	桌位名称
//    State	状态	 2
//    printsouce	是否本地打印	1是  2否
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPrinterManager.ashx")
    Observable<StringModel> hurry(
            @Field("Type") String s,
            @Field("printsouce") String printsouce,
            @Field("id") String shopID,
            @Field("billid") String billId,
            @Field("tableid") String tableid,
            @Field("tablename") String tableName,
            @Field("FoodType") String foodtype,
            @Field("Name") String name,
            @Field("Sate") String s1);

    //    Type	接口类型	2
//    ID	店铺ID
//    OrderID	 订单ID
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortOrderManagerASHX.ashx")
    Observable<StringModel> cancel(
            @Field("Type") String s,
            @Field("ID") String shopID,
            @Field("OrderID") String orderId);

    //    Type	接口类型	1
//    Id	店铺ID
//    OrderId	订单ID
//    BILLID	账单ID
//    Types	类别	3快餐   4外卖
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortOrderManagerASHX.ashx")
    Observable<StringModel> confirm(
            @Field("Type") String s,
            @Field("Id") String shopID,
            @Field("OrderId") String orderId,
            @Field("BILLID") String billId,
            @Field("Types") String type);


    //    Type	接口状态	7
//    ID	店铺ID
//    OrderId	账单ID
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortOrderManagerASHX.ashx")
    Observable<StringModel> oFinish(
            @Field("Type") String s,
            @Field("ID") String shopID,
            @Field("OrderId") String orderId);

    //    http://182.140.132.196:8097/Port/PortAndroidUpdateState.ashx
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPrinterManager.ashx")
    Observable<StringModel> updatePrint(
            @Field("type") String type,
            @Field("BILLID") String billId,
            @Field("IPADDRESS") String ipAddress);

    //修改打印状态
   /* @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPrinterManager.ashx")
    Observable<StringModel> updatePrint(
            @Field("id") String s);*/
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiDanAshx.ashx")
    Observable<StringModel> getMergeOrderList(
            @Field("type") String s,
            @Field("id") String shopID,
            @Field("TABLEID") String tableId);


    //    type	接口类型	3
//    id	店铺ID
//    Pindex
//            Psize
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTakeFoodAshx.ashx")
    Observable<FoodsModel> getMeal(
            @Field("type") String s,
            @Field("id") String shopID,
            @Field("Pindex1") int i,
            @Field("Psize1") int i1);


    //                        type	接口类型	1
//                        printsouce	是否本地打印	1是  2否
//                        id	商家ID
//                        billid	订单ID
//                        tableid	桌位 ID
//                        tablename	桌位名称
//                        Sate	状态	1   催菜
//                        Name	操作人员	 收银   或其他
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPrinterManager.ashx")
    Observable<StringModel> printAfter(
            @Field("type") String s,
            @Field("printsouce") String s1,
            @Field("id") String shopID,
            @Field("billid") String billid,
            @Field("tableid") String tableId,
            @Field("tablename") String tableName,
            @Field("personcount") int personcount,
            @Field("Sate") String s2,
            @Field("FoodType") String foodType,
            @Field("Name") String name);

    //    type	接口类型
//    id	店铺ID
//    OrderID	账单ID
//    Types	微信交易编码
//    Price	交易金额
//    http://182.140.132.196:8097/Port/PortBillManagerNweASHX.ashx
//    PortBillManagerNweASHX
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> weixinBill(
            @Field("type") String type,
            @Field("id") String id,
            @Field("OrderID") String OrderID,
            @Field("Types") String Types,
            @Field("Price") int Price

    );


    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> query(
            @Field("type") String type,
            @Field("id") String id,
            @Field("OrderID") String OrderID
    );


    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getImage(@Field("type") String type,
                                     @Field("id") String id);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTakeFoodServer.ashx")
    Observable<StringModel> getReason(
            @Field("id") String shopID,
            @Field("type") String s);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getDazheList(
            @Field("id") String shopID,
            @Field("type") String s);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortKaiDanAshx.ashx")
    Observable<StringModel> kaiDan(
            @Field("type") String type,
            @Field("DETAILID") String detailId,
            @Field("sum") int sum);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortTakeFoodServer.ashx")
    Observable<StringModel> getSeason(
            @Field("id") String id,
            @Field("Type") String type,
            @Field("ProtuctID") String protuctID);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortProductOperationASHX.ashx")
    Observable<StringModel> pushAllFood(
            @Field("id") String id,
            @Field("type") String type);


    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortProductOperationASHX.ashx")
    Observable<StringModel> pushFood(
            @Field("id") String id,
            @Field("type") String type,
            @Field("PRODUCTID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortProductOperationASHX.ashx")
    Observable<StringModel> putFood(
            @Field("id") String id,
            @Field("type") String type,
            @Field("PRODUCTID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getPay(
            @Field("id") String id,
            @Field("type") String type);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortPlaceOrderASHX.ashx")
    Observable<StringModel> rebillTangdian(
            @Field("Type") String type,
            @Field("id") String id,
            @Field("tableid") String tableid,
            @Field("types") String billid,
            @Field("foodinfo") String info,
            @Field("UserId") String userId,
            @Field("Name") String name,
            @Field("tablename") String tablename,
            @Field("price") double price,
            @Field("BillType") String foodType,
            @Field("FanBill") String fanbill);





    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> scanBill(
            @Field("type") String type,
            @Field("Code") String code,
            @Field("Pice") double pice,
            @Field("id") String shopId,
            @Field("PayID") String payId
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> reBill(
            @Field("Type") String type,
            @Field("id") String id,
            @Field("Rid") String rid,
            @Field("MenberID") String menberID,
            @Field("TableId") String tableId,
            @Field("zon") double zon,
            @Field("can") double can,
            @Field("pei") double pei,
            @Field("dabao") double dabao,
            @Field("types") String types,
            @Field("jsonObjquanxian") String jsonObjquanxian,
            @Field("jsonObj") String jsonObj,
            @Field("PayType") String payType,
            @Field("jsonPay") String jsonPay,
            @Field("GuaID") String guaID,
            @Field("PersonMonery") String personMonery,
            @Field("Changeid") String id1,
            @Field("Username") String name,
            @Field("zonweight") String zonweight,
            @Field("zonprice") String zonprice,
            @Field("zonstate") String zonstate,
            @Field("free") double free,
            @Field("price") double price,
            @Field("MaLing") double maLing,
            @Field("Rounding") double rounding,
            @Field("FnaBill") String fnaNill);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> getRetCause(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> addCause(
            @Field("type") String type,
            @Field("GUID") String guid,
            @Field("RESTAURANTID") String restaurantId,
            @Field("Remark") String remark);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> deleteCause(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId,
            @Field("Guid") String guid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsProductManager.ashx")
    Observable<StringModel> getFoodsList(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> getMealsList(
            @Field("Type") String type,
            @Field("RESTAURANTID") String restaurantId);

    /**
     * 未绑定商品
     *
     * @param type
     * @param restaurantId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> getFoodOfNoBindList(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("Product") String product,
            @Field("PackID") String mealId,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsProductManager.ashx")
    Observable<StringModel> addFood(
            @Field("type") String type,
            @Field("types") String types,
            @Field("PRODUCTID") String productId,
            @Field("RESTAURANTID") String restaurantId,
            @Field("PRODUCTNAME") String productName,
            @Field("ID") String id,
            @Field("PINYIN") String pinyin,
            @Field("UNIT") String unit,
            @Field("MINUNIT") String minunit,
            @Field("PRODUCTTYPEID") String productTypeId,
            @Field("PRODUCTTYPENAME") String productTypeName,
            @Field("PRICE") double price,
            @Field("PRODUCTFile") String productfile,
            @Field("PIRNTID") String printId,
            @Field("STATE") int state,
            @Field("REMARK") String remark,
            @Field("TasteID") String tasteID,
            @Field("ProductCount") String productCount,
            @Field("WarCount") String warCount,
            @Field("ChuCaiType") int chuCaiType,
            @Field("MemberPice") double memberPice,
            @Field("SalesType") int salesType,
            @Field("CanDiscount") int canDiscount,
            @Field("ProtuctShuXing") int protuctShuXing,
            @Field("AccordIng") int accordIng);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> addMeal(
            @Field("Type") String type,
            @Field("RESTAURANTID") String restaurantId,
            @Field("PackageName") String productName,
            @Field("Types") String types,
            @Field("ProductTypes") String productType,
            @Field("Price") double price,
            @Field("Status") int status,
            @Field("Info") String remark,
            @Field("MemberPice") double memberPice,
            @Field("Candiscount") int canDiscount,
            @Field("PackageImgName") String imgName);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> changeMeal(
            @Field("Type") String type,
            @Field("ID") String mealId,
            @Field("PackageName") String productName,
            @Field("Types") String types,
            @Field("ProductTypes") String productType,
            @Field("Price") double price,
            @Field("Status") int status,
            @Field("Info") String remark,
            @Field("MemberPice") double memberPice,
            @Field("Candiscount") int canDiscount,
            @Field("PackageImgName") String imgName);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> addMealType(
            @Field("Type") String type,
            @Field("ProductTypeName") String productTypeName,
            @Field("SortNum") String sortNum,
            @Field("RESTAURANTID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> changeMealType(
            @Field("Type") String type,
            @Field("ProductTypeName") String productTypeName,
            @Field("SortNum") String sortNum,
            @Field("GUID") String guId);

    @Multipart
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsProductManager.ashx")
    Observable<StringModel> editFood(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsProductManager.ashx")
    Observable<StringModel> deleteFood(
            @Field("type") String type,
            @Field("PRODUCTID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> deleteMeal(
            @Field("Type") String type,
            @Field("ID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> deleteMealType(
            @Field("Type") String type,
            @Field("Guid") String guId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "PrinterManager.ashx")
    Observable<StringModel> getPrint(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "PrinterManager.ashx")
    Observable<StringModel> deletePrint(
            @Field("type") String type,
            @Field("ID") String ID,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "PrinterManager.ashx")
    Observable<StringModel> addPrint(
            @Field("type") String type,
            @Field("ID") String id,
            @Field("NAME") String name,
            @Field("PORT") String port,
            @Field("IPADDRESS") String ipaddress,
            @Field("PRINTTYPE") int printtype,
            @Field("QieDao") int qiedao,
            @Field("Types") int types,
            @Field("CutType") int cutType,
            @Field("PrintSpec") int printspec,
            @Field("PrintWay") int printWay,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "billdazhe.ashx")
    Observable<StringModel> getSale(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "BILLDAZHE.ashx")
    Observable<StringModel> getSaleBindFood(
            @Field("type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("Product") String product,
            @Field("id") String id,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "billdazhe.ashx")
    Observable<StringModel> addSale(
            @Field("type") String type,
            @Field("Count") String count,
            @Field("Name") String name,
            @Field("GUID") String guid,
            @Field("ptypeid") String ptypeid,
            @Field("pid1") String pid1,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "billdazhe.ashx")
    Observable<StringModel> deleteSale(
            @Field("type") String type,
            @Field("GUID") String guid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "GuaBillManager.ashx")
    Observable<StringModel> getGuazhang(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "GuaBillManager.ashx")
    Observable<StringModel> getGuazhangDetail(
            @Field("type") String type,
            @Field("GUID") String guid,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "GuaBillManager.ashx")
    Observable<StringModel> guazhangDetailOfBill(
            @Field("type") String type,
            @Field("pid") String guid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "GuaBillManager.ashx")
    Observable<StringModel> deleteGuazhang(
            @Field("type") String type,
            @Field("GUID") String guid,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "GuaBillManager.ashx")
    Observable<StringModel> editGuazhang(
            @Field("type") String type,
            @Field("Name") String name,
            @Field("Phone") String phone,
            @Field("Ramark") String remark,
            @Field("GUID") String guid,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "RoomManager.ashx")
    Observable<StringModel> getHouse(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "RoomManager.ashx")
    Observable<StringModel> deleteHouse(
            @Field("type") String type,
            @Field("ID") String id);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "RoomManager.ashx")
    Observable<StringModel> editHouse(
            @Field("type") String type,
            @Field("ID") String id,
            @Field("SORTNO") String sortno,
            @Field("STATE") int state,
            @Field("RESTAURANTID") String restaurantId,
            @Field("RoomTypeID") String roomTypeID,
            @Field("AreaId") String areaId,
            @Field("Price") String price,
            @Field("Counts") String counts,
            @Field("NAME") String name);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "TableManager.ashx")
    Observable<StringModel> getDesk(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "TableManager.ashx")
    Observable<StringModel> deleteDesk(
            @Field("type") String type,
            @Field("ROOMTABLEID") String roomtableId,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "TableManager.ashx")
    Observable<StringModel> editDesk(
            @Field("type") String type,
            @Field("TABLENAME") String tableName,
            @Field("PERSONCOUNT") String personcount,
            @Field("SORTNO") String sortno,
            @Field("ROOMID") String roomId,
            @Field("PerconManager") String perconManager,
            @Field("WeiXinType") int weixinType,
            @Field("ROOMTABLEID") String roomtableId,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "TableManager.ashx")
    Observable<StringModel> getDesktype(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "TableManager.ashx")
    Observable<StringModel> editDesktype(
            @Field("type") String type,
            @Field("Guid") String guid,
            @Field("PersonCount") String personCount,
            @Field("PersonCountInfo") String personCountInfo,
            @Field("Name") String name,
            @Field("Types") String types,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "TableManager.ashx")
    Observable<StringModel> deleteDesktype(
            @Field("type") String type,
            @Field("Guid") String guid,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "hospitaldetails.ashx ")
    Observable<StringModel> queryUserInfo(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ChengJinManager.ashx")
    Observable<StringModel> getWeight(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ChengJinManager.ashx")
    Observable<StringModel> editWeight(
            @Field("type") String type,
            @Field("GUID") String guid,
            @Field("Name") String name,
            @Field("Price") String price,
            @Field("Weight") String weight,
            @Field("Isopen") int isopen,
            @Field("Deviation") String deviation,
            @Field("State") int state,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ChengJinManager.ashx")
    Observable<StringModel> deleteWeight(
            @Field("type") String type,
            @Field("GUID") String guid);

    /**
     * 获取商家权限人员列表 type1
     * 获取员工列表 type5
     *
     * @param type         1/5
     * @param restaurantId
     * @return http://182.140.132.196:8097/Maste/MerChantPermission.ashx
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerChantPermission.ashx")
    Observable<StringModel> getShopPermissionPersonList(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    /**
     * @param type         3
     * @param restaurantId
     * @param shopAddress
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "hospitaldetails.ashx")
    Observable<StringModel> changeShopAddress(
            @Field("Type") String type,
            @Field("RESTAURANTID") String restaurantId,
            @Field("Address") String shopAddress);

    /**
     * @param type         4
     * @param restaurantId
     * @param shopName
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "hospitaldetails.ashx")
    Observable<StringModel> changeShopName(
            @Field("Type") String type,
            @Field("RESTAURANTID") String restaurantId,
            @Field("Names") String shopName);

    /**
     * @param type         5
     * @param restaurantId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "hospitaldetails.ashx")
    Observable<StringModel> changePassword(
            @Field("Type") String type,
            @Field("RESTAURANTID") String restaurantId,
            @Field("PassWordNew") String oldPassword,
            @Field("PassWord") String newPassword);

    /**
     * 获取基本设置
     *
     * @param type         2
     * @param restaurantId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantBasicSetup.ashx")
    Observable<StringModel> queryBasicSets(
            @Field("Type") String type,
            @Field("RESTAURANTID") String restaurantId);

    /**
     * 获取员工权限
     *
     * @param type   2
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerChantPermission.ashx")
    Observable<StringModel> getPermissionsOfUser(
            @Field("Type") String type,
            @Field("USERID") String userId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "StatisticTableBillInfo.ashx")
    Observable<StringModel> getDeskBussiness(
            @Field("Type") String type,
            @Field("StartTime") String startTime,
            @Field("EndTime") String endTime,
            @Field("RESTAURANTID") String restaurantId);

    @Multipart
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Text.ashx")
    Observable<StringModel> uploadImage(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

    /**
     * 获取员工权限
     *
     * @param type           3
     * @param userId
     * @param permissionJson
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerChantPermission.ashx")
    Observable<StringModel> changePermissionsOfUser(
            @Field("Type") String type,
            @Field("USERID") String userId,
            @Field("PermissionJson") String permissionJson);

    /**
     * 获取会员信息
     *
     * @param type   1
     * @param shopId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> getMembersInfo(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId);

    /**
     * 查询单个会员信息
     *
     * @param type   2会员消费记录 3会员充值记录 4会员积分记录 5会员卡券记录
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> getMemberInfo(
            @Field("Type") String type,
            @Field("UserID") String userId);

    /**
     * 删除员工
     *
     * @param type   6
     * @param userID
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerChantPermission.ashx")
    Observable<StringModel> deleteStaff(
            @Field("Type") String type,
            @Field("USERSID") String userID);

    /**
     * 新增员工
     *
     * @param type 7
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerChantPermission.ashx")
    Observable<StringModel> addStaff(
            @Field("Type") String type,
            @Field("USERNAME") String userName,
            @Field("PASSWORD") String password,
            @Field("RESTAURANTID") String shopId,
            @Field("ROLEID") int stateRole,
            @Field("SEX") int stateSex,
            @Field("BIRTHDAY") String birthday,
            @Field("USERID") String userId,
            @Field("DEPARTMENTID") String departMentId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "StatisticTableBillInfo.ashx")
    Observable<StringModel> getBussinee(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductKouWei.ashx")
    Observable<StringModel> getKouwei(
            @Field("type") String type,
            @Field("ProtuctID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductKouWei.ashx")
    Observable<StringModel> editKouwei(
            @Field("type") String type,
            @Field("Name") String name,
            @Field("RESTAURANTID") String restaurantId,
            @Field("ProtuctID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductKouWei.ashx")
    Observable<StringModel> deleteKouwei(
            @Field("type") String type,
            @Field("GUID") String guid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductSeason.ashx")
    Observable<StringModel> getSeason(
            @Field("type") String type,
            @Field("ProtuctID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductSeason.ashx")
    Observable<StringModel> addSeason(
            @Field("type") String type,
            @Field("Name") String name,
            @Field("Price") String price,
            @Field("RESTAURANTID") String restaurantId,
            @Field("ProtuctID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductSeason.ashx")
    Observable<StringModel> deleteSeason(
            @Field("type") String type,
            @Field("GUID") String guid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductShuXing.ashx")
    Observable<StringModel> getShuxing(
            @Field("type") String type,
            @Field("ProtuctID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductShuXing.ashx")
    Observable<StringModel> addShuxing(
            @Field("type") String type,
            @Field("ProtuctName") String productName,
            @Field("Name") String name,
            @Field("Price") String price,
            @Field("RESTAURANTID") String restaurantId,
            @Field("ProtuctID") String productId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantProductShuXing.ashx")
    Observable<StringModel> deleteShuxing(
            @Field("type") String type,
            @Field("GUID") String guid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsProductManager.ashx")
    Observable<StringModel> getTaste(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsProductManager.ashx")
    Observable<StringModel> checkData(
            @Field("type") String type,
            @Field("RESTAURANTID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> checkShop(
            @Field("Type") String type,
            @Field("ShangJiaID") String restaurantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantBasicSetup.ashx")
    Observable<StringModel> editBasicSet(
            @Field("type") String type,
            @Field("PayImage") String payImage,
            @Field("PrintSet") String printSet,
            @Field("ProductSize") String productSize,
            @Field("PayType") String payType,
            @Field("ChenJinDaZhe") String chenJinDaZhe,
            @Field("JieZhangPay") String jieZhangPay,
            @Field("GuestShow") String guestShow,
            @Field("PayPassWord") String password,
            @Field("CashPayType") String cashPayType,
            @Field("RESTAURANTID") String restaurantId,
            @Field("IsChan") String memberComUse,
            @Field("UniFiedPice") String UnitePay,
            @Field("Numberingrules") String numberingRules,
            @Field("CountCoding") String countCoding,
            @Field("RegularfoodID") String foodId,
            @Field("RegularfoodName ") String foodName
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerChantPermission.ashx")
    Observable<StringModel> getDepartMent(
            @Field("type") String type);

    /**
     * 获取会员等级信息
     *
     * @param type   1
     * @param shopId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MembersLevelManager.ashx")
    Observable<StringModel> getMembersLevelInfo(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId);

    /**
     * 删除会员等级信息
     *
     * @param type 2
     * @param guId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MembersLevelManager.ashx")
    Observable<StringModel> deleteMemberLevel(
            @Field("Type") String type,
            @Field("GUID") String guId);

    /**
     * 新增或删除会员等级管理
     *
     * @param type
     * @param guId         删除需要id
     * @param name
     * @param maxPoints
     * @param minPoints
     * @param restaurantId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MembersLevelManager.ashx")
    Observable<StringModel> editMemberLevel(
            @Field("Type") String type,
            @Field("GUID") String guId,
            @Field("NAME") String name,
            @Field("MaxPoints") String maxPoints,
            @Field("MinPoints") String minPoints,
            @Field("RESTAURANTID") String restaurantId);

    /**
     * 获取充值方案
     *
     * @param type   1
     * @param shopId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "UserFillManager.ashx")
    Observable<StringModel> getRechargeInfo(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId);

    /**
     * 新增或删除充值方案管理
     *
     * @param type
     * @param guId          删除需要id
     * @param restaurantId  新增需要商店id
     * @param name
     * @param rechargeType
     * @param rechargeMoney
     * @param giveMoney
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "UserFillManager.ashx")
    Observable<StringModel> editRechargeManage(
            @Field("Type") String type,
            @Field("RESTAURANTID") String restaurantId,
            @Field("GUID") String guId,
            @Field("Name") String name,
            @Field("Types") int rechargeType,
            @Field("Fill") String rechargeMoney,
            @Field("Counts") String giveMoney);

    /**
     * 删除充值方案
     *
     * @param type 4
     * @param guId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "UserFillManager.ashx")
    Observable<StringModel> deleteRechargeItem(
            @Field("Type") String type,
            @Field("Guid") String guId);

    /**
     * 获取优惠券
     *
     * @param type   5
     * @param shopId
     * @param kName  模糊查询 卡券名称  如果查看全部不需要传值
     * @param leiBie 如果查看全部不需要传值 1 满送券 2代金券  3商品券 4团购券   查看全部不需要传入值
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsOrderManagerASHX.ashx")
    Observable<StringModel> getCouponInfo(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("order") String order,
            @Field("KName") String kName,
            @Field("LeiBie") String leiBie,
            @Field("ShangJiaID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialCounts.ashx")
    Observable<StringModel> getshopdetail(
            @Field("Type") String type,
            @Field("PiCi") String pici);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialCounts.ashx")
    Observable<StringModel> getShopInfo(
            @Field("Type") String type,
            @Field("MerchantID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialCounts.ashx")
    Observable<StringModel> setShop1(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopid,
            @Field("PiCi") String piCi,
            @Field("MerchantID") String merchantId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialTaoCan.ashx")
    Observable<StringModel> getProductInfo(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId);


    /**
     * 删除优惠券
     *
     * @param type 3
     * @param pici
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialCounts.ashx")
    Observable<StringModel> deleteCoupon(
            @Field("Type") String type,
            @Field("PiCi") String pici);

    /**
     * 获取团购券
     *
     * @param type   1
     * @param shopId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialTaoCan.ashx")
    Observable<StringModel> getGroupCouponInfo(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId);

    /**
     * 获取商品券
     *
     * @param type   1
     * @param shopId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialProduct.ashx")
    Observable<StringModel> getCommodityCouponInfo(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId);

    /**
     * 获取线下券
     *
     * @param type   1
     * @param shopId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewCardManagerQiTaASHX.ashx")
    Observable<StringModel> getLineDownInfo(
            @Field("Type") String type,
            @Field("RID") String shopId,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("Product") String product
    );

    /**
     * 新增优惠券
     *
     * @param type   2
     * @param shopId
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialCounts.ashx")
    Observable<StringModel> addCoupon(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("Name") String name,
            @Field("SumPrice") String needMoney,
            @Field("Price") String giveMoney,
            @Field("BeginTime") String beginTime,
            @Field("EndTime") String endTime,
            @Field("Remark") String detail,
            @Field("Code") String code,
            @Field("IntergialTypeID") int jifenType,
            @Field("Types") int waimaiType,
            @Field("UseType") String useType,
            @Field("MaxCount") String maxCount,
            @Field("AgoDays") String days,
            @Field("Sum") String sum,
            @Field("LimitPrice") String limitPrice
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsOrderManagerASHX.ashx")
    Observable<StringModel> addCoupon2(
            @Field("Type") String type,
            @Field("kaleibie") String kaleibie,
            @Field("productname") String productname,
            @Field("product") String product,
            @Field("manzhu") String manzhu,
            @Field("btime") String btime,
            @Field("max") String max,
            @Field("info") String info,
            @Field("optionsRadios") String optionsRadios,
            @Field("name") String name,
            @Field("xiaoshou") String xiaoshou,
            @Field("freepice") String freepice,
            @Field("etime") String etime,
            @Field("days") String days,
            @Field("mpice") String mpice,
            @Field("usetype") String usetype,
            @Field("TiaoJian5") String TiaoJian5,
            @Field("mxiaofei") String mxiaofei,
            @Field("mbtime") String mbtime,
            @Field("metime") String metime,
            @Field("TiaoJian1") String TiaoJian1,
            @Field("lxiaofei") String lxiaofei,
            @Field("lbtime") String lbtime,
            @Field("letime") String letime,
            @Field("TiaoJian2") String TiaoJian2,
            @Field("cxiaofei") String cxiaofei,
            @Field("cbtime") String cbtime,
            @Field("cetime") String cetime,
            @Field("TiaoJian3") String TiaoJian3,
            @Field("zxiaofei") String zxiaofei,
            @Field("zbtime") String zbtime,
            @Field("zetime") String zetime,
            @Field("TiaoJian4") String TiaoJian4,
            @Field("jxiaofei") String jxiaofei,
            @Field("jbtime") String jbtime,
            @Field("jetime") String jetime,
            @Field("zuibtime") String zuibtime,
            @Field("zuietime") String zuietime,
            @Field("ybtime") String ybtime,
            @Field("yetime") String yetime,
            @Field("pbtime") String pbtime,
            @Field("petime") String petime,
            @Field("TiaoJian6") String TiaoJian6,
            @Field("chaoshu") String chaoshu,
            @Field("caobtime") String caobtime,
            @Field("caoetime") String caoetime,
            @Field("ShangJiaID") String ShangJiaID,
            @Field("OperationUserID") String OperationUserID,
            @Field("OperationUserName") String OperationUserName
    );

    /**
     * 新增团购券
     *
     * @param type 3
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialTaoCan.ashx")
    Observable<StringModel> addCouponGroup(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("Name") String name,
            @Field("SumPrice") String needMoney,
            @Field("Price") String giveMoney,
            @Field("BeginTime") String beginTime,
            @Field("EndTime") String endTime,
            @Field("Remark") String detail,
            @Field("Code") String code,
            @Field("IntergialTypeID") int jifenType,
            @Field("Types") int waimaiType,
            @Field("UseType") String useType,
            @Field("MaxCount") String maxCount,
            @Field("AgoDays") String days,
            @Field("Sum") String sum,
            @Field("LimitPrice") String limitPrice,
            @Field("ProductName") String productName,
            @Field("ProductID") String productId
    );

    /**
     * 新增商品券
     *
     * @param type 3
     * @return
     */
    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialProduct.ashx")
    Observable<StringModel> addCommodityCoupon(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("Name") String name,
            @Field("SumPrice") String needMoney,
            @Field("Price") String giveMoney,
            @Field("BeginTime") String beginTime,
            @Field("EndTime") String endTime,
            @Field("Remark") String detail,
            @Field("Code") String code,
            @Field("IntergialTypeID") int jifenType,
            @Field("Types") int waimaiType,
            @Field("UseType") String useType,
            @Field("MaxCount") String maxCount,
            @Field("AgoDays") String days,
            @Field("Sum") String sum,
            @Field("LimitPrice") String limitPrice,
            @Field("ProductName") String productName,
            @Field("ProductID") String productId
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "WeiXinJiFen.ashx")
    Observable<StringModel> saveWechat(
            @Field("type") String type,
            @Field("PersonCenter") String personCenter,
            @Field("WeiXinOrder") String weiXinOrder,
            @Field("EnableCenter") int jifenAdding,
            @Field("EnableOrder") int jifenExchange,
            @Field("WeiXinYuDin") String weiXinYuDin,
            @Field("WeiXinWaiMai") String weiXinWaiMai,
            @Field("WeiXinKuaiCan") String weiXinKuaiCan,
            @Field("TanDian") String tanDian,
            @Field("RESTAURANTID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "WeiXinJiFen.ashx")
    Observable<StringModel> saveWechat2(
            @Field("type") String type,
            @Field("Days") int days,
            @Field("Prepaid") String prepaid,
            @Field("QrCodePay") int qrCodePay,
            @Field("Functions") String functions,
            @Field("FoodOrDes") String foodOrDes,
            @Field("WeiXinYuDin") String weiXinYuDin,
            @Field("WeiXinWaiMai") String weiXinWaiMai,
            @Field("WeiXinKuaiCan") String weiXinKuaiCan,
            @Field("TanDian") String tanDian,
            @Field("RESTAURANTID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "WeiXinJiFen.ashx")
    Observable<StringModel> getWechat(
            @Field("type") String type,
            @Field("RESTAURANTID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "hospitaldetails.ashx")
    Observable<StringModel> getVersion(
            @Field("type") String type);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortLineUPOrder.ashx")
    Observable<StringModel> getQueue(
            @Field("Type") String type,
            @Field("leibie") String leibie,
            @Field("Phone") String Phone,
            @Field("id") String shopId);


    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortLineUPOrder.ashx")
    Observable<StringModel> addQueue(
            @Field("Type") String type,
            @Field("selvalue") String selvalue,
            @Field("id") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortLineUPOrder.ashx")
    Observable<StringModel> bindQueue(
            @Field("Type") String type,
            @Field("selvalue") String selvalue,
            @Field("Name") String name,
            @Field("UserID") String userID,
            @Field("TableId") String tableId,
            @Field("TableName") String tableName,
            @Field("TableWareCount") String tableWareCount,
            @Field("orderid") String orderid,
            @Field("id") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortLineUPOrder.ashx")
    Observable<StringModel> deleteQueue(
            @Field("Type") String type,
            @Field("orderid") String orderid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> getRechargeMember(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("pageIndex") String pageIndex,
            @Field("pageSize") String pageSize,
            @Field("Name") String name,
            @Field("phone") String phone);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> getRecharge(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_INTERFACE + "PortBillManagerNweASHX.ashx")
    Observable<StringModel> getMemberDetail(
            @Field("Type") String type,
            @Field("id") String billId,
            @Field("rid") String rid,
            @Field("coue") String coue);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> addRechage(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("StaffTel") String staffTel,
            @Field("StaffDepart") String staffDepart,
            @Field("StaffLanguage") String staffLanguage,
            @Field("StaffCatalogue") String staffCatalogue
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> memberRechage(
            @Field("Type") String type,
            @Field("UserID") String userID,
            @Field("PriceCounts") String priceCounts,
            @Field("PriceFill") String priceFill,
            @Field("RESTAURANTID") String shopId,
            @Field("Effective") String effective,
            @Field("CardID") String cardID);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> checkCode(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("PassWord") String passWord);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> moneyCharge(
            @Field("Type") String type,
            @Field("UserID") String userID,
            @Field("RESTAURANTID") String shopId,
            @Field("Price") String price,
            @Field("PayType") int payType,
            @Field("OperaName") String operaName,
            @Field("OperaID") String operaId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "Merchants.ashx")
    Observable<StringModel> productCharge(
            @Field("Type") String type,
            @Field("UserID") String userID,
            @Field("RESTAURANTID") String shopId,
            @Field("CardID") String cardID,
            @Field("PayType") int payType,
            @Field("OperaName") String operaName,
            @Field("OperaID") String operaId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getShopCollection(
            @Field("Type") String type,
            @Field("order") String order,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("TimeBegin") String timeBegin,
            @Field("TimeEdn") String timeEdn,
            @Field("ShangJiaID") String shangJiaID,
            @Field("SelectType") int selectType);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getCollectionDetail(
            @Field("Type") String type,
            @Field("Time") String time,
            @Field("Rid") String rid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getCollectionDetail(
            @Field("Type") String type,
            @Field("Time") String time,
            @Field("Time1") String time1,
            @Field("Rid") String rid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getChain(
            @Field("Type") String type,
            @Field("ShangJiaID") String shopId);


    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getJion(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("order") String order,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("TimeBegin") String timeBegin,
            @Field("TimeEdn") String timeEdn,
            @Field("ShangJiaID") String shangJiaID,
            @Field("SelectType") int selectType);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getJionDetail(
            @Field("Type") String type,
            @Field("Rid") String rid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getProduct(
            @Field("Type") String type,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("TimeBegin") String timeBegin,
            @Field("TimeEdn") String timeEdn,
            @Field("ShangJiaID") String shangJiaID,
            @Field("SelectType") int selectType);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getSale(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("order") String order,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("TimeBegin") String timeBegin,
            @Field("TimeEdn") String timeEdn,
            @Field("ShangJiaID") String shangJiaID,
            @Field("ProductID") String productID,
            @Field("PRODUCTTYPEID") String productTypeID,
            @Field("SelectType") int selectType);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsOrderManagerASHX.ashx")
    Observable<StringModel> getReturn(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("order") String order,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("TimeBegin") String timeBegin,
            @Field("TimeEdn") String timeEdn,
            @Field("ShangJiaID") String shangJiaID,
            @Field("SelectType") int selectType);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getMemberTranscation(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("order") String order,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("TimeBegin") String timeBegin,
            @Field("TimeEdn") String timeEdn,
            @Field("ShangJiaID") String shangJiaID,
            @Field("SelectType") int selectType);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getRechargeDetail(
            @Field("Type") String type,
            @Field("order") String order,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("ShangJiaID") String shangJiaID
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewSinglShopStatisASHX.ashx")
    Observable<StringModel> getDetail(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("order") String order,
            @Field("Time") String dataBegin,
            @Field("Time1") String dataEdn,
            @Field("Uid") String uid,
            @Field("Rid") String shangJiaID);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsOrderManagerASHX.ashx")
    Observable<StringModel> getOrderManage(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("order") String order,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("TimeBegin") String timeBegin,
            @Field("TimeEdn") String timeEdn,
            @Field("ShangJiaID") String shangJiaID,
            @Field("SelectType") int selectType);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsOrderManagerASHX.ashx")
    Observable<StringModel> getOrderManageDetail(
            @Field("Type") String type,
            @Field("order") String order,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("BILLID") String billId
    );

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsOrderManagerASHX.ashx")
    Observable<StringModel> getSensitiveopearation(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("order") String order,
            @Field("DataBegin") String dataBegin,
            @Field("DataEdn") String dataEdn,
            @Field("TimeBegin") String timeBegin,
            @Field("TimeEdn") String timeEdn,
            @Field("ShangJiaID") String shangJiaID,
            @Field("MingGan") int sensitiveType,
            @Field("SelectType") int selectType);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsOrderManagerASHX.ashx")
    Observable<StringModel> getSensitiveopearationDetail(
            @Field("Type") String type,
            @Field("BILLID") String billId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "MerchantsOrderManagerASHX.ashx")
    Observable<StringModel> getCouponDetail(
            @Field("Type") String type,
            @Field("pid") String pid,
            @Field("ShangJiaID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "WeiXinJiFen.ashx")
    Observable<StringModel> getMansong(
            @Field("Type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("Product") String Product,
            @Field("RESTAURANTID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "WeiXinJiFen.ashx")
    Observable<StringModel> editMansong(
            @Field("Type") String type,
            @Field("Guid") String guid,
            @Field("Name") String name,
            @Field("TiaoJian") String tiaoJian,
            @Field("JinEr") String jinEr,
            @Field("Btime") String btime,
            @Field("Etime") String etime,
            @Field("Apply") int apply,
            @Field("IsBegin") int isBegin,
            @Field("RESTAURANTID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "WeiXinJiFen.ashx")
    Observable<StringModel> deleteMansong(
            @Field("Type") String type,
            @Field("Guid") String guid,
            @Field("RESTAURANTID") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewCardManagerQiTaASHX.ashx")
    Observable<StringModel> editCouponLineDown(
            @Field("Type") String type,
            @Field("Name") String name,
            @Field("shu") int shu,
            @Field("maxshu") int maxShu,
            @Field("pice") String money,
            @Field("ShangID") String shangId,
            @Field("Isok") int state,
            @Field("Rid") String shopId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewCardManagerQiTaASHX.ashx")
    Observable<StringModel> deleteCouponLine(
            @Field("Type") String type,
            @Field("id") String guid);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "IntergialCounts.ashx")
    Observable<StringModel> getShopOfCouponLine(
            @Field("Type") String type,
            @Field("PiCi") String pcId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> addFood(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("ProductCount") String productCount,
            @Field("ProductName") String productName,
            @Field("ProductID") String productID,
            @Field("ProductPackageID") String productPackageID);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "ProductBackManager.ashx")
    Observable<StringModel> deleteFood(
            @Field("Type") String type,
            @Field("RESTAURANTID") String shopId,
            @Field("ID") String productPackageID);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewCardManagerQiTaASHX.ashx")
    Observable<StringModel> saveFoodAndType(
            @Field("type") String type,
            @Field("mid") String shopId,
            @Field("ptypeid") String productTypeId,
            @Field("pid1") String productId,
            @Field("cardid") String couponId);

    @FormUrlEncoded
    @POST(Config.BASE_URL + Config.BASE_URL_MASTE + "NewCardManagerQiTaASHX.ashx")
    Observable<StringModel> getSetFood(
            @Field("type") String type,
            @Field("pageSize") int pageSize,
            @Field("pageIndex") int pageIndex,
            @Field("Product") String productName,
            @Field("pid") String productId,
            @Field("mid") String shopId);
}
