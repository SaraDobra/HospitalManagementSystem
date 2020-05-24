package com.hospitalsystem.hospital_management_system.repository;

import com.hospitalsystem.hospital_management_system.models.Role;
import com.hospitalsystem.hospital_management_system.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole( String roleName);
}
