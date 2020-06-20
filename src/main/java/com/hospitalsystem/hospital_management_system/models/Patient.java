package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "patient",fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Visit> visits;


    public List<Visit> getVisits(){
        List<Visit> visits = new ArrayList<>(this.visits);
        visits.sort(Comparator.comparing(Visit::getDate).reversed());
       return visits;
    }

    @OneToMany(mappedBy = "patient" , orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public String fullName(){
        return firstName + " "+lastName;
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
    }

    public void removeVisit(Visit visit){
        visit.setPatient(null);
        visits.remove(visit);
    }
}
