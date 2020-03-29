package com.fengmi.factory_test_interf.sdk_default;

import android.content.Context;

import com.fengmi.factory_test_interf.sdk_interf.AFCallback;
import com.fengmi.factory_test_interf.sdk_interf.MotorManagerInterf;

public class MotorManagerDefault implements MotorManagerInterf {
    @Override
    public boolean setMotorScale(Context context, int scale) {
        return false;
    }

    @Override
    public void setEventCallback(Context context, AFCallback afc) {

    }

    @Override
    public void unsetEventCallback(Context context, AFCallback afc) {

    }

    @Override
    public void startAutoFocus(Context context) {

    }

    @Override
    public void stopAutoFocus(Context context) {

    }

    @Override
    public String getCalibration(int index) {
        return null;
    }

    @Override
    public boolean startAFCheck() {
        return false;
    }

    @Override
    public int readAFCheckCallback() {
        return 0;
    }
}
