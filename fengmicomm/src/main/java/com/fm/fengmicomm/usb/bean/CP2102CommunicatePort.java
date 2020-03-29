package com.fm.fengmicomm.usb.bean;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;

import com.usbserial.driver.CommonUsbSerialPort;
import com.usbserial.driver.Cp21xxSerialDriver;
import com.usbserial.driver.UsbSerialDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

/**
 * @author lijie
 * @create 2019-02-22 13:33
 **/
public class CP2102CommunicatePort extends CommonUsbSerialPort {
    private static final String TAG = "CP2102CommunicatePort";
    private static SerialPort port;
    private Cp21xxSerialDriver cp21xxSerialDriver = null;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String[] devs = new String[]{
            "/dev/ttyUSB0",
            "/dev/ttyUSB1"
    };

    public CP2102CommunicatePort(UsbDevice device, int portNumber, Cp21xxSerialDriver cp21xxSerialDriver) {
        super(device, portNumber);
        this.cp21xxSerialDriver = cp21xxSerialDriver;
    }

    @Override
    public UsbSerialDriver getDriver() {
        return cp21xxSerialDriver;
    }


    @Override
    public void open(UsbDeviceConnection connection) throws IOException {
        if (port == null) {
            File file = getDevFile();
            if (file != null) {
                file.setReadable(true);
                file.setWritable(true);
                port = new SerialPort(file, 115200, 0);
                inputStream = port.getInputStream();
                outputStream = port.getOutputStream();
            } else {
                throw new FileNotFoundException("dev file is null");
            }
        } else {
            Log.d(TAG, "port already init !!!!");
        }
    }

    @Override
    public void close() throws IOException {
        if (port != null) {
            port.close();
            port = null;
        }
    }

    @Override
    public int read(byte[] dest, int timeoutMillis) throws IOException {
        int res = 0;
        if (inputStream != null) {
            if (inputStream.available() > 0) {
                res = inputStream.read(dest);
            }
        }
        return res;
    }

    @Override
    public int write(byte[] src, int timeoutMillis) throws IOException {
        if (outputStream != null) {
            outputStream.write(src);
            outputStream.flush();
        }
        return 0;
    }

    @Override
    public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException {

    }

    @Override
    public boolean getCD() throws IOException {
        return false;
    }

    @Override
    public boolean getCTS() throws IOException {
        return false;
    }

    @Override
    public boolean getDSR() throws IOException {
        return false;
    }

    @Override
    public boolean getDTR() throws IOException {
        return false;
    }

    @Override
    public void setDTR(boolean value) throws IOException {

    }

    @Override
    public boolean getRI() throws IOException {
        return false;
    }

    @Override
    public boolean getRTS() throws IOException {
        return false;
    }

    @Override
    public void setRTS(boolean value) throws IOException {

    }

    private File getDevFile() {
        File dev = null;
        for (String s : devs) {
            File file = new File(s);
            if (file.exists()) {
                dev = file;
            }
        }
        return dev;
    }

}
