package com.weeia.networkmanager.services.user;

import com.weeia.networkmanager.domain.user.User;
import com.weeia.networkmanager.domain.user.UserCreateForm;

import java.util.Collection;

public interface UserService {


    User save(User user);

    User get(int id);

    User update(User user);

    void delete(User user);

    boolean validate(String username, String password);

    User findByName(String name);

    User create(UserCreateForm createForm);

    boolean checkUniqueUsername(String username);

    Collection<User> getAll();
}
