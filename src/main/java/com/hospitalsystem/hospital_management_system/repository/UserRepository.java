package com.hospitalsystem.hospital_management_system.repository;

import com.hospitalsystem.hospital_management_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    @Query("SELECT u from User u where u.firstName LIKE %:term% or u.lastName LIKE %:term%")
    List<User> fetchUsers(String term);

    User findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmail(String email);
}
