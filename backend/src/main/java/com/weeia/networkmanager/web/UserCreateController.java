package com.weeia.networkmanager.web;

import com.weeia.networkmanager.domain.user.UserCreateForm;
import com.weeia.networkmanager.services.user.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserCreateController {

    public static final String PAGE_REGISTER = "/web/addUser";

    private final UserService userService;
    private final UserCreateFormValidator validator;

    @Autowired
    public UserCreateController(UserService userService, UserCreateFormValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = PAGE_REGISTER, method = RequestMethod.GET)
    public String getUserCreatePage(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new UserCreateForm());
        }
        return "user_create";
    }

    @RequestMapping(value = PAGE_REGISTER, method = RequestMethod.POST)
    public String handleUSerCreateForm(@Valid @ModelAttribute("form")UserCreateForm form, BindingResult result, RedirectAttributes attrs, HttpSession session) {
        if(result.hasErrors()) {
            attrs.addFlashAttribute("org.springframework.validation.BindingResult.form", result);
            attrs.addFlashAttribute("form", form);
            return "redirect:/web/addUser";
        }
        try {
            userService.create(form);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/web/addUser";
        }
        session.invalidate();
        return "redirect:/";
    }
}
