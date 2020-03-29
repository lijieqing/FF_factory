package com.fengmi.factory_test_interf.sdk_interf;

public interface FengManagerInterf extends BaseMiddleware {
    /*
     * ===========================================================================
     * ========================     Feng OS Audio    ===========================
     * ===========================================================================
     */
    int OUTMODE_SPEAKER = 0;
    int OUTMODE_SPDIF = 1;
    int OUTMODE_ARC = 2;
    int OUTMODE_LINEOUT = 3;

    int AUDIO_MUTE_ON = 0;
    int AUDIO_MUTE_OFF = 1;

    /*
     * ===========================================================================
     * ========================     Feng OS keystone   ===========================
     * ===========================================================================
     */
    int KEYSTONE_POINT_1 = 1;
    int KEYSTONE_POINT_2 = 2;
    int KEYSTONE_POINT_3 = 3;
    int KEYSTONE_POINT_4 = 4;
    int KEYSTONE_POINT_5 = 5;
    int KEYSTONE_POINT_6 = 6;
    int KEYSTONE_POINT_7 = 7;
    int KEYSTONE_POINT_8 = 8;
    int KEYSTONE_MOVLEFT = 1;
    int KEYSTONE_MOVDOWN = 2;
    int KEYSTONE_MOVUP = 3;
    int KEYSTONE_MOVRIGHT = 4;
    int KEYSTONE_2_POINTS_MODE = 2;
    int KEYSTONE_4_POINTS_MODE = 4;
    int KEYSTONE_8_POINTS_MODE = 8;
    /*
     * ===========================================================================
     * ========================     Feng OS motor   ===========================
     * ===========================================================================
     */
    int AUTO_FOCUS_START = 10;
    int AUTO_FOCUS_FINISH = 11;
    /**
     * 马达正转
     **/
    int DIR_NORMAL = 0;
    /**
     * 马达反转
     **/
    int DIR_REVERSE = 1;
    /**
     * 马达转速微调
     **/
    int SPEED_LOW = 0;
    /**
     * 马达转速正常
     **/
    int SPEED_NORMAL = 1;
    /*
     * ===========================================================================
     * ========================     Feng OS projector   ===========================
     * ===========================================================================
     */
    int IMAGE_DISPLAY_ORIENTATION_DESK = 1;
    int IMAGE_DISPLAY_ORIENTATION_DESK_INVERSE = 2;
    int IMAGE_DISPLAY_ORIENTATION_HANG = 3;
    int IMAGE_DISPLAY_ORIENTATION_HANG_INVERSE = 4;
    int IMAGE_BRIGHTNESS_MODE_POWEROFF = 0;
    int IMAGE_BRIGHTNESS_MODE_POWERSAVE = 100;
    int IMAGE_BRIGHTNESS_MODE_NORMAL = 200;
    int IMAGE_BRIGHTNESS_MODE_MOVIE = 230;
    int IMAGE_BRIGHTNESS_MODE_HIGHLIGHT = 255;
    int IMAGE_BRIGHTNESS_MODE_CUSTOM = 300;
    int IMAGE_BRIGHTNESS_MODE_CCA = 400;
    boolean AUTO_BRIGHTNESS_BY_IR_ENABLE = true;
    boolean AUTO_BRIGHTNESS_BY_IR_DISABLE = false;
    int PROJECTOR_EVENT_DEACTIVE = 0;
    int PROJECTOR_EVENT_ACTIVE = 1;
    int PROJECTOR_IR1_EVENT = 0;
    int PROJECTOR_IR2_EVENT = 1;

    int transOutputMode(int factoryMode);

    int getOutputDevice();

    boolean setCurAudioMute(int mute);

    boolean setAudioOutputDevice(int deviceID);

    int SetKeystoneSelectMode(int mode);

    int SetKeystoneSet(int select, int dir, int step);

    int SetKeystoneInit();

    int SetKeystoneReset();

    int SetKeystoneSave();

    int SetKeystoneLoad();

    int SetKeystoneCancel();

    boolean setMotorConfig(int direction, int speed, int period);

    boolean setMotorStart();

    boolean setMotorEventCallback(Object callback);

    boolean unsetMotorEventCallback(Object callback);

    boolean startAutoFocus();

    boolean stopAutoFocus();

    int startAutoFocusCheck();

    int tofAutoFocusCheck(int arg);

    int tofAutoFocus(int step);

    int getBacklight();

    boolean setBacklight(int val);

    String GetFlashBuildVersion();

    boolean setProjectorOrient(int mode);

    int getDisplayOrientation();

    int getProjectorEventStatus(int type);

    String GetSerialNo();

    boolean setAutoBrightnessByIR(boolean enable);

    boolean getAutoBrightnessByIR();

    /*
     * ===========================================================================
     * ========================     Feng OS BootEnv   ===========================
     * ===========================================================================
     */
    int setBootEnv(String key, String value);

    String getBootEnv(String key, String def);
}
