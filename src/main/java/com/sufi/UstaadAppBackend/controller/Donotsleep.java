package com.sufi.UstaadAppBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Donotsleep {

    @GetMapping("/ping")
    public String ping() {
        return "Backend is alive!";
    }
}
