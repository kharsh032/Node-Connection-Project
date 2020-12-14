package com.example.Ajira_Problem_Statement.Enumeration;

public enum DeviceType {

    COMPUTER("Computer"),REPEATER("REPEATER");
    String name;

    DeviceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
