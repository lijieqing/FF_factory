package com.fengmi.factory_test_interf.sdk_globle;

import android.content.ComponentName;
import android.util.Log;

public class TvCommandDescription {
    public static final String TAG = "TvCommandDescription";
    public static final int CMDID_FUNC_TEST = 0x1991;
    /***************1. TV command ID(the prefix is 0x1)**************/
    /*--------------the id unused for a long time, prepare reused it----*/
    public static final int CMDID_USB_LIST_SIZE = 0x1103;
    public static final int CMDID_USB_LIST_GET = 0x1104;
    public static final int CMDID_GET_SYSTEM_PROP = 0x111d;
    public static final int CMDID_PLAY_SPEAKER_AUDIO = 0x1117;
    /* ------------------------------------ class property stop ---------------------------*/

    /* ------------------------------------ command id start ---------------------------*/
    /********
     * command ID Recycling pool
     * 113c-113f
     * */
    public static final int CMDID_PLAY_SPDIF_AUDIO = 0x1120;
    public static final int CMDID_BOOT_ENV_READ = 0x1139;
    public static final int CMDID_BOOT_ENV_WRITE = 0x1140;
    public static final int CMDID_UNIFY_KEY_READ = 0x1141;
    public static final int CMDID_UNIFY_KEY_WRITE = 0x1142;
    public static final int CMDID_NETFLIX_KEY_READ = 0x1143;
    public static final int CMDID_NETFLIX_KEY_WRITE = 0x1144;
    public static final int CMDID_KTV_ENABLE = 0x1200;
    public static final int CMDID_AUDIO_RECORDER_ON = 0x1201;
    public static final int CMDID_AUDIO_RECORDER_READ = 0x1202;
    public static final int CMDID_AUDIO_RECORDER_PLAY = 0x1203;
    public static final int CMDID_SP_WRITE = 0x1204;
    public static final int CMDID_SP_READ = 0x1205;
    /*--------------the id unused for a long time, prepare reused it----*/
    public static final int CMDID_UDISK_2_CONTENT_TEST = 0x1101;
    public static final int CMDID_UDISK_3_CONTENT_TEST = 0x110d;
    public static final int CMDID_UDISK_TEXT_VERIFY = 0x1102;
    public static final int CMDID_UDISK_DECT_2_0 = 0x110b;
    public static final int CMDID_UDISK_UNMOUNT_3_0 = 0x110c;
    public static final int CMDID_UDISK_CHECK_SPEED = 0x1485;
    public static final int CMDID_SOUR_SWITCH = 0x1105;
    public static final int CMDID_SOUR_STOP = 0x1106;
    public static final int CMDID_HDMI_ARC_ON = 0x111f;
    public static final int CMDID_HDMI_ARC_OFF = 0x1107;
    public static final int CMDID_HDMI_1_CEC = 0x1109;
    public static final int CMDID_HDMI_1_CEC_NAME = 0x110e;
    public static final int CMDID_HDMI_2_CEC = 0x1111;
    public static final int CMDID_HDMI_2_CEC_NAME = 0x110f;
    public static final int CMDID_MEDIA_PLAY_AF = 0x1113;
    public static final int CMDID_MEDIA_PLAY_TOF = 0x1122;
    public static final int CMDID_MEDIA_STOP = 0x111a;
    public static final int CMDID_MHL_CEC_3 = 0x1114;
    public static final int CMDID_MHL_CEC_3_NAME = 0x111e;
    public static final int CMDID_ENABLE_SPEAKER = 0x1115;
    public static final int CMDID_DISABLE_SPEAKER = 0x1116;
    public static final int CMDID_ENABLE_SPDIF = 0x1118;
    public static final int CMDID_DISABLE_SPDIF = 0x1119;
    public static final int CMDID_GET_SOUND_VOLUME = 0x111b;
    public static final int CMDID_SET_SOUND_VOLUME = 0x111c;
    public static final int CMDID_WIFI_QUICKCONNECT = 0x142a;
    public static final int CMDID_WIFI_CONNECT_WITH_PASS = 0x142d;
    public static final int CMDID_WIFI_IPADDR_GET = 0x142c;
    public static final int CMDID_WIFI_RSSI_GET = 0x142b;
    public static final int CMDID_SET_WIFI_STATE = 0x112d;
    public static final int CMDID_SET_BT_STATE = 0x112e;
    public static final int CMDID_SET_BT_STATE_BLE = 0x112f;
    public static final int CMDID_WIFI_PING = 0x1121;
    public static final int CMDID_BT_SCAN = 0x1422;
    public static final int CMDID_ETH_PING = 0x1123;
    public static final int CMDID_IR_START = 0x1124;
    public static final int CMDID_IR_STOP = 0x1125;
    public static final int CMDID_SET_ETH_STATE = 0x1127;
    public static final int CMDID_GET_ETH_STATE = 0x1128;
    public static final int CMDID_SET_PRODUCT_FEATURE = 0x1129;
    public static final int CMDID_CHECK_PRODUCT_FEATURE = 0x1130;
    //--------------------info MiTV1~3 begin-----------------------
    public static final int CMDID_SERIAL_WRITE = 0x1400;
    public static final int CMDID_SERIAL_READ = 0x1401;
    public static final int CMDID_BT_MAC_WRITE = 0x1402;
    public static final int CMDID_BT_MAC_READ = 0x1403;
    public static final int CMDID_ETH_MAC_WRITE = 0x1404;
    public static final int CMDID_ETH_MAC_READ = 0x1405;
    public static final int CMDID_WIFI_MAC_WRITE = 0x1406;
    public static final int CMDID_WIFI_MAC_READ = 0x1407;
    public static final int CMDID_HDCP_KEY_WRITE = 0x1408;
    public static final int CMDID_HDCP_KEY_READ = 0x1409;
    public static final int CMDID_MIRACAST_KEY_WRITE = 0x1410;
    public static final int CMDID_MIRACAST_KEY_TRANSFER = 0x1137;
    public static final int CMDID_MIRACAST_KEY_READ = 0x1411;
    public static final int CMDID_HDCP14_TX_KEY_WRITE = 0x140a;
    public static final int CMDID_HDCP14_TX_KEY_TRANSFER = 0x140f;
    public static final int CMDID_HDCP14_TX_KEY_READ = 0x140b;
    public static final int CMDID_HDCP22_TX_KEY_WRITE = 0x140c;
    public static final int CMDID_HDCP22_TX_KEY_TRANSFER = 0x140d;
    public static final int CMDID_HDCP22_TX_KEY_READ = 0x140e;
    public static final int CMDID_MANUFACTURE_ID_WRITE = 0x1414;
    public static final int CMDID_MANUFACTURE_ID_READ = 0x1415;
    public static final int CMDID_WIDEWINE_KEY_READ = 0x1416;
    public static final int CMDID_WIDEWINE_KEY_WRITE = 0x1417;
    public static final int CMDID_ATTESTATION_KEY_READ = 0x1418;
    public static final int CMDID_ATTESTATION_KEY_WRITE = 0x1419;
    public static final int CMDID_SET_KEY_ACTIVE = 0x141A;
    //--------------------info MiTV1~3 end-----------------------
    //--------------------info MiTV4 ~ XXXXX begin --------------
    public static final int CMDID_N_PCBA_SERIAL_WRITE = 0x1470;
    public static final int CMDID_N_PCBA_SERIAL_READ = 0x1471;
    public static final int CMDID_N_PCBA_MANU_WRITE = 0x1472;
    public static final int CMDID_N_PCBA_MANU_READ = 0x1473;
    public static final int CMDID_N_ASSM_SERIAL_WRITE = 0x1474;
    public static final int CMDID_N_ASSM_SERIAL_READ = 0x1475;
    public static final int CMDID_N_ASSM_MANU_WRITE = 0x1476;
    public static final int CMDID_N_ASSM_MANU_READ = 0x1477;
    public static final int CMDID_N_BT_MAC_WRITE = 0x1478;
    public static final int CMDID_N_BT_MAC_READ = 0x1479;
    public static final int CMDID_N_ETH_MAC_WRITE = 0x147A;
    public static final int CMDID_N_ETH_MAC_READ = 0x147B;
    public static final int CMDID_N_DOLBY_DTS_CHECK = 0x1482;
    public static final int CMDID_N_GET_PANEL_ID_TAG = 0x1483;
    //--------------------info MiTV4 ~ XXXXX end --------------
    //--------------------media MiTV4 ~ XXXXX end --------------
    public static final int CMDID_N_TVVIEW_SOUR_START = 0x147C;
    public static final int CMDID_N_TVVIEW_SOUR_STOP = 0x147D;
    public static final int CMDID_N_TVVIEW_SOUR_SWITCH = 0x147E;
    public static final int CMDID_N_AMLOGIC_AGING_ON = 0x1480;
    public static final int CMDID_N_AMLOGIC_AGING_OFF = 0x1481;
    public static final int CMDID_N_SET_SCREEN_RESOLUTION = 0x1486;
    public static final int CMDID_N_CLOSE_DSP_DAP = 0x1492;
    public static final int CMDID_N_BYPASS_PQ = 0x1493;
    //--------------------media MiTV4 ~ XXXXX end --------------
    //--------------------util MiTV4 ~ XXXXX begin --------------
    public static final int CMDID_N_SET_GPIO_OUT_STAT = 0x147F;
    public static final int CMDID_N_GET_GPIO_IN_STAT = 0x1484;
    public static final int CMDID_N_SET_VCOM_I2C = 0x1487;
    public static final int CMDID_N_GET_VCOM_I2C = 0x1488;
    //--------------------util MiTV4 ~ XXXXX end --------------
    public static final int CMDID_WIFI_CONNECT_24AP = 0x1207;
    public static final int CMDID_WIFI_CONNECT_5AP = 0x1208;
    public static final int CMDID_WIFI_SPEED_START = 0x1209;
    public static final int CMDID_WIFI_SPEED_STOP = 0x120b;
    public static final int CMDID_WIFI_SPEED_PARA = 0x120d;
    public static final int CMDID_WIFI_WAKEUP = 0x1210;
    public static final int CMDID_BT_PCM_LOOPBACK = 0x1211;
    public static final int CMDID_BT_THROUGH_OUTPUT = 0x1212;
    public static final int CMDID_BT_FORCE_MATCH = 0x1213;
    public static final int CMDID_BT_WAKEUP = 0x1214;
    public static final int CMDID_BACKLIGHT_SET = 0x1220;
    public static final int CMDID_BACKLIGHT_GET = 0x1421;
    public static final int CMDID_AGING_ON = 0x1222;
    public static final int CMDID_AGING_OFF = 0x1223;
    public static final int CMDID_AGING_RESET_TIMER = 0x1224;
    public static final int CMDID_AGING_GET_TIMER = 0x143a;
    public static final int CMDID_AGING_TIMER_START = 0x1270;
    public static final int CMDID_AGING_TIMER_STOP = 0x1271;
    public static final int CMDID_PATTERN_SET = 0x1225;
    public static final int CMDID_PATTERN_DISABLE = 0x122b;
    public static final int CMDID_SYSTEM_MODE_GET = 0x1232;
    public static final int CMDID_SYSTEM_MODE_SET = 0x1231;
    public static final int CMDID_SYSTEM_REBOOT_RECOVERY = 0x1233;
    public static final int CMDID_FACTORY_RESET = 0x1227;
    public static final int CMDID_SYSTEM_MODE_CHANGE = 0x1228;
    public static final int CMDID_SYSTEM_MODE_CHANGE_NEW = 0x1230;
    public static final int CMDID_SYSTEM_REBOOT = 0x1229;
    public static final int CMDID_SYSTEM_SHUTDOWN = 0x122f;
    public static final int CMDID_LIGHT_SENSOR = 0x1430;
    public static final int CMDID_SET_PRODUCTREGION = 0x1461;
    public static final int CMDID_GET_PRODUCTREGION = 0x1462;
    //PQ
    public static final int CMDID_POSTGAIN_RED_GET = 0x1431;
    public static final int CMDID_POSTGAIN_GREEN_GET = 0x1432;
    public static final int CMDID_POSTGAIN_BLUE_GET = 0x1433;
    public static final int CMDID_POSTGAIN_RED_SET = 0x1234;
    public static final int CMDID_POSTGAIN_GREEN_SET = 0x1235;
    public static final int CMDID_POSTGAIN_BLUE_SET = 0x1236;
    public static final int CMDID_POSTOFFS_RED_GET = 0x1437;
    public static final int CMDID_POSTOFFS_GREEN_GET = 0x1438;
    public static final int CMDID_POSTOFFS_BLUE_GET = 0x1439;
    public static final int CMDID_POSTOFFS_RED_SET = 0x123a;
    public static final int CMDID_POSTOFFS_GREEN_SET = 0x123b;
    public static final int CMDID_POSTOFFS_BLUE_SET = 0x123c;

    public static final int CMDID_PQ_DB_SAVE = 0x124a;
    public static final int CMDID_PQ_PANEL_SET = 0x124b;
    //Pic Mode
    public static final int CMDID_PIC_MODE_RESET = 0x1245;
    public static final int CMDID_GET_PIC_MODE = 0x1446;
    public static final int CMDID_SET_PIC_MODE = 0x1247;
    public static final int CMDID_GET_PIC_BRIGHTNESS = 0x1448;
    public static final int CMDID_SET_PIC_BRIGHTNESS = 0x1249;
    public static final int CMDID_GET_PIC_CONTRAST = 0x1450;
    public static final int CMDID_SET_PIC_CONTRAST = 0x1251;
    public static final int CMDID_GET_PIC_SHARPNESS = 0x1465;
    public static final int CMDID_SET_PIC_SHARPNESS = 0x1266;
    public static final int CMDID_GET_PIC_HUE = 0x1467;
    public static final int CMDID_SET_PIC_HUE = 0x1268;
    public static final int CMDID_GET_PIC_SATURATION = 0x1469;
    public static final int CMDID_SET_PIC_SATURATION = 0x126a;
    public static final int CMDID_GET_PIC_COLORTEMP = 0x146b;
    public static final int CMDID_SET_PIC_COLORTEMP = 0x126c;

    public static final int CMDID_MODEL_NAME_GET = 0x1456;
    public static final int CMDID_GET_SYSTEM_FW_VER = 0x1457;

    public static final int CMDID_LED_TEST = 0x1261;
    public static final int CMDID_RC_LOCK = 0x1264;
    public static final int CMDID_HDCP_KSV_GET = 0x1412;
    public static final int CMDID_HDMI_EDID = 0x1413;
    public static final int CMDID_SYSTEM_PARTITION_CHECK = 0x1281;
    public static final int CMDID_BTRC_MAC_SET = 0x1272;
    public static final int CMDID_BTRC_MAC_GET = 0x1463;
    public static final int CMDID_WIFI_DISCONNECT = 0x1126;
    public static final int CMDID_WOOFER_PLUGIN = 0x1112;
    public static final int CMDID_CHECK_HDCPKEY14_VALID = 0x112a;
    public static final int CMDID_CHECK_HDCPKEY22_VALID = 0x112b;
    public static final int CMDID_SET_AUTORUN_STATUS = 0x1131;
    public static final int CMDID_GET_AUTORUN_STATUS = 0x1132;
    public static final int CMDID_SET_AUTORUN_COMMAND = 0x1133;
    public static final int CMDID_GET_AUTORUN_COMMAND = 0x1134;
    public static final int CMDID_CHECK_WIFI_SCAN_RESULT = 0x1135;
    public static final int CMDID_BT_FIND_DEVICE = 0x1136;
    public static final int CMDID_WIFI_SCAN_START = 0x1138;
    public static final int CMDID_SET_SOUND_OUTPUT_MODE = 0x113a;
    public static final int CMDID_SET_SPEAKER_SWITCH = 0x113b;
    //projector
    public static final int CMDID_PRODUCT_UIID_WRITE = 0x14A1;
    public static final int CMDID_PRODUCT_UIID_READ = 0x14A2;
    public static final int CMDID_DLP_SN_READ = 0x14A3;
    public static final int CMDID_SET_BODY_DETECT = 0x1489;
    public static final int CMDID_BODY_DETECT_STATUS = 0x148A;
    public static final int CMDID_UDISK_FILE_CHECK = 0x14A4;
    public static final int CMDID_DLP_SCREEN_CHECK = 0x14A5;
    public static final int CMDID_SET_DLP_SCREEN_CHECK = 0x14A6;
    public static final int CMDID_SET_MOTOR_SCALE = 0x14A7;
    public static final int CMDID_START_BODY_DETECT = 0x14A8;
    public static final int CMDID_STOP_BODY_DETECT = 0x14A9;
    public static final int CMDID_BODY_DETECT_COUNT = 0x14AA;
    public static final int CMDID_LINE_OUT_ENABLE = 0x14AB;
    public static final int CMDID_LINE_OUT_DISABLE = 0x14AC;
    public static final int CMDID_PQ_ENABLE = 0x14AD;
    public static final int CMDID_PQ_DISABLE = 0x14AE;
    public static final int CMDID_DLP_INFO_SYNC = 0x14B0;
    public static final int CMDID_DLP_INFO_SAVE = 0x14B1;
    public static final int CMDID_WHEEL_DELAY_READ = 0x14B2;
    public static final int CMDID_WHEEL_DELAY_WRITE = 0x14B3;
    public static final int CMDID_WIFI_CCODE_READ = 0x14B4;
    public static final int CMDID_WIFI_CCODE_WRITE = 0x14B5;
    //新增 Factory ProductID 写入命令
    public static final int CMDID_FACTORY_PID_WRITE = 0x14B6;
    //新增 Factory ProductID 读取命令
    public static final int CMDID_FACTORY_PID_READ = 0x14B7;
    //获取 key 激活状态命令
    public static final int CMDID_GET_KEY_ACTIVE_STATUS = 0x14B8;
    //USID读取命令
    public static final int CMDID_USID_READ = 0x14B9;
    //USID写入命令
    public static final int CMDID_USID_WRITE = 0x14BA;
    /**
     * 向下为05G 新增指令
     **/
    //读取 Rom 总空间
    public static final int CMDID_READ_ROM_TOTAL_SIZE = 0x14BB;
    //读取 Rom 可用空间
    public static final int CMDID_READ_ROM_AVAIL_SIZE = 0x14BC;
    //读取 Look Select
    public static final int CMDID_READ_LOOK_SELECT = 0x14BE;
    //写入 Look Select
    public static final int CMDID_WRITE_LOOK_SELECT = 0x14BD;
    //set XPR status
    public static final int CMDID_SET_XPR_STATUS = 0x14BF;
    public static final int CMDID_CAMERA_TEST_ON = 0x14C0;
    public static final int CMDID_CAMERA_TEST_OFF = 0x14C1;
    public static final int CMDID_CAMERA_TEST_OPEN = 0x14C2;
    public static final int CMDID_CAMERA_TEST_CAPTURE = 0x14C3;
    public static final int CMDID_SET_FAN_SPEED = 0x14C4;
    public static final int CMDID_MOTOR_CALIBRATION_RESET = 0x14C5;
    //set XPR shake status
    public static final int CMDID_SET_XPR_SHAKE_STATUS = 0x14C6;
    public static final int CMDID_GET_G_SENSOR_DATA = 0x14C7;
    public static final int CMDID_START_COLLECT_G_SENSOR = 0x14C8;
    public static final int CMDID_SAVE_G_SENSOR_DATA = 0x14C9;
    public static final int CMDID_READ_G_SENSOR_DATA = 0x14CA;
    public static final int CMDID_XPR_PIC_OPEN = 0x14CB;
    public static final int CMDID_XPR_PIC_OFF = 0x14CC;
    public static final int CMDID_LED_TEMP_READ = 0x14CD;
    public static final int CMDID_RGB_LED_CURRENT_READ = 0x14CE;
    public static final int CMDID_HDCP_14_MD5_READ = 0x14CF;
    public static final int CMDID_HDCP_22_MD5_READ = 0x14D0;
    public static final int CMDID_RESOLUTION_PIC_OPEN = 0x14D1;
    public static final int CMDID_RESOLUTION_PIC_OFF = 0x14D2;
    public static final int CMDID_START_AUTO_FOCUS = 0x14D3;
    public static final int CMDID_STOP_AUTO_FOCUS = 0x14D4;
    public static final int CMDID_ENABLE_AUTO_FOCUS = 0x14D5;
    public static final int CMDID_SET_AGING_LINE = 0x14D6;
    public static final int CMDID_GET_AGING_LINE = 0x14D7;
    public static final int CMDID_SET_AGING_LINE_VOL = 0x14D8;
    public static final int CMDID_GET_AGING_LINE_VOL = 0x14D9;
    public static final int CMDID_READ_GSENSOR_HORIZONTAL = 0x14DA;
    public static final int CMDID_READ_DLP_VERSION = 0x14DB;
    public static final int CMDID_ADJUST_VOLUME = 0x14DC;
    public static final int CMDID_KEYSTONE_ENABLE = 0x14DD;
    public static final int CMDID_KEYSTONE_SET = 0x14DE;
    public static final int CMDID_KEYSTONE_SETS = 0x14DF;
    public static final int CMDID_EXEC_PROJ_WRITE = 0x14E0;
    public static final int CMDID_EXEC_PROJ_READ = 0x14E1;
    public static final int CMDID_EXEC_I2C_WRITE = 0x14E2;
    public static final int CMDID_EXEC_I2C_READ = 0x14E3;
    public static final int CMDID_LOCAL_PLAY_ON = 0x14E4;
    public static final int CMDID_LOCAL_PLAY_OFF = 0x14E5;
    public static final int CMDID_MCU_FACT_ENABLE = 0x14E6;
    public static final int CMDID_KEY_CODE_TEST = 0x14E7;
    public static final int CMDID_REBOOT_UPDATE = 0x14E8;
    public static final int CMDID_AUTO_KEYSTONE_ON = 0x14E9;
    public static final int CMDID_AUTO_KEYSTONE_OFF = 0x14EA;
    public static final int CMDID_PATTERN_ON = 0x14EB;
    public static final int CMDID_PATTERN_SWITCH = 0x14EC;
    public static final int CMDID_PATTERN_OFF = 0x14ED;
    public static final int CMDID_FACTORY_MODE_WRITE = 0x14EE;
    public static final int CMDID_FACTORY_MODE_READ = 0x14EF;
    public static final int CMDID_USB_MEDIA_PLAY = 0x14F0;
    public static final int CMDID_USB_MEDIA_STOP = 0x14F1;

    public static final int CMDID_MOTOR_CALIBRATION_GET = 0x14F2;

    public static final int CMDID_TOF_CALIBRATION_STEP = 0x14F3;
    public static final int CMDID_TOF_DISTANCE_MEASURE = 0x14F4;
    public static final int CMDID_TOF_CAL_INFO_READ = 0x14F5;
    public static final int CMDID_TOF_TABLE_COLLECTING_START = 0x14F6;
    public static final int CMDID_TOF_TABLE_INFO_READ = 0x14F7;
    public static final int CMDID_TOF_AF_START = 0x14F8;
    public static final int CMDID_TOF_TABLE_INFO_WRITE = 0x14F9;

    public static final int CMDID_TERNARY_KEY_WRITE = 0x14FA;
    public static final int CMDID_TERNARY_KEY_READ = 0x14FB;

    public static final int CMDID_AF_CHECK_START = 0x14FC;
    public static final int CMDID_AF_CHECK_OFFSET_READ = 0x14FD;
    public static final int CMDID_PIC_RATIO = 0x14FE;
    /**************4. define CommandType***********************/
    public static final String CMD_TYPE_COMMON = "1";
    public static final String CMD_TYPE_ACTIVITY_ON = "2";
    public static final String CMD_TYPE_ACTIVITY_OFF = "3";

    /***************2. BOX command ID(the prefix is 0x2)**************/
    /* ------------------------------------ command id stop ---------------------------*/

    /* ------------------------------------ command property start ---------------------------*/
    public static final String CMD_TYPE_INNACTIVITY = "4";
    /**************5. define cmdDesc***********************/
    public static final String[][] cmdDesc = {
            {Integer.toHexString(CMDID_PIC_RATIO).toUpperCase(), "set Pic Ratio", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_AF_CHECK_START).toUpperCase(), "write MI ternary key", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_AF_CHECK_OFFSET_READ).toUpperCase(), "write MI ternary key", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_TERNARY_KEY_WRITE).toUpperCase(), "write MI ternary key", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_TERNARY_KEY_READ).toUpperCase(), "read MI ternary key", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_TOF_AF_START).toUpperCase(), "start Tof AF", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_TOF_TABLE_INFO_WRITE).toUpperCase(), "start Tof AF", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_TOF_TABLE_INFO_READ).toUpperCase(), "read TOF Distance&ClearlyStep table", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_TOF_TABLE_COLLECTING_START).toUpperCase(), "start collecting Distance&ClearlyStep data", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_TOF_CALIBRATION_STEP).toUpperCase(), " start tof calibration by step; ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_TOF_DISTANCE_MEASURE).toUpperCase(), " start tof measure and return distance", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_TOF_CAL_INFO_READ).toUpperCase(), " read tof calibration info,this cmd must be called after tof calibration ", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_MOTOR_CALIBRATION_GET).toUpperCase(), " read calibration info ", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_UNIFY_KEY_READ).toUpperCase(), " read unify key ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_UNIFY_KEY_WRITE).toUpperCase(), " write unify key", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_NETFLIX_KEY_READ).toUpperCase(), " read netflix key from file", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_NETFLIX_KEY_WRITE).toUpperCase(), " write netflix key to file", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_USB_MEDIA_PLAY).toUpperCase(), " open USB Media Play View", CMD_TYPE_ACTIVITY_ON, "SKIP"},
            {Integer.toHexString(CMDID_USB_MEDIA_STOP).toUpperCase(), " close USB Media Play View", CMD_TYPE_ACTIVITY_OFF, "SKIP"},
            {Integer.toHexString(CMDID_FACTORY_MODE_WRITE).toUpperCase(), "write Factory mode,1 for PCBA,2 for ASSM", CMD_TYPE_COMMON,"SKIP"},
            {Integer.toHexString(CMDID_FACTORY_MODE_READ).toUpperCase(), "read Factory Mode", CMD_TYPE_COMMON,"SKIP"},
            {Integer.toHexString(CMDID_AUDIO_RECORDER_ON).toUpperCase(), "start audio recorder with param (recording duration)", CMD_TYPE_COMMON,"SKIP"},
            {Integer.toHexString(CMDID_AUDIO_RECORDER_READ).toUpperCase(), "read audio file", CMD_TYPE_COMMON,"SKIP"},
            {Integer.toHexString(CMDID_AUDIO_RECORDER_PLAY).toUpperCase(), "play audio file", CMD_TYPE_COMMON,"SKIP"},
            {Integer.toHexString(CMDID_SP_WRITE).toUpperCase(), "write SP ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SP_READ).toUpperCase(), "read SP ", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_PATTERN_ON).toUpperCase(), " open android pattern", CMD_TYPE_ACTIVITY_ON,"SKIP"},
            {Integer.toHexString(CMDID_PATTERN_SWITCH).toUpperCase(), " switch android pattern ", CMD_TYPE_INNACTIVITY,"SKIP"},
            {Integer.toHexString(CMDID_PATTERN_OFF).toUpperCase(), " close android pattern ", CMD_TYPE_ACTIVITY_OFF,"SKIP"},

            {Integer.toHexString(CMDID_AUTO_KEYSTONE_ON).toUpperCase(), " open auto keystone func ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_AUTO_KEYSTONE_OFF).toUpperCase(), " close auto keystone func ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_REBOOT_UPDATE).toUpperCase(), " reboot update ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_KEY_CODE_TEST).toUpperCase(), "send key code to android ", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_MCU_FACT_ENABLE).toUpperCase(), "switch mcu into factory mode ", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_LOCAL_PLAY_ON).toUpperCase(), "local feng play open ", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_LOCAL_PLAY_OFF).toUpperCase(), "local feng play close ", CMD_TYPE_ACTIVITY_OFF},

            {Integer.toHexString(CMDID_EXEC_PROJ_WRITE).toUpperCase(), "echo data > /sys/class/projector/*  ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_EXEC_PROJ_READ).toUpperCase(), "cat /sys/class/projector/* ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_EXEC_I2C_WRITE).toUpperCase(), "echo data > /sys/class/i2c*/*  ", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_EXEC_I2C_READ).toUpperCase(), "cat /sys/class/i2c*/* ", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_KEYSTONE_SETS).toUpperCase(), "set 8 points[] keystone direct", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_KEYSTONE_SET).toUpperCase(), "set 8 points keystone direct", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_KEYSTONE_ENABLE).toUpperCase(), "set 8 points keystone enable", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_ADJUST_VOLUME).toUpperCase(), "adjust volume cmd increase & decrease", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_READ_DLP_VERSION).toUpperCase(), "read dlp version", CMD_TYPE_COMMON, "NULL"},

            {Integer.toHexString(CMDID_SET_AGING_LINE).toUpperCase(), "set aging count line for conan", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_GET_AGING_LINE).toUpperCase(), "get aging count line for conan", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_AGING_LINE_VOL).toUpperCase(), "set aging line volume for conan", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_GET_AGING_LINE_VOL).toUpperCase(), "get aging line volume conan", CMD_TYPE_COMMON, "NULL"},

            {Integer.toHexString(CMDID_READ_GSENSOR_HORIZONTAL).toUpperCase(), "read gsensor horizontal value for conan", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_ENABLE_AUTO_FOCUS).toUpperCase(), "enable auto focus for conan", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_START_AUTO_FOCUS).toUpperCase(), "start auto focus for conan", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_STOP_AUTO_FOCUS).toUpperCase(), "stop auto focus for conan", CMD_TYPE_ACTIVITY_OFF},
            {Integer.toHexString(CMDID_RESOLUTION_PIC_OPEN).toUpperCase(), "open RESOLUTION pic for conan", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_RESOLUTION_PIC_OFF).toUpperCase(), "close RESOLUTION pic for conan", CMD_TYPE_ACTIVITY_OFF},
            {Integer.toHexString(CMDID_HDCP_14_MD5_READ).toUpperCase(), "read hdcp 1.4 file md5", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP_22_MD5_READ).toUpperCase(), "read hdcp 2.2 file md5", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_RGB_LED_CURRENT_READ).toUpperCase(), "read rgb led current", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_LED_TEMP_READ).toUpperCase(), "LED temp Read", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_XPR_PIC_OPEN).toUpperCase(), "open XPR pic for conan", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_XPR_PIC_OFF).toUpperCase(), "close XPR pic for conan", CMD_TYPE_ACTIVITY_OFF},

            {Integer.toHexString(CMDID_READ_G_SENSOR_DATA).toUpperCase(), "read gSensor standard values", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SAVE_G_SENSOR_DATA).toUpperCase(), "save gSensor standard values", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_GET_G_SENSOR_DATA).toUpperCase(), "get gSensor values", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_START_COLLECT_G_SENSOR).toUpperCase(), "save standard gSensor data", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_SET_XPR_SHAKE_STATUS).toUpperCase(), "set XPR shake CMD", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MOTOR_CALIBRATION_RESET).toUpperCase(), "reset step motor, echo 1 > /sys/class/vg**/vg**/calibration", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SET_FAN_SPEED).toUpperCase(), "set FAN speed", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SET_XPR_STATUS).toUpperCase(), "set XPR status", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WRITE_LOOK_SELECT).toUpperCase(), "Write look select", CMD_TYPE_COMMON, "STRING|any"},
            {Integer.toHexString(CMDID_READ_LOOK_SELECT).toUpperCase(), "Read look select", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_READ_ROM_TOTAL_SIZE).toUpperCase(), "Read Rom Total Space", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_READ_ROM_AVAIL_SIZE).toUpperCase(), "Read Rom Available Space", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_CAMERA_TEST_ON).toUpperCase(), "CMD start camera Test Activity", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_CAMERA_TEST_OFF).toUpperCase(), "CMD finish camera Test Activity", CMD_TYPE_ACTIVITY_OFF},
            {Integer.toHexString(CMDID_CAMERA_TEST_OPEN).toUpperCase(), "CMD open camera", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_CAMERA_TEST_CAPTURE).toUpperCase(), "CMD camera capture ", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_ADJUST_VOLUME).toUpperCase(), "adjust volume cmd increase & decrease", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_FACTORY_PID_WRITE).toUpperCase(), "Factory PID write", CMD_TYPE_COMMON, "STRING|(20190)"},
            {Integer.toHexString(CMDID_FACTORY_PID_READ).toUpperCase(), "Factory PID read", CMD_TYPE_COMMON, "NULL"},
            /**************5.1 TV Segment***********************/
            {Integer.toHexString(CMDID_UDISK_2_CONTENT_TEST).toUpperCase(), "USB 2.0 disk content Test", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_UDISK_TEXT_VERIFY).toUpperCase(), "USB text verify", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_UDISK_DECT_2_0).toUpperCase(), "USB2.0 Test", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_UDISK_CHECK_SPEED).toUpperCase(), "get U disk Speed", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_UDISK_UNMOUNT_3_0).toUpperCase(), "USB3.0 Umount", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_UDISK_3_CONTENT_TEST).toUpperCase(), "USB 3.0 disk content test", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_USB_LIST_SIZE).toUpperCase(), "get size of USB device List", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_USB_LIST_GET).toUpperCase(), "get USB device name by position", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_SOUR_SWITCH).toUpperCase(), "Source Switch", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_SOUR_STOP).toUpperCase(), "Source Close", CMD_TYPE_ACTIVITY_OFF},
            {Integer.toHexString(CMDID_HDMI_ARC_ON).toUpperCase(), "HDMI ARC Test ON", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDMI_ARC_OFF).toUpperCase(), "HDMI ARC Test OFF", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDMI_1_CEC).toUpperCase(), "HDMI1 CEC Test", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDMI_1_CEC_NAME).toUpperCase(), "HDMI1 CEC Name Test", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDMI_2_CEC).toUpperCase(), "HDMI2 CEC Test", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDMI_2_CEC_NAME).toUpperCase(), "HDMI2 CEC name Test", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MEDIA_PLAY_AF).toUpperCase(), "LocalMediaPlay Start", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_MEDIA_PLAY_TOF).toUpperCase(), "LocalMediaPlay Start", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_MEDIA_STOP).toUpperCase(), "LocalMediaPlay Stop", CMD_TYPE_ACTIVITY_OFF},

            {Integer.toHexString(CMDID_GET_SYSTEM_PROP).toUpperCase(), "Touchpad Test", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MHL_CEC_3).toUpperCase(), "HDMI3 CEC Test", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MHL_CEC_3_NAME).toUpperCase(), "HDMI3 CEC Name Test", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_ENABLE_SPEAKER).toUpperCase(), "Speaker Enable", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_DISABLE_SPEAKER).toUpperCase(), "Speaker Disable", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_PLAY_SPEAKER_AUDIO).toUpperCase(), "Audio Play to Speaker", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_ENABLE_SPDIF).toUpperCase(), "Spdif Enable", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_DISABLE_SPDIF).toUpperCase(), "Spdif Disable", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_GET_SOUND_VOLUME).toUpperCase(), "Volume Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_SOUND_VOLUME).toUpperCase(), "Volume Set", CMD_TYPE_COMMON, "STRING|number"},

            {Integer.toHexString(CMDID_PLAY_SPDIF_AUDIO).toUpperCase(), "Audio Play to SPDIF", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_WIFI_QUICKCONNECT).toUpperCase(), "WIFI QuickConnect", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_CONNECT_WITH_PASS).toUpperCase(), "WIFI Connect with password", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_IPADDR_GET).toUpperCase(), "WIFI Get IP", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_WIFI_RSSI_GET).toUpperCase(), "WIFI Get RSSI", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_WIFI_STATE).toUpperCase(), "WIFI Set State", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SET_BT_STATE).toUpperCase(), "BT Set State", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_PING).toUpperCase(), "WIFI Ping", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BT_SCAN).toUpperCase(), "BT Scan", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_ETH_PING).toUpperCase(), "ETH Ping", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SET_ETH_STATE).toUpperCase(), "ETH set state", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_GET_ETH_STATE).toUpperCase(), "ETH get state", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SET_PRODUCT_FEATURE).toUpperCase(), "set product feature", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_CHECK_PRODUCT_FEATURE).toUpperCase(), "get product feature", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_IR_START).toUpperCase(), "IR Test Start", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_IR_STOP).toUpperCase(), "IR Test Stop", CMD_TYPE_ACTIVITY_OFF},

            //
            //--------------------info MiTV1~3 begin-----------------------
            {Integer.toHexString(CMDID_SERIAL_WRITE).toUpperCase(), "SN Write", CMD_TYPE_COMMON, "STRING|(99990000)"},
            {Integer.toHexString(CMDID_SERIAL_READ).toUpperCase(), "SN Read", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_BT_MAC_WRITE).toUpperCase(), "BTMAC Write", CMD_TYPE_COMMON, "STRING|(AF:AF:AF:AF:AF:AF)"},
            {Integer.toHexString(CMDID_BT_MAC_READ).toUpperCase(), "BTMAC Read", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_ETH_MAC_WRITE).toUpperCase(), "ETHMAC Write", CMD_TYPE_COMMON, "STRING|(BF:BF:BF:BF:BF:BF)"},
            {Integer.toHexString(CMDID_ETH_MAC_READ).toUpperCase(), "ETHMAC Read", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_WIFI_MAC_WRITE).toUpperCase(), "WIFIMAC Write", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_MAC_READ).toUpperCase(), "WIFIMAC Read", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP_KEY_WRITE).toUpperCase(), "HDCPKEY Write", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP_KEY_READ).toUpperCase(), "HDCPKEY Read", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP14_TX_KEY_WRITE).toUpperCase(), "write tx hdcp 1.4 key", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP14_TX_KEY_TRANSFER).toUpperCase(), "tx hdcp 1.4 key transfer", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP14_TX_KEY_READ).toUpperCase(), "read tx hdcp 1.4 key", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP22_TX_KEY_WRITE).toUpperCase(), "write tx hdcp 2.2 key", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP22_TX_KEY_READ).toUpperCase(), "read tx hdcp 2.2 key", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_HDCP22_TX_KEY_TRANSFER).toUpperCase(), "tx hdcp 2.2 key transfer", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MIRACAST_KEY_WRITE).toUpperCase(), "MIRACAST Write", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MIRACAST_KEY_TRANSFER).toUpperCase(), "MIRACAST Trans", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MIRACAST_KEY_READ).toUpperCase(), "MIRACAST Read", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MANUFACTURE_ID_WRITE).toUpperCase(), "MN Write", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_MANUFACTURE_ID_READ).toUpperCase(), "MN Read", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BOOT_ENV_READ).toUpperCase(), "read boot env by name", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BOOT_ENV_WRITE).toUpperCase(), "write boot env (name,value)", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIDEWINE_KEY_WRITE).toUpperCase(), "widewine key Write", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIDEWINE_KEY_READ).toUpperCase(), "widewine Read", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_ATTESTATION_KEY_WRITE).toUpperCase(), "google key attestation Write", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_ATTESTATION_KEY_READ).toUpperCase(), "google key attestation Read", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SET_KEY_ACTIVE).toUpperCase(), "google key attestation Read", CMD_TYPE_COMMON, "SKIP"},
            //--------------------info MiTV1~3 end-----------------------
            //--------------------info MiTV4~XXXXX begin-----------------------
            {Integer.toHexString(CMDID_N_PCBA_SERIAL_WRITE).toUpperCase(), "from MITV4, Write Pcba serial", CMD_TYPE_COMMON, "STRING|(PCB_SN_TEST)"},
            {Integer.toHexString(CMDID_N_PCBA_SERIAL_READ).toUpperCase(), "from MITV4, read pcba serial", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_N_PCBA_MANU_WRITE).toUpperCase(), "from MITV4, write pcba manufacture", CMD_TYPE_COMMON, "STRING|(PCB_MN_TEST)"},
            {Integer.toHexString(CMDID_N_PCBA_MANU_READ).toUpperCase(), "from MITV4, read pcba manufacture", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_N_ASSM_SERIAL_WRITE).toUpperCase(), "from MITV4, write assmbly serial", CMD_TYPE_COMMON, "STRING|(PCB_AS_SN_TEST)"},
            {Integer.toHexString(CMDID_N_ASSM_SERIAL_READ).toUpperCase(), "from MITV4, read assmbly serial", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_N_ASSM_MANU_WRITE).toUpperCase(), "from MITV4, write assmbly manufacture", CMD_TYPE_COMMON, "STRING|(PCB_AS_MN_TEST)"},
            {Integer.toHexString(CMDID_N_ASSM_MANU_READ).toUpperCase(), "from MITV4, read assmbly manufacture", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_N_BT_MAC_WRITE).toUpperCase(), "from MITV4, write bluetooth mac", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_N_BT_MAC_READ).toUpperCase(), "from MITV4, read bluetooth mac", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_N_ETH_MAC_WRITE).toUpperCase(), "from MITV4, write ethernet mac", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_N_ETH_MAC_READ).toUpperCase(), "from MITV4, read ethernet mac", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_N_DOLBY_DTS_CHECK).toUpperCase(), "from MITV4, check dolby and dts", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_N_GET_PANEL_ID_TAG).toUpperCase(), "from MITV4, check the ID tag for panel type", CMD_TYPE_COMMON, "SKIP"},
            //--------------------info MiTV4~XXXXX end-----------------------
            //--------------------media MiTV4~XXXXX begin-----------------------
            {Integer.toHexString(CMDID_N_TVVIEW_SOUR_START).toUpperCase(), "from MITV4, start source with tvview", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_N_TVVIEW_SOUR_STOP).toUpperCase(), "from MITV4, stop source with tvview", CMD_TYPE_ACTIVITY_OFF},
            {Integer.toHexString(CMDID_N_TVVIEW_SOUR_SWITCH).toUpperCase(), "from MITV4, Source Switch", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_N_AMLOGIC_AGING_ON).toUpperCase(), "for amlogic platform, start aging for unity tv", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_N_AMLOGIC_AGING_OFF).toUpperCase(), "for amlogic platform, stop aging for unity tv", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_N_SET_SCREEN_RESOLUTION).toUpperCase(), "set the resolution output to screen", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_N_CLOSE_DSP_DAP).toUpperCase(), "from ATMOS, stop dap in dsp", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_N_BYPASS_PQ).toUpperCase(), "from amlogic TV, enable/disable pq bypass", CMD_TYPE_COMMON, "SKIP"},
            //--------------------media MiTV4~XXXXX end-----------------------
            {Integer.toHexString(CMDID_N_SET_GPIO_OUT_STAT).toUpperCase(), "from MITV4, set gpio out status", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_N_GET_GPIO_IN_STAT).toUpperCase(), "from MITV4, get gpio in status", CMD_TYPE_INNACTIVITY},
            {Integer.toHexString(CMDID_N_SET_VCOM_I2C).toUpperCase(), "for MITV4,set vcom command to i2c", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_N_GET_VCOM_I2C).toUpperCase(), "for MITV4,get vcom data from i2c", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_KTV_ENABLE).toUpperCase(), "KTV Enable", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_CONNECT_24AP).toUpperCase(), "WIFI Conn to 24G", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_CONNECT_5AP).toUpperCase(), "WIFI Conn to 5G", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_SPEED_START).toUpperCase(), "WIFISPEED Start", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_SPEED_PARA).toUpperCase(), "WIFI SPEED with parameter", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_SPEED_STOP).toUpperCase(), "WIFISPEED Stop", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_WIFI_WAKEUP).toUpperCase(), "WIFI WakeUp", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BT_PCM_LOOPBACK).toUpperCase(), "BTPCM Loopback", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BT_THROUGH_OUTPUT).toUpperCase(), "BT Throughput", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BT_FORCE_MATCH).toUpperCase(), "BT Force Match", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BT_WAKEUP).toUpperCase(), "BT WakeUp", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BACKLIGHT_SET).toUpperCase(), "Backlight Set", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_BACKLIGHT_GET).toUpperCase(), "Backlight Get", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_AGING_ON).toUpperCase(), "Aging On", CMD_TYPE_ACTIVITY_ON},
            {Integer.toHexString(CMDID_AGING_OFF).toUpperCase(), "Aging Off", CMD_TYPE_ACTIVITY_OFF},
            {Integer.toHexString(CMDID_AGING_RESET_TIMER).toUpperCase(), "Aging Rest Timer", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_AGING_GET_TIMER).toUpperCase(), "Aging Get Timer", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_AGING_TIMER_START).toUpperCase(), "Aging Start Timer", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_AGING_TIMER_STOP).toUpperCase(), "Aging Stop Timer", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_PATTERN_SET).toUpperCase(), "Pattern Set", CMD_TYPE_COMMON, "RGB|(255:0:0)"},
            {Integer.toHexString(CMDID_PATTERN_DISABLE).toUpperCase(), "Pattern Disable", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SYSTEM_MODE_GET).toUpperCase(), "SystemMode Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_FACTORY_RESET).toUpperCase(), "Factory Reset", CMD_TYPE_COMMON, "NULL"},

            {Integer.toHexString(CMDID_SYSTEM_MODE_CHANGE).toUpperCase(), "SystemMode Switch", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SYSTEM_MODE_CHANGE_NEW).toUpperCase(), "SystemMode Switch NEW", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SYSTEM_REBOOT).toUpperCase(), "System Reboot", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SYSTEM_REBOOT_RECOVERY).toUpperCase(), "System Reboot & enter recovery", CMD_TYPE_COMMON, "SKIP"},
            {Integer.toHexString(CMDID_SYSTEM_SHUTDOWN).toUpperCase(), "System Shutdown", CMD_TYPE_COMMON, "SKIP"},

            {Integer.toHexString(CMDID_LIGHT_SENSOR).toUpperCase(), "LightSensor Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_PRODUCTREGION).toUpperCase(), "ProductRegion Set", CMD_TYPE_COMMON, "STRING|any"},
            {Integer.toHexString(CMDID_GET_PRODUCTREGION).toUpperCase(), "ProductRegion Get", CMD_TYPE_COMMON, "NULL"},

            //PQ
            {Integer.toHexString(CMDID_POSTGAIN_RED_SET).toUpperCase(), "PostGain Red Set", CMD_TYPE_COMMON, "HEX|(00,02,00)"},
            {Integer.toHexString(CMDID_POSTGAIN_GREEN_SET).toUpperCase(), "PostGain Green Set", CMD_TYPE_COMMON, "HEX|(00,03,00)"},
            {Integer.toHexString(CMDID_POSTGAIN_BLUE_SET).toUpperCase(), "PostGain Blue Set", CMD_TYPE_COMMON, "HEX|(00,04,00)"},
            {Integer.toHexString(CMDID_POSTGAIN_RED_GET).toUpperCase(), "PostGain Red Get", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_POSTGAIN_GREEN_GET).toUpperCase(), "PostGain Green Get", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_POSTGAIN_BLUE_GET).toUpperCase(), "PostGain Blue Get", CMD_TYPE_COMMON, "STRING|(0,1)"},

            {Integer.toHexString(CMDID_POSTOFFS_RED_SET).toUpperCase(), "PostOffs Red Set", CMD_TYPE_COMMON, "HEX|(00,04,00)"},
            {Integer.toHexString(CMDID_POSTOFFS_GREEN_SET).toUpperCase(), "PostOffs Green Set", CMD_TYPE_COMMON, "HEX|(00,04,00)"},
            {Integer.toHexString(CMDID_POSTOFFS_BLUE_SET).toUpperCase(), "PostOffs Blue Set", CMD_TYPE_COMMON, "HEX|(00,04,00)"},
            {Integer.toHexString(CMDID_POSTOFFS_RED_GET).toUpperCase(), "PostOffs Red Get", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_POSTOFFS_GREEN_GET).toUpperCase(), "PostOffs Green Get", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_POSTOFFS_BLUE_GET).toUpperCase(), "PostOffs Blue Get", CMD_TYPE_COMMON, "STRING|(0,1)"},

            {Integer.toHexString(CMDID_PQ_DB_SAVE).toUpperCase(), "PQ DataBase Save", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_PQ_PANEL_SET).toUpperCase(), "PQ Panel Set", CMD_TYPE_COMMON, "NULL"},
            //Pic Mode
            {Integer.toHexString(CMDID_PIC_MODE_RESET).toUpperCase(), "PIC Mode Reset", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_GET_PIC_MODE).toUpperCase(), "PIC Mode Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_PIC_MODE).toUpperCase(), "PIC Mode Set", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_GET_PIC_BRIGHTNESS).toUpperCase(), "PIC Brightness Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_PIC_BRIGHTNESS).toUpperCase(), "PIC Brightness Set", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_GET_PIC_CONTRAST).toUpperCase(), "PIC Contrast Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_PIC_CONTRAST).toUpperCase(), "PIC Contrast Set", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_GET_PIC_SHARPNESS).toUpperCase(), "PIC Sharpness Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_PIC_SHARPNESS).toUpperCase(), "PIC Sharpness Set", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_GET_PIC_HUE).toUpperCase(), "PIC Hue Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_PIC_HUE).toUpperCase(), "PIC Hue Set", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_GET_PIC_SATURATION).toUpperCase(), "PIC Saturation Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_PIC_SATURATION).toUpperCase(), "PIC Saturation Set", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_GET_PIC_COLORTEMP).toUpperCase(), "PIC ColorTemp Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_PIC_COLORTEMP).toUpperCase(), "PIC ColorTemp Set", CMD_TYPE_COMMON, "STRING|(0,1)"},

            {Integer.toHexString(CMDID_MODEL_NAME_GET).toUpperCase(), "ModelName Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_GET_SYSTEM_FW_VER).toUpperCase(), "SystemFW Ver Get", CMD_TYPE_COMMON, "NULL"},

            {Integer.toHexString(CMDID_LED_TEST).toUpperCase(), "LED Test", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_RC_LOCK).toUpperCase(), "RC Lock", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_HDCP_KSV_GET).toUpperCase(), "HDCP KSV Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_HDMI_EDID).toUpperCase(), "HDMI EDID Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SYSTEM_PARTITION_CHECK).toUpperCase(), "Partition Check", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_BTRC_MAC_SET).toUpperCase(), "BTRC MAC Set", CMD_TYPE_COMMON, "STRING|mac"},
            {Integer.toHexString(CMDID_BTRC_MAC_GET).toUpperCase(), "BTRC MAC Get", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_WIFI_DISCONNECT).toUpperCase(), "WIFI Disconnect", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_WOOFER_PLUGIN).toUpperCase(), "subwoofer detect", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_CHECK_HDCPKEY14_VALID).toUpperCase(), "check HDCP key 1.4 valid or no", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_CHECK_HDCPKEY22_VALID).toUpperCase(), "check HDCP key 2.2 valid or no", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_AUTORUN_STATUS).toUpperCase(), "set the flag to do auto-run", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_GET_AUTORUN_STATUS).toUpperCase(), "get the flag of auto-run", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_AUTORUN_COMMAND).toUpperCase(), "set auto-run command", CMD_TYPE_COMMON, "STRING|(1475/NULL)"},
            {Integer.toHexString(CMDID_GET_AUTORUN_COMMAND).toUpperCase(), "get auto-run command", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_CHECK_WIFI_SCAN_RESULT).toUpperCase(), "check wifi scan result", CMD_TYPE_COMMON, "STRING|(MITEST_24G_1)"},
            {Integer.toHexString(CMDID_BT_FIND_DEVICE).toUpperCase(), "check if bt scan result is empty", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_WIFI_SCAN_START).toUpperCase(), "wifi scan start", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_SOUND_OUTPUT_MODE).toUpperCase(), "change sound output mode", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_SET_SPEAKER_SWITCH).toUpperCase(), "change speakers enable/disable ", CMD_TYPE_COMMON, "STRING|number"},
            /**************5.2  Segment***********************/
            {Integer.toHexString(CMDID_PRODUCT_UIID_WRITE).toUpperCase(), "Product UI ID Write", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_PRODUCT_UIID_READ).toUpperCase(), "Product UI ID Read", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_DLP_SN_READ).toUpperCase(), "DLP SN Read", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_SET_BODY_DETECT).toUpperCase(), "set body detect status", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_BODY_DETECT_STATUS).toUpperCase(), "get body detect status", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_UDISK_FILE_CHECK).toUpperCase(), "udisk file check", CMD_TYPE_COMMON, "STRING|any"},
            {Integer.toHexString(CMDID_DLP_SCREEN_CHECK).toUpperCase(), "dlp screen check", CMD_TYPE_COMMON, "STRING|(0,1,2,3)"},
            {Integer.toHexString(CMDID_SET_DLP_SCREEN_CHECK).toUpperCase(), "set dlp screen check", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_SET_MOTOR_SCALE).toUpperCase(), "set motor scale", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_START_BODY_DETECT).toUpperCase(), "body detect start", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_STOP_BODY_DETECT).toUpperCase(), "body detect stop", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_BODY_DETECT_COUNT).toUpperCase(), "body detect count", CMD_TYPE_COMMON, "STRING|(0,1)"},
            {Integer.toHexString(CMDID_LINE_OUT_ENABLE).toUpperCase(), "line out enable", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_LINE_OUT_DISABLE).toUpperCase(), "line out disable", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_PQ_ENABLE).toUpperCase(), "pq enable", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_PQ_DISABLE).toUpperCase(), "pq disable", CMD_TYPE_COMMON, "NULL"},

            {Integer.toHexString(CMDID_DLP_INFO_SYNC).toUpperCase(), "dlp info sync", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_DLP_INFO_SAVE).toUpperCase(), "dlp info save", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_WHEEL_DELAY_READ).toUpperCase(), "wheel delay read", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_WHEEL_DELAY_WRITE).toUpperCase(), "wheel delay write", CMD_TYPE_COMMON, "STRING|number"},
            {Integer.toHexString(CMDID_WIFI_CCODE_READ).toUpperCase(), "wifi country code read", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_WIFI_CCODE_WRITE).toUpperCase(), "wifi country code write", CMD_TYPE_COMMON, "STRING|any"},
            {Integer.toHexString(CMDID_GET_KEY_ACTIVE_STATUS).toUpperCase(), "get key active status", CMD_TYPE_COMMON, "STRING|(hdcp_14_rx,hdcp_22_rx)"},
            {Integer.toHexString(CMDID_USID_READ).toUpperCase(), "usid read command", CMD_TYPE_COMMON, "NULL"},
            {Integer.toHexString(CMDID_USID_WRITE).toUpperCase(), "usid write command", CMD_TYPE_COMMON, "STRING|any"},
            {Integer.toHexString(CMDID_FUNC_TEST).toUpperCase(), "factory method func Test", CMD_TYPE_COMMON, "STRING|any"},
    };
    /* -------------------- command table operations managements stop ---------------*/
    /* -------------------- activity operations managements start -------------------*/
    /* start --- <command id,activity name> definition */
    private static final String APPLICATION_PACKAGENAME = "com.fengmi.factory";
    private static final String ACTIVITY_PACKAGENAME = "com.fengmi.factory.activity";
    /* ------------------------------------ command property stop ---------------------------*/

    /* ------------------------------------ command table start ---------------------------*/
    private static final String ACTION_FILTER_PREFIX = "com.duokan.action.";
    //TODO, you should add your test item for UI interaction
    private static final String Cmd_Activity[][] = {
            {Integer.toHexString(CMDID_USB_MEDIA_PLAY).toUpperCase(), "AudioRecoder"},
            {Integer.toHexString(CMDID_MEDIA_PLAY_AF).toUpperCase(), "LocalMediaAF"},
            {Integer.toHexString(CMDID_MEDIA_PLAY_TOF).toUpperCase(), "LocalMedia"},
            {Integer.toHexString(CMDID_LOCAL_PLAY_ON).toUpperCase(), "LocalPlay"},
            {Integer.toHexString(CMDID_N_TVVIEW_SOUR_START).toUpperCase(), "InputSourceForTvView"},
            {Integer.toHexString(CMDID_CAMERA_TEST_ON).toUpperCase(), "UVCCamera"},
            {Integer.toHexString(CMDID_XPR_PIC_OPEN).toUpperCase(), "PicTest"},
            {Integer.toHexString(CMDID_RESOLUTION_PIC_OPEN).toUpperCase(), "PicTest"},
            {Integer.toHexString(CMDID_START_AUTO_FOCUS).toUpperCase(), "AutoFocus"},
            {Integer.toHexString(CMDID_PATTERN_ON).toUpperCase(), "PatternTest"},
    };
    /* ------------------------------------ class property start ---------------------------*/
    static private TvCommandDescription _TvCommandDescription = null;
    /* -------------------- activity operations managements stop -------------------*/
    /* -------------------- activity operations - window status start -------------------*/
    private boolean WindowCmdWorkingStatus = false;

    protected TvCommandDescription() {
    }

    public static TvCommandDescription getInstance() {
        if (_TvCommandDescription == null) {
            synchronized (TvCommandDescription.class) {
                if (_TvCommandDescription == null) {
                    _TvCommandDescription = new TvCommandDescription();
                }
            }
        }
        return _TvCommandDescription;
    }

    public static String getCmdNameForCmdid(String cmdid) {
        if (cmdid == null) return null;
        for (int i = 0; i < Cmd_Activity.length; i++) {
            if (cmdid.equals(Cmd_Activity[i][0])) {
                return Cmd_Activity[i][1];
            }
        }
        return null;
    }

    public static ComponentName getComponentNameForCmd(String cmdid) {
        if (cmdid == null) return null;
        for (int i = 0; i < Cmd_Activity.length; i++) {
            if (cmdid.equals(Cmd_Activity[i][0])) {
                return new ComponentName(APPLICATION_PACKAGENAME, ACTIVITY_PACKAGENAME + "." + Cmd_Activity[i][1]);
            }
        }
        return null;
    }

    public static String getFilterActionForCmd(String cmdid) {
        if (cmdid == null) return null;
        for (int i = 0; i < Cmd_Activity.length; i++) {
            if (cmdid.equals(Cmd_Activity[i][0])) {
                return ACTION_FILTER_PREFIX + Cmd_Activity[i][1];
            }
        }
        return null;
    }

    /* ------------------------------------ command table stop ---------------------------*/
    /* -------------------- command table operations managements start -------------------*/
    public String getCmdExplanationByID(String id) {
        String ret = null;
        if (id == null) return ret;
        for (int i = 0; i < cmdDesc.length; i++) {
            if (id.equals(cmdDesc[i][0])) {
                ret = cmdDesc[i][1];
                break;
            }
        }
        return ret;
    }

    public String getCmdTypeByID(String id) {
        String ret = null;
        if (id == null) return ret;
        for (int i = 0; i < cmdDesc.length; i++) {
            if (id.equals(cmdDesc[i][0])) {
                ret = cmdDesc[i][2];
                break;
            }
        }
        return ret;
    }

    public String[] getCmdDescByID(String id) {
        String ret[] = null;
        if (id == null) return ret;
        for (int i = 0; i < cmdDesc.length; i++) {
            if (id.equals(cmdDesc[i][0])) {
                ret = cmdDesc[i];
                break;
            }
        }
        return ret;
    }

    public int getCmdIndexByID(String id) {
        int ret = 0;
        if (id == null) return ret;
        for (int i = 0; i < cmdDesc.length; i++) {
            if (id.equals(cmdDesc[i][0])) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    public String[] getCmdInfoByIndex(int index) {
        String ret[] = null;
        if (index < 0 || index > cmdDesc.length) return ret;
        ret = cmdDesc[index];
        return ret;

    }

    public String getCmdExplanationByIndex(int index) {
        String ret = null;
        if (index < 0 || index > cmdDesc.length) return ret;
        ret = cmdDesc[index][1];
        return ret;
    }

    public String getCmdTypeByIndex(int index) {
        String ret = null;
        if (index < 0 || index > cmdDesc.length) return ret;
        ret = cmdDesc[index][2];
        return ret;
    }

    public void setWindCmdWorkInactive() {
        Log.i(TAG, "set Window idle");
        WindowCmdWorkingStatus = false;
    }

    public void setWindCmdWorkActive() {
        Log.i(TAG, "set Window active");
        WindowCmdWorkingStatus = true;
    }

    public boolean CheckWindWorkFlag() {
        Log.i(TAG, "WindowCmdWorkingStatus = " + WindowCmdWorkingStatus);
        return WindowCmdWorkingStatus;
    }
    /* -------------------- activity operations - window status start -------------------*/
}
