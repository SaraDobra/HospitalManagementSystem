package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String anamneza;

    private String examination;

    private String diagnosis;

    private String recomandation;

    private String medicaments;

    private String instructions;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private User user;


}
