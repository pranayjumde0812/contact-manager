package com.coderview.smartcontact.service;


import com.coderview.smartcontact.model.User;

public interface UserService {

    User registerUser(User user);

    User getUserByUsername(String email);

    String changePassword(String oldRawPassword, String newRawPassword, User currentUser);

    int generateSixDigitOTP();

    boolean changePassword(User user, String newPassword);
}
