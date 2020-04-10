package com.fengmi.factory_impl_common;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_globle.SettingManager;
import com.fengmi.factory_test_interf.sdk_interf.FengManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.UtilManagerInterf;
import com.fengmi.factory_test_interf.sdk_utils.ShellUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.fengmi.factory_test_interf.sdk_utils.ShellUtil.echoEntry;


public class UtilManagerImpl implements UtilManagerInterf {

    private Context mContext;
    private SettingManager mSetManager;
    private LedFlashThread mLedFlashThread;
    private String mProductModel;
    private Handler mHandler = new Handler();
    private String ProductModel_TV3_55 = "MiTV3";
    private boolean LedFlashFlag = false;
    private int FlashPeriod = 0;
    private String IRLockPath = "/sys/module/rc_core/parameters/offir_debug";
    //=================================================================
    private int mRepeatCount = 0;
    private int mLeftEventCount = 0;
    private int mRightEventCount = 0;
    private Runnable mBodyDetectRunnable = new Runnable() {
        public void run() {
            if (mRepeatCount % 10 == 0) {
                Log.i(TAG, "mBodyDetectRunnable.run " + mRepeatCount);
            }
            int left = SDKManager.getFengManagerInterf().getProjectorEventStatus(FengManagerInterf.PROJECTOR_IR1_EVENT);
            if (left > 0) {
                Log.i(TAG, "BodyDetect Trigger (Left) " + mLeftEventCount);
                mLeftEventCount++;
            }
            int right = SDKManager.getFengManagerInterf().getProjectorEventStatus(FengManagerInterf.PROJECTOR_IR2_EVENT);
            if (right > 0) {
                Log.i(TAG, "BodyDetect Trigger (Right) " + mRightEventCount);
                mRightEventCount++;
            }
            mRepeatCount++;

            mHandler.postDelayed(mBodyDetectRunnable, 200);
        }
    };

    UtilManagerImpl(Context context) {
        mContext = context;
        mSetManager = new SettingManager();
        mProductModel = getProductModel();
    }

    private static String getHardwareVersion() {
        return SDKManager.getAndroidOSManagerInterf().getSystemProperty("ro.boot.hardware_version", "");
    }

    private static String getHardwareID() {
        return SDKManager.getAndroidOSManagerInterf().getSystemProperty("ro.boot.hardware_id", "");
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int[] getKeyTestSequence() {
        int[] ret = null;
        ret = keyTestSequence();
        return ret;
    }

    public int lightSensorGetValue() {
        int ret = -1;
        ret = readLightSensorValue();
        return ret;
    }

    public boolean systemReset() {
        boolean ret = false;
        ret = resetSystem();
        return ret;
    }

    public boolean touchpadSetStatus() {
        boolean ret = false;
        return ret;
    }

    public boolean remoteControlSetLock(String lock) {
        boolean ret = false;
        ret = remoteLockController(lock);
        return ret;
    }

    public boolean systemSleepSetStatus() {
        boolean ret = false;
        return ret;
    }

    public boolean checkSystemPartition() {
        boolean ret = false;
        ret = checkPartition();
        return ret;
    }

    public boolean setLedLightStat(String style) {
        boolean ret = false;
        ret = LedController(style);
        return ret;
    }

    public boolean ledStartFlash(int period) {
        boolean ret = false;
        ret = ledFlashStart(period);
        return ret;
    }

    public boolean ledStopFlash() {
        boolean ret = false;
        ret = ledFlashStop();
        return ret;
    }

    public boolean systemSwitchMode() {
        boolean ret = false;
        ret = switchSystem();
        return ret;
    }

    public boolean systemSwitchModeNew() {
        boolean ret = false;
        ret = switchFactToUserForP();
        return ret;
    }

    public boolean systemModeSet() {
        boolean ret = false;
        ret = setSystemMode();
        return ret;
    }

    public boolean systemModeGet() {
        boolean ret = false;
        ret = getSystemMode();
        return ret;
    }

    public boolean systemReboot() {
        boolean ret = false;
        ret = rebootSystem();
        return ret;
    }

    public boolean systemRebootRecovery() {
        boolean ret = false;
        ret = rebootRecovery();
        return ret;
    }

    public boolean systemShutdown() {
        boolean ret = false;
        ret = shutdownSystem();
        return ret;
    }

    public boolean systemMasterClear() {
        boolean ret = false;
        ret = systemClearAllShutdown();
        return ret;
    }

    //call it in commandservice's oncreate
    public boolean systemUpdateBootTimes() {
        boolean ret = false;
        ret = updateBootTimes();
        return ret;
    }

    public int systemGetBootTimes() {
        int ret = -1;
        ret = getBootTimes();
        return ret;
    }

    public boolean setProductRegion(String region) {
        boolean ret = false;
        //ret = setRegion(region);
        return ret;
    }

    public String getProductRegion() {
        String ret = null;
        ret = getRegion();
        return ret;
    }

    public boolean sleepSystem() {
        boolean ret = false;
        Log.d(TAG, "---- Factory Method Func Test Start----");
        try {
            int mode = Settings.System.getInt(mContext.getContentResolver(), "power_key_definition");
            Log.d(TAG, "old power_key_definition is " + mode);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        //设置按键模式
        ret = Settings.System.putInt(mContext.getContentResolver(), "power_key_definition", 1);
        try {
            int mode = Settings.System.getInt(mContext.getContentResolver(), "power_key_definition");
            Log.d(TAG, "new power_key_definition is " + mode);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "---- Factory Method Func Test End----");
        return ret;
    }

    public boolean resetTvPanelSelect() {
        boolean ret = false;
        ret = panelSelect();
        return ret;
    }

    public boolean setBtRcMac(String mac) {
        boolean ret = false;
        ret = writeBtRcAddrForP(mac);
        return ret;
    }

    public String getBtRcMac() {
        String ret = null;
        ret = readBtRcAddrForP();
        return ret;
    }

    public void bootupSystemDirect() {
        bootDirect();
    }

    public String getProductModel() {
        String ret = null;
        ret = getModel();
        return ret;
    }

    public boolean closeScreenSave2Sleep() {
        boolean ret = false;
        ret = disableScreenSaver2Sleep();
        return ret;
    }

    public boolean setApplicationRid() {
        boolean ret = false;
        ret = locSetApplicationRid();
        return ret;
    }

    public boolean getSubWooferStat() {
        boolean ret = false;
        ret = subWooferPlugInStat();
        return ret;
    }

    public boolean setPowerStandbyStat(String stat) {
        boolean ret = false;
        ret = setPowerStandby(stat);
        return ret;
    }

    public boolean setI2CPinStat(String stat) {
        boolean ret = false;
        //ret = setI2CPin(stat);
        return ret;
    }

    //empty function for compatible box
    public String getCpuTemp() {
        String ret = null;
        return ret;
    }

    public boolean setFanStat(String speed) {
        boolean ret = false;
        return ret;
    }

    public boolean setGpioOut(String portAstat) {
        boolean ret = false;
        ret = setGpioStat(portAstat);
        return ret;
    }

    public int getGpioInStat(String port) {
        int ret = -1;
        ret = getGpioInValue(port);
        return ret;
    }

    public String checkPanelIdTag() {
        String ret = null;
        ret = readPanelIdTag();
        return ret;
    }

    public boolean setVcom(String para) {
        boolean ret = false;
        ret = writeVcom(para);
        return ret;
    }

    public byte getVcom(String para) {
        byte ret = (byte) 0xff;
        ret = readVcom(para);
        return ret;
    }

    public boolean setWifiCountryCode(String para) {
        boolean ret = false;
        ret = writewificcode(para);
        return ret;
    }

    @Override
    public boolean switchMCUFactoryMode(String param) {
        if (TextUtils.isEmpty(param) || !TextUtils.isDigitsOnly(param)) {
            return false;
        }
        if (param.equals("1")) {
            echoEntry("/sys/class/fengmi_battery/fengmi_battery/fact_mode", "1");
            Log.d(TAG, "fengmi_battery in factory mode. param is " + param);
        } else {
            echoEntry("/sys/class/fengmi_battery/fengmi_battery/fact_mode", "0");
            Log.d(TAG, "fengmi_battery out factory mode. param is " + param);
        }
        return true;
    }

    @Override
    public boolean rebootUpdate() {
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        pm.reboot("update");
        //return reboot_update();
        return true;
    }

    @Override
    public boolean writeFactoryMode(String param) {
        return writeFactMode(param);
    }

    @Override
    public String readFactoryMode() {
        return readFactMode();
    }

    @Override
    public boolean writeBootEnv(String name, String val) {
        return writeFactBootEnv(name, val);
    }

    @Override
    public String readBootEnv(String name) {
        return readFactBootEnv(name);
    }

    @Override
    public boolean writeUnifyKey(String name, String value) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
            return false;
        }
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(name, value);
        return true;
    }

    @Override
    public String readUnifyKey(String name) {
        if (!TextUtils.isEmpty(name)) {
            return SDKManager.getAmlogicManagerInterf().readUnifyKey(name);
        }
        return null;
    }

    public String getWifiCountryCode() {
        String ret = null;
        ret = readwificcode();
        return ret;
    }

    public String readRomTotalSpace() {
        return readRomSpace("T");
    }

    public String readRomAvailSpace() {
        return readRomSpace("A");
    }

    public boolean setFanSpeed(String level) {
        return changeFanSpeed(level);
    }

    public boolean resetLEDStepMotor() {
        return resetStepMotor();
    }

    public String readLEDTemperature(String param) {
        return readLedTemperature(param);
    }

    public String readRGBLEDCurrent() {
        return readRGBCurrent();
    }

    @Override
    public boolean screenSaverEnable(boolean enbale) {
        return false;
    }

    /*===========================================local functions=====================*/
    private boolean writeFactBootEnv(String name, String val) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(val)) {
            return false;
        }
        SDKManager.getFengManagerInterf().setBootEnv(name, val);

        String mode = SDKManager.getFengManagerInterf().getBootEnv(name, "-1");
        if (TextUtils.equals(mode, val)) {
            return true;
        }
        return false;
    }

    private String readFactBootEnv(String name) {
        String mode = null;
        mode = SDKManager.getFengManagerInterf().getBootEnv(name, "error");
        return mode;
    }

    private boolean writeFactMode(String param) {
        if (TextUtils.isEmpty(param) || !TextUtils.isDigitsOnly(param)) {
            return false;
        }
        SDKManager.getFengManagerInterf().setBootEnv("factory_mode", param);

        String mode = SDKManager.getFengManagerInterf().getBootEnv("factory_mode", "-1");
        if (TextUtils.equals(mode, param)) {
            return true;
        }
        return false;
    }

    private String readFactMode() {
        String mode = null;
        mode = SDKManager.getFengManagerInterf().getBootEnv("factory_mode", "-1");
        return mode;
    }

    private boolean writeBtRcAddrForP(String mac) {
        if (TextUtils.isEmpty(mac)) {
            Log.d(TAG, "mac is empty,so return false. mac = " + mac);
            return false;
        }
        mac = mac.toUpperCase();
        if (mac.matches("^[0-9A-Z]{2}:[0-9A-Z]{2}:[0-9A-Z]{2}:[0-9A-Z]{2}:[0-9A-Z]{2}:[0-9A-Z]{2}$")) {
            SDKManager.getFengManagerInterf().setBootEnv("ubootenv.var.auto_pair_rc_macaddr", mac);
            String val = SDKManager.getFengManagerInterf().getBootEnv("ubootenv.var.auto_pair_rc_macaddr", "");
            if (TextUtils.equals(mac, val)) {
                return true;
            } else {
                Log.d(TAG, "write value is not equals read value,so return false");
                return false;
            }
        } else {
            return false;
        }
    }

    private String readBtRcAddrForP() {
        String mac = null;
        mac = SDKManager.getFengManagerInterf().getBootEnv("ubootenv.var.auto_pair_rc_macaddr", "null");
        return mac;
    }

    private String readRGBCurrent() {
        String res = "";
        res = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/class/projector/led-projector/rgb_led_current");
        Log.e(TAG, "read RGB LED CURRENT : " + res);
        return res;
    }

    private String readLedTemperature(String param) {
        if (TextUtils.isEmpty(param) || !TextUtils.isDigitsOnly(param)) {
            return "param error :" + param;
        }
        int index = Integer.parseInt(param);
        String temp;
        List<String> cmdList = SDKManager.getProjectorManager().getTemperatureCommandList();
        if (index < cmdList.size()) {
            try {
                temp = ShellUtil.execCommand(cmdList.get(index));
            } catch (IOException e) {
                e.printStackTrace();
                temp = "temp read error";
            }
        } else {
            temp = "param error > " + cmdList.size();
        }
        return temp;
    }

    private boolean resetStepMotor() {
        new Thread() {
            @Override
            public void run() {
                SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/class/vgsm2028/vgsm2028/proj_motor_calibration", "1");
            }
        }.start();
        return true;
    }

    private boolean changeFanSpeed(String level) {
        String fanLevel;
        switch (level) {
            case "0":
                //min
                fanLevel = "0";
                break;
            case "1":
                //mid
                fanLevel = "7";
                break;
            case "2":
                //max
                fanLevel = "15";
                break;
            default:
                fanLevel = "0";
                break;

        }
        //return echoEntry("/sys/class/projector/led-projector/projector-0-fan/fan_speed", fanLevel);
        return SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/class/projector/led-projector/projector-0-fan/fan_speed", fanLevel);
    }

    private String readRomSpace(String type) {
        Log.e(TAG, "Read Rom Space type : " + type);
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount;
        String romSpace = "Error";

        if ("T".equals(type)) {
            blockCount = stat.getBlockCountLong();
            romSpace = Formatter.formatFileSize(mContext, blockCount * blockSize);
        }
        if ("A".equals(type)) {
            blockCount = stat.getAvailableBlocksLong();
            romSpace = Formatter.formatFileSize(mContext, blockCount * blockSize);
        }
        Log.e(TAG, "Read Rom Space size : " + type);
        return romSpace;
    }

    private boolean writeVcom(String para) {
        boolean ret = false;
        Log.i(TAG, "set Vcom command");

        byte[] command_byte = toHexString(para).getBytes();
        try {
            FileOutputStream fstream = new FileOutputStream("/sys/class/i2c/slave");
            fstream.write(command_byte, 0, command_byte.length - 1);
            //fstream.getFD().sync();
            fstream.close();
            Log.e(TAG, "set Vcom command: OK");
            ret = true;
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            FileOutputStream fstream = new FileOutputStream("/sys/class/i2c1/mi_slave");
            fstream.write(command_byte, 0, command_byte.length - 1);
            //fstream.getFD().sync();
            fstream.close();
            Log.e(TAG, "set Vcom command: OK");
            ret = true;
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }

        //SystemClock.sleep(100);
        //mSystemControl.writeSysFs("sys/class/gpio/gpio199/value","0");
        return ret;
    }

    private byte readVcom(String para) {
        boolean ret1 = false;
        byte ret = (byte) 0xff;
        String value = "";
        Log.i(TAG, "get vcom data from i2c");
        ret1 = writeVcom(para);
        if (ret1) {
            value = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/class/i2c/slave");
            String buf = value.substring(2, 4);
            ret = (byte) Integer.parseInt(buf, 16);
        }
        Log.i(TAG, "get vcom data from i2c : " + ret);
        if (ret == 0) {
            value = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/class/i2c1/mi_slave");
            String buf = value.substring(2, 4);
            ret = (byte) Integer.parseInt(buf, 16);
        }
        Log.i(TAG, "get vcom data from i2c : " + ret);
        return ret;
    }

    private int readLightSensorValue() {
        Process proc = null;
        long lastTime = SystemClock.uptimeMillis();
        long currentTime = 0;
        boolean captureTimes = true;
        int ret = -1;
        Log.i(TAG, "get Light Sensor Result");
        try {
            String msg;
            Runtime rt = Runtime.getRuntime();
            proc = rt.exec("/system/bin/getals 2");
            proc.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while (captureTimes) {
                msg = in.readLine();
                if (msg.contains("lux")) {
                    String[] buf = msg.split("= ");
                    ret = Integer.parseInt(buf[1]);
                    //if valid capture, switch the flag and jump out cycle.
                    captureTimes = false;
                }
                currentTime = SystemClock.uptimeMillis();
                if ((currentTime - lastTime) > 4000) {
                    break;
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private boolean rebootSystem() {
        Log.i(TAG, "reboot system");
        //mContext.sendBroadcast(new Intent(mSetManager.INTENT_REBOOTSYS));

        Process proc = null;
        try {
            String cmd = "reboot";
            proc = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean rebootRecovery() {
        Process proc = null;
        try {
            String cmd = "reboot recovery";
            proc = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean reboot_update() {
        Process proc = null;
        try {
            String cmd = "reboot update";
            proc = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean resetSystem() {
        Log.i(TAG, "reset system (master clear)");
        SDKManager.getAndroidOSManagerInterf().setSystemProperty(mSetManager.SYSPROP_CONSOLEDISABLE, "1");
        SDKManager.getAndroidOSManagerInterf().setSystemProperty(mSetManager.SYSPROP_FACTORYPOWERMODE, POWER_HOLD);
        while (!SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_CONSOLEDISABLE).equals("1") ||
                !SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_FACTORYPOWERMODE).equals(POWER_HOLD)) {
            SystemClock.sleep(100);
        }
        Intent resetIntent = new Intent(mSetManager.INTENT_MASTERCLEARSYS);
        //resetIntent.putExtra("com.xiaomi.tv.WIPE_INSTALLED_APPS", true);
        mContext.sendBroadcast(resetIntent);
        return true;
    }

    private boolean resetSystem_clearAll() {
        Log.i(TAG, "reset system (master clear)");
        Intent resetIntent = new Intent(mSetManager.INTENT_MASTERCLEARSYS);
        resetIntent.putExtra("com.xiaomi.tv.WIPE_INSTALLED_APPS", true);
        mContext.sendBroadcast(resetIntent);
        return true;
    }

    private boolean switchsys() {
        Log.i(TAG, "reset system (master clear), and restore user system from backup partition");
        echoEntry(switchCmdPath, switchCmd);
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        pm.reboot(REBOOT_RECOVERY);
        Log.i(TAG, "switch system end");
        return true;
    }

    private boolean rebootSystemFormatData() {
        Log.i(TAG, "=================Mi User Package switch===================");
        Log.i(TAG, "reset system (master clear), and restore user system from backup partition");
        echoEntry(switchCmdPath, PROP_PRODUCT_FORMAT_DATA);
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        pm.reboot(REBOOT_RECOVERY);
        Log.i(TAG, "switch system end");
        return true;
    }


    private boolean rebootSystemWipeData() {
        Log.i(TAG, "=================F Mi User Package switch===================");
        Log.i(TAG, "reset system (master clear), and restore user system from backup partition");
        echoEntry(switchCmdPath, PROP_PRODUCT_WIPEDATA);
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        pm.reboot(REBOOT_RECOVERY);
        Log.i(TAG, "switch system end");
        return true;
    }

    private void shutdownsys() {
        SDKManager.getAndroidOSManagerInterf().shutdown(false, "factory cmd shutdown", false);
    }

    private byte[] getByte(String path, int len, int offset) {
        byte[] key = new byte[len];
        byte[] temp = new byte[offset];
        Log.i(TAG, "Get byte from file len:" + len + " offset:" + offset);
        Log.i(TAG, "Get byte from file length:" + key.length);
        File keyfile = new File(path);
        if (keyfile.exists()) {
            try {
                FileInputStream fstream = new FileInputStream(keyfile);
                int ret = fstream.read(temp, 0, offset);
                ret = fstream.read(key, 0, len);
                fstream.close();
                Log.e(TAG, "Get Bytes: get " + ret + "bytes ");
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return key;
    }

    private byte[] getByte(String path, int len) {
        byte[] key = new byte[len];
        Log.i(TAG, "Get byte from file length:" + key.length);
        File keyfile = new File(path);
        if (keyfile.exists()) {
            try {
                FileInputStream fstream = new FileInputStream(keyfile);
                int ret = fstream.read(key, 0, len);
                fstream.close();
                Log.e(TAG, "Get Bytes: get " + ret + "bytes ");
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return key;
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

    private boolean systemClearAllShutdown() {
        Log.i(TAG, "reset system (master clear)");
        //set power mode as secondary
        SDKManager.getAndroidOSManagerInterf().setSystemProperty(mSetManager.SYSPROP_FACTORYPOWERMODE, POWER_HOLD);
        //masterclear
        Intent resetIntent = new Intent(mSetManager.INTENT_MASTERCLEARSYS);
        resetIntent.putExtra("com.xiaomi.tv.WIPE_INSTALLED_APPS", true);
        resetIntent.putExtra("shutdown", true);
        mContext.sendBroadcast(resetIntent);
        return true;
    }

    private boolean writePowerMode() {
        String stat;
        String target;

        if (SDKManager.getHwManager().getHW().getKeyboard()) {
            //需要按键开机
            SDKManager.getAmlogicManagerInterf().writeUnifyKey(UNIFYKEY_POWER_MODE, POWER_HOLD);
            target = POWER_HOLD;
        } else {
            SDKManager.getAmlogicManagerInterf().writeUnifyKey(UNIFYKEY_POWER_MODE, POWER_ON_DIR);
            target = POWER_ON_DIR;
        }
        stat = SDKManager.getAmlogicManagerInterf().readUnifyKey(UNIFYKEY_POWER_MODE);
        Log.i(TAG, "read factory power mode : " + stat);
        return stat.equals(target);
    }

    private void bootDirect() {
        String stat = null;
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(UNIFYKEY_POWER_MODE, "direct");
        stat = SDKManager.getAmlogicManagerInterf().readUnifyKey(UNIFYKEY_POWER_MODE);
        Log.i(TAG, "factory power mode : " + stat);
    }

    private boolean shutdownSystem() {
        boolean ret = false;
        Log.i(TAG, "shutdownSystem ");
        if (writePowerMode()) {
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Log.i(TAG, "postDelayed shutdownsys");
                    shutdownsys();
                }
            }, 1000);
            ret = true;
        }
        return ret;
    }

    private boolean switchSystem() {
        boolean ret = false;
        Log.i(TAG, "switch system");
        if (writePowerMode()) {
            int delay = 1000;
            Log.i(TAG, "reboot after " + delay + "ms");
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Log.i(TAG, "postDelayed switchsys");
                    switchsys();
                }
            }, 1000);
            ret = true;
        }
        return ret;

    }

    private boolean switchFactToUserForP() {
        Log.i(TAG, "we are going to switch system to user,first writePowerMode");
        if (writePowerMode()) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String res = "error";
                    Log.i(TAG, "postDelayed switchsys eva");
                    SDKManager.getAndroidOSManagerInterf().setSystemProperty("vendor.factory_to_user", "1");
                    res = SDKManager.getAndroidOSManagerInterf().getSystemProperty("vendor.factory_to_user", "null");
                    SystemClock.sleep(5 * 1000);
                    Log.i(TAG, "switchsys factory_to_user result is " + res);
                    if (res.contains("1")) {
                        Log.i(TAG, "postDelayed eva reboot");
                        // try {
                        //     ShellUtil.execCommand("reboot");
                        // } catch (IOException e) {
                        //     e.printStackTrace();
                        // }
                        //用于以后，切换用户使用，用来替换上述 reboot 指令
                        // TODO: 2020-01-07  针对小米系列用户包，请使用rebootSystemFormatData
                        // TODO: 2020-01-07  针对峰米系列或海外用户包，请使用rebootSystemWipeData
                        if (SDKManager.getHwManager().getHW().getMiPackage()) {
                            rebootSystemFormatData();
                        } else {
                            rebootSystemWipeData();
                        }
                    } else {
                        Log.i(TAG, "switchsys failed , factory_to_user is " + res);
                    }
                }
            }, 1000);
            return true;
        } else {
            Log.i(TAG, "we are going to switchFactToUserForP but writePowerMode failed !!!!");
            return false;
        }
    }

    private boolean setSystemMode() {
        //SDKManager.getAndroidOSManagerInterf().setSystemProperty(mSetManager.SYSPROP_FACTORYMODE, "0");
        SDKManager.getAndroidOSManagerInterf().setSystemProperty(mSetManager.SYSPROP_BOOTSYSTEM, "0");
        SDKManager.getAndroidOSManagerInterf().setSystemProperty(mSetManager.SYSPROP_CONSOLEDISABLE, "1");
        SDKManager.getAndroidOSManagerInterf().setSystemProperty(mSetManager.SYSPROP_FACTORYPOWERMODE, POWER_HOLD);
        return true;
    }

    private boolean getSystemMode() {
        if (!SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_FACTORYMODE).equals("0") ||
                !SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_CONSOLEDISABLE).equals("1") ||
                !SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_FACTORYPOWERMODE).equals(POWER_HOLD)) {
            return false;
        }
        return true;
    }

    private String getRegion() {
        String propPath = "/persist/factory.prop";
        BufferedReader br = null;
        String language = "";
        String region = "";
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(propPath)), 512);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.contains("persist.sys.country")) {
                    region = line.substring(line.indexOf("=") + 1);
                } else if (line.contains("persist.sys.language")) {
                    language = line.substring(line.indexOf("=") + 1);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "read data exception for " + propPath, e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
        }
        return language + "_" + region;
    }

    private boolean checkPartition() {
        boolean ret = true;
        if (SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_PARTITIONCHECK).equals("1")) {
            ret = false;
        }
        Log.i(TAG, "check partition state: " + ret);
        return ret;
    }

    private boolean LedController(String stat) {
        Log.i(TAG, "LedController: " + "[" + stat + "]");
        boolean ret = false;

        //led cmd路径
        String ledFM18Path = "/sys/class/leds/sysled/brightness";
        String triggerPath = "/sys/class/leds/sysled/trigger";

        echoEntry(triggerPath, "none");
        switch (stat) {
            case "0":
                break;
            case "1":
                stat = "255";
                break;
            default:
                stat = "255";
                break;
        }
        String cmd = "echo " + stat + " > " + ledFM18Path;
        Log.i(TAG, cmd);
        ret = echoEntry(ledFM18Path, stat);
        return ret;
    }

    private boolean updateBootTimes() {
        boolean ret = false;
        ret = SDKManager.getLocalPropertyManager().increaseLocalPropInt(mSetManager.FACTPROP_BOOTCOUNT, 1);
        return ret;
    }

    private int getBootTimes() {
        int ret = -1;
        ret = SDKManager.getLocalPropertyManager().getLocalPropInt(mSetManager.FACTPROP_BOOTCOUNT);
        return ret;
    }

    private boolean ledFlashStart(int period) {
        boolean ret = false;
        if (mProductModel.equals(ProductModel_TV3_55)) {
            FileWriter f;
            final int LED_CLOSE = 0;
            final int LED_FLICKER = 1;
            final int LED_STATIC_LIGHT = 2;
            String LedPeriodPath = "/sys/module/timed_breathled_incptn/parameters/breathperiod";
            String LedEnablePath = "/sys/class/timed_output/breathled_b/enable";
            echoEntry(LedPeriodPath, String.valueOf(period));
            echoEntry(LedEnablePath, String.valueOf(LED_STATIC_LIGHT));
        } else {
            if (mLedFlashThread == null) {
                Log.i(TAG, "LedFlashThread begin work");
                LedFlashFlag = true;
                if (period > 0) {
                    FlashPeriod = period;
                } else {
                    FlashPeriod = DEFAULT_PERIOD;
                }
                mLedFlashThread = new LedFlashThread();
                mLedFlashThread.start();
                ret = true;
            }
        }
        return ret;
    }

    private boolean ledFlashStop() {
        boolean ret = false;
        if (!LedFlashFlag || mLedFlashThread == null) {
            Log.e(TAG, "now aging isn't work , dont close it repeatly");
            return ret;
        }
        LedFlashFlag = false;
        try {
            mLedFlashThread.join();
            mLedFlashThread = null;
            ret = true;
            Log.i(TAG, "stop Led Flash success!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG, "stop Led Flash failed");
        }
        return ret;
    }

    private boolean remoteLockController(String stat) {
        boolean ret = false;
        Log.i(TAG, "KeyLock: " + "[" + stat + "]");
        if (!stat.equals("0") && !stat.equals("1")) {
            return ret;
        }
        String cmd = "misysdiagnose:-s echo," + stat + ",>" + IRLockPath;
        SDKManager.getAndroidOSManagerInterf().setSystemProperty("ctl.start", cmd);
        ret = true;
        return ret;
    }

    private boolean disableScreenSaver2Sleep() {
        boolean ret = false;
        //close sleep
        android.provider.Settings.System.putInt(mContext.getContentResolver(), "screen_off_timeout", 2147483647);
        //close screen saver
        android.provider.Settings.System.putInt(mContext.getContentResolver(), "screen_saver_timeout", -2);
        //ScreenSaverManager manager = ScreenSaverManager.getInstance();
        //manager.setScreenSaverEnabled(false);
        ret = true;
        return ret;
    }

    //now 55 panel have two sources, and the direct is opposite.
    //when we reset this prop, system will read it from T-CON.
    //read the direct flag needs 3s, so system wouldn't read everytime.
    private boolean panelSelect() {
        boolean ret = false;
        int j = 0;
        Log.i(TAG, "reset tv panel select");
        SDKManager.getAndroidOSManagerInterf().setSystemProperty(mSetManager.SYSPROP_55TVPANELSELECT, "0");
        for (j = 0; j < 10; j++) {
            if (SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_55TVPANELSELECT).equals("0")) {
                break;
            } else {
                SystemClock.sleep(100);
            }
        }
        if (j < 10) {
            ret = true;
        }
        return ret;
    }

    private boolean writeBtRcAddr(String mac) {
        boolean ret = false;
        int j = 0;
        Log.i(TAG, "predetermine the remote control mac address: " + mac);
        FileWriter f;
        File rcbtmacfile = new File(BTRCMACFILEPATH);
        if (!rcbtmacfile.exists()) {
            try {
                rcbtmacfile.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            f = new FileWriter(rcbtmacfile);
            f.write(mac);
            f.flush();
            f.close();
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String readBtRcAddr() {
        String ret = "00:00:00:00:00:00";
        byte[] buf = new byte[17];
        Log.i(TAG, "get the predetermined remote control mac address");
        File rcbtmacfile = new File(BTRCMACFILEPATH);
        if (rcbtmacfile.exists()) {
            try {
                FileInputStream fstream = new FileInputStream(rcbtmacfile);
                fstream.read(buf, 0, 17);
                ret = new String(buf);
                fstream.close();
                Log.e(TAG, "Get rc bt mac: " + ret);
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "can't find the rc bt mac file");
        }
        return ret;
    }

    private String getModel() {
        return SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_PRODUCTMODEL);
    }

    private boolean subWooferPlugInStat() {
        boolean ret = false;
        if (WOOFER_IN.equals(SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_WOOFERPLUGIN))) {
            ret = true;
        }
        return ret;
    }
    /*===========================================local functions=====================*/
    /*===========================================tool functions=====================*/

    private boolean setPowerStandby(String stat) {
        boolean ret = false;
		/*
		if(stat.equals(GPIO_LOW)){
			try {
				TvManager.getInstance().setTvosCommonCommand("SetLowPowerOnoff");
				ret = true;
			} catch (TvCommonException e) {
				e.printStackTrace();
			}
		}else if(stat.equals(GPIO_HIGH)){
			try {
				TvManager.getInstance().setTvosCommonCommand("SetHighPowerOnoff");
				ret = true;
			} catch (TvCommonException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	private boolean setI2CPin(String stat){
		boolean ret = false;
		if(stat.equals(GPIO_LOW)){
			try {
				TvManager.getInstance().setTvosCommonCommand("SetLowFactoryTest");
				ret = true;
			} catch (TvCommonException e) {
				e.printStackTrace();
			}
		}else if(stat.equals(GPIO_HIGH)){
			try {
				TvManager.getInstance().setTvosCommonCommand("SetHighFactoryTest");
				ret = true;
			} catch (TvCommonException e) {
				e.printStackTrace();
			}
		}
		*/
        return ret;
    }

    private boolean locSetApplicationRid() {
        boolean ret = false;
        String rid = buildRIDString();
        if (rid == null) {
            Log.e(TAG, "can fetch application rid");
        } else {
            Log.i(TAG, "application rid:" + rid);
            SDKManager.getLocalPropertyManager().setAppPropString(mSetManager.APPLPROP_RID, rid);
            String retrid = SDKManager.getLocalPropertyManager().getAppPropString(mSetManager.APPLPROP_RID);
            Log.i(TAG, "application rid read back:" + retrid);
            ret = true;
        }
        return ret;
    }

    private int[] keyTestSequence() {
        int[] ret = null;
        int[] TV_DefaultSeq = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN,
                KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
        int[] TV3_55Seq = {KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS,
                KeyEvent.KEYCODE_MEDIA_PLAY, KeyEvent.KEYCODE_MEDIA_NEXT,
                KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_HEADSETHOOK};
        if (mProductModel.equals(ProductModel_TV3_55)) {
            Log.i(TAG, "Mitv 3 KeyTest Sequence");
            ret = TV3_55Seq;
        } else {
            Log.i(TAG, "default mitv keytest sequence");
            ret = TV_DefaultSeq;
        }
        return ret;
    }

    //the para structure is "stat + port", for example (HGPIOAO_13)
    private boolean setGpioStat(String portstat) {
        boolean ret = false;
        if (TextUtils.isEmpty(portstat)) {
            return ret;
        }
        String stat = portstat.substring(0, 1);
        String port = portstat.substring(1, portstat.length());
        Log.i(TAG, port + " - setting for gpio set: " + stat);
        boolean OutputSetting = true;
        int intst = -1;
        if (stat.equals("H")) {
            intst = 1;
        } else if (stat.equals("L")) {
            intst = 0;
        } else {
            Log.e(TAG, "error setting for gpio set. " + portstat);
            return ret;
        }
        if (!TextUtils.isDigitsOnly(port)) {
            return ret;
        }
        int value = SDKManager.getAmlogicManagerInterf().handleGPIO(port, OutputSetting, intst);
        Log.i(TAG, "setGpioStat,result is :" + value);
        if (value == 0) {
            ret = true;
        }
        return ret;
    }
    /*===========================================tool functions=====================*/

    //the para structure is "port", for example (GPIOAO_13)
    private int getGpioInValue(String port) {
        int ret = -1;
        Log.i(TAG, "get setting for gpio : " + port);
        ret = SDKManager.getAmlogicManagerInterf().handleGPIO(port, false, 0);
        Log.i(TAG, "getGpioInValue:" + ret);
        return ret;
    }

    private String readPanelIdTag() {
        String ret = null;
        ret = SDKManager.getAndroidOSManagerInterf().getSystemProperty(mSetManager.SYSPROP_PANEL_TYPE);
        Log.i(TAG, "the ID tag for panel type is " + ret);
        return ret;
    }

    private String toHexString(String src) {
        String dest = "";
        String srcs[] = src.split(",");
        for (int i = 0; i < srcs.length; i++) {
            dest += String.format("%#x", Integer.parseInt(srcs[i])) + " ";
        }
        return dest;
    }

    /*
     * this function used for application team.
     * it can create a radom value for media server authentication
     */
    private String buildRIDString() {
        String retValue = "{\"rid\":\"1ab567a628374de5be40742834f0af17\",\"ssec\":\"ZW9SOt==4YeZZT&?\"}";
        Log.i("buildRIDString", "default retValue = " + retValue);
        try {
            /*UUID, 8-4-4-4-12;*/
            String uuid = UUID.randomUUID().toString();
            String tmprid = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24, uuid.length());
            Log.i(TAG, "tmprid = " + tmprid);

            String ssecSeed = "0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@$%&?#-=";
            String tmpssec = new String();
            Random rand = new Random();

            for (int i = 0; i < 16; i++) {
                int pos = rand.nextInt(ssecSeed.length());
                tmpssec += ssecSeed.substring(pos, pos + 1);
            }

            Log.i("buildRIDString", "tmpssec = " + tmpssec);
            String JSON_KEY_SSEC = "ssec";
            String JSON_KEY_RID = "rid";
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(JSON_KEY_SSEC, tmpssec);
            jsonObject.put(JSON_KEY_RID, tmprid);
            retValue = jsonObject.toString();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("buildRIDString", "retValue = " + retValue);
        return retValue;
    }

    public String getDlpSn() {
        String ret = null;
        try {
            ret = SDKManager.getFengManagerInterf().GetSerialNo();
        } catch (Exception e) {
            Log.e(TAG, "getDlpSn error");
            e.printStackTrace();
        }
        Log.i(TAG, "getDlpSn:" + ret);
        return ret;
    }

    //关闭人体感应弹窗
    public boolean setBodyDetectStatus(String para) {
        boolean ret = false;
        try {
            if ("0".equals(para)) {
                Log.i(TAG, "open body detect windows");
                SDKManager.getFengManagerInterf().setAutoBrightnessByIR(true);
            } else {
                Log.i(TAG, "close body detect windows");
                SDKManager.getFengManagerInterf().setAutoBrightnessByIR(false);
            }
            ret = true;
        } catch (Exception e) {
            Log.e(TAG, "setBodyDetectStatus error");
            e.printStackTrace();
        }
        return ret;
    }

    public int getBodyDetectStatus() {
        int ret = 2;
        try {
            boolean val = SDKManager.getFengManagerInterf().getAutoBrightnessByIR();
            Log.i(TAG, "getBodyDetectStatus:" + val);
            if (val) {
                ret = 0;
            } else {
                ret = 1;
            }
        } catch (Exception e) {
            Log.e(TAG, "getBodyDetectStatus error");
            e.printStackTrace();
            ret = 2;
        }
        return ret;
    }

    public boolean startBodyDetectTest() {
        mHandler.removeCallbacks(mBodyDetectRunnable);
        mRepeatCount = 0;
        mLeftEventCount = 0;
        mRightEventCount = 0;
        mHandler.postDelayed(mBodyDetectRunnable, 200);
        return true;
    }

    public boolean stopBodyDetectTest() {
        mHandler.removeCallbacks(mBodyDetectRunnable);
        return true;
    }

    public int getBodyDetectCount(boolean isLeft) {
        int ret = 0;
        if (isLeft) {
            ret = mLeftEventCount;
            Log.i(TAG, "read BodyDetect Trigger (Left) " + mLeftEventCount);
            mLeftEventCount = 0;
        } else {
            ret = mRightEventCount;
            Log.i(TAG, "read BodyDetect Trigger (Right) " + mRightEventCount);
            mRightEventCount = 0;
        }
        return ret;
    }

    public boolean writewificcode(String para) {
        boolean ret = false;
        Log.i(TAG, "write wifi country code as [" + para + "]");
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(WIFI_COUNTRY_CODE, para);
        ret = true;
        return ret;
    }

    public String readwificcode() {
        String ret = null;
        Log.i(TAG, "read wifi country code");
        ret = SDKManager.getAmlogicManagerInterf().readUnifyKey(WIFI_COUNTRY_CODE);
        Log.i(TAG, "read wifi country code as [" + ret + "]");
        return ret;
    }

    public boolean setMotorScale(int delta) {
        if (delta > 0) {
            SDKManager.getFengManagerInterf().setMotorConfig(FengManagerInterf.DIR_NORMAL, FengManagerInterf.SPEED_NORMAL, delta);
            SDKManager.getFengManagerInterf().setMotorStart();
            return true;
        } else if (delta < 0) {
            delta = 0 - delta;
            SDKManager.getFengManagerInterf().setMotorConfig(FengManagerInterf.DIR_REVERSE, FengManagerInterf.SPEED_NORMAL, delta);
            SDKManager.getFengManagerInterf().setMotorStart();
            return true;
        }
        return false;
    }

    private class LedFlashThread extends Thread {
        @Override
        public void run() {
            Log.i(TAG, "enter Led Flash ...");
            boolean switchFlag = false;
            String brightValue = null;
            while (LedFlashFlag) {
                if (switchFlag) {
                    switchFlag = false;
                    brightValue = "0";
                } else {
                    switchFlag = true;
                    brightValue = "2";
                }
                LedController(brightValue);
                UtilManagerImpl.sleep(FlashPeriod);
            }
        }
    }

//=================================================================
}
