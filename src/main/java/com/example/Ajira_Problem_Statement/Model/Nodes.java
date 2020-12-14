package com.example.Ajira_Problem_Statement.Model;

import com.example.Ajira_Problem_Statement.Enumeration.DeviceType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Nodes {
    @Id
    private String name;
    @Enumerated(EnumType.STRING)
    private DeviceType type;
    private int deviceStrength;
    private List<String> nodeConnection;

    public Nodes(String name, DeviceType type, int deviceStrength, List<String> nodeConnection) {
        this.name = name;
        this.type = type;
        this.deviceStrength = deviceStrength;
        this.nodeConnection = nodeConnection;
    }

    public Nodes() {
    }
    @Override
    public String toString() {
        return "Nodes{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", deviceStrength=" + deviceStrength +
                ", nodeConnections='" + nodeConnection + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public int getDeviceStrength() {
        return deviceStrength;
    }

    public void setDeviceStrength(int deviceStrength) {
        this.deviceStrength = deviceStrength;
    }

    public List<String> getNodeConnections() {
        return nodeConnection;
    }

    public void setNodeConnections(List<String> nodeConnections) {
        this.nodeConnection = nodeConnections;
    }
}
