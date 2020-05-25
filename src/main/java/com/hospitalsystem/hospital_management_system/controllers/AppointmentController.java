package com.hospitalsystem.hospital_management_system.controllers;

import com.hospitalsystem.hospital_management_system.models.Appointment;
import com.hospitalsystem.hospital_management_system.models.Patient;
import com.hospitalsystem.hospital_management_system.models.User;
import com.hospitalsystem.hospital_management_system.services.AppointmentService;
import com.hospitalsystem.hospital_management_system.services.PatientService;
import com.hospitalsystem.hospital_management_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
   private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public String  showAllAppointments(Model model){

        return "/appointment/appointments";
    }

    @GetMapping("/addAppointment")
    public String showAddAppointment(Model model){
        Appointment appointment = new Appointment();
        model.addAttribute("appointment",appointment);
        return "/appointment/add-appointment";
    }


    @PostMapping("/addAppointment")
    public String addAppointment(@ModelAttribute("appointment") Appointment appointment, @Valid String patient_name,@Valid String doctor_name,@Valid String date_time){
        System.out.println("---->>>Appointment:"+appointment.toString());
        System.out.println("--->>>Patient Name"+patient_name);
        System.out.println("--->>>Koha "+date_time);
        Patient patient = patientService.findByNameAndLastName(patient_name.split(" ")[0],patient_name.split(" ")[1]);
        appointment.setPatient(patient);
        User user = userService.findByFirstNameAndLastName(doctor_name.split(" ")[0],doctor_name.split(" ")[1]);
        appointment.setUser(user);
        patient.addAppointment(appointment);
        user.addAppointment(appointment);
        System.out.println("----Patient "+patient.toString());
        System.out.println("----Doctor "+user.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date= date_time.split("T")[0];
        String time= date_time.split("T")[1];
        String dateTimeString= date+" "+time;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        Date dateAppointment = java.sql.Date.valueOf(dateTime.toLocalDate());
        Time timeAppointment = java.sql.Time.valueOf(dateTime.toLocalTime());
        appointment.setDate(dateAppointment);
        appointment.setTime(timeAppointment);
        System.out.println("---->>>Appointment:"+appointment.toString());
        appointmentService.addAppointment(appointment);

       return "redirect:/admin/users";
    }
}
