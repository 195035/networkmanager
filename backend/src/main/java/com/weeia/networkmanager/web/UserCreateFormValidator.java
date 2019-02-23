package com.weeia.networkmanager.web;

import com.weeia.networkmanager.domain.user.UserCreateForm;
import com.weeia.networkmanager.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserCreateFormValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserCreateFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateForm form = (UserCreateForm) target;
        validateUsername(form.getUsername(), errors);
        validatePassword(form.getPassword(), form.getPasswordRepeated(), errors);
    }

    private void validatePassword(String password, String passwordRepeated, Errors errors) {
        if(validatePasswordSigns(password, errors)) {
            validatePasswordSameAsRepeated(password, passwordRepeated, errors);
        }
    }

    private void validatePasswordSameAsRepeated(String password, String passwordRepeated, Errors errors) {
        if(!password.equals(passwordRepeated)) {
            errors.rejectValue("password", "auth.password.no_match", "auth.password.no_match");
        }
    }

    private boolean validatePasswordSigns(String password, Errors errors) {
        return checkForbiddenSigns("password", password, errors);
    }

    private void validateUsername(String username, Errors errors) {
        if (validateUsernameSigns(username, errors)) {
            validateUniqueUsername(username, errors);
        }
    }

    private void validateUniqueUsername(String username, Errors errors) {
        if(!userService.checkUniqueUsername(username)) {
            errors.rejectValue("username", "auth.username.exists", "auth.username.exists");
        }
    }

    private boolean validateUsernameSigns(String username, Errors errors) {
        return checkForbiddenSigns("username", username, errors);
    }

    private boolean checkForbiddenSigns(String field, String value, Errors errors) {
        for(char c : value.toCharArray()) {
            if(!Character.isAlphabetic(c) && !Character.isDigit(c) && c != '_' && c != '-' ) {
                errors.rejectValue(field,"auth.forbidden.sign", "auth.forbidden.sign");
                return false;
            }
        }
        return true;
    }
}
