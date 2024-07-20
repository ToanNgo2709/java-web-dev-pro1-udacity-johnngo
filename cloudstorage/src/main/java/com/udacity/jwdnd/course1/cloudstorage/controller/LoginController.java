package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping()
    public String login() {
//        if (user == null) {
//            return "redirect:signup";
//        }

//        try {
//            userService.verifyUser(user.getUsername(), user.getPassword());
//        } catch (Exception e) {
//            return "redirect:signup?error";
//        }

        return "login";
    }
}
