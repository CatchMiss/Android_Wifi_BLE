package com.example.lolita.halloword;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络列表适配器
 * */
public class WifiListAdapter extends BaseAdapter {

    private ArrayList<ScanResult> mList;
    private Context mContext;

    public WifiListAdapter(Context context, ArrayList<ScanResult> list){
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        return null;
    }
}
