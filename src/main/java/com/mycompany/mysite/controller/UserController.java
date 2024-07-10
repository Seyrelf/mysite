package com.mycompany.mysite.controller;

import com.mycompany.mysite.model.MyUserDetails;
import com.mycompany.mysite.model.Note;
import com.mycompany.mysite.model.User;
import com.mycompany.mysite.model.UserBig;
import com.mycompany.mysite.service.NoteService;
import com.mycompany.mysite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired(required = true)
    private UserService userService;


    @GetMapping(value = "/signUp")
    public String registerUser(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            model.addAttribute("authuser" ,user);
        }
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping(value = "/signUp/save")
    public String registerUser(User user) {
        userService.save(user);
        System.out.println("User saved");
        return "redirect:/";
    }
    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/settings")
    public String settings(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            UserBig userBig = new UserBig(user);
            model.addAttribute("user" ,userBig);
        }
        return "settings";
    }

    @PostMapping(value = "/settings/save")
    public String settingsSave(UserBig userBig,Model model) {
        User user = new User(userBig);
        userService.save(user);
        model.addAttribute("user" ,new UserBig(userService.findById(user.getId())));
        return "settings";
    }
}
