package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.ContactService;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchContactController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;


    // handler for search
    @GetMapping("/user/contact/search/{query}")
    public ResponseEntity<?> searchContact(@PathVariable("query") String query,
                                           Principal principal) {

        User user = userService.getUserByUsername(principal.getName());
        List<Contact> searchedContactByKeywords = contactService.findContactByNameContainingKeywords(query, user);

        return ResponseEntity.ok(searchedContactByKeywords);
    }

}
