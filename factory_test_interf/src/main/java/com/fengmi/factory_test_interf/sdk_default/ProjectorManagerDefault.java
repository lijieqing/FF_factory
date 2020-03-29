package com.fengmi.factory_test_interf.sdk_default;

import android.content.Context;

import com.fengmi.factory_test_interf.sdk_interf.ProjectorManagerInterf;

import java.util.List;

public class ProjectorManagerDefault implements ProjectorManagerInterf {
    @Override
    public String readDLPVersion(Context ctx) {
        return "";
    }

    @Override
    public void setProjectorOrient(int mode) {

    }

    @Override
    public int getProjectorOrient() {
        return 0;
    }

    @Override
    public List<String> getTemperatureNameList() {
        return null;
    }

    @Override
    public List<String> getTemperatureCommandList() {
        return null;
    }
}
