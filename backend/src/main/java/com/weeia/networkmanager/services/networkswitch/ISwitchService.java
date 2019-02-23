package com.weeia.networkmanager.services.networkswitch;

import com.weeia.networkmanager.domain.networkswitch.Switch;

import java.util.List;

public interface ISwitchService {
    List<Switch> getAll();
    void addSwitch(Switch aSwitch);

    Switch findByIpAddress(String ip);
    Switch findById(Long id);

    void delete(long id);
}
