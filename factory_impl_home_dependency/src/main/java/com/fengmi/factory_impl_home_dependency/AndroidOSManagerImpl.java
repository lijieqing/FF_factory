package com.fengmi.factory_impl_home_dependency;

import android.content.Context;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiTvClient;
import android.media.AudioManager;
import android.media.session.MediaSessionLegacyHelper;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.AndroidOSManagerInterf;

import java.util.List;

public class AndroidOSManagerImpl implements AndroidOSManagerInterf {
    private TvInputManager mTvInputManager;
    private HdmiControlManager mHdmiControlManager;
    private PowerManager powerManager;
    private StorageManager storageManager;
    private Context mContext;

    AndroidOSManagerImpl(Context context) {
        powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        mHdmiControlManager = (HdmiControlManager) context.getSystemService("hdmi_control");
        mTvInputManager = (TvInputManager) context.getSystemService(Context.TV_INPUT_SERVICE);
        mContext = context;

        if (Build.DEVICE.equals("goblin")) {
            SDKManager.getAndroidOSManagerInterf().setSystemProperty("media.player.forcenu", "1");
        }
    }

    @Override
    public String getSystemProperty(String key, String def) {
        return SystemProperties.get(key, def);
    }

    @Override
    public String getSystemProperty(String key) {
        return SystemProperties.get(key);
    }

    @Override
    public void setSystemProperty(String key, String value) {
        SystemProperties.set(key, value);
    }

    @Override
    public void shutdown(boolean confirm, String reason, boolean wait) {
        powerManager.shutdown(confirm, reason, wait);
    }

    @Override
    public String[] getVolumePaths() {

        return storageManager.getVolumePaths();
    }

    @Override
    public int getHDMICECCount() {
        HdmiTvClient hdmiClient = mHdmiControlManager.getTvClient();
        Log.i(TAG, "get hdmi tv client == " + hdmiClient);
        if (hdmiClient != null) {
            List<HdmiDeviceInfo> infoList = hdmiClient.getDeviceList();
            for (HdmiDeviceInfo info : infoList) {
                Log.i(TAG, "get HdmiDeviceInfo ===== isSourceType : " + info.isSourceType());
                Log.i(TAG, "get HdmiDeviceInfo ===== disPlayName : " + info.getDisplayName());
                Log.i(TAG, "get HdmiDeviceInfo ===== vendor Id : " + info.getVendorId());
                Log.i(TAG, "get HdmiDeviceInfo ===== port Id : " + info.getPortId());
                Log.i(TAG, "get HdmiDeviceInfo ===== Id : " + info.getId());
                Log.i(TAG, "get HdmiDeviceInfo ===== Logical Address : " + info.getLogicalAddress());
            }
            return infoList.size();
        }

        return -1;
    }

    @Override
    public String getHDMICECName(int port) {
        String name = "00";
        List<TvInputInfo> inputList = mTvInputManager.getTvInputList();
        for (TvInputInfo info : inputList) {
            Log.i(TAG, "TvInputInfo mId ==== " + info.getId());
            Log.i(TAG, "TvInputInfo mParent Id ==== " + info.getParentId());
            Log.i(TAG, "TvInputInfo mType ==== " + info.getType());

            String parentId = info.getParentId();
            String hdmiName = getHdmiName(port);

            if (null != parentId && null != hdmiName) {

                if (parentId.contains(hdmiName)) {

                    HdmiDeviceInfo hdmiInfo = info.getHdmiDeviceInfo();
                    if (hdmiInfo != null) {
                        Log.i(TAG, "get HdmiDeviceInfo ===== isSourceType : " + hdmiInfo.isSourceType());
                        Log.i(TAG, "get HdmiDeviceInfo ===== disPlayName : " + hdmiInfo.getDisplayName());
                        Log.i(TAG, "get HdmiDeviceInfo ===== vendor Id : " + hdmiInfo.getVendorId());
                        Log.i(TAG, "get HdmiDeviceInfo ===== port Id : " + hdmiInfo.getPortId());
                        Log.i(TAG, "get HdmiDeviceInfo ===== Id : " + hdmiInfo.getId());
                        Log.i(TAG, "get HdmiDeviceInfo ===== Logical Address : " + hdmiInfo.getLogicalAddress());
                        return hdmiInfo.getDisplayName();
                    }
                }

            }
        }
        return name;
    }

    @Override
    public boolean adjustVolume(String state) {
        boolean ret = false;
        switch (state) {
            case "0":
                MediaSessionLegacyHelper
                        .getHelper(mContext)
                        .sendAdjustVolumeBy(
                                AudioManager.STREAM_MUSIC,
                                AudioManager.ADJUST_LOWER,
                                AudioManager.FLAG_SHOW_UI
                        );
                ret = true;
                break;
            case "1":
                MediaSessionLegacyHelper
                        .getHelper(mContext)
                        .sendAdjustVolumeBy(
                                AudioManager.STREAM_MUSIC,
                                AudioManager.ADJUST_RAISE,
                                AudioManager.FLAG_SHOW_UI
                        );
                ret = true;
                break;
            default:
                ret = false;
                break;
        }
        return ret;
    }

    /**
     * 获取port对应的HDMI name
     */
    private String getHdmiName(int port) {
        String name = null;
        switch (port) {
            case HDMI1_PORT:
                name = "/HW5";
                break;
            case HDMI2_PORT:
                name = "/HW6";
                break;
            case HDMI3_PORT:
                name = "/HW7";
                break;
        }
        return name;
    }

}
