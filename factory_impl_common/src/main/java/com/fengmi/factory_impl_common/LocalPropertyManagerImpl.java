
package com.fengmi.factory_impl_common;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.fengmi.factory_test_interf.sdk_interf.LocalPropertyManagerInterf;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class LocalPropertyManagerImpl implements LocalPropertyManagerInterf {

    private Properties prop = null;
    private FileInputStream inputFile;
    private FileOutputStream outputFile;

    LocalPropertyManagerImpl(Context context) {
        prop = new Properties();
    }

    public boolean initLocalProperty() {
        boolean ret = false;
        ret = localPropInit();
        if (ret) {
            ret = appPropInit();
        }
        return ret;
    }

    public boolean clearLocalProperty() {
        return true;
    }

    public String getLocalPropertyPath() {
        return DEFAULTPATH;
    }

    public boolean setLocalPropString(String key, String value) {
        boolean ret = false;
        ret = writeStringProp(key, value);
        return ret;
    }

    public String getLocalPropString(String key) {
        String value = null;
        value = readStringProp(key);
        return value;
    }

    public boolean setLocalPropInt(String key, int value) {
        boolean ret = false;
        ret = writeIntProp(key, value);
        return ret;
    }

    public int getLocalPropInt(String key) {
        int ret = 0;
        ret = readIntProp(key);
        return ret;
    }

    public boolean setLocalPropBool(String key, boolean value) {
        boolean ret = false;
        ret = writeBoolProp(key, value);
        return ret;
    }

    public boolean getLocalPropBool(String key) {
        boolean ret = false;
        ret = readBoolProp(key);
        return ret;
    }

    public boolean increaseLocalPropInt(String key, int value) {
        Log.i(TAG, " do increase automatically for " + key);
        boolean ret = false;
        ret = propAutoIncrement(key, value);
        return ret;
    }

    public boolean writeProductFeatures(String feature) {
        Log.i(TAG, " write product feature: " + feature);
        boolean ret = false;

        return ret;

    }

    public boolean checkProductFeatures(String feature) {
        Log.i(TAG, " check product feature: " + feature);
        boolean ret = false;
        return ret;
    }

    //app interface
    public String getAppPropertyPath() {
        return APPLICATIONPATH;
    }

    public boolean setAppPropString(String key, String value) {
        boolean ret = false;
        ret = writeStringAppProp(key, value);
        return ret;
    }

    public String getAppPropString(String key) {
        String value = null;
        value = readStringAppProp(key);
        return value;
    }

    public boolean setAppPropInt(String key, int value) {
        boolean ret = false;
        ret = writeIntAppProp(key, value);
        return ret;
    }

    public int getAppPropInt(String key) {
        int ret = 0;
        ret = readIntAppProp(key);
        return ret;
    }

    public boolean setAppPropBool(String key, boolean value) {
        boolean ret = false;
        ret = writeBoolAppProp(key, value);
        return ret;
    }

    public boolean getAppPropBool(String key) {
        boolean ret = false;
        ret = readBoolAppProp(key);
        return ret;
    }

    /*===========================================local functions=====================*/
    private boolean localPropInit() {
        boolean ret = false;
        File dir = new File(LocalPropDir);
        if (!dir.exists()) {
            Log.i(TAG, "create factory directory");
            dir.mkdirs();
        }
        try {
            File f = new File(DEFAULTPATH);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    ret = true;
                } catch (IOException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                ret = true;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    private boolean writeStringProp(String key, String value) {
        boolean ret = false;
        Log.i(TAG, "String: key: " + key + "; value: " + value);
        if (loadPropertyFile()) {
            prop.setProperty(key, value);
            if (storePropertyFile()) {
                ret = true;
            }
        }
        return ret;
    }

    private String readStringProp(String key) {
        String value = null;
        if (loadPropertyFile()) {
            if (prop.containsKey(key)) {
                value = prop.getProperty(key);
            }
        }
        return value;
    }

    private boolean writeIntProp(String key, int value) {
        boolean ret = false;
        Log.i(TAG, "Int: key: " + key + "; value: " + value);
        if (writeStringProp(key, Integer.toString(value, 16))) {
            ret = true;
        }
        return ret;
    }

    private int readIntProp(String key) {
        int ret = 0;
        String buf = readStringProp(key);
        Log.i(TAG, "the key value is <" + buf + ">");
        if (buf != null) {
            ret = Integer.valueOf(buf, 16);
        } else {
            Log.e(TAG, "Error: can´ get value of " + key);
        }
        return ret;
    }

    private boolean writeBoolProp(String key, boolean value) {
        boolean ret = false;
        if (writeStringProp(key, Boolean.toString(value))) {
            ret = true;
        }
        return ret;
    }

    private boolean readBoolProp(String key) {
        boolean ret = false;
        String buf = readStringProp(key);
        Log.i(TAG, "the key value is <" + buf + ">");
        if (buf != null) {
            ret = Boolean.valueOf(buf);
        } else {
            Log.e(TAG, "Error: can´ get value of " + key);
        }
        return ret;
    }

    private boolean propAutoIncrement(String key, int value) {
        Log.i(TAG, " do increase automatically for " + key);
        boolean ret = false;
        int temp = 0;
        temp = readIntProp(key);
        if (writeIntProp(key, value + temp)) {
            ret = true;
        }
        return ret;
    }

    private boolean loadPropertyFile() {
        boolean ret = false;
        Log.i(TAG, "load property file");
        File dir = new File(LocalPropDir);
        if (!dir.exists()) {
            Log.i(TAG, "create app directory");
            dir.mkdirs();
        }
        File f = new File(DEFAULTPATH);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
                return ret;
            }
        }
        try {
            inputFile = new FileInputStream(DEFAULTPATH);
            prop.load(inputFile);
            inputFile.getFD().sync();
            inputFile.close();
            ret = true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    private boolean storePropertyFile() {
        boolean ret = false;
        String description = null;
        try {
            outputFile = new FileOutputStream(DEFAULTPATH);
            prop.store(outputFile, description);
            outputFile.getFD().sync();
            outputFile.close();
            ret = true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }



    private boolean appPropInit() {
        boolean ret = false;
        File dir = new File(LocalPropDir);
        if (!dir.exists()) {
            Log.i(TAG, "create app directory");
            dir.mkdirs();
        }
        try {
            File f = new File(APPLICATIONPATH);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    ret = true;
                } catch (IOException e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                ret = true;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    private boolean writeStringAppProp(String key, String value) {
        boolean ret = false;
        Log.i(TAG, "String: key: " + key + "; value: " + value);
        if (loadAppPropertyFile()) {
            prop.setProperty(key, value);
            if (storeAppPropertyFile()) {
                ret = true;
            }
        }
        return ret;
    }

    private String readStringAppProp(String key) {
        String value = null;
        if (loadAppPropertyFile()) {
            if (prop.containsKey(key)) {
                value = prop.getProperty(key);
            }
        }
        return value;
    }

    private boolean writeIntAppProp(String key, int value) {
        boolean ret = false;
        Log.i(TAG, "Int: key: " + key + "; value: " + value);
        if (writeStringAppProp(key, Integer.toString(value, 16))) {
            ret = true;
        }
        return ret;
    }

    private int readIntAppProp(String key) {
        int ret = 0;
        String buf = readStringAppProp(key);
        Log.i(TAG, "the key value is <" + buf + ">");
        if (buf != null) {
            ret = Integer.valueOf(buf, 16);
        } else {
            Log.e(TAG, "Error: can´ get value of " + key);
        }
        return ret;
    }

    private boolean writeBoolAppProp(String key, boolean value) {
        boolean ret = false;
        if (writeStringAppProp(key, Boolean.toString(value))) {
            ret = true;
        }
        return ret;
    }

    private boolean readBoolAppProp(String key) {
        boolean ret = false;
        String buf = readStringAppProp(key);
        Log.i(TAG, "the key value is <" + buf + ">");
        if (buf != null) {
            ret = Boolean.valueOf(buf);
        } else {
            Log.e(TAG, "Error: can´ get value of " + key);
        }
        return ret;
    }

    private boolean loadAppPropertyFile() {
        boolean ret = false;
        Log.i(TAG, "load property file");
        File dir = new File(LocalPropDir);
        if (!dir.exists()) {
            Log.i(TAG, "create app directory");
            dir.mkdirs();
        }
        File f = new File(APPLICATIONPATH);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage());
                e.printStackTrace();
                return ret;
            }
        }
        try {
            inputFile = new FileInputStream(APPLICATIONPATH);
            prop.load(inputFile);
            inputFile.getFD().sync();
            inputFile.close();
            ret = true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    private boolean storeAppPropertyFile() {
        boolean ret = false;
        String description = null;
        try {
            outputFile = new FileOutputStream(APPLICATIONPATH);
            prop.store(outputFile, description);
            outputFile.getFD().sync();
            outputFile.close();
            ret = true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

}
