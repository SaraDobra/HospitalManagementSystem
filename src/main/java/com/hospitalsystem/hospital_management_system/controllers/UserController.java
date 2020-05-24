package com.hospitalsystem.hospital_management_system.controllers;

import com.hospitalsystem.hospital_management_system.models.Department;
import com.hospitalsystem.hospital_management_system.models.Role;
import com.hospitalsystem.hospital_management_system.models.RoleName;
import com.hospitalsystem.hospital_management_system.models.User;
import com.hospitalsystem.hospital_management_system.services.DepartmentService;
import com.hospitalsystem.hospital_management_system.services.RoleService;
import com.hospitalsystem.hospital_management_system.services.UserService;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleService roleService;




    @RequestMapping(value= {"/", "/login"}, method= RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();

        model.setViewName("user/login");
        return model;
    }

    @GetMapping("/admin/users")
    public String showUsers(Model model){
        List<User> userList = userService.getAllUsers();
        for(User user :userList){
            System.out.println(user.getRoles());
        }
        model.addAttribute("users",userList);
        return "/user/users";

    }

    @GetMapping("/admin/addUser")
    public String showaddUserForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        model.addAttribute("departments",departmentService.getAllDepartments());
        model.addAttribute("titles",getTitles());
        model.addAttribute("roles", roleService.getAllRoles());

        return "user/add-user";

    }

    private String[] getTitles(){
     return new String []{"Doktor","Mjeke","Moter","Teknik"};
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("patient") User user,@Valid  String departmentId,@Valid String roleId){
       Optional<Department> department = departmentService.findById(departmentId);
        department.ifPresent(user::setDepartment);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive("1");
        Optional<Role> role = roleService.getRoleById(roleId);
        role.ifPresent(user::addRole);
        userService.addPatient(user);
        return "redirect:/admin/users";
    }

}
