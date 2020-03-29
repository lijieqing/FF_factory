package com.fengmi.factory_test_interf.sdk_interf;

public interface AndroidOSManagerInterf extends BaseMiddleware{
    int HDMI1_PORT = 23;
    int HDMI2_PORT = 24;
    int HDMI3_PORT = 25;

    String getSystemProperty(String key, String def);

    String getSystemProperty(String key);

    void setSystemProperty(String key, String value);

    void shutdown(boolean confirm, String reason, boolean wait);

    String[] getVolumePaths();

    int getHDMICECCount();

    String getHDMICECName(int port);

    boolean adjustVolume(String state);
}
