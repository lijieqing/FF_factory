package com.fengmi.factory_test_interf.sdk_utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

public final class WindowUtil {

    public static synchronized void showMenu(Context context, View view) {
        // 取得系统窗口
        WindowManager mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(WINDOW_SERVICE);
        // 窗口的布局样式
        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
        // 设置窗口显示类型――TYPE_SYSTEM_ALERT(系统提示)
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置窗口焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        // 设置显示的模式
        mLayoutParams.format = PixelFormat.RGBA_8888;
        // 设置对齐的方法
        mLayoutParams.gravity = Gravity.CENTER;
        // 设置窗口宽度和高度
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        // 设置窗口显示的位置。否则在屏幕中心显示
        // mLayoutParams.x = 50;
        // mLayoutParams.y = 50;
        mWindowManager.addView(view, mLayoutParams);
    }

    public static synchronized void removeMenu(Context context, View view) {
        WindowManager mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(WINDOW_SERVICE);
        mWindowManager.removeViewImmediate(view);
    }
}
