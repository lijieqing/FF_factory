package com.fengmi.factory;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fengmi.factory.adapter.InfoAdapter;
import com.fengmi.factory.adapter.TemperatureAdapter;
import com.fengmi.factory.service.CommandService;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_data.Hardware;
import com.fengmi.factory_test_interf.sdk_utils.SPUtil;
import com.fengmi.factory_test_interf.sdk_utils.ThreadUtils;
import com.fengmi.factory_test_interf.sdk_utils.WindowUtil;

import java.util.Arrays;

public class FactoryLauncher extends Activity {

    private static final String TAG = "FactoryLauncher";
    private TextView mTV_FW_Version;
    private TextView mTV_FW_Name;
    private TextView mTV_Temp_Status;

    private TemperatureAdapter tempAdapter;
    private InfoAdapter infoAdapter;
    private ListView infoList;
    private ListView tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "----------FactoryLauncher----------");
        Log.d(TAG, "---------- onCreate ----------");
        Log.d(TAG, "----------FactoryLauncher----------");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent serviceIntent = new Intent(this, CommandService.class);
        startService(serviceIntent);
        bindService(serviceIntent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "----------ServiceConnection " + name + " Connected----------");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "----------ServiceConnection " + name + " Disconnected----------");
            }
        }, Context.BIND_AUTO_CREATE);

        setContentView(R.layout.factory_launcher);

        tempAdapter = new TemperatureAdapter(this);
        infoAdapter = new InfoAdapter(this);

        mTV_FW_Version = findViewById(R.id.tv_factory_version);
        mTV_FW_Name = findViewById(R.id.tv_build_name);
        mTV_Temp_Status = findViewById(R.id.tv_temp_status);
        infoList = findViewById(R.id.lv_info);
        tempList = findViewById(R.id.lv_temp);

        infoList.setAdapter(infoAdapter);
        tempList.setAdapter(tempAdapter);

        android.provider.Settings.Secure.putString(getContentResolver(), "default_input_method", "com.baidu.input/.ImeService");
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown KeyCode " + keyCode);
        if (keyCode == 20) {
            Log.d(TAG, "onKeyDown KeyCode " + keyCode);
            ThreadUtils.runCmdOnBack("input keyevent 23", 1000);
        }
        if (keyCode == 68) {//键盘'~'事件
            Log.d(TAG, "show dialog");
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View menuView = inflater.inflate(R.layout.main, null);
            ListView listView = menuView.findViewById(R.id.lv_menu);
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arrays.asList("1111", "2222", "3333", "4444", "5555"));
            listView.setAdapter(adapter);
            WindowUtil.showMenu(this, menuView);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryFWVersion();
        infoAdapter.startTimer();
        tempAdapter.startTimer();
    }

    private void queryFWVersion() {
        String version = SDKManager.getAndroidOSManagerInterf().getSystemProperty("ro.build.version.incremental", "XXXX");
        String buildName = SDKManager.getAndroidOSManagerInterf().getSystemProperty("ro.build.product", "XXXX");
        if (SDKManager.getHwManager() != null) {
            Hardware hw = SDKManager.getHwManager().getHW();
            if (hw != null) {
                buildName = "项目名称：" + hw.getName() +
                        "\n硬件 ID：" + hw.getHardwareID() +
                        "\n描述信息：" + hw.getDesc() +
                        "\n工厂版本：" + version;
            }
        } else {
            buildName = "项目名称：" + buildName + "\n工厂版本：" + version;
        }
        mTV_FW_Name.setText(buildName);

        boolean tempStatus = (boolean) SPUtil.getParam(this, "temp_check_status", false);
        String tempData = (String) SPUtil.getParam(this, "temp_check_data", "----");

        Log.d(TAG, "tempStatus: " + tempStatus);
        Log.d(TAG, "tempData: " + tempData);
        if (tempStatus) {
            mTV_Temp_Status.setVisibility(View.VISIBLE);
            mTV_Temp_Status.setText(tempData);
        } else {
            mTV_Temp_Status.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        infoAdapter.stopTimer();
        tempAdapter.stopTimer();
    }
}