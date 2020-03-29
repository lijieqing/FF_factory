package com.fengmi.factory_test_interf.sdk_default;

import android.content.Context;

import com.fengmi.factory_test_interf.sdk_interf.KeystoneManagerInterf;

public class KeystoneManagerDefault implements KeystoneManagerInterf {
    @Override
    public boolean setKeyStoneMode(Context ctx, String param) {
        return false;
    }

    @Override
    public boolean setKeyStoneDirect(Context ctx, String param) {
        return false;
    }

    @Override
    public boolean setKeyStoneSets() {
        return false;
    }

    @Override
    public boolean openAutoKeystoneFunc() {
        return false;
    }

    @Override
    public boolean closeAutoKeystoneFunc() {
        return false;
    }
}
