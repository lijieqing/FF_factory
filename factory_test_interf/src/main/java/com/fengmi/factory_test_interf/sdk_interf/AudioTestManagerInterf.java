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
 * 本接口定义了如下信息的存取：
 * -------------- audio -----------------
 * 1 audioGetSoundVolume: 得到当前的音量设置
 * 2 audioSetSoundVolume: 设置音量，并立即生效
 * 3 audioGetSoundBalance: 得到当前的声音平衡功能设置
 * 4 audioSetSoundBalance: 设置声音平衡功能
 * 5 audioGetSoundMode: 得到当前的声音模式设置
 * 6 audioSetSoundMode: 设置当前的声音模式设置
 * 7 audioResetSoundMode: 复位声音模式回到默认值
 * 8 audioSwitchSpdif: spdif开关
 * 9 audioSwitchSpeaker: 喇叭开关
 * 10 audioSetSoundMute: 设置喇叭无声
 * 11 close DTS/DOLBY : 关闭DTS/DOLBY
 **/

package com.fengmi.factory_test_interf.sdk_interf;

public interface AudioTestManagerInterf extends BaseMiddleware {

    int OUTMODE_SPEAKER = 0;
    int OUTMODE_SPDIF = 1;
    int OUTMODE_ARC = 2;
    int OUTMODE_LINEOUT = 3;

    /**
     * get current audio volume value.
     *
     * @return value in int type.
     */
    int audioGetSoundVolume();

    /**
     * set current audio volume value.
     *
     * @return pass or fail.
     */
    boolean audioSetSoundVolume(int val);

    /**
     * get sound balance value.
     *
     * @return value in int type.
     */
    int audioGetSoundBalance();

    /**
     * set sound balance value.
     *
     * @return pass or fail.
     * @para: 0:both,1:left,2:right
     */
    boolean audioSetSoundBalance(int val);

    /**
     * get sound mode value.
     *
     * @return value in int type.
     */
    int audioGetSoundMode();

    /**
     * set sound mode value.
     *
     * @return pass or fail.
     * @SOUND_EFFECT_MODE_MINI = 0
     * @SOUND_EFFECT_MODE_DEFAULT = 0
     * @SOUND_EFFECT_MODE_MOVIE = 1
     * @SOUND_EFFECT_MODE_NEWS = 2
     * @SOUND_EFFECT_MODE_MAX = 2
     */
    boolean audioSetSoundMode(int val);

    /**
     * reset sound mode to default mode.
     *
     * @return pass or fail.
     */
    boolean audioResetSoundMode();

    /**
     * set spdif on/off.
     *
     * @return pass/fail.
     * @para: SPDIF-PCM(0);SPDIF_OUTPUT_NONPCM(1);SPDIF_OUTPUT_OFF(2)
     */
    boolean audioSwitchSpdif(int stat);

    /**
     * set speaker on/off.
     *
     * @return pass/fail.
     */
    boolean audioSwitchSpeaker(boolean enable);

    /**
     * set sound mute.
     *
     * @return pass/fail.
     */
    boolean audioSetSoundMute();

    /**
     * -11-
     * close DTS/DOLBY
     *
     * @return pass/fail for operation.
     */
    boolean closeDTS_DOLBY();

    /**
     * set arc on/off.
     *
     * @return pass/fail.
     * @para: ARC_ON(0);ARC_OFF(2)
     */
    boolean audioSwitchArc(int stat);

    /**
     * set output mode.
     *
     * @return pass/fail.
     * @para: speaker(0);spdif(1);arc(2)
     */
    boolean audioOutputMode(int stat);

    /**
     * set speakers enable/disable.
     *
     * @return pass/fail.
     * @para: 0b 1          1         1           1          1         1
     * left    lefttop  leftcenter  rightcenter  righttop   right
     */
    boolean speakerswitch(int stat);

    /**
     * close dap in dsp.
     *
     * @return pass/fail.
     */
    boolean closeDap();

    boolean audioSwitchLineOut(boolean state);

    int audioGetMaxSoundVolume();

    boolean audioSetSoundEffectMode(int mode);

    int audioGetSoundEffectMode();

    boolean adjustVolume(String param);
}
