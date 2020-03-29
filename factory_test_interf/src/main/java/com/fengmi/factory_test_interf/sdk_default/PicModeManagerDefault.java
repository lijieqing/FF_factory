package com.fengmi.factory_test_interf.sdk_default;

import com.fengmi.factory_test_interf.sdk_interf.PicModeManagerInterf;

public class PicModeManagerDefault implements PicModeManagerInterf {
    @Override
    public int picGetBacklight() {
        return 0;
    }

    @Override
    public boolean picSetBacklight(int val) {
        return false;
    }

    @Override
    public int picGetBrightness() {
        return 0;
    }

    @Override
    public boolean picSetBrightness(int val) {
        return false;
    }

    @Override
    public int picGetContrast() {
        return 0;
    }

    @Override
    public boolean picSetContrast(int val) {
        return false;
    }

    @Override
    public int picGetSharpness() {
        return 0;
    }

    @Override
    public boolean picSetSharpness(int val) {
        return false;
    }

    @Override
    public int picGetHue() {
        return 0;
    }

    @Override
    public boolean picSetHue(int val) {
        return false;
    }

    @Override
    public int picGetSatuation() {
        return 0;
    }

    @Override
    public boolean picSetSatuation(int val) {
        return false;
    }

    @Override
    public int picGetColorTemp() {
        return 0;
    }

    @Override
    public boolean picSetColorTemp(int val) {
        return false;
    }

    @Override
    public int picGetPostRedGain(int colortemp) {
        return 0;
    }

    @Override
    public boolean picSetPostRedGain(String gain) {
        return false;
    }

    @Override
    public int picGetPostGreenGain(int colortemp) {
        return 0;
    }

    @Override
    public boolean picSetPostGreenGain(String gain) {
        return false;
    }

    @Override
    public int picGetPostBlueGain(int colortemp) {
        return 0;
    }

    @Override
    public boolean picSetPostBlueGain(String gain) {
        return false;
    }

    @Override
    public int picGetPostRedOffset(int colortemp) {
        return 0;
    }

    @Override
    public boolean picSetPostRedOffset(String offset) {
        return false;
    }

    @Override
    public int picGetPostGreenOffset(int colortemp) {
        return 0;
    }

    @Override
    public boolean picSetPostGreenOffset(String offset) {
        return false;
    }

    @Override
    public int picGetPostBlueOffset(int colortemp) {
        return 0;
    }

    @Override
    public boolean picSetPostBlueOffset(String offset) {
        return false;
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

    @Override
    public boolean picModeSet(int stat) {
        return false;
    }

    @Override
    public int picModeGet() {
        return 0;
    }

    @Override
    public boolean picModeReset() {
        return false;
    }

    @Override
    public boolean picTransPQDataToDB() {
        return false;
    }

    @Override
    public boolean picPanelSelect(String sel) {
        return false;
    }

    @Override
    public boolean picSwitchPicUserMode() {
        return false;
    }

    @Override
    public boolean byPassPQ(String bypass) {
        return false;
    }

    @Override
    public boolean picEnablePQ() {
        return false;
    }

    @Override
    public boolean picDisablePQ() {
        return false;
    }
}
