package io.khasang.wlogs.controller;

import io.khasang.wlogs.form.UserRegistrationForm;
import io.khasang.wlogs.model.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.GET)
    public String createForm(@ModelAttribute("userRegistrationForm")UserRegistrationForm userRegistrationForm) {
        return "wlogs_user_registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createHandler(@Valid UserRegistrationForm userRegistrationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "wlogs_user_registration";
        }
        try {
            this.userManager.create(userRegistrationForm);
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("userRegistrationForm", e.getMessage()));
            return "wlogs_user_registration";
        }
        return "redirect:auth/login";
    }
}
