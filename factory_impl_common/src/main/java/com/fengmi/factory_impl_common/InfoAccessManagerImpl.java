package com.fengmi.factory_impl_common;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.InfoAccessManagerInterf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;

//amlogic TV

public class InfoAccessManagerImpl implements InfoAccessManagerInterf {

    private static int HDCP_14_RX_LEN;
    private static int HDCP_20_RX_LEN;

    static {
        if (Build.VERSION.SDK_INT == 28) {
            HDCP_14_RX_LEN = HDCP_14_RX_LEN_P;
            HDCP_20_RX_LEN = HDCP_20_RX_LEN_P;
        } else {
            HDCP_14_RX_LEN = HDCP_14_RX_LEN_O;
            HDCP_20_RX_LEN = HDCP_20_RX_LEN_O;
        }
    }

    public boolean setPcbaSerialNumber(String sn) {
        boolean ret = false;
        ret = writePcbaSn(sn);
        return ret;
    }

    public String getPcbaSerialNumber() {
        String serial = null;
        serial = readPcbaSn();
        return serial;
    }

    public boolean setPcbaManufactureNumber(String mn) {
        boolean ret = false;
        ret = writePcbaMn(mn);
        return ret;
    }

    public String getPcbaManufactureNumber() {
        String mn = null;
        mn = readPcbaMn();
        return mn;
    }

    public boolean setAssmSerialNumber(String sn) {
        boolean ret = false;
        ret = writeAssmSn(sn);
        return ret;
    }

    public String getAssmSerialNumber() {
        String serial = null;
        serial = readAssmSn();
        return serial;
    }

    public boolean setAssmManufactureNumber(String mn) {
        boolean ret = false;
        ret = writeAssmMn(mn);
        return ret;
    }

    public String getAssmManufactureNumber() {
        String mn = null;
        mn = readAssmMn();
        return mn;
    }

    public boolean setBluetoothMac(String mac) {
        boolean ret = false;
        ret = writeBtMac(mac);
        return ret;
    }

    public String getBluetoothMac() {
        String ret = null;
        ret = readBtMac();
        return ret;
    }

    public boolean setWifiMac(String mac) {
        boolean ret = false;
        //ret = setMac(NetType[1][0], mac);
        return ret;
    }

    public String getWifiMac() {
        String ret = null;
        //ret = getMac(NetType[1][0]);
        return ret;
    }

    public boolean setEthernetMac(String mac) {
        boolean ret = false;
        ret = writeEthMac(mac);
        return ret;
    }

    public String getEthernetMac() {
        String ret = null;
        ret = readEthMac();
        return ret;
    }

    //used for HDCP RX
    public boolean setHdcp14Key(String key) {
        boolean ret = false;
        ret = setKeys(key, HDCP_14_FILEPATH, HDCP_14_RX_LEN);
        return ret;
    }

    //used for HDCP RX
    public byte[] getHdcp14Key() {
        byte[] ret = null;
        ret = getKeys(HDCP_14_FILEPATH, HDCP_14_RX_LEN);
        return ret;
    }

    //used for HDCP RX
    public boolean setHdcp20Key(String key) {
        boolean ret = false;
        ret = setKeys(key, HDCP_20_FILEPATH, HDCP_20_RX_LEN);
        return ret;
    }

    //used for HDCP RX
    public byte[] getHdcp20Key() {
        byte[] ret = null;
        ret = getKeys(HDCP_20_FILEPATH, HDCP_20_RX_LEN);
        return ret;
    }

    //used for HDCP TX, unused for TV
    public boolean setHdcp14TxKey(String key) {
        boolean ret = false;
        ret = setKeys(key, HDCP_14_TX_FILEPATH, HDCP_14_TX_LEN);
        return ret;
    }

    //used for HDCP TX, unused for TV
    public byte[] getHdcp14TxKey() {
        byte[] ret = null;
        ret = getKeys(HDCP_14_TX_FILEPATH, HDCP_14_TX_LEN);
        return ret;
    }

    //used for HDCP TX, unused for TV
    public boolean setHdcp22TxKey(String key) {
        boolean ret = false;
        ret = setKeys(key, HDCP_22_TX_FILEPATH, HDCP_20_TX_LEN);
        return ret;
    }

    //used for HDCP TX, unused for TV
    public byte[] getHdcp22TxKey() {
        byte[] ret = null;
        ret = getKeys(HDCP_22_TX_FILEPATH, HDCP_20_TX_LEN);
        return ret;
    }

    public byte[] getHdcpKsv() {
        byte[] ret = null;
        //ret = readHdcpKsv(HDCP_14_FILEPATH);
        return ret;
    }

    public boolean setWidewineKey(String key) {
        boolean ret = false;
        ret = setKeys(key, WIDEWINE_KEY_FILEPATH, WIDEWINE_KEY_LEN);
        return ret;
    }

    public byte[] getWidewineKey() {
        byte[] ret = null;
        ret = getKeys(WIDEWINE_KEY_FILEPATH, WIDEWINE_KEY_LEN);
        return ret;
    }

    public boolean setAttestationKey(String key) {
        boolean ret = false;
        ret = setKeys(key, ATTESTATION_KEY_FILEPATH, ATTESTATION_KEY_LEN);
        return ret;
    }

    public byte[] getAttestationKey() {
        byte[] ret = null;
        ret = getKeys(ATTESTATION_KEY_FILEPATH, ATTESTATION_KEY_LEN);
        return ret;
    }

    public boolean setKeyActive(String keyName) {
        boolean ret = false;
        ret = enableKeys(keyName);
        return ret;
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
        return setKeys(param, TERNARY_KEY_FILEPATH, TERNARY_KEY_LEN);
    }

    @Override
    public byte[] getTernaryKey() {
        return getKeys(TERNARY_KEY_FILEPATH, TERNARY_KEY_LEN);
    }

    /*********************for M11 start*****************************/
    public boolean setHdcpKeyM11(String key) {
        boolean ret = false;
        //ret = writeHdcpKey(key, HDCP_FILEPATH, HDCP_LEN);
        return ret;
    }

    public byte[] getHdcpKeyM11() {
        byte[] ret = null;
        //ret = readHdcpKey(HDCP_FILEPATH, HDCP_LEN);
        return ret;
    }

    public boolean verHdcpKeyM11() {
        boolean ret = false;
        //ret = verifyHdcpKey();
        return ret;
    }

    public boolean transHdcpKeyM11() {
        boolean ret = false;
        //ret = setHdcpKeyToDrm(HDCP_FILEPATH, HDCP_LEN);
        return ret;
    }

    /*********************for M11 end****************************/
    public String getFirmwareVer() {
        String ret = null;
        ret = readFWVer();
        return ret;
    }

    public String getModelName() {
        String ret = null;
        ret = readModelName();
        return ret;
    }

    public boolean setWifiFreqOffset(String offset) {
        Log.i(TAG, "offset is " + offset);
        return true;
    }

    public byte getWifiFreqOffset() {
        byte offset_byte = 0x00;
        Log.i(TAG, "getWifiFreqOffset ");
        return offset_byte;
    }

    public String getPID() {
        String pid = null;
        pid = readProductId();
        return pid;
    }

    public boolean setPID(String pid) {
        boolean ret = false;
        ret = writeProductId(pid);
        return ret;
    }

    /**
     * set factory product id
     *
     * @param fid
     * @return success
     */
    public boolean setFactoryPID(String fid) {
        Log.i(TAG, "setFactoryID:" + fid);
        return writeFactoryProductID(fid);
    }

    /**
     * read factory product id
     *
     * @return factory
     */
    public String getFactoryPID() {
        String fid = null;
        fid = readFactoryProductId();
        Log.i(TAG, "read factory PID:" + fid);
        return fid;
    }

    /**
     * 获取 key 激活状态
     *
     * @param keyName
     * @return 1:已激活    0：未激活
     */
    public String getKeyActiveStatus(String keyName) {
        Log.i(TAG, "get key active status  key-name :" + keyName);
        String flag = null;
        flag = readKeyFlag(keyName);
        Log.i(TAG, "get key active status key- flag :" + flag);
        return flag;
    }

    public boolean writeUSID(String usid) {
        boolean ret = false;
        ret = setUSID(usid);
        return ret;
    }

    public String readUSID() {
        String usid = null;
        usid = getUSID();
        return usid;
    }

    public boolean setLookSelect(String ls) {
        boolean res = false;
        res = writeLookSelect(ls);
        return res;
    }

    public String getLookSelect() {
        String ls = null;
        ls = readLookSelect();
        Log.i(TAG, "read Look Select :" + ls);
        return ls;
    }

    public String readKeyMD5(String keyName) {
        Log.i(TAG, "read Key MD5 keyName is :" + keyName);
        return keyMD5Read(keyName);
    }

    private boolean writePcbaSn(String sn) {
        Log.i(TAG, "write pcba sn: " + sn);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(PCBA_SERIAL, sn);
        return true;
    }

    private String readPcbaSn() {
        String serial = "";
        serial = SDKManager.getAmlogicManagerInterf().readUnifyKey(PCBA_SERIAL);
        Log.i(TAG, "read pcba sn: " + serial);
        return serial;
    }

    private boolean writePcbaMn(String mn) {
        Log.i(TAG, "write pcba mn: " + mn);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(PCBA_MANUFACTURE, mn);
        return true;
    }

    private String readPcbaMn() {
        String manu = "";
        manu = SDKManager.getAmlogicManagerInterf().readUnifyKey(PCBA_MANUFACTURE);
        Log.i(TAG, "read pcba mn: " + manu);
        return manu;
    }

    private String getUSID() {
        String usid = "";
        usid = SDKManager.getAmlogicManagerInterf().readUnifyKey(USID);
        Log.i(TAG, "read usid : " + usid);
        return usid;
    }

    private boolean setUSID(String sn) {
        Log.i(TAG, "write usid : " + sn);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(USID, sn);
        return true;
    }

    private boolean writeAssmSn(String sn) {
        Log.i(TAG, "write Assm sn: " + sn);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(ASSM_SERIAL, sn);
        return true;
    }

    private String readAssmSn() {
        String serial = "";
        serial = SDKManager.getAmlogicManagerInterf().readUnifyKey(ASSM_SERIAL);
        Log.i(TAG, "read Assm sn: " + serial);
        return serial;
    }

    private boolean writeAssmMn(String mn) {
        Log.i(TAG, "write Assm mn: " + mn);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(ASSM_MANUFACTURE, mn);
        return true;
    }

    private String readAssmMn() {
        String manu = "";
        manu = SDKManager.getAmlogicManagerInterf().readUnifyKey(ASSM_MANUFACTURE);
        Log.i(TAG, "read Assm mn: " + manu);
        return manu;
    }

    private boolean writeEthMac(String mac) {
        if (mac != null) {
            mac = mac.toLowerCase();
        }
        Log.i(TAG, "write Ethernet Mac: " + mac);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(ETH_MAC, mac);
        return true;
    }

    private String readEthMac() {
        String mac = "";
        mac = SDKManager.getAmlogicManagerInterf().readUnifyKey(ETH_MAC);
        Log.i(TAG, "read Ethernet Mac: " + mac);
        return mac;
    }

    private boolean writeBtMac(String mac) {
        if (mac != null) {
            mac = mac.toLowerCase();
        }
        Log.i(TAG, "write Bluetooth Mac: " + mac);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(BT_MAC, mac);
        return true;
    }

    private String readBtMac() {
        String mac = "";
        mac = SDKManager.getAmlogicManagerInterf().readUnifyKey(BT_MAC);
        Log.i(TAG, "read Bluetooth Mac: " + mac);
        return mac;
    }

    private String readFWVer() {
        String ver = "";
        String mode = "";
        String userMode = "U";
        String debugMode = "D";
        String otherMode = "O";
        ver = SDKManager.getAndroidOSManagerInterf().getSystemProperty(VERSION_INCREMENTAL, ERROR_RESULT);
        mode = SDKManager.getAndroidOSManagerInterf().getSystemProperty(BUILD_TYPE, ERROR_RESULT);
        if (mode.equals(BUILD_MODE_USER)) {
            ver = ver + userMode;
        } else if (mode.equals(BUILD_MODE_DEBUG)) {
            ver = ver + debugMode;
        } else {
            ver = ver + otherMode;
        }
        Log.i(TAG, "get SW version: " + "[" + ver + "]");
        return ver;
    }

    private String readModelName() {
        String name = "";
        name = SDKManager.getAndroidOSManagerInterf().getSystemProperty(BUILD_MODELNAME, ERROR_RESULT);
        Log.i(TAG, "get model name: " + "[" + name + "]");
        return name;
    }

    private boolean setKeys(String key, String path, int len) {
        boolean ret = false;
        byte key_byte[] = new byte[len];
        String keys[] = key.split(",");
        if (len < keys.length) {
            return ret;
        }
        for (int i = 0; i < keys.length; i++) {
            key_byte[i] = (byte) Integer.parseInt(keys[i]);
        }
        File hdcpfile = new File(path);
        if (!hdcpfile.exists()) {
            try {
                hdcpfile.createNewFile();
                Log.i(TAG, "can find directory and file, create");
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fstream = new FileOutputStream(hdcpfile);
            fstream.write(key_byte, 0, len);
            fstream.getFD().sync();
            fstream.close();
            hdcpfile.setReadable(true, false);
            Log.e(TAG, "Set Key: OK | file path" + path);
            ret = true;
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    private byte[] getKeys(String path, int len) {
        byte[] key = new byte[len];
        Log.i(TAG, "Get Hdcp Key");
        File hdcpfile = new File(path);
        if (hdcpfile.exists()) {
            try {
                FileInputStream fstream = new FileInputStream(hdcpfile);
                int ret = fstream.read(key, 0, len);
                fstream.close();
                Log.e(TAG, "Get Hdcp Key: get " + ret + "bytes ");
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        Log.d(TAG, Arrays.toString(key));
        return key;
    }

    private boolean enableKeys(String name) {
        boolean ret = false;
        String key_file_path = null;
        String key_dts_name = null;
        int key_len = 0;
        Log.i(TAG, "enable key:" + name);
        switch (name) {
            case "hdcp_14_rx":
                key_dts_name = "hdcp14_rx";
                key_file_path = HDCP_14_FILEPATH;
                key_len = HDCP_14_RX_LEN;
                //special interface
                byte[] key_byte = readKey2Buffer(key_file_path, key_len);
                int[] key_int = new int[4096];
                for (int i = 0; i < key_byte.length; i++) {
                    key_int[i] = key_byte[i];
                }
                ret = SDKManager.getAmlogicManagerInterf().writeHdcpRX14Key(key_int, key_len);
                Log.e(TAG, " key active result : " + ret);
                // if (ret) {
                //     ret = enableKeyFlag(name);
                // }
                break;
            case "hdcp_22_rx":
                key_dts_name = "hdcp2_rx";
                key_file_path = HDCP_20_FILEPATH;
                key_len = HDCP_20_RX_LEN;

                //special interface
                ret = SDKManager.getAmlogicManagerInterf().writeHdcpRXImg(key_file_path);
                Log.e(TAG, " key active result : " + ret);
                // if (ret) {
                //     ret = enableKeyFlag(name);
                // }
                break;
            case "secure_boot":
                key_dts_name = "secure_boot_set";
                key_file_path = AML_SECURE_BOOT_KEY_FILEPATH;
                key_len = SECURE_BOOT_KEY_LEN;

                //ret = writeKey2Dts(key_file_path, key_dts_name, key_len);
                ret = writeAttestationKey(key_file_path, key_dts_name, key_len);
                break;
            case "playready_pub":
                key_dts_name = "prpubkeybox";
                key_file_path = PLAYREADY_PUBLIC_KEY_FILEPATH;
                key_len = PLAYREADY_PUB_KEY_LEN;

                //ret = writeKey2Dts(key_file_path, key_dts_name, key_len);
                ret = writeAttestationKey(key_file_path, key_dts_name, key_len);
                //ret = writePlayReadyKey(key_file_path, key_dts_name, key_len);
                break;
            case "playready_pri":
                key_dts_name = "prprivkeybox";
                key_file_path = PLAYREADY_PRIVATE_KEY_FILEPATH;
                key_len = PLAYREADY_PRI_KEY_LEN;

                //ret = writeKey2Dts(key_file_path, key_dts_name, key_len);
                ret = writeAttestationKey(key_file_path, key_dts_name, key_len);
                //ret = writePlayReadyKey(key_file_path, key_dts_name, key_len);
                break;
            case "pr_pub":
                key_dts_name = "prpubkeybox";
                key_file_path = PLAYREADY_PUBLIC_KEY_FILEPATH;
                key_len = PLAYREADY_PUB_KEY_LEN;

                //ret = writeKey2Dts(key_file_path, key_dts_name, key_len);
                //ret = writeAttestationKey(key_file_path, key_dts_name, key_len);
                ret = writePlayReadyKey(key_file_path, key_dts_name, key_len);
                break;
            case "pr_pri":
                key_dts_name = "prprivkeybox";
                key_file_path = PLAYREADY_PRIVATE_KEY_FILEPATH;
                key_len = PLAYREADY_PRI_KEY_LEN;

                //ret = writeKey2Dts(key_file_path, key_dts_name, key_len);
                //ret = writeAttestationKey(key_file_path, key_dts_name, key_len);
                ret = writePlayReadyKey(key_file_path, key_dts_name, key_len);
                break;
            case "widevine":
                key_dts_name = "widevinekeybox";
                key_file_path = WIDEWINE_KEY_FILEPATH;
                key_len = WIDEWINE_KEY_LEN;

                //ret = writeKey2Dts(key_file_path, key_dts_name, key_len);
                ret = writeAttestationKey(key_file_path, key_dts_name, key_len);
                Log.e(TAG, " key active result : " + ret);
                // if (ret) {
                //     ret = enableKeyFlag(name);
                // }
                break;
            case "attestation":
                key_dts_name = "attestationkeybox";
                key_file_path = ATTESTATION_KEY_FILEPATH;
                key_len = ATTESTATION_KEY_LEN;

                ret = writeAttestationKey(key_file_path, key_dts_name, key_len);
                Log.e(TAG, " key active result : " + ret);
                // if (ret) {
                //     ret = enableKeyFlag(name);
                // }
                break;
            case "ternary":
                ret = mapTernaryKey();
                break;
            default:
                Log.e(TAG, "error key name " + name);
                break;
        }
        if (ret) {
            keyMD5Generate(name);
        }
        return ret;
    }

    private boolean mapTernaryKey() {
        boolean res = false;
        try {
            FileInputStream ins = new FileInputStream(TERNARY_KEY_FILEPATH);
            int avail = ins.available();
            Log.d(TAG, "read MI ternary path=" + TERNARY_KEY_FILEPATH + " size = " + avail);
            if (avail == TERNARY_KEY_LEN) {
                byte[] datas = new byte[TERNARY_KEY_LEN];
                ins.read(datas);
                String ternary = new String(datas);
                Log.d(TAG, "MI Ternary data = " + ternary);
                String[] keys = ternary.split("\\|");
                if (keys.length == 3 && keys[0].length() == 12 && keys[1].length() == 9 && keys[2].length() == 16) {
                    String mac = keys[0];
                    String did = keys[1];
                    String miKey = keys[2];

                    StringBuilder sb_mac = new StringBuilder();
                    for (int i = 0; i < 6; i++) {
                        String s = mac.substring(i * 2, i * 2 + 2);
                        if (i == 5) {
                            sb_mac.append(s);
                        } else {
                            sb_mac.append(s).append(":");
                        }
                    }
                    res = writeBtMac(sb_mac.toString()) && writeTernary(ternary);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 读取key flag
     *
     * @param name keyname
     * @return
     */
    private String readKeyFlag(String name) {
        Log.e(TAG, "current actived key name " + name);

        String flag = KEY_ACTIVE_STATUS_OFF;
        String key_flag_dts_name = null;

        key_flag_dts_name = trans2KeyFlagName(name);

        if (key_flag_dts_name != null) {
            flag = SDKManager.getAmlogicManagerInterf().readUnifyKey(key_flag_dts_name);
            if (TextUtils.isEmpty(flag)) {
                flag = KEY_ACTIVE_STATUS_OFF;
            }
        }

        Log.e(TAG, "current actived key flag " + flag);

        return flag;
    }

    /**
     * key name 转换为 key dts flag name
     *
     * @param name key name
     * @return key_flag_dts_name maybe null
     */
    private String trans2KeyFlagName(String name) {
        Log.e(TAG, "current actived key name " + name);

        String key_flag_dts_name = null;
        switch (name) {
            case "hdcp_14_rx":
                key_flag_dts_name = "hdcp_14_rx_flag";
                break;
            case "hdcp_22_rx":
                key_flag_dts_name = "hdcp_22_rx_flag";
                break;
            case "attestation":
                key_flag_dts_name = "attestation_flag";
                break;
            case "widevine":
                key_flag_dts_name = "widevine_flag";
                break;
            default:
                Log.e(TAG, "undeclared key name " + name);
                break;
        }
        return key_flag_dts_name;
    }

    private boolean writeKey2Dts(String path, String name, int len) {
        boolean ret = false;
        //1.get byte from temp file
        Log.i(TAG, "write key to DTS:" + name);
        byte[] key_byte = readKey2Buffer(path, len);

        String key_Str = new String(key_byte);

        SDKManager.getAmlogicManagerInterf().writeUnifyKey(name, key_Str);

        ret = true;
        return ret;
    }

    /**
     * key写入，由于底层实现相同，对于WideVine、playready_pri|pub key 的写入操作共用此方法
     *
     * @param path    key数据
     * @param dtsName dts文件名
     * @param len     字节大小
     * @return success
     */
    private boolean writeAttestationKey(String path, String dtsName, int len) {
        boolean ret = false;
        String node = "/dev/unifykeys";

        byte[] key_byte = readKey2Buffer(path, len);

        Log.i(TAG, "writeKey method begin -- dtsName:" + dtsName);

        //初始化int数组，大小10×1024（amlogic强制要求）
        int[] key_data = new int[10 * 1024];

        Log.i(TAG, "writeKey ------- key_byte[] -length- " + key_byte.length);
        for (int i = 0; i < 80; i++) {
            if (i < key_byte.length)
                Log.i(TAG, "writeKey ------- key_byte[" + i + "] :" + key_byte[i]);
        }

        for (int i = 0; i < key_byte.length; i++) {
            key_data[i] = key_byte[i];
        }

        Log.i(TAG, "key data loaded -- key_data length:" + key_data.length);

        return SDKManager.getAmlogicManagerInterf().writeAttestationKey(node, dtsName, key_data, key_data.length);
    }

    private boolean writePlayReadyKey(String path, String dtsName, int len) {
        Log.i(TAG, "writePlayReadyKey method begin -- dtsName:" + dtsName);

        byte[] key_byte = readKey2Buffer(path, len);

        Log.i(TAG, "writeKey method begin -- dtsName:" + dtsName);

        //初始化int数组，大小4×1024（amlogic强制要求）
        //int[] key_data = new int[4 * 1024];
        int[] key_data = new int[len];

        Log.i(TAG, "writeKey ------- key_byte[] -length- " + key_byte.length);
        for (int i = 0; i < 80; i++) {
            if (i < key_byte.length)
                Log.i(TAG, "writeKey ------- key_byte[" + i + "] :" + key_byte[i]);
        }

        for (int i = 0; i < key_byte.length; i++) {
            key_data[i] = key_byte[i];
        }

        Log.i(TAG, "key data loaded -- key_data length:" + key_data.length);

        return SDKManager.getAmlogicManagerInterf().writePlayreadyKey(dtsName, key_data, key_data.length);
    }

    private byte[] readKey2Buffer(String path, int len) {
        byte[] key = new byte[len];
        Log.i(TAG, "Get Key to buffer");
        File hdcpfile = new File(path);
        if (hdcpfile.exists()) {
            try {
                FileInputStream fstream = new FileInputStream(hdcpfile);
                int ret = fstream.read(key, 0, len);
                fstream.close();
                Log.e(TAG, "Get Key: get " + ret + "bytes ");
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return key;
    }

    /*===========================================local functions=====================*/
    /*===========================================tool functions=====================*/
    private String delSpace(String src) {
        String dest = "";
        String srcs[] = src.split(",");
        for (int i = 0; i < srcs.length; i++) {
            dest += String.format("%02x", Integer.parseInt(srcs[i]));
        }
        return dest;
    }

    /*===========================================tool functions=====================*/
    private boolean writeProductId(String pid) {
        Log.i(TAG, "write PID:" + pid);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(PRODUCT_ID, pid);
        return true;
    }

    private String readProductId() {
        String pid = "";
        pid = SDKManager.getAmlogicManagerInterf().readUnifyKey(PRODUCT_ID);
        Log.i(TAG, "read PID:" + pid);
        return pid;
    }

    /**
     * factory product id 写入
     *
     * @param fid String
     * @return success
     */
    private boolean writeFactoryProductID(String fid) {
        Log.i(TAG, "write factory product pid" + fid);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(FACTORY_PID, fid);
        return true;
    }

    /**
     * factory product id 读取
     *
     * @return fid  String
     */
    private String readFactoryProductId() {
        String fid = "";
        fid = SDKManager.getAmlogicManagerInterf().readUnifyKey(FACTORY_PID);
        Log.i(TAG, "read factory product pid:" + fid);
        return fid;
    }

    private boolean writeLookSelect(String ls) {
        Log.i(TAG, "set Look_Select : " + ls);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(LOOK_SELECT, ls);
        return true;
    }

    private boolean writeTernary(String ls) {
        Log.i(TAG, "set MI_TERNARY : " + ls);
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(MI_TERNARY, ls);
        return true;
    }

    private String readLookSelect() {
        String ls = "";
        ls = SDKManager.getAmlogicManagerInterf().readUnifyKey(LOOK_SELECT);
        Log.i(TAG, "read Loock Select : " + ls);
        return ls;
    }

    /***
     * MD5加码 生成32位md5码
     */
    private String string2MD5(byte[] key) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
        byte[] md5Bytes = md5.digest(key);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        Log.i(TAG, "string2MD5 :" + hexValue.toString());
        return hexValue.toString();
    }

    private byte[] getHdcpKey(String path, int len) {
        byte[] key = new byte[len];
        Log.i(TAG, "Get Hdcp Key");
        File hdcpfile = new File(path);
        if (hdcpfile.exists()) {
            try {
                FileInputStream fstream = new FileInputStream(hdcpfile);
                int ret = fstream.read(key, 0, len);
                fstream.close();
                Log.e(TAG, "Get Hdcp Key: get " + ret + "bytes ");
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return key;
    }

    private void keyMD5Generate(String keyName) {
        String keyPath;
        int keyLen;
        String mapKey;
        String keyMD5;
        byte[] keyBytes;
        String keyInfo;
        switch (keyName) {
            case "hdcp_14_rx":
                keyPath = HDCP_14_FILEPATH;
                keyLen = HDCP_14_RX_LEN;
                mapKey = "HD14";
                break;
            case "hdcp_22_rx":
                keyPath = HDCP_20_FILEPATH;
                keyLen = HDCP_20_RX_LEN;
                mapKey = "HD22";
                break;
            case "attestation":
                keyPath = ATTESTATION_KEY_FILEPATH;
                keyLen = ATTESTATION_KEY_LEN;
                mapKey = "ATTE";
                break;
            case "widevine":
                keyPath = WIDEWINE_KEY_FILEPATH;
                keyLen = WIDEWINE_KEY_LEN;
                mapKey = "WIDE";
                break;
            case "playready_pub":
            case "pr_pub":
                keyPath = PLAYREADY_PUBLIC_KEY_FILEPATH;
                keyLen = PLAYREADY_PUB_KEY_LEN;
                mapKey = "PLAY_PUB";
                break;
            case "playready_pri":
            case "pr_pri":
                keyPath = PLAYREADY_PRIVATE_KEY_FILEPATH;
                keyLen = PLAYREADY_PRI_KEY_LEN;
                mapKey = "PLAY_PRI";
                break;
            case "ternary":
                keyPath = TERNARY_KEY_FILEPATH;
                keyLen = TERNARY_KEY_LEN;
                mapKey = "TERNARY";
                break;
            default:
                Log.e(TAG, "undeclared key name " + keyName);
                keyPath = "";
                mapKey = "";
                keyLen = 0;
                break;
        }
        if (keyPath.equals("")) {
            Log.e(TAG, "error key path " + keyPath);
            return;
        }
        //key MD5 Info generate
        keyBytes = getKeys(keyPath, keyLen);
        keyMD5 = string2MD5(keyBytes);
        keyInfo = mapKey + ":" + keyMD5;
        //读取系统存取的MD5信息
        String md5Info = SDKManager.getAmlogicManagerInterf().readUnifyKey(HDCP_MD5);

        Log.e(TAG, "read key md5 ,val is " + md5Info);
        if (TextUtils.isEmpty(md5Info)) {
            md5Info = keyInfo + ",";
        } else {
            if (md5Info.contains(",")) {
                String[] md5Maps = md5Info.split(",");
                //遍历ｋｅｙ是否存在,如果存在，清除
                for (String md5Map : md5Maps) {
                    Log.e(TAG, "read md5Map ,val is " + md5Map);
                    if (md5Map.contains(mapKey)) {
                        md5Info = md5Info.replace(md5Map + ",", "");
                        Log.e(TAG, "md5Map contains mapKey so we replaced it with null, " + md5Info);
                    }
                }
            }
            //增加新 MD5
            md5Info = md5Info + keyInfo + ",";
            Log.e(TAG, "read new md5 ,md5 is " + md5Info);
        }
        //write
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(HDCP_MD5, md5Info);
    }

    private String keyMD5Read(String keyName) {
        String mapKey;
        String md5Info;
        md5Info = SDKManager.getAmlogicManagerInterf().readUnifyKey(HDCP_MD5);
        Log.e(TAG, "find key md5, unify key is " + md5Info);
        switch (keyName) {
            case "hdcp_14_rx":
                mapKey = "HD14";
                break;
            case "hdcp_22_rx":
                mapKey = "HD22";
                break;
            case "attestation":
                mapKey = "ATTE";
                break;
            case "widevine":
                mapKey = "WIDE";
                break;
            case "playready_pub":
            case "pr_pub":
                mapKey = "PLAY_PUB";
                break;
            case "playready_pri":
            case "pr_pri":
                mapKey = "PLAY_PRI";
                break;
            case "ternary":
                mapKey = "TERNARY";
                break;
            default:
                Log.e(TAG, "undeclared key name " + keyName);
                mapKey = "";
                break;
        }
        if (TextUtils.isEmpty(md5Info) || TextUtils.isEmpty(mapKey) || !md5Info.contains(mapKey)) {
            Log.e(TAG, "mapKey or md5Info is empty | mapKey not be contained " + md5Info);
            return "";
        }
        if (md5Info.contains(",")) {
            String[] md5Maps = md5Info.split(",");
            for (String md5Map : md5Maps) {
                if (md5Map.contains(mapKey)) {
                    String[] map = md5Map.split(":");
                    if (map.length == 2) {
                        return map[1];
                    }
                }
            }
        } else {
            String[] map = md5Info.split(":");
            if (map.length == 2) {
                return map[1];
            }
        }
        Log.e(TAG, "error find key md5, md5Info is " + keyName);
        return "";
    }


}
