package com.fengmi.factory_impl_common;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.AudioTestManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.FengManagerInterf;

import java.io.IOException;

public class AudioTestManagerImpl implements AudioTestManagerInterf {
    private Context mContext;
    private AudioManager mAudioManager;

    AudioTestManagerImpl(Context context) {
        mContext = context;
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public int audioGetSoundVolume() {
        return getVolume();
    }

    @Override
    public boolean audioSetSoundVolume(int val) {
        return setVolume(val);
    }

    @Override
    public int audioGetSoundBalance() {
        return 0;
    }

    @Override
    public boolean audioSetSoundBalance(int val) {
        return false;
    }

    @Override
    public int audioGetSoundMode() {
        return 0;
    }

    @Override
    public boolean audioSetSoundMode(int val) {
        return false;
    }

    @Override
    public boolean audioResetSoundMode() {
        return false;
    }

    @Override
    public boolean audioSwitchSpdif(int stat) {
        boolean ret = false;
        ret = setSpdifState(stat);
        return ret;
    }

    @Override
    public boolean audioSwitchSpeaker(boolean disable) {
        boolean ret = false;
        ret = setSpeakerState(disable);
        return ret;
    }

    @Override
    public boolean audioSetSoundMute() {
        return false;
    }

    @Override
    public boolean closeDTS_DOLBY() {
        return false;
    }

    @Override
    public boolean audioSwitchArc(int stat) {
        boolean ret = false;
        ret = setArcState(stat);
        return ret;
    }

    @Override
    public boolean audioOutputMode(int stat) {
        if (!isAcceptedMode(stat)) {
            return false;
        }
        int mode = SDKManager.getFengManagerInterf().transOutputMode(stat);
        boolean ret = true;
        Log.i(TAG, "set output mode is " + mode);
        ret = SDKManager.getFengManagerInterf().setAudioOutputDevice(mode);
        return ret;
    }

    @Override
    public boolean speakerswitch(int stat) {
        boolean ret = true;
        int i;
        String[][] channel = new String[6][2];
        String command = null;

        channel[0][0] = "17";
        channel[1][0] = "33";
        channel[2][0] = "25";
        channel[3][0] = "24";
        channel[4][0] = "32";
        channel[5][0] = "16";

        for (i = 0; i < 6; i++) {
            if ((stat & (1 << i)) > 0)
                channel[i][1] = "1";
            else
                channel[i][1] = "0";
            command = "/system/bin/tinymix " + channel[i][0] + " " + channel[i][1];
            Log.i(TAG, "command is " + command);
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    public boolean closeDap() {
        return false;
    }

    @Override
    public boolean audioSwitchLineOut(boolean state) {
        boolean ret = false;
        ret = setLineoutState(state);
        return ret;
    }

    @Override
    public int audioGetMaxSoundVolume() {
        return getMaxVolume();
    }

    @Override
    public boolean audioSetSoundEffectMode(int mode) {
        return false;
    }

    @Override
    public int audioGetSoundEffectMode() {
        return 0;
    }

    @Override
    public boolean adjustVolume(String state) {
        return SDKManager.getAndroidOSManagerInterf().adjustVolume(state);
    }

    /*===========================================local functions=====================*/
    private boolean setVolume(int val) {
        boolean ret = false;
        Log.i(TAG, "set voice volume is " + val);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, val, 0);
        ret = true;
        return ret;
    }

    private int getVolume() {
        int ret = -1;
        ret = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.i(TAG, "get voice volume is " + ret);
        return ret;
    }

    private int getMaxVolume() {
        int ret = 0;
        ret = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.i(TAG, "getMaxVolume:" + ret);
        return ret;
    }

    private boolean setSpdifState(int st) {
        Log.i(TAG, "set spidf state is " + st);
        if (st == 0) {
            return audioOutputMode(OUTMODE_SPDIF);
        }
        //disable SPDIF Only effective,when target device is SPDIF
        if (!isTargetOutputDevice(FengManagerInterf.OUTMODE_SPDIF)) {
            return false;
        }
        return audioOutputMode(OUTMODE_LINEOUT);
    }

    private boolean setArcState(int st) {
        Log.i(TAG, "set HDMI ARC state is " + st);
        if (st == 0) {
            return audioOutputMode(OUTMODE_ARC);
        }
        //disable ARC Only effective,when target device is ARC
        if (!isTargetOutputDevice(FengManagerInterf.OUTMODE_ARC)) {
            return false;
        }
        return audioOutputMode(OUTMODE_LINEOUT);
    }

    private boolean setSpeakerState(boolean disable) {
        Log.i(TAG, "set speaker disable is " + disable);

        if (!disable) {
            return audioOutputMode(OUTMODE_SPEAKER);
        }
        boolean ret = false;
        ret = SDKManager.getFengManagerInterf().setCurAudioMute(getMuteState(disable));
        return ret;
    }

    private boolean setLineoutState(boolean disable) {
        Log.i(TAG, "set line out disable:" + disable);

        if (!disable) {
            return audioOutputMode(OUTMODE_LINEOUT);
        } else {
            return audioOutputMode(OUTMODE_SPEAKER);
        }
    }

    private boolean isTargetOutputDevice(int targetOutput) {
        int outputDevice = SDKManager.getFengManagerInterf().getOutputDevice();
        Log.i(TAG, "current Audio outputDevice:" + outputDevice);
        return targetOutput == outputDevice;
    }

    private int getMuteState(boolean audioEnable) {
        return audioEnable ? 1 : 0;
    }

    private boolean isAcceptedMode(int factoryMode) {
        if (factoryMode >= 0 && factoryMode <= 3) {
            return true;
        }
        return false;
    }

}
