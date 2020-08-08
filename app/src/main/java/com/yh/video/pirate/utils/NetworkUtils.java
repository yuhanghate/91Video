package com.yh.video.pirate.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtils {

    /**
     * 获取wifi的mac地址，适配到android Q
     * @param paramContext
     * @return
     */
    public static String getMac(Context paramContext) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                String str = getMacMoreThanM(paramContext);
                if (!TextUtils.isEmpty(str))
                    return str;
            }
            // 6.0以下手机直接获取wifi的mac地址即可
            WifiManager wifiManager = (WifiManager)paramContext.getSystemService("wifi");
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null)
                return wifiInfo.getMacAddress();
        } catch (Throwable throwable) {}
        return null;
    }

    /**
     * android 6.0+获取wifi的mac地址
     * @param paramContext
     * @return
     */
    public static String getMacMoreThanM(Context paramContext) {
        try {
            //获取本机器所有的网络接口
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface)enumeration.nextElement();
                //获取硬件地址，一般是MAC
                byte[] arrayOfByte = networkInterface.getHardwareAddress();
                if (arrayOfByte == null || arrayOfByte.length == 0) {
                    continue;
                }

                StringBuilder stringBuilder = new StringBuilder();
                for (byte b : arrayOfByte) {
                    //格式化为：两位十六进制加冒号的格式，若是不足两位，补0
                    stringBuilder.append(String.format("%02X:", new Object[] { Byte.valueOf(b) }));
                }
                if (stringBuilder.length() > 0) {
                    //删除后面多余的冒号
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                String str = stringBuilder.toString();
                // wlan0:无线网卡 eth0：以太网卡
                if (networkInterface.getName().equals("wlan0")) {
                    return str;
                }
            }
        } catch (SocketException socketException) {
            return null;
        }
        return null;
    }
}
