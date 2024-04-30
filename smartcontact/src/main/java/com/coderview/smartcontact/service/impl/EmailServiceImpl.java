package com.coderview.smartcontact.service.impl;

import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.service.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public boolean sendEmail(String subject, String message, String recipient) {

        boolean flag = false;

        // sender is constant
        String sender = "codersview08@gmail.com";

        // variable for gmail
        String host = "smtp.gmail.com";

        // get the system properties
        Properties properties = System.getProperties();
        System.out.println("Properties " + properties);

        // setting important information to properties object
        // setting host
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("codersview08@gmail.com", "ccuc eavh wsji umxa");
            }
        });
        session.setDebug(true);

        // Step 2 : Compose the message [text,multimedia]
        MimeMessage mimeMessage = new MimeMessage(session);

        try {

            // from email
            mimeMessage.setFrom(sender);

            // adding recipient to message
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // adding subject to message
            mimeMessage.setSubject(subject);

            // adding text to message
//            mimeMessage.setText(message);
            mimeMessage.setContent(message, "text/html");

            // send
            // Step 3 : send the message using Transport class

            Transport.send(mimeMessage);

            System.out.println("Send Successfully");

            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public String buildEmail(User user, int generatedOTP, String companyName, String purpose) {

        StringBuilder emailBuilder = new StringBuilder();

        emailBuilder.append("<div style=\"font-family: Arial, sans-serif; border:1px solid #e2e2e2; padding:20px \">\n");
        emailBuilder.append("\t<p>Dear ").append(user.getName()).append(",</p>\n\n");
        emailBuilder.append("\t<p>Your One-Time Password (OTP) for ").append(purpose).append(" is: <strong>").append(generatedOTP).append("</strong>.</p>\n\n");
        emailBuilder.append("\t<p>If you didn't request this OTP, please contact us immediately.</p>\n\n");
        emailBuilder.append("\t<p>Best regards,<br>").append("\t\t").append(companyName).append("</p>\n");
        emailBuilder.append("</div>");


        return emailBuilder.toString();
    }
}
