package com.fengmi.factory_test_interf.sdk_data;

import lee.hua.xmlparse.annotation.XmlAttribute;
import lee.hua.xmlparse.annotation.XmlBean;

@XmlBean(name = "Hardware")
public class Hardware {
    @XmlAttribute(name = "Name")
    private String Name;
    @XmlAttribute(name = "HardwareID")
    private String HardwareID;
    @XmlAttribute(name = "Desc")
    private String Desc;
    @XmlAttribute(name = "Keyboard")
    private Boolean Keyboard;
    @XmlAttribute(name = "MiPackage")
    private Boolean MiPackage;

    public Hardware() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHardwareID() {
        return HardwareID;
    }

    public void setHardwareID(String hardwareID) {
        HardwareID = hardwareID;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Boolean getKeyboard() {
        return Keyboard;
    }

    public void setKeyboard(Boolean keyboard) {
        Keyboard = keyboard;
    }

    public Boolean getMiPackage() {
        return MiPackage;
    }

    public void setMiPackage(Boolean miPackage) {
        MiPackage = miPackage;
    }

    @Override
    public String toString() {
        return "Hardware{" +
                "Name='" + Name + '\'' +
                ", HardwareID='" + HardwareID + '\'' +
                ", Desc='" + Desc + '\'' +
                ", Keyboard=" + Keyboard +
                ", MiPackage=" + MiPackage +
                '}';
    }
}
