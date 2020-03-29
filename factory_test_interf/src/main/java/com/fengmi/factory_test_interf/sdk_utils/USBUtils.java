package com.fengmi.factory_test_interf.sdk_utils;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public class USBUtils {
    private static final String TAG = "Factory_USBUtils";

    /**
     * 获取当前设备的 USB 数量
     *
     * @param context context
     * @return usb size
     */
    public static String getUSBListSize(Context context) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        if (usbManager != null) {
            HashMap<String, UsbDevice> map = usbManager.getDeviceList();
            return map.keySet().size() + "";
        }
        Log.d(TAG, "usbManager is null");
        return "0";
    }

    /**
     * 根据 VID 和 PID 获取 USB 信息，可以返回多个 USB 信息
     *
     * @param context context
     * @param vid     USB vid
     * @param pid     USB pid
     * @return USB info
     */
    public static String getUSBInfoByID(Context context, String vid, String pid) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isDigitsOnly(vid) && TextUtils.isDigitsOnly(pid)) {
            int vendorID = Integer.parseInt(vid);
            int productID = Integer.parseInt(pid);
            UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

            if (usbManager != null) {
                HashMap<String, UsbDevice> map = usbManager.getDeviceList();
                for (String key : map.keySet()) {
                    UsbDevice usb = map.get(key);
                    if (usb != null) {
                        Log.d(TAG, usb.toString());

                        if (usb.getVendorId() == vendorID && usb.getProductId() == productID) {
                            sb.append(USBInfoSimply(usb)).append("\n");
                        }
                    }
                }
                if (sb.length() < 5) {
                    sb.append("USB Not Found");
                }
            } else {
                sb.append("get USB Error");
            }
        }

        return sb.toString();
    }

    private static String USBInfoSimply(UsbDevice usbDevice) {
        StringBuilder info = new StringBuilder();
        info.append("vendorID=").append(usbDevice.getVendorId()).append(",")
                .append("productID=").append(usbDevice.getProductId()).append(",")
                .append("manufactureName=").append(usbDevice.getManufacturerName()).append(",")
                .append("productName=").append(usbDevice.getProductName()).append(",")
                .append("usbVersion=").append(usbDevice.getVersion()).append(",")
                .append("usbSerial=").append(usbDevice.getSerialNumber());

        return info.toString();
    }
}
