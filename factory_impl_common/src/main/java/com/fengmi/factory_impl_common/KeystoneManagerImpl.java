package com.fengmi.factory_impl_common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.FengManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.KeystoneManagerInterf;


public class KeystoneManagerImpl implements KeystoneManagerInterf {

    @Override
    public boolean setKeyStoneMode(Context ctx, String param) {

        Log.d(TAG, "0 is ON param=" + param);
        if (param.equals("0")) {
            //enable keystone mode
            SDKManager.getFengManagerInterf().SetKeystoneSelectMode(FengManagerInterf.KEYSTONE_8_POINTS_MODE);
            SDKManager.getFengManagerInterf().SetKeystoneInit();
            return true;
        } else if (param.equals("1")) {
            SDKManager.getFengManagerInterf().SetKeystoneReset();

            SDKManager.getFengManagerInterf().SetKeystoneSave();
            SDKManager.getFengManagerInterf().SetKeystoneLoad();
            SDKManager.getFengManagerInterf().SetKeystoneCancel();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setKeyStoneDirect(Context ctx, String param) {
        String[] datas = param.split(",");
        if (datas.length != 3) {
            return false;
        }
        for (String data : datas) {
            if (!TextUtils.isDigitsOnly(data)) {
                return false;
            }
        }
        int res = SDKManager.getFengManagerInterf().SetKeystoneSet(
                Integer.parseInt(datas[0]),
                Integer.parseInt(datas[1]),
                Integer.parseInt(datas[2])
        );

        Log.d(TAG, "SetKeystoneSet " + res);
        return true;
    }

    @Override
    public boolean setKeyStoneSets() {
        return false;
    }

    @Override
    public boolean openAutoKeystoneFunc() {
        return false;
    }

    @Override
    public boolean closeAutoKeystoneFunc() {
        return false;
    }

}
