package com.fengmi.factory_test_interf.sdk_default;

import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.View;

import com.fengmi.factory_test_interf.sdk_interf.MediaTestManagerInterf;

public class MediaTestManagerDefault implements MediaTestManagerInterf {
    @Override
    public int hdmiTestCec(int port) {
        return 0;
    }

    @Override
    public String hdmiTestCecName(int port) {
        return "";
    }

    @Override
    public boolean hdmiCheck3DSync() {
        return false;
    }

    @Override
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

    @Override
    public boolean switchInputSource(String sour) {
        return false;
    }

    @Override
    public boolean atvLoadChannel(String city) {
        return false;
    }

    @Override
    public boolean dtvLoadChannel(String city) {
        return false;
    }

    @Override
    public boolean selectLocalVideoPos(int id) {
        return false;
    }

    @Override
    public boolean agingInitTimerCount() {
        return false;
    }

    @Override
    public boolean agingSetTimerStatus(boolean stat) {
        return false;
    }

    @Override
    public boolean agingSetTimerStep(int val) {
        return false;
    }

    @Override
    public boolean agingHowLong(int val) {
        return false;
    }

    @Override
    public byte[] agingGetTimerCount() {
        return new byte[0];
    }

    @Override
    public boolean agingClearTimer() {
        return false;
    }

    @Override
    public boolean picSetFullHD() {
        return false;
    }

    @Override
    public boolean setTestPattern(int r, int g, int b) {
        return false;
    }

    @Override
    public boolean cancelTestPattern() {
        return false;
    }

    @Override
    public boolean setAspectMode(int val) {
        return false;
    }

    @Override
    public boolean surfaceInit(SurfaceHolder holder, MediaPlayer.OnErrorListener errlistener, MediaPlayer.OnPreparedListener prepListener) {
        return false;
    }

    @Override
    public boolean surfaceChange(int[] pos, int width, int height) {
        return false;
    }

    @Override
    public void surfacePlayerRelease() {

    }

    @Override
    public void surfacePlayerStart() {

    }

    @Override
    public boolean burningPrepare() {
        return false;
    }

    @Override
    public boolean burningStop() {
        return false;
    }

    @Override
    public boolean tvcontextInit() {
        return false;
    }

    @Override
    public int transSourNameToId(String SourName) {
        return 0;
    }

    @Override
    public boolean switchCurrSour(int sourId) {
        return false;
    }

    @Override
    public int getCurrSour() {
        return 0;
    }

    @Override
    public int[] getAllInputSour() {
        return new int[0];
    }

    @Override
    public boolean switchAtvChannel(int channel) {
        return false;
    }

    @Override
    public boolean switchDtvChannel(int channel) {
        return false;
    }

    @Override
    public int getAtvCurrChan() {
        return 0;
    }

    @Override
    public int getDtvCurrChan() {
        return 0;
    }

    @Override
    public int getAtvChanCount() {
        return 0;
    }

    @Override
    public int getDtvChanCount() {
        return 0;
    }

    @Override
    public String getSourName(int sourId) {
        return "";
    }

    @Override
    public void initSourceIdTable() {

    }

    @Override
    public boolean resetHPD() {
        return false;
    }

    @Override
    public boolean checkHdcp14Valid() {
        return false;
    }

    @Override
    public boolean checkHdcp22Valid() {
        return false;
    }

    @Override
    public boolean setAutoRunCommand(String CmdAPara) {
        return false;
    }

    @Override
    public String getAutoRunCommand() {
        return "";
    }

    @Override
    public boolean switchDisplay(int para) {
        return false;
    }

    @Override
    public boolean wooferEnable() {
        return false;
    }

    @Override
    public boolean initTvview() {
        return false;
    }

    @Override
    public boolean registerTvview(View mView) {
        return false;
    }

    @Override
    public boolean unregisterTvview(View mView) {
        return false;
    }

    @Override
    public String checkDolbyDts() {
        return "";
    }

    @Override
    public boolean setScreenRes(String res) {
        return false;
    }

    @Override
    public boolean agingSetAgingLine(String agingLine) {
        return false;
    }

    @Override
    public int agingGetAgingLine() {
        return 0;
    }

    @Override
    public boolean agingSetAgingVolume(String vol) {
        return false;
    }

    @Override
    public int agingGetAgingVolume() {
        return 0;
    }

    @Override
    public boolean transHdcp14TxKey() {
        return false;
    }
}
