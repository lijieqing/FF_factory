/*
 * Copyright (C) 2013 XiaoMi Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 这里主要有video, ktv, source, aging方面的操作
 * 本接口定义了如下信息的存取：
 * -------------- Source -----------------
 * 1. CEC test。测试各个HDMI接口的CEC。（hdmiTestCec)
 * 1.1 CEC test name。测试各个HDMI接口的CEC(验证name)。（hdmiTestCecName)
 * 2. the 3D sync signal on BT。蓝牙中的3D同步信号检查。（hdmiCheck3DSync)
 * 3. check EDID。检查电视的EDID。（hdmiCheckEdid)（保留）
 * 4. set Hdmi 3D mode。设置Hdmi的3D模式。（hdmiSet3D）
 * 5 get Hdmi 3D mode。读取Hdmi的3D模式。（hdmiGet3D）
 * 6. switch source。切换电视的视频输入源。（switchInputSource)
 * 7. load ATV channel table。载入ATV信号的预设频道表。（atvLoadChannel）
 * 8. load DTV channel table。载入DTV信号的预设频道表。(dtvLoadChannel)
 * -------------- video -----------------
 * 9. select local video position。选择本地视频源位置。（selectLocalVideoPos)
 * -------------- aging -----------------
 * 10. init aging timer。初始化烤机计时器。（agingInitTimerCount）
 * 11. start/stop Aging Timer。启动/停止烤机的计时器。（agingSetTimerStatus)
 * 12. set aging timer step。设置计数器的步进（即几秒记一次）。（agingSetTimerStep）
 * 13. set aging timer MAX in time type。设置烤机多长时间。（agingHowLong）
 * 13.1. fetch the timer count。读取烤机计时器记录。（agingGetTImerCount）
 * 14. clear aging timer。清除记录结果。（agingClearTimer）
 * <p>
 * -------------- ktv (预留）-----------------
 * 15. set KTV state。启动/停止NFC。（setKTVStatus)
 * 16. set FullHD output。设置为full HD 的输出模式（1080p）。（picSetFullHd)
 * 17. set test pattern. 设置不同的画面，纯色画面用于测试。(setTestPattern)
 * 18. cancel test pattern.取消设置的用于测试的纯色画面。(cancelTestPattern)
 * 37. reset Hdmi Hpd, actually, the 4030 chip is reset.
 */

package com.fengmi.factory_test_interf.sdk_interf;

import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.View;


public interface MediaTestManagerInterf extends BaseMiddleware {
    String TEMP_CHECK_ON = "temp_check_on";
    String TEMP_CHECK_THRESHOLD_01 = "temp_check_threshold_01";
    String TEMP_CHECK_THRESHOLD_02 = "temp_check_threshold_02";
    String TEMP_CHECK_DATA = "temp_check_data";
    String TEMP_CHECK_FAILED = "temp_check_status";

    int HDMI1_PORT = 23;
    int HDMI2_PORT = 24;
    int HDMI3_PORT = 25;
    /*--------------------------------PQ Function start--------------------*/
    String OSD_SWITCH = "/sys/class/graphics/fb0/blank";
    String OSD_DISPLAY_DEBUG = "/sys/class/graphics/fb0/osd_display_debug";
    String RGB_SCREEN = "/sys/class/video/rgb_screen";
    String DIS_VIDEO = "/sys/class/video/disable_video";
    int OSD_FLAG_CLOSE = 0x1;
    int OSD_FLAG_OPEN = 0x0;

    /**
     * -1-
     * test all hdmi interface's cec function.
     *
     * @return success or no.
     */
    int hdmiTestCec(int port);

    /**
     * -1.1-
     * test all hdmi interface's cec name function.
     *
     * @return cec name.
     */
    String hdmiTestCecName(int port);

    /**
     * -2-
     * the 3D sync signal on BT.
     * if just one port has 3D, the default is 0.
     * now just HuaXing Panel.
     *
     * @return success or no.
     */
    boolean hdmiCheck3DSync();

    /**
     * -3-
     * check Edid.
     * all HDMI port use the same Edid
     *
     * @return Edid in String type.
     */
    String hdmiCheckEdid();

    /**
     * -4-
     * set hdmi 3D mode.
     *
     * @return success or no.
     */
    boolean hdmiSet3D(int mode);

    /**
     * -5-
     * get hdmi 3D mode.
     *
     * @return success or no.
     * OFF(0);AUTO(1);SIDE_BY_SIDE(2);TOP_AND_BOTTOM(3);FRAMEPACKING(4)
     * abnormal return value: -1
     */
    int hdmiGet3D();

    /**
     * -6-
     * switch current input source.
     *
     * @return success or no.
     */
    boolean switchInputSource(String sour);

    /**
     * -7-
     * load ATV channel table.
     * params:
     * now have 3 city (factory): WZS, SZ, BJ
     * the params should be uppercase.
     * BJ: for beijing design cite
     *
     * @return success or no.
     */
    boolean atvLoadChannel(String city);

    /**
     * -8-
     * load DTV channel table.
     * now have 3 city (factory): WZS, SZ, BJ
     * the params should be uppercase.
     * BJ: for beijing design cite
     *
     * @return success or no.
     */
    boolean dtvLoadChannel(String city);

    /**
     * -9-
     * select local video source.
     *
     * @return success or no.
     */
    boolean selectLocalVideoPos(int id);

    /**
     * -10-
     * init burning(aging) timer count.
     *
     * @return success or no.
     */
    boolean agingInitTimerCount();

    /**
     * -11-
     * start/stop aging timer.
     *
     * @return success or no.
     */
    boolean agingSetTimerStatus(boolean stat);

    /**
     * -12-
     * set the timer count step.
     * the unit is "second".
     *
     * @return success or no.
     */
    boolean agingSetTimerStep(int val);

    /**
     * -13-
     * set how long the timer works.
     *
     * @return pass/fail.
     */
    boolean agingHowLong(int val);

    /**
     * -13.1-
     * fetch the timer count.
     *
     * @return the count value of timer.
     */
    byte[] agingGetTimerCount();

    /**
     * -14-
     * clear the timer count.
     *
     * @return success or no.
     */
    boolean agingClearTimer();

    /**
     * -16-
     * set FullHD output mode.
     *
     * @return pass or fail.
     */
    boolean picSetFullHD();

    /**
     * -17-
     * set different pattern on panel output.
     *
     * @return pass or fail.
     */
    boolean setTestPattern(int r, int g, int b);

    /**
     * -18-
     * cancel pattern shows on panel.
     *
     * @return pass or fail.
     */
    boolean cancelTestPattern();

    /**
     * -19-
     * set aspect strecth ratio
     * set AspectRatio(0:unknow,1:keep(原始),2:scretch(全屏),3:auto scale(智能缩放),
     * 4:auto scretch(智能拉伸),5:enlarge(等比例放大),6:overscan large)
     *
     * @return pass or fail.
     */
    boolean setAspectMode(int val);

    /**
     * -20-
     * init aging surface
     *
     * @return pass or fail.
     */
    boolean surfaceInit(SurfaceHolder holder, MediaPlayer.OnErrorListener errlistener,
                        MediaPlayer.OnPreparedListener prepListener);

    /**
     * -21-
     * do surface change setting
     *
     * @return pass or fail.
     */
    boolean surfaceChange(int[] pos, int width, int height);

    /**
     * -22-
     * release a tvplayer
     */
    void surfacePlayerRelease();

    /**
     * -22.1-
     * start tv player.
     *
     * @return
     */
    void surfacePlayerStart();

    /**
     * -23-
     * do prepare work before aging (burning)
     *
     * @return pass or fail.
     */
    boolean burningPrepare();

    /**
     * -24-
     * stop aging
     *
     * @return pass or fail.
     */
    boolean burningStop();

    /**
     * -25-
     * init tv context
     *
     * @return pass or fail.
     */
    boolean tvcontextInit();

    /**
     * -26-
     * the Control para is source name, here transfer it to source id.
     *
     * @return integer.
     */
    int transSourNameToId(String SourName);

    /**
     * -27-
     * switch to pointed source by para (source id)
     *
     * @return boolean.
     */
    boolean switchCurrSour(int sourId);

    /**
     * -28-
     * fetch current source's id.
     *
     * @return integer.
     */
    int getCurrSour();

    /**
     * -29-
     * fetch the source id list
     *
     * @return integer array.
     */
    int[] getAllInputSour();

    /**
     * -30-
     * tune to pointed atv channel (channel)
     *
     * @return boolean.
     */
    boolean switchAtvChannel(int channel);

    /**
     * -31-
     * tune to pointed dtv channel (channel)
     *
     * @return boolean.
     */
    boolean switchDtvChannel(int channel);

    /**
     * -32-
     * fetch current atv channel's id.
     *
     * @return integer.
     */
    int getAtvCurrChan();

    /**
     * -33-
     * fetch current dtv channel's id.
     *
     * @return integer.
     */
    int getDtvCurrChan();

    /**
     * -34-
     * fetch all atv channel number.
     *
     * @return integer.
     */
    int getAtvChanCount();

    /**
     * -35-
     * fetch all dtv channel number.
     *
     * @return integer.
     */
    int getDtvChanCount();

    /**
     * -36-
     * fetch source Name by Id.
     *
     * @return String.
     */
    String getSourName(int sourId);

    /**
     * -37-
     * init the hash table for source name and id.
     *
     * @return .
     */
    void initSourceIdTable();

    /**
     * -37-
     * reset Hdmi Hpd, actually, the 4030 chip is reset.
     *
     * @return pass/fail for operation.
     */
    boolean resetHPD();

    /**
     * -38-
     * check TV hdcp key 1.4 is valid or no.
     *
     * @return pass/fail for operation.
     */
    boolean checkHdcp14Valid();

    /**
     * -38-
     * check TV hdcp key 2.2 is valid or no.
     *
     * @return pass/fail for operation.
     */
    boolean checkHdcp22Valid();

    /**
     * -39-
     * save the auto run command (with parameter)
     *
     * @return pass/fail for operation.
     */
    boolean setAutoRunCommand(String CmdAPara);

    /**
     * -40-
     * read the auto run command (with parameter)
     *
     * @return pass/fail for operation.
     */
    String getAutoRunCommand();

    /**
     * -41-
     * switch Dispplay 1 : 1080P 60hz / 0 : 4K2K 30hz
     *
     * @return boolean.
     */
    boolean switchDisplay(int para);

    /**
     * -42-
     * switch woofer open (forcibly)
     *
     * @return boolean.
     */
    boolean wooferEnable();

    /**
     * -42-
     * init tvview
     *
     * @return boolean.
     */
    boolean initTvview();

    /**
     * -43-
     * register a tvview
     *
     * @return boolean.
     */
    boolean registerTvview(View mView);

    /**
     * -44-
     * unregister the tvview
     *
     * @return boolean.
     */
    boolean unregisterTvview(View mView);

    /**
     * -45-
     * check amlogic cpu for dolby dts
     *
     * @return boolean.
     */
    String checkDolbyDts();

    /**
     * -46-
     * set screen resolution
     *
     * @return boolean
     */
    boolean setScreenRes(String res);

    /**
     * -47-
     * set AgingLine , when aging count >=aging line ,we chage volume to agingvolume
     *
     * @return boolean
     */
    boolean agingSetAgingLine(String agingLine);

    /**
     * -48-
     * get AgingLine
     *
     * @return aging line
     */
    int agingGetAgingLine();

    /**
     * -49-
     * set AgingVolume
     *
     * @return success
     */
    boolean agingSetAgingVolume(String vol);

    /**
     * -50-
     * get AgingVolume
     *
     * @return volume
     */
    int agingGetAgingVolume();

    boolean transHdcp14TxKey();
}
