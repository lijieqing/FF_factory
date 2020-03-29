package com.fengmi.factory_test_interf.sdk_default;

import com.fengmi.factory_test_interf.sdk_interf.UtilManagerInterf;

public class UtilManagerDefault implements UtilManagerInterf {
    @Override
    public int lightSensorGetValue() {
        return 0;
    }

    @Override
    public boolean systemReset() {
        return false;
    }

    @Override
    public boolean touchpadSetStatus() {
        return false;
    }

    @Override
    public boolean remoteControlSetLock(String lock) {
        return false;
    }

    @Override
    public boolean systemSleepSetStatus() {
        return false;
    }

    @Override
    public boolean checkSystemPartition() {
        return false;
    }

    @Override
    public boolean ledStartFlash(int period) {
        return false;
    }

    @Override
    public boolean setLedLightStat(String style) {
        return false;
    }

    @Override
    public boolean ledStopFlash() {
        return false;
    }

    @Override
    public boolean systemSwitchMode() {
        return false;
    }

    @Override
    public boolean systemSwitchModeNew() {
        return false;
    }

    @Override
    public boolean systemReboot() {
        return false;
    }

    @Override
    public boolean systemShutdown() {
        return false;
    }

    @Override
    public boolean systemUpdateBootTimes() {
        return false;
    }

    @Override
    public int systemGetBootTimes() {
        return 0;
    }

    @Override
    public boolean sleepSystem() {
        return false;
    }

    @Override
    public boolean resetTvPanelSelect() {
        return false;
    }

    @Override
    public boolean setBtRcMac(String mac) {
        return false;
    }

    @Override
    public String getBtRcMac() {
        return "";
    }

    @Override
    public void bootupSystemDirect() {

    }

    @Override
    public String getProductModel() {
        return "";
    }

    @Override
    public boolean closeScreenSave2Sleep() {
        return false;
    }

    @Override
    public boolean systemRebootRecovery() {
        return false;
    }

    @Override
    public boolean systemModeSet() {
        return false;
    }

    @Override
    public boolean systemModeGet() {
        return false;
    }

    @Override
    public boolean setApplicationRid() {
        return false;
    }

    @Override
    public int[] getKeyTestSequence() {
        return new int[0];
    }

    @Override
    public boolean getSubWooferStat() {
        return false;
    }

    @Override
    public boolean setPowerStandbyStat(String stat) {
        return false;
    }

    @Override
    public boolean setI2CPinStat(String stat) {
        return false;
    }

    @Override
    public boolean systemMasterClear() {
        return false;
    }

    @Override
    public String getCpuTemp() {
        return "";
    }

    @Override
    public boolean setFanStat(String speed) {
        return false;
    }

    @Override
    public boolean setGpioOut(String portAstat) {
        return false;
    }

    @Override
    public String checkPanelIdTag() {
        return "";
    }

    @Override
    public int getGpioInStat(String port) {
        return 0;
    }

    @Override
    public boolean setVcom(String para) {
        return false;
    }

    @Override
    public byte getVcom(String para) {
        return 0;
    }

    @Override
    public String getDlpSn() {
        return "";
    }

    @Override
    public boolean setBodyDetectStatus(String para) {
        return false;
    }

    @Override
    public int getBodyDetectStatus() {
        return 0;
    }

    @Override
    public boolean startBodyDetectTest() {
        return false;
    }

    @Override
    public boolean stopBodyDetectTest() {
        return false;
    }

    @Override
    public int getBodyDetectCount(boolean isLeft) {
        return 0;
    }

    @Override
    public boolean setMotorScale(int delta) {
        return false;
    }

    @Override
    public String readRomTotalSpace() {
        return "";
    }

    @Override
    public String readRomAvailSpace() {
        return "";
    }

    @Override
    public boolean setFanSpeed(String level) {
        return false;
    }

    @Override
    public boolean resetLEDStepMotor() {
        return false;
    }

    @Override
    public String readLEDTemperature(String param) {
        return "";
    }

    @Override
    public String readRGBLEDCurrent() {
        return "";
    }

    @Override
    public boolean screenSaverEnable(boolean enbale) {
        return false;
    }

    @Override
    public boolean setProductRegion(String param) {
        return false;
    }

    @Override
    public String getProductRegion() {
        return "";
    }

    @Override
    public String getWifiCountryCode() {
        return "";
    }

    @Override
    public boolean setWifiCountryCode(String param) {
        return false;
    }

    @Override
    public boolean switchMCUFactoryMode(String param) {
        return false;
    }

    @Override
    public boolean rebootUpdate() {
        return false;
    }

    @Override
    public boolean writeFactoryMode(String param) {
        return false;
    }

    @Override
    public String readFactoryMode() {
        return null;
    }

    @Override
    public boolean writeBootEnv(String name, String val) {
        return false;
    }

    @Override
    public String readBootEnv(String name) {
        return null;
    }

    @Override
    public boolean writeUnifyKey(String name, String value) {
        return false;
    }

    @Override
    public String readUnifyKey(String name) {
        return null;
    }
}
