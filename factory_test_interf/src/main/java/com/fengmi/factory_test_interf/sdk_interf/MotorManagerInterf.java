package com.fengmi.factory_test_interf.sdk_interf;

import android.content.Context;

public interface MotorManagerInterf extends BaseMiddleware {
    /*****     Motor Status    *******/
    int MOTOR_STOP = 1;
    int MOTOR_MIN = 5;
    int MOTOR_MAX = 6;
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
    /**
     * 马达转速快速
     **/
    int SPEED_FAST = 2;
    int MOTOR_AUTO_FOCUS_DISABLE = 0;
    int MOTOR_AUTO_FOCUS_ENABLE = 1;


    boolean setMotorScale(Context context, int scale);

    void setEventCallback(Context context, AFCallback afc);

    void unsetEventCallback(Context context, AFCallback afc);

    void startAutoFocus(Context context);

    void stopAutoFocus(Context context);

    /**
     *
     * @param index
     *      0 = sensor 0 fallback space
     *      1 = sensor 1 fallback space
     *      2 = max step num
     * @return value
     */
    String getCalibration(int index);

    /**
     * 开始自动对焦回程差检查
     * @return success
     */
    boolean startAFCheck();

    int readAFCheckCallback();
}
