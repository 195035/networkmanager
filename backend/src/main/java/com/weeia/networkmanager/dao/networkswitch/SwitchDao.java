package com.weeia.networkmanager.dao.networkswitch;

import com.weeia.networkmanager.domain.networkswitch.Switch;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwitchDao extends JpaRepository<Switch, Long> {
    Optional<Switch> findByIpAddress(String ip);
}
