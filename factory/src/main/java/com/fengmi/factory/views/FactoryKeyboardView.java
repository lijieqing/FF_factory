package com.fengmi.factory.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_utils.WindowUtil;

public class FactoryKeyboardView extends RelativeLayout {
    private KeyDispatchEvent dispatchEvent;

    public FactoryKeyboardView(Context context) {
        super(context);
    }

    public FactoryKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FactoryKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDispatchEvent(KeyDispatchEvent dispatchEvent) {
        this.dispatchEvent = dispatchEvent;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d("FactoryKeyboardView", "---- keycode = " + event.getKeyCode());
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == 111) {//键盘`esc`
                WindowUtil.removeMenu(getContext(), this);
            } else {
                onKeyDown(event.getKeyCode());
            }
        }
        if (dispatchEvent != null) {
            dispatchEvent.dispatchKeyEventFactory(event);
        }
        return super.dispatchKeyEvent(event);
    }

    private void onKeyDown(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                SDKManager.getMotorManager().setMotorScale(getContext(), -6);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                SDKManager.getMotorManager().setMotorScale(getContext(), 6);
                break;
        }
    }

    public interface KeyDispatchEvent {
        void dispatchKeyEventFactory(KeyEvent event);
    }
}
