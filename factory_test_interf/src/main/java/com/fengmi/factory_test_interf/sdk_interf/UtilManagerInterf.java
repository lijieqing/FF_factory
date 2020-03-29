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
 * 本接口定义了如下信息：
 * 1. get light sensor value。读取light sensor的值。（lightSensorGetValue)
 * 2. system reset。系统执行复位，并重启。（systemReset)
 * 3. disable/enable touchpad。停止/开始响应touchpad。（touchpadSetStatus)
 * 4. disable/enable remote control。停止/开始响应遥控器。（remoteControlSetLock）
 * 5. disable/enable system sleep。允许/停止系统休眠。（systemSleepSetStatus）
 * 6. Partition check。分区检查。（checkPartition)
 * 7. Led flash setting. led闪烁起停及方式。
 * 8. switch mode from Factory to user. 从工厂到用户模式的切换
 * 9. reboot system。系统重启
 * 10. shutdown system。系统关机，但是不再启动。
 * 11. boot times。系统启动次数。
 * 12. set the product saled region.设置产品的销售区域
 * 13. get the region product saled.获取产品的销售区域设置
 * ..
 * 21. get product name.获取产品名字（40/55共用代码，可以用于区分40：hancock/55：gladiator，getProductName）
 * 22. disable screen saver and sleep mode. 关闭屏保和待机休眠.工厂下默认是关闭的,该接口适用所有产品.
 * 23. reboot recovery
 * 24. set system mode to user mode
 * 25. check system mode is user mod
 * 26. create a rid used by application team
 * 27. get key test sequence
 * 28. get woofer status
 * 29. set PowerStandby status
 * 30. set I2C port Low/High(SDA and SCK pin)
 * 31. set/get vcom command to i2c
 */

package com.fengmi.factory_test_interf.sdk_interf;

public interface UtilManagerInterf extends BaseMiddleware {
    //amlogic tv
    String switchCmd =
            "--locale=zh_CN\n--restore\n--fact2user";
    String switchCmdPath = "/cache/recovery/command";
    String POWER_HOLD = "secondary";
    String POWER_ON_DIR = "direct";
    String UNIFYKEY_POWER_MODE = "factory_power_mode";
    //the nation or area name define is at "readmeForNationAndAreaName" in doc directory
    String[] REGION = {
            "HK", //hong kong
            "CN", //chinese
            "TW", //Tai Wan
            "MO", //Macau
    };
    //set led controller (touch logo led)
    //2: set LED light steady
    //1: set LED breath
    //0: set LED disenable
    String LED_STEADY = "2";
    String LED_BREATH = "1";
    String LED_DISABLE = "0";
    int DEFAULT_PERIOD = 500;
    String BTRCMACFILEPATH = "/tvinfo/RemoteControllerBtMac";
    String WIFI_COUNTRY_CODE = "wifi_ccode";
    //=================================================================
    int MOTOR_STEP = 30;
    String REBOOT_RECOVERY = "recovery";
    String PROP_PRODUCT_NAME = "ro.product.name";
    String PROP_PRODUCT_LACALE = "ro.product.locale";
    //for fengmi product
    String PROP_PRODUCT_WIPEDATA = "--wipe_data";
    //for xiaomi product
    String PROP_PRODUCT_FORMAT_DATA = "--format_data";
    String WOOFER_IN = "1";
    String WOOFER_OUT = "0";
    String GPIO_LOW = "1";
    String GPIO_HIGH = "0";

    /*
     * -1-
     * get light sensor value.
     *
     * @return success or no.
     */
    int lightSensorGetValue();

    /**
     * -2-
     * system reset.
     *
     * @return value in integer type.
     */
    boolean systemReset();

    /**
     * -3-
     * isable/enable touchpad.
     *
     * @return success or no.
     */
    boolean touchpadSetStatus();

    /**
     * -4-
     * enable/disable DUT have the ability to repond to RC.
     * param:
     * 1: disable
     * 0: enable
     *
     * @return success or no.
     */
    boolean remoteControlSetLock(String lock);

    /**
     * -5-
     * disable/enable system sleep.
     *
     * @return success or no.
     */
    boolean systemSleepSetStatus();

    /**
     * -6-
     * Partition check.
     *
     * @return success or no.
     */
    boolean checkSystemPartition();

    /**
     * -7-
     * led flash start by period.
     *
     * @return success or no.
     */
    boolean ledStartFlash(int period);

    /**
     * -7.1-
     * led show given stat(light steady, breath, off).
     *
     * @return success or no.
     */
    boolean setLedLightStat(String style);

    /**
     * -8-
     * led flash stop.
     *
     * @return success or no.
     */
    boolean ledStopFlash();

    /**
     * -9-
     * switch mode from factory to user
     *
     * @return success or no.
     */
    boolean systemSwitchMode();

    /**
     * -39-
     * switch mode from factory to user
     *
     * @return success or no.
     */
    boolean systemSwitchModeNew();

    /**
     * -10-
     * reboot DUT
     *
     * @return success or no.
     */
    boolean systemReboot();

    /**
     * -11-
     * shutdown system (it would not start up automatically)
     *
     * @return success or no.
     */
    boolean systemShutdown();

    /**
     * -12-
     * note the system boot up times
     *
     * @return success or no.
     */
    boolean systemUpdateBootTimes();

    /**
     * -13-
     * return the system boot up times
     *
     * @return bootup times in int type.
     */
    int systemGetBootTimes();

    // /**
    //  * -14-
    //  * set the region product will be saled at.
    //  *
    //  * @return pass/fail.
    //  */
    //  boolean setProductRegion(String region);
    //
    // /**
    //  * -15-
    //  * get the region product will be saled at.
    //  *
    //  * @return region name in string type
    //  */
    //  String getProductRegion();

    /**
     * -16-
     * force system come into sleep mode.
     *
     * @return pass/fail
     */
    boolean sleepSystem();

    /**
     * -17-
     * reset flag to force read panel info directly from T-CON next bootup.
     *
     * @return pass/fail
     */
    boolean resetTvPanelSelect();

    /**
     * -18-
     * predetermine the bt rc mac to save pair time.
     *
     * @return pass/fail
     */
    boolean setBtRcMac(String mac);

    /**
     * -19-
     * return bt rc mac address.
     *
     * @return string
     */
    String getBtRcMac();

    /**
     * -20-
     * system bootup directly (need't powerkey or touch pad).
     *
     * @return string
     */
    void bootupSystemDirect();

    /**
     * -21-
     * get the model of product.
     *
     * @return product model in string type
     */
    String getProductModel();

    /**
     * -22-
     * in factory mode, the screen saver and sleep mode should be disabled.
     *
     * @return pass/fail
     */
    boolean closeScreenSave2Sleep();

    /**
     * -23-
     * reboot recovery DUT
     *
     * @return success or no.
     */
    boolean systemRebootRecovery();

    /**
     * -24-
     * switch mode from factory to user
     *
     * @return success or no.
     */
    boolean systemModeSet();

    /**
     * -25-
     * check mode is user
     *
     * @return success or no.
     */
    boolean systemModeGet();

    /**
     * -26-
     * create a radom value and save it as rid used by application team
     *
     * @return success or no.
     */
    boolean setApplicationRid();

    /**
     * -27-
     * return the key test sequence for current product
     *
     * @return key sequence.
     */
    int[] getKeyTestSequence();

    /**
     * -28-
     * get SubWoofer status
     *
     * @return subwoofer in or out.
     */
    boolean getSubWooferStat();

    /**
     * -29-
     * set PowerStandby status
     *
     * @return true/false for operation.
     */
    boolean setPowerStandbyStat(String stat);

    /**
     * -30-
     * set I2C port Low/High(SDA and SCK pin)
     *
     * @return true/false for operation.
     */
    boolean setI2CPinStat(String stat);

    /**
     * -31-
     * do master clear directly, no any pre-condition.
     *
     * @return true/false for operation.
     */
    boolean systemMasterClear();

    /**
     * -32-
     * get cpu temperature for box.
     *
     * @return true/false for operation.
     * note: empty function for compatible box
     */
    String getCpuTemp();

    /**
     * -33-
     * set fans stat for box.
     *
     * @return true/false for operation.
     * note: empty function for compatible box
     */
    boolean setFanStat(String speed);

    /**
     * -34-
     * set gpio stat (out stat).
     *
     * @return true/false for operation.
     */
    boolean setGpioOut(String portAstat);

    /**
     * -35-
     * get the panel type (it's a hw ID).
     *
     * @return the hw id for panel type.
     */
    String checkPanelIdTag();

    /**
     * -36-
     * get the gpio in status.
     *
     * @return the gpio in status.
     */
    int getGpioInStat(String port);

    /**
     * -37-
     * set vcom command to i2c.
     *
     * @return true/false for operation.
     */
    boolean setVcom(String para);

    /**
     * -38-
     * get data from vcom i2c.
     *
     * @return the vcom data.
     */
    byte getVcom(String para);

    /**
     * get sn from DLP info
     */
    String getDlpSn();

    /**
     * set Body Detect Status
     */
    boolean setBodyDetectStatus(String para);

    /**
     * get Body Detect Status
     */
    int getBodyDetectStatus();

    boolean startBodyDetectTest();

    boolean stopBodyDetectTest();

    int getBodyDetectCount(boolean isLeft);

    boolean setMotorScale(int delta);

    /**
     * 读取 Rom 全部空间大小
     *
     * @return
     */
    String readRomTotalSpace();

    /**
     * 读取 Rom 可用空间大小
     *
     * @return
     */
    String readRomAvailSpace();

    /**
     * set Fan Speed 0 for min;1 for mid;2 for max
     */
    boolean setFanSpeed(String level);

    /**
     * reset led step motor
     * echo 1 > proj_motor_calibration
     */
    boolean resetLEDStepMotor();

    String readLEDTemperature(String param);

    String readRGBLEDCurrent();

    boolean screenSaverEnable(boolean enbale);

    boolean setProductRegion(String param);

    String getProductRegion();

    String getWifiCountryCode();

    boolean setWifiCountryCode(String param);

    boolean switchMCUFactoryMode(String param);

    /**
     * reboot for USB burning
     *
     * @return true or false
     */
    boolean rebootUpdate();

    /**
     * 根据工厂测试阶段设置不同的参数
     *
     * @param param factory_mode=0 ---用户模式
     *              <p>
     *              factory_mode=1 ---工厂默认状态,会忽略一些错误,保证单板可以使用
     *              <p>
     *              factory_mode=2---工厂模式,不忽略错误
     * @return success
     */
    boolean writeFactoryMode(String param);

    /**
     * 读取工厂模式
     *
     * @return factory_mode
     */
    String readFactoryMode();

    /**
     * 写入 boot env 数据
     *
     * @param name boot env name
     * @param val  boot env val
     * @return success
     */
    boolean writeBootEnv(String name, String val);

    /**
     * 读取 boot env 数据
     *
     * @param name boot env name
     * @return boot env val
     */
    String readBootEnv(String name);

    /**
     * 将数据写入指定 unify key中
     *
     * @param name  key name
     * @param value key data
     * @return success
     */
    boolean writeUnifyKey(String name, String value);

    /**
     * 读取指定 unify key 数据
     *
     * @param name key name
     * @return key data
     */
    String readUnifyKey(String name);
}
