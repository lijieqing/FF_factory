package com.fm.fengmicomm.usb.task;

import androidx.annotation.NonNull;
import android.util.Log;

import com.fm.fengmicomm.usb.callback.CP210xSerialCallback;
import com.fm.fengmicomm.usb.command.SerialCommand;

import java.util.Arrays;

import static com.fm.fengmicomm.usb.USBContext.cp210xSerialTxQueue;
import static com.fm.fengmicomm.usb.USBContext.cp210xSerialUsb;

public class CP210xSerialCommTask extends Thread {
    private static final String TAG = "CP210x-LOGCAT";
    private static volatile boolean running = false;
    private static volatile boolean stop = false;
    private byte[] tempBuffer = new byte[10 * 1024];
    private byte[] recvBuffer = new byte[100 * 1024];
    private int recvLen = 0;
    private int cmdEnd = 0;
    private CP210xSerialCallback serialCallback;

    @Override
    public void run() {
        if (cp210xSerialUsb == null || serialCallback == null) {
            throw new RuntimeException("CL200 USB or serialCallback is null");
        }
        while (running && !stop) {
            if (cp210xSerialUsb != null) {
                int aval = cp210xSerialUsb.readData(tempBuffer, 50);
                if (aval > 0) {
                    Log.d(TAG, aval + " <== len temp recv data ==> " + Arrays.toString(tempBuffer));
                    byte[] realBytes = new byte[aval];
                    System.arraycopy(tempBuffer, 0, realBytes, 0, aval);
                    Arrays.fill(tempBuffer, (byte) 0);
                    Log.d(TAG, Arrays.toString(realBytes));
                    System.arraycopy(realBytes, 0, recvBuffer, recvLen, aval);
                    recvLen += aval;
                    while (true) {
                        if ((cmdEnd + 1) < recvLen) {
                            if (recvBuffer[cmdEnd] == 0x0D && recvBuffer[cmdEnd + 1] == 0x0A) {
                                byte[] data = new byte[cmdEnd];
                                System.arraycopy(recvBuffer, 0, data, 0, cmdEnd);
                                Log.d("----DATA-RECEIVED----", "--------------" + cmdEnd + "-----------------" + Arrays.toString(data));
                                serialCallback.onDataReceived(new String(data));
                                recvLen = recvLen - cmdEnd - 2;
                                System.arraycopy(recvBuffer, cmdEnd + 2, recvBuffer, 0, recvLen);
                                cmdEnd = 0;
                            } else {
                                Log.d("----DATA-RECEIVED----", "--------------" + cmdEnd);
                                cmdEnd++;
                            }
                        } else {
                            break;
                        }
                    }

                } else {
                    if (cp210xSerialTxQueue != null) {
                        while (cp210xSerialTxQueue.size() > 0) {
                            SerialCommand cmd = cp210xSerialTxQueue.poll();
                            if (cmd != null) {
                                cp210xSerialUsb.writeData(cmd.getCmdData(), 50);
                            }
                        }
                    }
                }
            }
        }

    }

    public void initTask(@NonNull CP210xSerialCallback callback) {
        running = true;
        stop = false;
        serialCallback = callback;
    }

    public void killComm() {
        running = false;
        stop = true;
    }
}
