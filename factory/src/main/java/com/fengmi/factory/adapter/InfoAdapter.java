package com.fengmi.factory.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fengmi.factory.R;
import com.fengmi.factory.views.InfoTagView;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.InfoAccessManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.ProjectorManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.UtilManagerInterf;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class InfoAdapter extends BaseAdapter {
    private String[] keyList = new String[]{
            "US-ID", "BT-MAC", "E-MAC", "UI-ID", "Free-Rom",
            "Serial-Number", "Product-ID", "DLP-Version"
    };
    private Timer timer;
    private HashMap<String, String> infoMap = new HashMap<>();
    private InfoAccessManagerInterf infoManager;
    private UtilManagerInterf utilManager;
    private ProjectorManagerInterf projectorManager;
    private Activity mContext;

    public InfoAdapter(Activity context) {
        mContext = context;
        infoManager = SDKManager.getInfoAccessManager();
        utilManager = SDKManager.getUtilManager();
        projectorManager = SDKManager.getProjectorManager();
        for (String key : keyList) {
            infoMap.put(key, "");
        }
    }

    @Override
    public int getCount() {
        return keyList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.info_item, null);
        InfoTagView infoTAG = view.findViewById(R.id.info_item);
        infoTAG.setTagName(keyList[position]);
        infoTAG.setTagValue(infoMap.get(keyList[position]));
        return view;
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String data = "";
                for (String key : keyList) {
                    switch (key) {
                        case "US-ID":
                            data = infoManager.readUSID();
                            break;
                        case "BT-MAC":
                            data = infoManager.getBluetoothMac();
                            break;
                        case "E-MAC":
                            data = infoManager.getEthernetMac();
                            break;
                        case "UI-ID":
                            data = infoManager.getPID();
                            break;
                        case "Free-Rom":
                            data = utilManager.readRomAvailSpace();
                            break;
                        case "LookSelect":
                            data = infoManager.getLookSelect();
                            break;
                        case "Product-ID":
                            data = infoManager.getFactoryPID();
                            break;
                        case "DLP-Version":
                            data = projectorManager.readDLPVersion(mContext);
                            break;
                        case "Serial-Number":
                            data = infoManager.getAssmSerialNumber();
                            break;
                    }
                    if (TextUtils.isEmpty(data)) {
                        data = "----";
                    }
                    infoMap.put(key, data);
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        }, 2 * 1000, 2 * 60 * 1000);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
