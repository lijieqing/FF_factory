package com.fengmi.factory_test_interf.sdk_default;

import com.fengmi.factory_test_interf.sdk_interf.InfoAccessManagerInterf;

public class InfoAccessManagerDefault implements InfoAccessManagerInterf {
    public InfoAccessManagerDefault() {
    }

    @Override
    public boolean setPcbaSerialNumber(String sn) {
        return false;
    }

    @Override
    public String getPcbaSerialNumber() {
        return "";
    }

    @Override
    public boolean setPcbaManufactureNumber(String mn) {
        return false;
    }

    @Override
    public String getPcbaManufactureNumber() {
        return "";
    }

    @Override
    public boolean setAssmSerialNumber(String sn) {
        return false;
    }

    @Override
    public String getAssmSerialNumber() {
        return "";
    }

    @Override
    public boolean setAssmManufactureNumber(String mn) {
        return false;
    }

    @Override
    public String getAssmManufactureNumber() {
        return "";
    }

    @Override
    public boolean setBluetoothMac(String mac) {
        return false;
    }

    @Override
    public String getBluetoothMac() {
        return "";
    }

    @Override
    public boolean setWifiMac(String mac) {
        return false;
    }

    @Override
    public String getWifiMac() {
        return "";
    }

    @Override
    public boolean setEthernetMac(String mac) {
        return false;
    }

    @Override
    public String getEthernetMac() {
        return "";
    }

    @Override
    public boolean setHdcp14Key(String mac) {
        return false;
    }

    @Override
    public byte[] getHdcp14Key() {
        return new byte[0];
    }

    @Override
    public boolean setHdcp20Key(String mac) {
        return false;
    }

    @Override
    public byte[] getHdcp20Key() {
        return new byte[0];
    }

    @Override
    public boolean setHdcp14TxKey(String key) {
        return false;
    }

    @Override
    public byte[] getHdcp14TxKey() {
        return new byte[0];
    }

    @Override
    public boolean setHdcp22TxKey(String key) {
        return false;
    }

    @Override
    public byte[] getHdcp22TxKey() {
        return new byte[0];
    }

    @Override
    public byte[] getHdcpKsv() {
        return new byte[0];
    }

    @Override
    public String getFirmwareVer() {
        return "";
    }

    @Override
    public String getModelName() {
        return "";
    }

    @Override
    public boolean setHdcpKeyM11(String mac) {
        return false;
    }

    @Override
    public byte[] getHdcpKeyM11() {
        return new byte[0];
    }

    @Override
    public boolean transHdcpKeyM11() {
        return false;
    }

    @Override
    public boolean verHdcpKeyM11() {
        return false;
    }

    @Override
    public boolean setWifiFreqOffset(String offset) {
        return false;
    }

    @Override
    public byte getWifiFreqOffset() {
        return 0;
    }

    @Override
    public boolean setPID(String pid) {
        return false;
    }

    @Override
    public String getPID() {
        return "";
    }

    @Override
    public boolean setFactoryPID(String fid) {
        return false;
    }

    @Override
    public String getFactoryPID() {
        return "";
    }

    @Override
    public boolean setLookSelect(String ls) {
        return false;
    }

    @Override
    public String getLookSelect() {
        return "";
    }

    @Override
    public String readKeyMD5(String keyName) {
        return "";
    }

    @Override
    public String readUSID() {
        return "";
    }

    @Override
    public boolean writeUSID(String param) {
        return false;
    }

    @Override
    public String getKeyActiveStatus(String param) {
        return "";
    }

    @Override
    public boolean setWidewineKey(String param) {
        return false;
    }

    @Override
    public byte[] getWidewineKey() {
        return new byte[0];
    }

    @Override
    public boolean setAttestationKey(String param) {
        return false;
    }

    @Override
    public byte[] getAttestationKey() {
        return new byte[0];
    }

    @Override
    public boolean setKeyActive(String param) {
        return false;
    }

    @Override
    public boolean setNetflixKey(String param) {
        return false;
    }

    @Override
    public byte[] getNetflixKey() {
        return new byte[0];
    }

    @Override
    public boolean setTernaryKey(String param) {
        return false;
    }

    @Override
    public byte[] getTernaryKey() {
        return new byte[0];
    }
}
