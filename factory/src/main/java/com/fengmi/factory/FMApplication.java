package com.fengmi.factory;

import android.app.Application;
import android.util.Log;

import com.fengmi.factory_impl_common.SDK_Common;
import com.fengmi.factory_impl_dependency.SDK_Dependency;
import com.fengmi.factory_test_interf.SDKManager;

public class FMApplication extends Application {
    private static final String TAG = "Factory_Application";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "----------FMApplication onCreate ----------");
        Log.d(TAG, "----------init HW info ----------");
        SDKManager.initHardwareIDInfo(this);

        Log.d(TAG, "----------init SDK Dependency----------");
        SDK_Dependency sdk_dependency = new SDK_Dependency();
        sdk_dependency.initSDKImpl(this);

        Log.d(TAG, "----------init SDK Common----------");
        SDK_Common sdk_common = new SDK_Common();
        sdk_common.initSDKImpl(this);

    }

}
