package com.hospitalsystem.hospital_management_system.services;

import com.hospitalsystem.hospital_management_system.models.Appointment;
import com.hospitalsystem.hospital_management_system.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public void addAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }
}
