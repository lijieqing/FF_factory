package com.fengmi.factory_test_interf;

import android.content.Context;
import android.util.Log;

import com.fengmi.factory_test_interf.sdk_data.HardwareList;
import com.fengmi.factory_test_interf.sdk_default.AudioTestManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.InfoAccessManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.KeyManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.KeystoneManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.LocalPropertyManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.MediaTestManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.MotorManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.PicModeManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.ProjectorManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.RfNetManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.SensorManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.StorageManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.SysAccessManagerDefault;
import com.fengmi.factory_test_interf.sdk_default.UtilManagerDefault;
import com.fengmi.factory_test_interf.sdk_interf.AmlogicManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.AndroidOSManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.AudioTestManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.FengManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.InfoAccessManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.KeyManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.KeystoneManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.LocalPropertyManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.MediaTestManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.MotorManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.PicModeManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.ProjectorManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.RfNetManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.SensorManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.StorageManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.SysAccessManagerInterf;
import com.fengmi.factory_test_interf.sdk_interf.UtilManagerInterf;

import java.io.IOException;

import lee.hua.xmlparse.api.XMLAPI;

public final class SDKManager {
    private static final String TAG = "Factory_SDKManager";
    private static AudioTestManagerInterf audioTestManager;
    private static InfoAccessManagerInterf infoAccessManager;
    private static KeyManagerInterf keyManager;
    private static KeystoneManagerInterf keystoneManager;
    private static LocalPropertyManagerInterf localPropertyManager;
    private static MediaTestManagerInterf mediaTestManager;
    private static MotorManagerInterf motorManager;
    private static PicModeManagerInterf picModeManager;
    private static ProjectorManagerInterf projectorManager;
    private static RfNetManagerInterf netManager;
    private static StorageManagerInterf storageManager;
    private static SysAccessManagerInterf sysAccessManager;
    private static UtilManagerInterf utilManager;
    private static SensorManagerInterf sensorManager;
    private static FengManagerInterf fengManagerInterf;
    private static AmlogicManagerInterf amlogicManagerInterf;
    private static AndroidOSManagerInterf androidOSManagerInterf;
    private static HardwareList hwManager;

    public static HardwareList getHwManager() {
        if (hwManager == null) {
            Log.e(TAG, "HardwareList is null,please call SDK.initHardwareIDInfo first");
        }
        return hwManager;
    }

    static void setHwManager(HardwareList hwManager) {
        SDKManager.hwManager = hwManager;
    }


    public static SensorManagerInterf getSensorManager() {
        if (sensorManager == null) {
            Log.e(TAG, "sensorManager is null,init default");
            sensorManager = new SensorManagerDefault();
        }
        return sensorManager;
    }

    public static void setSensorManager(SensorManagerInterf sensorManager) {
        SDKManager.sensorManager = sensorManager;
    }

    public static AudioTestManagerInterf getAudioTestManager() {
        if (audioTestManager == null) {
            Log.e(TAG, "audioTestManager is null,init default");
            audioTestManager = new AudioTestManagerDefault();
        }
        return audioTestManager;
    }

    public static void setAudioTestManager(AudioTestManagerInterf audioTestManager) {
        SDKManager.audioTestManager = audioTestManager;
    }

    public static InfoAccessManagerInterf getInfoAccessManager() {
        if (infoAccessManager == null) {
            Log.e(TAG, "infoAccessManager is null,init default");
            infoAccessManager = new InfoAccessManagerDefault();
        }
        return infoAccessManager;
    }

    public static void setInfoAccessManager(InfoAccessManagerInterf infoAccessManager) {
        SDKManager.infoAccessManager = infoAccessManager;
    }

    public static KeyManagerInterf getKeyManager() {
        if (keyManager == null) {
            Log.e(TAG, "keyManager is null,init default");
            keyManager = new KeyManagerDefault();
        }
        return keyManager;
    }

    public static void setKeyManager(KeyManagerInterf keyManager) {
        SDKManager.keyManager = keyManager;
    }

    public static KeystoneManagerInterf getKeystoneManager() {
        if (keystoneManager == null) {
            Log.e(TAG, "keystoneManager is null,init default");
            keystoneManager = new KeystoneManagerDefault();
        }
        return keystoneManager;
    }

    public static void setKeystoneManager(KeystoneManagerInterf keystoneManager) {
        SDKManager.keystoneManager = keystoneManager;
    }

    public static LocalPropertyManagerInterf getLocalPropertyManager() {
        if (localPropertyManager == null) {
            Log.e(TAG, "localPropertyManager is null,init default");
            localPropertyManager = new LocalPropertyManagerDefault();
        }
        return localPropertyManager;
    }

    public static void setLocalPropertyManager(LocalPropertyManagerInterf localPropertyManager) {
        SDKManager.localPropertyManager = localPropertyManager;
    }

    public static MediaTestManagerInterf getMediaTestManager() {
        if (mediaTestManager == null) {
            Log.e(TAG, "mediaTestManager is null,init default");
            mediaTestManager = new MediaTestManagerDefault();
        }
        return mediaTestManager;
    }

    public static void setMediaTestManager(MediaTestManagerInterf mediaTestManager) {
        SDKManager.mediaTestManager = mediaTestManager;
    }

    public static MotorManagerInterf getMotorManager() {
        if (motorManager == null) {
            Log.e(TAG, "motorManager is null,init default");
            motorManager = new MotorManagerDefault();
        }
        return motorManager;
    }

    public static void setMotorManager(MotorManagerInterf motorManager) {
        SDKManager.motorManager = motorManager;
    }

    public static PicModeManagerInterf getPicModeManager() {
        if (picModeManager == null) {
            Log.e(TAG, "picModeManager is null,init default");
            picModeManager = new PicModeManagerDefault();
        }
        return picModeManager;
    }

    public static void setPicModeManager(PicModeManagerInterf picModeManager) {
        SDKManager.picModeManager = picModeManager;
    }

    public static ProjectorManagerInterf getProjectorManager() {
        if (projectorManager == null) {
            Log.e(TAG, "projectorManager is null,init default");
            projectorManager = new ProjectorManagerDefault();
        }
        return projectorManager;
    }

    public static void setProjectorManager(ProjectorManagerInterf projectorManager) {
        SDKManager.projectorManager = projectorManager;
    }

    public static RfNetManagerInterf getNetManager() {
        if (netManager == null) {
            Log.e(TAG, "netManager is null,init default");
            netManager = new RfNetManagerDefault();
        }
        return netManager;
    }

    public static void setNetManager(RfNetManagerInterf netManager) {
        SDKManager.netManager = netManager;
    }

    public static StorageManagerInterf getStorageManager() {
        if (storageManager == null) {
            Log.e(TAG, "storageManager is null,init default");
            storageManager = new StorageManagerDefault();
        }
        return storageManager;
    }

    public static void setStorageManager(StorageManagerInterf storageManager) {
        SDKManager.storageManager = storageManager;
    }

    public static SysAccessManagerInterf getSysAccessManager() {
        if (sysAccessManager == null) {
            Log.e(TAG, "sysAccessManager is null,init default");
            sysAccessManager = new SysAccessManagerDefault();
        }
        return sysAccessManager;
    }

    public static void setSysAccessManager(SysAccessManagerInterf sysAccessManager) {
        SDKManager.sysAccessManager = sysAccessManager;
    }

    public static UtilManagerInterf getUtilManager() {
        if (utilManager == null) {
            Log.e(TAG, "utilManager is null,init default");
            utilManager = new UtilManagerDefault();
        }
        return utilManager;
    }

    public static void setUtilManager(UtilManagerInterf utilManager) {
        SDKManager.utilManager = utilManager;
    }

    public static FengManagerInterf getFengManagerInterf() {
        return fengManagerInterf;
    }

    public static void setFengManagerInterf(FengManagerInterf fengManagerInterf) {
        SDKManager.fengManagerInterf = fengManagerInterf;
    }

    public static AmlogicManagerInterf getAmlogicManagerInterf() {
        return amlogicManagerInterf;
    }

    public static void setAmlogicManagerInterf(AmlogicManagerInterf amlogicManagerInterf) {
        SDKManager.amlogicManagerInterf = amlogicManagerInterf;
    }

    public static AndroidOSManagerInterf getAndroidOSManagerInterf() {
        return androidOSManagerInterf;
    }

    public static void setAndroidOSManagerInterf(AndroidOSManagerInterf androidOSManagerInterf) {
        SDKManager.androidOSManagerInterf = androidOSManagerInterf;
    }

    /**
     * init HardwareID.xml(path is assets/HardwareID.xml)
     * <p>
     * call this before initSDKImpl {@link com.fengmi.factory_test_interf.SDK}
     *
     * @param context ctx
     */
    public static void initHardwareIDInfo(Context context) {
        if (SDKManager.getHwManager() == null) {
            String scanPackage = "com.fengmi.factory_test_interf.sdk_data";
            XMLAPI.setXmlBeanScanPackage(context, scanPackage);
            XMLAPI.setClassLoader(context.getClassLoader());
            try {
                HardwareList hardwareList = (HardwareList) XMLAPI.readXML(context.getAssets().open("HardwareID"));
                if (hardwareList == null) {
                    Log.d("Factory_SDK", "hardware list is null");
                } else {
                    String desc = hardwareList.toString();
                    Log.d("Factory_SDK", desc);
                    SDKManager.setHwManager(hardwareList);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Factory_SDK", "Hardware List already exist");
        }
    }
}
