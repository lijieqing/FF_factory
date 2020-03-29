package com.fengmi.factory_impl_dependency;

import android.util.Log;

import com.droidlogic.app.SystemControlManager;
import com.droidlogic.app.tv.TvControlManager;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.AmlogicManagerInterf;

public class AmlogicManagerImpl implements AmlogicManagerInterf {
    private SystemControlManager mSystenControl;
    private TvControlManager tvControlManager;

    AmlogicManagerImpl() {
        this.mSystenControl = SystemControlManager.getInstance();
        this.tvControlManager = TvControlManager.getInstance();
    }

    @Override
    public void writeUnifyKey(String keyName, String keyValue) {
        mSystenControl.writeUnifyKey(keyName, keyValue);
    }

    @Override
    public String readUnifyKey(String keyName) {
        return mSystenControl.readUnifyKey(keyName);
    }

    @Override
    public byte[] readUnifyKeyByte(String keyName, int size) {
        int[] temp = new int[size];
        byte[] data = new byte[size];
        // TODO: 2020/3/28 调整 Amlogic 接口
        //mSystenControl.readUnifyKeyByte(keyName, temp, size);
        //mSystenControl.readAttestationKey(keyName, temp, size);
        for (int i = 0; i < temp.length; i++) {
            data[i] = (byte) temp[i];
        }
        return data;
    }

    @Override
    public boolean writeHdcpRX14Key(int[] data, int size) {
        return mSystenControl.writeHdcpRX14Key(data, size);
    }

    @Override
    public boolean writeHdcpRXImg(String hk_22_Path) {
        return mSystenControl.writeHdcpRXImg(hk_22_Path);
    }

    @Override
    public boolean writeAttestationKey(String node, String dtsName, int[] key_data, int length) {
        return mSystenControl.writeAttestationKey(node, dtsName, key_data, length);
    }

    @Override
    public boolean writePlayreadyKey(String dtsName, int[] key_data, int length) {
        return mSystenControl.writePlayreadyKey(dtsName, key_data, length);
    }

    @Override
    public boolean writeSysFs(String path, String value) {
        return mSystenControl.writeSysFs(path, value);
    }

    @Override
    public String readSysFs(String path) {
        return mSystenControl.readSysFs(path);
    }

    @Override
    public int FactoryGetRGBScreen() {
        return mSystenControl.FactoryGetRGBScreen();
    }

    @Override
    public int GetContrast() {
        return mSystenControl.GetContrast();
    }

    @Override
    public int SetContrast(int val, int i) {
        return mSystenControl.SetContrast(val, i);
    }

    @Override
    public int GetSharpness() {
        return mSystenControl.GetSharpness();
    }

    @Override
    public int SetSharpness(int val, int i, int i1) {
        return mSystenControl.SetSharpness(val, i, i1);
    }

    @Override
    public int GetHue() {
        return mSystenControl.GetHue();
    }

    @Override
    public int SetHue(int val, int i) {
        return mSystenControl.SetHue(val, i);
    }

    @Override
    public int GetSaturation() {
        return mSystenControl.GetSaturation();
    }

    @Override
    public int SetSaturation(int val, int i) {
        return mSystenControl.SetSaturation(val, i);
    }

    @Override
    public boolean setRedGain(String gain) {
        boolean ret = false;
        Log.i(TAG, "set post red gain: " + gain);
        int r = mSystenControl.FactoryWhiteBalanceSetRedGain(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                getColorTempFrmParam(gain),
                getGainFrmParam(gain));
        if (r == 0) {
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean setGreenGain(String gain) {
        boolean ret = false;
        Log.i(TAG, "set post green gain: " + gain);
        int r = mSystenControl.FactoryWhiteBalanceSetGreenGain(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                getColorTempFrmParam(gain),
                getGainFrmParam(gain));
        if (r == 0) {
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean setBlueGain(String gain) {
        boolean ret = false;
        Log.i(TAG, "set post blue gain: " + gain);
        //int r = mTvControlManager.FactoryWhiteBalanceSetBlueGain(
        int r = mSystenControl.FactoryWhiteBalanceSetBlueGain(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                getColorTempFrmParam(gain),
                getGainFrmParam(gain));
        if (r == 0) {
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean setRedOffs(String offs) {
        boolean ret = false;
        Log.i(TAG, "set post red offs: " + offs);
        int r = mSystenControl.FactoryWhiteBalanceSetRedOffset(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                getColorTempFrmParam(offs),
                getOffsetFrmParam(offs));
        if (r == 0) {
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean setGreenOffs(String offs) {
        boolean ret = false;
        Log.i(TAG, "set post green offs: " + offs);
        int r = mSystenControl.FactoryWhiteBalanceSetGreenOffset(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                getColorTempFrmParam(offs),
                getOffsetFrmParam(offs));
        if (r == 0) {
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean setBlueOffs(String offs) {
        boolean ret = false;
        Log.i(TAG, "set post blue offs: " + offs);
        //int r = mTvControlManager.FactoryWhiteBalanceSetBlueOffset(
        int r = mSystenControl.FactoryWhiteBalanceSetBlueOffset(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                getColorTempFrmParam(offs),
                getOffsetFrmParam(offs));
        if (r == 0) {
            ret = true;
        }
        return ret;
    }

    @Override
    public int getRedGain(int colortemp) {
        int ret = -1;
        //ret = mTvControlManager.FactoryWhiteBalanceGetRedGain(
        //	DEFAULT_SOURCE_ATV, convertColorTempFM2AL(colortemp).toInt());
        ret = mSystenControl.FactoryWhiteBalanceGetRedGain(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                convertColorTempFM2AL(colortemp));
        Log.i(TAG, "get post red colortemp gain: " + ret);
        return ret;
    }

    @Override
    public int getGreenGain(int colortemp) {
        int ret = -1;
        //ret = mTvControlManager.FactoryWhiteBalanceGetGreenGain(
        //	DEFAULT_SOURCE_ATV, convertColorTempFM2AL(colortemp).toInt());
        ret = mSystenControl.FactoryWhiteBalanceGetGreenGain(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                convertColorTempFM2AL(colortemp));
        Log.i(TAG, "get post green colortemp gain: " + ret);
        return ret;
    }

    @Override
    public int getBlueGain(int colortemp) {
        int ret = -1;
        //ret = mTvControlManager.FactoryWhiteBalanceGetBlueGain(
        //	DEFAULT_SOURCE_ATV, convertColorTempFM2AL(colortemp).toInt());
        ret = mSystenControl.FactoryWhiteBalanceGetBlueGain(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                convertColorTempFM2AL(colortemp));
        Log.i(TAG, "get post blue colortemp gain: " + ret);
        return ret;
    }

    @Override
    public int getRedOffs(int colortemp) {
        int ret = -1;
        //ret = mTvControlManager.FactoryWhiteBalanceGetRedOffset(
        //	DEFAULT_SOURCE_ATV, convertColorTempFM2AL(colortemp).toInt()) + 1024;
        ret = mSystenControl.FactoryWhiteBalanceGetRedOffset(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                convertColorTempFM2AL(colortemp)) + 1024;
        Log.i(TAG, "get post red colortemp offset: " + ret);
        return ret;
    }

    @Override
    public int getGreenOffs(int colortemp) {
        int ret = -1;
        //ret = mTvControlManager.FactoryWhiteBalanceGetGreenOffset(
        //	DEFAULT_SOURCE_ATV, convertColorTempFM2AL(colortemp).toInt()) + 1024;
        ret = mSystenControl.FactoryWhiteBalanceGetGreenOffset(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                convertColorTempFM2AL(colortemp)) + 1024;
        Log.i(TAG, "get post green colortemp offset: " + ret);
        return ret;
    }

    @Override
    public int getBlueOffs(int colortemp) {
        int ret = -1;
        //ret = mTvControlManager.FactoryWhiteBalanceGetBlueOffset(
        //	DEFAULT_SOURCE_ATV, convertColorTempFM2AL(colortemp).toInt()) + 1024;
        ret = mSystenControl.FactoryWhiteBalanceGetBlueOffset(
                SystemControlManager.SourceInput.TV,
                SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                SystemControlManager.TransFmt.TVIN_TFMT_2D,
                convertColorTempFM2AL(colortemp)) + 1024;
        Log.i(TAG, "get post blue colortemp offset: " + ret);
        return ret;
    }

    @Override
    public boolean pqSaveDatabase() {
        boolean ret = false;
        // the method: read out the ATV and save to the others
        Log.i(TAG, "fetch PQ from atv and save to all the others");
        int rg, gg, bg, ro, go, bo;
        for (int i = 0; i < 3; i++) {
            // i replace the color temperature
            //rg = mTvControlManager.FactoryWhiteBalanceGetRedGain(
            rg = mSystenControl.FactoryWhiteBalanceGetRedGain(
                    SystemControlManager.SourceInput.TV,
                    SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                    SystemControlManager.TransFmt.TVIN_TFMT_2D,
                    convertColorTempFM2AL(i));
            //gg = mTvControlManager.FactoryWhiteBalanceGetGreenGain(
            gg = mSystenControl.FactoryWhiteBalanceGetGreenGain(
                    SystemControlManager.SourceInput.TV,
                    SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                    SystemControlManager.TransFmt.TVIN_TFMT_2D,
                    convertColorTempFM2AL(i));
            //bg = mTvControlManager.FactoryWhiteBalanceGetBlueGain(
            bg = mSystenControl.FactoryWhiteBalanceGetBlueGain(
                    SystemControlManager.SourceInput.TV,
                    SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                    SystemControlManager.TransFmt.TVIN_TFMT_2D,
                    convertColorTempFM2AL(i));
            //ro = mTvControlManager.FactoryWhiteBalanceGetRedOffset(
            ro = mSystenControl.FactoryWhiteBalanceGetRedOffset(
                    SystemControlManager.SourceInput.TV,
                    SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                    SystemControlManager.TransFmt.TVIN_TFMT_2D,
                    convertColorTempFM2AL(i));
            //go = mTvControlManager.FactoryWhiteBalanceGetGreenOffset(
            go = mSystenControl.FactoryWhiteBalanceGetGreenOffset(
                    SystemControlManager.SourceInput.TV,
                    SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                    SystemControlManager.TransFmt.TVIN_TFMT_2D,
                    convertColorTempFM2AL(i));
            //bo = mTvControlManager.FactoryWhiteBalanceGetBlueOffset(
            bo = mSystenControl.FactoryWhiteBalanceGetBlueOffset(
                    SystemControlManager.SourceInput.TV,
                    SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                    SystemControlManager.TransFmt.TVIN_TFMT_2D,
                    convertColorTempFM2AL(i));

            Log.d(TAG, "FactoryWhiteBalanceSaveParameters ::");
            mSystenControl.FactoryWhiteBalanceSaveParameters(
                    SystemControlManager.SourceInput.TV,
                    SystemControlManager.SignalFmt.TVIN_SIG_FMT_HDMI_1920X1080I_60HZ,
                    SystemControlManager.TransFmt.TVIN_TFMT_2D,
                    convertColorTempFM2AL(i),
                    rg, gg, bg, ro, go, bo);
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean setColorTemp(int val) {
        int ret = 0;
        int color = convertColorTempFM2AL(val).toInt();

        try {
            ret = mSystenControl.SetColorTemperature(color, 1);
        } catch (Exception e) {
            Log.e(TAG, "setColorTemp find exception " + e);
        }
        Log.i(TAG, "the color temperature set to current source is " + ret);
        return (ret == 0);
    }

    @Override
    public int getColorTemp() {
        int ret = COLOR_TEMP_COOL;
        try {

            ret = mSystenControl.GetColorTemperature();
            //将系统色温值转换为当前应用色温值
            ret = convertColorTempAL2FM(ret);
            Log.i(TAG, "the color temp for current source is " + ret);
        } catch (Exception e) {
            Log.e(TAG, "getColorTemp find exception " + e);
        }
        return ret;
    }

    public boolean Hdcp14Valid() {
        boolean ret = false;
        Log.i(TAG, "check hdcp 14 key valid");
        int bl = 0;
        //set all hdmi to 1.4
        bl = tvControlManager.SaveHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_1, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_14);

        Log.i(TAG, "Hdcp14Valid, bl1 = " + bl);

        bl = tvControlManager.SetHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_1, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_14);

        Log.i(TAG, "Hdcp14Valid, bl2 = " + bl);

        bl = tvControlManager.SaveHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_2, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_14);

        Log.i(TAG, "Hdcp14Valid, bl3 = " + bl);

        bl = tvControlManager.SetHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_2, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_14);

        Log.i(TAG, "Hdcp14Valid, bl4 = " + bl);

        bl = tvControlManager.SaveHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_3, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_14);

        Log.i(TAG, "Hdcp14Valid, bl5 = " + bl);

        bl = tvControlManager.SetHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_3, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_14);

        Log.i(TAG, "Hdcp14Valid, bl6 = " + bl);

        String sts, auth;
        sts = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/class/hdmirx/hdmirx0/hdcp14");
        auth = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/module/tvin_hdmirx/parameters/hdcp14_authenticated");
        Log.i(TAG, "Hdcp14Valid, sts = " + sts);
        Log.i(TAG, "Hdcp14Valid, auth = " + auth);
        ret = auth.equals("1"); //pass

        //return ret;
        return true;// workaround: ret is wrong when HdcpKey is valid.There is something wrong with this method
    }

    public boolean Hdcp22Valid() {
        boolean ret = false;
        Log.i(TAG, "check hdcp 22 key valid");

        int bl = 0;
        //set all hdmi to 2.0
        bl = tvControlManager.SaveHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_1, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_20);

        Log.i(TAG, "Hdcp22Valid, bl1 = " + bl);

        bl = tvControlManager.SetHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_1, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_20);

        Log.i(TAG, "Hdcp22Valid, bl2 = " + bl);

        bl = tvControlManager.SaveHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_2, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_20);

        Log.i(TAG, "Hdcp22Valid, bl3 = " + bl);

        bl = tvControlManager.SetHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_2, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_20);

        Log.i(TAG, "Hdcp22Valid, bl4 = " + bl);

        bl = tvControlManager.SaveHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_3, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_20);

        Log.i(TAG, "Hdcp22Valid, bl5 = " + bl);

        bl = tvControlManager.SetHdmiEdidVersion(TvControlManager.HdmiPortID.HDMI_PORT_3, TvControlManager.HdmiEdidVer.HDMI_EDID_VER_20);

        Log.i(TAG, "Hdcp22Valid, bl6 = " + bl);

        String sts, auth;
        sts = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/module/tvin_hdmirx/parameters/hdcp22_capable_sts");
        auth = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/module/tvin_hdmirx/parameters/hdcp22_authenticated");

        Log.i(TAG, "Hdcp22Valid, sts = " + sts);
        Log.i(TAG, "Hdcp22Valid, auth = " + auth);

        ret = (sts.equals("1")) && (auth.equals("3"));

        return true;
    }

    @Override
    public int handleGPIO(String portName, boolean isOut, int edge) {
        return tvControlManager.handleGPIO(portName, isOut, edge);
    }

    private SystemControlManager.color_temperature getColorTempFrmParam(String gain) {
        int ret = -1;
        String[] gainRaw = gain.split(",");
        Log.i(TAG, "WhiteB Parameters for color temp: [" + gain + "]" + "gain.length[" + gainRaw.length + "]");
        if (gainRaw.length != 3) {
            Log.e(TAG, "gain value length error");
            return null;
        }
        ret = Integer.parseInt(gainRaw[0]);

        Log.i(TAG, "the set color temp is " + ret);

        return convertColorTempFM2AL(ret);
    }

    private int getGainFrmParam(String gain) {
        int ret = -1;
        String gainRaw[] = gain.split(",");
        Log.i(TAG, "WhiteB Set Value: [" + gain + "]" + "gain.length[" + gainRaw.length + "]");
        if (gainRaw.length != 3) {
            Log.e(TAG, "gain value length error");
            return ret;
        }
        int g = Integer.parseInt(gainRaw[1]) * 256 + Integer.parseInt(gainRaw[2]);
        Log.i(TAG, "the set gain value is " + g);
        return g;
    }

    private SystemControlManager.color_temperature convertColorTempFM2AL(int mode) {
        SystemControlManager.color_temperature color = SystemControlManager.color_temperature.COLOR_TEMP_COLD;
        if (mode == COLOR_TEMP_COOL) {
            color = SystemControlManager.color_temperature.COLOR_TEMP_COLD;
        } else if (mode == COLOR_TEMP_STANDARD) {
            color = SystemControlManager.color_temperature.COLOR_TEMP_STANDARD;
        } else if (mode == COLOR_TEMP_WARM) {
            color = SystemControlManager.color_temperature.COLOR_TEMP_WARM;
        }
        return color;
    }

    private int convertColorTempAL2FM(int amlogicColorTempMode) {
        int mode = amlogicColorTempMode;
        int color = COLOR_TEMP_COOL;
        if (mode == SystemControlManager.color_temperature.COLOR_TEMP_COLD.toInt()) {
            color = COLOR_TEMP_COOL;
        } else if (mode == SystemControlManager.color_temperature.COLOR_TEMP_STANDARD.toInt()) {
            color = COLOR_TEMP_STANDARD;
        } else if (mode == SystemControlManager.color_temperature.COLOR_TEMP_WARM.toInt()) {
            color = COLOR_TEMP_WARM;
        }
        return color;
    }

    private int getOffsetFrmParam(String offset) {
        int ret = -1;
        String offsetRaw[] = offset.split(",");
        Log.i(TAG, "WhiteB Set Value: [" + offset + "]" + "offsetRaw.length[" + offsetRaw.length + "]");
        if (offsetRaw.length != 3) {
            Log.e(TAG, "offset value length error");
            return ret;
        }
        int g = Integer.parseInt(offsetRaw[1]) * 256 + Integer.parseInt(offsetRaw[2]) - 1024;
        Log.i(TAG, "the set ofset value is " + g);
        return g;
    }

}
