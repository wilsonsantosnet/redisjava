package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.DatetimeSingleton;


@RestController
public class TestHelthController {


    @GetMapping("/health")
    public String getHealth() {

        return new DatetimeSingleton().getDatetime().toString();
        
    }
}