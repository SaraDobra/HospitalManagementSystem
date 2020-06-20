package com.hospitalsystem.hospital_management_system.controllers;

import com.hospitalsystem.hospital_management_system.models.Appointment;
import com.hospitalsystem.hospital_management_system.services.AppointmentService;
import com.hospitalsystem.hospital_management_system.services.PatientService;
import com.hospitalsystem.hospital_management_system.services.UserService;
import com.hospitalsystem.hospital_management_system.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    PatientService patientService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    VisitService visitService;

    @Autowired
    UserService userService;

    @GetMapping("/dashboard")
    public String showHome(Model model){
        model.addAttribute("patientsCount",patientService.patientCount());
        model.addAttribute("visitsCount",visitService.visitsCount());
        model.addAttribute("appointmentsCount",appointmentService.appointmentsCount());
        model.addAttribute("usersCount",userService.usersCount());
        return "/home/dashboard";
    }

    @GetMapping("frag")
    public String showFragments(){
        return "fragments";
    }

    @RequestMapping(value= {"/access_denied"}, method= RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/access_denied");
        return model;
    }

}
