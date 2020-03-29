package com.fengmi.factory_test_interf.sdk_interf;

import android.content.Context;

public interface KeystoneManagerInterf extends BaseMiddleware {

    boolean setKeyStoneMode(Context ctx, String param);

    boolean setKeyStoneDirect(Context ctx, String param);

    boolean setKeyStoneSets();

    boolean openAutoKeystoneFunc();

    boolean closeAutoKeystoneFunc();

}
