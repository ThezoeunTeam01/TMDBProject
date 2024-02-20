package com.example.tmdbproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String healthCheck() {
        return "Service is up and running........";
    }
}
