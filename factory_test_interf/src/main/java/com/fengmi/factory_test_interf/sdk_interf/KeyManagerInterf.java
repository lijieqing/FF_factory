package com.fengmi.factory_test_interf.sdk_interf;

import android.content.Context;

public interface KeyManagerInterf extends BaseMiddleware {

     boolean writeHDCP_RX_14(String datas);

     boolean writeHDCP_RX_22(String datas);

     boolean writeHDCP_TX_14(String datas);

     boolean writeHDCP_TX_22(String datas);

     byte[] readHDCP_RX_14();

     byte[] readHDCP_RX_22();

     byte[] readHDCP_TX_14();

     byte[] readHDCP_TX_22();

     boolean writeAttestationKey(byte[] datas);

     byte[] readAttestationKey();

     boolean writeWidevineKey(byte[] datas);

     byte[] readWidevineKey();

     boolean enableAllKey();

}
