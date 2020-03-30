package com.fengmi.factory_impl_common;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_interf.ProjectorManagerInterf;

import java.util.Arrays;
import java.util.List;


public class ProjectorManagerImpl implements ProjectorManagerInterf {

    @Override
    public String readDLPVersion(Context ctx) {
        String version = "read error";
        version = SDKManager.getFengManagerInterf().GetFlashBuildVersion();
        Log.d(TAG,"readDLPVersion : "+version);
        String[] vers = version.split(",");
        String[] vs = new String[3];
        for (String ver : vers) {
            if (ver.contains("major: ")) {
                vs[0] = (ver.replace("major: ", "").trim());
            }
            if (ver.contains("minor: ")) {
                vs[1] = (ver.replace("minor: ", "").trim());
            }
            if (ver.contains("patch: ")) {
                vs[2] = (ver.replace("patch: ", "").trim());
            }
        }
        version = vs[0] + "." + vs[1] + "." + vs[2];
        return version;
    }

    @Override
    public void setProjectorOrient(int mode) {
        SDKManager.getFengManagerInterf().setProjectorOrient(mode);
    }

    @Override
    public int getProjectorOrient() {
        return SDKManager.getFengManagerInterf().getDisplayOrientation();
    }

    @Override
    public List<String> getTemperatureNameList() {
        switch (Build.DEVICE){
            case "goblin":
                return Arrays.asList(
                        "CPU 温度",
                        "环境温度",
                        "红光温度",
                        "绿光温度",
                        "蓝光温度");
            case "eva":
                return Arrays.asList(
                        "CPU 温度",
                        "红光温度",
                        "绿光温度",
                        "环境温度");
            case "angleeUHD":
                return Arrays.asList(
                        "CPU 温度",
                        "环境温度",
                        "色轮温度",
                        "DLP 温度");
            case "ironman":
                return Arrays.asList(
                        "CPU 温度",
                        "BP 温度",
                        "环境温度",
                        "绿光温度",
                        "蓝光温度",
                        "红光温度");
            default:
                return Arrays.asList(
                        "CPU 温度",
                        "环境温度-0",
                        "环境温度-1",
                        "色轮温度",
                        "激光温度");

        }
    }

    @Override
    public List<String> getTemperatureCommandList() {
        switch (Build.DEVICE){
            case "goblin":
                return Arrays.asList(
                        "cat /sys/devices/virtual/thermal/thermal_zone0/temp",
                        "cat /sys/class/projector/led-projector/projector-0-temp/temp",
                        "cat /sys/class/projector/led-projector/projector-1-temp/temp",
                        "cat /sys/class/projector/led-projector/projector-3-temp/temp",
                        "cat /sys/class/projector/led-projector/projector-2-temp/temp");
            case "eva":
                return Arrays.asList(
                        "cat /sys/devices/virtual/thermal/thermal_zone0/temp",
                        "cat /sys/class/projector/led-projector/projector-0-temp/temp",
                        "cat /sys/class/projector/led-projector/projector-1-temp/temp",
                        "cat /sys/class/projector/led-projector/projector-2-temp/temp");
            case "ironman":
                return Arrays.asList(
                        "cat /sys/devices/virtual/thermal/thermal_zone0/temp",
                        "cat /sys/class/projector/led-projector/temp_bp_ntc/temp",
                        "cat /sys/class/projector/led-projector/temp_evr_ntc/temp",
                        "cat /sys/class/projector/led-projector/temp_grn_ntc/temp",
                        "cat /sys/class/projector/led-projector/temp_blu_ntc/temp",
                        "cat /sys/class/projector/led-projector/temp_red_ntc/temp");
            case "angleeUHD":
                return Arrays.asList(
                        "cat /sys/devices/virtual/thermal/thermal_zone0/temp",
                        "cat /sys/class/projector/laser-projector/projector-0-temp/temp",
                        "cat /sys/class/projector/laser-projector/projector-1-temp/temp",
                        "cat /sys/class/projector/laser-projector/projector-2-temp/temp");
            default:
                return Arrays.asList(
                        "cat /sys/devices/virtual/thermal/thermal_zone0/temp",
                        "cat /sys/class/projector/laser-projector/projector-0-temp/temp",
                        "cat /sys/class/projector/laser-projector/projector-1-temp/temp",
                        "cat /sys/class/projector/laser-projector/projector-2-temp/temp",
                        "cat /sys/class/projector/laser-projector/projector-3-temp/temp");
        }
    }
}
