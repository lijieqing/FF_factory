package com.fengmi.factory_test_interf.sdk_interf;

public interface AmlogicManagerInterf extends BaseMiddleware {
    /**
     * 色温: 冷色温
     */
    int COLOR_TEMP_COOL = 0;
    /**
     * 色温: 标准色温
     */
    int COLOR_TEMP_STANDARD = 1;
    /**
     * 色温: 暖色温
     */
    int COLOR_TEMP_WARM = 2;

    /*
     * ===========================================================================
     * ====================Amlogic SystemControl manager==========================
     * ===========================================================================
     */
    void writeUnifyKey(String keyName, String keyValue);

    String readUnifyKey(String keyName);

    byte[] readUnifyKeyByte(String keyName, int size);

    boolean writeHdcpRX14Key(int[] data, int size);

    boolean writeHdcpRXImg(String hk_22_Path);

    boolean writeAttestationKey(String node, String dtsName, int[] key_data, int length);

    boolean writePlayreadyKey(String dtsName, int[] key_data, int length);

    boolean writeNetflixKey(String dtsName, int[] key_data, int length);

    boolean writeSysFs(String path, String value);

    String readSysFs(String path);

    int FactoryGetRGBScreen();

    int GetContrast();

    int SetContrast(int val, int i);

    int GetSharpness();

    int SetSharpness(int val, int i, int i1);

    int GetHue();

    int SetHue(int val, int i);

    int GetSaturation();

    int SetSaturation(int val, int i);

    boolean setRedGain(String gain);

    boolean setGreenGain(String gain);

    boolean setBlueGain(String gain);

    boolean setRedOffs(String offs);

    boolean setGreenOffs(String offs);

    boolean setBlueOffs(String offs);

    int getRedGain(int colortemp);

    int getGreenGain(int colortemp);

    int getBlueGain(int colortemp);

    int getRedOffs(int colortemp);

    int getGreenOffs(int colortemp);

    int getBlueOffs(int colortemp);

    boolean pqSaveDatabase();

    boolean setColorTemp(int val);

    int getColorTemp();

    /*
     * ===========================================================================
     * ====================Amlogic TvControl manager==============================
     * ===========================================================================
     */
    boolean Hdcp14Valid();

    boolean Hdcp22Valid();

    int handleGPIO(String portName, boolean isOut, int edge);


}
