package com.coderview.smartcontact.service;

import com.coderview.smartcontact.model.User;

public interface EmailService {

    boolean sendEmail(String subject, String message, String recipient);

    String buildEmail(User user, int generatedOTP, String companyName, String purpose);
}
