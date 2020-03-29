package com.fengmi.factory_test_interf.sdk_data;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.fengmi.factory_test_interf.SDKManager;

import java.util.ArrayList;
import java.util.List;

import lee.hua.xmlparse.annotation.XmlBean;
import lee.hua.xmlparse.annotation.XmlListNode;

@XmlBean(name = "HardwareList")
public class HardwareList {
    private static String TAG = "Factory_HWList";
    @XmlListNode(name = "Project", nodeType = Project.class)
    private List<Project> projects;

    public HardwareList() {
        projects = new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects.addAll(projects);
    }

    @Override
    public String toString() {
        return "HardwareList{" +
                ", projects=" + projects +
                '}';
    }

    /**
     * 根据 projectName 和 hwID 获取产品 Hardware 描述信息
     *
     * @param projectName 项目名称（ironman,eva,goblin 等）
     * @param hwID        硬件 ID
     * @return 硬件描述信息
     */
    public Hardware getHWByIDAndName(String projectName, String hwID) {
        if (!TextUtils.isDigitsOnly(hwID)) {
            Log.d(TAG, "getHWByID param error hwID=" + hwID);
            return null;
        }
        for (Project project : projects) {
            String name = project.getName().toUpperCase();
            if (name.equals(projectName.toUpperCase())) {
                for (Hardware hardware : project.getHardwares()) {
                    if (hardware.getHardwareID().equals(hwID)) {
                        return hardware;
                    }
                }
            }
        }
        Log.d(TAG, "the hwInfo about project=" + projectName + ",hw ID=" + hwID + " was not found!");
        return null;
    }

    /**
     * 获取当前产品的硬件描述信息
     *
     * @return 硬件描述信息
     */
    public Hardware getHW() {
        String hwID = SDKManager.getAndroidOSManagerInterf().getSystemProperty("ro.boot.hardware_id", "null").trim();
        String name = Build.DEVICE;
        Log.d(TAG, "the hwInfo about project=" + name + ",hw ID=" + hwID);
        return getHWByIDAndName(name, hwID);
    }
}
