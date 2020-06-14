package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String description;

    private String anamneza;

    private String examination;

    private String diagnosis;

    private String recomandation;

    private String medicaments;

    private String instructions;

    @ManyToOne
    @ToString.Exclude
    private Patient patient;

    @ManyToOne
    @ToString.Exclude
    private User user;
}
