package net.vatri.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(Model user, @RequestParam(value = "email", required = true) String email){
        return "Hello";
    }

}
