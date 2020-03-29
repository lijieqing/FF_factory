package com.fengmi.factory_impl_common;

import android.content.Context;

import com.fengmi.factory_test_interf.SDK;
import com.fengmi.factory_test_interf.SDKManager;

public class SDK_Common extends SDK {
    @Override
    public void initSDKImpl(Context context) {
        SDKManager.setAudioTestManager(new AudioTestManagerImpl(context));
        SDKManager.setInfoAccessManager(new InfoAccessManagerImpl());
        SDKManager.setKeystoneManager(new KeystoneManagerImpl());
        SDKManager.setLocalPropertyManager(new LocalPropertyManagerImpl(context));
        SDKManager.setMediaTestManager(new MediaTestManagerImpl(context));
        SDKManager.setMotorManager(new MotorManagerImpl(context));
        SDKManager.setPicModeManager(new PicModeManagerImpl(context));
        SDKManager.setProjectorManager(new ProjectorManagerImpl());
        SDKManager.setNetManager(new RfNetManagerImpl(context));
        SDKManager.setStorageManager(new StorageManagerImpl(context));
        SDKManager.setSysAccessManager(new SysAccessManagerImpl(context));
        SDKManager.setUtilManager(new UtilManagerImpl(context));
    }
}
