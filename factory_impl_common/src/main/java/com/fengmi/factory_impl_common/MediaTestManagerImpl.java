package com.fengmi.factory_impl_common;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.tv.TvInputManager;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_globle.SettingManager;
import com.fengmi.factory_test_interf.sdk_interf.MediaTestManagerInterf;
import com.fengmi.factory_test_interf.sdk_utils.AgingUtil;
import com.fengmi.factory_test_interf.sdk_utils.SPUtil;
import com.fengmi.factory_test_interf.sdk_utils.ShellUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class MediaTestManagerImpl implements MediaTestManagerInterf {
    private static int defaultPattern;
    private Context mContext = null;
    private AgeThread mAgeThread;
    private boolean AgeWorking = false;
    private int AGING_USING_UNIT = 3;
    private int AGING_DEFAULT_UNIT = 3;
    private int MAX_AGING_PERIOD = 0xFFFFFF;


    MediaTestManagerImpl(Context context) {
        mContext = context;
    }

    public static void sleepCurrentThread(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean transHdcp14TxKey() {
        return true;
    }

    public int hdmiTestCec(int port) {
        return SDKManager.getAndroidOSManagerInterf().getHDMICECCount();
    }

    public String hdmiTestCecName(int port) {
        return SDKManager.getAndroidOSManagerInterf().getHDMICECName(port);
    }

    @Override
    public boolean hdmiCheck3DSync() {
        return false;
    }

    public String hdmiCheckEdid() {
        return "";
    }

    @Override
    public boolean hdmiSet3D(int mode) {
        return false;
    }

    @Override
    public int hdmiGet3D() {
        return 0;
    }

    public boolean switchInputSource(String sour) {
        return true;
    }

    @Override
    public boolean atvLoadChannel(String city) {
        return false;
    }

    @Override
    public boolean dtvLoadChannel(String city) {
        return false;
    }

    public boolean selectLocalVideoPos(int id) {
        return true;
    }

    public boolean agingInitTimerCount() {
        boolean ret = false;
        ret = agingTimerInit();
        return ret;
    }

    public boolean agingSetTimerStatus(boolean stat) {
        boolean ret = false;
        if (stat) {
            ret = agingTimerStart();
        } else {
            ret = agingTimerStop();
        }
        return ret;

    }

    @Override
    public boolean agingSetTimerStep(int val) {
        return false;
    }

    @Override
    public boolean agingHowLong(int val) {
        return false;
    }

    public byte[] agingGetTimerCount() {
        byte[] ret = null;
        ret = int2byte(getAgingCount());
        return ret;
    }

    public boolean agingClearTimer() {
        boolean ret = false;
        ret = clearAgingCount();
        return ret;
    }

    public boolean picSetFullHD() {
        return true;
    }

    public boolean setTestPattern(int r, int g, int b) {
        return setPattern(r, g, b);
    }

    public boolean cancelTestPattern() {
        return disablePattern();
    }

    public boolean setAspectMode(int val) {
        return true;
    }

    public boolean surfaceInit(SurfaceHolder holder, MediaPlayer.OnErrorListener errlistener,
                               MediaPlayer.OnPreparedListener prepListener) {
        boolean ret = false;
        Log.i(TAG, "surfaceCreated is coming");
        //ret = surfaceCreate(holder, errlistener, prepListener);
        Log.i(TAG, "surfaceCreated come: " + ret);
        return ret;
    }

    public boolean surfaceChange(int[] pos, int width, int height) {
        boolean ret = false;
        //ret = surfaceShapeChange(pos, width, height);
        return ret;
    }

    public void surfacePlayerRelease() {
        //playerRelease();
    }

    public void surfacePlayerStart() {
        //playerStart();
    }

    public boolean tvcontextInit() {
        boolean ret = false;
        //ret = initTvContext();
        return ret;

    }

    public boolean burningPrepare() {
        boolean ret = false;
        //ret = burningInit();
        return ret;
    }

    public boolean burningStop() {
        boolean ret = false;
        //burningExit();
        return ret;
    }

    public int transSourNameToId(String SourName) {
        int ret = -1;
        //ret = getSourceIdByName(SourName);
        return ret;
    }

    public boolean switchCurrSour(int sourId) {
        boolean ret = false;
        //ret = switchSour(sourId);
        return ret;
    }

    public int getCurrSour() {
        int ret = -1;
        //ret = currSour();
        return ret;
    }

    public int[] getAllInputSour() {
        int[] ret = null;
        //ret = queryAllSour();
        return ret;
    }

    public boolean switchAtvChannel(int channel) {
        boolean ret = false;
        //ret = atvTuneChan(channel);
        return ret;
    }

    public boolean switchDtvChannel(int channel) {
        boolean ret = false;
        //ret = dtvTuneChan(channel);
        return ret;
    }

    public int getAtvCurrChan() {
        int ret = -1;
        //ret = atvCurrChan();
        return ret;
    }

    public int getDtvCurrChan() {
        int ret = -1;
        //ret = dtvCurrChan();
        return ret;
    }

    public int getAtvChanCount() {
        int ret = -1;
        //ret = atvChanCount();
        return ret;
    }

    public int getDtvChanCount() {
        int ret = -1;
        //ret = dtvChanCount();
        return ret;
    }

    public String getSourName(int sourId) {
        String ret = null;
        //ret = sourceName(sourId);
        return ret;
    }

    public void initSourceIdTable() {
        //InitInputSourceId();
    }

    public boolean resetHPD() {
        return true;
    }

    public boolean checkHdcp14Valid() {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().Hdcp14Valid();
        return ret;
    }

    public boolean checkHdcp22Valid() {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().Hdcp22Valid();
        return ret;
    }

    public boolean setAutoRunCommand(String CmdAPara) {
        boolean ret = false;
        ret = setAutoRunCmd(CmdAPara);
        return ret;
    }

    public String getAutoRunCommand() {
        String ret = null;
        ret = getAutoRunCmd();
        return ret;
    }

    public boolean switchDisplay(int para) {
        return true;
    }

    public boolean wooferEnable() {
        return true;
    }

    public boolean initTvview() {
        return true;
    }

    public boolean registerTvview(View mView) {
        return true;
    }

    public boolean unregisterTvview(View mView) {

        return true;
    }

    public String checkDolbyDts() {
        return "";
    }

    public boolean setScreenRes(String res) {
        boolean ret = false;
        //ret = setScreenResolution(res);
        return ret;
    }

    public boolean agingSetAgingLine(String agingLine) {
        return setAgingLine(agingLine);
    }

    public int agingGetAgingLine() {
        return AgingUtil.getAgingLine(mContext);
    }

//----------------Aging-------------------------------

    public boolean agingSetAgingVolume(String vol) {
        return setAgingVolume(vol);
    }

    public int agingGetAgingVolume() {
        return AgingUtil.getAgingVolume(mContext);
    }

    /*===========================================local functions=====================*/
    private boolean setAgingLine(String agingLine) {
        if (!TextUtils.isDigitsOnly(agingLine)) {
            return false;
        }
        int val = Integer.parseInt(agingLine);
        return AgingUtil.setAgingLine(mContext, val);
    }

    private boolean setAgingVolume(String vol) {
        if (!TextUtils.isDigitsOnly(vol)) {
            return false;
        }
        int val = Integer.parseInt(vol);
        return AgingUtil.setAgingVolume(mContext, val);
    }

    /**
     * 获取port对应的HDMI name
     */
    private String getHdmiName(int port) {
        String name = null;
        switch (port) {
            case HDMI1_PORT:
                name = "/HW5";
                break;
            case HDMI2_PORT:
                name = "/HW6";
                break;
            case HDMI3_PORT:
                name = "/HW7";
                break;
        }
        return name;
    }


    //----------------AutoRun --------------------------------
    private boolean setAutoRunCmd(String cmd) {
        boolean ret = false;
        ret = SDKManager.getLocalPropertyManager().setLocalPropString(SettingManager.FACTPROP_AUTORUN_COMMAND, cmd);
        return ret;
    }

    private String getAutoRunCmd() {
        String ret = null;
        ret = SDKManager.getLocalPropertyManager().getLocalPropString(SettingManager.FACTPROP_AUTORUN_COMMAND);
        if (ret == null || ret.length() == 0) {
            return "null";
        }
        return ret;
    }

    //the input's unit is second
    private boolean setAgingDeadline(int val) {
        Log.i(TAG, "set the deadline of aging time");
        boolean ret = false;
        if (val < 0) {
            Log.i(TAG, "the aging setting error");
        } else if (AGING_USING_UNIT < 1) {
            Log.i(TAG, "the aging timer unit has error");
        } else {
            MAX_AGING_PERIOD = val / AGING_USING_UNIT;
            ret = true;
        }
        return ret;
    }

    private boolean agingSetUnit(int val) {
        Log.i(TAG, "set the aging count interval, it should larger than 0, and less than 10");
        boolean ret = false;
        if (val < 1 || val > 10) {
            AGING_USING_UNIT = AGING_DEFAULT_UNIT;
        } else {
            AGING_USING_UNIT = val;
            ret = true;
        }
        return ret;
    }

    private int getAgingCount() {
        int count = -1;
        Log.i(TAG, "aging count key: " + SettingManager.FACTPROP_AGINGTIMERCOUNT);
        count = SDKManager.getLocalPropertyManager().getLocalPropInt(SettingManager.FACTPROP_AGINGTIMERCOUNT);
        return count;
    }

    private boolean clearAgingCount() {
        boolean ret = false;
        if (SDKManager.getLocalPropertyManager().setLocalPropInt(SettingManager.FACTPROP_AGINGTIMERCOUNT, 0)) {
            ret = true;
        }
        return ret;
    }

    private boolean agingTimerStart() {
        boolean ret = false;
        if (AgeWorking) {
            Log.e(TAG, "now aging is working, dont reopen it");
            return ret;
        }
        agingSetUnit(AGING_DEFAULT_UNIT);
        setAgingDeadline(0xFFFFFF);
        AgeWorking = true;
        Log.i(TAG, "start aging");
        if (mAgeThread == null) {
            Log.i(TAG, "AgThread begin work");
            mAgeThread = new AgeThread();
            mAgeThread.start();
            ret = true;
        }
        return ret;
    }

    private boolean agingTimerStop() {
        boolean ret = false;
        if (!AgeWorking || mAgeThread == null) {
            Log.e(TAG, "now aging isn't work , dont close it repeatly");
            return ret;
        }
        AgeWorking = false;
        try {
            mAgeThread.join();
            mAgeThread = null;
            ret = true;
            Log.e(TAG, "stop Timer Aging succeed");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG, "stop Timer Aging failed");
        }
        return ret;
    }

    private boolean agingTimerInit() {
        boolean ret = false;
        AgeWorking = false;
        AGING_USING_UNIT = AGING_DEFAULT_UNIT;
        if (SDKManager.getLocalPropertyManager().setLocalPropInt(SettingManager.FACTPROP_AGINGTIMERCOUNT, 0)) {
            ret = true;
        }
        return ret;
    }

    private byte[] int2byte(int sour) {
        byte[] dest = new byte[4];
        for (int i = 0; i < 4; i++) {
            dest[i] = (byte) (sour >> (24 - i * 8));
        }
        return dest;
    }

    private boolean setPattern(int r, int g, int b) {
        boolean ret = false;
        //get default rgb pattern
        defaultPattern = SDKManager.getAmlogicManagerInterf().FactoryGetRGBScreen();
        Log.i(TAG, "default pattern as <" + defaultPattern + ">");
        //close video
        SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/class/video/disable_video", "1");
        //close osd
        SDKManager.getAmlogicManagerInterf().writeSysFs(OSD_DISPLAY_DEBUG, String.valueOf(OSD_FLAG_CLOSE));
        SDKManager.getAmlogicManagerInterf().writeSysFs(OSD_SWITCH, String.valueOf(OSD_FLAG_CLOSE));
        //generate rgb str
        String red = rgbComplete(Integer.toHexString(r));
        String green = rgbComplete(Integer.toHexString(g));
        String blue = rgbComplete(Integer.toHexString(b));
        //write rgb color
        SDKManager.getAmlogicManagerInterf().writeSysFs(RGB_SCREEN, "0x" + red + green + blue);
        ret = true;
        Log.i(TAG, "set pattern as <" + r + " - " + g + " - " + b + ">");
        return ret;
    }

    private String rgbComplete(String hexVal) {
        if (hexVal.length() == 1) {
            return "0" + hexVal;
        }
        return hexVal;
    }

    private boolean disablePattern() {
        boolean ret = false;
        int r, g, b;
        r = (defaultPattern | 0xFF0000) >> 16;
        g = (defaultPattern | 0x00FF00) >> 8;
        b = (defaultPattern | 0x0000FF);
        echoEntry(OSD_SWITCH, String.valueOf(OSD_FLAG_OPEN));
        echoEntry(OSD_DISPLAY_DEBUG, String.valueOf(OSD_FLAG_OPEN));
        String red = rgbComplete(Integer.toHexString(r));
        String green = rgbComplete(Integer.toHexString(g));
        String blue = rgbComplete(Integer.toHexString(b));
        SDKManager.getAmlogicManagerInterf().writeSysFs(RGB_SCREEN, "0x" + red + green + blue);
        SDKManager.getAmlogicManagerInterf().writeSysFs(DIS_VIDEO, "0");
        Log.i(TAG, "cancel pattern");
        ret = true;
        return ret;
    }

    /*--------------------------------PQ Function stop--------------------*/
    private boolean echoEntry(final String entry, String rts) {
        boolean ret = false;
        OutputStreamWriter osw = null;
        Log.i(TAG, "echo [" + rts + "] to path [" + entry + "]");
        try {
            osw = new OutputStreamWriter(new FileOutputStream(entry));
            osw.write(rts, 0, rts.length());
            osw.flush();
            ret = true;
        } catch (Exception e) {
            Log.e(TAG, "final write data to file " + entry + " execetpion=" + e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                }
            }
        }
        return ret;
    }

    private void tempCheck(int count) {
        int doInt = count % 5;
        if (doInt == 4) {
            String onValue = (String) SPUtil.getParam(mContext, TEMP_CHECK_ON, "OFF");
            Log.d(TAG, "--------------we are going to temp check, first check on value is " + onValue);
            if (TextUtils.isEmpty(onValue)) {
                return;
            }
            if (onValue.contains("ON")) {
                Log.d(TAG, "-----------------------TEMP- " + doInt + " -CHECK---------------------------");
                String red = "";
                String green = "";
                String blue = "";
                try {
                    red = ShellUtil.execCommand(SDKManager.getProjectorManager().getTemperatureCommandList().get(2)).trim();
                    green = ShellUtil.execCommand(SDKManager.getProjectorManager().getTemperatureCommandList().get(3)).trim();
                    blue = ShellUtil.execCommand(SDKManager.getProjectorManager().getTemperatureCommandList().get(4)).trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "-----------------------TEMP CHECK---------------------------");
                Log.d(TAG, "-------------------------RED--" + red + "-------------------------");
                Log.d(TAG, "-------------------------GREEN--" + green + "-------------------------");
                Log.d(TAG, "-------------------------BLUE--" + blue + "-------------------------");
                Log.d(TAG, "-----------------------TEMP CHECK---------------------------");

                if (TextUtils.isDigitsOnly(red) && TextUtils.isDigitsOnly(green) && TextUtils.isDigitsOnly(blue)) {
                    int redTemp = Integer.parseInt(red);
                    int greenTemp = Integer.parseInt(green);
                    int blueTemp = Integer.parseInt(blue);
                    int threshold_01 = 2;
                    int threshold_02 = 12;
                    String thresholdStr01 = (String) SPUtil.getParam(mContext, TEMP_CHECK_THRESHOLD_01, "2");
                    String thresholdStr02 = (String) SPUtil.getParam(mContext, TEMP_CHECK_THRESHOLD_02, "12");
                    if (!TextUtils.isEmpty(thresholdStr02) && TextUtils.isDigitsOnly(thresholdStr02)) {
                        threshold_02 = Integer.parseInt(thresholdStr02);
                    }
                    if (!TextUtils.isEmpty(thresholdStr01) && TextUtils.isDigitsOnly(thresholdStr01)) {
                        threshold_01 = Integer.parseInt(thresholdStr01);
                    }
                    Log.d(TAG, "-----------------------we get temp threshold 01 is " + threshold_01 + "---------------------------");
                    Log.d(TAG, "-----------------------we get temp threshold 02 is " + threshold_02 + "---------------------------");
                    boolean rule01 = (redTemp > blueTemp) && (redTemp - blueTemp) > threshold_01;
                    boolean rule02 = (blueTemp - redTemp) > threshold_02;
                    if (rule01 || rule02) {
                        Log.d(TAG, "-----------------------WARNING---------------------------");
                        Log.d(TAG, "-------------rule 01 ---" + rule01 + "--- RED > BLUE + 2 --------------");
                        Log.d(TAG, "-------------rule 02 ---" + rule02 + "--- BLUE - RED > 10 --------------");

                        Log.d(TAG, "-----------------------save error temp data to SP---------------------------");
                        String errorData = "Red-Blue>" + threshold_01 + ":" + rule01 + "\nBlue-Red>" + threshold_02 + ":" + rule02 + "\ntempData=(R:" + redTemp + ",G:" + greenTemp + ",B:" + blueTemp + ")";
                        SPUtil.setParam(mContext, TEMP_CHECK_DATA, errorData);

                        SDKManager.getLocalPropertyManager().setLocalPropBool(SettingManager.FACTPROP_AUTORUN_STATUS, false);
                        Log.d(TAG, "-----------------------set auto run is false---------------------------");
                        SPUtil.setParam(mContext, TEMP_CHECK_FAILED, true);
                        Log.d(TAG, "-----------------------set temp check fail status is true ---------------------------");
                        SPUtil.setParam(mContext, TEMP_CHECK_ON, "OFF");
                        Log.d(TAG, "-----------------------set temp check on status is OFF ---------------------------");

                        Log.d(TAG, "-----------------------wo are gonging to power off---------------------------");
                        SystemClock.sleep(1500);
                        SDKManager.getUtilManager().systemShutdown();

                    } else {
                        Log.d(TAG, "-----------------------TEMP CHECK IS PASS---------------------------");
                        SPUtil.setParam(mContext, TEMP_CHECK_FAILED, false);
                        Log.d(TAG, "-----------------------set temp check fail status is false ---------------------------");
                    }
                }
            }
        }
    }

    private class AgeThread extends Thread {
        @Override
        public void run() {
            Log.i(TAG, "AgThread begin to run");
            int updateTime = AGING_USING_UNIT * 1000;
            int slice = 1500;
            int UPDATE_COUNT = updateTime / slice;
            int cur = 0;
            int record = 0;
            while (AgeWorking) {
                sleepCurrentThread(slice);
                cur++;
                if (cur >= UPDATE_COUNT) {
                    if (!SDKManager.getLocalPropertyManager().increaseLocalPropInt(SettingManager.FACTPROP_AGINGTIMERCOUNT, 1)) {
                        Log.i(TAG, "increase timer count failed");
                        AgeWorking = false;
                        break;
                    } else {
                        cur = 0;
                    }
                }
                if (MAX_AGING_PERIOD > 0) {
                    int agingCount = getAgingCount();
                    tempCheck(agingCount);
                    //change aging vol when aging count > aging line
                    Log.i(TAG, "agingCount is " + agingCount);
                    if (agingCount > AgingUtil.getAgingLine(mContext)) {
                        Log.i(TAG, " audioSetSoundVolume is " + AgingUtil.getAgingVolume(mContext));
                        SDKManager.getAudioTestManager().audioSetSoundVolume(AgingUtil.getAgingVolume(mContext));
                    }
                    if (agingCount > MAX_AGING_PERIOD) {
                        AgeWorking = false;
                    }
                }
            }
        }
    }


}
