package com.hospitalsystem.hospital_management_system.services;

import com.hospitalsystem.hospital_management_system.models.User;
import com.hospitalsystem.hospital_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HelperService{

    @Autowired
    UserRepository userRepository;

    public String getLoggedUserName(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        User user = userRepository.findByEmail(email);
        if(user != null){
            return user.getFirstName()+" "+user.getLastName();
        }
        return "";

    }

}
