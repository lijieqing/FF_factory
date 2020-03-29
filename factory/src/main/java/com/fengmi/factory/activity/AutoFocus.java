package com.fengmi.factory.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;
import com.fengmi.factory_test_interf.sdk_interf.AFCallback;

public class AutoFocus extends BaseActivity implements AFCallback {
    private TextView tv;

    @Override
    public void onAFStart() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText("自动对焦中，请等待");
            }
        });
    }

    @Override
    public void onAFFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText("自动对焦完成，请继续");
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_focus);
        tv = findViewById(R.id.tv_auto_focus);
        SDKManager.getMotorManager().setEventCallback(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SDKManager.getMotorManager().unsetEventCallback(this, this);
    }

    @Override
    protected void handleControlMsg(int cmdType, String cmdID, String cmdPara) {
        if (FactorySetting.COMMAND_TASK_STOP == cmdType) {
            SDKManager.getMotorManager().stopAutoFocus(this);
            finish();
            if (!cmdPara.equals("fake")) {//不是内部广播，正常通讯，并返回数据
                setResult(cmdID, PASS, true);
            }
        } else {
            int cmd = Integer.parseInt(cmdID, 16);
            if (cmd == 0x14D5) {
                SDKManager.getMotorManager().startAutoFocus(this);
                if (!cmdPara.equals("fake")) {//不是内部广播，正常通讯，并返回数据
                    setResult(cmdID, PASS, false);
                }
            }
        }
    }
}
