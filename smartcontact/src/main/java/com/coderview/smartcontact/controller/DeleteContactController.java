package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.helper.Message;
import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.ContactService;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("user")
public class DeleteContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @GetMapping("/contact/delete-contact/{currentPage}/{contactId}")
    public String deleteContact(@PathVariable("contactId") long contactId,
                                @PathVariable("currentPage") Integer currentPage,
                                Model model, Principal principal,
                                HttpSession session) {

        String userName = principal.getName();
        User user = userService.getUserByUsername(userName);

        Contact contactByContactId = contactService.findContactByContactId(contactId);

        if (user.getUserId() == contactByContactId.getUser().getUserId()) {
            String deleteMessage = contactService.deleteContact(contactByContactId);
            session.setAttribute("deleteMessage", new Message("Contact deleted successfully", "alert-success"));
            System.out.println(deleteMessage);
        }
        return "redirect:/user/view-contact/" + currentPage;
    }
}
