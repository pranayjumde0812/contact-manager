package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.ContactService;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserSidebarController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

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

    // Handler for profile
    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("title", "Profile");
        return "normal/profile";
    }

    // Handler for watching add contacts form
    @GetMapping("/add-contact")
    public String addContact(Model model) {

        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "normal/add-contacts";
    }


    // Handler for view total contacts of logged-in user
    // Now Pagination work start
    // per page contact sow = 5 [n]
    // current page = 0 [page]
    @GetMapping("/view-contact/{page}")
    public String viewContact(@PathVariable("page") Integer page, Model model, Principal principal) {
        model.addAttribute("title", "View Contact");
        // fetch contact list
        String userName = principal.getName();
        User user = userService.getUserByUsername(userName);
//        List<Contact> userContacts = user.getContacts();

        Page<Contact> contactListOfLoggedInUser = contactService.findContactByUser(user.getUserId(), page);

        model.addAttribute("contactList", contactListOfLoggedInUser);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactListOfLoggedInUser.getTotalPages());

        return "normal/view-contact";
    }


    // Handler for settings
    @GetMapping("/settings")
    public String settings(Model model) {

        model.addAttribute("title", "Settings");
        return "normal/settings";
    }
}
