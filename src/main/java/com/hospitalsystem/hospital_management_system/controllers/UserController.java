package com.hospitalsystem.hospital_management_system.controllers;

import com.hospitalsystem.hospital_management_system.models.*;
import com.hospitalsystem.hospital_management_system.services.DepartmentService;
import com.hospitalsystem.hospital_management_system.services.RoleService;
import com.hospitalsystem.hospital_management_system.services.UserService;
import org.dom4j.rule.Mode;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private String firstThreeCharacters;

    private List<User> allUsers;


    @RequestMapping(value= {"/", "/login"}, method= RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();

        model.setViewName("user/login");
        return model;
    }

    @GetMapping("/admin/users")
    public String showUsers(Model model){
        List<User> userList = userService.findAll();
        for(User user :userList){
            System.out.println(user.getRoles());
        }
        Stream<User> users = userService.findAll().stream().filter(user -> !user.getRole().equals(RoleName.ROLE_ADMIN.name()));
        model.addAttribute("users",users.collect(Collectors.toList()));
        return "/user/users";

    }

    @RequestMapping( value = "/admin/deleteUser/{userId}", method = RequestMethod.POST )
    public String deleteUserById(@PathVariable( "userId" ) String userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/editUser/{userId}")
    public String showEditUser(@PathVariable("userId") long userId, Model model){
       Optional<User>optionalUser = userService.findById(userId);
       if(optionalUser.isPresent()){
           model.addAttribute("user",optionalUser.get());
           model.addAttribute("departments",departmentService.getAllDepartments());
           Stream<Role> roles = roleService.getAllRoles().stream().filter(role -> !role.getRole().name().equals(RoleName.ROLE_ADMIN.name()));
           model.addAttribute("roles", roles.collect(Collectors.toList()));
           model.addAttribute("userDepartment",optionalUser.get().getDepartment().getDepartmentName());
           model.addAttribute("userRole",optionalUser.get().getRole());

       }
       return "user/edit-user";
    }

    @PostMapping("/admin/EditUser")
    public String editUser(@ModelAttribute("user") User user,@Valid String roleId){
        Optional<User> optionalUser= userService.findById(user.getId());
        if(optionalUser.isPresent()){
            user.setVisits(optionalUser.get().getVisits());
            user.setAppointments(optionalUser.get().getAppointments());
            user.setPassword(optionalUser.get().getPassword());
        }
        Optional<Role> role = roleService.getRoleById(roleId);
        role.ifPresent(user::addRole);
        System.out.println(user.toString());
        userService.updateUser(user);
        return "redirect:/admin/users";
    }



    @GetMapping("/admin/addUser")
    public String showaddUserForm(Model model){

        User user = new User();
        model.addAttribute("user",user);
        model.addAttribute("departments",departmentService.getAllDepartments());
        Stream<Role> roles = roleService.getAllRoles().stream().filter(role -> !role.getRole().name().equals(RoleName.ROLE_ADMIN.name()));
        model.addAttribute("roles", roles.collect(Collectors.toList()));

        return "user/add-user";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("user") User user, @Valid  String departmentId, @Valid String roleId, Model model){
        if (userExistsByEmail(user.getEmail())) {
            model.addAttribute("user",user);
            model.addAttribute("message",new Message("Përdoruesi me këtë email ekziston","warning"));
            model.addAttribute("departments",departmentService.getAllDepartments());
            Stream<Role> roles = roleService.getAllRoles().stream().filter(role -> !role.getRole().name().equals(RoleName.ROLE_ADMIN.name()));
            model.addAttribute("roles", roles.collect(Collectors.toList()));
            return "user/add-user";
        }
        Optional<Department> department = departmentService.findById(departmentId);
        department.ifPresent(user::setDepartment);
        user.setActive("1");
        Optional<Role> role = roleService.getRoleById(roleId);
        role.ifPresent(user::addRole);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.addPatient(user);
        return "redirect:/admin/users";
    }

    @RequestMapping("/usersAutoComplete")
    @ResponseBody
    public List<String> userNameAutoComplete(@RequestParam(value="term", required = false, defaultValue="") String term){
        List<String> suggestions = new ArrayList<>();

        if(term.length() >= 3){
            firstThreeCharacters = term;
            allUsers = userService.fetchUsers(term);
        }


        for(User user : allUsers){
            String userNameLastName = user.getFirstName()+" "+user.getLastName();
            if (userNameLastName.contains(term) && user.getRole().equals(RoleName.ROLE_DOCTOR.name())) {
                suggestions.add(user.getFirstName()+" "+user.getLastName());
            }
        }
        return suggestions;
    }

    public boolean userExistsByEmail(String email){
       return userService.existsByEmail(email);
    }

}
