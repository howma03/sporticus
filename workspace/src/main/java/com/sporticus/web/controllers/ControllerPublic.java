package com.sporticus.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mark on 11/02/2017.
 */
@Controller
public class ControllerPublic {

    @RequestMapping("/login")
    public String login() {
        return "public/login";
    }

    @RequestMapping("/home")
    public String home() {
        return "public/home";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "public/accessDenied";
    }

    @RequestMapping("/error2")
    public String errorHandler() {
        return "public/error";
    }
}
