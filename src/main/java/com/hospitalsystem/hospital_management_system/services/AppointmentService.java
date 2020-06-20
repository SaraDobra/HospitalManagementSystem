package com.hospitalsystem.hospital_management_system.services;

import com.hospitalsystem.hospital_management_system.models.Appointment;
import com.hospitalsystem.hospital_management_system.models.User;
import com.hospitalsystem.hospital_management_system.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public void addAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAllByOrderByDateDesc();
    }

    public List<Appointment> getAppointmentsByUser(User loggedUser) {
        return appointmentRepository.findByUserOrderByDateDesc(loggedUser);
    }

    public void deleteAppointmentById(long appointmentId) {
        Optional<Appointment> appointment= appointmentRepository.findById(appointmentId);
        System.out.println(appointment.get().toString()+" --------------<<<<<<<<<<<<<<");
        appointment.ifPresent(value -> appointmentRepository.delete(value));
    }

    public Optional<Appointment> getAppointmentById(long id) {
        return appointmentRepository.findById(id);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public long appointmentsCount() {
        return appointmentRepository.count();
    }
}
