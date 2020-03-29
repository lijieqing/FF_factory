package com.fengmi.factory_test_interf.sdk_default;

import android.content.Context;

import com.fengmi.factory_test_interf.sdk_interf.SensorManagerInterf;

public class SensorManagerDefault implements SensorManagerInterf {
    @Override
    public boolean startTOFCalibration(int step) {
        return false;
    }

    @Override
    public String readTOFDistance() {
        return null;
    }

    @Override
    public byte[] readTOFCalibrationInfo() {
        return null;
    }

    @Override
    public boolean startTableCollecting(Context context, String distance, String times, String split) {
        return false;
    }

    @Override
    public String readDistanceStepTable() {
        return null;
    }

    @Override
    public boolean writeDistanceStepTable(String tableInfo) {
        return false;
    }

    @Override
    public boolean supportTOF() {
        return false;
    }

    @Override
    public boolean startTofAF(Context context) {
        return false;
    }
}
