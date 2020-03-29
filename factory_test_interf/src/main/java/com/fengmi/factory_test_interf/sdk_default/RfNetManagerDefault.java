package com.fengmi.factory_test_interf.sdk_default;

import android.net.wifi.ScanResult;

import com.fengmi.factory_test_interf.sdk_interf.RfNetManagerInterf;

import java.util.List;

public class RfNetManagerDefault implements RfNetManagerInterf {
    @Override
    public boolean wifiSetStatus(boolean stat) {
        return false;
    }

    @Override
    public boolean wifiGetStatus() {
        return false;
    }

    @Override
    public boolean wifiConnectAp(String ssid) {
        return false;
    }

    @Override
    public boolean wifiThroughputStart() {
        return false;
    }

    @Override
    public boolean wifiThroughputWithParameter(String para) {
        return false;
    }

    @Override
    public boolean wifiThroughputStop() {
        return false;
    }

    @Override
    public String wifiGetIpAddr() {
        return "";
    }

    @Override
    public int wifiGetRssi() {
        return 0;
    }

    @Override
    public byte[] wifiGetRssiBox() {
        return new byte[0];
    }

    @Override
    public boolean wifiDisconnect() {
        return false;
    }

    @Override
    public boolean wifiStartScan() {
        return false;
    }

    @Override
    public boolean btSetStatus(boolean stat) {
        return false;
    }

    @Override
    public boolean btSetStatusBLE(boolean stat) {
        return false;
    }

    @Override
    public boolean btGetStatus() {
        return false;
    }

    @Override
    public boolean btStartScan() {
        return false;
    }

    @Override
    public int btGetRssi(String mac) {
        return 0;
    }

    @Override
    public boolean ethernetSetStatus(boolean stat) {
        return false;
    }

    @Override
    public boolean ethernetGetStatus() {
        return false;
    }

    @Override
    public boolean ethernetPingAp(String ipaddr) {
        return false;
    }

    @Override
    public boolean wifiPingAp(String ipaddr) {
        return false;
    }

    @Override
    public byte[] nonSigHciCmdRun(String param) {
        return new byte[0];
    }

    @Override
    public boolean nonSigCloseNormalBt() {
        return false;
    }

    @Override
    public boolean nonSigOpenNormalBt() {
        return false;
    }

    @Override
    public int nonSigIsBleSupport() {
        return 0;
    }

    @Override
    public int nonSigInitBt() {
        return 0;
    }

    @Override
    public int nonSigUninitBt() {
        return 0;
    }

    @Override
    public int nonSigBleGetChipId() {
        return 0;
    }

    @Override
    public boolean enterWifi() {
        return false;
    }

    @Override
    public boolean exitWifi() {
        return false;
    }

    @Override
    public boolean startWifiRx(String param) {
        return false;
    }

    @Override
    public String stopWifiRx() {
        return "";
    }

    @Override
    public boolean startWifiTx(String param) {
        return false;
    }

    @Override
    public boolean stopWifiTx() {
        return false;
    }

    @Override
    public List<ScanResult> wifiGetScanList() {
        return null;
    }

    @Override
    public boolean setBtRcMac(String param) {
        return false;
    }

    @Override
    public List btGetList() {
        return null;
    }
}
