package com.fengmi.factory_impl_dependency;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.droidlogic.app.SystemControlManager;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.AFCallback;
import com.fengmi.factory_test_interf.sdk_interf.SensorManagerInterf;
import com.fengmi.factory_test_interf.sdk_utils.ShellUtil;
import com.fengmi.factory_test_interf.sdk_utils.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import fengmi.hardware.MotorManager;
import fengmi.hardware.TofManager;
import fengmi.hardware.TofNode;
import vendor.fengmi.hardware.tof.V1_0.ITofService;
import vendor.fengmi.hardware.tof.V1_0.RangingData;


public class SensorManagerImpl implements SensorManagerInterf {
    private static ITofService tofService;
    private boolean tofAvailable = false;
    private TofManager tofManager;
    private MotorManager motorManager;

    /**
     * 请注意 tofService 和 tofManager 的初始化顺序。
     * 目前请确保 tofService 优先于 tofManager 初始化。
     * 最先初始化出来的对象才能和tof service 正常通信。
     * <p>
     * tofService 用于 TOF 校准,TOF 测距
     * tofManager 用于完成无感对焦数据采集和保存
     *
     * @param context 上下文对象
     */
    SensorManagerImpl(Context context) {
        if (tofService == null) {
            try {
                initTofService();
                tofManager = TofManager.getInstance(context);
                motorManager = MotorManager.getInstance(context);
            } catch (Exception e) {
                Log.e(TAG, "TOF service get failed, error info :\n" + e.getMessage());
                e.printStackTrace();
            }
        }

        if (tofService != null) {
            try {
                if (tofService.supportTof()) {
                    tofAvailable = true;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "SensorManagerImpl tof service available is " + tofAvailable);
    }

    private void initTofService() {
        try {
            tofService = ITofService.getService();
        } catch (RemoteException e) {
            Log.e(TAG, "TOF service get failed, error info :\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean startTOFCalibration(int step) {
        if (!tofAvailable) {
            return false;
        }
        try {
            switch (step) {
                case 0:
                    tofService.setTofPerformRefSpadManagement((b, s) -> {
                        Log.d(TAG, s);
                    });
                    return true;
                case 1:
                    tofService.setTofPerformOffsetCalibration(140, (b, s) -> {
                        Log.d(TAG, s);
                    });
                    return true;
                case 2:
                    tofService.setTofPerformSingleTargetXTalkCalibration(600, (b, s) -> {
                        Log.d(TAG, s);
                    });
                    return true;
                case 3:
                    tofService.saveCalibrationData((b, s) -> {
                        Log.d(TAG, s);
                    });
                    return true;
                default:
                    return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            initTofService();
            return false;
        }
    }

    @Override
    public String readTOFDistance() {
        getTofMeasureDataCallback cb = new getTofMeasureDataCallback();
        if (tofAvailable) {
            try {
                tofService.getTofMeasureData(cb);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Exception is " + e.getMessage());
                initTofService();
            }
        } else {
            return "tof-error";
        }
        return cb.distance;
    }

    @Override
    public byte[] readTOFCalibrationInfo() {
        int size = 108;
        int[] res = new int[size];
        byte[] data = new byte[size];
        SystemControlManager sysCtl = SystemControlManager.getInstance();
        sysCtl.readUnifyKeyByte("tof_calibration", res, size);
        for (int i = 0; i < res.length; i++) {
            data[i] = (byte) res[i];
        }
        return data;
    }

    @Override
    public boolean startTableCollecting(Context context, String distance, String times, String split) {
        if (!tofAvailable) {
            return false;
        }
        TableInfoCollection tc = new TableInfoCollection(context, distance, times, split);
        try {
            return ThreadUtils.runBoolTask(tc).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "table collection Task run error");
        }
        return false;
    }

    @Override
    public String readDistanceStepTable() {
        return tofManager.getTofTable().toString();
    }

    @Override
    public boolean writeDistanceStepTable(String tableInfo) {
        if (TextUtils.isEmpty(tableInfo)){
            return false;
        }
        return SDKManager.getUtilManager().writeUnifyKey("tof_focusdata",tableInfo);
    }

    @Override
    public boolean supportTOF() {
        if (tofAvailable) {
            try {
                return tofService.supportTof();
            } catch (Exception e) {
                e.printStackTrace();
                initTofService();
                Log.d(TAG, e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean startTofAF(Context context) {
        if (tofAvailable) {
            String str_dis = readTOFDistance();
            Log.d(TAG, "start TOF AF str_dis=" + str_dis);
            if (TextUtils.isDigitsOnly(str_dis)) {
                int dis = Integer.parseInt(str_dis);
                int step = tofManager.getStepByDistance(dis);
                Log.d(TAG, "start TOF AF dis=" + dis + ",step=" + step);
                if (step < 0) {
                    return false;
                }
                //声明 latch 用于等待对焦完成后再返回结果
                CountDownLatch latch = new CountDownLatch(1);
                //设置对焦回调函数
                TofAFCallback tofAFCallback = new TofAFCallback(context, latch);
                SDKManager.getMotorManager().setEventCallback(context, tofAFCallback);
                //启动 TOF 对焦
                motorManager.tofAutoFocus(step);

                try {
                    //对焦超时等待7S
                    latch.await(7, TimeUnit.SECONDS);
                    return true;
                } catch (InterruptedException e) {
                    Log.d(TAG, "tofAutoFocus callback was interrupt,so we return false");
                    return false;
                } finally {
                    SDKManager.getMotorManager().unsetEventCallback(context, tofAFCallback);
                }
            }

        }
        return false;
    }

    private class getTofMeasureDataCallback implements ITofService.getTofMeasureDataCallback {
        String distance = "no-cb";

        @Override
        public void onValues(RangingData rangingData, byte b, String s) {
            Log.d(TAG, rangingData.RangeStatus + "sssss");
            Log.d(TAG, rangingData.RangeMilliMeter + "sssss");
            distance = rangingData.RangeMilliMeter + "";
        }
    }

    /**
     * 线程任务：根据传入的 times 进行对应次数的自动对焦，
     * 并对从AFStepCallback获取到的马达步数做处理，
     * 最后写入 Table 表中
     */
    private class TableInfoCollection implements Callable<Boolean> {
        private String distance;
        private String times;
        private String split;
        private Context context;

        private TableInfoCollection(Context context, String distance, String times, String split) {
            this.distance = distance;
            this.times = times;
            this.split = split;
            this.context = context;
        }

        @Override
        public Boolean call() throws Exception {
            if (TextUtils.isDigitsOnly(distance) && TextUtils.isDigitsOnly(times) && TextUtils.isDigitsOnly(split)) {
                int dis = Integer.parseInt(distance);
                int t = Integer.parseInt(times);
                int delta = Integer.parseInt(split);
                if (dis < 1 || t < 1) {
                    return false;
                }
                int[] steps = new int[t];
                //open AF Pattern
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.fengmi.factory_test", "com.fengmi.factory_test.activity.PicTest"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("commandid", "14CB");
                intent.putExtra("commandparas", "fake");
                context.startActivity(intent);

                //根据采集次数进行多次自动对焦，并采集对焦完成后的马达步数
                for (int i = 0; i < t; i++) {
                    SystemClock.sleep(1000);
                    Future<String> stepFuture = ThreadUtils.runStringTask(new AFStepCallback(context));

                    motorManager.tofAutoFocus(-0xFFFF);

                    String step = stepFuture.get();
                    steps[i] = Integer.parseInt(step);
                }
                //计算采集到的马达步数是否合理
                Arrays.sort(steps);
                int stepSplit = Math.abs(steps[steps.length - 1] - steps[0]);
                Log.d(TAG, "delta=" + split + ",stepSplit=" + stepSplit + ",Distance=" + dis + "\nstep array is " + Arrays.toString(steps));
                //检测到马达step 差异过大，需要返回false，重新测试
                if (stepSplit > delta) {
                    //关闭测试画面
                    Intent finishIntent = new Intent();
                    finishIntent.setAction("com.fengmi.factory_test.activity.PicTest.Finish");
                    finishIntent.putExtra("finish", true);
                    context.sendBroadcast(finishIntent);
                    SystemClock.sleep(1000);
                    return false;
                }
                //马达步数合理，计算平均值，写入
                int sum = 0;
                for (int step : steps) {
                    sum += step;
                }
                int data = sum / steps.length;

                Log.d(TAG, "Distance=" + dis + ",avg step is " + data);
                tofManager.addTofNode(new TofNode(dis, data), true);
                SystemClock.sleep(1000);

                //关闭测试画面
                Intent finishIntent = new Intent();
                finishIntent.setAction("com.fengmi.factory_test.activity.PicTest.Finish");
                finishIntent.putExtra("finish", true);
                context.sendBroadcast(finishIntent);
                return true;
            } else {
                Log.d(TAG, "startTableCollecting param was illegal");
                return false;
            }
        }
    }

    /**
     * 线程任务：自动对焦状态监听，当检测到对焦完成后记录当前马达步数
     */
    private class AFStepCallback implements Callable<String>, AFCallback {
        private Context context;
        private CountDownLatch downLatch = new CountDownLatch(1);

        private AFStepCallback(Context context) {
            this.context = context;
        }

        @Override
        public String call() throws Exception {
            String res;
            SDKManager.getMotorManager().setEventCallback(context, this);
            downLatch.await();
            SystemClock.sleep(1000);
            res = ShellUtil.execCommand("cat /sys/class/vgsm2028/vgsm2028/motor_cur_step").trim();
            return res;
        }

        @Override
        public void onAFStart() {
            Log.d(TAG, "AF step is onAFStart");
        }

        @Override
        public void onAFFinish() {
            Log.d(TAG, "AF step is onAFFinish");
            downLatch.countDown();
            SDKManager.getMotorManager().unsetEventCallback(context, this);
        }
    }

    /**
     * TOF 对焦回调，主要作用是监听到完成事件后通知任务返回结果
     */
    private static class TofAFCallback implements AFCallback {
        private CountDownLatch latch;

        private TofAFCallback(Context context, CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onAFStart() {
            Log.d(TAG, "TOF AF onAFStart");
        }

        @Override
        public void onAFFinish() {
            Log.d(TAG, "TOF AF onAFFinish");
            latch.countDown();
        }
    }
}
