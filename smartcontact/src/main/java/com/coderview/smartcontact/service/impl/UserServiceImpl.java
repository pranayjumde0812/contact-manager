package com.coderview.smartcontact.service.impl;

import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.repository.UserRepo;
import com.coderview.smartcontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Override
    public User registerUser(User user) {
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setImageUrl("default.png");

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepo.save(user);

        return saveUser;
    }

    @Override
    public User getUserByUsername(String email) {

        User user = userRepo.getUserByUserName(email);

        return user;
    }

    @Override
    public String changePassword(String oldRawPassword, String newRawPassword, User currentUser) {

        String status = "fail";
        boolean matches = passwordEncoder.matches(oldRawPassword, currentUser.getPassword());

        if (matches) {

            currentUser.setPassword(passwordEncoder.encode(newRawPassword));

            User save = userRepo.save(currentUser);

            status = "success";
        }

        return status;
    }


    Random random = new Random();

    @Override
    public int generateSixDigitOTP() {

        int otp = 100000 + random.nextInt(900000);

        return otp;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {

        boolean status = false;

        if (user != null) {

            String encodedPassword = passwordEncoder.encode(newPassword);
            System.out.println(encodedPassword);
            user.setPassword(encodedPassword);

            userRepo.save(user);

            status = true;
        }

        return status;
    }

}
