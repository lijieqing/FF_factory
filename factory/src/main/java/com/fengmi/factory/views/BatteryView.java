package com.fengmi.factory.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.Gravity;
import android.os.Build;


import androidx.annotation.Nullable;

import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_utils.DisplayUtils;
import com.fengmi.factory_test_interf.sdk_utils.ShellUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BatteryView extends ViewGroup {
    private static final String TAG = "BatteryView";
    private static final String CMD_PRE_FIX = "cat /sys/class/fengmi_battery/fengmi_battery/";

    private static final String BATTERY_V = "V_BAT";
    private static final String BATTERY_I = "I_BAT";
    private static final String BATTERY_ADP_I = "I_ADP";
    private static final String BATTERY_MCU_VER = "MCU_VER";
    private static final String BATTERY_TEMP = "TEMP_BAT";

    private static final String[][] batteryMap = new String[][]{
//          ||  key || node || desc ||
            {BATTERY_V, "vbat", "电池电压："},
            {BATTERY_I, "ibat", "电池电流："},
            {BATTERY_ADP_I, "iadp", "适配器电流："},
            {BATTERY_MCU_VER, "ver", "MCU软件版本："},
            {BATTERY_TEMP, "temp", "电池温度："},
    };

    private Map<String, TextView> batteryViews;

    private Handler mainHandler = null;

    private BatteryInfoTask batteryInfoTask = new BatteryInfoTask();

    private int mWidth;
    private int mHeight;
    private float textSize;
    private int bgColor;
    private int margin;

    public BatteryView(Context context) {
        super(context);
        initItem(null);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initItem(attrs);
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initItem(attrs);
    }

    private void initItem(@Nullable AttributeSet attrs) {
        String hardwareVersion = SDKManager.getAndroidOSManagerInterf().getSystemProperty("ro.boot.hardware_version", "");
        if (TextUtils.equals(hardwareVersion,"t962x_m055-g") || TextUtils.equals("doraemon",Build.DEVICE)) {
            mainHandler = new Handler(Looper.getMainLooper());

            if (attrs != null) {
                TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.StringTagView);
                textSize = array.getDimension(R.styleable.StringTagView_tagTextSize, 14);
                bgColor = array.getColor(R.styleable.StringTagView_tagBackgroundColor, Color.BLACK);
                margin = array.getInteger(R.styleable.StringTagView_tagMargin, 5);
                array.recycle();
            } else {
                textSize = DisplayUtils.sp2px(getContext(), 14);
                bgColor = Color.BLACK;
                margin = (int) DisplayUtils.dp2px(getContext(), 5);
            }

            batteryViews = new HashMap<>();
            for (String[] strings : batteryMap) {
                Log.d(TAG, strings[0]);
                TextView tv = new TextView(getContext());
                tv.setBackgroundColor(bgColor);
                tv.setTextSize(textSize);
                tv.setTextColor(Color.WHITE);
                tv.setGravity(Gravity.CENTER | Gravity.LEFT);
                tv.setHeight((int) DisplayUtils.dp2px(getContext(), 18));
                tv.setText(strings[0]);
                addView(tv);

                batteryViews.put(strings[0], tv);
            }
            setBackgroundColor(Color.LTGRAY);
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mainHandler != null) {
            mainHandler.postDelayed(batteryInfoTask, 1000);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mainHandler != null) {
            mainHandler.removeCallbacks(batteryInfoTask);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "width = " + width + ",height = " + height);
        int totalH = -margin;
        int singleH = (int) DisplayUtils.dp2px(getContext(), 18);
        for (int i = 0; i <= batteryViews.keySet().size(); i++) {
            totalH += singleH + margin;
        }
        Log.d(TAG, "total Height = " + totalH);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, totalH);
    }

    private int currentHeight = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "getChildCount " + getChildCount());
        if (getChildCount() <= 0) {
            return;
        }
        int totalHeight = margin;
        int currentLineHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            int left, top, right, bottom;
            View child = getChildAt(i);

            //高度累加
            totalHeight += currentLineHeight + margin;

            left = margin;
            top = totalHeight;
            right = mWidth - margin;
            bottom = top + child.getMeasuredHeight();

            currentLineHeight = child.getHeight() + margin;
            child.layout(left, top, right, bottom);
        }
    }

    class BatteryInfoTask implements Runnable {
        @Override
        public void run() {
            for (String[] item : batteryMap) {
                String key = item[0];
                String node = item[1];
                String desc = item[2];

                String res = "";
                try {
                    res = ShellUtil.execCommand(CMD_PRE_FIX + node);
                    if (res.contains("-->")) {
                        String[] ss = res.split("-->");
                        if (ss.length > 1) {
                            res = ss[1];
                        }
                    }
                    String[] vals = parse(res);
                    if (key.equals(BATTERY_V)) {
                        if (vals.length == 3) {
                            res = vals[1] + " 容量: " + vals[2];
                        }
                    }
                    if (key.equals(BATTERY_I)) {
                        if (vals.length == 2) {
                            res = vals[1];
                        }
                    }
                    if (key.equals(BATTERY_TEMP)) {
                        if (vals.length == 2) {
                            res = vals[1];
                        }
                    }
                } catch (IOException e) {
                    res = "IO Error";
                    e.printStackTrace();
                }

                TextView tv = batteryViews.get(key);
                tv.setText(desc + res);
                Log.d(TAG, desc + res);
            }

            invalidate();
            if (mainHandler != null) {
                mainHandler.postDelayed(batteryInfoTask, 2 * 1000);
            }
        }
    }

    private String[] parse(String data) {
        String rule = "[a-z]{1,9}[_]{0,1}[a-z]{1,9}[_]{0,1}[a-z]{1,9}[_ ]{0,1}:";
        data = data.trim();
        String[] ss = data.split(rule);
        return ss;
    }
}
