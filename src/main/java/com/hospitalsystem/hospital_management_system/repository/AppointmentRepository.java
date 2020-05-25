package com.hospitalsystem.hospital_management_system.repository;

import com.hospitalsystem.hospital_management_system.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {


}
