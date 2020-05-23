package com.hospitalsystem.hospital_management_system.repository;

import com.hospitalsystem.hospital_management_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
