package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.helper.Message;
import com.coderview.smartcontact.model.Contact;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.repository.UserRepo;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class PostSaveContactController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/add-contact")
    public String saveContact(@ModelAttribute("contact") Contact contact,
                              @RequestParam("profileImage") MultipartFile file,
                              Principal principal,
                              Model model,
                              HttpSession session) {

        try {
            String userName = principal.getName();
            System.out.println(userName);

            // fetch user by username
            User user = userService.getUserByUsername(userName);
            System.out.println(user);

            // processing and uploading file
            if (file.isEmpty()) {
                System.out.println("File is empty");
                contact.setImageUrl("default.png");
            } else {
                // upload the file to folder and update the name to contact
                contact.setImageUrl(file.getOriginalFilename());

                File file1 = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            model.addAttribute("user", user);

            // This is bidirectional mapping
            // set user in contacts first
            contact.setUser(user);

            // update the user with new contact added in it
            user.getContacts().add(contact);

            userRepo.save(user);

            // print successfully contact added message
            session.setAttribute("message", new Message("Contact added Successfully","alert-success"));

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());

            // print message if contact not added successfully
            session.setAttribute("message", new Message("Something went wrong! Please try again. ","alert-danger"));

        }

        return "normal/add-contacts";
    }
}
