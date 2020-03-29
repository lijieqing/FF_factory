package com.fengmi.factory_test_interf.sdk_interf;

public interface SysAccessManagerInterf extends BaseMiddleware {
    String[] projector_type = new String[]{//14E0:write     14E1:read
            "/sys/class/projector/laser-projector/",
            "/sys/class/projector/led-projector/",
            //DLP 相关操作节点
            "/sys/class/projector/dlp/",//2
            "/sys/class/projector/misc/",//3
    };
    String[] projector_node = new String[]{
            "serial_write",
            "projector-0-temp/temp",//1
            "projector-1-temp/temp",//2
            "projector-2-temp/temp",//3
            "projector-3-temp/temp",//4
            "i2c_read",
            "i2c_write",
            "i2c_busy",
            "projector-0-fan/fan_speed",//8 风扇速度节点
            "fan2_control",
            "fan3_control",
            "fan_level",
            "dlp_brightness",
            "dlp_status",
            "look_select",//14
            //以下为 DLP 操作节点
            "rgb_current",//15
            "dlp_bl_en",
            "image_freeze",
            "input_source",
            "look_select",//19
            "pitch_angle",
            "power",
            "read_reg",
            "test_pattern",
            "write_reg",//24
            //misc相关的
            "bp_pwm",//25
            "bl_value",
            "get_irq",
            "at_cmd",//28
            "splash_pattern"
    };
    String[] i2c_prefix = new String[]{//14E2:write     14E3:read
            //i2c 操作节点
            "/sys/class/i2c1/",//0
            "/sys/class/i2c2/",//1
            "/sys/class/i2c3/",//2
            //峰米电池操作节点
            "/sys/class/fengmi_battery/fengmi_battery/",//3
            //马达相关操作
            "/sys/class/vgsm2028/vgsm2028/",//4
    };
    String[] i2c_suffix = new String[]{
            //以下为 I2C 操作节点
            "debug",//0
            "mode",
            "slave",
            "speed",
            "trig_gpio",//4
            //以下为 MCU 电源板操作节点
            "iadp",//适配电流5
            "ibat",//电池电流6
            "vbat",//电池电压7
            "ver",//MCU 版本8
            "temp",//电池温度9
            "i2c_read",//MCU读取节点10
            "i2c_write",//MCU写入节点11
            //马达相关
            "proj_motor_move_step",
            "motor_cur_step",//13
            "proj_motor_calibration",//14 用于PI Sensor定位，指令 echo 1 > proj_motor_calibration
            "motor_run",
            "motor_all_step_num",//16
    };
    boolean enableScreenCheck(String param);

    boolean screenCheck(int mode);

    boolean syncDlpInfo();

    boolean saveDlpInfo();

    boolean setWheelDelay(int delay);

    int getWheelDelay();

    boolean enableXPRCheck(String param);

    boolean enableXPRShake(String param);

    /**
     * get gseneor values
     * return float[] : [xMax,yMax,zMax,xMin,Ymin,zMin]
     */
    boolean checkGSensorFunc();

    boolean startGSensorCollect();

    boolean saveGSensorStandard();

    String readGSensorStandard();

    String readGSensorHorizontalData();

    String readDLPVersion();

    /**
     * 开启关闭8点校正
     *
     * @param param 0 = on,1 = off
     * @return boolean
     */
    boolean setKeyStoneMode(String param);

    /**
     * 8点校正 调整
     *
     * @param param point(1-8),direct(1-4),step(1,5)
     * @return
     */
    boolean setKeyStoneDirect(String param);

    /**
     * 写入 DLP 光机相关指令，并返回执行结果
     *
     * @param param 节点,指令.参数格式：X,Y,Z
     *              X=写入节点前置代号，
     *              Y=写入节点后置代号
     *              Z=写入节点数据
     * @return result 执行结果
     */
    boolean writeProjectorCMD(String param);

    /**
     * 读取 DLP 光机相关指令，并返回读取的数据
     *
     * @param param 节点,指令。参数格式：X,Y
     *              X=要读取的节点前置代号，
     *              Y=要读取的节点后置代号
     * @return result 读取结果
     */
    String readProjectorCMD(String param);

    /**
     * 写入 DLP 光机相关指令，并返回执行结果
     *
     * @param param 节点,指令。参数格式：X,Y,Z
     *              X=写入节点前置代号，
     *              Y=写入节点后置代号
     *              Z=写入节点数据
     * @return result 执行结果
     */
    boolean writeI2CCMD(String param);

    /**
     * 读取 DLP 光机相关指令，并返回读取的数据
     *
     * @param param 节点,指令。参数格式：X,Y
     *              X=要读取的节点前置代号，
     *              Y=要读取的节点后置代号
     * @return result 读取结果
     */
    String readI2CCMD(String param);

    boolean sendKeyCode(String param);
}
