package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentName;

    private String departmentDescription;

    @OneToMany(mappedBy = "department")
    private Set<User> users;
}
