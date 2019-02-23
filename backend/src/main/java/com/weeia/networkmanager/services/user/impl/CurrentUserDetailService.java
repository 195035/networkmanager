package com.weeia.networkmanager.services.user.impl;

import com.weeia.networkmanager.domain.user.User;
import com.weeia.networkmanager.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CurrentUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByName(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CurrentUser(user);
    }
}
