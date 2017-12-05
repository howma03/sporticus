package com.sporticus.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mark on 11/02/2017.
 */
@Controller
public class ControllerPublic {

    @RequestMapping("/")
    public String errorHandler() {
        return "forward:/app/index.html";
    }
}
