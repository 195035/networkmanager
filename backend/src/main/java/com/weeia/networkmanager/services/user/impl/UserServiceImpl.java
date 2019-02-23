package com.weeia.networkmanager.services.user.impl;

import com.weeia.networkmanager.domain.user.User;
import com.weeia.networkmanager.domain.user.UserCreateForm;
import com.weeia.networkmanager.dao.user.UsersDao;
import com.weeia.networkmanager.services.user.UserService;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {


    private final UsersDao usersDao;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UsersDao usersDao, PasswordEncoder encoder) {
        this.usersDao = usersDao;
        this.encoder = encoder;
        initializeAdmin();
    }

    private void initializeAdmin() {
        User admin = usersDao.findByUsername("admin").orElseGet(() -> new User("admin", encoder.encode("admin")));
        save(admin);
    }

    @Override
    @Transactional
    public User save(User user) {
        return usersDao.save(user);
    }

    @Override
    public User get(int id) {
        return usersDao.getOne(id);
    }

    @Override
    @Transactional
    public User update(User user) {
        return usersDao.save(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        usersDao.delete(user);
    }

    @Override
    public boolean validate(String username, String password) {
        User user = findByName(username);
        return user != null && user.getPasswordHash().equals(password);
    }

    @Override
    public User findByName(String name) {
        return usersDao.findByUsername(name).orElse(null);
    }

    @Override
    public User create(UserCreateForm createForm) {
        User user = new User();
        user.setUsername(createForm.getUsername());
        user.setPasswordHash(encoder.encode(createForm.getPassword()));
        return save(user);
    }

    @Override
    public boolean checkUniqueUsername(String username) {
        return findByName(username) == null;
    }

    @Override
    public Collection<User> getAll() {
        return usersDao.findAll();
    }
}
