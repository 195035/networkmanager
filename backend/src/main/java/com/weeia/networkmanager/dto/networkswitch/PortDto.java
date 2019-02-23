package com.weeia.networkmanager.dto.networkswitch;

public class PortDto {

    private int number;
    private boolean enabled;
    private int vlan;
    private String incomingLoad;
    private String outgoingLoad;

    public PortDto(int number, boolean enabled, int vlan, String incomingLoad, String outgoingLoad) {
        this.number = number;
        this.enabled = enabled;
        this.vlan = vlan;
        this.incomingLoad = incomingLoad;
        this.outgoingLoad = outgoingLoad;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getIncomingLoad() {
        return incomingLoad;
    }

    public void setIncomingLoad(String incomingLoad) {
        this.incomingLoad = incomingLoad;
    }

    public String getOutgoingLoad() {
        return outgoingLoad;
    }

    public void setOutgoingLoad(String outgoingLoad) {
        this.outgoingLoad = outgoingLoad;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVlan() {
        return vlan;
    }

    public void setVlan(int vlan) {
        this.vlan = vlan;
    }
}
