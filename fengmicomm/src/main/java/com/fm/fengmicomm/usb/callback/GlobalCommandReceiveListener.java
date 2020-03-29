package com.fm.fengmicomm.usb.callback;


import androidx.annotation.Nullable;

public interface GlobalCommandReceiveListener {
    void onRXWrapperReceived(String cmdID, @Nullable byte[] data);
}
