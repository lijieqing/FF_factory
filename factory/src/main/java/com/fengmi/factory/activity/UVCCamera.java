package com.fengmi.factory.activity;

import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;
import com.fengmi.factory_test_interf.sdk_globle.TvCommandDescription;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usbcameracommon.UVCCameraHandler;
import com.serenegiant.widget.CameraViewInterface;

public class UVCCamera extends BaseActivity {

    protected int SETTINGS_HIDE_DELAY_MS = 2500;
    private boolean DEBUG = true;

    private boolean USE_SURFACE_ENCODER = false;
    /**
     * preview resolution(width)
     * if your camera does not support specific resolution and mode,
     * [com.serenegiant.usb.UVCCamera.setPreviewSize] throw exception
     */
    private int PREVIEW_WIDTH = 1280;
    /**
     * preview resolution(height)
     * if your camera does not support specific resolution and mode,
     * [com.serenegiant.usb.UVCCamera.setPreviewSize] throw exception
     */
    private int PREVIEW_HEIGHT = 720;
    /**
     * preview mode
     * if your camera does not support specific resolution and mode,
     * [com.serenegiant.usb.UVCCamera.setPreviewSize] throw exception
     * 0:YUYV, other:MJPEG
     */
    private int PREVIEW_MODE = 1;
    private int[] CAMERA_VID = new int[]{3141, 5075};
    /**
     * for accessing USB
     */
    private USBMonitor mUSBMonitor = null;
    /**
     * Handler to execute camera related methods sequentially on private thread
     */
    private UVCCameraHandler mCameraHandler = null;
    /**
     * for camera preview display
     */
    private CameraViewInterface mUVCCameraView = null;
    /**
     * for open&start / stop&close camera preview
     */
    private TextView mCameraInfo = null;

    private UsbDevice mUsbDevice = null;

    private USBMonitor.OnDeviceConnectListener mOnDeviceConnectListener = new USBMonitor.OnDeviceConnectListener() {
        @Override
        public void onAttach(UsbDevice usbDevice) {
            Log.d(TAG, usbDevice.toString());
            if (isTargetDevice(usbDevice)) {
                mUsbDevice = usbDevice;
                Toast.makeText(UVCCamera.this, "CAMERA_DEVICE_ATTACHED", Toast.LENGTH_SHORT).show();
                mCameraHandler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCameraInfo.setVisibility(View.GONE);
                                    }
                                });
                                mUSBMonitor.requestPermission(mUsbDevice);
                            }
                        }, 1500);
            }
        }

        @Override
        public void onDettach(UsbDevice usbDevice) {
            Toast.makeText(UVCCamera.this, "USB_DEVICE_DETACHED", Toast.LENGTH_SHORT).show();
            mUsbDevice = null;
        }

        @Override
        public void onConnect(UsbDevice usbDevice, USBMonitor.UsbControlBlock usbControlBlock, boolean b) {
            Log.d(TAG, "onConnect:");
            mCameraHandler.open(usbControlBlock);
            startPreview();
        }

        @Override
        public void onDisconnect(UsbDevice usbDevice, USBMonitor.UsbControlBlock usbControlBlock) {
            Log.d(TAG, "onDisconnect:");
            if (mCameraHandler != null) {
                mCameraHandler.close();
            }
        }

        @Override
        public void onCancel(UsbDevice usbDevice) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uvccamera);
        mCameraInfo = findViewById(R.id.tv_camera_info);

        mUVCCameraView = findViewById(R.id.camera_view);

        mUVCCameraView.setAspectRatio(PREVIEW_WIDTH / PREVIEW_HEIGHT);

        mUSBMonitor = new USBMonitor(this, mOnDeviceConnectListener);
        mCameraHandler = UVCCameraHandler.createHandler(this, mUVCCameraView,
                USE_SURFACE_ENCODER ? 0 : 1, PREVIEW_WIDTH, PREVIEW_HEIGHT, PREVIEW_MODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUSBMonitor.register();
        if (mUVCCameraView != null)
            mUVCCameraView.onResume();
    }

    @Override
    protected void onStop() {
        mCameraHandler.close();
        if (mUVCCameraView != null)
            mUVCCameraView.onPause();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mCameraHandler != null) {
            mCameraHandler.release();
            mCameraHandler = null;
        }
        if (mUSBMonitor != null) {
            mUSBMonitor.destroy();
            mUSBMonitor = null;
        }
        mUVCCameraView = null;
        mCameraInfo = null;
        super.onDestroy();
    }

    @Override
    protected void handleControlMsg(int cmdType, String cmdID, String cmdPara) {
        if (FactorySetting.COMMAND_TASK_STOP == cmdType) {
            mCameraHandler.close();
            finish();
            setResult(cmdID, PASS, true);
        } else if (FactorySetting.COMMAND_TASK_BUSINESS == cmdType) {
            int cmd = Integer.parseInt(cmdID, 16);
            if (TvCommandDescription.CMDID_CAMERA_TEST_OPEN == cmd) {
                if (!mCameraHandler.isOpened()) {
                    if (mUsbDevice != null) {
                        mCameraInfo.setVisibility(View.GONE);
                        mUSBMonitor.requestPermission(mUsbDevice);
                    } else {
                        mCameraInfo.setText("未检测到 Camera \n 请确认Camera连接是否正常");
                        mCameraInfo.setTextColor(Color.RED);
                        mCameraInfo.setVisibility(View.VISIBLE);
                    }
                }
                setResult(cmdID, PASS, false);
            } else if (TvCommandDescription.CMDID_CAMERA_TEST_CAPTURE == cmd) {
                setResult(cmdID, PASS, false);
            }
        }
    }

    private void startPreview() {
        SurfaceTexture st = mUVCCameraView.getSurfaceTexture();
        mCameraHandler.startPreview(new Surface(st));
    }

    private boolean isTargetDevice(UsbDevice usbDevice) {
        boolean res = false;
        int vid = usbDevice.getVendorId();
        int pid = usbDevice.getProductId();

        for (int i = 0; i < CAMERA_VID.length; i++) {
            if (vid == CAMERA_VID[i]) {
                res = true;
            }
        }
        return res;
    }
}
