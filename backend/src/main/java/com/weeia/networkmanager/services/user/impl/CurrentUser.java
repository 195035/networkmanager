package com.weeia.networkmanager.services.user.impl;

import com.weeia.networkmanager.domain.user.User;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public CurrentUser(User user) {
        super(user.getUsername(), user.getPasswordHash(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
