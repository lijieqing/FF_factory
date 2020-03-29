package com.fengmi.factory_test_interf.sdk_default;

import com.fengmi.factory_test_interf.sdk_interf.KeyManagerInterf;

public class KeyManagerDefault implements KeyManagerInterf {
    @Override
    public boolean writeHDCP_RX_14(String datas) {
        return false;
    }

    @Override
    public boolean writeHDCP_RX_22(String datas) {
        return false;
    }

    @Override
    public boolean writeHDCP_TX_14(String datas) {
        return false;
    }

    @Override
    public boolean writeHDCP_TX_22(String datas) {
        return false;
    }

    @Override
    public byte[] readHDCP_RX_14() {
        return new byte[0];
    }

    @Override
    public byte[] readHDCP_RX_22() {
        return new byte[0];
    }

    @Override
    public byte[] readHDCP_TX_14() {
        return new byte[0];
    }

    @Override
    public byte[] readHDCP_TX_22() {
        return new byte[0];
    }

    @Override
    public boolean writeAttestationKey(byte[] datas) {
        return false;
    }

    @Override
    public byte[] readAttestationKey() {
        return new byte[0];
    }

    @Override
    public boolean writeWidevineKey(byte[] datas) {
        return false;
    }

    @Override
    public byte[] readWidevineKey() {
        return new byte[0];
    }

    @Override
    public boolean enableAllKey() {
        return false;
    }
}
