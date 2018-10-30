package com.admin.shopkeeper.utils;

import android.annotation.SuppressLint;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrintClass {
    public boolean Print(String enty) throws IOException {
        boolean b = true;
        int port = 9100;
        String[] IP = enty.split("\\$");
        String ipPrint = IP[0];
//        String ipPrint = "182.140.132.196";
        Socket client = new Socket();
        try {
            client.setSendBufferSize(8192 * 10);
            client.connect(new InetSocketAddress(ipPrint, port), 2000);
            String[] shu = enty.split(">");
            for (int s = 0; s < shu.length; s++) {
                String IP1 = shu[s].split("\\$")[0];
                String type = shu[s].split("\\$")[3];
                String product = shu[s].split("\\$")[11];
                String tname = shu[s].split("\\$")[1];
                String sname = shu[s].split("\\$")[2];
                String personcount = shu[s].split("\\$")[5];
                String shiji = shu[s].split("\\$")[6];
                String yinshou = shu[s].split("\\$")[7];
                String cuttype = shu[s].split("\\$")[8];
                String username = shu[s].split("\\$")[9];
                connectedCallback(client, IP1, type, product, tname, sname, personcount, shiji, yinshou, cuttype, username);
            }
        } catch (Exception ex) {
            //打印机缺纸或者纸匣打开时,不会出现异常,不用特殊代码判断,数据不会丢失.
            b = false;
        } finally {
            client.close();
        }
        return b;
    }


    private void connectedCallback(Socket socket, String IP, String type, String product, String tname, String sname, String personcount, String shiji, String yinshou, String cuttype, String username) throws Exception {


        boolean b = true;
        byte[] big = new byte[]{29, 33, 17};//1D 21 选择字符集
        byte[] small = new byte[]{29, 33, 0};//1D 21 选择字符集
        byte[] fonts = new byte[]{27, 82, 0};//1D 21 选择字符集
        byte[] bty_tmp = new byte[]{29, 86, 1, 49};//切纸
        byte[] Internal = new byte[]{27, 64};//初始化打印机
        byte[] Internals = new byte[]{27, 83};//初始化打印机
        OutputStream socketwriter = new DataOutputStream(socket.getOutputStream());
        byte[] data;
        socketwriter.write(Internal, 0, Internal.length);
        socketwriter.write(Internals, 0, Internals.length);
        socketwriter.write(fonts, 0, fonts.length);//设置字符集
        if (type.equals("1") || type.equals("0") || type.equals("4") || type.equals("6")) {
            //MessageBox.Show("11");
            byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
        }
        if (type.equals("1")) {
            if (cuttype.equals("0")) {

                String[] oldshu = tname.split("z&f");
                byte[] temps = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
                socketwriter.write(temps, 0, temps.length);//设置字符集
                byte[] datas81 = ("        " + oldshu[0] + "\n").getBytes("gbk");
                socketwriter.write(datas81, 0, datas81.length);//输出文字

                socketwriter.write(small, 0, small.length);
                byte[] datas831 = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n").getBytes("gbk");
                socketwriter.write(datas831, 0, datas831.length);//输出文字
                socketwriter.write(big, 0, big.length);
                byte[] datas82 = ("      桌位:" + oldshu[1] + "\n").getBytes("gbk");
                socketwriter.write(datas82, 0, datas82.length);//输出文字


                byte[] temp5 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
                socketwriter.write(temp5, 0, temp5.length);//设置字符集
                byte[] datas83 = ("\n------------------------------------------------\n打单日期:" + getTime() + "\n").getBytes("gbk");
                socketwriter.write(datas83, 0, datas83.length);//输出文字

                byte[] datas8 = ("开单员:" + username + " 人数:" + oldshu[3] + " 订单编号:" + oldshu[2] + "\n").getBytes("gbk");
                socketwriter.write(datas8, 0, datas8.length);//输出文字
                byte[] datas821 = ("------------------------------------------------\n").getBytes("gbk");
                socketwriter.write(datas821, 0, datas821.length);//输出文字
                socketwriter.write(Internal, 0, Internal.length);
                byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
                socketwriter.write(temp, 0, temp.length);//设置字符集
                byte[] datas = (product + "\n\n\n\n\n").getBytes("gbk");
                socketwriter.write(datas, 0, datas.length);//输出文字
                //切刀
                socketwriter.write(bty_tmp, 0, bty_tmp.length);

            } else {
                String[] oldshu = tname.split("z&f");
                String[] er = product.split("\\<");
                for (int i = 0; i < er.length - 1; i++) {
                    byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
                    socketwriter.write(temp, 0, temp.length);//设置字符集
                    Thread.sleep(500);
                    byte[] datas81 = ("        " + oldshu[0] + "\n").getBytes("gbk");
                    socketwriter.write(datas81, 0, datas81.length);//输出文字

                    socketwriter.write(small, 0, small.length);
                    byte[] datas831 = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n").getBytes("gbk");
                    socketwriter.write(datas831, 0, datas831.length);//输出文字
                    socketwriter.write(big, 0, big.length);
                    byte[] datas82 = ("      桌位:" + oldshu[1] + "\n").getBytes("gbk");
                    socketwriter.write(datas82, 0, datas82.length);//输出文字

                    byte[] temp5 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
                    socketwriter.write(temp5, 0, temp5.length);//设置字符集
                    byte[] datas = ("------------------------------------------------\n订单编号:" + oldshu[2] + "\n------------------------------------------------\n").getBytes("gbk");
                    socketwriter.write(datas, 0, datas.length);//输出文字
                    socketwriter.write(Internal, 0, Internal.length);
                    byte[] temp1 = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
                    socketwriter.write(temp1, 0, temp1.length);//设置字符集
                    byte[] datas1 = ("" + er[i] + "\n\n\n\n\n\n\n\n").getBytes("gbk");
                    socketwriter.write(datas1, 0, datas1.length);//输出文字
                    //切刀
                    socketwriter.write(bty_tmp, 0, bty_tmp.length);
                    socketwriter.write(Internal, 0, Internal.length);
                }


            }
        }
        if (type.equals("6")) {

            if (cuttype.equals("0")) {

                String[] oldshu = tname.split("z&f");
                byte[] temps = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
                socketwriter.write(temps, 0, temps.length);//设置字符集
                byte[] datas81 = ("        " + oldshu[0] + "\n\n").getBytes("gbk");
                socketwriter.write(datas81, 0, datas81.length);//输出文字

                socketwriter.write(small, 0, small.length);
                byte[] datas831 = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n").getBytes("gbk");
                socketwriter.write(datas831, 0, datas831.length);//输出文字
                socketwriter.write(big, 0, big.length);
                byte[] datas82 = ("    " + oldshu[3] + "\n").getBytes("gbk");
                socketwriter.write(datas82, 0, datas82.length);//输出文字


                byte[] temp5 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
                socketwriter.write(temp5, 0, temp5.length);//设置字符集
                byte[] datas83 = ("------------------------------------------------\n打单日期:" + getTime() + "\n").getBytes("gbk");
                socketwriter.write(datas83, 0, datas83.length);//输出文字
                byte[] datas8 = ("订单编号:" + oldshu[2] + "\n------------------------------------------------\n").getBytes("gbk");
                socketwriter.write(datas8, 0, datas8.length);//输出文字
                socketwriter.write(Internal, 0, Internal.length);
                byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
                socketwriter.write(temp, 0, temp.length);//设置字符集
                byte[] datas = (product + "\n\n\n\n\n").getBytes("gbk");
                socketwriter.write(datas, 0, datas.length);//输出文字
                //切刀
                socketwriter.write(bty_tmp, 0, bty_tmp.length);
            } else {

                String[] oldshu = tname.split("z&f");
                String[] er = product.split("\\<");
                for (int i = 0; i < er.length - 1; i++) {
                    byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
                    socketwriter.write(temp, 0, temp.length);//设置字符集
                    Thread.sleep(500);
                    byte[] datas81 = ("        " + oldshu[0] + "\n\n").getBytes("gbk");
                    socketwriter.write(datas81, 0, datas81.length);//输出文字


                    socketwriter.write(small, 0, small.length);
                    byte[] datas831 = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n").getBytes("gbk");
                    socketwriter.write(datas831, 0, datas831.length);//输出文字
                    socketwriter.write(big, 0, big.length);
                    byte[] datas82 = ("    " + oldshu[3] + "\n").getBytes("gbk");
                    socketwriter.write(datas82, 0, datas82.length);//输出文字

                    byte[] temp5 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
                    socketwriter.write(temp5, 0, temp5.length);//设置字符集
                    byte[] datas8 = ("------------------------------------------------\n").getBytes("gbk");
                    socketwriter.write(datas8, 0, datas8.length);//输出文字
                    byte[] datas821 = ("订单编号:" + oldshu[2] + "\n------------------------------------------------\n").getBytes("gbk");
                    socketwriter.write(datas821, 0, datas821.length);//输出文字
                    socketwriter.write(Internal, 0, Internal.length);
                    byte[] temp1 = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
                    socketwriter.write(temp1, 0, temp1.length);//设置字符集
                    byte[] datas = (er[i] + "\n\n\n\n\n\n\n\n").getBytes("gbk");
                    socketwriter.write(datas, 0, datas.length);//输出文字
                    //切刀
                    socketwriter.write(bty_tmp, 0, bty_tmp.length);
                    socketwriter.write(Internal, 0, Internal.length);

                }
            }

        }
        //排号打印
        if (type.equals("9")) {
            String[] er = product.split("\\@");
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temps = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temps, 0, temps.length);//设置字符集
            byte[] datas33 = ("        排队叫号\n\n").getBytes("gbk");
            socketwriter.write(datas33, 0, datas33.length);//输出文字
            byte[] temp8 = new byte[]{27, 50};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp8, 0, temp8.length);//设置字符集
            byte[] temp9 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp9, 0, temp9.length);//设置字符集
            byte[] datas3 = ("------------------------------------------------\n               排号成功,您的号码为:\n\n").getBytes("gbk");
            socketwriter.write(datas3, 0, datas3.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
            byte[] datas1 = ("          " + er[2] + er[0] + "" + "\n\n").getBytes("gbk");
            socketwriter.write(datas1, 0, datas1.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temp4 = new byte[]{27, 50};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp4, 0, temp4.length);//设置字符集
            byte[] temp91 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp91, 0, temp91.length);//设置字符集
            byte[] datas31 = ("               前面还有" + er[1] + "桌在等候!\n\n------------------------------------------------\n         请注意叫号,过号作废,谢谢合作。\n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas31, 0, datas31.length);//输出文字
            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);
        }
        //充值打印
        if (type.equals("10")) {
            String[] er = product.split("\\@");
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temps = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temps, 0, temps.length);//设置字符集
            byte[] datas11 = ("" + sname.split("\\@")[0] + "" + "\n\n").getBytes("gbk");
            socketwriter.write(datas11, 0, datas11.length);//输出文字
            byte[] temp8 = new byte[]{27, 50};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp8, 0, temp8.length);//设置字符集
            byte[] temp9 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp9, 0, temp9.length);//设置字符集
            byte[] datas31 = ("       会员卡充值凭据        \n").getBytes("gbk");
            socketwriter.write(datas31, 0, datas31.length);//输出文字
            byte[] datas3 = ("-------------------------------\n").getBytes("gbk");
            socketwriter.write(datas3, 0, datas3.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temp = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
            byte[] datas32 = ("操 作 人：" + username + "\n\n").getBytes("gbk");
            socketwriter.write(datas32, 0, datas32.length);//输出文字
            byte[] datas33 = ("卡    号：" + tname + "\n\n").getBytes("gbk");
            socketwriter.write(datas33, 0, datas33.length);//输出文字
            byte[] datas34 = ("充值金额：" + shiji.split("\\@")[0] + "\n\n").getBytes("gbk");
            socketwriter.write(datas34, 0, datas34.length);//输出文字
            byte[] datas38 = ("会员余额：" + shiji.split("\\@")[1] + "\n\n").getBytes("gbk");
            socketwriter.write(datas38, 0, datas38.length);//输出文字
            byte[] datas39 = ("充值类型：" + shiji.split("\\@")[2] + "\n\n").getBytes("gbk");
            socketwriter.write(datas39, 0, datas39.length);//输出文字
            byte[] datas35 = ("充值时间：" + CalendarUtils.formatDate(new Date()) + "\n\n").getBytes("gbk");
            socketwriter.write(datas35, 0, datas35.length);//输出文字
            byte[] datas37 = ("注意：请仔细核对小票,并妥善保管\n\n").getBytes("gbk");
            socketwriter.write(datas37, 0, datas37.length);//输出文字
            byte[] datas36 = ("-------------------------------\n").getBytes("gbk");
            socketwriter.write(datas36, 0, datas36.length);//输出文字
            byte[] datas14 = ("商家地址:" + sname.split("\\@")[2] + "\n商家电话:" + sname.split("\\@")[1] + "\n").getBytes("gbk");
            socketwriter.write(datas14, 0, datas14.length);//输出文字
            byte[] datas15 = ("欢迎下次光临！\n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas15, 0, datas15.length);//输出文字
            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);
        }
        if (type.equals("0")) {

            String[] oldshu = tname.split("z&f");
            byte[] temps = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temps, 0, temps.length);//设置字符集
            byte[] datas81 = ("        " + oldshu[0] + "\n\n").getBytes("gbk");
            socketwriter.write(datas81, 0, datas81.length);//输出文字

            socketwriter.write(small, 0, small.length);
            byte[] datas831 = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n").getBytes("gbk");
            socketwriter.write(datas831, 0, datas831.length);//输出文字
            socketwriter.write(big, 0, big.length);
            byte[] datas82 = ("      桌  位:" + oldshu[1] + "\n").getBytes("gbk");
            socketwriter.write(datas82, 0, datas82.length);//输出文字

            byte[] temp5 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp5, 0, temp5.length);//设置字符集
            byte[] datas83 = ("------------------------------------------------\n打单日期:" + getTime() + "\n").getBytes("gbk");
            socketwriter.write(datas83, 0, datas83.length);//输出文字
            byte[] datas8 = ("订单编号:" + oldshu[2] + "\n------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas8, 0, datas8.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
            byte[] datas = (product + "\n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas, 0, datas.length);//输出文字
            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);
            //data = ("桌位：" + tname + "（催菜单）" + "\n" + product + "\n\n\n\n\n").getBytes("gbk");
            //socketwriter.write(data, 0, data.length);//输出文字
            ////切刀
            //socketwriter.write(bty_tmp, 0, bty_tmp.length);
        }
        if (type.equals("4")) {
            String[] oldshu = tname.split("z&f");
            byte[] temps = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temps, 0, temps.length);//设置字符集
            byte[] datas81 = ("        " + oldshu[0] + "\n\n").getBytes("gbk");
            socketwriter.write(datas81, 0, datas81.length);//输出文字

            socketwriter.write(small, 0, small.length);
            byte[] datas831 = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n").getBytes("gbk");
            socketwriter.write(datas831, 0, datas831.length);//输出文字
            socketwriter.write(big, 0, big.length);
            byte[] datas82 = ("      桌  位:" + oldshu[1] + "\n").getBytes("gbk");
            socketwriter.write(datas82, 0, datas82.length);//输出文字

            byte[] temp5 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp5, 0, temp5.length);//设置字符集
            byte[] datas83 = ("------------------------------------------------\n打单日期:" + getTime() + "\n").getBytes("gbk");
            socketwriter.write(datas83, 0, datas83.length);//输出文字
            byte[] datas8 = ("订单编号:" + oldshu[2] + "\n").getBytes("gbk");
            socketwriter.write(datas8, 0, datas8.length);//输出文字
            byte[] datas821 = ("退菜原因:" + oldshu[3] + "\n------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas821, 0, datas821.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
            byte[] datas = (product + "\n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas, 0, datas.length);//输出文字
            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);
        }
        if (type.equals("2")) {
            String[] oldshu = tname.split("z&f");
            byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
            byte[] datas1 = ("" + sname + "" + "\n").getBytes("gbk");
            socketwriter.write(datas1, 0, datas1.length);//输出文字
            byte[] datas81 = ("        " + oldshu[0] + "\n\n").getBytes("gbk");
            socketwriter.write(datas81, 0, datas81.length);//输出文字

            socketwriter.write(small, 0, small.length);
            byte[] datas831 = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n").getBytes("gbk");
            socketwriter.write(datas831, 0, datas831.length);//输出文字
            socketwriter.write(big, 0, big.length);
            byte[] datas82 = ("      桌  位:" + oldshu[1] + "\n").getBytes("gbk");
            socketwriter.write(datas82, 0, datas82.length);//输出文字

            socketwriter.write(Internal, 0, Internal.length);
            byte[] temps = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temps, 0, temps.length);//设置字符集
            byte[] datas83 = ("------------------------------------------------\n打单日期:" + getTime() + "\n").getBytes("gbk");
            socketwriter.write(datas83, 0, datas83.length);//输出文字
            byte[] datas8 = ("订单编号:" + oldshu[2] + "\n------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas8, 0, datas8.length);//输出文字
            byte[] datas12 = ("商品名称              数量     单价     小计\n").getBytes("gbk");
            socketwriter.write(datas12, 0, datas12.length);//输出文字
            byte[] datas = ("" + product + "\n\n").getBytes("gbk");
            socketwriter.write(datas, 0, datas.length);//输出文字
            byte[] datas5 = ("------------------------------------------------\n商品合计:" + shiji + "元\n\n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas5, 0, datas5.length);//输出文字
            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);
        }
        if (type.equals("3")) {

            String[] oldshu = tname.split("z&f");
            byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
            byte[] datas1 = ("" + sname.split("@")[0] + "" + "\n").getBytes("gbk");
            socketwriter.write(datas1, 0, datas1.length);//输出文字
            byte[] datas11 = ("        " + oldshu[0] + "" + "\n").getBytes("gbk");
            socketwriter.write(datas11, 0, datas11.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] datas7 = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas7, 0, datas7.length);//输出文字
            byte[] temp4 = new byte[]{27, 50};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp4, 0, temp4.length);//设置字符集
            byte[] temps = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temps, 0, temps.length);//设置字符集
            byte[] temp5 = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp5, 0, temp5.length);//设置字符集
            byte[] datas2 = ("桌位:" + oldshu[1] + " 人数:" + personcount + "  结账员:" + username + "\n").getBytes("gbk");
            socketwriter.write(datas2, 0, datas2.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] datas8 = ("打单日期:" + getTime() + "\n").getBytes("gbk");
            socketwriter.write(datas8, 0, datas8.length);//输出文字
            byte[] datas9 = ("订单号:" + oldshu[2] + "\n------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas9, 0, datas9.length);//输出文字
            byte[] temp7 = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp7, 0, temp7.length);//设置字符集
            byte[] datas12 = ("商品名称              数量     单价     小计\n").getBytes("gbk");
            socketwriter.write(datas12, 0, datas12.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temp8 = new byte[]{27, 50};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp8, 0, temp8.length);//设置字符集
            byte[] temp9 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp9, 0, temp9.length);//设置字符集
            byte[] datas = ("" + product + "\n").getBytes("gbk");
            socketwriter.write(datas, 0, datas.length);//输出文字
            byte[] datas6 = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas6, 0, datas6.length);//输出文字
            byte[] datas5 = ("商品合计:" + shiji.split("@")[0] + "元\n").getBytes("gbk");
            socketwriter.write(datas5, 0, datas5.length);//输出文字
            byte[] datas111 = ("优惠金额:" + shiji.split("@")[1] + "元\n").getBytes("gbk");
            socketwriter.write(datas111, 0, datas111.length);//输出文字
            byte[] datas112 = ("优惠明细:" + oldshu[3].split("@")[0] + "\n").getBytes("gbk");
            socketwriter.write(datas112, 0, datas112.length);//输出文字
            byte[] datas13 = ("预    付:" + oldshu[3].split("@")[1] + "元\n").getBytes("gbk");
            socketwriter.write(datas13, 0, datas13.length);//输出文字
            byte[] datas18 = ("应    付:" + oldshu[3].split("@")[2] + "元\n").getBytes("gbk");
            socketwriter.write(datas18, 0, datas18.length);//输出文字
            byte[] temp6 = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp6, 0, temp6.length);//设置字符集
            byte[] datas10 = ("" + yinshou + "元\n" + "------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas10, 0, datas10.length);//输出文字
            byte[] datas14 = ("商家地址:" + sname.split("@")[2] + "\n商家电话:" + tname.split("@")[1] + "\n").getBytes("gbk");
            socketwriter.write(datas14, 0, datas14.length);//输出文字
            byte[] datas15 = ("欢迎下次光临！\n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas15, 0, datas15.length);//输出文字
            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);


//            
        }
        if (type.equals("7")) {
//             
            //获取就餐人信息
            String[] oldshu = tname.split("z&f");
            //获取就餐人信息
            String names = "暂无";
            String phone = "暂无";
            String address = "暂无";
            names = oldshu[1].split("@")[0];
            phone = oldshu[1].split("@")[1];
            address = oldshu[1].split("@")[2];
            byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
            byte[] datas1 = ("" + sname.split("@")[0] + "" + "\n").getBytes("gbk");
            socketwriter.write(datas1, 0, datas1.length);//输出文字
            byte[] datas11 = ("        " + oldshu[0] + "" + "\n").getBytes("gbk");
            socketwriter.write(datas11, 0, datas11.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] datas7 = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas7, 0, datas7.length);//输出文字
            byte[] temp4 = new byte[]{27, 50};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp4, 0, temp4.length);//设置字符集
            byte[] temps = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temps, 0, temps.length);//设置字符集
            byte[] temp5 = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp5, 0, temp5.length);//设置字符集
            byte[] datas21 = ("打单日期:" + getTime() + "  订单号:" + oldshu[2].split("@")[0] + "\n").getBytes("gbk");
            socketwriter.write(datas21, 0, datas21.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] datas8 = ("姓名:" + names + "          电话:" + phone + "\n").getBytes("gbk");
            socketwriter.write(datas8, 0, datas8.length);//输出文字
            byte[] temp7 = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            byte[] datas80 = ("地址:" + address + "\n").getBytes("gbk");
            socketwriter.write(datas80, 0, datas80.length);//输出文字
            socketwriter.write(temp7, 0, temp7.length);//设置字符集
            byte[] datas66 = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas66, 0, datas66.length);//输出文字
            byte[] datas12 = ("商品名称              数量     单价     小计\n").getBytes("gbk");
            socketwriter.write(datas12, 0, datas12.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temp8 = new byte[]{27, 50};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp8, 0, temp8.length);//设置字符集
            byte[] temp9 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp9, 0, temp9.length);//设置字符集
            byte[] datas = ("" + product + "\n").getBytes("gbk");
            socketwriter.write(datas, 0, datas.length);//输出文字
            byte[] datas6 = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas6, 0, datas6.length);//输出文字
            byte[] datas5 = ("商品合计:" + shiji.split("@")[0] + "元\n").getBytes("gbk");
            socketwriter.write(datas5, 0, datas5.length);//输出文字
            byte[] datas111 = ("优惠金额:" + shiji.split("@")[1] + "元\n").getBytes("gbk");
            socketwriter.write(datas111, 0, datas111.length);//输出文字
            byte[] datas112 = ("优惠明细:" + oldshu[3].split("@")[0] + "\n").getBytes("gbk");
            socketwriter.write(datas112, 0, datas112.length);//输出文字
            byte[] datas13 = ("预    付:" + oldshu[3].split("@")[1] + "元\n").getBytes("gbk");
            socketwriter.write(datas13, 0, datas13.length);//输出文字
            byte[] datas18 = ("应    付:" + oldshu[3].split("@")[2] + "元\n").getBytes("gbk");
            socketwriter.write(datas18, 0, datas18.length);//输出文字
            byte[] temp6 = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp6, 0, temp6.length);//设置字符集
            byte[] datas10 = ("" + yinshou + "元\n" + "------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas10, 0, datas10.length);//输出文字
            byte[] datas14 = ("商家地址:" + sname.split("@")[2] + "\n商家电话:" + sname.split("@")[1] + "\n").getBytes("gbk");
            socketwriter.write(datas14, 0, datas14.length);//输出文字
            byte[] datas15 = ("欢迎下次光临！\n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas15, 0, datas15.length);//输出文字
            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);
        }
        if (type.equals("8")) {


            String[] oldshu = tname.split("z&f");
            byte[] temp = new byte[]{29, 33, 17};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp, 0, temp.length);//设置字符集
            byte[] datas1 = ("" + sname.split("@")[0] + "" + "\n").getBytes("gbk");
            socketwriter.write(datas1, 0, datas1.length);//输出文字
            byte[] datas11 = ("        " + oldshu[0] + "" + "\n").getBytes("gbk");
            socketwriter.write(datas11, 0, datas11.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] datas7 = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas7, 0, datas7.length);//输出文字
            socketwriter.write(big, 0, big.length);
            byte[] datas82 = ("" + oldshu[2].split("@")[1] + "\n").getBytes("gbk");
            socketwriter.write(datas82, 0, datas82.length);//输出文字
            socketwriter.write(small, 0, small.length);
            byte[] paihao = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(paihao, 0, paihao.length);//输出文字

            byte[] datas21 = ("订单号:" + oldshu[2].split("@")[0] + "\n").getBytes("gbk");
            socketwriter.write(datas21, 0, datas21.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] datas8 = ("打单日期:" + getTime() + "\n").getBytes("gbk");
            socketwriter.write(datas8, 0, datas8.length);//输出文字
            //byte[] datas9 = ("人数:" + personcount + "\n------------------------------------------------\n");
            // socketwriter.write(datas9, 0, datas9.length);//输出文字
            byte[] temp7 = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp7, 0, temp7.length);//设置字符集
            byte[] datas12 = ("商品名称              数量     单价     小计\n").getBytes("gbk");
            socketwriter.write(datas12, 0, datas12.length);//输出文字
            socketwriter.write(Internal, 0, Internal.length);
            byte[] temp8 = new byte[]{27, 50};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp8, 0, temp8.length);//设置字符集
            byte[] temp9 = new byte[]{29, 33, 0};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp9, 0, temp9.length);//设置字符集
            byte[] datas = ("" + product + "\n").getBytes("gbk");
            socketwriter.write(datas, 0, datas.length);//输出文字
            byte[] datas6 = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas6, 0, datas6.length);//输出文字
            byte[] datas5 = ("商品合计:" + shiji.split("@")[0] + "元\n").getBytes("gbk");
            socketwriter.write(datas5, 0, datas5.length);//输出文字
            byte[] datas111 = ("优惠金额:" + shiji.split("@")[1] + "元\n").getBytes("gbk");
            socketwriter.write(datas111, 0, datas111.length);//输出文字
            byte[] datas112 = ("优惠明细:" + oldshu[3].split("@")[0] + "\n").getBytes("gbk");
            socketwriter.write(datas112, 0, datas112.length);//输出文字
            byte[] datas13 = ("预    付:" + oldshu[3].split("@")[1] + "元\n").getBytes("gbk");
            socketwriter.write(datas13, 0, datas13.length);//输出文字
            byte[] datas18 = ("应    付:" + oldshu[3].split("@")[2] + "元\n").getBytes("gbk");
            socketwriter.write(datas18, 0, datas18.length);//输出文字
            byte[] temp6 = new byte[]{27, 69, 1};//1D 21 选择字体大小,0x01放大一倍
            socketwriter.write(temp6, 0, temp6.length);//设置字符集
            byte[] datas10 = ("" + yinshou + "元\n" + "------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas10, 0, datas10.length);//输出文字
            byte[] datas14 = ("商家地址:" + sname.split("@")[2] + "\n商家电话:" + sname.split("@")[1] + "\n").getBytes("gbk");
            socketwriter.write(datas14, 0, datas14.length);//输出文字
            byte[] datas15 = ("欢迎下次光临！\n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas15, 0, datas15.length);//输出文字
            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);
//            
        }
        if (type.equals("5")) {


            byte[] temp7 = new byte[]{27, 69, 1};
            socketwriter.write(temp7, 0, temp7.length);
            byte[] datas12 = ("                交班单收银统计表\n").getBytes("gbk");
            socketwriter.write(datas12, 0, datas12.length);


            String strInfo = String.format("交班人：%1s\n", username);
            byte[] datas13 = (strInfo).getBytes("gbk");
            socketwriter.write(datas13, 0, datas13.length);
            strInfo = String.format("交班时间：%1s\n\n", getTime());
            datas13 = (strInfo).getBytes("gbk");
            socketwriter.write(datas13, 0, datas13.length);
            byte[] datas6 = ("------------------------------------------------\n").getBytes("gbk");
            socketwriter.write(datas6, 0, datas6.length);//输出文字

            byte[] btPayTypeDataTitle = ("               收款方式统计 \n\n").getBytes("gbk");
            socketwriter.write(btPayTypeDataTitle, 0, btPayTypeDataTitle.length);


            //列名
            byte[] btPayTypeDataTitle1 = ("收款方式       次数      金额 \n").getBytes("gbk");
            socketwriter.write(btPayTypeDataTitle1, 0, btPayTypeDataTitle1.length);


            //接收到客户端传过来的数据                     // 现金&         9&    424.00,刷卡&         3&    184.00,微信&         0&    0,挂账&         0&    0|结账单数&    12  ,结账人数&    24  | 特色菜品&    16, 荤菜类&    1, 素菜类&    0, 锅底系列&    0, 小吃类&    1, 酒水&    0, 其他&    0|情侣套餐A    &99
            int indx = product.lastIndexOf(",");
            if (indx != -1) {
                product = product.substring(0, indx) + product.substring(indx + 1, product.length());
            }
            String strPrintContent = product;
            String[] PrintArray = strPrintContent.split("\\|");


            //从服务器来的数据 支付方式|台数/人数|菜品类别|套餐/包席
            if (PrintArray.length == 6) {
//                        #region 支付方式
                //获取到支付方式的内容 [现金&         9&    424.00,刷卡&         3&    184.00,微信&         0&    0,挂账&         0&    0    ]
                String[] PayTypeArray = PrintArray[0].split(",");
                for (String aPayTypeArray : PayTypeArray) {
                    String[] strpayTypeCurrentIndex = aPayTypeArray.split("&");
                    String strPayTypePrintContent = String.format("%1s    %2s    %3s\n", strpayTypeCurrentIndex[0], strpayTypeCurrentIndex[1], strpayTypeCurrentIndex[2]);

                    byte[] btPayTypeDataContent = (strPayTypePrintContent).getBytes("gbk");
                    socketwriter.write(btPayTypeDataContent, 0, btPayTypeDataContent.length);
                }
//                        #endregion

                byte[] datasc = ("-------------------------------\n").getBytes("gbk");
                socketwriter.write(datasc, 0, datasc.length);
//                        #region 撤单优惠
                //获取到支付方式的内容 [现金&         9&    424.00,刷卡&         3&    184.00,微信&         0&    0,挂账&         0&    0    ]
                String[] PayTypeArrayc = PrintArray[4].split(",");
                for (String aPayTypeArrayc : PayTypeArrayc) {
                    String[] strpayTypeCurrentIndex = aPayTypeArrayc.split("&");
                    String strPayTypePrintContent = String.format("%1s    %2s    %3s\n", strpayTypeCurrentIndex[0], strpayTypeCurrentIndex[1], strpayTypeCurrentIndex[2]);

                    byte[] btPayTypeDataContent = (strPayTypePrintContent).getBytes("gbk");
                    socketwriter.write(btPayTypeDataContent, 0, btPayTypeDataContent.length);
                }
//                        #endregion
                byte[] datasz = ("------------------------------------------------\n").getBytes("gbk");
                socketwriter.write(datasz, 0, datasz.length);
//                        #region 总营业额
                //获取到支付方式的内容 [现金&         9&    424.00,刷卡&         3&    184.00,微信&         0&    0,挂账&         0&    0    ]
                String[] PayTypeArrayz = PrintArray[5].split(",");
                for (String aPayTypeArrayz : PayTypeArrayz) {
                    String[] strpayTypeCurrentIndex = aPayTypeArrayz.split("&");
                    String strPayTypePrintContent = String.format("%1s    %2s    %3s\n", strpayTypeCurrentIndex[0], strpayTypeCurrentIndex[1], strpayTypeCurrentIndex[2]);

                    byte[] btPayTypeDataContent = (strPayTypePrintContent).getBytes("gbk");
                    socketwriter.write(btPayTypeDataContent, 0, btPayTypeDataContent.length);
                }
//                        #endregion


//                        #region 人数/台数
                byte[] datas7 = ("------------------------------------------------\n").getBytes("gbk");
                socketwriter.write(datas7, 0, datas7.length);//输出文字
                String strArray1Title = "\n\n           台数/人数\n\n";
                byte[] btArray1DataContent = (strArray1Title).getBytes("gbk");
                socketwriter.write(btArray1DataContent, 0, btArray1DataContent.length);

                //获取到台数/人数的内容  [结账单数&    12  ,结账人数&    24 ]
                String[] Array1 = PrintArray[1].split(",");
                for (String aArray1 : Array1) {
                    String[] Array1PrintContent = aArray1.split("&");
                    String Array1CurrentPrintContent = String.format("%1s      %2s\n", Array1PrintContent[0], Array1PrintContent[1]);
                    byte[] btArrayTemp = (Array1CurrentPrintContent).getBytes("gbk");
                    socketwriter.write(btArrayTemp, 0, btArrayTemp.length);
                }
//                        #endregion

                byte[] datas8 = ("------------------------------------------------\n").getBytes("gbk");
                socketwriter.write(datas8, 0, datas8.length);

//                        #region 菜品统计
                String strArray2Title = "\n\n          菜品分类统计\n\n";

                byte[] btArray2DataContent = (strArray2Title).getBytes("gbk");
                socketwriter.write(btArray2DataContent, 0, btArray2DataContent.length);
                //获取到按菜品统计的内容    [特色菜品&    16, 荤菜类&    1, 素菜类&    0, 锅底系列&    0, 小吃类&    1, 酒水&    0, 其他&    0,]
                String[] Array2 = PrintArray[2].split(",");
                //循环每一个菜品类别
                for (String aArray2 : Array2) {
                    String[] strCurrentProductTypes = aArray2.split("&");
                    String strProductContent = String.format("%1s%2s\n", AppentSpace(strCurrentProductTypes[0].trim()), strCurrentProductTypes[1].trim());
                    byte[] btProductArray = (strProductContent).getBytes("gbk");
                    socketwriter.write(btProductArray, 0, btProductArray.length);
                }
//                        #endregion

                byte[] datas9 = ("------------------------------------------------\n").getBytes("gbk");
                socketwriter.write(datas9, 0, datas9.length);

//                        #region 套餐/包席[情侣套餐 & 20]
                String[] ProductPackageArray = PrintArray[3].split(",");
                //if (ProductPackageArray.length > 1)
                //{
                for (String aProductPackageArray : ProductPackageArray) {
                    String[] strCurrentProductPackage = aProductPackageArray.split("&");
                    if (strCurrentProductPackage.length > 1) {
                        String strProductPackageContent = String.format("%1s%2s\n", AppentSpace(strCurrentProductPackage[0].trim()), strCurrentProductPackage[1].trim());
                        byte[] btProductPackageArray = (strProductPackageContent).getBytes("gbk");
                        socketwriter.write(btProductPackageArray, 0, btProductPackageArray.length);
                    }
                }
                //}
//                        #endregion
            }

            byte[] datas3 = ("           \n\n\n\n\n").getBytes("gbk");
            socketwriter.write(datas3, 0, datas3.length);//输出文字

            //切刀
            socketwriter.write(bty_tmp, 0, bty_tmp.length);
        }
        //socketwriter.close();
        //socketwriter.flush();

    }

//    public byte[] open_money() {
//        byte[] result = new byte[5];
//        result[0] = 27;
//        result[1] = 112;
//        result[2] = 48;
//        result[3] = 64;
//        result[4] = 0;
//        return result;
//    }

    private String getTime() {
        Long timeLong = System.currentTimeMillis();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeLong);


        return format.format(calendar.getTime());

    }

    private String AppentSpace(String value) {
        int intSpaceCount = (9 - value.length()) * 2;
        for (int i = 0; i < intSpaceCount; i++) {
            value += " ";
        }
        return value;
    }

//    private static void NewMethod(String id) {
//        String serverLogFile = ConfigurationManager.AppSettings["ServiceLogFile"];
//        if (Directory.Exists(serverLogFile)) {
//            using(FileStream fs = new FileStream(serverLogFile + "DncServiceLog.txt", FileMode.Append, FileAccess.Write))
//            {
//                using(StreamWriter sw = new StreamWriter(fs))
//                {
//                    String writeString = String.Format("{0} 日志编写：服务执行成功【消息：{1}】\r\n ", DateTime.Now, id);
//                    sw.WriteLine(writeString);
//                }
//            }
//        } else {
//            Directory.CreateDirectory(serverLogFile);
//            using(FileStream fs = new FileStream(serverLogFile + "DncServiceLog.txt", FileMode.Append, FileAccess.Write))
//            {
//                using(StreamWriter sw = new StreamWriter(fs))
//                {
//                    String writeString = String.Format("{0} 日志编写：服务执行成功【消息：{1}】\r\n ", DateTime.Now, id);
//                    sw.WriteLine(writeString);
//                }
//            }
//        }
//    }
}