package com.cll.sample.downloadnet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cll on 2018/1/23.
 */

public class NetStatusUtils {





    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        return networkInfo;
    }

    public static String getDeviceName(){
        String deviceName = Build.MANUFACTURER + "_" + Build.MODEL;
        return deviceName;
    }

    public static boolean isWifiNet(Context context){
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo != null && networkInfo.isConnected()){
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                return true;
            }
        };
        return false;
    }

    public static void WifiAddressAndSignalLevel(Context context,int networkType){

        WifiManager mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        switch (networkType){
            case ConnectivityManager.TYPE_WIFI:
                String ipAddress = intToIp(mWifiInfo.getIpAddress());
                int wifiSignalLevel = WifiManager.calculateSignalLevel(mWifiInfo.getRssi(),5);
                break;
            case ConnectivityManager.TYPE_MOBILE:
                break;
            case  ConnectivityManager.TYPE_ETHERNET:
                String ethernetIpAddress = getEthernetIpAddress();
                break;
        }
    }
    public static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    public static final String IP_ADDRESS = "dhcp.eth0.ipaddress";
    public static String getEthernetIpAddress() {
        Class<?> clazz;
        try {
            clazz = Class.forName("android.os.SystemProperties");
            Method mMethod = clazz.getDeclaredMethod("get", String.class);
            Object mObject = clazz.newInstance();
            String ipAddress = (String) mMethod.invoke(mObject, IP_ADDRESS);
            return ipAddress;
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    public class Status{
        private boolean connected;
        private int networkType;
        private int wifiSignalLevel;
        private String ipAddress;
        private String deviceName;
        private String ethernetIpAddress;
    }
}
