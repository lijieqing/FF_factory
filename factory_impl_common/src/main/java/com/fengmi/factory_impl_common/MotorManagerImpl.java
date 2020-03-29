package com.fengmi.factory_impl_common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.fengmi.factory_impl_dependency.FengManagerImpl;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.AFCallback;
import com.fengmi.factory_test_interf.sdk_interf.MotorManagerInterf;
import com.fengmi.factory_test_interf.sdk_utils.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class MotorManagerImpl implements MotorManagerInterf {

    private static volatile int mAFCheckOff = -1;
    private FengManagerImpl.MyCallback callback;
    private Context context;

    MotorManagerImpl(Context context) {
        callback = new FengManagerImpl.MyCallback();
        this.context = context;
    }


    @Override
    public boolean setMotorScale(Context context, int scale) {
        boolean res = false;
        if (scale > 0) {
            SDKManager.getFengManagerInterf().setMotorConfig(DIR_NORMAL, SPEED_NORMAL, scale);
        } else {
            scale = 0 - scale;
            SDKManager.getFengManagerInterf().setMotorConfig(DIR_REVERSE, SPEED_NORMAL, scale);
        }
        return SDKManager.getFengManagerInterf().setMotorStart();
    }

    @Override
    public void setEventCallback(Context context, AFCallback afc) {
        callback.addAFCallback(afc);
    }

    @Override
    public void unsetEventCallback(Context context, AFCallback afc) {
        callback.removeAFCallback(afc);
    }

    @Override
    public void startAutoFocus(Context context) {
        SDKManager.getFengManagerInterf().startAutoFocus();
    }

    @Override
    public void stopAutoFocus(Context context) {
        SDKManager.getFengManagerInterf().stopAutoFocus();
    }

    @Override
    public String getCalibration(int index) {
        String res = SDKManager.getAmlogicManagerInterf().readSysFs("/sys/class/vgsm2028/vgsm2028/proj_motor_calibration");
        return parseCalibration(index, res);
    }

    @Override
    public boolean startAFCheck() {
        Future<Integer> task = ThreadUtils.runIntTask(new AFCheckTask());
        try {
            mAFCheckOff = task.get();
            Log.d(TAG, "mAFCheckOff = " + mAFCheckOff);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int readAFCheckCallback() {
        int res = mAFCheckOff;
        mAFCheckOff = -1;
        return res;
    }

    private String parseCalibration(int index, String data) {
        String res = "error";
        if (TextUtils.isEmpty(data) || index < 0) {
            return "error";
        }
        Log.d(TAG, "parseCalibration: data = " + data);

        data = data.replace("sensor 0 fallback space:", "FM")
                .replace("sensor 1 fallback space:", "FM")
                .replace("max step num :", "FM");
        if (data.startsWith("FM")) {
            data = data.substring(2);
        }
        Log.d(TAG, "parseCalibration: data after replace = " + data);
        String[] datas = data.split("FM");
        Log.d(TAG, "parseCalibration: datas = " + Arrays.toString(datas));
        if (index < datas.length) {
            return datas[index];
        }
        return res;
    }

    private class AFCheckTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            CountDownLatch latch = new CountDownLatch(1);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.fengmi.factory_test", "com.fengmi.factory_test.activity.PicTest"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("commandid", "14CB");
            intent.putExtra("commandparas", "fake");
            context.startActivity(intent);

            SystemClock.sleep(1000);

            FengManagerImpl.AFCheckCallback afCheckCallback = new FengManagerImpl.AFCheckCallback(latch);
            SDKManager.getFengManagerInterf().setMotorEventCallback(afCheckCallback);
            SDKManager.getFengManagerInterf().startAutoFocusCheck();

            latch.await();

            SystemClock.sleep(1000);

            Intent finishIntent = new Intent();
            finishIntent.setAction("com.fengmi.factory_test.activity.PicTest.Finish");
            finishIntent.putExtra("finish", true);
            context.sendBroadcast(finishIntent);

            SDKManager.getFengManagerInterf().unsetMotorEventCallback(afCheckCallback);
            return afCheckCallback.getOffset();

        }
    }
}
