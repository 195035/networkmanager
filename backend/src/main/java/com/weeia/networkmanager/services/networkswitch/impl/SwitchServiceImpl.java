package com.weeia.networkmanager.services.networkswitch.impl;

import com.weeia.networkmanager.dao.networkswitch.SwitchDao;
import com.weeia.networkmanager.domain.networkswitch.Switch;
import com.weeia.networkmanager.services.networkswitch.ISwitchService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitchServiceImpl implements ISwitchService {

    private final SwitchDao switchDao;

    @Autowired
    public SwitchServiceImpl(SwitchDao switchDao) {
        this.switchDao = switchDao;
    }

    @Override
    public List<Switch> getAll() {
        return switchDao.findAll();
    }

    @Override
    public void addSwitch(Switch aSwitch) {
        switchDao.save(aSwitch);
    }

    @Override
    public Switch findByIpAddress(String ip) {
        return switchDao.findByIpAddress(ip).orElse(null);
    }

    @Override
    public Switch findById(Long id) {
        return switchDao.findById(id).orElse(null);
    }
}
