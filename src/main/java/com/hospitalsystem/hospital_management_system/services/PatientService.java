package com.hospitalsystem.hospital_management_system.services;

import com.hospitalsystem.hospital_management_system.models.Patient;
import com.hospitalsystem.hospital_management_system.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientService {

    @Autowired
    PatientRepository patientRepository;



    public void addPatient(Patient patient){
        patientRepository.save(patient);
    }


    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public void deletePatientById(String idNr) {
        patientRepository.deleteByIdNr(idNr);
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }
}
