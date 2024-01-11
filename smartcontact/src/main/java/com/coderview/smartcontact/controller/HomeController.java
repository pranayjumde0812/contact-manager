package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    //    Home Handler
    @RequestMapping("/")
    public String home(Model model) {

        model.addAttribute("title", "Home - Smart Contact Manager");

        return "index";
    }

    //    About Handler
    @RequestMapping("/about")
    public String about(Model model) {

        model.addAttribute("title", "About - Smart Contact Manager");

        return "about";
    }

    //    Signup Handler
    @RequestMapping("/signup")
    public String signup(Model model, User user) {

        model.addAttribute("title", "Signup - Smart Contact Manager");
        model.addAttribute("user", user);

        return "signup";
    }

    //     Login Handler
    @RequestMapping("/signin")
    public String login(Model model) {

        model.addAttribute("login", "Login - Smart Contact Manager");

        return "login";
    }


}
