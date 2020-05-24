package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;
import lombok.ToString;


import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentName;

    private String departmentDescription;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "department")
    @ToString.Exclude
    private Set<User> users;


}
