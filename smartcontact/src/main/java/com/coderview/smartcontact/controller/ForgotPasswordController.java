package com.coderview.smartcontact.controller;

import com.coderview.smartcontact.helper.Message;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.EmailService;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // email id form open handler
    @GetMapping("/forgot-password")
    public String openEmailForm(Model model) {

        model.addAttribute("title", "Search Account");
        return "forgot-email-form";
    }

    // Send OTP handler
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam("email") String emailId,
                          Model model, HttpSession session) {

        String returnValue = "";

        model.addAttribute("title", "Verify OTP");

        String purpose = "Smart Contact Manager (SCM) for password reset";
        String companyName = "CODERS VIEW";
        String subject = "OTP for Smart Contact Manager password reset";

        User user = userService.getUserByUsername(emailId);
        System.out.println(user);

        try {

            if (user != null) {

                // Create 6 digit otp
                int otp = userService.generateSixDigitOTP();

                // build an email format
                String message = emailService.buildEmail(user, otp, companyName, purpose);

                // send OTP to the user on email
                boolean result = emailService.sendEmail(subject, message, user.getEmail());

                if (result) {
                    session.setAttribute("otpSend", new Message("OTP sent to your email...", "alert-success"));
                    session.setAttribute("email", emailId);
                    session.setAttribute("generatedOTP", otp);
                    returnValue = "verify-otp-form";

                } else {
                    throw new Exception("Something went in Email service. Email was not send to user");
                }
            } else {
                session.setAttribute("invalidUser", new Message("Email not registered", "alert-danger"));
                returnValue = "redirect:/forgot-password";
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
            session.setAttribute("emailNotSend", new Message("Something went wrong", "alert-danger"));
            returnValue = "redirect:/forgot-password";

        }

        return returnValue;
    }


    // verify otp handler
    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam("otp") int enteredOTP,
                            Model model, HttpSession session) {

        String email = (String) session.getAttribute("email");
        int originalGeneratedOTP = (int) session.getAttribute("generatedOTP");

        if (originalGeneratedOTP == enteredOTP) {

            model.addAttribute("email", email);
            return "change-password";

        } else {
            session.setAttribute("wrongOTP", new Message("You Entered wrong OTP...", "alert-danger"));
            return "verify-otp-form";
        }
    }

    @PostMapping("/change-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String newPassword,
                                HttpSession session) {

        System.out.println(email);
        System.out.println(newPassword);

        User userByUsername = userService.getUserByUsername(email);

        boolean status = userService.changePassword(userByUsername, newPassword);

        if (status) {

            session.setAttribute("success", new Message("Password changes Successfully", "alert-success"));
            return "redirect:/signin";

        } else {

            session.setAttribute("wrong", new Message("Something went wrong...", "alert-danger"));
            return "change-password";
        }

    }
}
