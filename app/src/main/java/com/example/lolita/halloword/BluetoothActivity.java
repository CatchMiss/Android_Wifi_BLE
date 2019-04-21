package com.example.lolita.halloword;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class BluetoothActivity extends Activity implements AdapterView.OnItemClickListener {

    private final static String TAG = "BluetoothActivity";

    private Context mContext;
    private ListView mBleListView;
    private BleDeviceAdapter mListAdapter;
    private ArrayList<BluetoothDevice> mBleDevices = new ArrayList<>();

    private BluetoothAdapter mBluetoothAdapter;
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBleDevices.add(device);
                mListAdapter.notifyDataSetChanged();
                Log.i(TAG,"find bleDevice");
            }else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                Toast.makeText(mContext, "扫描开始", Toast.LENGTH_SHORT).show();
                Log.i(TAG,"started discovery");
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                Toast.makeText(mContext, "扫描结束", Toast.LENGTH_SHORT).show();
                Log.i(TAG,"finished discovery");
            }else{
                Log.i(TAG,"ble receiver");
            }
        }
    };
    private OutputStream mBleOutstream;
    private Switch sw_ble;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        mContext = this;
        Log.i(TAG, "create");

        findViewById(R.id.bt_openBle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBluetooth();
            }
        });

        findViewById(R.id.bt_closeBle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeBluetooth();
            }
        });

        findViewById(R.id.bt_discoverBle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discoverBluetooth();
            }
        });

        mBleListView = findViewById(R.id.lv_ble);
        mListAdapter = new BleDeviceAdapter(mContext,mBleDevices);
        mBleListView.setAdapter(mListAdapter);
        mBleListView.setOnItemClickListener(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //注册广播接收者
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothReceiver,filter);

        sw_ble = findViewById(R.id.sw_ble);
        sw_ble.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    openBluetooth();
                }else {
                    closeBluetooth();
                }
            }
        });
        sw_ble.setChecked(mBluetoothAdapter.isEnabled());

    }

    /*
    * 打开蓝牙
    * */
    protected void openBluetooth(){
        if(!mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable();
            discoverBluetooth();
        }
    }

    /**
     * 关闭蓝牙
     * */
    protected void closeBluetooth(){
        if(mBluetoothAdapter.enable()){
            stopScanBluetooth();
            mBluetoothAdapter.disable();
        }
    }

    /*
    * 扫描设备,扫描结果通过广播接收
    * */
    protected void discoverBluetooth(){
        mBleDevices.clear();
        mListAdapter.notifyDataSetChanged();
        mBluetoothAdapter.startDiscovery();
    }

    protected void stopScanBluetooth(){
        mBleDevices.clear();
        mListAdapter.notifyDataSetChanged();
        mBluetoothAdapter.cancelDiscovery();
    }


    @Override
    protected void onDestroy() {

        unregisterReceiver(mBluetoothReceiver);
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BluetoothDevice device = mBleDevices.get(i);
        conn(device);
    }

    /*
    * 连接蓝牙设备
    * */
    private void conn(final BluetoothDevice device) {
        new Thread(){
            @Override
            public void run() {
                try {
                    BluetoothSocket bleSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(" "));
                    //连接
                    bleSocket.connect();
                    //获取输出流，往蓝牙写命令
                    mBleOutstream = bleSocket.getOutputStream();
                    //UI提示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "连接成功", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /*
    *   字符串转16进制
    * */
    private byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }


}
