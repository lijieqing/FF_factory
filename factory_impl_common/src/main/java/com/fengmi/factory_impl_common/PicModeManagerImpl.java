package com.fengmi.factory_impl_common;

import android.content.Context;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.PicModeManagerInterf;
import com.fengmi.factory_test_interf.sdk_utils.ShellUtil;


public class PicModeManagerImpl implements PicModeManagerInterf {

    private Context mContext;


    PicModeManagerImpl(Context context) {
        mContext = context;
    }

    public int picGetBacklight() {
        int ret = 0;
        ret = SDKManager.getFengManagerInterf().getBacklight();
        return ret;
    }

    public boolean picSetBacklight(int val) {
        boolean ret = false;
        ret = SDKManager.getFengManagerInterf().setBacklight(val);
        return ret;
    }

    public int picGetBrightness() {
        int ret = -1;
        return ret;
    }

    public boolean picSetBrightness(int val) {
        boolean ret = false;
        return ret;
    }

    public int picGetContrast() {
        int ret = -1;
        ret = getContrast();
        return ret;
    }

    public boolean picSetContrast(int val) {
        boolean ret = false;
        ret = setContrast(val);
        return ret;
    }

    public int picGetSharpness() {
        int ret = -1;
        ret = getSharpness();
        return ret;
    }

    public boolean picSetSharpness(int val) {
        boolean ret = false;
        ret = setSharpness(val);
        return ret;
    }

    public int picGetHue() {
        int ret = 0;
        ret = getHue();
        return ret;
    }

    public boolean picSetHue(int val) {
        boolean ret = false;
        ret = setHue(val);
        return ret;
    }
    //PQ API

    public int picGetSatuation() {
        int ret = -1;
        ret = getSaturation();
        return ret;
    }

    public boolean picSetSatuation(int val) {
        boolean ret = false;
        ret = setSaturation(val);
        return ret;
    }

    public int picGetColorTemp() {
        int ret = 0;
        ret = SDKManager.getAmlogicManagerInterf().getColorTemp();
        return ret;
    }

    public boolean picSetColorTemp(int val) {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().setColorTemp(val);
        return ret;
    }

    public boolean picModeSet(int stat) {
        boolean ret = false;
        ret = setSceneMode(stat);
        return ret;
    }

    public int picModeGet() {
        int ret = -1;
        ret = getSceneMode();
        return ret;
    }

    public boolean picModeReset() {
        boolean ret = false;
        ret = resetSceneMode();
        return ret;
    }

    public boolean picPanelSelect(String sel) {
        boolean ret = false;
        //ret = panelSet(sel);
        return ret;
    }

    public int picGetPostRedGain(int colortemp) {
        int ret = -1;
        ret = SDKManager.getAmlogicManagerInterf().getRedGain(colortemp);
        return ret;
    }

    public boolean picSetPostRedGain(String gain) {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().setRedGain(gain);
        return ret;
    }

    public int picGetPostGreenGain(int colortemp) {
        int ret = -1;
        ret = SDKManager.getAmlogicManagerInterf().getGreenGain(colortemp);
        return ret;
    }

    public boolean picSetPostGreenGain(String gain) {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().setGreenGain(gain);
        return ret;
    }

    public int picGetPostBlueGain(int colortemp) {
        int ret = -1;
        ret = SDKManager.getAmlogicManagerInterf().getBlueGain(colortemp);
        return ret;
    }

    public boolean picSetPostBlueGain(String gain) {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().setBlueGain(gain);
        return ret;
    }

    public int picGetPostRedOffset(int colortemp) {
        int ret = -1;
        ret = SDKManager.getAmlogicManagerInterf().getRedOffs(colortemp);
        return ret;
    }

    public boolean picSetPostRedOffset(String offset) {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().setRedOffs(offset);
        return ret;
    }

    public int picGetPostGreenOffset(int colortemp) {
        int ret = -1;
        ret = SDKManager.getAmlogicManagerInterf().getGreenOffs(colortemp);
        return ret;
    }

    public boolean picSetPostGreenOffset(String offset) {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().setGreenOffs(offset);
        return ret;
    }

    public int picGetPostBlueOffset(int colortemp) {
        int ret = -1;
        ret = SDKManager.getAmlogicManagerInterf().getBlueOffs(colortemp);
        return ret;
    }

    public boolean picSetPostBlueOffset(String offset) {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().setBlueOffs(offset);
        return ret;
    }

    @Override
    public boolean picGeneralWBSave() {
        return false;
    }

    @Override
    public boolean picGeneralWBGain(String gain) {
        return false;
    }

    @Override
    public boolean picGeneralWBOffset(String offset) {
        return false;
    }

    public boolean picTransPQDataToDB() {
        boolean ret = false;
        ret = SDKManager.getAmlogicManagerInterf().pqSaveDatabase();
        return ret;
    }

    public boolean picSwitchPicUserMode() {
        boolean ret = false;
        ret = switchPicUserMode();
        return ret;

    }

    public boolean byPassPQ(String bypass) {
        boolean ret = false;
        ret = setPQbypass(bypass);
        return ret;
    }

    public boolean picEnablePQ() {
        boolean ret = false;
        try {
            ret = setPQStatus(true);
        } catch (Exception e) {
        }
        return ret;
    }

    public boolean picDisablePQ() {
        boolean ret = false;
        try {
            ret = setPQStatus(false);
        } catch (Exception e) {
        }
        return ret;
    }

    private boolean setPQStatus(boolean enable) {
        if (enable) {
            SDKManager.getAndroidOSManagerInterf().setSystemProperty(KEY_PROPERTY_PQ, "255");
        } else {
            SDKManager.getAndroidOSManagerInterf().setSystemProperty(KEY_PROPERTY_PQ, "300");
        }
        int temp = SDKManager.getAmlogicManagerInterf().getColorTemp();
        SDKManager.getAmlogicManagerInterf().setColorTemp(temp);
        return true;
    }

    //unused
    private boolean switchPicUserMode() {
        boolean ret = false;
        //ret = mPicSetManager.setSceneMode(mPicSetManager.SCENE_MODE_USER);
        Log.i(TAG, "the scene mode setting result is " + ret);
        return ret;
    }

    private int getContrast() {
        int ret = 50;
        try {

            ret = SDKManager.getAmlogicManagerInterf().GetContrast();
        } catch (Exception e) {
            Log.e(TAG, "getContrast find exception " + e);
        }
        Log.i(TAG, "the contrast for current source is " + ret);
        return ret;
    }

    private boolean setContrast(int val) {
        int ret = 0;
        if (val > 100 || val < 0) {
            Log.i(TAG, "setContrast ,but param is wrong");
            return false;
        }

        try {
            ret = SDKManager.getAmlogicManagerInterf().SetContrast(val, 1);
        } catch (Exception e) {
            Log.e(TAG, "setContrast find exception " + e);
        }
        Log.i(TAG, "the contrast set to current source is " + ret);
        return (ret == 0);
    }

    private int getSharpness() {
        int ret = 50;
        try {

            ret = SDKManager.getAmlogicManagerInterf().GetSharpness();
        } catch (Exception e) {
            Log.e(TAG, "getSharpness find exception " + e);
        }
        Log.i(TAG, "the sharpness for current source is " + ret);
        return ret;
    }

    private boolean setSharpness(int val) {
        int ret = 0;
        if (val > 100 || val < 0) {
            Log.i(TAG, "setSharpness ,but param is wrong");
            return false;
        }

        try {
            ret = SDKManager.getAmlogicManagerInterf().SetSharpness(val, 1, 1);
        } catch (Exception e) {
            Log.e(TAG, "setSharpness find exception " + e);
        }
        Log.i(TAG, "the sharpness set to current source is " + ret);
        return (ret == 0 ? true : false);
    }

    private int getHue() {
        int ret = 50;
        try {

            ret = SDKManager.getAmlogicManagerInterf().GetHue();
        } catch (Exception e) {
            Log.e(TAG, "getHue find exception " + e);
        }
        Log.i(TAG, "the hue for current source is " + ret);
        return ret;
    }

    private boolean setHue(int val) {
        int ret = 0;
        if (val > 100 || val < 0) {
            Log.i(TAG, "setHue ,but param is wrong");
            return false;
        }

        //TVInSignalInfo.SignalFmt fmt = TvControlManager.getInstance().GetCurrentSignalInfo().sigFmt;
        try {
            ret = SDKManager.getAmlogicManagerInterf().SetHue(val, 1);
        } catch (Exception e) {
            Log.e(TAG, "getHue find exception " + e);
        }
        Log.i(TAG, "the hue set to current source is " + ret);
        return (ret == 0 ? true : false);
    }

    private int getSaturation() {
        int ret = 50;
        try {

            ret = SDKManager.getAmlogicManagerInterf().GetSaturation();
        } catch (Exception e) {
            Log.e(TAG, "getSaturation exception " + e);
        }
        Log.i(TAG, "the saturation for current source is " + ret);
        return ret;
    }

    private boolean setSaturation(int val) {
        if (val > 100 || val < 0) {
            Log.i(TAG, "setSaturation ,but param is wrong");
            return false;
        }
        int ret = 0;

        try {
            ret = SDKManager.getAmlogicManagerInterf().SetSaturation(val, 1);
        } catch (Exception e) {
            Log.e(TAG, "getSaturation exception " + e);
        }
        Log.i(TAG, "the saturation set to current source is " + ret);
        return (ret == 0);
    }

    //unused
    private boolean setSceneMode(int stat) {
        boolean ret = false;
        //ret = mPicSetManager.setSceneMode(stat);
        Log.i(TAG, "the scene mode set to current source is " + ret);
        return ret;
    }

    //unused
    private int getSceneMode() {
        int ret = -1;
        //ret = mPicSetManager.getSceneMode();
        Log.i(TAG, "the scene mode for current source is " + ret);
        return ret;
    }

    //unused
    private boolean resetSceneMode() {
        boolean ret = false;
        //ret = mPicSetManager.setSceneMode(mPicSetManager.SCENE_MODE_USER);
        Log.i(TAG, "the scene mode reset operation is " + ret);
        return ret;
    }


    private boolean setPQbypass(String pqbypass) {
        boolean ret = false;
        Log.i(TAG, "setPQbypass [" + pqbypass + "]");
        ret = ShellUtil.echoEntry(PQBYPASS_NODE, pqbypass);
        return ret;
    }

}
