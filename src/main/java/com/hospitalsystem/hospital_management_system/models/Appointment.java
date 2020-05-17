package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private Time time;

    private String title;

    private String description;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private User user;


}
