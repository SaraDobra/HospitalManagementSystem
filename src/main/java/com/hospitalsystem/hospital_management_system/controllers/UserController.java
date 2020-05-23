package com.hospitalsystem.hospital_management_system.controllers;

import com.hospitalsystem.hospital_management_system.models.User;
import com.hospitalsystem.hospital_management_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(value= {"/", "/login"}, method= RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();

        model.setViewName("user/login");
        return model;
    }

    @GetMapping("/admin/users")
    public String showUsers(Model model){
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users",userList);
        return "users";

    }
}
