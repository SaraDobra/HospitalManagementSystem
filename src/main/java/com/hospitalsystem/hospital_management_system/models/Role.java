package com.hospitalsystem.hospital_management_system.models;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Table( name = "role" )
public class Role {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Enumerated( EnumType.STRING )
    @NaturalId
    @Column( length = 60 )
    private RoleName role;

}