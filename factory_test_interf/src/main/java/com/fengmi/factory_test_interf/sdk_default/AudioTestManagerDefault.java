package com.fengmi.factory_test_interf.sdk_default;

import com.fengmi.factory_test_interf.sdk_interf.AudioTestManagerInterf;

public class AudioTestManagerDefault implements AudioTestManagerInterf {
    @Override
    public int audioGetSoundVolume() {
        return 0;
    }

    @Override
    public boolean audioSetSoundVolume(int val) {
        return false;
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
        return false;
    }

    @Override
    public boolean audioSwitchSpeaker(boolean enable) {
        return false;
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
        return false;
    }

    @Override
    public boolean audioOutputMode(int stat) {
        return false;
    }

    @Override
    public boolean speakerswitch(int stat) {
        return false;
    }

    @Override
    public boolean closeDap() {
        return false;
    }

    @Override
    public boolean audioSwitchLineOut(boolean state) {
        return false;
    }

    @Override
    public int audioGetMaxSoundVolume() {
        return 0;
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
    public boolean adjustVolume(String param) {
        return false;
    }
}
