package com.fengmi.factory_test_interf.sdk_default;

import com.fengmi.factory_test_interf.sdk_interf.LocalPropertyManagerInterf;

public class LocalPropertyManagerDefault implements LocalPropertyManagerInterf {
    @Override
    public boolean initLocalProperty() {
        return false;
    }

    @Override
    public boolean clearLocalProperty() {
        return false;
    }

    @Override
    public String getLocalPropertyPath() {
        return "";
    }

    @Override
    public boolean setLocalPropString(String key, String value) {
        return false;
    }

    @Override
    public String getLocalPropString(String key) {
        return "";
    }

    @Override
    public boolean setLocalPropInt(String key, int value) {
        return false;
    }

    @Override
    public int getLocalPropInt(String key) {
        return 0;
    }

    @Override
    public boolean setLocalPropBool(String key, boolean value) {
        return false;
    }

    @Override
    public boolean getLocalPropBool(String key) {
        return false;
    }

    @Override
    public boolean increaseLocalPropInt(String key, int value) {
        return false;
    }

    @Override
    public String getAppPropertyPath() {
        return "";
    }

    @Override
    public boolean setAppPropString(String key, String value) {
        return false;
    }

    @Override
    public String getAppPropString(String key) {
        return "";
    }

    @Override
    public boolean setAppPropInt(String key, int value) {
        return false;
    }

    @Override
    public int getAppPropInt(String key) {
        return 0;
    }

    @Override
    public boolean setAppPropBool(String key, boolean value) {
        return false;
    }

    @Override
    public boolean getAppPropBool(String key) {
        return false;
    }

    @Override
    public boolean writeProductFeatures(String param) {
        return false;
    }

    @Override
    public boolean checkProductFeatures(String param) {
        return false;
    }
}
