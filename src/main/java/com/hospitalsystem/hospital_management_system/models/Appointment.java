package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @DateTimeFormat(pattern = "HH:mm")
    private Time time;

    private String title;

    private String description;

    @ManyToOne
    @ToString.Exclude
    private Patient patient;

    @ManyToOne
    @ToString.Exclude
    private User user;


}
