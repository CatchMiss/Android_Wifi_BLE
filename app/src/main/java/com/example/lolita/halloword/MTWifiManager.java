package com.example.lolita.halloword;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 *  wifi管理类
 *
 * */
public class MTWifiManager {

    //单例模式
    private static MTWifiManager mtWifimgr = null;
    public static MTWifiManager getInstance(Context context){
        if(mtWifimgr == null){
            mtWifimgr = new MTWifiManager(context);
            return mtWifimgr;
        }
        return null;
    }
    private MTWifiManager(Context context) {
        this.mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.mWifiInfo = mWifiManager.getConnectionInfo();
        this.mWifiConfigList = mWifiManager.getConfiguredNetworks();
    }

    private List<WifiConfiguration> mWifiConfigList;
    private List<ScanResult> mWifiList;

    //WifiManager.WifiLock mWifiLock;

    private WifiInfo mWifiInfo;
    public WifiManager mWifiManager;

    /**打开Wifi**/
    public void OpenWifi() {
        if(!this.mWifiManager.isWifiEnabled()){
            this.mWifiManager.setWifiEnabled(true);
        }
    }
    /**关闭Wifi**/
    public void closeWifi() {
        if(mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }
    /**端口指定id的wifi**/
    public void disconnectWifi(int paramInt) {
        this.mWifiManager.disableNetwork(paramInt);
    }

    /**添加指定网络**/
    public void addNetwork(WifiConfiguration paramWifiConfiguration) {
        int i = mWifiManager.addNetwork(paramWifiConfiguration);
        mWifiManager.enableNetwork(i, true);
    }

    /**
     * 连接指定配置好的网络
     * @param index 配置好网络的ID
     */
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfigList.size()) {
            return;
        }
        //连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfigList.get(index).networkId, true);
    }

    /**获取wifi名**/
    public String getSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
    }

    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    /**得到配置好的网络 **/
    public List<WifiConfiguration> getConfiguration() {
        return this.mWifiConfigList;
    }

    /**获取ip地址**/
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    /**获取物理地址(Mac)**/
    @SuppressLint("HardwareIds")
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    /**获取网络id**/
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    /**获取wifi连接信息**/
    public WifiInfo getWifiInfo() {
        return this.mWifiManager.getConnectionInfo();
    }
    /** 得到网络列表**/
    public List<ScanResult> getWifiList() {
        return this.mWifiList;
    }

    /**查看扫描结果**/
    public StringBuilder lookUpScan() {
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++)
        {
            localStringBuilder.append("Index_"+new Integer(i + 1).toString() + ":");
            //将ScanResult信息转换成一个字符串包
            //其中把包括：BSSID、SSID、capabilities、frequency、level
            localStringBuilder.append((mWifiList.get(i)).toString());
            localStringBuilder.append("\n");
        }
        return localStringBuilder;
    }

    /** 设置wifi搜索结果 **/
    public void setWifiList() {
        this.mWifiList = this.mWifiManager.getScanResults();
    }
    /**开始搜索wifi**/
    public void startScan() {
        this.mWifiManager.startScan();
    }
}
