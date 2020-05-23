package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String firstName;

    @NotBlank
    @Size(min=3, max = 50)
    private String lastName;

    @NotBlank
    @Size(min=3, max = 50)
    private String phoneNr;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min=6, max = 100)
    private String password;

    private String title;

    private String active;

    private String specialization;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
    	joinColumns = @JoinColumn(name = "user_id"),
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "user")
    private Set<Visit> visits;

    @OneToMany(mappedBy = "user")
    private Set<Appointment> appointments;

}