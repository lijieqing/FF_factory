package com.fengmi.factory_impl_dependency;

import android.content.Context;
import android.util.Log;

import com.fengmi.factory_test_interf.sdk_interf.AFCallback;
import com.fengmi.factory_test_interf.sdk_interf.FengManagerInterf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import fengmi.hardware.BootenvManager;
import fengmi.hardware.KeystoneManager;
import fengmi.hardware.MotorFocusCallback;
import fengmi.hardware.MotorManager;
import fengmi.hardware.SoundManager;
import fengmi.hardware.display.ProjectorManager;

public class FengManagerImpl implements FengManagerInterf {
    private SoundManager soundManager;
    private KeystoneManager keystoneManager;
    private MotorManager motorManager;
    private ProjectorManager projectorManager;
    private BootenvManager bootenvManager;

    FengManagerImpl(Context context) {
        this.soundManager = SoundManager.getInstance(context);
        this.keystoneManager = KeystoneManager.getInstance(context);
        this.motorManager = MotorManager.getInstance(context);
        this.projectorManager = ProjectorManager.getInstance(context);
        this.bootenvManager = BootenvManager.getInstance(context);
    }

    @Override
    public int transOutputMode(int factoryMode) {
        if (factoryMode == 0) {
            return SoundManager.SOUND_OUTPUT_DEVICE_INTERNAL_SPEAKER;
        } else if (factoryMode == 1) {
            return SoundManager.SOUND_OUTPUT_DEVICE_SPDIF;
        } else if (factoryMode == 2) {
            return SoundManager.SOUND_OUTPUT_DEVICE_HDMI_ARC;
        } else if (factoryMode == 3) {
            return SoundManager.SOUND_OUTPUT_DEVICE_HEADSET;
        }
        return SoundManager.SOUND_OUTPUT_DEVICE_UNKNOWN;
    }

    @Override
    public int getOutputDevice() {
        return soundManager.getOutputDevice();
    }

    @Override
    public boolean setCurAudioMute(int mute) {
        return soundManager.setCurAudioMute(mute);
    }

    @Override
    public boolean setAudioOutputDevice(int deviceID) {
        return soundManager.setAudioOutputDevice(deviceID);
    }

    @Override
    public int SetKeystoneSelectMode(int mode) {
        return keystoneManager.SetKeystoneSelectMode(mode);
    }

    @Override
    public int SetKeystoneSet(int select, int dir, int step) {
        return keystoneManager.SetKeystoneSet(select, dir, step);
    }

    @Override
    public int SetKeystoneInit() {
        return keystoneManager.SetKeystoneInit();
    }

    @Override
    public int SetKeystoneReset() {
        return keystoneManager.SetKeystoneReset();
    }

    @Override
    public int SetKeystoneSave() {
        return keystoneManager.SetKeystoneSave();
    }

    @Override
    public int SetKeystoneLoad() {
        return keystoneManager.SetKeystoneLoad();
    }

    @Override
    public int SetKeystoneCancel() {
        return keystoneManager.SetKeystoneCancel();
    }

    @Override
    public boolean setMotorConfig(int direction, int speed, int period) {
        return motorManager.setMotorConfig(direction, speed, period);
    }

    @Override
    public boolean setMotorStart() {
        return motorManager.setMotorStart();
    }

    @Override
    public boolean setMotorEventCallback(Object callback) {
        if (callback instanceof MotorFocusCallback) {
            return motorManager.setMotorEventCallback((MotorFocusCallback) callback);
        }
        return false;
    }

    @Override
    public boolean unsetMotorEventCallback(Object callback) {
        if (callback instanceof MotorFocusCallback) {
            return motorManager.unsetMotorEventCallback((MotorFocusCallback) callback);
        }
        return false;
    }

    @Override
    public boolean startAutoFocus() {
        return motorManager.startAutoFocus();
    }

    @Override
    public boolean stopAutoFocus() {
        return motorManager.stopAutoFocus();
    }

    @Override
    public int startAutoFocusCheck() {
        return motorManager.startAutoFocusCheck();
    }

    @Override
    public int tofAutoFocusCheck(int arg) {
        return motorManager.tofAutoFocusCheck(arg);
    }

    @Override
    public int tofAutoFocus(int step) {
        return motorManager.tofAutoFocus(step);
    }

    @Override
    public int getBacklight() {
        int ret = -1;
        int bri = projectorManager.getProjectorBrightness();
        if (bri == ProjectorManager.IMAGE_BRIGHTNESS_MODE_POWERSAVE) {
            ret = 1;
        } else if (bri == ProjectorManager.IMAGE_BRIGHTNESS_MODE_NORMAL) {
            ret = 2;
        } else if (bri == ProjectorManager.IMAGE_BRIGHTNESS_MODE_HIGHLIGHT) {
            ret = 3;
        }
        Log.i(TAG, "the backlight for current source is " + ret);
        return ret;
    }

    @Override
    public boolean setBacklight(int val) {
        boolean ret = false;
        Log.i(TAG, "SetProjectorBacklightMode " + val);
        if (val == 1) {
            ret = projectorManager.setProjectorBrightness(ProjectorManager.IMAGE_BRIGHTNESS_MODE_POWERSAVE);
        } else if (val == 2) {
            ret = projectorManager.setProjectorBrightness(ProjectorManager.IMAGE_BRIGHTNESS_MODE_NORMAL);
        } else if (val == 3) {
            ret = projectorManager.setProjectorBrightness(ProjectorManager.IMAGE_BRIGHTNESS_MODE_HIGHLIGHT);
        }
        Log.i(TAG, "the backlight set to current source is " + ret);
        return ret;
    }

    @Override
    public String GetFlashBuildVersion() {
        return projectorManager.getProjectorInfo().GetFlashBuildVersion();
    }

    @Override
    public boolean setProjectorOrient(int mode) {
        return projectorManager.setProjectorOrient(mode);
    }

    @Override
    public int getDisplayOrientation() {
        return projectorManager.getDisplayOrientation();
    }

    @Override
    public int getProjectorEventStatus(int type) {
        return projectorManager.getProjectorEventStatus(type);
    }

    @Override
    public String GetSerialNo() {
        return projectorManager.getProjectorInfo().GetSerialNo();
    }

    @Override
    public boolean setAutoBrightnessByIR(boolean enable) {
        return projectorManager.setAutoBrightnessByIR(enable);
    }

    @Override
    public boolean getAutoBrightnessByIR() {
        return projectorManager.getAutoBrightnessByIR();
    }

    @Override
    public int setBootEnv(String key, String value) {
        return bootenvManager.setBootEnv(key, value);
    }

    @Override
    public String getBootEnv(String key, String def) {
        return bootenvManager.getBootEnv(key, def);
    }

    /**
     * AF 回程差监听回调
     */
    public static class AFCheckCallback extends MotorFocusCallback {
        private CountDownLatch latch;
        private int offset = -1;

        public AFCheckCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public int eventCallback(MotorEvent event) {
            int status = event.status;
            Log.d(TAG, "AFCheckCallback event.status=" + status);

            if (event.type == 11) {
                offset = status;

                latch.countDown();
            }
            return 0;
        }

        public int getOffset() {
            return offset;
        }
    }

    /**
     * 自动对焦回调
     */
    public static class MyCallback extends MotorFocusCallback {
        private List<AFCallback> afCallbackList = new ArrayList<>();

        @Override
        public int eventCallback(MotorEvent event) {
            Log.d(TAG, "IMotorFocusCallback :: status = " + event.status);
            Log.d(TAG, "IMotorFocusCallback :: type = " + event.type);
            for (AFCallback afc : afCallbackList) {
                if (afc != null && AUTO_FOCUS_START == event.type) {
                    afc.onAFStart();
                }
                if (afc != null && AUTO_FOCUS_FINISH == event.type) {
                    afc.onAFFinish();
                }
            }
            return 0;
        }

        public void addAFCallback(AFCallback afc) {
            afCallbackList.add(afc);
        }

        public void removeAFCallback(AFCallback afc) {
            afCallbackList.remove(afc);
        }
    }
}
