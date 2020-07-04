package com.hospitalsystem.hospital_management_system.services;

import com.hospitalsystem.hospital_management_system.models.User;
import com.hospitalsystem.hospital_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email){
       return userRepository.findByEmail(email);
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

    public void deleteUserById(String userId) {

        Optional<User> user = findById(Long.parseLong(userId));
        if(user.isPresent()){
            user.get().getVisits().clear();
            user.get().getAppointments().clear();

        }
        userRepository.deleteById(Long.parseLong(userId));
    }

    public Optional<User> findById(long userId) {
        return userRepository.findById(userId);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public long usersCount() {
        return userRepository.count();
    }

    public boolean existsByName(String firstName, String lastName) {
        return userRepository.existsByFirstNameAndLastName(firstName,lastName);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
