package com.fengmi.factory_impl_dependency;

import android.content.Context;

import com.fengmi.factory_test_interf.SDK;
import com.fengmi.factory_test_interf.SDKManager;

public class SDK_Dependency extends SDK {
    @Override
    public void initSDKImpl(Context context) {
        SDKManager.setAmlogicManagerInterf(new AmlogicManagerImpl());
        SDKManager.setAndroidOSManagerInterf(new AndroidOSManagerImpl(context));
        SDKManager.setSensorManager(new SensorManagerImpl(context));
        SDKManager.setFengManagerInterf(new FengManagerImpl(context));
    }
}
