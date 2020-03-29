package com.fengmi.factory_test_interf.sdk_interf;

import android.content.Context;

import java.util.List;

public interface ProjectorManagerInterf extends BaseMiddleware {
    String readDLPVersion(Context ctx);

    void setProjectorOrient(int mode);

    int getProjectorOrient();

    List<String> getTemperatureNameList();

    List<String> getTemperatureCommandList();
}
