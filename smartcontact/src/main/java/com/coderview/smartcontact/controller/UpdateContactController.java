package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.helper.Message;
import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.repository.ContactRepo;
import com.coderview.smartcontact.repository.UserRepo;
import com.coderview.smartcontact.service.ContactService;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("user/contact")
public class UpdateContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ContactRepo contactRepo;

    @PostMapping("/update-contact/{currentPage}/{contactId}")
    public String updateContact(@PathVariable("contactId") long contactId,
                                @PathVariable("currentPage") Integer currentPage,
                                Model model, Principal principal) {

        String userName = principal.getName();
        User user = userService.getUserByUsername(userName);
        model.addAttribute("user", user);

        Contact contact = contactService.findContactByContactId(contactId);

        model.addAttribute("contact", contact);
        model.addAttribute("currentPage", currentPage);

        model.addAttribute("title", "Update Contact");
        return "normal/update-contact";
    }

    @PostMapping("/update-contact")
    public String saveUpdatedContact(@ModelAttribute("contact") Contact contact,
                                     @RequestParam("profileImage") MultipartFile file,
                                     @RequestParam("currentPage") Integer currentPage,
                                     Model model, Principal principal,
                                     HttpSession session) {

        try {

            // fetch old contact details
            Contact oldContactDetails = contactService.findContactByContactId(contact.getContactId());


            if (!file.isEmpty()) {
                // delete old photo
                File deleteFile = new ClassPathResource("static/img").getFile();

                new File(deleteFile, oldContactDetails.getImageUrl()).delete();

                // update new photo
                File saveFile = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                contact.setImageUrl(file.getOriginalFilename());

            } else {
                // save old image
                contact.setImageUrl(oldContactDetails.getImageUrl());
            }

            String userName = principal.getName();
            User user = userService.getUserByUsername(userName);

            contact.setUser(user);

//            user.getContacts().add(contact);
//
//            userRepo.save(user);

            contactRepo.save(contact);

            session.setAttribute("contactUpdated", new Message("Contact Updated Successfully", "alert-success"));

        } catch (Exception e) {

        }

        return "redirect:/user/contact-details/" + currentPage + "/" + contact.getContactId();
    }
}
