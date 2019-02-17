package com.weeia.networkmanager.services.port;

import org.springframework.stereotype.Service;

public interface PortService {

    int getPortVlan(long switchId, int portNumber);
    void changePortVlan(long switchId, int portNumber, int newVlan);

    int getIncomingTraffic(long switchId, int portNumber);
    int getOutgoingTraffic(long switchId, int portNumber);

    boolean getPortEnabled(long switchId, int portNumber);
    void changePortEnabled(long switchId, int portNumber, boolean bEnabled);

}
