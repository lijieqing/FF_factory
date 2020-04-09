package com.fengmi.factory.service;

import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;
import com.fengmi.factory_test_interf.sdk_globle.SettingManager;
import com.fengmi.factory_test_interf.sdk_globle.TvCommandDescription;
import com.fengmi.factory_test_interf.sdk_utils.MICUtil;
import com.fengmi.factory_test_interf.sdk_utils.NetworkUtil;
import com.fengmi.factory_test_interf.sdk_utils.SPUtil;
import com.fengmi.factory_test_interf.sdk_utils.USBUtils;

import java.io.PrintWriter;
import java.util.List;

public class CommandService extends BaseCmdService {
    private static final String BURNINGSTOP = "BurningStop";
    private static final String BURNING_CLEARCOUNT = "BurningClearCount";
    private static final String BURNING_READCOUNT = "BurningReadCount";
    private static final int HDMI1_ID = 23;
    private static final int HDMI2_ID = 24;
    private static final int HDMI3_ID = 25;
    private static final int HDMI4_ID = 26;
    private static String READ_ERR = "read error";
    private static String productType = null;
    private static boolean inOperating = false;
    private Handler mHandler = new Handler();
    private Runnable mDisableBodyDetect = new Runnable() {
        @Override
        public void run() {
            boolean ret = SDKManager.getUtilManager().setBodyDetectStatus("1");//disable Body Detect
            if (!ret) {
                mHandler.postDelayed(this, 1000);//retry
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Factory command service");
        productType = SDKManager.getAndroidOSManagerInterf().getSystemProperty(SettingManager.SYSPROP_PRODUCTMODEL, "");
        prepareFactorytest();

    }

    @Override
    public void handleCommand(String msgid, String param) {
        Log.i(TAG, "[CMD] handleCommand cmdid : " + msgid + " para :" + param);
        if (FactorySetting.COMMAND_PRODUCT_TYPE_TV.equals(msgid.substring(0, 1))) {
            Log.i(TAG, "[CMD] TV Command");
            try {
                handleCommandTv(msgid, param);
            } catch (RemoteException e) {
                // TODO: 2020/3/27
            }
        } else {
            Log.i(TAG, "[CMD] can't find this command");
        }
    }

    @Override
    public void setResultOuter(PrintWriter writer) {

    }

    private void handleCommandTv(String cmdid, String Param) throws RemoteException {
        //print some command description information
        String[] cmdInfo;
        cmdInfo = mTvCd.getCmdDescByID(cmdid);
        if (cmdInfo == null) {
            Log.i(TAG, "[CMD] can't find this command");
            mBinder.setResult_bool(cmdid, false);
            return;
        }
        Log.i(TAG, "[CMD] Description: " + cmdInfo[1] + ",[CMD] Type: " + cmdInfo[2]);
        String param = "";
        if (!TextUtils.isEmpty(Param)) {
            param = changeStringToAscII(Param);
        }
        /**
         * response 是用来描述是否需要进行指令回复的
         * 当一个指令携带的参数是 fake 时就认为是不需要回复的
         *
         * response 用在内部发送广播的情况，当你想真正使用 response 时，请在对应指令的case下做进一步处理
         *
         * 目前使用了 response 特性的指令是 CMDID_STOP_AUTO_FOCUS、CMDID_ENABLE_AUTO_FOCUS、CMDID_START_AUTO_FOCUS
         * 可以根据 CMDID = 0x14F6 来跟踪使用流程
         */
        boolean response = !param.equals("fake");

        if (response && checkTvWindowInOperating(cmdid)) {
            inOperating = true;
            Log.d(TAG, "checkTvWindowInOperating is true, so we return");
            return;
        }
        inOperating = false;

        Log.i(TAG, "handleCommand cmdid : " + cmdid + " para :" + param);
        //now command status list just manager the activity function, so normal and
        //innActivity command should not be put into list
        Command c = null;
        if (response && cmdInfo[2].equals(TvCommandDescription.CMD_TYPE_ACTIVITY_ON)) {
            c = addRunningCommand(cmdid, param);
        } else {
            c = new Command(cmdid, param);
        }
        int id = -1;
        String result = null;
        try {
            id = Integer.parseInt(cmdid, 16);
        } catch (NumberFormatException e) {
        }

        byte[] value = new byte[1];
        String[] val;
        int colorTemp = 0;
        int gain = 0;
        int offset = 0;
        int temp;
        boolean retFlag = false;
        Log.i(TAG, "handleCommand cmdid :" + id);

        switch (id) {
            case TvCommandDescription.CMDID_FUNC_TEST:
            /*===================================case for Activity control start======================================*/
            case TvCommandDescription.CMDID_USB_MEDIA_PLAY:
            case TvCommandDescription.CMDID_CAMERA_TEST_ON:
            case TvCommandDescription.CMDID_N_TVVIEW_SOUR_START:
            case TvCommandDescription.CMDID_LOCAL_PLAY_ON:
            case TvCommandDescription.CMDID_MEDIA_PLAY_TOF:
            case TvCommandDescription.CMDID_XPR_PIC_OPEN:
            case TvCommandDescription.CMDID_PATTERN_ON:
            case TvCommandDescription.CMDID_RESOLUTION_PIC_OPEN:
                mBinder.setResult_bool(cmdid, true);
                break;
            case TvCommandDescription.CMDID_USB_MEDIA_STOP:
            case TvCommandDescription.CMDID_LOCAL_PLAY_OFF:
            case TvCommandDescription.CMDID_CAMERA_TEST_OFF:
                TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_STOP, cmdid, param);
                break;
            case TvCommandDescription.CMDID_MEDIA_PLAY_AF:
                //2. start burn timer
                SDKManager.getMediaTestManager().agingSetTimerStatus(true);
                mBinder.setResult_bool(cmdid, true);
                break;
            case TvCommandDescription.CMDID_MEDIA_STOP:
                if (getTvRunningWindCmd() != null)
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_STOP, cmdid, param);
                //2. stop burn timer
                SDKManager.getMediaTestManager().agingSetTimerStatus(false);
                break;
            case TvCommandDescription.CMDID_N_TVVIEW_SOUR_SWITCH:

            case TvCommandDescription.CMDID_SOUR_SWITCH:
                String sour = "SOURCE:" + param;
                if (getTvRunningWindCmd() != null)
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_BUSINESS, cmdid, sour);
                break;
            case TvCommandDescription.CMDID_N_TVVIEW_SOUR_STOP:

            case TvCommandDescription.CMDID_SOUR_STOP:
                Log.i(TAG, "[CMD] running Cmd: " + getTvRunningWindCmd().cmdid);
                Log.i(TAG, "[CMD] curr cmd:" + cmdid);
                Log.i(TAG, "[CMD] para: " + param);
                if (getTvRunningWindCmd() != null)
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_STOP, cmdid, param);
                retFlag = true;
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_XPR_PIC_OFF:
                if (getTvRunningWindCmd() != null)
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_STOP, cmdid, param);
                break;
            case TvCommandDescription.CMDID_RESOLUTION_PIC_OFF:
            case TvCommandDescription.CMDID_PATTERN_OFF:
                if (getTvRunningWindCmd() != null) {
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_STOP, cmdid, param);
                }
                break;
            case TvCommandDescription.CMDID_ENABLE_AUTO_FOCUS:
                if (response && getTvRunningWindCmd() != null) {
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_BUSINESS, cmdid, param);
                } else {
                    //因为这是内部发送的指令，我们创建对应的 Activity 指令，并直接执行
                    Command fakeCMD = new Command("14D3", "fake");
                    fakeCMD.state = Command.COMMAND_STATE_START;
                    TvSetControlMsg(fakeCMD, FactorySetting.COMMAND_TASK_BUSINESS, cmdid, param);
                }
                break;
            case TvCommandDescription.CMDID_START_AUTO_FOCUS:
                if (response) {
                    mBinder.setResult_bool(cmdid, true);
                }
                break;
            case TvCommandDescription.CMDID_STOP_AUTO_FOCUS:
                if (response && getTvRunningWindCmd() != null) {
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_STOP, cmdid, param);
                } else {
                    //因为这是内部发送的指令，我们创建对应的 Activity 指令，并直接执行
                    Command fakeCMD = new Command("14D3", "fake");
                    fakeCMD.state = Command.COMMAND_STATE_START;
                    TvSetControlMsg(fakeCMD, FactorySetting.COMMAND_TASK_STOP, cmdid, param);
                }
                break;
            case TvCommandDescription.CMDID_PATTERN_SWITCH:
            case TvCommandDescription.CMDID_CAMERA_TEST_CAPTURE:

            case TvCommandDescription.CMDID_CAMERA_TEST_OPEN:
                if (getTvRunningWindCmd() != null) {
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_BUSINESS, cmdid, param);
                }
                break;
            /*===================================case for Activity control end======================================*/

            case TvCommandDescription.CMDID_CHECK_STEP_ALGORITHM:
                mBinder.setResult_bool(cmdid, SDKManager.getSensorManager().checkStepAlgorithm(param));
                break;

            case TvCommandDescription.CMDID_GET_STEP_ALGORITHM:
                mBinder.setResult_string(cmdid, SDKManager.getSensorManager().getStepByAlgorithm(param));
                break;

            case TvCommandDescription.CMDID_PIC_RATIO:
                String[] picParams = param.split(",");
                int picLen = picParams.length;
                if (picLen == 2) {
                    int srcID = Integer.parseInt(picParams[0]);
                    int modeID = Integer.parseInt(picParams[1]);
                    mBinder.setResult_bool(cmdid, SDKManager.getAmlogicManagerInterf().setDisplayMode(srcID, modeID));
                } else {
                    mBinder.setResult_bool(cmdid, false);
                }
                break;

            case TvCommandDescription.CMDID_AF_CHECK_START:
                mBinder.setResult_bool(cmdid, SDKManager.getMotorManager().startAFCheck());
                break;

            case TvCommandDescription.CMDID_AF_CHECK_OFFSET_READ:
                mBinder.setResult_string(cmdid, SDKManager.getMotorManager().readAFCheckCallback() + "");
                break;

            case TvCommandDescription.CMDID_TERNARY_KEY_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setTernaryKey(Param));
                break;

            case TvCommandDescription.CMDID_TERNARY_KEY_READ:
                mBinder.setResult_byte(cmdid, SDKManager.getInfoAccessManager().getTernaryKey());
                break;

            case TvCommandDescription.CMDID_TOF_TABLE_INFO_READ:
                mBinder.setResult_string(cmdid, SDKManager.getSensorManager().readDistanceStepTable());
                break;

            case TvCommandDescription.CMDID_TOF_TABLE_INFO_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getSensorManager().writeDistanceStepTable(param));
                break;

            case TvCommandDescription.CMDID_TOF_AF_START:
                mBinder.setResult_bool(cmdid, SDKManager.getSensorManager().startTofAF(this));
                break;

            case TvCommandDescription.CMDID_TOF_TABLE_COLLECTING_START:
                String[] disTimes = param.split(",");
                int len = disTimes.length;
                if (len == 3) {
                    String dis = disTimes[0];
                    String tim = disTimes[1];
                    String split = disTimes[2];
                    mBinder.setResult_bool(cmdid, SDKManager.getSensorManager().startTableCollecting(this, dis, tim, split));
                } else {
                    mBinder.setResult_bool(cmdid, false);
                }
                break;
            case TvCommandDescription.CMDID_TOF_CAL_INFO_READ:
                mBinder.setResult_byte(cmdid, SDKManager.getSensorManager().readTOFCalibrationInfo());
                break;

            case TvCommandDescription.CMDID_TOF_DISTANCE_MEASURE:
                mBinder.setResult_string(cmdid, SDKManager.getSensorManager().readTOFDistance());
                break;

            case TvCommandDescription.CMDID_TOF_CALIBRATION_STEP:
                int step = 10;
                if (TextUtils.isDigitsOnly(param)) {
                    step = Integer.parseInt(param);
                }
                mBinder.setResult_bool(cmdid, SDKManager.getSensorManager().startTOFCalibration(step));
                break;

            case TvCommandDescription.CMDID_MOTOR_CALIBRATION_GET:
                int i = 10;
                if (TextUtils.isDigitsOnly(param)) {
                    i = Integer.parseInt(param);
                }
                mBinder.setResult_string(cmdid, SDKManager.getMotorManager().getCalibration(i));
                break;

            case TvCommandDescription.CMDID_UNIFY_KEY_READ:
                mBinder.setResult_string(cmdid, SDKManager.getUtilManager().readUnifyKey(param));
                break;

            case TvCommandDescription.CMDID_UNIFY_KEY_WRITE:
                String[] unifyKeyInfoList = param.split(",");
                int lens = unifyKeyInfoList.length;
                if (lens == 2) {
                    String key = unifyKeyInfoList[0];
                    String data = unifyKeyInfoList[1];
                    mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().writeUnifyKey(key, data));
                } else {
                    mBinder.setResult_bool(cmdid, false);
                }
                break;

            case TvCommandDescription.CMDID_NETFLIX_KEY_READ:
                mBinder.setResult_byte(cmdid, SDKManager.getInfoAccessManager().getNetflixKey());
                break;

            case TvCommandDescription.CMDID_NETFLIX_KEY_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setNetflixKey(Param));
                break;

            case TvCommandDescription.CMDID_SP_READ:
                mBinder.setResult_string(cmdid, (String) SPUtil.getParam(this, param, "null"));
                break;

            case TvCommandDescription.CMDID_SP_WRITE:
                String[] spInfoList = param.split(",");
                int lensp = spInfoList.length;
                if (lensp == 2) {
                    String key = spInfoList[0];
                    String data = spInfoList[1];
                    SPUtil.setParam(this, key, data);
                    mBinder.setResult_bool(cmdid, true);
                } else {
                    mBinder.setResult_bool(cmdid, false);
                }
                break;

            case TvCommandDescription.CMDID_BOOT_ENV_READ:
                mBinder.setResult_string(cmdid, SDKManager.getUtilManager().readBootEnv(param));
                break;

            case TvCommandDescription.CMDID_BOOT_ENV_WRITE:
                String[] bootInfoList = param.split(",");
                int envlen = bootInfoList.length;
                if (envlen == 2) {
                    String name = bootInfoList[0];
                    String data = bootInfoList[1];
                    mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().writeBootEnv(name, data));
                } else {
                    mBinder.setResult_bool(cmdid, false);
                }
                break;

            case TvCommandDescription.CMDID_FACTORY_MODE_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().writeFactoryMode(param));
                break;

            case TvCommandDescription.CMDID_FACTORY_MODE_READ:
                mBinder.setResult_string(cmdid, SDKManager.getUtilManager().readFactoryMode());
                break;

            case TvCommandDescription.CMDID_AUDIO_RECORDER_ON:
                mBinder.setResult_bool(cmdid, MICUtil.startTinyCap(param));
                break;

            case TvCommandDescription.CMDID_AUDIO_RECORDER_PLAY:
                mBinder.setResult_bool(cmdid, MICUtil.startTinyPlay());
                break;

            case TvCommandDescription.CMDID_WIFI_CONNECT_WITH_PASS:
                String[] wifiInfoList = param.split(",");
                int passlen = wifiInfoList.length;
                if (passlen == 2) {
                    String wifiName = wifiInfoList[0];
                    String wifiPass = wifiInfoList[1];
                    NetworkUtil.scanWifiInfo(this);
                    NetworkUtil.connectWifi(this, wifiName, wifiPass, "WPA");
                    mBinder.setResult_bool(cmdid, true);
                } else {
                    mBinder.setResult_bool(cmdid, false);
                }
                break;

            case TvCommandDescription.CMDID_REBOOT_UPDATE:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().rebootUpdate());
                break;

            case TvCommandDescription.CMDID_KEY_CODE_TEST:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().sendKeyCode(param));
                break;

            case TvCommandDescription.CMDID_MCU_FACT_ENABLE:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().switchMCUFactoryMode(param));
                break;

            case TvCommandDescription.CMDID_EXEC_PROJ_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().writeProjectorCMD(param));
                break;


            case TvCommandDescription.CMDID_EXEC_PROJ_READ:
                mBinder.setResult_string(cmdid, SDKManager.getSysAccessManager().readProjectorCMD(param));
                break;


            case TvCommandDescription.CMDID_EXEC_I2C_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().writeI2CCMD(param));
                break;


            case TvCommandDescription.CMDID_EXEC_I2C_READ:
                mBinder.setResult_string(cmdid, SDKManager.getSysAccessManager().readI2CCMD(param));
                break;


            case TvCommandDescription.CMDID_KEYSTONE_ENABLE:
                mBinder.setResult_bool(cmdid, SDKManager.getKeystoneManager().setKeyStoneMode(this, param));
                break;


            case TvCommandDescription.CMDID_KEYSTONE_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getKeystoneManager().setKeyStoneDirect(this, param));
                break;


            case TvCommandDescription.CMDID_KEYSTONE_SETS:
                mBinder.setResult_bool(cmdid, SDKManager.getKeystoneManager().setKeyStoneSets());
                break;


            case TvCommandDescription.CMDID_READ_DLP_VERSION:
                mBinder.setResult_string(cmdid, SDKManager.getProjectorManager().readDLPVersion(this));
                break;


            case TvCommandDescription.CMDID_SET_AGING_LINE:
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().agingSetAgingLine(param));
                break;


            case TvCommandDescription.CMDID_GET_AGING_LINE:
                mBinder.setResult_string(cmdid, result = SDKManager.getMediaTestManager().agingGetAgingLine() + "");
                break;


            case TvCommandDescription.CMDID_SET_AGING_LINE_VOL:
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().agingSetAgingVolume(param));
                break;


            case TvCommandDescription.CMDID_GET_AGING_LINE_VOL:
                mBinder.setResult_string(cmdid, result = SDKManager.getMediaTestManager().agingGetAgingVolume() + "");
                break;


            case TvCommandDescription.CMDID_READ_GSENSOR_HORIZONTAL:
                mBinder.setResult_string(cmdid, SDKManager.getSysAccessManager().readGSensorHorizontalData());
                break;


            case TvCommandDescription.CMDID_HDCP_14_MD5_READ:
                mBinder.setResult_string(cmdid, SDKManager.getInfoAccessManager().readKeyMD5(param));
                break;


            case TvCommandDescription.CMDID_HDCP_22_MD5_READ:
                mBinder.setResult_string(cmdid, SDKManager.getInfoAccessManager().readKeyMD5(param));
                break;


            case TvCommandDescription.CMDID_RGB_LED_CURRENT_READ:
                mBinder.setResult_string(cmdid, SDKManager.getUtilManager().readRGBLEDCurrent());
                break;


            case TvCommandDescription.CMDID_LED_TEMP_READ:
                mBinder.setResult_string(cmdid, SDKManager.getUtilManager().readLEDTemperature(param));
                break;

            case TvCommandDescription.CMDID_READ_G_SENSOR_DATA:
                mBinder.setResult_string(cmdid, SDKManager.getSysAccessManager().readGSensorStandard());
                break;


            case TvCommandDescription.CMDID_SAVE_G_SENSOR_DATA:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().saveGSensorStandard());
                break;


            case TvCommandDescription.CMDID_START_COLLECT_G_SENSOR:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().startGSensorCollect());
                break;


            case TvCommandDescription.CMDID_GET_G_SENSOR_DATA//此处将取数据据改为直接判定gsensor功能是否正常
                    :
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().checkGSensorFunc());
                break;


            case TvCommandDescription.CMDID_SET_XPR_SHAKE_STATUS:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().enableXPRShake(param));
                break;


            case TvCommandDescription.CMDID_MOTOR_CALIBRATION_RESET:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().resetLEDStepMotor());
                break;


            case TvCommandDescription.CMDID_SET_FAN_SPEED:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setFanSpeed(param));
                break;
            /***test set XPR status */
            case TvCommandDescription.CMDID_SET_XPR_STATUS:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().enableXPRCheck(param));
                break;


            /***test write factory pid */
            case TvCommandDescription.CMDID_WRITE_LOOK_SELECT:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setLookSelect(param));
                break;


            /**test read factory pid */
            case TvCommandDescription.CMDID_READ_LOOK_SELECT:
                result = SDKManager.getInfoAccessManager().getLookSelect();
                mBinder.setResult_string(cmdid, result);
                break;


            case TvCommandDescription.CMDID_READ_ROM_TOTAL_SIZE:
                mBinder.setResult_string(cmdid, SDKManager.getUtilManager().readRomTotalSpace());
                break;


            case TvCommandDescription.CMDID_READ_ROM_AVAIL_SIZE:
                mBinder.setResult_string(cmdid, SDKManager.getUtilManager().readRomAvailSpace());
                break;


            case TvCommandDescription.CMDID_ADJUST_VOLUME:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().adjustVolume(param));
                break;


            case TvCommandDescription.CMDID_USID_READ:
                mBinder.setResult_string(cmdid, SDKManager.getInfoAccessManager().readUSID());
                break;


            case TvCommandDescription.CMDID_USID_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().writeUSID(param));
                break;


            case TvCommandDescription.CMDID_GET_KEY_ACTIVE_STATUS:
                mBinder.setResult_string(cmdid, SDKManager.getInfoAccessManager().getKeyActiveStatus(param));
                break;


            case TvCommandDescription.CMDID_FACTORY_PID_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setFactoryPID(param));
                break;


            case TvCommandDescription.CMDID_FACTORY_PID_READ:
                result = SDKManager.getInfoAccessManager().getFactoryPID();
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_LED_TEST:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setLedLightStat(param));
                break;


            case TvCommandDescription.CMDID_UDISK_2_CONTENT_TEST//detect tf card
                    :
                mBinder.setResult_bool(cmdid, SDKManager.getStorageManager().usbContent2SpeedTest(param, "2"));
                break;


            case TvCommandDescription.CMDID_UDISK_3_CONTENT_TEST:
                mBinder.setResult_bool(cmdid, SDKManager.getStorageManager().usbContent2SpeedTest(param, "3"));
                break;


            case TvCommandDescription.CMDID_UDISK_TEXT_VERIFY:
                mBinder.setResult_bool(cmdid, SDKManager.getStorageManager().usbTextFileCheck());
                break;


            case TvCommandDescription.CMDID_UDISK_FILE_CHECK:
                mBinder.setResult_bool(cmdid, SDKManager.getStorageManager().usbFileCheck(param));
                break;


            case TvCommandDescription.CMDID_USB_LIST_SIZE:
                mBinder.setResult_string(cmdid, USBUtils.getUSBListSize(this));
                break;


            case TvCommandDescription.CMDID_USB_LIST_GET//USB Device get
                    :
                if (param.matches("[0-9]{1,5},[0-9]{1,5}")) {
                    String vid = param.split(",")[0];
                    String pid = param.split(",")[1];
                    mBinder.setResult_string(cmdid, USBUtils.getUSBInfoByID(this, vid, pid));
                } else {
                    mBinder.setResult_string(cmdid, "param error");
                }
                break;

            case TvCommandDescription.CMDID_UDISK_CHECK_SPEED:
                result = SDKManager.getStorageManager().usbSpeedCheck(param);
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;
            case TvCommandDescription.CMDID_HDMI_1_CEC:
                value[0] = (byte) SDKManager.getMediaTestManager().hdmiTestCec(HDMI1_ID);
                mBinder.setResult_byte(cmdid, value);
                break;

            case TvCommandDescription.CMDID_HDMI_2_CEC:
                value[0] = (byte) SDKManager.getMediaTestManager().hdmiTestCec(HDMI2_ID);
                mBinder.setResult_byte(cmdid, value);
                break;

            case TvCommandDescription.CMDID_MHL_CEC_3:
                value[0] = (byte) SDKManager.getMediaTestManager().hdmiTestCec(HDMI3_ID);
                mBinder.setResult_byte(cmdid, value);
                break;

            case TvCommandDescription.CMDID_HDMI_1_CEC_NAME:
                result = SDKManager.getMediaTestManager().hdmiTestCecName(HDMI1_ID);
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_HDMI_2_CEC_NAME:
                result = SDKManager.getMediaTestManager().hdmiTestCecName(HDMI2_ID);
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;
            case TvCommandDescription.CMDID_MHL_CEC_3_NAME:
                result = result = SDKManager.getMediaTestManager().hdmiTestCecName(HDMI3_ID);
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;
            case TvCommandDescription.CMDID_LINE_OUT_ENABLE:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSwitchLineOut(false));
                break;


            case TvCommandDescription.CMDID_LINE_OUT_DISABLE:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSwitchLineOut(true));
                break;


            case TvCommandDescription.CMDID_ENABLE_SPEAKER:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSwitchSpeaker(false));
                break;


            case TvCommandDescription.CMDID_DISABLE_SPEAKER:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSwitchSpeaker(true));
                break;


            case TvCommandDescription.CMDID_ENABLE_SPDIF:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSwitchSpdif(0));
                break;


            case TvCommandDescription.CMDID_HDMI_ARC_ON:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSwitchArc(0));
                break;


            case TvCommandDescription.CMDID_DISABLE_SPDIF:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSwitchSpdif(1));
                break;


            case TvCommandDescription.CMDID_HDMI_ARC_OFF:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSwitchArc(1));
                break;


            case TvCommandDescription.CMDID_WIFI_QUICKCONNECT:
                mBinder.setResult_bool(cmdid, SDKManager.getNetManager().wifiConnectAp(param));
                break;

            case TvCommandDescription.CMDID_WIFI_IPADDR_GET:
                result = SDKManager.getNetManager().wifiGetIpAddr();
                if (TextUtils.equals(NetworkUtil.IP_ERROR, result)) {
                    Log.d(TAG, "read ip is 0.0.0.0 !,we try another way to get");
                    result = NetworkUtil.getCurrentIP(this);
                }
                mBinder.setResult_string(cmdid, result);
                break;
            case TvCommandDescription.CMDID_WIFI_PING:
                if (TextUtils.isEmpty(Param)) {
                    mBinder.setResult_bool(cmdid, false);
                } else {
                    mBinder.setResult_bool(cmdid, SDKManager.getNetManager().wifiPingAp(changeStringToIP(Param)));
                }
                break;

            case TvCommandDescription.CMDID_BT_SCAN//Bluetooth scan test
                    :
                byte[] btRssi = new byte[1];
                //btRssi[0] = (byte)mBTSignal.findBTDeviceScanned(param);
                btRssi[0] = (byte) SDKManager.getNetManager().btGetRssi(param);
                mBinder.setResult_byte(cmdid, btRssi);
                break;
            case TvCommandDescription.CMDID_BT_FIND_DEVICE:
                retFlag = false;
                List list = SDKManager.getNetManager().btGetList();
                retFlag = !(list == null || list.isEmpty());
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_ETH_PING:
                if (Param == null) {
                    mBinder.setResult_bool(cmdid, false);
                } else {
                    String ip = changeStringToIP(Param);
                    mBinder.setResult_bool(cmdid, SDKManager.getNetManager().ethernetPingAp(ip));
                }
                break;
            case TvCommandDescription.CMDID_SET_ETH_STATE:
                boolean ethStat = false;
                ethStat = Integer.parseInt(param) == 0;

                Log.i(TAG, "set Ethernet status: " + ethStat);
                retFlag = SDKManager.getNetManager().ethernetSetStatus(ethStat);
                mBinder.setResult_bool(cmdid, retFlag);
                break;

            case TvCommandDescription.CMDID_GET_ETH_STATE:
                retFlag = SDKManager.getNetManager().ethernetGetStatus();
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_SET_PRODUCT_FEATURE:
                retFlag = SDKManager.getLocalPropertyManager().writeProductFeatures(param);
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_CHECK_PRODUCT_FEATURE:
                retFlag = SDKManager.getLocalPropertyManager().checkProductFeatures(param);
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_IR_START:
                retFlag = true;
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_IR_STOP:
                if (getTvRunningWindCmd() != null)
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_BUSINESS, cmdid, param);
                break;

            case TvCommandDescription.CMDID_SERIAL_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setPcbaSerialNumber(param));
                break;

            case TvCommandDescription.CMDID_SERIAL_READ:
            case TvCommandDescription.CMDID_N_PCBA_SERIAL_READ:
                result = SDKManager.getInfoAccessManager().getPcbaSerialNumber();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;
            case TvCommandDescription.CMDID_BT_MAC_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setBluetoothMac(param));
                break;


            case TvCommandDescription.CMDID_BT_MAC_READ:
            case TvCommandDescription.CMDID_N_BT_MAC_READ:
                result = SDKManager.getInfoAccessManager().getBluetoothMac();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;
            case TvCommandDescription.CMDID_ETH_MAC_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setEthernetMac(param));
                break;


            case TvCommandDescription.CMDID_ETH_MAC_READ:
            case TvCommandDescription.CMDID_N_ETH_MAC_READ:
                result = SDKManager.getInfoAccessManager().getEthernetMac();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_WIFI_MAC_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setWifiMac(param));
                break;


            case TvCommandDescription.CMDID_WIFI_MAC_READ:
                result = SDKManager.getInfoAccessManager().getWifiMac();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;
            case TvCommandDescription.CMDID_HDCP_KEY_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setHdcp14Key(Param));
                break;

            case TvCommandDescription.CMDID_HDCP_KEY_READ:
                mBinder.setResult_byte(cmdid, SDKManager.getInfoAccessManager().getHdcp14Key());
                break;

            case TvCommandDescription.CMDID_MIRACAST_KEY_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setHdcp20Key(Param));
                break;

            case TvCommandDescription.CMDID_MIRACAST_KEY_READ:
                byte[] Mkey = SDKManager.getInfoAccessManager().getHdcp20Key();
                mBinder.setResult_byte(cmdid, Mkey);
                break;
            case TvCommandDescription.CMDID_MANUFACTURE_ID_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setPcbaManufactureNumber(param));
                break;

            case TvCommandDescription.CMDID_ATTESTATION_KEY_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setAttestationKey(Param));
                break;

            case TvCommandDescription.CMDID_ATTESTATION_KEY_READ:
                byte[] attestation = SDKManager.getInfoAccessManager().getAttestationKey();
                mBinder.setResult_byte(cmdid, attestation);
                break;

            case TvCommandDescription.CMDID_WIDEWINE_KEY_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setWidewineKey(Param));
                break;

            case TvCommandDescription.CMDID_WIDEWINE_KEY_READ:
                mBinder.setResult_byte(cmdid, SDKManager.getInfoAccessManager().getWidewineKey());
                break;

            case TvCommandDescription.CMDID_SET_KEY_ACTIVE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setKeyActive(param));
                break;


            case TvCommandDescription.CMDID_MANUFACTURE_ID_READ:
            case TvCommandDescription.CMDID_N_PCBA_MANU_READ:
                result = SDKManager.getInfoAccessManager().getPcbaManufactureNumber();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;
            //--------------------info MiTV4~XXXXX begin-----------------------
            case TvCommandDescription.CMDID_N_PCBA_SERIAL_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setPcbaSerialNumber(param));
                break;

            case TvCommandDescription.CMDID_N_PCBA_MANU_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setPcbaManufactureNumber(param));
                break;

            case TvCommandDescription.CMDID_N_ASSM_SERIAL_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setAssmSerialNumber(param));
                break;
            case TvCommandDescription.CMDID_N_ASSM_SERIAL_READ:
                result = SDKManager.getInfoAccessManager().getAssmSerialNumber();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_N_ASSM_MANU_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setAssmManufactureNumber(param));
                break;

            case TvCommandDescription.CMDID_N_ASSM_MANU_READ:
                result = SDKManager.getInfoAccessManager().getAssmManufactureNumber();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;
            case TvCommandDescription.CMDID_N_BT_MAC_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setBluetoothMac(param));
                break;
            case TvCommandDescription.CMDID_N_ETH_MAC_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setEthernetMac(param));
                break;

            //--------------------info MiTV4~XXXXX end-----------------------
            //--------------------media MiTV4~XXXXX start-----------------------

            case TvCommandDescription.CMDID_N_AMLOGIC_AGING_ON:
                //1. lock remote control
                SDKManager.getUtilManager().remoteControlSetLock("1");
                //2. start burn timer
                SDKManager.getMediaTestManager().agingSetTimerStatus(true);
                //3. set suto run command
                String autoruncmd = "1480" + "/";
                SDKManager.getLocalPropertyManager().setLocalPropString(SettingManager.FACTPROP_AUTORUN_COMMAND, autoruncmd);
                //4. open autorun switch
                SDKManager.getLocalPropertyManager().setLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS, true);
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().setTestPattern(0xFF, 0xFF, 0xFF));
                break;
            case TvCommandDescription.CMDID_N_AMLOGIC_AGING_OFF:
                //1. lock remote control
                SDKManager.getUtilManager().remoteControlSetLock("0");
                //2. start burn timer
                SDKManager.getMediaTestManager().agingSetTimerStatus(false);
                SDKManager.getMediaTestManager().agingInitTimerCount();
                SDKManager.getLocalPropertyManager().setLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS, false);
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().cancelTestPattern());
                break;

            case TvCommandDescription.CMDID_N_SET_SCREEN_RESOLUTION:
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().setScreenRes(param));
                break;


            //--------------------media MiTV4~XXXXX start-----------------------
            //--------------------util MiTV4 ~ XXXXX begin --------------
            case TvCommandDescription.CMDID_N_SET_GPIO_OUT_STAT:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setGpioOut(param));
                break;

            case TvCommandDescription.CMDID_N_GET_GPIO_IN_STAT:
                byte[] in = new byte[1];
                in[0] = (byte) SDKManager.getUtilManager().getGpioInStat(param);
                mBinder.setResult_byte(cmdid, in);
                break;

            case TvCommandDescription.CMDID_N_SET_VCOM_I2C:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setVcom(Param));
                break;


            case TvCommandDescription.CMDID_N_GET_VCOM_I2C:
                byte[] vcom = new byte[1];
                vcom[0] = SDKManager.getUtilManager().getVcom(Param);
                mBinder.setResult_byte(cmdid, vcom);
                break;

            case TvCommandDescription.CMDID_LIGHT_SENSOR:
                int senValue = SDKManager.getUtilManager().lightSensorGetValue();
                mBinder.setResult_string(cmdid, Integer.toString(senValue));
                break;
            case TvCommandDescription.CMDID_AGING_ON:
                //1. lock remote control
                SDKManager.getUtilManager().remoteControlSetLock("1");
                //2. start burn timer
                SDKManager.getMediaTestManager().agingSetTimerStatus(true);
                //3. set suto run command
                String autorunCommand = "1222" + "/";
                SDKManager.getLocalPropertyManager().setLocalPropString(SettingManager.FACTPROP_AUTORUN_COMMAND, autorunCommand);
                //4. open autorun switch
                SDKManager.getLocalPropertyManager().setLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS, true);
                retFlag = true;
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_AGING_OFF:
                //1. stop remote control
                SDKManager.getUtilManager().remoteControlSetLock("0");
                SDKManager.getMediaTestManager().agingSetTimerStatus(false);
                //7. init aging
                SDKManager.getMediaTestManager().agingInitTimerCount();
                SDKManager.getLocalPropertyManager().setLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS, false);
                if (getTvRunningWindCmd() != null)
                    TvSetControlMsg(getTvRunningWindCmd(), FactorySetting.COMMAND_TASK_BUSINESS, cmdid, BURNINGSTOP);
                retFlag = true;
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_SET_AUTORUN_STATUS:
                if (Integer.parseInt(param) == 0) {
                    Log.i(TAG, "set auto run flag as true");
                    retFlag = SDKManager.getLocalPropertyManager().setLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS, true);
                } else if (Integer.parseInt(param) == 1) {
                    Log.i(TAG, "set auto run flag as false");
                    //7. init aging
                    SDKManager.getMediaTestManager().agingInitTimerCount();
                    retFlag = SDKManager.getLocalPropertyManager().setLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS, false);
                }
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_GET_AUTORUN_STATUS:
                retFlag = SDKManager.getLocalPropertyManager().getLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS);
                mBinder.setResult_bool(cmdid, retFlag);
                break;
            case TvCommandDescription.CMDID_SET_AUTORUN_COMMAND:
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().setAutoRunCommand(param));
                break;
            case TvCommandDescription.CMDID_GET_AUTORUN_COMMAND:
                String autorunCmd = null;
                autorunCmd = SDKManager.getMediaTestManager().getAutoRunCommand();
                mBinder.setResult_string(cmdid, autorunCmd);
                break;

            case TvCommandDescription.CMDID_AGING_RESET_TIMER:
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().agingInitTimerCount());
                break;


            case TvCommandDescription.CMDID_AGING_GET_TIMER:
                mBinder.setResult_byte(cmdid, SDKManager.getMediaTestManager().agingGetTimerCount());
                break;


            case TvCommandDescription.CMDID_AGING_TIMER_START:
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().agingSetTimerStatus(true));
                break;


            case TvCommandDescription.CMDID_AGING_TIMER_STOP:

                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().agingSetTimerStatus(false));
                break;
            case TvCommandDescription.CMDID_PATTERN_SET:
                int r = 255;
                int g = 255;
                int b = 255;
                String[] vals = param.split(":");
                try {
                    r = Integer.parseInt(vals[0]);
                    g = Integer.parseInt(vals[1]);
                    b = Integer.parseInt(vals[2]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "red:" + vals[0] + ">>green:" + vals[1] + ">>blue: " + vals[2]);
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().setTestPattern(r, g, b));
                break;

            case TvCommandDescription.CMDID_PATTERN_DISABLE:
                mBinder.setResult_bool(cmdid, SDKManager.getMediaTestManager().cancelTestPattern());
                break;


            case TvCommandDescription.CMDID_FACTORY_RESET:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().systemReset());
                break;


            case TvCommandDescription.CMDID_SYSTEM_MODE_CHANGE:
                mBinder.setResult_bool(cmdid, false);
                break;


            case TvCommandDescription.CMDID_SYSTEM_MODE_CHANGE_NEW:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().systemSwitchModeNew());
                break;


            case TvCommandDescription.CMDID_SYSTEM_MODE_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().systemModeSet());
                break;


            case TvCommandDescription.CMDID_SYSTEM_MODE_GET:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().systemModeGet());
                break;


            case TvCommandDescription.CMDID_SYSTEM_REBOOT:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().systemReboot());
                break;


            case TvCommandDescription.CMDID_SYSTEM_REBOOT_RECOVERY:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().systemRebootRecovery());
                break;


            case TvCommandDescription.CMDID_SYSTEM_SHUTDOWN:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().systemShutdown());
                break;


            case TvCommandDescription.CMDID_PIC_MODE_RESET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picModeReset());
                break;


            case TvCommandDescription.CMDID_GET_PIC_MODE:
                value[0] = (byte) SDKManager.getPicModeManager().picModeGet();
                mBinder.setResult_byte(cmdid, value);
                break;

            case TvCommandDescription.CMDID_SET_PIC_MODE:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picModeSet(Integer.parseInt(param)));
                break;


            case TvCommandDescription.CMDID_GET_PIC_BRIGHTNESS:
                value[0] = (byte) SDKManager.getPicModeManager().picGetBrightness();
                mBinder.setResult_byte(cmdid, value);
                break;
            case TvCommandDescription.CMDID_SET_PIC_BRIGHTNESS:
                temp = Integer.parseInt(param);
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetBrightness(temp));
                break;
            case TvCommandDescription.CMDID_GET_PIC_CONTRAST:
                value[0] = (byte) SDKManager.getPicModeManager().picGetContrast();
                mBinder.setResult_byte(cmdid, value);
                break;
            case TvCommandDescription.CMDID_SET_PIC_CONTRAST:
                temp = Integer.parseInt(param);
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetContrast(temp));
                break;
            case TvCommandDescription.CMDID_GET_PIC_SHARPNESS:
                value[0] = (byte) SDKManager.getPicModeManager().picGetSharpness();
                mBinder.setResult_byte(cmdid, value);
                break;
            case TvCommandDescription.CMDID_SET_PIC_SHARPNESS:
                temp = Integer.parseInt(param);
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetSharpness(temp));
                break;
            case TvCommandDescription.CMDID_GET_PIC_HUE:
                value[0] = (byte) SDKManager.getPicModeManager().picGetHue();
                mBinder.setResult_byte(cmdid, value);
                break;
            case TvCommandDescription.CMDID_SET_PIC_HUE:
                temp = Integer.parseInt(param);
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetHue(temp));
                break;
            case TvCommandDescription.CMDID_GET_PIC_SATURATION:
                value[0] = (byte) SDKManager.getPicModeManager().picGetSatuation();
                mBinder.setResult_byte(cmdid, value);
                break;
            case TvCommandDescription.CMDID_SET_PIC_SATURATION:
                temp = Integer.parseInt(param);
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetSatuation(temp));
                break;
            case TvCommandDescription.CMDID_GET_PIC_COLORTEMP:
                value[0] = (byte) SDKManager.getPicModeManager().picGetColorTemp();
                mBinder.setResult_byte(cmdid, value);
                break;
            case TvCommandDescription.CMDID_SET_PIC_COLORTEMP:
                temp = Integer.parseInt(param);
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetColorTemp(temp));
                break;

            case TvCommandDescription.CMDID_POSTGAIN_RED_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetPostRedGain(Param));
                break;


            case TvCommandDescription.CMDID_POSTGAIN_GREEN_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetPostGreenGain(Param));
                break;


            case TvCommandDescription.CMDID_POSTGAIN_BLUE_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetPostBlueGain(Param));
                break;


            case TvCommandDescription.CMDID_POSTGAIN_RED_GET:
                colorTemp = Integer.parseInt(param);

                gain = SDKManager.getPicModeManager().picGetPostRedGain(colorTemp);
                mBinder.setResult_string(cmdid, changeInt2String(gain));
                break;
            case TvCommandDescription.CMDID_POSTGAIN_GREEN_GET:
                colorTemp = Integer.parseInt(param);

                gain = SDKManager.getPicModeManager().picGetPostGreenGain(colorTemp);
                mBinder.setResult_string(cmdid, changeInt2String(gain));
                break;
            case TvCommandDescription.CMDID_POSTGAIN_BLUE_GET:
                colorTemp = Integer.parseInt(param);

                gain = SDKManager.getPicModeManager().picGetPostBlueGain(colorTemp);
                mBinder.setResult_string(cmdid, changeInt2String(gain));
                break;
            case TvCommandDescription.CMDID_POSTOFFS_RED_GET:
                colorTemp = Integer.parseInt(param);

                offset = SDKManager.getPicModeManager().picGetPostRedOffset(colorTemp);
                mBinder.setResult_string(cmdid, changeInt2String(offset));
                break;
            case TvCommandDescription.CMDID_POSTOFFS_GREEN_GET:
                colorTemp = Integer.parseInt(param);

                offset = SDKManager.getPicModeManager().picGetPostGreenOffset(colorTemp);
                mBinder.setResult_string(cmdid, changeInt2String(offset));
                break;
            case TvCommandDescription.CMDID_POSTOFFS_BLUE_GET:
                colorTemp = Integer.parseInt(param);
                offset = SDKManager.getPicModeManager().picGetPostBlueOffset(colorTemp);
                mBinder.setResult_string(cmdid, changeInt2String(offset));
                break;

            case TvCommandDescription.CMDID_POSTOFFS_RED_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetPostRedOffset(Param));
                break;


            case TvCommandDescription.CMDID_POSTOFFS_GREEN_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetPostGreenOffset(Param));
                break;


            case TvCommandDescription.CMDID_POSTOFFS_BLUE_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetPostBlueOffset(Param));
                break;

            case TvCommandDescription.CMDID_SET_SOUND_OUTPUT_MODE:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioOutputMode(Integer.parseInt(param)));
                break;


            case TvCommandDescription.CMDID_SET_SPEAKER_SWITCH:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().speakerswitch(Integer.parseInt(param)));
                break;


            case TvCommandDescription.CMDID_RC_LOCK:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().remoteControlSetLock(param));
                break;


            case TvCommandDescription.CMDID_MODEL_NAME_GET:
                result = SDKManager.getInfoAccessManager().getModelName();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_GET_SYSTEM_FW_VER:
                result = SDKManager.getInfoAccessManager().getFirmwareVer();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_HDCP_KSV_GET:
                byte[] ksv = new byte[5];
                ksv = SDKManager.getInfoAccessManager().getHdcpKsv();
                mBinder.setResult_byte(cmdid, ksv);
                break;


            case TvCommandDescription.CMDID_WIFI_RSSI_GET:
                byte[] rssi = new byte[1];
                rssi[0] = (byte) SDKManager.getNetManager().wifiGetRssi();
                mBinder.setResult_byte(cmdid, rssi);
                break;


            case TvCommandDescription.CMDID_SET_WIFI_STATE:
                boolean wifiStat = false;
                if (Integer.parseInt(param) == 0) {
                    wifiStat = true;
                }
                Log.i(TAG, "set wifi status: " + wifiStat);
                retFlag = SDKManager.getNetManager().wifiSetStatus(wifiStat);
                mBinder.setResult_bool(cmdid, retFlag);
                break;

            case TvCommandDescription.CMDID_WIFI_SCAN_START:
                retFlag = SDKManager.getNetManager().wifiStartScan();
                mBinder.setResult_bool(cmdid, retFlag);
                break;


            case TvCommandDescription.CMDID_CHECK_WIFI_SCAN_RESULT:
                List<ScanResult> scanresults = null;
                if (param == null || param.length() == 0) {
                    retFlag = false;
                } else {
                    scanresults = SDKManager.getNetManager().wifiGetScanList();
                    for (ScanResult scanresult : scanresults) {
                        if (scanresult.SSID.equals(param)) {
                            retFlag = true;
                            break;
                        }

                    }
                }
                Log.i(TAG, "scan result: " + retFlag);
                mBinder.setResult_bool(cmdid, retFlag);
                break;


            case TvCommandDescription.CMDID_SET_BT_STATE:
                boolean btStat = false;
                if (Integer.parseInt(param) == 0) {
                    btStat = true;
                }
                Log.i(TAG, "set BT status: " + btStat);
                retFlag = SDKManager.getNetManager().btSetStatus(btStat);
                mBinder.setResult_bool(cmdid, retFlag);
                break;


            case TvCommandDescription.CMDID_SET_BT_STATE_BLE:
                boolean btBLEStat = false;
                if (Integer.parseInt(param) == 0) {
                    btBLEStat = true;
                }
                Log.i(TAG, "set BT status: " + btBLEStat);
                retFlag = SDKManager.getNetManager().btSetStatusBLE(btBLEStat);
                mBinder.setResult_bool(cmdid, retFlag);
                break;

            case TvCommandDescription.CMDID_GET_SOUND_VOLUME:
                byte[] vol = new byte[1];
                vol[0] = (byte) SDKManager.getAudioTestManager().audioGetSoundVolume();
                mBinder.setResult_byte(cmdid, vol);
                break;


            case TvCommandDescription.CMDID_SET_SOUND_VOLUME:
                mBinder.setResult_bool(cmdid, SDKManager.getAudioTestManager().audioSetSoundVolume(Integer.parseInt(param)));
                break;


            case TvCommandDescription.CMDID_PQ_DB_SAVE:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picTransPQDataToDB());
                break;


            case TvCommandDescription.CMDID_SYSTEM_PARTITION_CHECK:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().checkSystemPartition());
                break;


            case TvCommandDescription.CMDID_UDISK_UNMOUNT_3_0:
                mBinder.setResult_bool(cmdid, SDKManager.getStorageManager().usbHost30Unmount());
                break;


            case TvCommandDescription.CMDID_PQ_PANEL_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picPanelSelect(Param));
                break;


            case TvCommandDescription.CMDID_GET_SYSTEM_PROP//Get TV touch pad status
                    :
                mBinder.setResult_string(cmdid, SDKManager.getAndroidOSManagerInterf().getSystemProperty(param, "NotFound"));
                break;


            case TvCommandDescription.CMDID_SET_PRODUCTREGION:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setProductRegion(param));
                break;


            case TvCommandDescription.CMDID_GET_PRODUCTREGION:
                result = SDKManager.getUtilManager().getProductRegion();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_BTRC_MAC_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setBtRcMac(param));
                break;


            case TvCommandDescription.CMDID_BTRC_MAC_GET:
                result = SDKManager.getUtilManager().getBtRcMac();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;


            case TvCommandDescription.CMDID_WIFI_DISCONNECT:
                mBinder.setResult_bool(cmdid, SDKManager.getNetManager().wifiDisconnect());
                break;


            case TvCommandDescription.CMDID_WOOFER_PLUGIN:
                boolean wooferStat = false;
                wooferStat = SDKManager.getUtilManager().getSubWooferStat();
                mBinder.setResult_bool(cmdid, wooferStat);
                break;


            case TvCommandDescription.CMDID_CHECK_HDCPKEY14_VALID:
                boolean hdcp14 = false;
                hdcp14 = SDKManager.getMediaTestManager().checkHdcp14Valid();
                mBinder.setResult_bool(cmdid, hdcp14);
                break;


            case TvCommandDescription.CMDID_CHECK_HDCPKEY22_VALID:
                boolean hdcp22 = false;
                hdcp22 = SDKManager.getMediaTestManager().checkHdcp22Valid();
                mBinder.setResult_bool(cmdid, hdcp22);
                break;


            case TvCommandDescription.CMDID_N_DOLBY_DTS_CHECK:
                result = SDKManager.getMediaTestManager().checkDolbyDts();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_N_GET_PANEL_ID_TAG:
                result = SDKManager.getUtilManager().checkPanelIdTag();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;

            case TvCommandDescription.CMDID_N_CLOSE_DSP_DAP:
                boolean closedap = false;
                closedap = SDKManager.getAudioTestManager().closeDap();
                mBinder.setResult_bool(cmdid, closedap);
                break;

            case TvCommandDescription.CMDID_N_BYPASS_PQ:
                boolean bypasspq = false;
                bypasspq = SDKManager.getPicModeManager().byPassPQ(param);
                mBinder.setResult_bool(cmdid, bypasspq);
                break;


            case TvCommandDescription.CMDID_PRODUCT_UIID_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getInfoAccessManager().setPID(param));
                break;


            case TvCommandDescription.CMDID_PRODUCT_UIID_READ:
                result = SDKManager.getInfoAccessManager().getPID();
                if (null == result) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;


            case TvCommandDescription.CMDID_DLP_SN_READ:
                result = SDKManager.getUtilManager().getDlpSn();
                if (null == result || result.length() == 0) {
                    result = READ_ERR;
                }
                mBinder.setResult_string(cmdid, result);
                break;


            case TvCommandDescription.CMDID_SET_BODY_DETECT:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setBodyDetectStatus(param));
                break;


            case TvCommandDescription.CMDID_BODY_DETECT_STATUS:
                int bodyDetect = SDKManager.getUtilManager().getBodyDetectStatus();
                mBinder.setResult_string(cmdid, bodyDetect + "");
                break;

            case TvCommandDescription.CMDID_DLP_SCREEN_CHECK:
                int mode = -1;
                try {
                    mode = Integer.parseInt(param);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().screenCheck(mode));
                break;

            case TvCommandDescription.CMDID_SET_DLP_SCREEN_CHECK:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().enableScreenCheck(param));
                break;


            case TvCommandDescription.CMDID_SET_MOTOR_SCALE:
                int scale = 0;
                try {
                    scale = Integer.parseInt(param);
                } catch (Exception e) {
                }

                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setMotorScale(scale));
                break;

            case TvCommandDescription.CMDID_BODY_DETECT_COUNT:
                boolean isLeft = true;
                try {
                    int iP = Integer.parseInt(param);
                    isLeft = iP == 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().getBodyDetectCount(isLeft) > 0);

                break;
            case TvCommandDescription.CMDID_STOP_BODY_DETECT:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().stopBodyDetectTest());
                break;


            case TvCommandDescription.CMDID_START_BODY_DETECT:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().startBodyDetectTest());
                break;


            case TvCommandDescription.CMDID_PQ_ENABLE:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picEnablePQ());
                break;
            case TvCommandDescription.CMDID_PQ_DISABLE:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picDisablePQ());
                break;

            case TvCommandDescription.CMDID_DLP_INFO_SYNC:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().syncDlpInfo());
                break;


            case TvCommandDescription.CMDID_DLP_INFO_SAVE:
                mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().saveDlpInfo());
                break;


            case TvCommandDescription.CMDID_WHEEL_DELAY_READ:
                mBinder.setResult_string(cmdid, SDKManager.getSysAccessManager().getWheelDelay() + "");
                break;

            case TvCommandDescription.CMDID_WHEEL_DELAY_WRITE:
                int delay = -1;
                try {
                    delay = Integer.parseInt(param);
                } catch (Exception e) {
                }

                if (delay != -1) {
                    mBinder.setResult_bool(cmdid, SDKManager.getSysAccessManager().setWheelDelay(delay));

                } else {
                    mBinder.setResult_bool(cmdid, false);
                }
                break;
            case TvCommandDescription.CMDID_WIFI_CCODE_READ:
                mBinder.setResult_string(cmdid, SDKManager.getUtilManager().getWifiCountryCode());


            case TvCommandDescription.CMDID_WIFI_SPEED_START:
                mBinder.setResult_bool(cmdid, SDKManager.getNetManager().wifiThroughputStart());
                break;

            case TvCommandDescription.CMDID_WIFI_SPEED_PARA:
                mBinder.setResult_bool(cmdid, SDKManager.getNetManager().wifiThroughputWithParameter(param));
                break;

            case TvCommandDescription.CMDID_WIFI_SPEED_STOP:
                mBinder.setResult_bool(cmdid, SDKManager.getNetManager().wifiThroughputStop());
                break;

            case TvCommandDescription.CMDID_BACKLIGHT_SET:
                mBinder.setResult_bool(cmdid, SDKManager.getPicModeManager().picSetBacklight(Integer.parseInt(param)));
                break;

            case TvCommandDescription.CMDID_BACKLIGHT_GET:
                mBinder.setResult_string(cmdid, SDKManager.getPicModeManager().picGetBacklight() + "");
                break;

            case TvCommandDescription.CMDID_WIFI_CCODE_WRITE:
                mBinder.setResult_bool(cmdid, SDKManager.getUtilManager().setWifiCountryCode(param));
                break;
            default:
                Log.e(TAG, "handleCommand command ID not support yet!");
                mBinder.setResult_bool(cmdid, false);
                break;
        }
        if (mTvCd.getCmdTypeByID(cmdid).equals(TvCommandDescription.CMD_TYPE_ACTIVITY_ON)) {
            Log.i(TAG, "do activity operations");
            TvhandleCommandForActivity(c);
        }

    }


    private void prepareFactorytest() {
        prepareFactorytestTV();
        //FM JIRA PROJECTOR-412
        mHandler.postDelayed(mDisableBodyDetect, 10);
        //connAppo();
    }

    private void connAppo() {
        NetworkUtil.scanWifiInfo(this);
        NetworkUtil.connectWifi(this, "appo-5G", "fm@567765", "WPA");
    }

    //TV
    private void prepareFactorytestTV() {
        //1.1 update Boot Times
        Log.i(TAG, "CMDSERVICE: update boot times");
        SDKManager.getLocalPropertyManager().initLocalProperty();
        SDKManager.getUtilManager().systemUpdateBootTimes();
        //设置按键模式(135、185)
        SDKManager.getUtilManager().sleepSystem();
        //2. Led flash
        if (SDKManager.getUtilManager().systemGetBootTimes() == 1) {
            Log.i(TAG, "first  boot up");
            SystemClock.sleep(11000);
            //4. prop init
            Log.i(TAG, "CMDSERVICE: init local prop");
            SDKManager.getLocalPropertyManager().setLocalPropInt(SettingManager.FACTPROP_AGINGTIMERCOUNT, 0);
            SDKManager.getLocalPropertyManager().setLocalPropInt(SettingManager.FACTPROP_BACKLIGHT, 0);
            SDKManager.getLocalPropertyManager().setLocalPropInt(SettingManager.FACTPROP_BRIGHTNESS, 0);
            SDKManager.getLocalPropertyManager().setLocalPropInt(SettingManager.FACTPROP_CONTRAST, 0);
            SDKManager.getLocalPropertyManager().setLocalPropInt(SettingManager.FACTPROP_3D, 0);
            SDKManager.getLocalPropertyManager().setLocalPropInt(SettingManager.FACTPROP_BURNINGSOUR, 0);
            SDKManager.getLocalPropertyManager().setLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS, false);
            String defaultAutorun = "1113" + "/";
            SDKManager.getLocalPropertyManager().setLocalPropString(SettingManager.FACTPROP_AUTORUN_COMMAND, defaultAutorun);

            if (!SDKManager.getUtilManager().setApplicationRid()) {
                Log.e(TAG, "can't create rid");
            }
        }
        //3. disable BT
        Log.i(TAG, "CMDSERVICE: set bt disable");
        SDKManager.getNetManager().btSetStatus(false);
        //6. if huaxing, start panel sync init
        Log.i(TAG, "CMDSERVICE: init 3D sync");
        //result = SDKManager.getMediaTestManager().hdmiCheck3DSyncInit();
        //5. autoruncmd
        Log.i(TAG, "CMDSERVICE: check and set aging");
        autoRunCommand();
        //8. set system boot up directly next time
        SDKManager.getUtilManager().bootupSystemDirect();
        //9. set SoundVolume
        SDKManager.getAudioTestManager().audioSetSoundVolume(SDKManager.getAudioTestManager().audioGetMaxSoundVolume());
        //10. set backlight as normal
        SDKManager.getPicModeManager().picSetBacklight(2);
        //11. close DTS/DOLBY
        //SDKManager.getAudioTestManager().closeDTS_DOLBY();
        //12. if soundbar, set resolution as 1080p60 for capture card
        SDKManager.getMediaTestManager().setScreenRes("1080P60");
        //13. switch to speaker (param:disable)
        SDKManager.getAudioTestManager().audioSwitchSpeaker(false);
    }

    private void autoRunCommand() {
        //1. Burning Mode should auto run, if it doesn't be close in last cycle
        String cmd = null;
        String id = null;
        String para = null;
        SystemClock.sleep(2000);
        cmd = SDKManager.getLocalPropertyManager().getLocalPropString(SettingManager.FACTPROP_AUTORUN_COMMAND);
        if (cmd == null) {
            return;
        }
        String[] cmdInfo = cmd.split("/");
        if (cmdInfo.length > 0) {
            if (cmdInfo.length == 1 && !TextUtils.isEmpty(cmdInfo[0])) {
                Log.i(TAG, "auto Run cmd id=" + cmdInfo[0] + ")");
                id = cmdInfo[0];
            } else if (cmdInfo.length == 2 && !TextUtils.isEmpty(cmdInfo[0])) {
                Log.i(TAG, "auto Run cmd id=" + cmdInfo[0] + ", param=" + cmdInfo[1]);
                id = cmdInfo[0];
                para = cmdInfo[1];
            } else {
                Log.i(TAG, "auto Run cmd is illegal, " + cmd);
                return;
            }
        }
        if (SDKManager.getLocalPropertyManager().getLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS)) {
            Log.i(TAG, "CMDSERVICE: do auto run " + cmd);
            handleCommand(id, para);
        }
    }

    public String changeStringToAscII(String src) {
        int dest_byte;
        StringBuilder dest = new StringBuilder();
        String[] srcs = null;
        srcs = src.split(",");
        for (int i = 0; i < srcs.length; i++) {
            Log.i(TAG, "srcs[" + i + "] = [" + srcs[i] + "]");
            if (srcs[i] != null && srcs[i].matches("[0-9]+")) {
                dest_byte = Integer.parseInt(srcs[i]);
                dest.append((char) dest_byte);
            } else {
                Log.i(TAG, "paras is abnormal <" + srcs[i] + ">");
            }
        }
        return dest.toString();
    }

    public String changeStringToIP(String src) {
        int dest_byte;
        String dest = "";
        Log.i(TAG, "changeStringToIP : src [" + src + "]");
        String[] srcs = src.split(",");
        for (int i = 0; i < srcs.length - 1; i++) {
            dest_byte = Integer.parseInt(srcs[i]);
            Log.i(TAG, "changeStringToIP : dest_byte [" + dest_byte + "]");
            dest += dest_byte + ".";
        }
        dest += Integer.parseInt(srcs[srcs.length - 1]);
        Log.i(TAG, "changeStringToIP : dest [" + dest + "]");
        return dest;
    }

    public String changeInt2String(int src) {
        return src + "";
    }
}
