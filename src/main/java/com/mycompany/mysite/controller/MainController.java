package com.mycompany.mysite.controller;

import com.mycompany.mysite.model.MyUserDetails;
import com.mycompany.mysite.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String home(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            model.addAttribute("user" ,user);
        }
        model.addAttribute("info" ,"Welcome to My Site");
        return "home";
    }
}
