package com.fengmi.factory_impl_common;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.SystemClock;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.StorageManagerInterf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class StorageManagerImpl implements StorageManagerInterf {

    protected UsbManager usbManager;
    private Context mContext;
    /*===========================================local functions=====================*/
    //read content 5 times, if it fails yet, return false.
    private String diskTargetString = "Disk Varification!";

    StorageManagerImpl(Context context) {
        mContext = context;
        usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
    }

    @Override
    public boolean usbHost30Test() {
        return false;
    }

    public boolean usbTextFileCheck() {
        boolean ret = false;
        ret = usb30Test();
        return ret;
    }

    /* abandon */
    public boolean usbHost20Test() {
        boolean ret = false;
        ret = usb20Test();
        return ret;
    }

    public String usbSpeedCheck(String id) {
        String ret = null;
        ret = checkUsbProp(id);
        Log.i(TAG, "target udisk speed is " + ret);
        return ret;
    }

    public boolean usbContent2SpeedTest(String Name, String UsbType) {
        boolean ret = false;
        ret = usbContentTest(Name, UsbType);
        return ret;
    }

    //unused from TV2
    public boolean tfCardTest() {
        boolean ret = false;
        ret = tfTest();
        return ret;
    }

    public boolean usbHost30Unmount() {
        boolean ret = false;
        ret = usb30Unmount();
        return ret;
    }

    public boolean usbHost20Unmount() {
        boolean ret = false;
        ret = usb20Unmount();
        return ret;
    }

    //unused from TV2
    public boolean tfCardUnmount() {
        boolean ret = false;
        ret = tfUnmount();
        return ret;
    }

    public int externalMemoryTest() {
        int ret = 0;
        return ret;
    }

    public boolean externalMemoryUnmount() {
        boolean ret = false;
        return ret;
    }

    public boolean adbToUsb() {
        boolean ret = false;
        //ret = changeAdbtoUsb();
        return ret;
    }

    public boolean usbFileCheck(String para) {
        boolean ret = false;
        ret = checkFileExist(para);
        return ret;
    }

    private boolean testUDiskAndTFdisk(String path) {
        boolean ret = false;
        File f = new File(path);
        Log.i(TAG, "external memory Test Started: " + path);
        int readCount = 0;
        while (readCount++ < 5) {
            try {
                FileInputStream fis = new FileInputStream(f);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                String res = new String(buffer, "UTF-8");//EncodingUtils.getString(buffer, "UTF-8");
                Log.i(TAG, "Disk Content " + res);
                if (res.contains(diskTargetString)) {
                    ret = true;
                    break;
                }
            } catch (Exception ex) {
                Log.i(TAG, "Disk Result failed");
                SystemClock.sleep(200);
            }
        }
        return ret;
    }

    private boolean diskUnmount(String path) {
        Log.i(TAG, "disk unMount: " + path);
/*
		try {
			IMountService mountService = IMountService.Stub
				.asInterface(ServiceManager.getService("mount"));

			mountService.unmountVolume(path, true, false);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return false;
		}
*/
        return true;
    }

    private String[] getExternalMemPath() {
        Log.i(TAG, "Storage: get external path");
        String[] externalPath = null;
        try {
            externalPath = SDKManager.getAndroidOSManagerInterf().getVolumePaths();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return externalPath;
    }

    private boolean usb30Test() {
        boolean ret = false;
        String[] pathList = null;
        pathList = getExternalMemPath();
        if (pathList != null) {
            for (int i = 0; i < pathList.length; i++) {
                Log.i(TAG, "external memory[" + i + "] is <" + pathList[i] + ">");
                Log.i(TAG, "external memory target file is " + uDiskFile);
                if (pathList[i] != null && !pathList[i].contains("emulated")
                        && !pathList[i].contains("self")) {
                    ret = testUDiskAndTFdisk(pathList[i] + uDiskFile);
                    break;
                }
            }
        } else {
            Log.i(TAG, "can't enum this disk");
        }
        return ret;
    }

    private boolean usb20Test() {
        boolean ret = false;
        String[] pathList = null;
        pathList = getExternalMemPath();
        if (pathList != null) {
            for (int i = 0; i < pathList.length; i++) {
                Log.i(TAG, "external memory[" + i + "] is <" + pathList[i] + ">");
                Log.i(TAG, "external memory target file is " + uDiskFile);
                if (pathList[i] != null && !pathList[i].contains(tfDeviceName)) {
                    ret = testUDiskAndTFdisk(pathList[i] + uDiskFile);
                    break;
                }
            }
        } else {
            Log.i(TAG, "can't enum this disk");
        }
        return ret;
    }

    private boolean tfTest() {
        boolean ret = false;
        String[] pathList = null;
        pathList = getExternalMemPath();
        if (pathList != null) {
            for (int i = 0; i < pathList.length; i++) {
                Log.i(TAG, "external memory[" + i + "] is <" + pathList[i] + ">");
                Log.i(TAG, "external memory target file is " + tfCardFile);
                if (pathList[i] != null && !pathList[i].contains(uDiskDeviceName)) {
                    ret = testUDiskAndTFdisk(pathList[i] + tfCardFile);
                    break;
                }
            }
        } else {
            Log.i(TAG, "can't enum this disk");
        }
        return ret;
    }

    private boolean usb30Unmount() {
        boolean ret = false;
        String[] pathList = null;
        pathList = getExternalMemPath();
        if (pathList == null) {
            return ret;
        }
        for (int i = 0; i < pathList.length; i++) {
            if (pathList[i] != null && !pathList[i].contains(tfDeviceName)) {
                ret = diskUnmount(pathList[i]);
                break;
            }
        }
        return ret;
    }

    private boolean usb20Unmount() {
        boolean ret = false;
        String[] pathList = null;
        pathList = getExternalMemPath();
        if (pathList == null) {
            return ret;
        }
        for (int i = 0; i < pathList.length; i++) {
            if (pathList[i] != null && !pathList[i].contains(tfDeviceName)) {
                ret = diskUnmount(pathList[i]);
                break;
            }
        }
        return ret;
    }

    private boolean tfUnmount() {
        boolean ret = false;
        String[] pathList = null;
        pathList = getExternalMemPath();
        if (pathList == null) {
            return ret;
        }
        for (int i = 0; i < pathList.length; i++) {
            if (pathList[i] != null && pathList[i].contains(tfDeviceName)) {
                ret = diskUnmount(pathList[i]);
                break;
            }
        }
        return ret;
    }

    private boolean usbContentTest(String filename, String type) {
        boolean ret = false;
        String[] path = null;
        path = getExternalMemPath();
        if (path != null) {
            for (int i = 0; i < path.length; i++) {
                Log.i(TAG, "external memory[" + i + "] is <" + path[i] + ">");
                Log.i(TAG, "external memory target file is " + filename);
                if (path[i] != null && !path[i].contains("emulated")
                        && !path[i].contains("self")) {
                    // 1. 被测文件放在u盘根目录下的Factory目录中
                    String factPath = path[i] + FACTORY_TEST_DIR;
                    File fpath = new File(factPath);
                    if (!fpath.isDirectory()) {
                        Log.e(TAG, "can not find the Factory directory");
                        return ret;
                    }
					/*
					// 2. 该目录中的被测文件是唯一的（即目录中仅有一个文件）
					if(!(fpath.listFiles().length == 1)){
						Log.e(TAG, "there isn't only one test file in this folder");
						return ret;
					}
					*/
                    // 3. 被测文件名要与输入名一致
                    String fileFullName[] = fpath.list()[0].split("/");

                    if (!fileFullName[fileFullName.length - 1].equals(filename)) {
                        Log.e(TAG, "the test file doesn't match with expected file -> " + fpath.listFiles()[0]);
                        Log.e(TAG, "the test file doesn't match with expected file -> " + fileFullName[fileFullName.length - 1]);
                        return ret;
                    }
                    String testfile = factPath + "/" + filename;
                    // 4. 统计字符个数是否与预期一致
                    File ftest = new File(testfile);
                    int bufsize = 4 * 1024 * 1024;
                    BufferedReader br = null;
                    int filesize = 0;
                    long time1 = System.currentTimeMillis();
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(testfile)), bufsize);
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            filesize = filesize + line.length();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "read test file exception for " + testfile, e);
                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (Exception e) {
                            }
                        }
                    }
                    long time2 = System.currentTimeMillis();
                    if (type.equals("2")) {
                        if ((time2 - time1) < 1500 && filesize == 16000000) {
                            ret = true;
                        }
                    } else if (type.equals("3")) {
                        if ((time2 - time1) < 800 && filesize == 16000000) {
                            ret = true;
                        }
                    } else {
                        Log.i(TAG, "usb type is error:" + type);
                    }
                    //ret = testUDiskAndTFdisk(path[i] + uDiskFile);
                }
            }
        } else {
            Log.i(TAG, "can't enum this disk");
        }
        return ret;
    }

    private String checkUsbProp(String id) {
        String ret = null;
        String udiskManufacture;
        String pathvendor, pathproduct, pathspeed;
        if (id.length() < 8) {
            return ret;
        }
        String aimIdVendor = id.substring(0, 4);
        String aimIdProduct = id.substring(4, 8);
        Log.i(TAG, "aim id. vendor is " + aimIdVendor + "; product is " + aimIdProduct);
        File f = new File(USB_DEVICE_PATH);
        if (!f.isDirectory()) {
            Log.i(TAG, "can't open this directory");
            return ret;
        }
        String[] listBuf = f.list();
        boolean findDisk = false;
        for (int i = 0; i < listBuf.length; i++) {
            File fSub = new File(USB_DEVICE_PATH + listBuf[i]);
            if (!fSub.isDirectory()) {
                Log.i(TAG, "can't open this directory");
                return ret;
            }
            String[] subListBuf = fSub.list();
            String[] idProductValue = null;
            String[] idVendorValue = null;
            String[] usbSpeed = null;
            for (int j = 0; j < subListBuf.length; j++) {
                if (subListBuf[j].equals(ID_VENDOR)) {
                    Log.i(TAG, "find vendor id file");
                    pathvendor = USB_DEVICE_PATH + listBuf[i] + "/" + ID_VENDOR;
                    pathproduct = USB_DEVICE_PATH + listBuf[i] + "/" + ID_PRODUCT;
                    pathspeed = USB_DEVICE_PATH + listBuf[i] + "/" + USB_SPEED;
                    Log.i(TAG, "vendor: " + pathvendor);
                    Log.i(TAG, "product: " + pathproduct);
                    idVendorValue = catOperation(pathvendor);
                    idProductValue = catOperation(pathproduct);
                    usbSpeed = catOperation(pathspeed);
                    Log.i(TAG, "idvendor: " + idVendorValue[0]);
                    Log.i(TAG, "idProduct: " + idProductValue[0]);
                    Log.i(TAG, "speed: " + usbSpeed[0]);
                    if (aimIdProduct.equals(idProductValue[0]) && aimIdVendor.equals(idVendorValue[0])) {
                        ret = usbSpeed[0];
                        findDisk = true;
                        break;
                    }
                }
            }
            if (findDisk) {
                break;
            }
        }
        return ret;
    }

    /*===========================================local functions=====================*/
    /*===========================================tool functions=====================*/
    private String[] catOperation(String filename) {
        ArrayList list = new ArrayList();
        int i = 0;
        File f = new File(filename);
        if (!f.exists()) {
            return null;
        }
        try {
            BufferedReader input = new BufferedReader(new FileReader(f));
            StringBuffer buffer = new StringBuffer();
            String text;
            while ((text = input.readLine()) != null)
                buffer.append(text);
            list.add(buffer.toString());
        } catch (IOException ioException) {
            System.err.println("File Error!");
        }
        //new String[] li = list.toArray(new String[list.size]);
        String[] li = new String[list.size()];
        li = (String[]) list.toArray(li);
        return li;
    }

    /*===========================================tool functions=====================*/
    private boolean checkFileExist(String filename) {
        if (filename == null || filename.length() == 0) {
            return false;
        }
        boolean ret = false;
        String[] path = null;
        path = getExternalMemPath();
        if (path != null) {
            for (int i = 0; i < path.length; i++) {
                Log.i(TAG, "external memory[" + i + "] is <" + path[i] + ">");
                Log.i(TAG, "external memory target file is " + filename);
                if (path[i] != null && !path[i].contains("emulated")
                        && !path[i].contains("self")) {
                    // 1. 被测文件放在u盘根目录下的Factory目录中
                    String factPath = path[i] + FACTORY_TEST_DIR;
                    File fpath = new File(factPath);
                    if (!fpath.isDirectory()) {
                        Log.e(TAG, "can not find the Factory directory");
                        return ret;
                    }
                    // 2. 该目录中的被测文件是唯一的（即目录中仅有一个文件）
                    if (!(fpath.listFiles().length == 1)) {
                        Log.e(TAG, "there isn't only one test file in this folder");
                        return ret;
                    }
                    // 3. 被测文件名要与输入名一致
                    String fileFullName[] = fpath.list()[0].split("/");

                    if (fileFullName[fileFullName.length - 1].equals(filename)) {
                        return true;
                    }
                }
            }
        }
        return ret;
    }
}
