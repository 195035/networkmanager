package com.weeia.networkmanager.domain.networkswitch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Switch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int numOfInterfaces;

    @Column(nullable = false)
    private String interfaceType;

    @Column(unique = true, nullable = false)
    private String ipAddress;

    @Column
    private String info;

    public Switch() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumOfInterfaces() {
        return numOfInterfaces;
    }

    public void setNumOfInterfaces(int numOfInterfaces) {
        this.numOfInterfaces = numOfInterfaces;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
