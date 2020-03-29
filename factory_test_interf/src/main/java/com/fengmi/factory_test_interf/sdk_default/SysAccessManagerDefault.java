package com.fengmi.factory_test_interf.sdk_default;

import com.fengmi.factory_test_interf.sdk_interf.SysAccessManagerInterf;

public class SysAccessManagerDefault implements SysAccessManagerInterf {
    @Override
    public boolean enableScreenCheck(String param) {
        return false;
    }

    @Override
    public boolean screenCheck(int mode) {
        return false;
    }

    @Override
    public boolean syncDlpInfo() {
        return false;
    }

    @Override
    public boolean saveDlpInfo() {
        return false;
    }

    @Override
    public boolean setWheelDelay(int delay) {
        return false;
    }

    @Override
    public int getWheelDelay() {
        return 0;
    }

    @Override
    public boolean enableXPRCheck(String param) {
        return false;
    }

    @Override
    public boolean enableXPRShake(String param) {
        return false;
    }

    @Override
    public boolean checkGSensorFunc() {
        return false;
    }

    @Override
    public boolean startGSensorCollect() {
        return false;
    }

    @Override
    public boolean saveGSensorStandard() {
        return false;
    }

    @Override
    public String readGSensorStandard() {
        return "";
    }

    @Override
    public String readGSensorHorizontalData() {
        return "";
    }

    @Override
    public String readDLPVersion() {
        return "";
    }

    @Override
    public boolean setKeyStoneMode(String param) {
        return false;
    }

    @Override
    public boolean setKeyStoneDirect(String param) {
        return false;
    }

    @Override
    public boolean writeProjectorCMD(String param) {
        return false;
    }

    @Override
    public String readProjectorCMD(String param) {
        return "";
    }

    @Override
    public boolean writeI2CCMD(String param) {
        return false;
    }

    @Override
    public String readI2CCMD(String param) {
        return "";
    }

    @Override
    public boolean sendKeyCode(String param) {
        return false;
    }
}
