package com.hospitalsystem.hospital_management_system.controllers;

import com.hospitalsystem.hospital_management_system.models.*;
import com.hospitalsystem.hospital_management_system.services.AppointmentService;
import com.hospitalsystem.hospital_management_system.services.HelperService;
import com.hospitalsystem.hospital_management_system.services.PatientService;
import com.hospitalsystem.hospital_management_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
   private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private HelperService helperService;

    @GetMapping("/appointments")
    public String  showAllAppointments(Model model){
        User loggedUser = helperService.getLoggedUser();
        List<Appointment> appointments = new ArrayList<>();
        Role userRole = null;
        for(Role r : loggedUser.getRoles()){
            userRole = r;
        }
        if((userRole.getRole().toString().equals(RoleName.ROLE_ADMIN.toString())) || (userRole.getRole().toString().equals(RoleName.ROLE_RECEPTIONIST.toString()))){
            appointments = appointmentService.getAllAppointments();
            model.addAttribute("appointments",appointments);
            return "/appointment/appointments";
        }
        appointments = appointmentService.getAppointmentsByUser(loggedUser);
        model.addAttribute("appointments",appointments);
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

       return "redirect:/appointment/appointments";
    }

    @PostMapping("/deleteAppointment/{appointmentId}")
    public String deleteAppointment(@PathVariable("appointmentId") long appointmentId){
        appointmentService.deleteAppointmentById(appointmentId);
        return "redirect:/appointment/appointments";
    }

}
