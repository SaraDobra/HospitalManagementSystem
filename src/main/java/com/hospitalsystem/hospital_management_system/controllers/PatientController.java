package com.hospitalsystem.hospital_management_system.controllers;


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

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
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

    @GetMapping("/patients")
    public String showPacients(Model model){
       List<Patient> patientList =  patientService.getAllPatients();
       model.addAttribute("patients",patientList);
        return "patients";
    }

    @GetMapping("/addPatient")
    public String showAddPatientPage(Model model){
        Patient patient = new Patient();
        model.addAttribute("patient",patient);

        return "add-patient";
    }

    @PostMapping("/addPatient")
    public String addPatient(@ModelAttribute("patient") Patient patient){
        System.out.println("ADD PATIENT >>>>>>>>>>"+patient.toString());
        patientService.addPatient(patient);
        return "redirect:/patient/patients";
    }

    @RequestMapping(value = "/deletePatient/{idNr}", method = RequestMethod.POST)
    public String deletePatientById(@PathVariable("idNr") String idNr){
        patientService.deletePatientById(idNr);
        return "redirect:/patient/patients";
    }

    @GetMapping("/edit/{id}")
    public String showPatientEditForm(@PathVariable("id") long id,Model model){
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        model.addAttribute("patient",patient);
        return "patient-edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, @Valid Patient patient, BindingResult result,Model model){
        if(result.hasErrors()){
            patient.setId(id);
            return "patient-edit";
        }

        patientService.savePatient(patient);
        return "redirect:/patient/patients";
    }

    @RequestMapping("/patientsAutoComplete")
    @ResponseBody
    public List<String> patientNamesAutoComplete(@RequestParam(value="term", required = false, defaultValue="") String term){
        List<String> suggestions = new ArrayList<>();

        if(term.length() >= 3){
            firstThreeCharacters = term;
            allPatients = patientService.fetchPatients(term);
        }

        for(Patient patient : allPatients){
            String patientNameLastName = patient.getFirstName()+" "+patient.getLastName();
            if (patientNameLastName.contains(term)) {
                suggestions.add(patient.getFirstName()+" "+patient.getLastName());
            }
        }
        return suggestions;
    }

    @GetMapping("/details/{id}")
    public String showPatientDetails(@PathVariable("id") long id,Model model){
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        System.out.println("visits");
        for(Visit visit: patient.getVisits()){
            System.out.println(visit);
        }
        model.addAttribute("patient",patient);
        return "patient-details";
    }

    @GetMapping("/details/{patientId}/visit/{visitId}")
    public String showPatientVisit(@PathVariable("patientId") long patientId,@PathVariable("visitId") long visitId,Model model){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + patientId));
        Optional<Visit> visit = visitService.getVisitById(visitId);
        if(visit.isPresent()){
            System.out.println(visit.toString());
            model.addAttribute("visitPac",visit.get());
        }
        model.addAttribute("patient",patient);
        return "patient-details";
    }

    @GetMapping("/details/{patientId}/visit/addVisit")
    public String showAddVisit(@PathVariable("patientId") long patientId, Model model){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + patientId));
        model.addAttribute("patient",patient);
        Visit visit = new Visit();
        model.addAttribute("visit",visit);
        return "visit-add";
    }


    @PostMapping("/details/addVisit/{patientId}")
    public String addVisit(@PathVariable("patientId") long patientId,@ModelAttribute("visit") Visit visit,Model model){
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
        model.addAttribute("patient",visit.getPatient());
        return "patient-details";
    }


}
