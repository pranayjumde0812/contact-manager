package com.coderview.smartcontact.service.impl;

import com.coderview.smartcontact.configuration.CustomUserDetailsConfig;
import com.coderview.smartcontact.model.User;
import com.coderview.smartcontact.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { // 2nd step

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.getUserByUserName(username);

//      If user is null (null check)
        if (user == null) {
            throw new UsernameNotFoundException("Could not found user !!");
        }

        CustomUserDetailsConfig customUserDetails = new CustomUserDetailsConfig(user);

        return customUserDetails;
    }
}
