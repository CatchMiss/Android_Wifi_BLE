package com.example.lolita.halloword;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BleDeviceAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> mDevices;
    private Context mContext;

    public BleDeviceAdapter(Context context, ArrayList<BluetoothDevice> devices){
        mContext = context;
        mDevices = devices;
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.item_ble,null);

            holder.mTvName = view.findViewById(R.id.tv_name);
            holder.mTvAddress = view.findViewById(R.id.tv_address);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        BluetoothDevice device = mDevices.get(i);
        holder.mTvName.setText(device.getName());
        holder.mTvAddress.setText(device.getAddress());

        return view;
    }

    class ViewHolder{
        TextView mTvName;
        TextView mTvAddress;
    }

}
