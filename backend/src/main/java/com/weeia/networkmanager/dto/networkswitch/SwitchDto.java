package com.weeia.networkmanager.dto.networkswitch;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Map;

public class SwitchDto {

    private long id;

    @Min(24)
    private int numOfInterfaces;

    @NotBlank
    private String interfaceType;

    @NotBlank
    private String ipAddress;
    private String info;

    private Map<String, String> errors;

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

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
