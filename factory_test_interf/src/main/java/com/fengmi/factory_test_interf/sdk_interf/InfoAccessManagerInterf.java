/*
 * Copyright (C) 2013 XiaoMi Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 本接口定义了如下信息的存取：
 * 1. PCBA SN的读写 （setPcbaSerialNumber, getPcbaSerialNumber）
 * 2. PCBA MN的读写 （setPcbaManufactureNumber, getPcbaManufactureNumber）
 * 1.1. ASSM SN的读写 （setSerialNumber, getSerialNumber）
 * 2.1. ASSM MN的读写 （setManufactureNumber, getManufactureNumber）
 * 3. BT MAC的读写（setBluetoothMac, getBluetoothMac）
 * 4. WIFI MAC的读写（setWifiMac, getWifiMac）
 * 5. Ethernet MAC的读写（setEthernetMac, getEthernetMac）
 * 6. HDCP key 1.4的读写（setHdcp14key, getHdcp14key）
 * 7. HDCP key 2.0的读写（setHdcp20key, getHdcp20key）
 * ********BOX HDCP***********************************
 * 6. HDCP key 的读写（setHdcpkey, getHdcpkey）
 * 7. HDCP key 的设定/验证（transHdcpkey, verHdcpkey）
 * ********OX HDCP***********************************
 * 8. FIRMWARE Ver的读取（getFirmwareVer)
 * 9. Model Name的读取（getModelName)
 * 10. Projector PID 读写 (setPID,getPID)
 * note: 由于java的限制，一次性写入的字符串长度不能大于65534个
 * note: 工厂端对mac, sn, mn等信息的操作必须是从prop或着nvram读写；我们不能直接
 * 从模块中读取它。
 */

package com.fengmi.factory_test_interf.sdk_interf;


public interface InfoAccessManagerInterf extends BaseMiddleware {
     String HDCP_14_FILEPATH = "/data/misc/hdcp14_rxkey.bin";
     String HDCP_20_FILEPATH = "/data/misc/hdcp22_rxkey.bin";
     String HDCP_14_TX_FILEPATH = "/data/misc/hdcp14_txkey.bin";
     String HDCP_22_TX_FILEPATH = "/data/misc/hdcp22_txkey.bin";

    String TERNARY_KEY_FILEPATH = "/data/misc/ternary.bin";
     String NETFLIX_KEY_FILEPATH = "/data/misc/netflix.bin";
     String WIDEWINE_KEY_FILEPATH = "/data/misc/widewine.bin";
     String PLAYREADY_PUBLIC_KEY_FILEPATH = "/system/factory/playready_pub.bin";
     String PLAYREADY_PRIVATE_KEY_FILEPATH = "/system/factory/playready_pri.bin";
     String ATTESTATION_KEY_FILEPATH = "/data/misc/attestation.bin";
     String AML_SECURE_BOOT_KEY_FILEPATH = "/system/factory/secure_boot.bin";
     int HDCP_14_RX_LEN_O = 348;
     int HDCP_14_RX_LEN_P = 328;
     int HDCP_14_TX_LEN = 288;
     int HDCP_20_RX_LEN_O = 3192;
     int HDCP_20_RX_LEN_P = 5320;
     int HDCP_20_TX_LEN = 32;
     int WIDEWINE_KEY_LEN = 128;
     int ATTESTATION_KEY_LEN = 8967;
     int PLAYREADY_PUB_KEY_LEN = 1276;
     int PLAYREADY_PRI_KEY_LEN = 32;
     int SECURE_BOOT_KEY_LEN = 1024;
     int NETFLIX_KEY_LEN = 114;
     int TERNARY_KEY_LEN = 39;

    String BUILD_MODE_DEBUG = "userdebug";
     String BUILD_MODE_USER = "user";
     String KEY_ACTIVE_STATUS_ON = "ON";
     String KEY_ACTIVE_STATUS_OFF = "OFF";
    /*===========================================local functions=====================*/
     String USID = "usid";
     String PCBA_SERIAL = "pcba_sn";
     String PCBA_MANUFACTURE = "pcba_mn";
     String ASSM_SERIAL = "assm_sn";
     String ASSM_MANUFACTURE = "assm_mn";
     String BT_MAC = "mac_bt";
     String WIFI_MAC = "WIFI_MAC";
     String ETH_MAC = "mac";
     String PRODUCT_ID = "product_id";
     String FACTORY_PID = "fact_product_id";
     String LOOK_SELECT = "look_select";
     String MI_TERNARY = "mi_ternary";
     String HDCP_MD5 = "md5_hdcp";
     String VERSION_INCREMENTAL = "ro.build.version.incremental";
     String BUILD_TYPE = "ro.build.type";
     String BUILD_MODELNAME = "ro.build.product";
     String ERROR_RESULT = "xxxx";

    /**
     * 1
     * Set Serial Number (SN); write serial number into system at PCBA stage.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return success or no.
     */
     boolean setPcbaSerialNumber(String sn);

    /**
     * 2
     * Get Serial Number (SN); Read serial number from system at PCBA stage.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return the serial number value in String type.
     */
     String getPcbaSerialNumber();

    /**
     * 3
     * Set Manufacture Number (MN); Write manufacture number into system at PCBA stage.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return success or no.
     */
     boolean setPcbaManufactureNumber(String mn);

    /**
     * 4
     * Get Manufacture Number (MN); Read manufacture number into system at PCBA stage.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return the manufacture number value in String type.
     */
     String getPcbaManufactureNumber();

    /**
     * 5
     * Set Serial Number (SN); write serial number into system at ASSMBLY stage.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return success or no.
     */
     boolean setAssmSerialNumber(String sn);

    /**
     * 6
     * Get Serial Number (SN); Read serial number from system at ASSMBLY stage.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return the serial number value in String type.
     */
     String getAssmSerialNumber();

    /**
     * 7
     * Set Manufacture Number (MN); Write manufacture number into system at ASSMBLY stage.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return success or no.
     */
     boolean setAssmManufactureNumber(String mn);

    /**
     * 8
     * Get Manufacture Number (MN); Read manufacture number into system at ASSMBLY stage.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return the manufacture number value in String type.
     */
     String getAssmManufactureNumber();

    /**
     * 9
     * Set Bluetooth MAC (BT MAC); Write BT MAC into system.
     * Note: if just use one API, just use PCBA stage API, and ingore ASSM API
     *
     * @return success or no.
     */
     boolean setBluetoothMac(String mac);

    /**
     * 10
     * Get Bluetooth MAC (BT MAC); Read BT MAC into system.
     *
     * @return the Bluetooth MAC in String type.
     */
     String getBluetoothMac();

    /**
     * 11
     * Set Wifi MAC (WIFI MAC); Write WIFI MAC into system.
     *
     * @return success or no.
     */
     boolean setWifiMac(String mac);

    /**
     * 12
     * Get Wifi MAC (WIFI MAC); Read WIFI MAC into system.
     *
     * @return the WIFI MAC in String type.
     */
     String getWifiMac();

    /**
     * 13
     * Set Ethernet MAC (ETH MAC); Write Ethernet MAC into system.
     *
     * @return success or no.
     */
     boolean setEthernetMac(String mac);

    /**
     * 14
     * Get Ethernet MAC (ETH MAC); Read Ethernet MAC into system.
     *
     * @return the Ethernet MAC in String type.
     */
     String getEthernetMac();

    /**
     * 15
     * Set HDCP KEY (1.4); Write HDCP Key 1.4  into system.
     * note: here raw data should be sent to, that is to say, what you received from port, what you should be sent to here.
     * now all product follow this rule; I hope it be upgrade.
     *
     * @return success or no.
     */
     boolean setHdcp14Key(String mac);

    /**
     * 16
     * Get HDCP Key (1.4); Read HDCP Key 1.4 into system.
     *
     * @return the HDCP Key 1.4 in byte array type.
     */
     byte[] getHdcp14Key();

    /**
     * 17
     * Set HDCP KEY (2.0); Write HDCP Key 2.0  into system.
     * note: here raw data should be sent to, that is to say, what you received from port, what you should be sent to here.
     * now all product follow this rule; I hope it be upgrade.
     *
     * @return success or no.
     */
     boolean setHdcp20Key(String mac);

    /**
     * 18
     * Get HDCP Key (2.0); Read HDCP Key 2.0 into system.
     *
     * @return the HDCP Key 2.0 in byte array type.
     */
     byte[] getHdcp20Key();

    /**
     * 19
     * Get HDCP TX Key (1.4); write HDCP Key 1.4 into system.
     *
     * @return success or no.
     */
     boolean setHdcp14TxKey(String key);

    /**
     * 20
     * Get HDCP TX Key (1.4); Read HDCP Key 1.4 into system.
     *
     * @return the HDCP Key 1.4 in byte array type.
     */
    byte[] getHdcp14TxKey();

    /**
     * 21
     * Get HDCP TX Key (2.2); write HDCP Key 2.2 into system.
     *
     * @return success or no.
     */
    boolean setHdcp22TxKey(String key);

    /**
     * 22
     * Get HDCP TX Key (2.2); Read HDCP Key 2.2 into system.
     *
     * @return the HDCP Key 2.2 in byte array type.
     */
    byte[] getHdcp22TxKey();

    /**
     * 23
     * Get HDCP Key (1.4); Read HDCP first five bytes into system.
     * ksv: key selection vector
     *
     * @return the HDCP Ksv 1.4 in byte array type.
     */
    byte[] getHdcpKsv();

    /**
     * 24
     * Get Firmware Version; Read Firmware Version.
     *
     * @return the Firmware Version in String type.
     */
    String getFirmwareVer();

    /**
     * 25
     * Get Model Name; Read Model Name.
     *
     * @return the name in String type.
     */
    String getModelName();
/********************add for M11 *******************/
    /**
     * 15
     * Set HDCP KEY (1.4 tx / 2.2 rx ); Write HDCP Key into /persist/factory/hdcp.
     * note: here raw data should be sent to, that is to say, what you received from port, what you should be sent to here.
     * now all product follow this rule; I hope it be upgrade.
     *
     * @return success or no.
     */
    boolean setHdcpKeyM11(String mac);

    /**
     * 16
     * Get HDCP Key info(1.4 tx / 2.2 rx); Read HDCP Key from /persist/factory/hdcp.
     *
     * @return the HDCP Key in byte array type.
     */
    byte[] getHdcpKeyM11();

    /**
     * 17
     * Trans HDCP Key (1.4 rx/2.2 tx) to MTK Drm.
     *
     * @return success or no.
     */
    boolean transHdcpKeyM11();

    /**
     * 18
     * Verify HDCP Key (1.4 rx/2.2 tx).
     *
     * @return success or no.
     */
    boolean verHdcpKeyM11();

    /**
     * 26
     * Set Wifi FREQ OFFSET; Write into system.
     *
     * @return success or no.
     */
    boolean setWifiFreqOffset(String offset);

    /**
     * 27
     * Get Wifi FREQ OFFSET; Read from system.
     *
     * @return wifi freq in String type.
     */
    byte getWifiFreqOffset();
/********************add for M11 *******************/

    /******************** add for projector ***********/
    boolean setPID(String pid);

    String getPID();
/******************** add for projector end *******/
    /******************** test for factory product Id  *******/
    boolean setFactoryPID(String fid);

    String getFactoryPID();
/******************** test for factory product Id end *******/
    /******************** test for look select  *******/
    boolean setLookSelect(String ls);

    String getLookSelect();

    /******************** test for look select *******/
    String readKeyMD5(String keyName);

    String readUSID();

    boolean writeUSID(String param);

    String getKeyActiveStatus(String param);

    boolean setWidewineKey(String param);

    byte[] getWidewineKey();

    boolean setAttestationKey(String param);

    byte[] getAttestationKey();

    boolean setKeyActive(String param);

    boolean setNetflixKey(String param);

    byte[] getNetflixKey();

    boolean setTernaryKey(String param);

    byte[] getTernaryKey();
}
