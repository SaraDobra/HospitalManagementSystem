package com.hospitalsystem.hospital_management_system;

import com.hospitalsystem.hospital_management_system.services.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.LongAdder;

@org.springframework.web.bind.annotation.ControllerAdvice()
public class ControllerAdvice {
    @Autowired
    HelperService helperService;

    public String getLoggedUserNanme(){
        if(helperService.getLoggedUser() != null){
            System.out.println("Role="+helperService.getLoggedUser().getRole());
        }
        System.out.println("--------------- "+helperService.getLoggedUserName());
       return helperService.getLoggedUserName();
    }

    @ModelAttribute
    public void handleRequest(HttpServletRequest request, Model model) {
        model.addAttribute("userName", getLoggedUserNanme());
        if(helperService.getLoggedUser() != null){
            model.addAttribute("user_role",helperService.getLoggedUser().getRole());
        }
    }

}
