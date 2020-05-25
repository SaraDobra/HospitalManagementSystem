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

    private Date date;

    private Time time;

    private String title;

    private String description;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Patient patient;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;


}
