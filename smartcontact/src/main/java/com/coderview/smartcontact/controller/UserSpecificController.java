package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class UserSpecificController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/index")
    public String userDashboard(Model model, Principal principal) {

        String userName = principal.getName();
        System.out.println(userName);

        // fetch user by username
        User user = userService.getUserByUsername(userName);
        System.out.println(user);

        model.addAttribute("user", user);
        model.addAttribute("title", "USER - Dashboard");

        return "/normal/user-dashboard";
    }

    @RequestMapping("/admin/index")
    public String adminDashboard(Principal principal, Model model) {

        String userName = principal.getName();
        System.out.println(userName);

        // fetch user by username
        User user = userService.getUserByUsername(userName);
        System.out.println(user);

        model.addAttribute("user", user);

        model.addAttribute("title", "Admin - Dashboard");

        return "/admin/admin-dashboard";
    }

}
