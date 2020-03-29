package com.fengmi.factory_test_interf.sdk_default;

import com.fengmi.factory_test_interf.sdk_interf.StorageManagerInterf;

public class StorageManagerDefault implements StorageManagerInterf {
    @Override
    public boolean usbHost30Test() {
        return false;
    }

    @Override
    public boolean usbHost20Test() {
        return false;
    }

    @Override
    public boolean tfCardTest() {
        return false;
    }

    @Override
    public boolean usbHost30Unmount() {
        return false;
    }

    @Override
    public boolean usbHost20Unmount() {
        return false;
    }

    @Override
    public boolean tfCardUnmount() {
        return false;
    }

    @Override
    public int externalMemoryTest() {
        return 0;
    }

    @Override
    public boolean externalMemoryUnmount() {
        return false;
    }

    @Override
    public boolean adbToUsb() {
        return false;
    }

    @Override
    public String usbSpeedCheck(String id) {
        return "";
    }

    @Override
    public boolean usbContent2SpeedTest(String Name, String UsbType) {
        return false;
    }

    @Override
    public boolean usbFileCheck(String name) {
        return false;
    }

    @Override
    public boolean usbTextFileCheck() {
        return false;
    }
}
