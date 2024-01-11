package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.helper.Message;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.ContactService;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    // method for adding common data (common logged-in user)
    // after providing @ModelAttribute annotation it will pass common data to all the handler in class
    @ModelAttribute
    public void addLoggedInUser(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println(userName);

        // fetch user by username
        User user = userService.getUserByUsername(userName);
        System.out.println(user);

        model.addAttribute("user", user);
    }
    // handler for changing password processing

    @PostMapping("/user/settings/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Principal principal, Model model, HttpSession session) {

        User currentUser = userService.getUserByUsername(principal.getName());


        String status = userService.changePassword(oldPassword, newPassword, currentUser);

        System.out.println(status);

        if (status.equals("success")) {
            session.setAttribute("info", new Message("Password change successfully", "alert-success"));
        } else {
            session.setAttribute("info", new Message("Please enter correct old password", "alert-danger"));
        }

        return "redirect:/user/settings";
    }
}
