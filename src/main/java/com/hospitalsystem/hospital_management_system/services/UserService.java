package com.hospitalsystem.hospital_management_system.services;

import com.hospitalsystem.hospital_management_system.models.User;
import com.hospitalsystem.hospital_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email){
       return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addPatient(User user) {
        userRepository.save(user);
    }

    public List<User> fetchUsers(String term) {
        return userRepository.fetchUsers(term);
    }

    public User findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName,lastName);
    }
}
