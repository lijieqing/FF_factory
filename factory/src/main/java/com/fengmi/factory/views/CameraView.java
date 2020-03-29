package com.fengmi.factory.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_utils.ShellUtil;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CameraView extends LinearLayout {

    private static String TAG = "Factory_CameraView";

    private TextView cameraText = null;
    private TextView afText = null;
    private TextView afCount = null;

    private Timer timer = null;

    public CameraView(Context context) {
        super(context);
        initCameraView();
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCameraView();
    }

    /**
     * 初始化view
     */
    private void initCameraView() {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.TOP);

        if (Build.DEVICE.equals("franky") || Build.DEVICE.equals("eva") || Build.DEVICE.equals("goblin") || Build.DEVICE.equals("ironman")) {
            Log.d(TAG, "Factory Build.DEVICE is " + Build.DEVICE + ",now show camera aging view !!!");
            setBackgroundColor(Color.BLACK);
            if (cameraText == null) {
                cameraText = new TextView(getContext());
                cameraText.setBackgroundColor(Color.DKGRAY);
                //todo 改为XML属性获取
                cameraText.setTextSize(20f);
                cameraText.setTextColor(Color.WHITE);
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
                params.bottomMargin = 10;
                addView(cameraText, params);
            }
            if (afText == null) {
                afText = new TextView(getContext());
                afText.setBackgroundColor(Color.DKGRAY);
                //todo 改为XML属性获取
                afText.setTextSize(20f);
                afText.setTextColor(Color.WHITE);
                LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
                params.bottomMargin = 10;
                addView(afText, params);
            }
            if (afCount == null) {
                afCount = new TextView(getContext());
                afCount.setBackgroundColor(Color.DKGRAY);
                //todo 改为XML属性获取
                afCount.setTextSize(20f);
                afCount.setTextColor(Color.WHITE);
                LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
                params.bottomMargin = 10;
                addView(afCount, params);
            }
            timerOn();
        } else {
            Log.d(TAG, "Factory Build.DEVICE is " + Build.DEVICE + ",now skip show camera aging view");
            setVisibility(GONE);
        }
    }

    public void updateAF(Activity activity,String info) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                afText.setText(info);
            }
        });
    }

    public void updateCount(Activity activity,String info) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                afCount.setText(info);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timerOff();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
    }

    private void timerOn() {
        //开启 usb 检测
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    String cpuTemp = "";
                    try {
                        cpuTemp = ShellUtil.execCommand(SDKManager.getProjectorManager().getTemperatureCommandList().get(0)).trim();
                        if (cameraDetected()) {
                            showTextInfo("CPU 温度：" + cpuTemp + "    检测到 Camera 连接");
                        } else {
                            showTextInfo("CPU 温度：" + cpuTemp + "   未检测到 Camera 硬件连接");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }, 2000, (10 * 1000));
        }
    }

    private void timerOff() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void showTextInfo(String info) {
        if (cameraText != null)
            cameraText.setText(info);
    }

    private boolean cameraDetected() {
        File cameraFile00 = new File("/dev/video0");
        File cameraFile30 = new File("/dev/video30");
        Log.d(TAG, "check /dev/video30 /dev/video0 file exist ????");
        return cameraFile00.exists() || cameraFile30.exists();
    }
}
