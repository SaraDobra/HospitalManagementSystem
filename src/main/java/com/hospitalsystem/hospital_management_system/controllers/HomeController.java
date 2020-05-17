package com.hospitalsystem.hospital_management_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/dashboard")
    public String showHome(){
        return "dashboard";
    }

    @GetMapping("frag")
    public String showFragments(){
        return "fragments";
    }

}
