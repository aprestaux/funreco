package com.github.aprestaux.funreco.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping({"/", "index.html"})
    public String page(Model model) {
        model.addAttribute("message", "Hello world");

        return "index";
    }
}