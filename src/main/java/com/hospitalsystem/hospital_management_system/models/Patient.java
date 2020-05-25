package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idNr;

    private String firstName;

    private String lastName;

    @Email
    @NaturalId
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    private String phoneNr;

    private String address;

    private String city;

    private String country;

    private String note;

    @OneToMany(mappedBy = "patient")
    private Set<Visit> visits;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
}
