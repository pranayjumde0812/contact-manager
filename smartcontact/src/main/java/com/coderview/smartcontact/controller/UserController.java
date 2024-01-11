package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.helper.Message;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    // Handler for register user
    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute("user") User user,
                                  BindingResult result1,
                                  @RequestParam(value = "agreement",
                                          defaultValue = "false") boolean agreement,
                                  Model model,
                                  HttpSession session) {

        try {
            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                throw new Exception("You have not agreed the terms and conditions");

//                model.addAttribute("errorMsg", "Please select terms and condition");
//                return "signup";
            }

            //Check for Server side Validation
            if (result1.hasErrors()) {
                System.out.println("ERROR " + result1);
                model.addAttribute("user", user);
                return "signup";
            }

            User saveUser = userService.registerUser(user);

            model.addAttribute("user", new User());
            session.setAttribute("message", new Message("Successfully Registered!!! ", "alert-success"));

            return "signup";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong!!! " + e.getMessage(), "alert-danger"));

            return "signup";
        }

    }

}
