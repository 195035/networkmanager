package com.weeia.networkmanager.services.network;

public interface NetworkService {

    int getPortVlan(String switchIp, int portNumber);
    void changePortVlan(String switchIp, int portNumber, int newVlan);

    boolean getPortEnabled(String switchIp, int portNumber);
    void changePortEnabled(String switchIp, int portNumber, boolean bEnabled);

    int getIncomingTraffic(String switchIp, int portNumber);
    int getOutgoingTraffic(String switchIp, int portNumber);

    void performInternalUpdate();
}
