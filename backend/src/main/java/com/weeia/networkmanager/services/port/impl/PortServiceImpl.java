package com.weeia.networkmanager.services.port.impl;

import com.weeia.networkmanager.dao.networkswitch.SwitchDao;
import com.weeia.networkmanager.services.network.NetworkService;
import com.weeia.networkmanager.services.port.PortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortServiceImpl implements PortService {

    private final SwitchDao switchDao;
    private final NetworkService networkService;

    private String switchIdToIp(long switchId) {
        return switchDao.getOne(switchId).getIpAddress();
    }

    @Autowired
    public PortServiceImpl(SwitchDao switchDao, NetworkService networkService) {
        this.switchDao = switchDao;
        this.networkService = networkService;
    }

    @Override
    public int getPortVlan(long switchId, int portNumber) {
        return networkService.getPortVlan( switchIdToIp(switchId), portNumber );
    }

    @Override
    public void changePortVlan(long switchId, int portNumber, int newVlan) {
        networkService.changePortVlan( switchIdToIp(switchId), portNumber, newVlan );
    }

    @Override
    public int getIncomingTraffic(long switchId, int portNumber) {
        return networkService.getIncomingTraffic( switchIdToIp(switchId), portNumber );
    }

    @Override
    public int getOutgoingTraffic(long switchId, int portNumber) {
        return networkService.getOutgoingTraffic( switchIdToIp(switchId), portNumber );
    }

    @Override
    public boolean getPortEnabled(long switchId, int portNumber) {
        return networkService.getPortEnabled( switchIdToIp(switchId), portNumber );
    }

    @Override
    public void changePortEnabled(long switchId, int portNumber, boolean bEnabled) {
        networkService.changePortEnabled( switchIdToIp(switchId), portNumber, bEnabled );
    }
}
