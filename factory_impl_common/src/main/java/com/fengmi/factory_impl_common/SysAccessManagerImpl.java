package com.fengmi.factory_impl_common;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_data.DLPCmd;
import com.fengmi.factory_test_interf.sdk_interf.SysAccessManagerInterf;
import com.fengmi.factory_test_interf.sdk_utils.ShellUtil;

import java.io.IOException;

public class SysAccessManagerImpl implements SysAccessManagerInterf {
    private String[] IMAGE_MODES = null;
    private AsyncTask<Void, Void, Boolean> mSaveTask = null;
    private DLPCmd dlmCMD;
    private Projector_Sensor ps = null;
    private Context mCtx;

    SysAccessManagerImpl(Context context) {
        mCtx = context;
        this.dlmCMD = DLPCmd.initDLPCmd(Build.DEVICE);
        this.IMAGE_MODES = dlmCMD.getImageModes();

    }

    @Override
    public boolean enableScreenCheck(String param) {
        boolean ret;
        boolean enable = false;
        if ("0".equals(param)) {
            enable = true;
        }
        ret = enableCheckScreen(enable);
        return ret;
    }

    public boolean screenCheck(int mode) {
        boolean ret = false;
        ret = doCheckScreen(mode);
        return ret;
    }

//========================================

    public boolean syncDlpInfo() {
        try {
            ShellUtil.execCommand("dlp_metadata --sync");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveDlpInfo() {
        try {
            return startSaveProgress();
        } catch (Exception e) {
            return false;
        }

    }

    public boolean setWheelDelay(int delay) {
        SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/class/projector/laser-projector/laser_seq", String.valueOf(delay));
        return true;
    }

    public int getWheelDelay() {
        try {
            String value = ShellUtil.execCommand("cat /sys/class/projector/laser-projector/laser_seq");
            int v = ShellUtil.formatDelayNumber(value);
            return v;
        } catch (Exception e) {
            e.printStackTrace();
            return 1000;
        }

    }

    public boolean startSaveProgress() {
        if (mSaveTask != null) {
            return false;
        }
        mSaveTask = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    String execCommand = ShellUtil.execCommand("cat /sys/class/projector/laser-projector/laser_seq");
                    int value = ShellUtil.formatDelayNumber(execCommand);
                    Log.i(TAG, "laser_seq is " + value);
                    ShellUtil.execCommand("dlp_metadata --set dlp_index_delay " + value);
                    ShellUtil.execCommand("dlp_metadata --save");
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                mSaveTask = null;
            }

        };
        mSaveTask.execute();
        return true;
    }

    public boolean doCheckScreen(int mode) {
        if (mode < 0 || mode >= IMAGE_MODES.length) {
            return false;
        }
        SDKManager.getAmlogicManagerInterf().writeSysFs(dlmCMD.getImageCMDPath(), IMAGE_MODES[mode]);
        return true;
    }

    public boolean enableCheckScreen(boolean enable) {
        SDKManager.getAmlogicManagerInterf().writeSysFs(dlmCMD.getImageInitCMDPath(), dlmCMD.getScreenCheckInitCmd(enable));
        return true;
    }

    public boolean enableXPRCheck(String param) {
        if ("0".equals(param)) {
            SDKManager.getAmlogicManagerInterf().writeSysFs(dlmCMD.getImageCMDPath(), dlmCMD.getXPRCheckInitCmd(true));
        } else {
            SDKManager.getAmlogicManagerInterf().writeSysFs(dlmCMD.getImageCMDPath(), dlmCMD.getXPRCheckInitCmd(false));
        }
        return true;
    }

    public boolean enableXPRShake(String param) {
        if ("0".equals(param)) {
            SDKManager.getAmlogicManagerInterf().writeSysFs(dlmCMD.getImageCMDPath(), dlmCMD.getXPRShakeCmd(true));
        } else {
            SDKManager.getAmlogicManagerInterf().writeSysFs(dlmCMD.getImageCMDPath(), dlmCMD.getXPRShakeCmd(false));
        }
        return true;
    }

    public boolean checkGSensorFunc() {
        if (ps == null) {
            ps = new Projector_Sensor(mCtx);
        }
        Log.i("GSensor", "gSensor startCollect data ");
        if (ps.isCompleted()) {
            //float[] res = ps.getSensorResult();
            //return Arrays.toString(res);
            return ps.getSensorResult();
        }
        return false;
    }

    public boolean startGSensorCollect() {
        if (ps == null) {
            ps = new Projector_Sensor(mCtx);
        }
        ps.startCollect();
        return true;
    }

    public boolean saveGSensorStandard() {
        if (ps == null) {
            ps = new Projector_Sensor(mCtx);
        }
        return ps.saveStandardData();
    }

    public String readGSensorStandard() {
        if (ps == null) {
            ps = new Projector_Sensor(mCtx);
        }
        return ps.readGsensorData();
    }

    public String readGSensorHorizontalData() {
        if (ps == null) {
            ps = new Projector_Sensor(mCtx);
        }
        return ps.readHorizontal();
    }

    public String readDLPVersion() {
        return getDlpVersion();
    }

    @Override
    public boolean setKeyStoneMode(String param) {
        return false;
    }

    @Override
    public boolean setKeyStoneDirect(String param) {
        return false;
    }

    private String getDlpVersion() {
        String version = "";
        SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/class/projector/led-projector/i2c_read", "d9 4");
        version = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/class/projector/led-projector/i2c_read");
        Log.i("DlpVersion", "read original dlp version info :: " + version);
        if (!version.trim().equals("")) {
            if (version.contains("echo")) {
                version = version.split("echo")[0];
            } else {
                version = version.substring(0, 8);
            }
        }
        Log.i("DlpVersion", "read dlp version info :: " + version);
        return version;
    }

    @Override
    public boolean writeProjectorCMD(String param) {
        if (param == null) {
            return false;
        }
        Log.d(TAG, "write projector CMD : " + param);
        String[] ss = param.split(",");
        //判断格式是否符合
        if (ss.length == 3 && TextUtils.isDigitsOnly(ss[0]) && TextUtils.isDigitsOnly(ss[1])) {
            int prefix = Integer.parseInt(ss[0]);
            int suffix = Integer.parseInt(ss[1]);
            if (prefix < projector_type.length && suffix < projector_node.length) {
                //向指定节点写入数据
                return SDKManager.getAmlogicManagerInterf().writeSysFs(projector_type[prefix] + projector_node[suffix], ss[2]);
            }
            return false;
        }
        return false;
    }

    @Override
    public String readProjectorCMD(String param) {
        if (param == null) {
            return "error";
        }
        Log.d(TAG, "read projector CMD : " + param);
        String[] ss = param.split(",");
        //判断格式是否符合
        if (ss.length == 2 && TextUtils.isDigitsOnly(ss[0]) && TextUtils.isDigitsOnly(ss[1])) {
            int prefix = Integer.parseInt(ss[0]);
            int suffix = Integer.parseInt(ss[1]);
            if (prefix < projector_type.length && suffix < projector_node.length) {
                //向指定节点写入数据
                return SDKManager.getAmlogicManagerInterf().readSysFs(projector_type[prefix] + projector_node[suffix]);
            }
            return "error";
        }
        return "error";
    }

    @Override
    public boolean writeI2CCMD(String param) {
        if (param == null) {
            return false;
        }
        Log.d(TAG, "write I2C CMD : " + param);
        String[] ss = param.split(",");
        //判断格式是否符合
        if (ss.length == 3 && TextUtils.isDigitsOnly(ss[0]) && TextUtils.isDigitsOnly(ss[1])) {
            int prefix = Integer.parseInt(ss[0]);
            int suffix = Integer.parseInt(ss[1]);
            if (prefix < i2c_prefix.length && suffix < i2c_suffix.length) {
                //向指定节点写入数据
                return SDKManager.getAmlogicManagerInterf().writeSysFs(i2c_prefix[prefix] + i2c_suffix[suffix], ss[2]);
            }
            return false;
        }
        return false;
    }

    @Override
    public String readI2CCMD(String param) {
        if (param == null) {
            return "error";
        }
        Log.d(TAG, "read I2C CMD : " + param);
        String[] ss = param.split(",");
        //判断格式是否符合
        if (ss.length == 2 && TextUtils.isDigitsOnly(ss[0]) && TextUtils.isDigitsOnly(ss[1])) {
            int prefix = Integer.parseInt(ss[0]);
            int suffix = Integer.parseInt(ss[1]);
            if (prefix < i2c_prefix.length && suffix < i2c_suffix.length) {
                //向指定节点写入数据
                return SDKManager.getAmlogicManagerInterf().readSysFs(i2c_prefix[prefix] + i2c_suffix[suffix]);
            }
            return "error";
        }
        return "error";
    }

    @Override
    public boolean sendKeyCode(String param) {
        if (TextUtils.isEmpty(param) || !TextUtils.isDigitsOnly(param)) {
            return false;
        }
        try {
            ShellUtil.execCommand("input keyevent " + param);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

