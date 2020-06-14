package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
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

    private String active;

    private String specialization;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
    	joinColumns = @JoinColumn(name = "user_id"),
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    @EqualsAndHashCode.Exclude
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Department department;

    @OneToMany(mappedBy = "user" , orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Visit> visits;

    @OneToMany(mappedBy = "user" , orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Appointment> appointments;

    public void addRole(Role role){
        roles.add(role);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public String fullName(){
        return firstName + " "+lastName;
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
    }

    public String getRole(){
        return roles.stream().findFirst().get().getRole().name();
    }

    public void removeVisit(Visit visit){
        visit.setUser(null);
        visits.remove(visit);
    }
}