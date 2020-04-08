package com.fengmi.factory_test_interf.sdk_interf;

import android.content.Context;

public interface SensorManagerInterf extends BaseMiddleware{
    /**
     * 启动 TOG 校准，一共包含三步校准，根据传入的参数来决定执行哪一步
     *
     * @param step <br/>0：Ref SPAD Calibration
     *             <br/>1: Offset Calibration
     *             <br/>2: XTalk Calibration
     *             <br/>3: Save Calibration
     * @return success
     */
    boolean startTOFCalibration(int step);

    /**
     * 启动 TOF 测距，并返回测量结果
     *
     * @return distance
     */
    String readTOFDistance();

    /**
     * 读取 TOF 的校准数据，此方法在校准完成后使用才有机会获得较为准确的数据
     *
     * @return calibration Info
     */
    byte[] readTOFCalibrationInfo();

    /**
     * 启动 TOF 马达步数 Table 数据采集
     *
     * @param context 上下文对象
     * @param distance 需要采集马达步数的举例
     * @param times    需要采集的次数
     * @param split    step阈值
     * @return 启动成功、启动失败
     */
    boolean startTableCollecting(Context context, String distance, String times, String split);

    /**
     * 读取无感对焦校准数据
     *
     * @return string
     */
    String readDistanceStepTable();

    /**
     * 读取无感对焦校准数据
     *
     * @return string
     */
    boolean writeDistanceStepTable(String tableInfo);

    /**
     * 是否支持 TOF
     *
     * @return support
     */
    boolean supportTOF();

    /**
     * 开启 Tof 无感对焦,此方法应在TOF 校准和 TofTable 搜集完成后使用
     * @param context 上线文对象
     * @return 对焦成功、失败
     */
    boolean startTofAF(Context context);

    /**
     * 获取理论步数，请在校准完2M 的步数后使用
     *
     * @param distance 距离
     * @return step 理论步数
     */
    String getStepByAlgorithm(String distance);
}
