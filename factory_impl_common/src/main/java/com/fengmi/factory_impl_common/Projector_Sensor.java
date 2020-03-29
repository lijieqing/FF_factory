package com.fengmi.factory_impl_common;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Projector_Sensor implements SensorEventListener {
    public static final String G_SENSOR_X = "x";
    public static final String G_SENSOR_Y = "y";
    public static final String G_SENSOR_Z = "z";
    private static final String TAG = "Factory_Sensor";
    private static final String G_SENSOR_KEY = "g_sensor_standard";
    private static boolean collecting = false;
    private static boolean finish = false;
    private Map<String, ArrayList<Integer>> datas;
    private boolean result;

    Projector_Sensor(Context context) {
        datas = new HashMap<>();
        datas.put(G_SENSOR_X, new ArrayList<Integer>());
        datas.put(G_SENSOR_Y, new ArrayList<Integer>());
        datas.put(G_SENSOR_Z, new ArrayList<Integer>());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged : sensor " + sensor.getName());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (collecting) {
            //datas.get(G_SENSOR_X).add(event.values[0]);
            //datas.get(G_SENSOR_Y).add(event.values[1]);
            //datas.get(G_SENSOR_Z).add(event.values[2]);
            //Log.d(TAG, "start listener onSensorChanged : event values : " + Arrays.toString(event.values));
        }
    }

    public void startCollect() {
        switch (Build.DEVICE) {
            case "ironamn":
                if (SDKManager.getHwManager().getHW().getHardwareID().equals("2")) {//045 case
                    Log.d(TAG, "in 045 Case startOldSensorCollection");
                    startOldSensorCollection();
                } else {//085 case
                    startNewSensorCollection(0);
                }
                break;
            case "goblin":
            case "franky":
            case "eva":
                startOldSensorCollection();
                break;
        }

    }

    public boolean isCompleted() {
        Log.d(TAG, "sensor check thread collecting is " + collecting);
        Log.d(TAG, "sensor check thread finish is " + finish);
        return !collecting & finish;
    }

    public boolean getSensorResult() {
        //if (result == null) {
        //    result = new float[]{0, 0, 0, 0, 0, 0};
        //}
        finish = false;
        Log.d(TAG, "check sensor func normal is " + result);
        return result;
    }

    /**
     * 水平位置时保存G Sensor X、Y、Z标准数值(共6个数值，包含xMax,yMax,zMax,xMin,yMin,zMin)
     *
     * @return success
     */
    public boolean saveStandardData() {
        String standard;
        switch (Build.DEVICE) {
            case "ironman":
                if (SDKManager.getHwManager().getHW().getHardwareID().equals("2")) {//045 case
                    standard = old_G_Sensor_cali();
                    Log.d(TAG, "G Sensor cali Data is " + standard);
                } else {//085 case
                    standard = new_Sensor_Cali();
                    Log.d(TAG, "6D cali Data is " + standard);
                }
                break;
            case "goblin":
            case "franky":
            case "eva":
                standard = old_G_Sensor_cali();
                break;
            default:
                standard = null;
        }

        if (standard == null) {
            return false;
        }
        SDKManager.getAmlogicManagerInterf().writeUnifyKey(G_SENSOR_KEY, standard);
        return TextUtils.equals(standard, SDKManager.getAmlogicManagerInterf().readUnifyKey(G_SENSOR_KEY));
    }

    public String readGsensorData() {
        String sensorData = null;
        sensorData = SDKManager.getAmlogicManagerInterf().readUnifyKey(G_SENSOR_KEY);
        Log.d(TAG, "readGsensorData  sensorData : " + sensorData);
        return sensorData == null ? "null" : sensorData;
    }

    public String readHorizontal() {
        String value = null;
        value = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/devices/virtual/bst/ACC/value").replace(" ", ",");
        return value;
    }

    private void resetGSensorData() {
        datas.get(G_SENSOR_X).clear();
        datas.get(G_SENSOR_Y).clear();
        datas.get(G_SENSOR_Z).clear();
        result = false;
    }

    private boolean isMoved(int preValue, int aftValue, int threshold) {
        int diff;
        if (preValue > 0) {
            if (aftValue > 0) {
                diff = aftValue - preValue;
            } else {
                diff = preValue - aftValue;
            }
        } else {
            if (aftValue > 0) {
                diff = aftValue - preValue;
            } else {
                diff = Math.abs(aftValue - preValue);
            }
        }
        return diff >= threshold;
    }

    /**
     * 老版本校准流程，用于 GSensor 的校准
     *
     * @return 校准数据
     */
    private String old_G_Sensor_cali() {
        boolean echoFine = false;
        echoFine = SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/devices/virtual/bst/ACC/fast_calibration_x", "0");
        if (!echoFine) {
            Log.d(TAG, "echo 0 > /sys/devices/virtual/bst/ACC/fast_calibration_x failed");
            return null;
        }
        echoFine = SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/devices/virtual/bst/ACC/fast_calibration_y", "0");
        if (!echoFine) {
            Log.d(TAG, "echo 0 > /sys/devices/virtual/bst/ACC/fast_calibration_y failed");
            return null;
        }
        echoFine = SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/devices/virtual/bst/ACC/fast_calibration_z", "2");
        if (!echoFine) {
            Log.d(TAG, "echo 2 > /sys/devices/virtual/bst/ACC/fast_calibration_z failed");
            return null;
        }

        StringBuilder sb = new StringBuilder();
        String offset = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/devices/virtual/bst/ACC/offset_x");
        sb.append(offset).append(",");
        offset = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/devices/virtual/bst/ACC/offset_y");
        sb.append(offset).append(",");
        offset = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/devices/virtual/bst/ACC/offset_z");
        sb.append(offset);

        return sb.toString();
    }

    /**
     * 老版本 Sensor 数据采集
     */
    private void startOldSensorCollection() {
        new Thread() {
            @Override
            public void run() {
                resetGSensorData();

                collecting = true;
                finish = false;
                result = false;
                String[] values;

                int count = 0;
                while (count <= 30) {
                    String v = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/devices/virtual/bst/ACC/value");
                    values = v.split(" ");
                    Log.d(TAG, "v " + v + " values : " + Arrays.toString(values));
                    if (values.length > 2) {
                        datas.get(G_SENSOR_X).add(Integer.parseInt(values[0]));
                        datas.get(G_SENSOR_Y).add(Integer.parseInt(values[1]));
                        datas.get(G_SENSOR_Z).add(Integer.parseInt(values[2]));
                    } else {
                        Log.d(TAG, "sensor data parse error: values=" + Arrays.toString(values));
                    }
                    count++;
                    SystemClock.sleep(100);
                }

                collecting = false;
                ArrayList<Integer> xValues = datas.get(G_SENSOR_X);
                ArrayList<Integer> yValues = datas.get(G_SENSOR_Y);
                ArrayList<Integer> zValues = datas.get(G_SENSOR_Z);
                Log.d(TAG, "xValues" + xValues.size() + " yValues" + yValues.size() + " zValues" + zValues.size());
                if (xValues.size() == 0 || yValues.size() == 0 || zValues.size() == 0) {
                    Log.d(TAG, "data collect error ! X or Y or Z is empty");
                    result = false;
                } else {
                    int xMax = Collections.max(xValues);
                    int yMax = Collections.max(yValues);
                    int zMax = Collections.max(zValues);
                    Log.d(TAG, "xMax" + xMax + " yMax" + yMax + " zMax" + zMax);

                    int xMin = Collections.min(xValues);
                    int yMin = Collections.min(yValues);
                    int zMin = Collections.min(zValues);
                    Log.d(TAG, "xMin" + xMin + " yMin" + yMin + " zMin" + zMin);

                    result = isMoved(xValues.get(0), xMax, 10) || isMoved(yValues.get(0), yMax, 10) || isMoved(zValues.get(0), zMax, 10);
                }
                finish = true;
            }
        }.start();
    }

    /**
     * 6轴数据校准
     * accel_data,gyro_data
     *
     * @return 校准数据
     */
    private String new_Sensor_Cali() {
        StringBuilder sb = new StringBuilder();
        SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/devices/virtual/sensors_STM/operatenodes/accel/enable", "1");
        SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/devices/virtual/sensors_STM/operatenodes/accel/cali", "1");
        SystemClock.sleep(2000);
        String accel_dataa = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/devices/virtual/sensors_STM/operatenodes/accel/cali");
        SystemClock.sleep(500);
        SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/devices/virtual/sensors_STM/operatenodes/gyro/enable", "1");
        SDKManager.getAmlogicManagerInterf().writeSysFs("/sys/devices/virtual/sensors_STM/operatenodes/gyro/cali", "1");
        SystemClock.sleep(2000);
        String gyro_data = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/devices/virtual/sensors_STM/operatenodes/gyro/cali");

        Log.d(TAG, "read accel cali Data=" + accel_dataa);
        Log.d(TAG, "read gyro cali Data=" + gyro_data);
        sb.append(accel_dataa).append(",").append(gyro_data);

        String standard = sb.toString();
        if (TextUtils.isEmpty(standard) || !standard.matches("[-0-9]+,[-0-9]+,[-0-9]+,[-0-9]+,[-0-9]+,[-0-9]+")) {
            Log.d(TAG, "6D cali Data is illegal,so we return false");
            return null;
        }
        return standard;
    }

    /**
     * 6轴数据采集
     * 目前只采集加速度的数据，因为 accel 和 gyro一个工作就可保证硬件 OK
     *
     * @param param 0 accel 数据采集
     *              1 gyro 数据采集
     */
    private void startNewSensorCollection(final int param) {
        new Thread() {
            @Override
            public void run() {
                resetGSensorData();

                collecting = true;
                finish = false;
                result = false;
                String[] values;

                String path;
                if (param == 0) {
                    path = "/sys/devices/virtual/sensors_STM/operatenodes/accel/cali";
                } else {
                    path = "/sys/devices/virtual/sensors_STM/operatenodes/gyro/cali";
                }

                int count = 0;
                while (count <= 30) {
                    //触发测量模式
                    SDKManager.getAmlogicManagerInterf().writeSysFs(path, "2");
                    SystemClock.sleep(500);
                    String v = SDKManager.getAmlogicManagerInterf().readSysFs(path).trim();
                    values = v.split(",");
                    Log.d(TAG, "v " + v + " values : " + Arrays.toString(values));
                    if (values.length > 2) {
                        datas.get(G_SENSOR_X).add(Integer.parseInt(values[0]));
                        datas.get(G_SENSOR_Y).add(Integer.parseInt(values[1]));
                        datas.get(G_SENSOR_Z).add(Integer.parseInt(values[2]));
                    } else {
                        Log.d(TAG, "sensor data parse error: values=" + Arrays.toString(values));
                    }
                    count++;
                }

                collecting = false;
                ArrayList<Integer> xValues = datas.get(G_SENSOR_X);
                ArrayList<Integer> yValues = datas.get(G_SENSOR_Y);
                ArrayList<Integer> zValues = datas.get(G_SENSOR_Z);
                Log.d(TAG, "xValues" + xValues.size() + " yValues" + yValues.size() + " zValues" + zValues.size());
                if (xValues.size() == 0 || yValues.size() == 0 || zValues.size() == 0) {
                    Log.d(TAG, "data collect error ! X or Y or Z is empty");
                    result = false;
                } else {
                    int xMax = Collections.max(xValues);
                    int yMax = Collections.max(yValues);
                    int zMax = Collections.max(zValues);
                    Log.d(TAG, "xMax" + xMax + " yMax" + yMax + " zMax" + zMax);

                    int xMin = Collections.min(xValues);
                    int yMin = Collections.min(yValues);
                    int zMin = Collections.min(zValues);
                    Log.d(TAG, "xMin" + xMin + " yMin" + yMin + " zMin" + zMin);

                    result = isMoved(xValues.get(0), xMax, 10000) || isMoved(yValues.get(0), yMax, 10000) || isMoved(zValues.get(0), zMax, 10000);

                    Log.d(TAG, "sensor check work status = " + result);
                }
                finish = true;
            }
        }.start();
    }

}