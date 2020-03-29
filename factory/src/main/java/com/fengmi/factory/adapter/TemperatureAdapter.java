package com.fengmi.factory.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fengmi.factory.R;
import com.fengmi.factory.views.InfoTagView;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_utils.ShellUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TemperatureAdapter extends BaseAdapter {
    private List<String> keyList;
    private Timer timer;
    private HashMap<String, String> infoMap = new HashMap<>();
    private Activity context;

    public TemperatureAdapter(Activity context) {
        this.context = context;
        keyList = SDKManager.getProjectorManager().getTemperatureNameList();
        for (String key : keyList) {
            infoMap.put(key, "");
        }
    }

    @Override
    public int getCount() {
        return keyList.size();
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
        View view = View.inflate(context, R.layout.info_item, null);
        InfoTagView tagView = view.findViewById(R.id.info_item);
        tagView.setTagName(infoMap.get(keyList.get(position)));
        tagView.setTagValue(keyList.get(position));
        return view;
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < keyList.size(); i++) {
                    try {
                        String temp = ShellUtil.execCommand(SDKManager.getProjectorManager().getTemperatureCommandList().get(i)).trim();
                        Log.d("Factory_temp", "name is " + keyList.get(i));
                        Log.d("Factory_temp", "temp is " + temp);
                        if (temp.length() == 5) {
                            Log.d("Factory_temp", keyList.get(i));
                            DecimalFormat format = new DecimalFormat("0.0");
                            int t = Integer.parseInt(temp);
                            float tf = t / 1000f;
                            temp = format.format(tf);
                        }
                        if (temp.length() == 3) {
                            Log.d("Factory_temp", keyList.get(i));
                            DecimalFormat format = new DecimalFormat("0.0");
                            int t = Integer.parseInt(temp);
                            float tf = t / 10f;
                            temp = format.format(tf);
                        }
                        infoMap.put(keyList.get(i), temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500, 3 * 1000);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
