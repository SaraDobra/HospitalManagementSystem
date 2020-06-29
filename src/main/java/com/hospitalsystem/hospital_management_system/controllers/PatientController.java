package com.hospitalsystem.hospital_management_system.controllers;


import com.hospitalsystem.hospital_management_system.models.Appointment;
import com.hospitalsystem.hospital_management_system.models.Message;
import com.hospitalsystem.hospital_management_system.models.Patient;
import com.hospitalsystem.hospital_management_system.models.Visit;
import com.hospitalsystem.hospital_management_system.repository.PatientRepository;
import com.hospitalsystem.hospital_management_system.services.HelperService;
import com.hospitalsystem.hospital_management_system.services.PatientService;
import com.hospitalsystem.hospital_management_system.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping( "/patient" )
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    private List<Patient> allPatients;

    @Autowired
    private VisitService visitService;

    private String firstThreeCharacters;

    @Autowired
    private HelperService helperService;

    @GetMapping( "/patients" )
    public String showPacients(Model model) {
        List<Patient> patientList = patientService.getAllPatients();
        model.addAttribute("patients", patientList);
        return "patients";
    }

    @GetMapping( "/addPatient" )
    public String showAddPatientPage(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "add-patient";
    }

    @PostMapping( "/addPatient" )
    public String addPatient(@ModelAttribute( "patient" ) Patient patient, Model model) {
        if(patientExists(patient.getIdNr())){
            model.addAttribute("patient",new Patient());
            model.addAttribute("message",new Message("Pacienti Ekziston","warning"));
            return "add-patient";
        }
        patientService.addPatient(patient);
        return "redirect:/patient/patients";
    }

    @RequestMapping( value = "/deletePatient/{idNr}", method = RequestMethod.POST )
    public String deletePatientById(@PathVariable( "idNr" ) String idNr) {
        patientService.deletePatientById(idNr);
        return "redirect:/patient/patients";
    }

    @GetMapping( "/edit/{id}" )
    public String showPatientEditForm(@PathVariable( "id" ) long id, Model model) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        model.addAttribute("patient", patient);
        return "patient-edit";
    }

    @PostMapping( "/edit/{id}" )
    public String editPatient(@PathVariable( "id" ) long id, @Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            patient.setId(id);
            return "patient-edit";
        }
        Patient patientOld = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        Set<Appointment>appointmentList = new HashSet<>();
        Set<Visit>visitList = new HashSet<>();
        appointmentList.addAll(patientOld.getAppointments());
        visitList.addAll(patientOld.getVisits());

        patientOld.getVisits().clear();
        patientOld.getAppointments().clear();

        patient.setVisits(visitList);
        patient.setAppointments(appointmentList);
        patientService.savePatient(patient);
        return "redirect:/patient/patients";
    }

    @RequestMapping( "/patientsAutoComplete" )
    @ResponseBody
    public List<String> patientNamesAutoComplete(@RequestParam( value = "term", required = false, defaultValue = "" ) String term) {
        List<String> suggestions = new ArrayList<>();

        if (term.length() >= 3) {
            firstThreeCharacters = term;
            allPatients = patientService.fetchPatients(term);
        }

        for (Patient patient : allPatients) {
            String patientNameLastName = patient.getFirstName() + " " + patient.getLastName();
            if (patientNameLastName.contains(term)) {
                suggestions.add(patient.getFirstName() + " " + patient.getLastName());
            }
        }
        return suggestions;
    }

    @GetMapping( "/details/{id}" )
    public String showPatientDetails(@PathVariable( "id" ) long id, Model model) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        model.addAttribute("patient", patient);
        return "patient-details";
    }

    @GetMapping( "/details/{patientId}/visit/{visitId}" )
    public String showPatientVisit(@PathVariable( "patientId" ) long patientId, @PathVariable( "visitId" ) long visitId, Model model) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + patientId));
        Optional<Visit> visit = visitService.getVisitById(visitId);
        if (visit.isPresent()) {
            System.out.println(visit.toString());
            model.addAttribute("visitPac", visit.get());
        }
        model.addAttribute("patient", patient);
        return "patient-details";
    }

    @GetMapping( "/details/{patientId}/visit/addVisit" )
    public String showAddVisit(@PathVariable( "patientId" ) long patientId, Model model) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + patientId));
        model.addAttribute("patient", patient);
        Visit visit = new Visit();
        model.addAttribute("visit", visit);
        return "visit-add";
    }


    @PostMapping( "/details/addVisit/{patientId}" )
    public String addVisit(@PathVariable( "patientId" ) long patientId, @ModelAttribute( "visit" ) Visit visit, Model model) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + patientId));
        visit.setPatient(patient);
        visit.setUser(helperService.getLoggedUser());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(visit.getPatient());
        patient.addVisit(visit);
        helperService.getLoggedUser().addVisit(visit);
        Date date = new Date();
        visit.setDate(date);
        visitService.addVisit(visit);
        model.addAttribute("patient", visit.getPatient());
        return "patient-details";
    }

    @PostMapping("/details/visits/deleteVisit/{visitId}")
    public String deleteVisit(@PathVariable("visitId") long visitId){
        visitService.deleteVisit(visitId);
        return "redirect:/patient/patients";
    }

    @GetMapping("/details/{patientId}/visit/editVisit/{visitId}")
    public String showEditVisit(@PathVariable("patientId") long patientId,@PathVariable("visitId") long visitId,Model model){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + patientId));
        model.addAttribute("patient",patient);
        Optional  optionalVisit= visitService.getVisitById(visitId);
        if(optionalVisit.isPresent()){
            model.addAttribute("visit",optionalVisit.get());
        }
        return "visit-edit";
    }


    @PostMapping("/details/visit/editVisit/{visitId}")
    public String editVisit(@Valid Visit visit,@PathVariable("visitId") long visitId,Model model){
        Optional<Visit>  optionalVisit= visitService.getVisitById(visitId);
        visit.setId(visitId);
        if(optionalVisit.isPresent()){
            model.addAttribute("patient",optionalVisit.get().getPatient());
            visit.setPatient(optionalVisit.get().getPatient());
            visit.setUser(optionalVisit.get().getUser());
        }
        Date date = new Date();
        visit.setDate(date);
        System.out.println("EditVisit inside");
        visitService.editVisit(visit);
        return "redirect:/patient/details/"+visit.getPatient().getId();
    }

    private boolean patientExists(String idNr){
        return patientService.patientExists(idNr);
    }


}
