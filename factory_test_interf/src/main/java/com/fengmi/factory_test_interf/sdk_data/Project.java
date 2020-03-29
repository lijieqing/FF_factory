package com.fengmi.factory_test_interf.sdk_data;

import java.util.ArrayList;
import java.util.List;

import lee.hua.xmlparse.annotation.XmlAttribute;
import lee.hua.xmlparse.annotation.XmlBean;
import lee.hua.xmlparse.annotation.XmlListNode;

@XmlBean(name = "Project")
public class Project {
    @XmlAttribute(name = "Name")
    private String Name;
    @XmlListNode(name = "Hardware", nodeType = Hardware.class)
    private List<Hardware> hardwares;

    public Project() {
        this.hardwares = new ArrayList<>();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Hardware> getHardwares() {
        return hardwares;
    }

    public void setHardwares(List<Hardware> hardwares) {
        this.hardwares.addAll(hardwares);
    }

    @Override
    public String toString() {
        return "Project{" +
                "Name='" + Name + '\'' +
                ", hardwares=" + hardwares +
                '}';
    }
}
