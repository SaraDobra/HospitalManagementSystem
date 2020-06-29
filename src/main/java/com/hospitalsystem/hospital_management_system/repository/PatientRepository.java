package com.hospitalsystem.hospital_management_system.repository;

import com.hospitalsystem.hospital_management_system.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    void deleteByIdNr(String idNr);

    boolean existsByIdNr(String idNr);

    @Query("SELECT p from Patient p where p.firstName LIKE %:term% or p.lastName LIKE %:term%")
    List<Patient> fetchPatients(String term);

    Patient findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByFirstNameAndLastName(String fistName,String lastName);
}
