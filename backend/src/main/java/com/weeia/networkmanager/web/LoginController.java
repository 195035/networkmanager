package com.weeia.networkmanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Your username/email or password is invalid.");

        return "user_login";
    }

}
