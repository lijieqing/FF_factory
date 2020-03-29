package com.fengmi.factory_test_interf;

import android.content.Context;
import android.util.Log;

import com.fengmi.factory_test_interf.sdk_data.HardwareList;

import java.io.IOException;

import lee.hua.xmlparse.api.XMLAPI;

public abstract class SDK {
    public abstract void initSDKImpl(Context context);
}
