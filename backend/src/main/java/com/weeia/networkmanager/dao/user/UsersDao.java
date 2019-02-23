package com.weeia.networkmanager.dao.user;

import com.weeia.networkmanager.domain.user.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDao extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
