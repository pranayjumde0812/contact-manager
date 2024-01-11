package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.ContactService;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class GetContactDetailsController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

//    // method for adding common data (common logged-in user)
//    // after providing @ModelAttribute annotation it will pass common data to all the handler in class
//    @ModelAttribute
//    public void addLoggedInUser(Model model, Principal principal) {
//        String userName = principal.getName();
//        System.out.println(userName);
//
//        // fetch user by username
//        User user = userService.getUserByUsername(userName);
//        System.out.println(user);
//
//        model.addAttribute("user", user);
//    }

    @GetMapping("/user/contact-details/{currentPage}/{contactId}")
    public String getContactDetails(@PathVariable("contactId") long contactId,
                                    @PathVariable("currentPage") Integer currentPage,
                                    Model model, Principal principal) {

        String userName = principal.getName();
        User user = userService.getUserByUsername(userName);

        Contact contactByContactId = contactService.findContactByContactId(contactId);
        System.out.println(contactByContactId);

        model.addAttribute("user", user);

        model.addAttribute("title", "Contact Details");
        model.addAttribute("currentPage", currentPage);

        if (user.getUserId() == contactByContactId.getUser().getUserId()) {
            model.addAttribute("contactDetails", contactByContactId);
        }

        return "normal/contact-details";
    }
}
